package Order;

import static com.example.mystore.ui.MainActivity.DATABASE_URL;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private static final String TAG = "OrderAdapter";
    private ArrayList<order> orders;
    private orderClickListener listener;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    public OrderAdapter(ArrayList<order> orders,  orderClickListener orderListener){
        this.orders = orders;
        this.listener = orderListener;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Adapter created");
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users")
                .child(mAuth.getCurrentUser().getUid()).child("pending_orders");
        return new orderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        order i = orders.get(position);
        String id = i.getOrder_id();
        String name = i.getName();
        String Rate = i.getRate();
        int rate = Integer.parseInt(Rate.substring(0, Rate.indexOf("/")));
        int quantity = Integer.parseInt(i.getQuantity());
        Log.d(TAG, "onBindViewHolder: "+i.getName());
        holder.orderName.setText(i.getName());
        holder.orderQuantity.setText(String.valueOf(quantity));
        holder.orderRate.setText(MessageFormat.format("Rate: {0}", Rate));
        holder.orderImage.setImageResource(R.drawable.ic_app_logo);
        holder.orderItemTotal.setText(MessageFormat.format("Total: {0}", String.valueOf(rate * quantity)));
        //holder.setIsRecyclable(true);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView orderImage;
        private MaterialTextView orderName,orderQuantity;
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
            listener.OnOrderClick(getAdapterPosition());
        }
    }
    public interface orderClickListener{
        public void OnOrderClick(int position);
    }
}
