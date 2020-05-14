package com.pchen.util;

import com.pchen.model.Image;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ImageUtil {

    private static Gson gson = new Gson();

    public static String toJson(Image image) {
        return gson.toJson(image);
    }

    public static String toJson(Set<Integer> imageIds) {
        return gson.toJson(imageIds);
    }

    public static Image fromJson(String json) {
        return gson.fromJson(json, Image.class);
    }


}
