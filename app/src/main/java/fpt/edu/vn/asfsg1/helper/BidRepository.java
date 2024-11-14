package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.BidService;

public class BidRepository {
    public static BidService getBidService(){
        return APIClient.getClient().create(BidService.class);
    }
}
