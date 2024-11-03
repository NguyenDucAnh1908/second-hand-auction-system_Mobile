package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.AuthService;
import fpt.edu.vn.asfsg1.services.MainCategoryService;

public class MainCategoryRepository {
    public static MainCategoryService getMainCategoryService(){
        return APIClient.getClient().create(MainCategoryService.class);
    }
}
