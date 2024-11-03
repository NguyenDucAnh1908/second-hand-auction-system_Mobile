package fpt.edu.vn.asfsg1.models.response;

public class SubCategoryResponse {
    private int subCategoryId;
    private String subCategory;
    private String description;

    public SubCategoryResponse() {
    }

    public SubCategoryResponse(int subCategoryId, String subCategory, String description) {
        this.subCategoryId = subCategoryId;
        this.subCategory = subCategory;
        this.description = description;
    }

    // Getters and Setters
    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
