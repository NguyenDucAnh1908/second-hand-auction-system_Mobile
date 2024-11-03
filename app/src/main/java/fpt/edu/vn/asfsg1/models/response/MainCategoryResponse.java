package fpt.edu.vn.asfsg1.models.response;

public class MainCategoryResponse {
    private int mainCategoryId;
    private String categoryName;
    private String description;
    private String iconUrl;

    public MainCategoryResponse() {
    }

    public MainCategoryResponse(Integer mainCategoryId, String categoryName, String description, String iconUrl) {
        this.mainCategoryId = mainCategoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.iconUrl = iconUrl;
    }
    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
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