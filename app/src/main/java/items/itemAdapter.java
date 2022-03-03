package items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder> {

    private ArrayList<Items> i;
    private onItemListener mListener;
    public  itemAdapter(ArrayList<Items> i, onItemListener itemListener){
        this.i = i;
        this.mListener = itemListener;
    }


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false),mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
            Items currentItem = i.get(position);

            holder.itemImage.setImageResource(currentItem.getImage());
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
        onItemListener onItemListener;
        public itemViewHolder(@NonNull View itemView, onItemListener i) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_imageView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_small_description);
            this.onItemListener = i;
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
