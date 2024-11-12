package fpt.edu.vn.asfsg1.ui.home;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
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

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.activity.ChatActivity;
import fpt.edu.vn.asfsg1.activity.MainActivity;
import fpt.edu.vn.asfsg1.activity.WishlistActivity;
import fpt.edu.vn.asfsg1.adapter.AuctionAdapter;
import fpt.edu.vn.asfsg1.adapter.CategoryAdapter;
import fpt.edu.vn.asfsg1.adapter.ItemAdapter;
import fpt.edu.vn.asfsg1.adapter.SliderAdapter;
import fpt.edu.vn.asfsg1.databinding.FragmentHomeBinding;
import fpt.edu.vn.asfsg1.domains.SliderItemsDomain;
import fpt.edu.vn.asfsg1.helper.AuctionRepository;
import fpt.edu.vn.asfsg1.helper.ItemRepository;
import fpt.edu.vn.asfsg1.helper.MainCategoryRepository;
import fpt.edu.vn.asfsg1.models.response.AuctionListResponse;
import fpt.edu.vn.asfsg1.models.response.ItemResponse;
import fpt.edu.vn.asfsg1.models.response.MainCategoryResponse;
import fpt.edu.vn.asfsg1.services.AuctionService;
import fpt.edu.vn.asfsg1.services.ItemService;
import fpt.edu.vn.asfsg1.services.MainCategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MainCategoryService mainCategoryService;
    private ItemService itemService;
    private ArrayList<MainCategoryResponse.MainCategoryData> categoryItems;
    private AuctionService auctionService;

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
        itemService = ItemRepository.getItemService();
        auctionService = AuctionRepository.getAuctionService();
        
        initTopNav();
        initBanner();
        initMainCategory();
        initPopular();

        return root;
    }

    private void initTopNav() {
        EditText etSearch = binding.etSearch;
        ImageView wishlist = binding.wishlist;
        View btnChat = binding.btnChat;
        TextView tvNotify = binding.tvNotify;

        wishlist.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WishlistActivity.class); //Chuyển từ Fragment Bottom Nav qua Wishlist
            startActivity(intent);
        });

        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class); //Chuyển từ Fragment Bottom Nav qua Chat
            startActivity(intent);
        });
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
        ArrayList<MainCategoryResponse.MainCategoryData> categoryItems = new ArrayList<>(); // Khởi tạo danh sách các danh mục

        mainCategoryService.getMainCategories().enqueue(new Callback<MainCategoryResponse>() {
            @Override
            public void onResponse(Call<MainCategoryResponse> call, Response<MainCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MainCategoryResponse.MainCategoryData> data = response.body().getData();
                    if (data != null) {
                        categoryItems.addAll(data);
                        if(!categoryItems.isEmpty()){
                            binding.recyclerViewOfficial.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            binding.recyclerViewOfficial.setAdapter(new CategoryAdapter(categoryItems));
                        }
                        binding.progressBarOfficial.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "No categories found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainCategoryResponse> call, Throwable t) {
                binding.progressBarOfficial.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error fetching categories", t);
            }
        });
    }

    private void initPopular() {
        binding.progressBarPopular.setVisibility(View.VISIBLE);
        ArrayList<AuctionListResponse.AuctionItem> popularItems = new ArrayList<>();
        itemService.getAuctionList().enqueue(new Callback<AuctionListResponse>() {
            @Override
            public void onResponse(Call<AuctionListResponse> call, Response<AuctionListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extracting data from the AuctionListResponse
                    AuctionListResponse.AuctionListData auctionListData = response.body().getData();
                    if (auctionListData != null) {
                        List<AuctionListResponse.AuctionItem> data = auctionListData.getData(); // Correct method to get the list of AuctionItem
                        if (data != null && !data.isEmpty()) {
                            popularItems.addAll(data);
                            binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            binding.recyclerViewPopular.setAdapter(new AuctionAdapter(popularItems, itemService));
                        } else {
                            Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    }
                    binding.progressBarPopular.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Không load được sản phẩm", Toast.LENGTH_SHORT).show();
                    binding.progressBarPopular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AuctionListResponse> call, Throwable t) {
                binding.progressBarPopular.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error fetching item", t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the binding reference to avoid memory leaks
        binding = null;
    }
}
