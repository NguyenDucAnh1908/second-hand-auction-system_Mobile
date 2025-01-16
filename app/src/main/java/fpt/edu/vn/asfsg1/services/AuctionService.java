package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.Auction;
import fpt.edu.vn.asfsg1.models.response.AuctionResponse;
import fpt.edu.vn.asfsg1.models.response.OneAuctionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuctionService {
    String AUCTION = "auctions";

    @GET(AUCTION)
    Call<OneAuctionResponse> getAuctions();
}
