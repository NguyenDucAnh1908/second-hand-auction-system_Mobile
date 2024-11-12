package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.AuctionRegisterService;
import fpt.edu.vn.asfsg1.services.AuctionService;

public class AuctionRegisterRepository {
    public static AuctionRegisterService getAuctionRegisterService(){
        return APIClient.getClient().create(AuctionRegisterService.class);
    }
}
