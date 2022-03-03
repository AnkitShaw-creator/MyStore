package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ItemFullContentFragment extends Fragment {

    private FloatingActionButton backButton;
    private ImageView itemImage;
    private MaterialButton mWishList, mBuy;
    private TextView mDescription; // long description

    public ItemFullContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_full_content, container, false);
        itemImage= v.findViewById(R.id.item_imageView);
        mWishList = v.findViewById(R.id.wishListButton);
        mBuy = v.findViewById(R.id.buyButton);
        mDescription = v.findViewById(R.id.item_long_description_textView);
        backButton = v.findViewById(R.id.back_fab);
        set_UI();
        return  v;
    }

    private void set_UI() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}