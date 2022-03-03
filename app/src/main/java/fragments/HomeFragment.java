package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Items.items;
import Items.itemAdapter;


public class HomeFragment extends Fragment  implements itemAdapter.onItemListener {


    private static final String LOG_TAG = "HomeFragment";
    private RecyclerView ItemList;
    private itemAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();


        ItemList = view.findViewById(R.id.content_list);
        ItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<items> itemList = new ArrayList<>();
        //itemList.add(new Items(R.drawable.ic_icon_order, "Item1", "Small description of the item1"));
        //itemList.add(new Items(R.drawable.ic_icon_order, "Item2", "Small description of the item2"));
        //itemList.add(new Items(R.drawable.ic_icon_order, "Item3", "Small description of the item3"));
        //itemList.add(new Items(R.drawable.ic_icon_order, "Item4", "Small description of the item4"));

        ref.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                items newItem = snapshot.getValue(items.class);
                Log.d(LOG_TAG, "onDataChange: item received");
                itemList.add(newItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Item  not added", Toast.LENGTH_SHORT).show();
            }
        });
        /*ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                Items newItem = snapshot.getValue(Items.class);
                itemList.add(newItem);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Item  not added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/

        mAdapter = new itemAdapter(itemList, this);
        ItemList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        getChildFragmentManager()
                .beginTransaction().attach(new ItemFullContentFragment()).replace(R.id.fragmentContainerView,new ItemFullContentFragment(), null)
                .commit();
    }
}