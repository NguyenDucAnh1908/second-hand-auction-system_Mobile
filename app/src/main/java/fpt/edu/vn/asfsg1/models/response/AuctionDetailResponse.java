package fpt.edu.vn.asfsg1.models.response;

import java.io.Serializable;
import java.util.List;

public class AuctionDetailResponse implements Serializable {
    private String message;
    private String status;
    private AuctionDetailData data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public AuctionDetailData getData() {
        return data;
    }

    public static class AuctionDetailData implements Serializable{
        private int itemId;
        private String itemName;
        private String itemDescription;
        private String thumbnail;
        private String itemStatus;
        private Auction auction;
        private SubCategory scId;
        private ItemSpecific itemSpecific;
        private List<AuctionImage> images;

        public int getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public Auction getAuction() {
            return auction;
        }

        public SubCategory getScId() {
            return scId;
        }

        public ItemSpecific getItemSpecific() {
            return itemSpecific;
        }

        public List<AuctionImage> getImages() {
            return images;
        }
    }

    public static class Auction implements Serializable{
        private String startDate;
        private String endDate;
        private String status;
        private int auction_id;
        private String start_time;
        private String end_time;
        private int start_price;
        private String approved_at;
        private String created_by;
        private String create_at;
        private String update_at;

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getStatus() {
            return status;
        }

        public int getAuction_id() {
            return auction_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public int getStart_price() {
            return start_price;
        }

        public String getApproved_at() {
            return approved_at;
        }

        public String getCreated_by() {
            return created_by;
        }

        public String getCreate_at() {
            return create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }
    }

    public static class SubCategory implements Serializable{
        private int sub_category_id;
        private String sub_category;

        public int getSub_category_id() {
            return sub_category_id;
        }

        public String getSub_category() {
            return sub_category;
        }
    }

    public static class ItemSpecific implements Serializable{
        private Integer percent;
        private Integer itemSpecId;
        private String type;
        private String color;
        private int weight;
        private String dimension;
        private String original;
        private String manufactureDate;
        private String material;

        public Integer getPercent() {
            return percent;
        }

        public Integer getItemSpecId() {
            return itemSpecId;
        }

        public String getType() {
            return type;
        }

        public String getColor() {
            return color;
        }

        public int getWeight() {
            return weight;
        }

        public String getDimension() {
            return dimension;
        }

        public String getOriginal() {
            return original;
        }

        public String getManufactureDate() {
            return manufactureDate;
        }

        public String getMaterial() {
            return material;
        }
    }

    public static class AuctionImage implements Serializable{
        private int idImage;
        private String image;

        public int getIdImage() {
            return idImage;
        }

        public String getImage() {
            return image;
        }
    }
}
