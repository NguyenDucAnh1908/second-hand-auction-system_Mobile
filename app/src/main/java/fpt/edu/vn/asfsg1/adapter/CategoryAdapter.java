package fpt.edu.vn.asfsg1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpt.edu.vn.asfsg1.databinding.ViewholderCategoryBinding;
import fpt.edu.vn.asfsg1.models.response.MainCategoryResponse;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    private List<MainCategoryResponse> categoryItems; // Danh sách các danh mục

    public CategoryAdapter(List<MainCategoryResponse> categoryItems) { // Nhận vào danh sách
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCategoryBinding binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
        MainCategoryResponse category = categoryItems.get(position);
        holder.binding.title.setText(category.getCategoryName());

        Glide.with(holder.binding.pic.getContext())
                .load(category.getIconUrl())
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;

        public Viewholder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
