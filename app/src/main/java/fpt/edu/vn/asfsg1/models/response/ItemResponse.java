package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

public class ItemResponse {
    private String message;
    private String status;
    private List<Item> data;

    // Getters and setters
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

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

public  static class Item {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private String thumbnail;
    private String itemStatus;
    private Object auction;  // Assuming auction can be null or some type
    private SubCategory scId;
    private String createdAt;  // Assuming these can be null
    private String updatedAt;

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Object getAuction() {
        return auction;
    }

    public void setAuction(Object auction) {
        this.auction = auction;
    }

    public SubCategory getScId() {
        return scId;
    }

    public void setScId(SubCategory scId) {
        this.scId = scId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class SubCategory {
        private int sub_category_id;
        private String sub_category;

        // Getters and setters
        public int getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(int sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSub_category() {
            return sub_category;
        }

        public void setSub_category(String sub_category) {
            this.sub_category = sub_category;
        }
    }
    }
}
