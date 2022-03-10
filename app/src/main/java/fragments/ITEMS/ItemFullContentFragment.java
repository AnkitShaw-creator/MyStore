package fragments.ITEMS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Items.items;


public class ItemFullContentFragment extends Fragment {

    private FloatingActionButton backButton;
    private ImageView itemImage;
    private MaterialButton mWishList, mBuy;
    private TextView mItemName,mDescription,mPrice, mQuantity; // long description
    private MaterialTextView error_message;

    private itemViewModel viewModel;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    HomeFragment parent;
    private String itemTitle;

    public ItemFullContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_full_content, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(itemViewModel.class);

        itemImage= v.findViewById(R.id.item_imageView);
        mItemName = v.findViewById(R.id.detail_item_name);
        mWishList = v.findViewById(R.id.wishListButton);
        mBuy = v.findViewById(R.id.buyButton);
        mDescription = v.findViewById(R.id.item_long_description_textView);
        mPrice = v.findViewById(R.id.item_price);
        mQuantity = v.findViewById(R.id.item_quantity);
        backButton = v.findViewById(R.id.back_fab);
        error_message = v.findViewById(R.id.item_error_message_tv);
        error_message.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
       if(mAuth.getCurrentUser() != null){
           set_UI();
       }
       parent = new HomeFragment();


        return  v;
    }

    private void set_UI() {

        viewModel.getItem().observe(getViewLifecycleOwner(), item -> {
            mItemName.setText(item.getTitle());
            mPrice.setText(item.getPrice());
            mQuantity.setText(item.getQuantity());
            mDescription.setText(item.getDescription());
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detailFragmentContainer, new ItemListFragment(),null)
                        .commit();

            }
        });
    }
}