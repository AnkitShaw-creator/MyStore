package fragments.ORDERS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mystore.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class OrderFragment extends Fragment {

    private ShapeableImageView noOrderIV;
    private MaterialTextView noOrderTV;
    private RecyclerView orderList;

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
        setUI();
        return v;
    }

    private void setUI() {
        orderList.setHasFixedSize(true);
        orderList.setVisibility(View.INVISIBLE);
    }
}