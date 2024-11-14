package fpt.edu.vn.asfsg1.models;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private String role;
    private boolean status;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public boolean isStatus() {
        return status;
    }
}
