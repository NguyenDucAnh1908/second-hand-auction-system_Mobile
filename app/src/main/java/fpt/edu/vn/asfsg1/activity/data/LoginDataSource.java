package fpt.edu.vn.asfsg1.activity.data;

import fpt.edu.vn.asfsg1.activity.data.model.LoggedInUser;
import fpt.edu.vn.asfsg1.request.LoginRequest;
import fpt.edu.vn.asfsg1.response.LoginResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final AuthService authService;

    public LoginDataSource(AuthService authService) {
        this.authService = authService;
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // Tạo một LoginRequest với username và password
            LoginRequest request = new LoginRequest(username, password);

            // Gọi API đăng nhập
            Call<LoginResponse> call = authService.login(request);
            Response<LoginResponse> response = call.execute(); // Thực hiện gọi đồng bộ

            if (response.isSuccessful() && response.body() != null) {
                // Nếu đăng nhập thành công, lấy thông tin người dùng từ phản hồi
                LoginResponse loginResponse = response.body();

                // Kiểm tra trạng thái từ backend
                if (loginResponse.getStatus() == 200) {
                    LoginResponse.LoginData loginData = loginResponse.getData();
                    LoginResponse.User userData = loginData.getUser();

                    // Tạo LoggedInUser với thông tin từ LoginResponse
                    LoggedInUser user = new LoggedInUser(
                            userData.getEmail(),
                            userData.getFullName()
                    );

                    // Thêm token nếu cần cho các hoạt động sau đăng nhập
                    String token = loginData.getToken();
                    String refreshToken = loginData.getRefresh_token();

                    // Trả về kết quả đăng nhập thành công
                    return new Result.Success<>(user);
                } else {
                    // Nếu có lỗi từ API, trả về lỗi với message
                    return new Result.Error(new IOException("Login failed: " + loginResponse.getMessage()));
                }

            } else {
                return new Result.Error(new IOException("Login failed: " + response.message()));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}