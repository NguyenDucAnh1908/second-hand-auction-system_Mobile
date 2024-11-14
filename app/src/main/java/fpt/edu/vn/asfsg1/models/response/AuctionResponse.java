package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

import fpt.edu.vn.asfsg1.models.Auction;

public class AuctionResponse {
    private String message;
    private String status;
    private List<Auction> data;


    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<Auction> getData() {
        return data;
    }

}
