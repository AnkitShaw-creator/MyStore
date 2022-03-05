package fragments.ITEMS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Items.itemAdapter;
import Items.items;


public class HomeFragment extends Fragment  implements itemAdapter.onItemListener {


    private static final String LOG_TAG = "HomeFragment";

    private RecyclerView ItemList;
    private itemAdapter mAdapter;
    private FragmentContainerView detailFragmentContainer;
    private List<items> itemList;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private itemViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        detailFragmentContainer = view.findViewById(R.id.detailFragmentContainer);
        viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://deft-apparatus-339005-default-rtdb.asia-southeast1.firebasedatabase.app");
        //added the URl of the database location as was getting a warning of database being in a different region
        ref = database.getReference();

        ItemList = view.findViewById(R.id.content_list);
        ItemList.setVisibility(View.VISIBLE);
        ItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList = new ArrayList<>();
        mAdapter = new itemAdapter(itemList, this);
        ItemList.setAdapter(mAdapter);

        setUI();

        return view;
    }

    public void setUI(){
        ref.child("items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                //Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "onChildAdded: "+snapshot.getChildrenCount());
                items newItem = snapshot.getValue(items.class);

                Log.i(LOG_TAG, "onChildAdded: "+newItem.getTitle());
                itemList.add(newItem);
                Log.i(LOG_TAG, "onChildAdded: "+itemList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                items newItem = snapshot.getValue(items.class);
                itemList.add(newItem);
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
                Log.e(LOG_TAG, "onCancelled: ", error.toException());
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        ItemList.setVisibility(View.INVISIBLE);
        viewModel.setItem(itemList.get(position));
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragmentContainer,new ItemFullContentFragment(), null)
                .addToBackStack("Detail Fragment")
                .commit();
        //getChildFragmentManager().executePendingTransactions();
    }
}