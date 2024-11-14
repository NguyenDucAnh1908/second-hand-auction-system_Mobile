package fpt.edu.vn.asfsg1.models;

import fpt.edu.vn.asfsg1.models.response.ItemResponse;

public class Item {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private String thumbnail;
    private String itemStatus;
    private Object auction;  // Assuming auction can be null or some type
    private ItemResponse.Item.SubCategory scId;
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

    public ItemResponse.Item.SubCategory getScId() {
        return scId;
    }

    public void setScId(ItemResponse.Item.SubCategory scId) {
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
}
