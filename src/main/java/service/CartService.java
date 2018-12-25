package service;

import com.google.gson.JsonObject;
import db.requests.CartDB;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static app.Helper.parseToJsonObject;

@Path("cart")
public class CartService extends BaseService {

    @POST
    @Path("addProduct")
    public Response addProduct(@Context HttpServletRequest request, String product) {
        if (request.isRequestedSessionIdValid()) {
            boolean isAdded;

            JsonObject requestJson = parseToJsonObject(product);
            int productId = requestJson.get("id").getAsInt();
            int quantity = requestJson.get("quantity").getAsInt();

            isAdded = cartDB.modifyProductsInCart(productId, quantity, request.getRequestedSessionId());

            if (isAdded) {
                return Response.status(200).entity("Product is updated (added/modified/deleted).").build();
            } else {
                return Response.status(400).entity("Products are not updated (no such amount in store).").build();
            }


        }
        return Response.status(401).entity("You are not authorized").build();
    }

    @GET
    @Path("getProducts")
    @Produces("application/json")
    public Response getProducts(@Context HttpServletRequest request) {
        if (request.isRequestedSessionIdValid()) {
            String sessionId = request.getSession().getId();

            return Response.status(200).entity(cartDB.getAllProducts(request.getRequestedSessionId())).build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }

    @GET
    @Path("checkout")
    public Response checkout(@Context HttpServletRequest request) {
        if (request.isRequestedSessionIdValid()) {
            String sessionId = request.getRequestedSessionId();
            if (cartDB.checkout(sessionId)) {
                return Response.status(200).entity("Checkout completed.").build();
            }
            return Response.status(200).entity("Checkout failed").build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }
}
