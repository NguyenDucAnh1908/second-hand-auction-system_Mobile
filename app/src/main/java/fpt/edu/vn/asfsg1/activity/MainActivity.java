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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        BottomNavigationView navView = binding.navView;

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId =  item.getItemId();
            if(itemId == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.navigation_dashboard) {
                replaceFragment(new DashboardFragment());
            } else if (itemId == R.id.navigation_notifications) {
                replaceFragment(new NotificationsFragment());
            } else if (itemId == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile
        ).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
