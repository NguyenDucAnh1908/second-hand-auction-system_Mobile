package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
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
import android.window.OnBackInvokedCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fpt.edu.vn.asfsg1.databinding.ActivityLoginBinding;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.models.request.LoginRequest;
import fpt.edu.vn.asfsg1.models.response.LoginResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import fpt.edu.vn.asfsg1.tokenManager.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthService authService;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authService = AuthRepository.getAuthService();

        if (isUserLoggedIn()) {
            navigateToMainActivity();
            return; // End the current activity as user is already logged in
        }
        
        initializeUI();
        setupBackNavigation();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        return preferences.contains("userObject"); // Returns true if userObject is saved
    }

    private void initializeUI() {
        binding.tvSignUp.setOnClickListener(v -> navigateToSignUp());
        setSpannableText(binding.tvSignUp, "Chưa có tài khoản? Đăng ký", "Đăng ký");
        setSpannableText(binding.tvForgetPassword, "Quên mật khẩu? Lấy lại mật khẩu", "Lấy lại mật khẩu");

        binding.username.setText("chanhtong11@gmail.com");
        binding.password.setText("Trieuvanlv6o$");

        binding.username.addTextChangedListener(getEmailTextWatcher());
        binding.password.addTextChangedListener(getPasswordTextWatcher());

        binding.login.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        binding.loading.setVisibility(View.VISIBLE);

        String email = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            binding.loading.setVisibility(View.GONE);
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            binding.loading.setVisibility(View.GONE);
        } else {
            performLogin(new LoginRequest(email, password));
        }
    }

    private void performLogin(LoginRequest loginRequest) {
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.loading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    handleLoginSuccess(response.body());
                } else {
                    handleLoginFailure(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    // Có thể do lỗi mạng
                    Toast.makeText(LoginActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Có thể là lỗi khác
                    Toast.makeText(LoginActivity.this, "Lỗi không xác định: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                t.printStackTrace();
            }
        });
    }



    private void handleLoginSuccess(LoginResponse loginResponse) {
        if (loginResponse.getStatus() == 200) {
            if (loginResponse.getData().getUser().getRole().equals("BUYER")){
                Toast.makeText(this, "Chào " + loginResponse.getData().getUser().getFullName(), Toast.LENGTH_SHORT).show();
                saveLoginDetails(loginResponse);
                navigateToMainActivity();
            } else if (loginResponse.getData().getUser().getRole().equals("SELLER")) {
                Toast.makeText(this, "Chào " + loginResponse.getData().getUser().getFullName(), Toast.LENGTH_SHORT).show();
                saveLoginDetails(loginResponse);
                navigateToMainActivity();
            } else {
                Toast.makeText(this, "Chỉ dùng tài khoản người BUYER và SELLER trên app", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLoginFailure(Response<LoginResponse> response) {
        String message;
        if (response.code() == 404) {
            message = "Lỗi đăng nhập: tài khoản không tồn tại.";
        } else if (response.code() == 500) {
            message = "Lỗi máy chủ. Vui lòng thử lại sau.";
        } else {
            message = "Đăng nhập thất bại: " + response.message();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.e("LoginActivity", "Đăng nhập thất bại. Code: " + response.code());
    }

    private void saveLoginDetails(LoginResponse loginResponse) {
        TokenManager.setToken(loginResponse.getData().getToken());
        TokenManager.setId_user(loginResponse.getData().getUser().getId());
        JSONObject userObject = createUserJSONObject(loginResponse);
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        preferences.edit().putString("userObject", userObject.toString()).apply();
        preferences.edit().putString("token", TokenManager.getToken().toString()).apply();
    }

    private JSONObject createUserJSONObject(LoginResponse loginResponse) {
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("id", loginResponse.getData().getUser().getId());
            userObject.put("fullName", loginResponse.getData().getUser().getFullName());
            userObject.put("avatar", loginResponse.getData().getUser().getAvatar());
            userObject.put("role", loginResponse.getData().getUser().getRole());
            userObject.put("token", loginResponse.getData().getToken());
            userObject.put("refresh_token", loginResponse.getData().getRefreshToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userObject;
    }

    private void setSpannableText(TextView textView, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        int startIndex = text.indexOf(keyword);
        int endIndex = startIndex + keyword.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#462D48")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(16, true), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private TextWatcher getEmailTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(s.toString())) {
                    binding.username.setError("Email không hợp lệ");
                } else {
                    binding.username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private TextWatcher getPasswordTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isPasswordValid(s.toString())) {
                    binding.password.setError("Mật khẩu phải có ít nhất 8 ký tự");
                } else {
                    binding.password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

        startActivity(intent);
        finish();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupBackNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    /* priority */ 0,
                    () -> showExitConfirmation()
            );
        } else {
            // For older versions
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    showExitConfirmation();
                }
            });
        }
    }

    private void showExitConfirmation() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        // Reset after 2 seconds
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

}
