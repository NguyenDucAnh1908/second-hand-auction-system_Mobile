package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.response.UserResponse;
import fpt.edu.vn.asfsg1.tokenManager.TokenManager;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserService {
    String USER = "user";

    String token = TokenManager.getToken();

    @GET("get-users")
    Call<UserResponse> getUsers(@Header("Authorization") String token, @Query("search") String search);
}
