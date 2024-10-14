package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpt.edu.vn.asfsg1.MainActivity;
import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.activity.data.login.LoginActivity;
import fpt.edu.vn.asfsg1.databinding.ActivityLoginBinding;
import fpt.edu.vn.asfsg1.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    private ActivitySplashBinding binding;
    Animation topAnimation;
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        logoImage = binding.logoImage;

        logoImage.setAnimation(topAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}