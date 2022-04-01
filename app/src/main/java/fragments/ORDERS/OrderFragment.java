package fragments.ORDERS;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Order.order;

public class OrderFragment extends Fragment implements OrderAdapter.orderClickListener {

    private static final String TAG = "OrderFragment";

    private ShapeableImageView noOrderIV;
    private MaterialTextView noOrderTV;
    private RecyclerView orderList;

    private ArrayList<order> pendingOrder;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private OrderAdapter mAdapter;

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
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        noOrderIV = v.findViewById(R.id.no_order_ImageView);
        noOrderTV = v.findViewById(R.id.no_order_textView);
        orderList = v.findViewById(R.id.order_List);

        pendingOrder = new ArrayList<>();

        mAdapter = new OrderAdapter(pendingOrder, this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://deft-apparatus-339005-default-rtdb.asia-southeast1.firebasedatabase.app");
        ref = database.getReference("users").child(user.getUid());
        Log.d(TAG, "onCreateView: "+user.getUid());
        setUI();
        return v;
    }

    private void setUI() {
        Log.d(TAG, "setUI: Here");
        orderList.setVisibility(View.INVISIBLE);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));

        ref.child("pending_orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: "+snapshot.getChildrenCount());
                if(snapshot.hasChildren()){
                    orderList.setVisibility(View.VISIBLE);
                    noOrderIV.setVisibility(View.INVISIBLE);
                    noOrderTV.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                    order o = snapshot.getValue(order.class);
                    Log.d(TAG, "onChildAdded: pending_order_id: " +o.getOrder_id());
                    pendingOrder.add(o);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                order o = snapshot.getValue(order.class);
                Log.d(TAG, "onChildAdded: pending_order_id: " +o.getOrder_id());
                pendingOrder.add(o);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException() );
            }
        });
        //Log.d(TAG, "setUI: "+pendingOrder.get(0).getName());
        mAdapter = new OrderAdapter(pendingOrder, this);
        orderList.setAdapter(mAdapter);

    }

    @Override
    public void OnOrderClick(int position) {

    }
}