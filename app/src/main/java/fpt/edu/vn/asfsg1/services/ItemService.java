package fpt.edu.vn.asfsg1.services;

import java.util.List;

import fpt.edu.vn.asfsg1.models.response.AuctionDetailResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionListResponse;
import fpt.edu.vn.asfsg1.models.response.AuctionResponse;
import fpt.edu.vn.asfsg1.models.response.ItemResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {
    String ITEM = "item";

    // API để lấy top 10 sản phẩm nổi bật
    @GET(ITEM + "/top-10-featured-item")
    Call<ItemResponse> getTop10FeaturedItem();

    @GET(ITEM)
    Call<AuctionListResponse> getAuctionList();

    @GET(ITEM + "/detail/{id}")
    Call<AuctionDetailResponse> getAuctionDetail(
            @Path("id") String id
    );
}
