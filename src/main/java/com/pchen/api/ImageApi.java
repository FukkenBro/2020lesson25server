package com.pchen.api;

import com.pchen.model.Image;
import com.pchen.util.Storage;
import com.pchen.util.ImageUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/image")
public class ImageApi {

    @GET
    @Path("/not-found")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestImageNotFound() {
        String resultJson = "{}";
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(resultJson)
                .header("customHeader", "TEST")
                .build();
    }

    @POST
    @Path("/newImage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createImageJson(String body) {
        Image image = ImageUtil.fromJson(body);
        Storage storage = Storage.getInstance();
        boolean createStatus = storage.addImage(image);
        String resultJson = null;
        if (createStatus) {
            resultJson = String.format("{\"result\": \"Image id=%s has been created; \n%s\"}", image.getImageId(), image.getBase64());
        } else {
            resultJson = "{\"result\": \"Image with such ID already exist\"}";
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @GET
    @Path("/{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImage(@PathParam("imageId") int imageId) {
        Storage storage = Storage.getInstance();
        Image image = storage.getImage(imageId);
        String result = null;
        if (image != null) {
            result = ImageUtil.toJson(image);
        } else {
            result = "{\"result\":\"Image not found\"}";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("/updateImage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateImageJson(String body) {
        Image image = ImageUtil.fromJson(body);
        Storage storage = Storage.getInstance();
        Boolean updateStatus = storage.updateImage(image);
        String resultJson = null;
        if (updateStatus) {
            resultJson = String.format("{\"result\": \"Image id=%s was successfully updated\" ; \n%s}", image.getImageId(), image.getBase64());
        } else {
            resultJson = "{\"result\": \"Image not found\"}";
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @GET
    @Path("/delete{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage(@PathParam("imageId") int imageId) {
        Storage storage = Storage.getInstance();
        Image image = storage.getImage(imageId);
        String result = null;
        if (image != null) {
            storage.deleteImage(imageId);
            result = String.format("{\"result\": \"Image id=%s was successfully removed\" }", image.getImageId());
        } else {
            result = "{\"result\":\"Image not found\"}";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/deleteAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage() {
        Storage storage = Storage.getInstance();
        storage.wipeAll();
        String result = "{\"result\":\"Images wiped successfully\"}";
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/getAllImages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllImages() {
        Storage storage = Storage.getInstance();
        Set<Integer> images = storage.getAllImages();
        String result = null;
        if (!images.isEmpty() || images != null) {
            result = ImageUtil.toJson(images);
        } else {
            result = "{\"result\":\"No images here ;(\"}";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/show/{imageId}")
    @Produces(MediaType.TEXT_HTML)
    public String showImage(@PathParam("imageId") int imageId) {
        Storage storage = Storage.getInstance();
        Image image = storage.getImage(imageId);
        String base64 = image.getBase64();
        return templateBuilder(base64);
    }

    String templateBuilder(String base64) {
        String resultHtml = "<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Getting Started: Serving Web Content</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <p>Image</p>\n" +
                "    <img src=\"data:image/png;base64," + base64 + "\" alt=\"Image\" />\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return resultHtml;
    }

}
