package service;

import com.google.gson.JsonObject;
import db.requests.StoreDB;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static app.Helper.parseToJsonObject;

@Path("/store")
public class StoreService extends BaseService {

    @GET
    @Path("getProducts")
    @Produces("application/json")
    public Response getProducts(@Context HttpServletRequest request) {
        if (request.isRequestedSessionIdValid()) {
            return Response.status(200).entity(StoreDB.getAllProducts()).build();
        }
        return Response.status(401).entity("You are not authorized").build();
    }


    @POST
    @Path("addProduct")
    public Response addProduct(String products) {
        JsonObject requestJson = parseToJsonObject(products);
        String title = requestJson.get("title").getAsString();
        double price = requestJson.get("price").getAsDouble();
        int quantity = requestJson.get("quantity").getAsInt();

        try (PreparedStatement st = conn.prepareStatement("INSERT INTO PRODUCT (TITLE, PRICE, QUANTITY) VALUES (?,?,?)")) {
            st.setString(1, title);
            st.setDouble(2, price);
            st.setInt(3, quantity);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(200).entity("Product added").build();
    }

}
