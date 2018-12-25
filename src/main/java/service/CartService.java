package service;

import app.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("cart")
public class CartService extends BaseService {

    @POST
    @Path("addProduct")
    public Response addProduct(@Context HttpServletRequest request, String productJson) {
        if (request.isRequestedSessionIdValid()) {
            Product product = gson.fromJson(productJson, Product.class);

            if (cartController.modifyProductsInCart(product, request.getRequestedSessionId())) {
                return Response.status(200).entity("Product is updated (added/modified/deleted).").build();
            } else {
                return Response.status(400).entity("Products are not updated (no such amount in store).").build();
            }


        }
        return Response.status(401).entity("You are not authorized").build();
    }

    @POST
    @Path("removeProduct")
    public Response removeProduct(@Context HttpServletRequest request, Product product){
        if (request.isRequestedSessionIdValid()) {
            if(cartController.removeProduct(product, request.getRequestedSessionId())){
                return Response.status(200).entity("Product is deleted.").build();
            }
            return Response.status(400).entity("Product is NOT deleted.").build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }

    @GET
    @Path("getProducts")
    @Produces("application/json")
    public Response getProducts(@Context HttpServletRequest request) {
        if (request.isRequestedSessionIdValid()) {
            return Response.status(200).entity(cartController.getAllProducts(request.getRequestedSessionId())).build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }

    @GET
    @Path("checkout")
    public Response checkout(@Context HttpServletRequest request) {
        if (request.isRequestedSessionIdValid()) {
            if (cartController.checkout(request.getRequestedSessionId())) {
                return Response.status(200).entity("Checkout completed.").build();
            }
            return Response.status(200).entity("Checkout failed").build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }
}
