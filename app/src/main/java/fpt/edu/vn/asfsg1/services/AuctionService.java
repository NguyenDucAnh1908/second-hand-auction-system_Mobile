package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.response.AuctionResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuctionService {
    String AUCTION = "auctions";

    @GET(AUCTION)
    Call<AuctionResponse> getAuctions();
}
