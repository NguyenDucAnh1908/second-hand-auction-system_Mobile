package fpt.edu.vn.asfsg1.tokenManager;

import org.json.JSONObject;

public class TokenManager {
    private static String token = null;
    private static String userId = null;
    private static JSONObject userObject = null;


    public static JSONObject getUserObject() {
        return userObject;
    }

    public static void setUserObject(JSONObject user) {
        TokenManager.userObject = user;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenManager.token = token;
    }
    public static void clearToken(){
        token = null;
        userId = null;
    }

    public static String getId_user() {
        return userId;
    }

    public static void setId_user(String id_user) {
        TokenManager.userId = id_user;
    }
}
