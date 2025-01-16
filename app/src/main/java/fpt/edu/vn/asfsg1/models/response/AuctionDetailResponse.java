package fpt.edu.vn.asfsg1.models.response;

import java.io.Serializable;
import java.util.List;

import fpt.edu.vn.asfsg1.models.Auction;

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

    public static class AuctionDetailData implements Serializable {
        private Integer itemId;
        private String itemName;
        private String itemDescription;

        // Ánh xạ với priceStepItem
        private Double priceStepItem;

        private String itemDocument;
        private String thumbnail;

        // Enum hoặc String, tuỳ logic bên bạn
        private String itemStatus;

        private Double priceBuyNow;
        private String reason;
        private String imei;
        private String storage;
        private String color;
        private Double batteryHealth;
        private String osVersion;
        private String icloudStatus;
        private String bodyCondition;
        private String screenCondition;
        private String cameraCondition;
        private String portCondition;
        private String buttonCondition;

        // Thay vì Auction cũ, ta liên kết sang ItemAuctionResponse (nếu bạn có class tương tự)
        private Auction auction;

        // Thay vì SubCategory cũ, ta dùng SubCategoryItemResponse
        // (nếu dự án có sẵn lớp này để đồng bộ với BE)
        private SubCategoryItemResponse scId;

        // Nếu có AuctionTypeResponse, ta thêm vào
        private AuctionTypeResponse auctionType;

        // Thay vì ItemSpecific cũ, ta dùng ItemSpecificResponse như bạn mô tả
        private ItemSpecificResponse itemSpecific;

        // Danh sách hình ảnh
        private List<AuctionImage> images;

        private int numberParticipant;
        private Integer checkBid;

        private String brand;
        private String model;
        private Integer serial;
        private Integer controlNumber;
        private Boolean valid;
        private String manufacturer;

        // Loại thiết bị
        private String type;

        // Link ảnh từ API
        private String deviceImage;

        // Các Constructor, Getter/Setter ...
        public Integer getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public Double getPriceStepItem() {
            return priceStepItem;
        }

        public String getItemDocument() {
            return itemDocument;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public Double getPriceBuyNow() {
            return priceBuyNow;
        }

        public String getReason() {
            return reason;
        }

        public String getImei() {
            return imei;
        }

        public String getStorage() {
            return storage;
        }

        public String getColor() {
            return color;
        }

        public Double getBatteryHealth() {
            return batteryHealth;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public String getIcloudStatus() {
            return icloudStatus;
        }

        public String getBodyCondition() {
            return bodyCondition;
        }

        public String getScreenCondition() {
            return screenCondition;
        }

        public String getCameraCondition() {
            return cameraCondition;
        }

        public String getPortCondition() {
            return portCondition;
        }

        public String getButtonCondition() {
            return buttonCondition;
        }

        public Auction getAuction() {
            return auction;
        }

        public SubCategoryItemResponse getScId() {
            return scId;
        }

        public AuctionTypeResponse getAuctionType() {
            return auctionType;
        }

        public ItemSpecificResponse getItemSpecific() {
            return itemSpecific;
        }

        public List<AuctionImage> getImages() {
            return images;
        }

        public int getNumberParticipant() {
            return numberParticipant;
        }

        public Integer getCheckBid() {
            return checkBid;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public Integer getSerial() {
            return serial;
        }

        public Integer getControlNumber() {
            return controlNumber;
        }

        public Boolean getValid() {
            return valid;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public String getType() {
            return type;
        }

        public String getDeviceImage() {
            return deviceImage;
        }
    }

    /**
     * Thay thế cho Auction cũ,
     * tuỳ bạn map lại theo cấu trúc ItemAuctionResponse của dự án
     */

    /**
     * Thay cho SubCategory cũ, map với SubCategoryItemResponse
     */
    public static class SubCategoryItemResponse implements Serializable {
        private Integer subCategoryId;
        private String subCategoryName;

        // Getter/Setter...
        public Integer getSubCategoryId() {
            return subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }
    }

    /**
     * Nếu dự án có AuctionTypeResponse, có thể khai báo tương tự
     */
    public static class AuctionTypeResponse implements Serializable {
        private Integer auctionTypeId;
        private String auctionTypeName;
        // etc.
    }

    /**
     * Thay cho ItemSpecific cũ, map với ItemSpecificResponse
     */
    public static class ItemSpecificResponse implements Serializable {
        private Integer itemSpecificationId;
        private String cpu;
        private String ram;
        private String screenSize;
        private String cameraSpecs;
        private String connectivity;
        private String sensors;
        private String sim;
        private Integer simSlots;
        private String os;
        private String osFamily;
        private String bluetooth;
        private String usb;
        private String wlan;
        private String speed;
        private String networkTechnology;

        // Getter/Setter...
        public Integer getItemSpecificationId() {
            return itemSpecificationId;
        }

        public String getCpu() {
            return cpu;
        }

        public String getRam() {
            return ram;
        }

        public String getScreenSize() {
            return screenSize;
        }

        public String getCameraSpecs() {
            return cameraSpecs;
        }

        public String getConnectivity() {
            return connectivity;
        }

        public String getSensors() {
            return sensors;
        }

        public String getSim() {
            return sim;
        }

        public Integer getSimSlots() {
            return simSlots;
        }

        public String getOs() {
            return os;
        }

        public String getOsFamily() {
            return osFamily;
        }

        public String getBluetooth() {
            return bluetooth;
        }

        public String getUsb() {
            return usb;
        }

        public String getWlan() {
            return wlan;
        }

        public String getSpeed() {
            return speed;
        }

        public String getNetworkTechnology() {
            return networkTechnology;
        }
    }

    /**
     * Thay cho AuctionImage cũ, map với ImageItemResponse
     */
    public static class AuctionImage implements Serializable {
        private Integer id;
        private String image;

        // Getter/Setter...
        public Integer getId() {
            return id;
        }

        public String getImage() {
            return image;
        }
    }
}