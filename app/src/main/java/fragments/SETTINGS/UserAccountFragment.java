package fragments.SETTINGS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mystore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserAccountFragment extends Fragment {

    private FloatingActionButton backButton;
    private EditText mName, mAddress, mEmail, mPhone;

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

        backButton = v.findViewById(R.id.back_setting_fab);
        mName = v.findViewById(R.id.user_account_FullName);
        mName.setEnabled(false);
        mAddress = v.findViewById(R.id.user_account_Address);
        mAddress.setEnabled(false);
        mEmail = v.findViewById(R.id.user_account_Email);
        mEmail.setEnabled(false);
        mPhone = v.findViewById(R.id.user_account_PhoneNumber);
        mPhone.setEnabled(false);
        setUI();
        return v;
    }

    private void setUI() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new SettingsFragment())
                        .commit();
            }
        });
    }
}