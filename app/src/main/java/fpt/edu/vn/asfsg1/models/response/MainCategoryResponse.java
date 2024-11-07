package fpt.edu.vn.asfsg1.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainCategoryResponse {

    private String message;
    private String status;
    private List<MainCategoryData> data;

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

    public List<MainCategoryData> getData() {
        return data;
    }

    public void setData(List<MainCategoryData> data) {
        this.data = data;
    }

    public boolean isValid() {
        return "success".equals(status) && data != null && !data.isEmpty();
    }

    public static class MainCategoryData {
        @SerializedName("main_category_id")
        private int mainCategoryId;

        @SerializedName("category_name")
        private String categoryName;

        @SerializedName("description")
        private String description;

        @SerializedName("icon_url")
        private String iconUrl;

        public int getMainCategoryId() {
            return mainCategoryId;
        }

        public void setMainCategoryId(int mainCategoryId) {
            this.mainCategoryId = mainCategoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }

}
