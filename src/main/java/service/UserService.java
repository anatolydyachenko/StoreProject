package service;

import app.model.User;
import com.google.gson.JsonObject;
import com.lambdaworks.crypto.SCryptUtil;
import db.requests.UserDB;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

import static app.Helper.parseToJsonObject;

@Path("/session")
public class UserService extends BaseService{
    @POST
    @Path("register")
    public Response register(String request) {
        JsonObject requestJson = parseToJsonObject(request);
        String email = requestJson.get("email").getAsString();
        String password = requestJson.get("password").getAsString();

//        boolean userExists = usersRegistered.stream().anyMatch(it -> it.getEmail().equals(email));
        boolean userExists = UserDB.userExists(email);
        if (userExists) {
            return Response.status(409).entity("User with email " + email + " already exists.").build();
        }
        else {
//            usersRegistered.add(new User(email, password));
            return Response.status(200).entity("User with email " + email + " is created.").build();
        }
    }

//    @POST
//    @Path("login")
//    public Response login(final @Context HttpServletRequest request, String credentials) {
//        JsonObject loginJson = parseToJsonObject(credentials);
//        String email = loginJson.get("email").getAsString();
//        String password = loginJson.get("password").getAsString();
//
//        User userFound = usersRegistered.stream().filter(it -> it.getEmail().equals(email)).findFirst().orElse(null);
//        if (userFound != null) {
//            if (SCryptUtil.check(password, userFound.getPasswordHash())) {
//                userCarts.stream()
//                        .filter(it -> it.getUser().equals(userFound))
//                        .findFirst().orElse(null);
//                return Response.status(200).entity("User with email " + email + " logged in successfully.\n" +
//                        "SessionId = " + request.getSession().getId()).build();
//            } else {
//                return Response.status(401).entity("Incorrect password for user with email " + email).build();
//            }
//
//        } else {
//            return Response.status(401).entity("User with email " + email + " is not registered.").build();
//        }
//    }

    @GET
    @Path("is_session_valid")
    public Response is_session_valid(final @Context HttpServletRequest request) {

        return Response.status(200).entity(String.valueOf(request.isRequestedSessionIdValid())).build();
    }

    @GET
    @Path("get_session")
    public Response getSession(final @Context HttpServletRequest request) {
        String lastTime = new Timestamp(request.getSession().getCreationTime()).toString();
        return Response.status(200).entity(request.getSession().getId() + " " + lastTime).build();
    }

    @GET
    @Path("login/{sessionId}")
    public Response checkSession(@PathParam("sessionId") String sessionId, final @Context HttpServletRequest request) {
        Boolean validSession = false;
        if (request.getSession(false) != null && request.getSession(false).getId().equals(sessionId)) {
            validSession = true;
        }
        return Response.status(200).entity(validSession.toString()).build();
    }
}
