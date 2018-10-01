package service;


import app.Product;
import app.Store;
import app.User;
import com.google.gson.JsonObject;
import com.lambdaworks.crypto.SCryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static app.Helper.parseToJsonObject;


@Path("/")
public class StoreService {
    private static Store store = new Store();
    private static List<User> usersRegistered = new ArrayList<>();

    static {
        Product product1 = new Product(1, "Product Name1", 10.1);
        Product product2 = new Product(2, "Product Name2", 20.0);

        store.addProduct(product1, 1);
        System.out.println(store.getAllProducts());

        store.addProduct(product2, 2);
        System.out.println(store.getAllProducts());

        store.addProduct(product1, 5);
        System.out.println(store.getAllProducts());
    }


    @GET
    @Path("getProducts")
    @Produces("application/json")
    public Response getProducts() {
        return Response.status(200).entity(store.getAllProducts()).build();
    }

    @POST
    @Path("register")
    public Response register(String request) {
        JsonObject requestJson = parseToJsonObject(request);
        String email = requestJson.get("email").getAsString();
        String password = requestJson.get("password").getAsString();

        boolean userExists = usersRegistered.stream().anyMatch(it -> it.getEmail().equals(email));
        if (userExists) {
            return Response.status(409).entity("User with email " + email + " already exists.").build();
        } else {
            usersRegistered.add(new User(email, password));
            return Response.status(200).entity("User with email " + email + " is created.").build();
        }
    }

    @POST
    @Path("login")
    public Response login(final @Context HttpServletRequest request, String credentials) {
        JsonObject loginJson = parseToJsonObject(credentials);
        String email = loginJson.get("email").getAsString();
        String password = loginJson.get("password").getAsString();

        User userFound = usersRegistered.stream().filter(it -> it.getEmail().equals(email)).findFirst().orElse(null);
        if (userFound != null) {
            if (SCryptUtil.check(password, userFound.getPasswordHash())) {
                return Response.status(200).entity("User with email " + email + " logged in successfully.\nSessionId = " + request.getSession().getId()).build();
            } else {
                return Response.status(401).entity("Incorrect password for user with email " + email).build();
            }

        } else {
            return Response.status(401).entity("User with email " + email + " is not registered.").build();
        }
    }

//    @GET
//    @Path("logindebug")
//    public Response getSession(final @Context HttpServletRequest request) {
//        String lastTime = new Timestamp(request.getSession().getCreationTime()).toString();
//        return Response.status(200).entity(request.getSession().getId() + " " + lastTime).build();
//    }
//
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
