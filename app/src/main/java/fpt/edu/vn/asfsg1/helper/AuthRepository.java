package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.AuthService;

public class AuthRepository {
    public static AuthService getAuthService(){
        return APIClient.getClient().create(AuthService.class);
    }
}
