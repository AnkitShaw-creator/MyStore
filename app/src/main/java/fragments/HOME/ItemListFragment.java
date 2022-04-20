package fragments.HOME;

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
import android.widget.Toast;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Items.itemAdapter;
import Items.items;


public class ItemListFragment extends Fragment implements itemAdapter.onItemListener{

    private static final String TAG = ItemListFragment.class.getName();
    private RecyclerView contentList;
    private ArrayList<items> itemList;
    private itemAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private itemViewModel viewModel;

    public ItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_item_list,container,false);
         contentList = v.findViewById(R.id.content_list);
         itemList = new ArrayList<>();
         contentList.setLayoutManager(new LinearLayoutManager(getContext()));
         viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);

         mAuth = FirebaseAuth.getInstance();
         if(mAuth.getCurrentUser() != null){
             database = FirebaseDatabase.getInstance(DATABASE_URL);
             ref = database.getReference();
         }
         mAdapter = new itemAdapter(itemList,this);

         ref.child("items").addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 //Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                 items currentItem = snapshot.getValue(items.class);
                 itemList.add(currentItem);
                 mAdapter.notifyDataSetChanged();
                 //Log.d(TAG, "onChildChanged: itemlist-"+ itemList.size());
             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 //Log.d(TAG, "onChildAdded: "+snapshot.getKey());
                 items currentItem = snapshot.getValue(items.class);
                 itemList.add(currentItem);
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
                 Log.e(TAG, "onCancelled: ", error.toException());
             }
         });
         mAdapter = new itemAdapter(itemList,this);
         contentList.swapAdapter(mAdapter,true);
         return v;
    }

    @Override
    public void onItemClick(int position) {
        //Log.d(TAG, "onCreateView: size"+itemList.size());
        viewModel.setItem(itemList.get(position));
        viewModel.setParent("ItemList");
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.detailFragmentContainer, new ItemFullContentFragment(), null)
                .commit();
    }
}