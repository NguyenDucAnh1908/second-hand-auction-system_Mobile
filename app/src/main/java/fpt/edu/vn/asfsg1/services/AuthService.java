package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.request.LoginRequest;
import fpt.edu.vn.asfsg1.models.request.RegisterRequest;
import fpt.edu.vn.asfsg1.models.response.LoginResponse;
import fpt.edu.vn.asfsg1.models.response.RegisterResponse;
import fpt.edu.vn.asfsg1.models.response.VerifyUserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {
    String AUTH = "auth";

    @POST(AUTH)
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST(AUTH + "/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // POST request for registration
    @POST(AUTH + "/refreshToken")
    Call<LoginResponse> refreshToken(@Body RegisterRequest registerRequest);

    // POST request for user verification
    @POST(AUTH + "/verify")
    Call<VerifyUserResponse> verifyUser(
            @Query("email") String email,
            @Query("otp") String otp
    );

    @POST(AUTH + "/logout")
    Call<LoginResponse> logout();
}
