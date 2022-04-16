package fragments.SETTINGS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import USERS.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccountFragment extends Fragment {

    public static final String TAG = "UserAccountFragment";

    private FloatingActionButton backButton, editButton, changeImage;
    private EditText mName, mAddress, mEmail, mPhone;
    private MaterialButton mSave, mRevert;
    private CircleImageView userProfileImage;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

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
        ref = database.getReference("users");

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
                        Log.d(TAG, "onSuccess: Picasso"+imageURL);
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
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new SettingsFragment())
                        .commit();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    private void revert_changes() {
        editButton.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.INVISIBLE);
        mRevert.setVisibility(View.INVISIBLE);
    }

    private void save_changes() {
        editButton.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.INVISIBLE);
        mRevert.setVisibility(View.INVISIBLE);
    }
}