package fpt.edu.vn.asfsg1.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.activity.AuctionDetailActivity;
import fpt.edu.vn.asfsg1.databinding.ViewholderAuctionListBinding;
import fpt.edu.vn.asfsg1.helper.AuctionRegisterRepository;
import fpt.edu.vn.asfsg1.models.response.AuctionDetailResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionListResponse;
import fpt.edu.vn.asfsg1.models.response.CheckStatusAuctionRegisterResponse;
import fpt.edu.vn.asfsg1.services.AuctionRegisterService;
import fpt.edu.vn.asfsg1.services.ItemService;
import fpt.edu.vn.asfsg1.tokenManager.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.Viewholder>{
    private List<AuctionListResponse.AuctionItem> auctionList; // Danh sách các mục
    private ItemService itemService;
    Context context;
    String token;
    private AuctionRegisterService auctionRegisterService;

    public AuctionAdapter(List<AuctionListResponse.AuctionItem> auctionList, ItemService itemService) { // Nhận vào danh sách
        this.auctionList = auctionList;
        this.itemService = itemService;
    }

    @NonNull
    @Override
    public AuctionAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderAuctionListBinding binding = ViewholderAuctionListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionAdapter.Viewholder holder, int position) {
        auctionRegisterService = AuctionRegisterRepository.getAuctionRegisterService();


        AuctionListResponse.AuctionItem auction = auctionList.get(position);
        holder.binding.tvSubCate.setText(auction.getScId().getSub_category());
        holder.binding.title.setText(auction.getItemName());

        token = getToken();


        String endDate = auction.getAuction().getEndDate();
        String endTime = auction.getAuction().getEnd_time();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        try {
            // Combine endDate and endTime, then parse it
            String dateTime = endDate + "T" + endTime;
            long endTimeMillis = dateFormat.parse(dateTime).getTime();

            long currentTimeMillis = System.currentTimeMillis();
            long countdownMillis = endTimeMillis - currentTimeMillis;

            if (countdownMillis > 0) {
                new CountDownTimer(countdownMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                        long hours = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                        long minutes = (millisUntilFinished / (1000 * 60)) % 60;
                        long seconds = (millisUntilFinished / 1000) % 60;

                        String timeLeftFormatted = String.format(Locale.getDefault(), "%d Ngày, %02d:%02d:%02d", days, hours, minutes, seconds);
                        holder.binding.timeCountdown.setText(timeLeftFormatted);
                    }

                    @Override
                    public void onFinish() {
                        holder.binding.timeCountdown.setVisibility(View.GONE);
                        holder.binding.timeStatus.setText("Đã kết thúc");
                    }
                }.start();
            } else {
                holder.binding.timeCountdown.setVisibility(View.GONE);
                holder.binding.timeStatus.setText("Đã kết thúc");
                holder.binding.timeStatus.setTextColor(context.getResources().getColor(R.color.alert, null));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.binding.timeCountdown.setText("Không xác định");
        }

        if (token != null) {
            auctionRegisterService.checkRegistration("Bearer " + token, auction.getAuction().getAuction_id()).enqueue(new Callback<CheckStatusAuctionRegisterResponse>() {
                @Override
                public void onResponse(Call<CheckStatusAuctionRegisterResponse> call, Response<CheckStatusAuctionRegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CheckStatusAuctionRegisterResponse check = response.body();
                        holder.binding.btnDeposit.setText("Đặt giá thầu");
                        holder.binding.btnDeposit.setOnClickListener(v -> {
                            int currentPosition = auction.getItemId();

                            itemService.getAuctionDetail(String.valueOf(currentPosition)).enqueue(new Callback<AuctionDetailResponse>() {
                                @Override
                                public void onResponse(Call<AuctionDetailResponse> call1, Response<AuctionDetailResponse> response1) {
                                    if (response1.isSuccessful() && response1.body() != null) {
                                        // Navigate to AuctionDetailActivity with auction details
                                        AuctionDetailResponse auctionDetail = response1.body();
                                        Intent intent = new Intent(context, AuctionDetailActivity.class);
                                        intent.putExtra("auctionDetail", auctionDetail);
                                        context.startActivity(intent);
                                    } else {
                                        System.out.println(response1);
                                    }
                                }

                                @Override
                                public void onFailure(Call<AuctionDetailResponse> call1, Throwable t) {
                                    // Handle error (e.g., show a message to the user)
                                }
                            });
                        });
                    } else {
                        holder.binding.btnDeposit.setOnClickListener(v -> {
                            int currentPosition = auction.getItemId();

                            itemService.getAuctionDetail(String.valueOf(currentPosition)).enqueue(new Callback<AuctionDetailResponse>() {
                                @Override
                                public void onResponse(Call<AuctionDetailResponse> call12, Response<AuctionDetailResponse> response12) {
                                    if (response12.isSuccessful() && response12.body() != null) {
                                        // Navigate to AuctionDetailActivity with auction details
                                        AuctionDetailResponse auctionDetail = response12.body();
                                        Intent intent = new Intent(context, AuctionDetailActivity.class);
                                        intent.putExtra("auctionDetail", auctionDetail);
                                        context.startActivity(intent);
                                    } else {
                                        System.out.println(response12);
                                    }
                                }

                                @Override
                                public void onFailure(Call<AuctionDetailResponse> call12, Throwable t) {
                                    // Handle error (e.g., show a message to the user)
                                }
                            });
                        });
                    }
                }

                @Override
                public void onFailure(Call<CheckStatusAuctionRegisterResponse> call, Throwable t) {
                    System.out.println(call);
                }
            });
        }



        holder.itemView.setOnClickListener(v -> {
            int currentPosition = auction.getItemId();

            itemService.getAuctionDetail(String.valueOf(currentPosition)).enqueue(new Callback<AuctionDetailResponse>() {
                @Override
                public void onResponse(Call<AuctionDetailResponse> call, Response<AuctionDetailResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Navigate to AuctionDetailActivity with auction details
                        AuctionDetailResponse auctionDetail = response.body();
                        Intent intent = new Intent(context, AuctionDetailActivity.class);
                        intent.putExtra("auctionDetail", auctionDetail);
                        context.startActivity(intent);
                    } else {
                        System.out.println(response);
                    }
                }

                @Override
                public void onFailure(Call<AuctionDetailResponse> call, Throwable t) {
                    Log.e("AuctionAdapter", "Error fetching AuctionDetail", t);
                }
            });

        });


        holder.binding.priceTxt.setText(String.valueOf(auction.getAuction().getStart_price())); // Thay thế bằng giá thực tế từ item

        // Thiết lập hình ảnh
        Glide.with(holder.binding.pic.getContext())
                .load(auction.getThumbnail())
                .into(holder.binding.pic);
    }



    @Override
    public int getItemCount() {
        return auctionList != null ? auctionList.size() : 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderAuctionListBinding binding;
        public Viewholder(ViewholderAuctionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private String getToken() {
        SharedPreferences preferences = context.getSharedPreferences("userPrefs", MODE_PRIVATE);
        return preferences.getString("token", null); // Returns null if token is not found
    }
}
