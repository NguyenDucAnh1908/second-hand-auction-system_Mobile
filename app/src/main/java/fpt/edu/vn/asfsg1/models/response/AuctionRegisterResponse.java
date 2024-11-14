package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

import fpt.edu.vn.asfsg1.models.Auction;

public class AuctionRegisterResponse {
    private String message;
    private String status;
    private List<AuctionRegisterResponse.RegisterData> data;

    public List<RegisterData> getData() {
        return data;
    }

    public void setData(List<RegisterData> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class RegisterData {
        private int ar_id;
        private double depositAmount;
        private Boolean registration;
        private Auction data;

        public Boolean getRegistration() {
            return registration;
        }

        public double getDepositAmount() {
            return depositAmount;
        }

        public int getAr_id() {
            return ar_id;
        }

        public void setAr_id(int ar_id) {
            this.ar_id = ar_id;
        }

        public void setDepositAmount(double depositAmount) {
            this.depositAmount = depositAmount;
        }

        public void setRegistration(Boolean registration) {
            this.registration = registration;
        }

        public Auction getData() {
            return data;
        }

        public void setData(Auction data) {
            this.data = data;
        }
    }
}
