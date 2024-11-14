package fpt.edu.vn.asfsg1.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.ActivityKycactivityBinding;
import fpt.edu.vn.asfsg1.databinding.ActivityMainBinding;

public class KYCActivity extends AppCompatActivity {
    private ActivityKycactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKycactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}