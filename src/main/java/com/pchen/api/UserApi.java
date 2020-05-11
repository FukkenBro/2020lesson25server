package com.pchen.api;

import com.pchen.model.User;
import com.pchen.util.Storage;
import com.pchen.util.UserUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/user")
public class UserApi {

    @GET
    @Path("/not-found")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTestUserNotFound() {
        String resultJson = "{}";
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(resultJson)
                .header("customHeader", "TEST")
                .build();
    }

    @POST
    @Path("/newUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserJson(String body) {
        User user = UserUtil.fromJson(body);
        Storage storage = Storage.getInstance();
        boolean createStatus = storage.addUser(user);
        String resultJson = null;
        if (createStatus) {
            resultJson = String.format("{\"result\": \"User %s id=%s has been created\"}", user.getName(), user.getUserId());
        } else {
            resultJson = "User already exists";
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userID") int userID) {
        Storage storage = Storage.getInstance();
        User user = storage.getUser(userID);
        String result = null;
        if (user != null) {
            result = UserUtil.toJson(user);
        } else {
            result = "User not found";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserJson(String body) {
        User user = UserUtil.fromJson(body);
        Storage storage = Storage.getInstance();
        Boolean updateStatus = storage.updateUser(user);
        String resultJson = null;
        if (updateStatus) {
            resultJson = String.format("{\"result\": User %s id=%s was successfully updated }", user.getName(), user.getUserId());
        } else {
            resultJson = "User not found";
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserJson(String body) {
        User user = UserUtil.fromJson(body);
        int targetId = user.getUserId();
        Storage storage = Storage.getInstance();
        Boolean updateStatus = storage.deleteUser(user);
        String resultJson = null;
        if (updateStatus) {
            resultJson = String.format("{\"result\": User %s id=%s was successfully removed }", user.getName(), user.getUserId());
        } else {
            resultJson = "User not found";
        }
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }

    @GET
    @Path("/getAllUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        Storage storage = Storage.getInstance();
        ArrayList<User> users = storage.getAllUsers();
        String result = null;
        if (!users.isEmpty() || users != null) {
            result = UserUtil.toJson(users);
        } else {
            result = "User not found";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

}
