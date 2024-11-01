package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpt.edu.vn.asfsg1.databinding.ActivityVerifyBinding;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.models.request.VerifyUserRequest;
import fpt.edu.vn.asfsg1.models.response.VerifyUserResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {
    private ActivityVerifyBinding binding;
    AuthService authService;

    private EditText[] otpFields;

    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otpFields = new EditText[]{
                binding.otpDigit1,
                binding.otpDigit2,
                binding.otpDigit3,
                binding.otpDigit4,
                binding.otpDigit5,
                binding.otpDigit6
        };

        email = getIntent().getStringExtra("email");

        final TextView verifyButton = (TextView) binding.verify;
        ProgressBar loadingProgressBar = binding.loading;
        final TextView tvLogin = binding.tvLogin;
        authService = AuthRepository.getAuthService();

        String text = "Đã có tài khoản? Đăng nhập";

        SpannableString spannableString = new SpannableString(text);
        int startIndex = text.indexOf("Đăng nhập");
        int endIndex = startIndex + "Đăng nhập".length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#462D48")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(16, true), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogin.setText(spannableString);

        for (int i = 0; i < otpFields.length; i++) {
            final int index = i; // For use in the listener
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        // Move to the next EditText
                        otpFields[index + 1].requestFocus();
                    }
                    if (s.length() == 0 && index > 0) {
                        // Move to the previous EditText if current is empty
                        otpFields[index - 1].requestFocus();
                    }
                    // Check if all fields are filled to create a complete OTP string
                    if (areAllFieldsFilled()) {
                        String otp = getOtpString();
                        // Here you can do something with the OTP string, e.g., send it for verification
                        Log.d("OTP", "Entered OTP: " + otp);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Optional: Add a button to submit or verify the OTP
        verifyButton.setOnClickListener(v -> {
            if (areAllFieldsFilled()) {
                String otp = getOtpString();
                // Handle OTP verification logic here
                loadingProgressBar.setVisibility(View.VISIBLE);

                VerifyUserRequest request = new VerifyUserRequest(email, otp);
                authService = AuthRepository.getAuthService();
                System.out.println(email);
                System.out.println(otp);

                Call<VerifyUserResponse> call = authService.verifyUser(request.getEmail(), request.getOtp());
                call.enqueue(new Callback<VerifyUserResponse>() {
                    @Override
                    public void onResponse(Call<VerifyUserResponse> call, Response<VerifyUserResponse> response) {
                        loadingProgressBar.setVisibility(View.GONE);
                        if (response.body() != null) {
                            VerifyUserResponse verifyResponse = response.body();
                            if (verifyResponse.getStatus() == 200) {
                                Toast.makeText(VerifyActivity.this, "Xác thực thành công. Đăng nhập lại", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(VerifyActivity.this, verifyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(VerifyActivity.this, "Đã xảy ra lỗi, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyUserResponse> call, Throwable t) {
                        loadingProgressBar.setVisibility(View.GONE);
                        Log.e("VerifyActivity", "Verification failed: " + t.getMessage());
                        Toast.makeText(VerifyActivity.this, "Đã xảy ra lỗi, vui lòng kiểm tra kết nối của bạn.", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("OTP", "Entered OTP on button click: " + otp);
            }
        });
    }

    private boolean areAllFieldsFilled() {
        for (EditText otpField : otpFields) {
            if (otpField.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getOtpString() {
        StringBuilder otpBuilder = new StringBuilder();
        for (EditText otpField : otpFields) {
            otpBuilder.append(otpField.getText().toString());
        }
        return otpBuilder.toString();
    }

}
