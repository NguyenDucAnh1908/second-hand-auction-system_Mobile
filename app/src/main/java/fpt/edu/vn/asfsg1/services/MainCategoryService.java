package fpt.edu.vn.asfsg1.services;

import java.util.List;

import fpt.edu.vn.asfsg1.models.request.MainCategoryRequest;
import fpt.edu.vn.asfsg1.models.response.MainCategoryResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MainCategoryService {
    String MAIN_CATEGORY = "main-category";

    @GET(MAIN_CATEGORY)
    Call<List<MainCategoryResponse>> getMainCategories();

    @GET(MAIN_CATEGORY + "/{id}")
    Call<MainCategoryResponse> getMainCategoryById(@Path("id") int id);
}