package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.request.WalletRequest;
import fpt.edu.vn.asfsg1.models.response.WalletResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WalletService {
    String WALLET = "walletCustomer";

    @GET(WALLET + "/get-balance")
    Call<WalletResponse> getBalance(
            @Header("Authorization") String token
    );

    @POST(WALLET + "/deposit")
    Call<WalletResponse> deposit(
            @Header("Authorization") String token,
            @Body WalletRequest walletRequest);
}
