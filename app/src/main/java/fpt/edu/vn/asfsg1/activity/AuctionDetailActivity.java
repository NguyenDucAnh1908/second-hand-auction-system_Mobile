package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpt.edu.vn.asfsg1.adapter.ImageSliderAdapter;
import fpt.edu.vn.asfsg1.databinding.ActivityAuctionDetailBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDescribeBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDetailBinding;
import fpt.edu.vn.asfsg1.models.response.AuctionDetailResponse;

public class AuctionDetailActivity extends AppCompatActivity {
    ActivityAuctionDetailBinding binding;
    private AuctionDetailActivity object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuctionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AuctionDetailResponse auctionDetail = (AuctionDetailResponse) getIntent().getSerializableExtra("auctionDetail");

        if (auctionDetail != null) {
            // Populate UI with auction details
            initPic(auctionDetail.getData().getImages());

            binding.subcategory.setText(auctionDetail.getData().getScId().getSub_category());
            binding.titleTxt.setText(auctionDetail.getData().getItemName());
            binding.priceTxt.setText(String.valueOf(auctionDetail.getData().getAuction().getStart_price()) + " VNĐ");
            binding.numberRecent.setText("chưa có");

            String endDate = auctionDetail.getData().getAuction().getEndDate();
            String endTime = auctionDetail.getData().getAuction().getEnd_time();
            startCountdown(endDate, endTime);
        }

        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initPic(List<AuctionDetailResponse.AuctionImage> images) {
        if (images != null && !images.isEmpty()) {
            ImageSliderAdapter adapter = new ImageSliderAdapter(images);
            binding.viewpageSlider.setAdapter(adapter);

            // Add Page Transformer for smooth transition
            CompositePageTransformer transformer = new CompositePageTransformer();
            transformer.addTransformer(new MarginPageTransformer(40));
            transformer.addTransformer((page, position) -> {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            });
            binding.viewpageSlider.setPageTransformer(transformer);
        }
    }

    private void startCountdown(String endDate, String endTime) {
        // Define the date format according to the expected format in `endDate` and `endTime`
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

                        String timeLeftFormatted = String.format(Locale.getDefault(), "%d Ngày %02d giờ %02d phút %02d giây", days, hours, minutes, seconds);
                        binding.numberTime.setText(timeLeftFormatted);
                    }

                    @Override
                    public void onFinish() {
                        binding.numberTime.setText("Đã hết thời gian");
                    }
                }.start();
            } else {
                binding.numberTime.setText("Đã hết thời gian");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            binding.numberTime.setText("Không xác định");
        }
    }

}