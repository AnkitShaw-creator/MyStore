package fragments.HOME;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Items.itemAdapter;
import Items.items;


public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";

    private FragmentContainerView detailFragmentContainer;


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
        //Log.d(TAG, "onCreateView:"+ref.child("items"));

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragmentContainer,new ItemListFragment(), null)
                .commit();

        /*ItemList = view.findViewById(R.id.content_list);
        ItemList.setVisibility(View.VISIBLE);
        ItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList = new ArrayList<>();
        mAdapter = new itemAdapter(itemList, this);
        ItemList.setAdapter(mAdapter);*/

        return view;
    }


}