package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

public class AuctionResponse {
    private String message;
    private String status;
    private List<AuctionData> data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<AuctionData> getData() {
        return data;
    }

    public static class AuctionData {
        private String startDate;
        private String endDate;
        private String start_time;
        private String end_time;
        private int start_price;
        private String description;
        private String terms_conditions;
        private int price_step;
        private String ship_type;
        private String status;
        private String approved_by;
        private String approved_at;
        private String created_by;
        private int itemId;

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
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

        public String getDescription() {
            return description;
        }

        public String getTerms_conditions() {
            return terms_conditions;
        }

        public int getPrice_step() {
            return price_step;
        }

        public String getShip_type() {
            return ship_type;
        }

        public String getStatus() {
            return status;
        }

        public String getApproved_by() {
            return approved_by;
        }

        public String getApproved_at() {
            return approved_at;
        }

        public String getCreated_by() {
            return created_by;
        }

        public int getItemId() {
            return itemId;
        }
    }
}
