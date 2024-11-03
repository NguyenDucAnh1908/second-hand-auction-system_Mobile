package fpt.edu.vn.asfsg1.models.request;

public class MainCategoryRequest {
    private String categoryName;
    private String description;
    private String iconUrl;

    public MainCategoryRequest() {
    }

    public MainCategoryRequest(String categoryName, String description, String iconUrl) {
        this.categoryName = categoryName;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    // Getters and Setters
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
