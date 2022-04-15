package fragments.SETTINGS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;


public class ContactUsFragment extends Fragment {

    private static final String TAG = "ContactUsFragment";

    private MaterialButton callUs, emailUs;

    public ContactUsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        callUs = v.findViewById(R.id.callUsButton);
        emailUs = v.findViewById(R.id.emailUsButton);
        setUI();
        return v;
    }

    private void setUI() {
        callUs.setOnClickListener(view -> {});

        emailUs.setOnClickListener(view -> {});
    }
}