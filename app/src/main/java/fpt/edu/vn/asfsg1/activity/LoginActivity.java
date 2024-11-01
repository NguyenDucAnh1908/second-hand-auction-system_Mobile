package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import org.json.JSONException;
import org.json.JSONObject;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = AuthRepository.getAuthService();
        initializeUI();
    }

    private void initializeUI() {
        binding.tvSignUp.setOnClickListener(v -> navigateToSignUp());
        setSpannableText(binding.tvSignUp, "Chưa có tài khoản? Đăng ký", "Đăng ký");
        setSpannableText(binding.tvForgetPassword, "Quên mật khẩu? Lấy lại mật khẩu", "Lấy lại mật khẩu");

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
                Toast.makeText(LoginActivity.this, "Fail: Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLoginSuccess(LoginResponse loginResponse) {
        if (loginResponse.getStatus() == 200) {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            saveLoginDetails(loginResponse);
            navigateToMainActivity();
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
        TokenManager.setUserObject(createUserJSONObject(loginResponse));
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
}
