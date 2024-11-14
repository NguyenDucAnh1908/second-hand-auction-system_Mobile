package fpt.edu.vn.asfsg1.models.response;

public class CheckStatusAuctionRegisterResponse {
    private String message;
    private String status;
    private Data data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private int userId;
        private int auctionId;
        private boolean statusRegistration;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(int auctionId) {
            this.auctionId = auctionId;
        }

        public boolean isStatusRegistration() {
            return statusRegistration;
        }

        public void setStatusRegistration(boolean statusRegistration) {
            this.statusRegistration = statusRegistration;
        }
    }
}
