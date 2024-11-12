package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

public class AuctionListResponse {
    private String message;
    private String status;
    private AuctionListData data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public AuctionListData getData() {
        return data;
    }

    public static class AuctionListData {
        private List<AuctionItem> data;
        private int totalPages;
        private int totalElements;

        public List<AuctionItem> getData() {
            return data;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }
    }

    public static class AuctionItem {
        private int itemId;
        private String itemName;
        private String itemDescription;
        private String thumbnail;
        private String itemStatus;
        private Auction auction;
        private SubCategory scId;
        private String created_at;
        private String updated_at;

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

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Auction {
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

    public static class SubCategory {
        private int sub_category_id;
        private String sub_category;

        public int getSub_category_id() {
            return sub_category_id;
        }

        public String getSub_category() {
            return sub_category;
        }
    }
}
