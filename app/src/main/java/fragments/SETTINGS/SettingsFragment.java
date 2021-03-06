package fragments.SETTINGS;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.example.mystore.ui.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private MaterialButton logOutButton,accountButton, changePassword, contactUsButton, orderButton, wishlistButton;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();
        logOutButton = v.findViewById(R.id.logOutButton);
        accountButton = v.findViewById(R.id.account_settings);
        changePassword = v.findViewById(R.id.changePasswordButton);
        contactUsButton = v.findViewById(R.id.customerCare_button);
        orderButton = v.findViewById(R.id.order_history);
        wishlistButton = v.findViewById(R.id.wishlist);
        setUI();

        return v;
    }

    private void setUI() {
        logOutButton.setOnClickListener(view -> {
            mAuth.signOut();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user == null){
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);

            }
        });
        accountButton.setOnClickListener(view -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new UserAccountFragment())
                    .commit();
        });
        changePassword.setOnClickListener(view -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new UpdatePasswordFragment())
                    .commit();
        });
        contactUsButton.setOnClickListener(view -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new ContactUsFragment())
                .commit()
        );
        orderButton.setOnClickListener(view -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new OrderFragment())
                    .commit();
        });
        wishlistButton.setOnClickListener(view -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new WishlistFragment())
                    .commit();
        });

    }
}