package fragments.SETTINGS;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mystore.R;
import com.example.mystore.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UpdatePasswordFragment extends Fragment {


    private static final String TAG= "UpdatePasswordFragment";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AuthCredential userCred;
    private EditText mEmail, mOldPassword, mNewPassword;
    private MaterialButton mUpdate;
    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_password, container, false);

        mAuth = FirebaseAuth.getInstance();
        user  = mAuth.getCurrentUser();

        mEmail = v.findViewById(R.id.password_email_et);
        mOldPassword = v.findViewById(R.id.password_old_et);
        mNewPassword = v.findViewById(R.id.password_new_et);
        mUpdate = v.findViewById(R.id.updatePassword);
        if(user != null) {
            setUI();
        }
        return v;
    }

    private void setUI() {

        if(user != null) {
            final String email = user.getEmail();
            mEmail.setText(email);

            mUpdate.setOnClickListener(view -> {
                final String oldPassword = String.valueOf(mOldPassword.getText());
                final String newPassword = String.valueOf(mNewPassword.getText());

                if(!email.equals("") && !oldPassword.equals("")) {
                    userCred = EmailAuthProvider.getCredential(email, oldPassword);
                    user.reauthenticate(userCred).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Password was updated", Toast.LENGTH_SHORT).show();
                                            logOut();
                                            Log.d(TAG, "onComplete: Password changed");
                                        }
                                        else{
                                            Toast.makeText(getContext(), "Password was not updated", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "onComplete: Password  was changed", task.getException());
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getContext(), "Password was not updated", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onComplete: ", task.getException());
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Password was not updated", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Log.e(TAG, "setUI:  User not found" );
        }
    }

    private void logOut() {
        mAuth.signOut();
        if(mAuth.getCurrentUser() == null){
            Intent signInIntent = new Intent(getActivity(), MainActivity.class);
            signInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signInIntent);
        }
    }
}