package com.example.mystore.ui;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;
import static com.example.mystore.ui.MainActivity.STORAGE_URL;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import USERS.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private DatabaseReference userRef;
    private StorageReference storageRef;

    private TextInputEditText mName,mAddress, mPhoneNumber, mEmail, mPassword;
    private CircleImageView userImage;
    private FloatingActionButton addImage;
    private static final String LOG_TAG = "SignUpActivity";
    private MaterialButton mAddAccount;

    private Uri IMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(DATABASE_URL);
        storage = FirebaseStorage.getInstance();
        userRef = database.getReference();
        userRef.keepSynced(true);
        storageRef = storage.getReference();
        userImage = findViewById(R.id.user_image);
        mName = findViewById(R.id.userFullName);
        mAddress = findViewById(R.id.userAddress);
        mPhoneNumber = findViewById(R.id.userPhoneNumber);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mAddAccount = findViewById(R.id.button_add_account);
        addImage = findViewById(R.id.add_photo_button);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            IMAGE = result.getData().getData();
                            Log.d(LOG_TAG, "onActivityResult: ");
                            userImage.setImageURI(IMAGE);
                        }
                    }
                }
        );


        addImage.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(Intent.createChooser(galleryIntent, "Select an image"));
        });





        mAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void addUser() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String userName = mName.getText().toString();
        String address = mAddress.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        final String[] image_download_uri = new String[1];




        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final StorageReference ref = storageRef.child(MessageFormat.format("photos/profile_photos/{0}_profile_pic.jpg", mAuth.getUid()));
                    UploadTask imageTask = ref.putFile(IMAGE);

                    Task<Uri> result = imageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()) {
                                Log.e(LOG_TAG, "Image Upload: ", task.getException() );
                                throw task.getException();
                            }

                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {
                                image_download_uri[0] = task.getResult().toString();

                                String url = image_download_uri[0];
                                Log.d(LOG_TAG, "onComplete: image url "+url);
                                Users newUser = new Users(address, email, url, userName, phoneNumber);

                                Map<String, String> user = new HashMap<>();
                                user = newUser.toMap();
                                String userId = mAuth.getUid();

                                userRef.child("users").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d(LOG_TAG, "userRef-onComplete: SIgn Up complete");
                                            Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            updateUI(true);
                                        }
                                        else{
                                            Log.e(LOG_TAG, "userRef-onComplete:  SignUp failed", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Authentication failed, please try again", Toast.LENGTH_SHORT).show();
                                            updateUI(false);
                                        }
                                    }
                                });
                            }

                            else{
                                Log.e(LOG_TAG, "onComplete: ",task.getException() );
                            }
                        }
                    });

                }
                else{
                    Log.e(LOG_TAG, "onComplete:  SignUp failed", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed, please try again", Toast.LENGTH_SHORT).show();
                    updateUI(false);
                }
            }
        });

    }

    private void updateUI(boolean b) {
        if(b){
            Intent contentActivity = new Intent(SignUpActivity.this, ContentActivity.class);
            contentActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(contentActivity);
            finish();
        }
        else{
            Intent parentActivity = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(parentActivity);
            finish();
        }
    }
}