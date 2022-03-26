package fragments.ORDERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;

import java.util.ArrayList;

import Order.order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private ArrayList<order> orders;
    private orderClickListener listener;
    public OrderAdapter(ArrayList<order> list,  orderClickListener orderListener){
        this.orders = list;
        this.listener = orderListener;
    }



    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new orderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        order i = orders.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public orderViewHolder(@NonNull View itemView, orderClickListener listener) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public interface orderClickListener{
        public void OnOrderClick(int position);
    }
}
