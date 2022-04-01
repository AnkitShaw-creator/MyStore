package fragments.ORDERS;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import Order.order;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private static final String TAG = "OrderAdapter";
    private ArrayList<order> orders;
    private orderClickListener listener;

    public OrderAdapter(ArrayList<order> list,  orderClickListener orderListener){
        this.orders = list;
        this.listener = orderListener;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Here");
        return new orderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        order i = orders.get(position);
        Log.d(TAG, "onBindViewHolder: "+i.getName());
        holder.orderName.setText(i.getName());
        holder.orderQuantity.setText(i.getQuantity());
        holder.orderRate.setText(i.getRate());
        holder.orderImage.setImageResource(R.drawable.ic_app_logo);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView orderImage;
        private MaterialTextView orderName;
        private EditText orderQuantity, orderRate;
        private orderClickListener listener;
        public orderViewHolder(@NonNull View itemView, orderClickListener listener) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.order_item_image);
            orderName = itemView.findViewById(R.id.order_item_name);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderRate = itemView.findViewById(R.id.order_rate);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {

        }
    }
    public interface orderClickListener{
        public void OnOrderClick(int position);
    }
}
