package fpt.edu.vn.asfsg1.models.response;

import java.time.LocalDateTime;

public class BidResponse {
    private String totalPage, totalElement;
    private String message;
    private String status;
    private BidData data;

    public String getTotalElement() {
        return totalElement;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public BidData getData() {
        return data;
    }

    public static class BidData {
        private int bidAmount;
        private LocalDateTime bidTime;
        private boolean winBid;
        private Integer userId;
        private Integer auctionId;
        private String username;

        public int getBidAmount() {
            return bidAmount;
        }

        public void setBidAmount(int bidAmount) {
            this.bidAmount = bidAmount;
        }

        public LocalDateTime getBidTime() {
            return bidTime;
        }

        public void setBidTime(LocalDateTime bidTime) {
            this.bidTime = bidTime;
        }

        public boolean isWinBid() {
            return winBid;
        }

        public void setWinBid(boolean winBid) {
            this.winBid = winBid;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(Integer auctionId) {
            this.auctionId = auctionId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
