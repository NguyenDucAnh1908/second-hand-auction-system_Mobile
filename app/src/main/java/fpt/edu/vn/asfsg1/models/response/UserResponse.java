package fpt.edu.vn.asfsg1.models.response;

public class UserResponse {
    private String message;
    private String status;
    private UserData data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public UserData getData() {
        return data;
    }



    public static class UserData {
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
}
