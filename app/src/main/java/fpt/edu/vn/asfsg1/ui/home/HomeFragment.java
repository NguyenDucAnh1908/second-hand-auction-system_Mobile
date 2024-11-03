package fpt.edu.vn.asfsg1.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.vn.asfsg1.adapter.CategoryAdapter;
import fpt.edu.vn.asfsg1.adapter.SliderAdapter;
import fpt.edu.vn.asfsg1.databinding.FragmentHomeBinding;
import fpt.edu.vn.asfsg1.domains.SliderItemsDomain;
import fpt.edu.vn.asfsg1.helper.MainCategoryRepository;
import fpt.edu.vn.asfsg1.models.response.MainCategoryResponse;
import fpt.edu.vn.asfsg1.services.MainCategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MainCategoryService mainCategoryService;
    private ArrayList<MainCategoryResponse> categoryItems;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mainCategoryService = MainCategoryRepository.getMainCategoryService();
        EditText etSearch = binding.etSearch;
        ImageView wishlist = binding.wishlist;
        View btnCart = binding.btnCart;
        TextView tvNotify = binding.tvNotify;

        wishlist.setOnClickListener(v -> {
            // Handle wishlist click
        });

        // Example for cart button click event
        btnCart.setOnClickListener(v -> {
            // Handle cart button click
        });

        initMainCategory();
        initBanner();

        return root;
    }

    private void initBanner() {
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://asfsg-538c3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItemsDomain> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItemsDomain.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banners(ArrayList<SliderItemsDomain> items) {
        binding.viewpagerSlider.setAdapter(new SliderAdapter(items, binding.viewpagerSlider));
        binding.viewpagerSlider.setClipToPadding(false);
        binding.viewpagerSlider.setClipChildren(false);
        binding.viewpagerSlider.setOffscreenPageLimit(3);
        binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer);
    }


    private void initMainCategory() {
        binding.progressBarOfficial.setVisibility(View.VISIBLE);
        categoryItems = new ArrayList<>(); // Khởi tạo danh sách các danh mục

        mainCategoryService.getMainCategories().enqueue(new Callback<List<MainCategoryResponse>>() {
            @Override
            public void onResponse(Call<List<MainCategoryResponse>> call, Response<List<MainCategoryResponse>> response) {
//                binding.progressBarOfficial.setVisibility(View.GONE); // Ẩn progress bar ngay khi có phản hồi
//                if (response.isSuccessful() && response.body() != null) {
//                    categoryItems.addAll(response.body()); // Thêm tất cả danh mục vào danh sách
//                    setupRecyclerView(categoryItems); // Gọi phương thức setupRecyclerView với danh sách
//                } else {
//                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<List<MainCategoryResponse>> call, Throwable t) {
//                binding.progressBarOfficial.setVisibility(View.GONE);
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("HomeFragment", "Error fetching categories", t);
            }
        });
    }

//    private void setupRecyclerView(ArrayList<MainCategoryResponse> categoryItems) {
//        CategoryAdapter adapter = new CategoryAdapter(categoryItems); // Truyền vào danh sách
//        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
//        binding.recyclerViewPopular.setAdapter(adapter);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the binding reference to avoid memory leaks
        binding = null;
    }
}
