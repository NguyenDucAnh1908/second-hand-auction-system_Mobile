package fpt.edu.vn.asfsg1.activity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.ActivityMainBinding;
import fpt.edu.vn.asfsg1.ui.dashboard.DashboardFragment;
import fpt.edu.vn.asfsg1.ui.home.HomeFragment;
import fpt.edu.vn.asfsg1.ui.notifications.NotificationsFragment;
import fpt.edu.vn.asfsg1.ui.profile.ProfileFragment;

import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment homeFragment, dashboardFragment, notificationsFragment, profileFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo các Fragment một lần duy nhất
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        profileFragment = new ProfileFragment();

        // Đặt Fragment mặc định là Home
        activeFragment = homeFragment;

        // Thêm các Fragment vào FragmentManager, chỉ hiển thị Fragment Home ban đầu
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, profileFragment, "4").hide(profileFragment)
                .add(R.id.frameLayout, notificationsFragment, "3").hide(notificationsFragment)
                .add(R.id.frameLayout, dashboardFragment, "2").hide(dashboardFragment)
                .add(R.id.frameLayout, homeFragment, "1").commit();

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                switchFragment(homeFragment);
            } else if (itemId == R.id.navigation_dashboard) {
                switchFragment(dashboardFragment);
            } else if (itemId == R.id.navigation_notifications) {
                switchFragment(notificationsFragment);
            } else if (itemId == R.id.navigation_profile) {
                switchFragment(profileFragment);
            }
            return true;
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != activeFragment) {
            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment).commit();
            activeFragment = fragment;
        }
    }
//        replaceFragment(new ProfileFragment());
//
//        binding.navView.setOnItemSelectedListener(item -> {
//            int itemId =  item.getItemId();
//            if(itemId == R.id.navigation_home) {
//                replaceFragment(new HomeFragment());
//            } else if (itemId == R.id.navigation_dashboard) {
//                replaceFragment(new DashboardFragment());
//            } else if (itemId == R.id.navigation_notifications) {
//                replaceFragment(new NotificationsFragment());
//            } else if (itemId == R.id.navigation_profile) {
//                replaceFragment(new ProfileFragment());
//            }
//            return true;
//        });
//
//
//    }
//
//
//
//
//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();
//    }


}
