package com.pchen.model;


public class Image {

    private static int id = 0;
    private String base64;
    private int imageId;

    public Image() {
    }

    public Image(String base64) {
        imageId = id++;
        this.base64 = base64;
    }

    public static void resetId() {
        id = 0;
    }


    public String getBase64() {
        return base64;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "\n base64='" + base64 + '\'' +
                "\n imageId=" + imageId +
                '}';
    }

}
