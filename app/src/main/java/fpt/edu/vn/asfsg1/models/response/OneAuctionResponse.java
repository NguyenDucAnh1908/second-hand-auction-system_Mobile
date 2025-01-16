package fpt.edu.vn.asfsg1.models.response;

import fpt.edu.vn.asfsg1.models.Auction;

public class OneAuctionResponse {
    private String message;
    private String status;
    private Auction data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Auction getData() {
        return data;
    }
}
