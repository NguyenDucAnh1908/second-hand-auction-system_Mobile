package fpt.edu.vn.asfsg1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpt.edu.vn.asfsg1.databinding.ViewholderAuctionListBinding;
import fpt.edu.vn.asfsg1.models.response.ItemResponse;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Viewholder>{
    private List<ItemResponse.Item> itemList; // Danh sách các mục

    public ItemAdapter(List<ItemResponse.Item> itemList) { // Nhận vào danh sách
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderAuctionListBinding binding = ViewholderAuctionListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ItemResponse.Item item = itemList.get(position);

        // Thiết lập các thuộc tính cho các TextView
        holder.binding.tvSubCate.setText(item.getScId().getSub_category());
        holder.binding.title.setText(item.getItemName());

        // Giả sử bạn có giá khởi điểm và đánh giá từ các thuộc tính khác
        holder.binding.priceTxt.setText("Chưa có"); // Thay thế bằng giá thực tế từ item

        // Thiết lập hình ảnh
        Glide.with(holder.binding.pic.getContext())
                .load(item.getThumbnail())
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderAuctionListBinding binding;
        public Viewholder(ViewholderAuctionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
