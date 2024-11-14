package fpt.edu.vn.asfsg1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.adapter.ImageSliderAdapter;
import fpt.edu.vn.asfsg1.databinding.ActivityAuctionDetailBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDescribeBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDetailBinding;
import fpt.edu.vn.asfsg1.helper.AuctionRegisterRepository;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.helper.BidRepository;
import fpt.edu.vn.asfsg1.helper.WalletRepository;
import fpt.edu.vn.asfsg1.models.response.AuctionDetailResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionRegisterResponse;
import fpt.edu.vn.asfsg1.models.response.CheckStatusAuctionRegisterResponse;
import fpt.edu.vn.asfsg1.models.response.WalletResponse;
import fpt.edu.vn.asfsg1.services.AuctionRegisterService;
import fpt.edu.vn.asfsg1.services.AuthService;
import fpt.edu.vn.asfsg1.services.BidService;
import fpt.edu.vn.asfsg1.services.WalletService;
import fpt.edu.vn.asfsg1.tokenManager.TokenManager;
import fpt.edu.vn.asfsg1.ui.auction.DescribeFragment;
import fpt.edu.vn.asfsg1.ui.auction.DetailFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionDetailActivity extends AppCompatActivity {
    ActivityAuctionDetailBinding binding;
    AuctionDetailResponse auctionDetail;
    private BidService bidService;
    private AuctionRegisterService auctionRegisterService;
    private WalletService walletService;
    double depositAmount;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuctionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bidService = BidRepository.getBidService();
        auctionRegisterService = AuctionRegisterRepository.getAuctionRegisterService();
        walletService = WalletRepository.getWalletService();



        auctionDetail = (AuctionDetailResponse) getIntent().getSerializableExtra("auctionDetail");

        setupViewpager();
        initBotNav();
        initTopNav();
        System.out.println(auctionDetail.getData().getAuction().getAuction_id());

        if (auctionDetail != null) {
            // Populate UI with auction details
            initPic(auctionDetail.getData().getImages());

            binding.titleTxt.setText(auctionDetail.getData().getItemName());
            binding.priceTxt.setText(String.valueOf(auctionDetail.getData().getAuction().getStart_price()) + " VNĐ");
            binding.numberRecent.setText("chưa có");

            String endDate = auctionDetail.getData().getAuction().getEndDate();
            String endTime = auctionDetail.getData().getAuction().getEnd_time();
            startCountdown(endDate, endTime);
        }

    }

    private void initBotNav() {
        token = getToken();
        if (token != null) {
            auctionRegisterService.checkRegistration("Bearer " + token, auctionDetail.getData().getAuction().getAuction_id()).enqueue(new Callback<CheckStatusAuctionRegisterResponse>() {
                @Override
                public void onResponse(Call<CheckStatusAuctionRegisterResponse> call, Response<CheckStatusAuctionRegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CheckStatusAuctionRegisterResponse check = response.body();
                        runOnUiThread(() -> {
                            binding.btnAuction.setText("Đặt giá thầu");
                            binding.btnAuction.setOnClickListener(v -> showBidPopup());
                        });

                    } else {
                        binding.btnAuction.setOnClickListener(v -> showAuctionPopup());
                        System.out.println(response);
                    }
                }

                @Override
                public void onFailure(Call<CheckStatusAuctionRegisterResponse> call, Throwable t) {
                    binding.btnAuction.setOnClickListener(v -> showAuctionPopup());
                    System.out.println(call);
                }
            });
        }

        binding.btnListAuctioner.setOnClickListener(v -> {

        });
    }

    private void initTopNav() {
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

    private void showBidPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_bid, null);
        builder.setView(dialogView);

        builder.setTitle("Đặt Giá Thầu")
                .setPositiveButton("Đấu giá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Hủy bỏ", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
        negativeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
    }


    private void showAuctionPopup() {
        token = getToken();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_auction, null);
        builder.setView(dialogView);

        CheckBox checkBox = dialogView.findViewById(R.id.checkbox_agree);

        builder.setTitle("Tham Gia Đấu Giá")
                .setPositiveButton("Đăng Ký", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkBox.isChecked()) {
                            if (auctionDetail.getData().getAuction().getStatus().equals("OPEN")){
                                walletService.getBalance(token).enqueue(new Callback<WalletResponse>() {
                                    @Override
                                    public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            WalletResponse wallet = response.body();

                                            depositAmount = 0.1 * auctionDetail.getData().getAuction().getStart_price();
                                            if (!wallet.getData().getStatusWallet().equals("ACTIVE")) {
                                                Toast.makeText(AuctionDetailActivity.this, "Ví chưa được tạo", Toast.LENGTH_SHORT).show();
                                            } else if (wallet.getData().getBalance() <= depositAmount) {
                                                Toast.makeText(AuctionDetailActivity.this, "Ví không đủ số dư", Toast.LENGTH_SHORT).show();
                                            } else {
                                                auctionRegisterService.createAuctionRegister(auctionDetail.getData().getAuction().getAuction_id())
                                                        .enqueue(new Callback<AuctionRegisterResponse>() {
                                                            @Override
                                                            public void onResponse(Call<AuctionRegisterResponse> call, Response<AuctionRegisterResponse> response) {
                                                                if (response.isSuccessful()) {
                                                                    Toast.makeText(AuctionDetailActivity.this, "Bạn đã đăng ký tham gia đấu giá!", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(AuctionDetailActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<AuctionRegisterResponse> call, Throwable t) {
                                                                Toast.makeText(AuctionDetailActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WalletResponse> call, Throwable t) {
                                        Toast.makeText(AuctionDetailActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(AuctionDetailActivity.this, "Phiên đấu giá đã kết thúc", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                })
                .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
        negativeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
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
                        binding.tvTime.setText("Đã kết thúc:");
                        binding.tvTime.setTextColor(getResources().getColor(R.color.alert, null));
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

    private void setupViewpager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DescribeFragment describeFragment = new DescribeFragment();
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();

        bundle.putString("description", auctionDetail.getData().getItemDescription());

        bundle1.putString("percent", String.valueOf(auctionDetail.getData().getItemSpecific().getPercent()));
        bundle1.putString("type", String.valueOf(auctionDetail.getData().getItemSpecific().getType()));
        bundle1.putString("color", String.valueOf(auctionDetail.getData().getItemSpecific().getColor()));
        bundle1.putString("weight", String.valueOf(auctionDetail.getData().getItemSpecific().getWeight()));
        bundle1.putString("dimension", String.valueOf(auctionDetail.getData().getItemSpecific().getDimension()));
        bundle1.putString("original", String.valueOf(auctionDetail.getData().getItemSpecific().getOriginal()));
        bundle1.putString("manufacture_date", String.valueOf(auctionDetail.getData().getItemSpecific().getManufactureDate()));
        bundle1.putString("material", String.valueOf(auctionDetail.getData().getItemSpecific().getMaterial()));

        describeFragment.setArguments(bundle);
        detailFragment.setArguments(bundle1);

        adapter.addFrag(describeFragment, "Mô tả");
        adapter.addFrag(detailFragment, "Thông tin sản phẩm");

        binding.viewpaper.setAdapter(adapter);
        binding.tablelayout.setupWithViewPager(binding.viewpaper);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

    private String getToken() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        return preferences.getString("token", null); // Returns null if token is not found
    }

}