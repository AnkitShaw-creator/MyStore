package fragments.SETTINGS;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Items.itemAdapter;
import Items.items;
import fragments.HOME.ItemFullContentFragment;
import fragments.HOME.itemViewModel;


public class WishlistFragment extends Fragment implements itemAdapter.onItemListener {


    private static final String TAG = "WishlistFragment";

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private RecyclerView wishlist;
    private itemAdapter adapter;
    private  static ArrayList<items> i;
    private itemViewModel viewModel;

    public WishlistFragment() {
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
        View v = inflater.inflate(R.layout.fragment_wishlist, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance(DATABASE_URL).getReference().child("users").child(user.getUid());
        wishlist = v.findViewById(R.id.user_wishlist);
        i = new ArrayList<>();
        adapter = new itemAdapter(i,this);
        viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);
        setUI();
        return v;
    }

    private void setUI() {
        wishlist.setLayoutManager(new LinearLayoutManager(getContext()));

        ref.child("wishlist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                items I = snapshot.getValue(items.class);
                i.add(I);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                items I = snapshot.getValue(items.class);
                i.add(I);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                items I = snapshot.getValue(items.class);
                i.remove(I);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });

        wishlist.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: ");
        viewModel.setItem(i.get(position));
        viewModel.setParent("Wishlist");
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new ItemFullContentFragment(), null)
                .commit();
    }
}