package fpt.edu.vn.asfsg1.services;

import java.util.List;

import fpt.edu.vn.asfsg1.models.response.ItemResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ItemService {
    String ITEM = "item";

//    @POST(ITEM)
//    Call<ItemResponse> createItem(@Body ItemDto itemDto);
//
//    // API để cập nhật thông tin sản phẩm
//    @PUT("item/{itemId}")
//    Call<ResponseObject> updateItem(@Path("itemId") Integer itemId, @Body ItemDto itemDto);
//
//    // API để phê duyệt một sản phẩm
//    @PUT("item/approve/{itemId}")
//    Call<ResponseObject> approveItem(@Path("itemId") Integer itemId, @Body ItemApprove itemApprove);
//
//    // API để xóa một sản phẩm
//    @DELETE("item/{itemId}")
//    Call<ResponseObject> deleteItem(@Path("itemId") Integer itemId);

    // API để lấy top 10 sản phẩm nổi bật
    @GET(ITEM + "/top-10-featured-item")
    Call<ItemResponse> getTop10FeaturedItem();

//    // API để lấy sản phẩm của người bán
//    @GET("item/product-user")
//    Call<List<AuctionItemResponse>> getProductSeller(
//            @Query("page") int page,
//            @Query("limit") int limit);
//
//    // API để lấy sản phẩm đang đấu giá
//    @GET("item/product-appraisal")
//    Call<List<AuctionItemResponse>> getProductAppraisal(
//            @Query("page") int page,
//            @Query("limit") int limit);
//
//    // API để lấy chi tiết sản phẩm
//    @GET("item/detail/{id}")
//    Call<ItemDetailResponse> getItemDetail(@Path("id") Integer id);
//
//    // API để lấy danh sách sản phẩm đấu giá của người dùng
//    @GET("item/auction-process/user")
//    Call<List<AuctionItemResponse>> getAuctionProcessByUser(
//            @Query("page") int page,
//            @Query("limit") int limit);
//
//    // API để lấy sản phẩm đã đấu giá thành công
//    @GET("item/auction-completed/user")
//    Call<List<AuctionItemResponse>> getAuctionCompleted(
//            @Query("page") int page,
//            @Query("limit") int limit);
//
//    // API để lấy sản phẩm đang trong quá trình đấu giá
//    @GET("item/auction-process/{id}")
//    Call<AuctionItemResponse> getItemAuctionProcess(@Path("id") Integer id);
}
