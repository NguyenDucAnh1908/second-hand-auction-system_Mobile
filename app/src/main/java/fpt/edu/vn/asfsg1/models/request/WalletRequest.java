package fpt.edu.vn.asfsg1.models.request;

import com.google.gson.annotations.SerializedName;

public class WalletRequest {
    @SerializedName("paymentMethod")
    String paymentMethod;
    @SerializedName("description")
    String description;
    @SerializedName("amount")
    int amount;

    public WalletRequest(String paymentMethod, String description, int amount) {
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
