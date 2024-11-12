package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.AuctionService;

public class AuctionRepository {
    public static AuctionService getAuctionService(){
        return APIClient.getClient().create(AuctionService.class);
    }
}
