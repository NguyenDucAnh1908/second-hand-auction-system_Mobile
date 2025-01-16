package fpt.edu.vn.asfsg1.services;

import fpt.edu.vn.asfsg1.models.response.BidResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BidService {
    String BID = "bids";

    @GET(BID + "/history-bid/{auctionId}")
    Call<BidResponse> getBids(
            @Path("auctionId") int auctionId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    default Call<BidResponse> getBids(int auctionId) {
        return getBids(auctionId, 10, 0);  // default limit = 10, default page = 0
    }
}
