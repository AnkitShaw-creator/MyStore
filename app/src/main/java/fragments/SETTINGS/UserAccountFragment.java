package fragments.SETTINGS;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.Map;

import USERS.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccountFragment extends Fragment {

    public static final String TAG = "UserAccountFragment";
    private  Uri IMAGE;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private FloatingActionButton backButton, editButton, changeImage;
    private EditText mName, mAddress, mEmail, mPhone;
    private MaterialButton mSave, mRevert;
    private CircleImageView userProfileImage;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference ref;
    private StorageReference storageRef;

    private static boolean updated = false;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://deft-apparatus-339005-default-rtdb.asia-southeast1.firebasedatabase.app");
        storage = FirebaseStorage.getInstance();
        ref = database.getReference("users");
        storageRef = storage.getReference().child("photos");

        backButton = v.findViewById(R.id.back_setting_fab);
        editButton = v.findViewById(R.id.edit_fab);
        changeImage = v.findViewById(R.id.change_image_button);
        changeImage.setVisibility(View.INVISIBLE);

        userProfileImage = v.findViewById(R.id.user_account_profile_image);
        userProfileImage.setEnabled(false);
        mName = v.findViewById(R.id.user_account_FullName);
        mName.setEnabled(false);
        mAddress = v.findViewById(R.id.user_account_Address);
        mAddress.setEnabled(false);
        mEmail = v.findViewById(R.id.user_account_Email);
        mEmail.setEnabled(false);
        mPhone = v.findViewById(R.id.user_account_PhoneNumber);
        mPhone.setEnabled(false);
        mSave = v.findViewById(R.id.account_save_update);
        mSave.setVisibility(View.INVISIBLE);
        mRevert = v.findViewById(R.id.account_revert_changes);
        mRevert.setVisibility(View.INVISIBLE);

        setUI();
        return v;
    }

    private void setUI() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        Log.d(TAG, "setUI:"+currentUser.getUid());

        ref.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                Log.d(TAG, "onDataChange: user"+user.getName());
                String imageURL = user.getImage();
                Log.d(TAG, "onDataChange: adding image: "+imageURL);
                Picasso.get().load(imageURL).placeholder(R.drawable.ic_person)
                        .into(userProfileImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Log.d(TAG, "onSuccess: Picasso"+imageURL);
                    }
                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(imageURL).placeholder(R.drawable.ic_person)
                                .into(userProfileImage);
                    }
                });
                mName.setText(user.getName());
                mAddress.setText(user.getAddress());
                mEmail.setText(user.getEmail());
                mPhone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!updated) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new SettingsFragment())
                            .commit();
                }
                if(updated){
                    Log.e(TAG, "onClick: back button: updates"+ updated );
                    // show unsaved changes dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Unsaved changes")
                            .setMessage("You have changes that are not saved to the database. Would you like to save them first or cancel the changes made?")
                            .setPositiveButton("Stay in the page", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).setNegativeButton("No, discard the changes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updated = false;
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainerView, new SettingsFragment())
                                    .commit();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!updated)
                    updated = true;
                userProfileImage.setEnabled(true);
                changeImage.setVisibility(View.VISIBLE);
                mName.setEnabled(true);
                mAddress.setEnabled(true);
                mPhone.setEnabled(true);
                mSave.setVisibility(View.VISIBLE);
                mRevert.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);
            }
        });

        mSave.setOnClickListener(view->{

            save_changes();
        });

        mRevert.setOnClickListener(view -> {
            revert_changes();
        });

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            IMAGE = result.getData().getData();
                            Log.d(TAG, "onActivityResult: ");
                            userProfileImage.setImageURI(IMAGE);
                        }
                    }
                }
        );


        changeImage.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(galleryIntent);
            change_image();
        });
    }

    private void change_image() {
    }


    private void revert_changes() {
        editButton.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.INVISIBLE);
        mRevert.setVisibility(View.INVISIBLE);
        changeImage.setVisibility(View.INVISIBLE);
    }

    private void save_changes() {
        editButton.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.INVISIBLE);
        mRevert.setVisibility(View.INVISIBLE);
        changeImage.setVisibility(View.INVISIBLE);

        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
        String address = mAddress.getText().toString();
        String email = mEmail.getText().toString();

        final StorageReference tempRef = storageRef.child(MessageFormat.format("profile_photos/{0}_profile_pic.jpg", mAuth.getUid()));
        UploadTask imageTask = tempRef.putFile(IMAGE);

        Task<Uri> imageUploadTask = imageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    Log.e(TAG, "then: ", task.getException());
                    throw task.getException();
                }
                return tempRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    String url = task.getResult().toString();
                    Log.d(TAG, "onComplete: changed image url"+url);

                    Users updatedUsers = new Users(address,email, url, name, phone);
                    Map<String, Object> map = updatedUsers.toMap();
                    ref.child(mAuth.getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "User details were updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onComplete: ", task.getException() );
                            }
                        }
                    });

                }
            }
        });
    }
}