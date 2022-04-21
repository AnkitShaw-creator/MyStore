package fragments.HOME;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

import Items.items;
import fragments.SETTINGS.WishlistFragment;


public class ItemFullContentFragment extends Fragment {

    private FloatingActionButton backButton;
    private ImageView itemImage;
    private MaterialButton mWishList, mBuy;
    private TextView mItemName,mDescription,mPrice, mQuantity; // long description
    private MaterialTextView error_message;

    private itemViewModel viewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String itemTitle;

    private static final String TAG ="ItemFullContentFragment";
    private String p;
    public ItemFullContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_full_content, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);
        p = viewModel.getParent();

        itemImage= v.findViewById(R.id.item_imageView);
        mItemName = v.findViewById(R.id.detail_item_name);
        mWishList = v.findViewById(R.id.wishListButton);
        mBuy = v.findViewById(R.id.cartButton);
        mDescription = v.findViewById(R.id.item_long_description_textView);
        mPrice = v.findViewById(R.id.item_price);
        mQuantity = v.findViewById(R.id.item_quantity);
        backButton = v.findViewById(R.id.back_fab);
        error_message = v.findViewById(R.id.item_error_message_tv);
        error_message.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
       if(mAuth.getCurrentUser() != null){
           user = mAuth.getCurrentUser();
           database = FirebaseDatabase.getInstance(DATABASE_URL);
           ref = database.getReference("users");
           set_UI();
       }
        return  v;
    }

    private void set_UI() {
        // TODO need to create a hash function for items and orders
        viewModel.getItem().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                mItemName.setText(item.getTitle());
                mPrice.setText(item.getPrice());
                mQuantity.setText(item.getQuantity());
                mDescription.setText(item.getDescription());
            }
        });

        mWishList.setText(getButtonText());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.equals("ItemList")) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detailFragmentContainer, new ItemListFragment(), null)
                            .commit();
                }
                if(p.equals("Wishlist")){
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new WishlistFragment(), null)
                            .commit();
                }

            }
        });
        mWishList.setOnClickListener(view ->{
            if(p.equals("ItemList")) {
                add_to_wishlist();
            }
            if(p.equals("Wishlist")){
               remove_from_wishlist();
            }
        });
        mBuy.setOnClickListener(view ->{
            String time = new Date().toString();
            HashMap<String, String> pending_order = new HashMap<>();
            pending_order.put("name", mItemName.getText().toString());
            pending_order.put("quantity", "1");
            pending_order.put("rate", mPrice.getText().toString());
            String orderKey = String.valueOf(pending_order.hashCode());
            pending_order.put("order_id", orderKey);
            pending_order.put("time", time );
            Log.d(TAG, "set_UI: key"+orderKey);
            ref.child(user.getUid()).child("pending_orders")
                    .child(orderKey).setValue(pending_order)
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
        });

    }

    private String getButtonText() {
        if(p.equals("ItemList")) {
            return "Add to wishlist";
        }
        if(p.equals("Wishlist")){
            return "Remove from wishlist";
        }
        return null;
    }

    private void add_to_wishlist(){
        items i = viewModel.getItem().getValue();
        ref.child(user.getUid()).child("wishlist")
                .child(String.valueOf(i.getId())).setValue(i).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: "+i.getId());
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Item added to wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, "onComplete: ", task.getException());
                }
            }
        });
    }
    private void remove_from_wishlist(){
        items i = viewModel.getItem().getValue();
        ref.child(user.getUid()).child("wishlist")
                .child(String.valueOf(i.getId())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: "+i.getId());
                    viewModel.setItem(null);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, new WishlistFragment(), null)
                            .commit();
                }
                else{
                    Log.e(TAG, "onComplete: ", task.getException() );
                }
            }
        });
    }
}