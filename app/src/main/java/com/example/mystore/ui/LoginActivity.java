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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    private TextInputEditText mEmail, mPassword;
    private MaterialButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.LoginEmail);
        mPassword = findViewById(R.id.LoginPassword);
        signInButton = findViewById(R.id.loginButton);
        

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(mEmail.getText()).toString();
                String password = mPassword.getText().toString();
                Log.d(LOG_TAG, "onCreate:"+email+":"+password);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d(LOG_TAG, "onComplete: Sign in successful ");
                                    Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                                    updateUI(true);
                                }
                                else{
                                    Log.e(LOG_TAG, "onComplete: Sign in failed",task.getException());
                                    Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    private void updateUI(boolean b) {
        if(b){
            Intent contentActivity = new Intent(LoginActivity.this, ContentActivity.class);
            startActivity(contentActivity);
            finish();
        }
    }
}