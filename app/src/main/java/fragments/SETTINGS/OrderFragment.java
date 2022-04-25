package fragments.SETTINGS;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Order.OrderAdapter;
import Order.order;

    //TODO: Update the code in this file. Now the orders would be shown depending on the time they were placed,
    // and the actual order would be shown in a different list
    // group of order -> single order

public class OrderFragment extends Fragment implements OrderAdapter.orderClickListener {

    private static final String TAG = "OrderFragment";
    private RecyclerView orderList;
    private TextView noOrder;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private OrderAdapter mAdapter;
    private ArrayList<order> orders;

    public OrderFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_order, container, false);
        orderList = v.findViewById(R.id.order_recycler_view);
        noOrder = v.findViewById(R.id.empty_list_tv);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(DATABASE_URL);
        ref = database.getReference().child("users").child(mAuth.getCurrentUser().getUid());

        orders = new ArrayList<>();
        mAdapter = new OrderAdapter(orders, this);

        setUI();
        return v;
    }

    private void setUI() {
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList.setAdapter(mAdapter);

        ref.child("orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.hasChildren()){
                    noOrder.setVisibility(View.INVISIBLE);
                    order o = snapshot.getValue(order.class);
                    orders.add(o);
                    mAdapter.notifyDataSetChanged();

                }
                else{
                    noOrder.setVisibility(View.VISIBLE);
                    orderList.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mAdapter = new OrderAdapter(orders, this);
        orderList.swapAdapter(mAdapter, true);

    }

    @Override
    public void OnOrderClick(int position) {

    }
}