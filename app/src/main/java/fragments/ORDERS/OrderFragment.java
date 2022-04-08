package fragments.ORDERS;

//import static Order.OrderAdapter.TotalFinal;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Order.OrderAdapter;
import Order.order;

public class OrderFragment extends Fragment implements OrderAdapter.orderClickListener {

    private static final String TAG = "OrderFragment";
    private static int TOTAL;
    private static boolean HAS_CHILDS = false;
    private ShapeableImageView noOrderIV;
    private MaterialTextView noOrderTV,totalTV;
    private RecyclerView orderList;
    private ConstraintLayout orderTotal;
    private MaterialButton placeOrder;

    private ArrayList<order> pendingOrder;

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref, ref2;

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

        View v = inflater.inflate(R.layout.fragment_order, container, false);
        noOrderIV = v.findViewById(R.id.no_order_ImageView);
        noOrderIV.setVisibility(View.INVISIBLE);
        noOrderTV = v.findViewById(R.id.no_order_textView);
        noOrderTV.setVisibility(View.INVISIBLE);

        orderList = v.findViewById(R.id.order_List);
        orderList.setVisibility(View.INVISIBLE);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));

        orderTotal = v.findViewById(R.id.order_total);
        orderTotal.setVisibility(View.INVISIBLE);
        totalTV = v.findViewById(R.id.display_total);
        placeOrder = v.findViewById(R.id.place_order);

        pendingOrder = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance(DATABASE_URL);
        ref = database.getReference("users").child(user.getUid());
        ref2 = database.getReference("users").child(user.getUid());
        //Log.d(TAG, "onCreateView: "+user.getUid());
        TOTAL=0;
        setUI();
        return v;
    }

    private void setUI() {
        orderList.setVisibility(View.INVISIBLE);
        mAdapter = new OrderAdapter(pendingOrder, this);
        ref.child("pending_orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Log.d(TAG, "onChildAdded: "+snapshot.getChildrenCount());
                if(snapshot.hasChildren()){
                    HAS_CHILDS = true;
                    noOrderIV.setVisibility(View.INVISIBLE);
                    noOrderTV.setVisibility(View.INVISIBLE);
                    orderList.setVisibility(View.VISIBLE);
                    orderTotal.setVisibility(View.VISIBLE);
                    //Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                    order o = snapshot.getValue(order.class);
                    String Rate = o.getRate();
                    int rate = Integer.parseInt(Rate.substring(0, Rate.indexOf("/")));
                    int quantity = Integer.parseInt(o.getQuantity());
                    TOTAL += rate*quantity;
                    totalTV.setText(MessageFormat.format("Total: {0}", String.valueOf(TOTAL)));
                    Log.d(TAG, "onChildAdded: TOTAL:" +TOTAL);
                    pendingOrder.add(o);
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    orderList.setVisibility(View.INVISIBLE);
                    orderTotal.setVisibility(View.INVISIBLE);
                    noOrderIV.setVisibility(View.VISIBLE);
                    noOrderTV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.hasChildren()){
                    HAS_CHILDS=true;
                    noOrderIV.setVisibility(View.INVISIBLE);
                    noOrderTV.setVisibility(View.INVISIBLE);
                    orderList.setVisibility(View.VISIBLE);
                    orderTotal.setVisibility(View.VISIBLE);
                    //Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                    order o = snapshot.getValue(order.class);
                    String Rate = o.getRate();
                    int rate = Integer.parseInt(Rate.substring(0, Rate.indexOf("/")));
                    int quantity = Integer.parseInt(o.getQuantity());
                    TOTAL += rate*quantity;
                    totalTV.setText(MessageFormat.format("Total: {0}", String.valueOf(TOTAL)));
                    Log.d(TAG, "onChildAdded: TOTAL:" +TOTAL);
                    pendingOrder.add(o);
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    orderList.setVisibility(View.INVISIBLE);
                    orderTotal.setVisibility(View.INVISIBLE);
                    noOrderIV.setVisibility(View.VISIBLE);
                    noOrderTV.setVisibility(View.VISIBLE);
                }
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

        if(!HAS_CHILDS){
            orderList.setVisibility(View.INVISIBLE);
            orderTotal.setVisibility(View.INVISIBLE);
            noOrderIV.setVisibility(View.VISIBLE);
            noOrderTV.setVisibility(View.VISIBLE);
        }

        //Log.d(TAG, "setUI: "+pendingOrder.get(0).getName());
        mAdapter = new OrderAdapter(pendingOrder, this);
        orderList.swapAdapter(mAdapter, true);
        TOTAL=0;

        placeOrder.setOnClickListener(view -> {
            ref.child("pending_orders").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    order o = snapshot.getValue(order.class);
                    ref2.child("orders").setValue(o.toMap());
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
        });
    }

    @Override
    public void OnOrderClick(int position) {
    }
}