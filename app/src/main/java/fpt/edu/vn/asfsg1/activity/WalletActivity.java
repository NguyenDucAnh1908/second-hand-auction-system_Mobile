package fpt.edu.vn.asfsg1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.ActivityAuctionDetailBinding;
import fpt.edu.vn.asfsg1.databinding.ActivityWalletBinding;
import fpt.edu.vn.asfsg1.services.WalletService;
import fpt.edu.vn.asfsg1.ui.auction.DescribeFragment;
import fpt.edu.vn.asfsg1.ui.auction.DetailFragment;
import fpt.edu.vn.asfsg1.ui.wallet.DepositFragment;
import fpt.edu.vn.asfsg1.ui.wallet.WithdrawFragment;

public class WalletActivity extends AppCompatActivity {

    ActivityWalletBinding binding;
    private WalletService walletService;
    String userObjectString;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        userObjectString = preferences.getString("userObject", null);

        setupViewpager();
        initBotNav();
        initTopNav();
    }

    private void initTopNav() {
    }

    private void initBotNav() {

    }

    private void setupViewpager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();

        DepositFragment depositFragment = new DepositFragment();
        WithdrawFragment withdrawFragment = new WithdrawFragment();

        depositFragment.setArguments(bundle);
        withdrawFragment.setArguments(bundle1);

        adapter.addFrag(depositFragment, "Nạp tiền");
        adapter.addFrag(withdrawFragment, "Rút tiền");
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
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
}