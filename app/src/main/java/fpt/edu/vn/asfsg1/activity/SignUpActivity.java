package fpt.edu.vn.asfsg1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import fpt.edu.vn.asfsg1.databinding.ActivitySignUpBinding;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.models.request.RegisterRequest;
import fpt.edu.vn.asfsg1.models.response.RegisterResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        final EditText usernameEditText = binding.username;
        usernameEditText.setText("binhdx@gmail.com");
        final EditText phoneEditText = binding.phone;
        phoneEditText.setText("0376819713");
        final EditText fullnameEditText = binding.fullname;
        fullnameEditText.setText("Đào Xuân Bình");
        final EditText passwordEditText = binding.password;
        passwordEditText.setText("12345678");
        final EditText repasswordEditText = binding.repassword;
        repasswordEditText.setText("12345678");
        final TextView signupButton = (TextView) binding.signup;
        final ProgressBar loadingProgressBar = binding.loading;
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

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signupButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            String email = usernameEditText.getText().toString().trim();
            String phoneNumber = phoneEditText.getText().toString().trim();
            String fullName = fullnameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String repassword = repasswordEditText.getText().toString().trim();



            // Kiểm tra tính hợp lệ của dữ liệu (đơn giản)
            if (email.isEmpty() || phoneNumber.isEmpty() || fullName.isEmpty() || password.isEmpty() || !password.equals(repassword)) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin và kiểm tra mật khẩu", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.GONE);
                return;
            }

            RegisterRequest registerRequest = new RegisterRequest(email, password, fullName, phoneNumber);
            try {
                Call<RegisterResponse> call = authService.registerUser(registerRequest);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                        if (response.body() == null) {
                            Log.e("resgister", "Response body is null. Code: " + response.code());
                        }
                        if (response.isSuccessful() && response.body() != null){
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse.getStatus() == 200) {
                                Toast.makeText(SignUpActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        loadingProgressBar.setVisibility(View.GONE);
                        Log.e("Register Error", "Failed to register user: " + t.getMessage(), t);
                        Toast.makeText(SignUpActivity.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại.", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
//                        intent.putExtra("email", username);
//                        startActivity(intent);
//                        finish();
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}