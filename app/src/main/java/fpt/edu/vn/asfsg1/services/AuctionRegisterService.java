package fpt.edu.vn.asfsg1.services;

import static fpt.edu.vn.asfsg1.services.UserService.token;

import fpt.edu.vn.asfsg1.models.response.AuctionRegisterResponse;
import fpt.edu.vn.asfsg1.models.response.CheckStatusAuctionRegisterResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuctionRegisterService {
    String AUCTION_REGISTER = "auction-register";



    @POST(AUCTION_REGISTER)
    Call<AuctionRegisterResponse> createAuctionRegister(
            @Path("auction_id") int auction_id
    );

    @GET(AUCTION_REGISTER + "/check-registration/{auctionId}")
    Call<CheckStatusAuctionRegisterResponse> checkRegistration(
            @Header("Authorization") String token,
            @Path("auctionId") int auction_id
    );
}
