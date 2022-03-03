package com.example.mystore.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    MaterialButton loginButton, signUpButton;
    ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // This method is used so that the splash screen uses full screen
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.button_log_in);
        signUpButton = findViewById(R.id.button_sign_up);
        mProgressBar = findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        signUpButton.setVisibility(View.INVISIBLE);




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        upDateUI(currentUser);

    }

    private void upDateUI(FirebaseUser currentUser) {
        if (currentUser != null){
            //move to content
            mProgressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent contentIntent = new Intent(MainActivity.this, ContentActivity.class);
                    startActivity(contentIntent);
                    finish();
                }
            },3000);

        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            });
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SignUp = new Intent(MainActivity.this,SignUpActivity.class);
                    startActivity(SignUp);
                }
            });
        }
    }
}