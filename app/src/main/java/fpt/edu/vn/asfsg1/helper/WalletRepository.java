package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.UserService;
import fpt.edu.vn.asfsg1.services.WalletService;

public class WalletRepository {
    public static WalletService getWalletService(){
        return APIClient.getClient().create(WalletService.class);
    }
}
