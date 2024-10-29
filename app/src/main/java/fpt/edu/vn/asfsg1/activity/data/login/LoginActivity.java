package fpt.edu.vn.asfsg1.activity.data.login;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.activity.SignUpActivity;
import fpt.edu.vn.asfsg1.databinding.ActivityLoginBinding;
import fpt.edu.vn.asfsg1.helper.APIClient;
import fpt.edu.vn.asfsg1.request.LoginRequest;
import fpt.edu.vn.asfsg1.response.LoginResponse;
import fpt.edu.vn.asfsg1.response.RegisterResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final TextView loginButton = (TextView) binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final TextView tvSignUp = binding.tvSignUp;
        final TextView tvForgetPassword = binding.tvForgetPassword;

        String text = "Chưa có tài khoản? Đăng ký";
        String forgetText = "Quên mật khẩu? Lấy lại mật khẩu";

        setSpannableText(tvSignUp, text, "Đăng ký");
        setSpannableText(tvForgetPassword, forgetText, "Lấy lại mật khẩu");
//        SpannableString spannableString = new SpannableString(text);
//        SpannableString spannableStringForget = new SpannableString(forgetText);
//        int startIndex = text.indexOf("Đăng ký");
//        int startIndexForget = forgetText.indexOf("Lấy lại mật khẩu");
//        int endIndex = startIndex + "Đăng ký".length();
//        int endIndexForget = startIndexForget + "Lấy lại mật khẩu".length();
//
//        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#462D48")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new AbsoluteSizeSpan(16, true), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        spannableStringForget.setSpan(new StyleSpan(Typeface.BOLD), startIndexForget, endIndexForget, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringForget.setSpan(new ForegroundColorSpan(Color.parseColor("#462D48")), startIndexForget, endIndexForget, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringForget.setSpan(new AbsoluteSizeSpan(16, true), startIndexForget, endIndexForget, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tvSignUp.setText(spannableString);
//        tvForgetPassword.setText(spannableStringForget);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                // Retrieve input from the EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                loginViewModel.login(username,password);
//                // Create the login request object
//                LoginRequest loginRequest = new LoginRequest(username, password);
//
//                // Initialize Retrofit and call the login API
//                AuthService apiService = APIClient.getClient().create(AuthService.class);
//                Call<LoginResponse> call = apiService.login(loginRequest);
//
//                // Execute the API call asynchronously
//                call.enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        loadingProgressBar.setVisibility(View.GONE);
//
//                        if (response.isSuccessful() && response.body() != null) {
//                            // Get the response body
//                            LoginResponse loginResponse = response.body();
//
//                            // Check the status of the response
//                            if (loginResponse.getStatus() == 200) {
//                                // Login is successful
//                                LoginResponse.LoginData loginData = loginResponse.getData();
//                                LoginResponse.User user = loginData.getUser();
//
//                                // Save the token if needed (for future API calls)
//                                String token = loginData.getToken();
//                                String refreshToken = loginData.getRefresh_token();
//
//                                // Update UI with user information
//                                updateUiWithUser(new LoggedInUserView(user.getFullName()));
//
//                                // Handle successful login (e.g., navigating to the next screen)
//                            } else {
//                                // Show an error message if login failed
//                                showLoginFailed(R.string.login_failed);
//                            }
//                        } else {
//                            // Handle unsuccessful login
//                            showLoginFailed(R.string.login_failed);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        // Hide the loading indicator and show an error message
//                        loadingProgressBar.setVisibility(View.GONE);
//                        showLoginFailed(R.string.login_failed);
//                    }
//                });
            }
        });


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

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}