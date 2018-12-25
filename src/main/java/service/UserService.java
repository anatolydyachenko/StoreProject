package service;

import app.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/auth")
public class UserService extends BaseService {

    @POST
    @Path("register")
    public Response register(String credentialsJson) {
        User user = gson.fromJson(credentialsJson, User.class);

        if (userController.userExists(user)) {
            return Response.status(409).entity("User with email " + user.getEmail() + " already exists.").build();
        } else {
            userController.addUser(user);
            return Response.status(200).entity("User with email " + user.getEmail() + " is created.").build();
        }
    }

    @POST
    @Path("login")
    public Response login(final @Context HttpServletRequest request, String credentialsJson) {
        User user = gson.fromJson(credentialsJson, User.class);

        if (userController.userExists(user)) {
            if (userController.login(user)) {
                return Response.status(200).entity("User with email " + user.getEmail() + " logged in successfully.\n" +
                        "SessionId = " + request.getSession().getId()).build();
            } else {
                return Response.status(401).entity("Incorrect password for user with email " + user.getEmail()).build();
            }

        } else {
            return Response.status(401).entity("User with email " + user.getEmail() + " is not registered.").build();
        }
    }

//    @GET
//    @Path("is_session_valid")
//    public Response is_session_valid(final @Context HttpServletRequest request) {
//
//        return Response.status(200).entity(String.valueOf(request.isRequestedSessionIdValid())).build();
//    }
//
//    @GET
//    @Path("get_session")
//    public Response getSession(final @Context HttpServletRequest request) {
//        String lastTime = new Timestamp(request.getSession().getCreationTime()).toString();
//        return Response.status(200).entity(request.getSession().getId() + " " + lastTime).build();
//    }

//    @GET
//    @Path("login/{sessionId}")
//    public Response checkSession(@PathParam("sessionId") String sessionId, final @Context HttpServletRequest request) {
//        Boolean validSession = false;
//        if (request.getSession(false) != null && request.getSession(false).getId().equals(sessionId)) {
//            validSession = true;
//        }
//        return Response.status(200).entity(validSession.toString()).build();
//    }
}
