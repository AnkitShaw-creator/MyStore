package fragments.ORDERS;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.MessageFormat;
import java.util.ArrayList;

import Order.order;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private static final String TAG = "OrderAdapter";
    private ArrayList<order> orders;
    private orderClickListener listener;

    public OrderAdapter(ArrayList<order> orders,  orderClickListener orderListener){
        this.orders = orders;
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
        String Rate = i.getRate();
        int rate = Integer.parseInt(Rate.substring(0, Rate.indexOf("/")));
        int quantity = Integer.parseInt(i.getQuantity());
        holder.orderQuantity.setText(String.valueOf(quantity));
        holder.orderRate.setText(MessageFormat.format("Rate: {0}", Rate));
        holder.orderImage.setImageResource(R.drawable.ic_app_logo);
        holder.orderItemTotal.setText(MessageFormat.format("Total: {0}", String.valueOf(rate * quantity)));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView orderImage;
        private MaterialTextView orderName;
        private EditText orderQuantity;
        private TextView orderRate, orderItemTotal;
        private orderClickListener listener;
        public orderViewHolder(@NonNull View itemView, orderClickListener listener) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.order_item_image);
            orderName = itemView.findViewById(R.id.order_item_name);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderRate = itemView.findViewById(R.id.order_rate);
            orderItemTotal = itemView.findViewById(R.id.order_item_total);
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
