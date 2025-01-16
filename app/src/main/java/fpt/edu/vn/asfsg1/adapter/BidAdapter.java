package fpt.edu.vn.asfsg1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpt.edu.vn.asfsg1.databinding.ViewholderBidBinding;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.Viewholder>{
    ArrayList<String> items;
    Context context;
    int selectedPosition = -1;
    int lastSelectedPosition = -1;
    private EditText bidAmount;

    public BidAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BidAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderBidBinding binding = ViewholderBidBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BidAdapter.Viewholder holder, int position) {
        holder.binding.tvBid.setText(items.get(position));

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);

                bidAmount.setText(items.get(selectedPosition));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderBidBinding binding;
        public Viewholder(@NonNull ViewholderBidBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
