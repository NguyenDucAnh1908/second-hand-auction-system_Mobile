package fpt.edu.vn.asfsg1.models.response;

import java.util.List;

import fpt.edu.vn.asfsg1.models.User;

public class UserResponse {
    private String message;
    private String status;
    private List<User> data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<User> getData() {
        return data;
    }
}
