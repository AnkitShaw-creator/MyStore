package com.example.mystore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private TextInputEditText mName,mAddress, mPhoneNumber, mEmail, mPassword;
    private static final String LOG_TAG = "SignUpActivity";
    private MaterialButton mAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://deft-apparatus-339005-default-rtdb.asia-southeast1.firebasedatabase.app");
        userRef = database.getReference();

        mName = findViewById(R.id.userFullName);
        mAddress = findViewById(R.id.userAddress);
        mPhoneNumber = findViewById(R.id.userPhoneNumber);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mAddAccount = findViewById(R.id.button_add_account);

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
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, String> user = new HashMap<>();
                    user.put("name", userName);
                    user.put("address",address);
                    user.put("phone",phoneNumber);
                    user.put("email",email);
                    String userId = userName+database.hashCode();

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