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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.adapter.BidAdapter;
import fpt.edu.vn.asfsg1.adapter.ImageSliderAdapter;
import fpt.edu.vn.asfsg1.databinding.ActivityAuctionDetailBinding;
import fpt.edu.vn.asfsg1.databinding.DialogBidBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDescribeBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentDetailBinding;
import fpt.edu.vn.asfsg1.helper.AuctionRegisterRepository;
import fpt.edu.vn.asfsg1.helper.AuctionRepository;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.helper.BidRepository;
import fpt.edu.vn.asfsg1.helper.WalletRepository;
import fpt.edu.vn.asfsg1.models.Auction;
import fpt.edu.vn.asfsg1.models.response.AuctionDetailResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionRegisterResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionResponse;
import fpt.edu.vn.asfsg1.models.response.BidResponse;
import fpt.edu.vn.asfsg1.models.response.CheckStatusAuctionRegisterResponse;
import fpt.edu.vn.asfsg1.models.response.OneAuctionResponse;
import fpt.edu.vn.asfsg1.models.response.WalletResponse;
import fpt.edu.vn.asfsg1.services.AuctionRegisterService;
import fpt.edu.vn.asfsg1.services.AuctionService;
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
    private AuctionService auctionService;
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
        bidService = BidRepository.getBidService();
        auctionService = AuctionRepository.getAuctionService();



        auctionDetail = (AuctionDetailResponse) getIntent().getSerializableExtra("auctionDetail");

        setupViewpager();
        initBotNav();
        initTopNav();

        if (auctionDetail != null) {
            // Populate UI with auction details
            initPic(auctionDetail.getData().getImages());

            binding.titleTxt.setText(auctionDetail.getData().getItemName());
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(auctionDetail.getData().getAuction().getStart_price());
            binding.priceTxt.setText(formattedPrice + " VNĐ");
//            binding.priceTxt.setText(String.valueOf(auctionDetail.getData().getAuction().getStart_price()) + " VNĐ");
            binding.numberRecent.setText("0 VNĐ");

            String endDate = auctionDetail.getData().getAuction().getEndDate();
            String endTime = auctionDetail.getData().getAuction().getEnd_time();
            startCountdown(endDate, endTime);
        }

    }

    private void initBotNav() {
        token = getToken();
        if (token != null ) {
            // Ở đây chắc chắn getAuction_id() != null
            auctionRegisterService.checkRegistration("Bearer " + token,
                    auctionDetail.getData().getAuction().getAuctionId()
            ).enqueue(new Callback<CheckStatusAuctionRegisterResponse>() {
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
                    }
                }

                @Override
                public void onFailure(Call<CheckStatusAuctionRegisterResponse> call, Throwable t) {
                    binding.btnAuction.setOnClickListener(v -> showAuctionPopup());
                }
            });
        } else {
            // Xử lý trường hợp auctionDetail hoặc AuctionId bị null
            binding.btnAuction.setOnClickListener(v -> {
                Toast.makeText(this, "Không tìm thấy thông tin đấu giá", Toast.LENGTH_SHORT).show();
            });
        }

        binding.btnListAuctioner.setOnClickListener(v -> {
            bidService.getBids(auctionDetail.getData().getAuction().getAuctionId()).enqueue(new Callback<BidResponse>() {
                @Override
                public void onResponse(Call<BidResponse> call, Response<BidResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        BidResponse bidResponse = response.body();
                    }
                }

                @Override
                public void onFailure(Call<BidResponse> call, Throwable t) {

                }
            });
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
        final OneAuctionResponse[] auction = new OneAuctionResponse[1];

        RecyclerView suggestBid = dialogView.findViewById(R.id.recyclerBid);

        ArrayList<String> list = new ArrayList<>();


        auctionService.getAuctions().enqueue(new Callback<OneAuctionResponse>() {
            @Override
            public void onResponse(Call<OneAuctionResponse> call, Response<OneAuctionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    auction[0] = response.body();


                }
            }

            @Override
            public void onFailure(Call<OneAuctionResponse> call, Throwable t) {

            }
        });

        list.add(String.valueOf(auction[0].getData().getStart_price() + auction[0].getData().getPrice_step()));
        list.add(String.valueOf(auction[0].getData().getStart_price() + auction[0].getData().getPrice_step() * 2));
        list.add(String.valueOf(auction[0].getData().getStart_price() + auction[0].getData().getPrice_step() * 3));


        BidAdapter bidAdapter = new BidAdapter(list);
        suggestBid.setAdapter(bidAdapter);
        suggestBid.setLayoutManager(new LinearLayoutManager(dialogView.getContext(), LinearLayoutManager.HORIZONTAL, false));



        builder.setTitle("Đặt Giá Thầu")
                .setPositiveButton("Đấu giá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        ArrayList<String> list = new ArrayList<>();
//                        list.add("200gr");
//
//
//
//                        suggestBid.setAdapter(new BidAdapter(list));
//                        suggestBid.setLayoutManager(new LinearLayoutManager(suggestBid.getContext(), LinearLayoutManager.HORIZONTAL, false));

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

                                            double startPrice = auctionDetail.getData().getAuction().getStart_price();
                                            depositAmount = 0.1 * startPrice;
                                            if (!wallet.getData().getStatusWallet().equals("ACTIVE")) {
                                                Toast.makeText(AuctionDetailActivity.this, "Ví chưa được tạo", Toast.LENGTH_SHORT).show();
                                            } else if (wallet.getData().getBalance() <= depositAmount) {
                                                Toast.makeText(AuctionDetailActivity.this, "Ví không đủ số dư", Toast.LENGTH_SHORT).show();
                                            } else {
                                                auctionRegisterService.createAuctionRegister(auctionDetail.getData().getAuction().getAuctionId())
                                                        .enqueue(new Callback<AuctionRegisterResponse>() {
                                                            @Override
                                                            public void onResponse(Call<AuctionRegisterResponse> call, Response<AuctionRegisterResponse> response) {
                                                                if (response.isSuccessful()) {
                                                                    AuctionRegisterResponse registerResponse = response.body();
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

        // Fragment mô tả
        DescribeFragment describeFragment = new DescribeFragment();
        DetailFragment detailFragment = new DetailFragment();

        // Bundle cho fragment Mô tả
        Bundle bundleDescribe = new Bundle();
        bundleDescribe.putString("description", auctionDetail.getData().getItemDescription() != null
                ? auctionDetail.getData().getItemDescription()
                : "Không có mô tả.");
        describeFragment.setArguments(bundleDescribe);

        // Bundle cho fragment Thông tin chi tiết
        AuctionDetailResponse.ItemSpecificResponse itemSpec = auctionDetail.getData().getItemSpecific();
        Bundle bundleDetail = new Bundle();

        bundleDetail.putString("cpu", itemSpec.getCpu() != null ? itemSpec.getCpu() : "N/A");
        bundleDetail.putString("ram", itemSpec.getRam() != null ? itemSpec.getRam() : "N/A");
        bundleDetail.putString("screen_size", itemSpec.getScreenSize() != null ? itemSpec.getScreenSize() : "N/A");
        bundleDetail.putString("camera_specs", itemSpec.getCameraSpecs() != null ? itemSpec.getCameraSpecs() : "N/A");
        bundleDetail.putString("connectivity", itemSpec.getConnectivity() != null ? itemSpec.getConnectivity() : "N/A");
        bundleDetail.putString("sensors", itemSpec.getSensors() != null ? itemSpec.getSensors() : "N/A");
        bundleDetail.putString("sim", itemSpec.getSim() != null ? itemSpec.getSim() : "N/A");
        bundleDetail.putString("sim_slots", itemSpec.getSimSlots() != null ? String.valueOf(itemSpec.getSimSlots()) : "N/A");
        bundleDetail.putString("os", itemSpec.getOs() != null ? itemSpec.getOs() : "N/A");
        bundleDetail.putString("os_family", itemSpec.getOsFamily() != null ? itemSpec.getOsFamily() : "N/A");
        bundleDetail.putString("bluetooth", itemSpec.getBluetooth() != null ? itemSpec.getBluetooth() : "N/A");
        bundleDetail.putString("usb", itemSpec.getUsb() != null ? itemSpec.getUsb() : "N/A");
        bundleDetail.putString("wlan", itemSpec.getWlan() != null ? itemSpec.getWlan() : "N/A");
        bundleDetail.putString("speed", itemSpec.getSpeed() != null ? itemSpec.getSpeed() : "N/A");
        bundleDetail.putString("network_technology", itemSpec.getNetworkTechnology() != null ? itemSpec.getNetworkTechnology() : "N/A");

        // Truyền các thông tin bổ sung
        bundleDetail.putString("battery_health", auctionDetail.getData().getBatteryHealth() != null
                ? String.valueOf(auctionDetail.getData().getBatteryHealth())
                : "N/A");
        bundleDetail.putString("imei", auctionDetail.getData().getImei() != null ? auctionDetail.getData().getImei() : "N/A");
        bundleDetail.putString("storage", auctionDetail.getData().getStorage() != null ? auctionDetail.getData().getStorage() : "N/A");
        bundleDetail.putString("body_condition", auctionDetail.getData().getBodyCondition() != null
                ? auctionDetail.getData().getBodyCondition()
                : "N/A");
        bundleDetail.putString("screen_condition", auctionDetail.getData().getScreenCondition() != null
                ? auctionDetail.getData().getScreenCondition()
                : "N/A");
        bundleDetail.putString("camera_condition", auctionDetail.getData().getCameraCondition() != null
                ? auctionDetail.getData().getCameraCondition()
                : "N/A");
        bundleDetail.putString("port_condition", auctionDetail.getData().getPortCondition() != null
                ? auctionDetail.getData().getPortCondition()
                : "N/A");
        bundleDetail.putString("button_condition", auctionDetail.getData().getButtonCondition() != null
                ? auctionDetail.getData().getButtonCondition()
                : "N/A");
        bundleDetail.putString("brand", auctionDetail.getData().getBrand() != null ? auctionDetail.getData().getBrand() : "N/A");
        bundleDetail.putString("model", auctionDetail.getData().getModel() != null ? auctionDetail.getData().getModel() : "N/A");
        bundleDetail.putString("serial", auctionDetail.getData().getSerial() != null
                ? String.valueOf(auctionDetail.getData().getSerial())
                : "N/A");
        bundleDetail.putString("control_number", auctionDetail.getData().getControlNumber() != null
                ? String.valueOf(auctionDetail.getData().getControlNumber())
                : "N/A");
        bundleDetail.putString("manufacturer", auctionDetail.getData().getManufacturer() != null
                ? auctionDetail.getData().getManufacturer()
                : "N/A");
        bundleDetail.putString("device_image", auctionDetail.getData().getDeviceImage() != null
                ? auctionDetail.getData().getDeviceImage()
                : "");
        detailFragment.setArguments(bundleDetail);

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

    private String getAuctionDetail() {
        SharedPreferences preferences = getSharedPreferences("auctionPrefs", MODE_PRIVATE);
        return preferences.getString("token", null); // Returns null if token is not found
    }

}