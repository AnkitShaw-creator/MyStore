package Items;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder> {

    private static final String LOG_TAG = "itemAdapter";
    private ArrayList<items> i;
    private onItemListener mListener;
    public  itemAdapter(ArrayList<items> i, onItemListener itemListener){
        this.i = i;
        this.mListener = itemListener;
    }


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false),mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        items currentItem = i.get(position);
        //Log.i(LOG_TAG, "onBindViewHolder: "+currentItem.getQuantity());
        holder.itemImage.setImageResource(R.drawable.ic_icon_order);
        holder.itemName.setText(currentItem.getTitle());
        holder.itemDescription.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return i.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDescription;
        private MaterialButton detailsButton;
        onItemListener onItemListener;
        public itemViewHolder(@NonNull View itemView, onItemListener i) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_imageView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_small_description);
            detailsButton = itemView.findViewById(R.id.open_full_content_button);
            this.onItemListener = i;
            detailsButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface onItemListener{
        public void onItemClick(int position);
    }
}
