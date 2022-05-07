package fragments.HOME;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import Order.order;


public class quantityFragment extends Fragment {


    private static final String TAG = "quantityFragment";

    private TextView inputQuantity;
    private EditText delivery_address;
    private MaterialButton min, max, save,cancel;
    private RadioGroup paymentMode;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private itemViewModel viewModel;

    public quantityFragment() {
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
        View v= inflater.inflate(R.layout.fragment_quantity, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);
        //p = viewModel.getParent();

        inputQuantity = v.findViewById(R.id.input_quantity);
        delivery_address = v.findViewById(R.id.delivery_address);
        paymentMode = v.findViewById(R.id.payment_group);

        min= v.findViewById(R.id.quantity_button_min);
        max=v.findViewById(R.id.quantity_button_max);
        save=v.findViewById(R.id.save_order_details);
        cancel = v.findViewById(R.id.cancel_order_details);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            user = mAuth.getCurrentUser();
            database = FirebaseDatabase.getInstance(DATABASE_URL);
            ref = database.getReference("users");
            setUI();
        }
        return v;
    }

    private void setUI() {
        min.setOnClickListener(view -> {
            int value = Integer.parseInt(inputQuantity.getText().toString());
            if(value > 1)
                inputQuantity.setText(String.valueOf(--value));
            else
                Toast.makeText(getContext(), "Quantity can't be 0", Toast.LENGTH_SHORT).show();
        });

        max.setOnClickListener(view -> {
            int value = Integer.parseInt(inputQuantity.getText().toString());
            if(value < 100)
                inputQuantity.setText(String.valueOf(++value));
            else
                Toast.makeText(getContext(), "You can not order more than 100 items at once", Toast.LENGTH_SHORT).show();
        });
        final String[] payment_method = {""};
        paymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.cash_on_delivery: {
                        payment_method[0] = "cash_on_delivery";
                        break;
                    }
                    case R.id.upi:{
                        payment_method[0]= "upi";
                        break;
                    }
                    case R.id.debit_credit_card:{
                        payment_method[0] ="debit/credit card";
                        break;
                    }
                    default:
                        break;

                }
            }
        });


        save.setOnClickListener(view -> {
            String address = delivery_address.getText().toString();
            String quantity = inputQuantity.getText().toString();
            String payment = payment_method[0];
            if(address != null && quantity != null){
                final String[] name = new String[1];
                final String rate[]= new String[1];
                viewModel.getItem().observe(getViewLifecycleOwner(), item -> {
                    if(item != null) {
                        name[0] = item.getTitle();
                        rate[0] = item.getPrice();
                    }
                });
                String time = new Date().toString();

                order o = new order(address,name[0],payment, quantity,rate[0], time);
                Map<String, Object> pending_order = o.toMap();
                //Log.d(TAG, "set_UI: key"+orderKey);
                ref.child(user.getUid()).child("pending_orders")
                        .child(o.getOrder_id()).setValue(pending_order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Log.e(TAG, "onComplete: ", task.getException());
                                }
                            }
                        });
            }
        });

    }
}