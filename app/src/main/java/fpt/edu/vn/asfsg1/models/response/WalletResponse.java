package fpt.edu.vn.asfsg1.models.response;

import fpt.edu.vn.asfsg1.models.User;

public class WalletResponse {
    private String message;
    private String status;
    private WalletData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WalletData getData() {
        return data;
    }

    public void setData(WalletData data) {
        this.data = data;
    }

    public static class WalletData {
        private Integer walletId;
        private double balance;
        private String statusWallet;
        private String walletType;
        private User userId;

        public Integer getWalletId() {
            return walletId;
        }

        public void setWalletId(Integer walletId) {
            this.walletId = walletId;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getStatusWallet() {
            return statusWallet;
        }

        public void setStatusWallet(String statusWallet) {
            this.statusWallet = statusWallet;
        }

        public String getWalletType() {
            return walletType;
        }

        public void setWalletType(String walletType) {
            this.walletType = walletType;
        }

        public User getUserId() {
            return userId;
        }

        public void setUserId(User userId) {
            this.userId = userId;
        }
    }
}
