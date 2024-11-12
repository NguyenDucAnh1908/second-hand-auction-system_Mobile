package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.MainCategoryService;
import fpt.edu.vn.asfsg1.services.UserService;

public class UserRepository {
    public static UserService getUserService(){
        return APIClient.getClient().create(UserService.class);
    }
}
