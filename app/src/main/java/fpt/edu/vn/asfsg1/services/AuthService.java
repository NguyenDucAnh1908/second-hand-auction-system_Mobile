package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.request.LoginRequest;
import fpt.edu.vn.asfsg1.request.RegisterRequest;
import fpt.edu.vn.asfsg1.response.LoginResponse;
import fpt.edu.vn.asfsg1.response.RegisterResponse;
import fpt.edu.vn.asfsg1.response.VerifyUserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {
    String AUTH = "auth";

    @POST(AUTH + "/login")
    Call<LoginResponse> login(@Body LoginRequest credentials);

    // POST request for registration
    @POST(AUTH + "/Register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest credentials);

    // POST request for user verification
    @POST(AUTH + "/verify")
    Call<VerifyUserResponse> verifyUser(
            @Query("email") String email,
            @Query("otp") String otp
    );
}
