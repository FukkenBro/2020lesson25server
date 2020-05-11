package com.pchen.util;

import com.pchen.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserUtil {

    private static Gson gson = new Gson();

    public static String toJson(User user) {
        return gson.toJson(user);
    }

    public static String toJson(ArrayList<User> users) {
        return gson.toJson(users);
    }

    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }


}
