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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputEditText mFirstName,mLastName, mPhoneNumber, mEmail, mPassword;
    private static final String LOG_TAG = "SignUpActivity";
    private MaterialButton mAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mFirstName = findViewById(R.id.userFirstName);
        mLastName = findViewById(R.id.userLastName);
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
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "onComplete: SIgn Up complete");
                    Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    updateUI(true);
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
            startActivity(contentActivity);
            finish();
        }
        /*else{
            Intent parentActivity = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(parentActivity);
            finish();
        }*/
    }
}