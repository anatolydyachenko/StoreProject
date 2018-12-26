package shop;

import org.testng.Assert;
import org.testng.annotations.Test;
import shop.app.dto.ProductDto;
import shop.controller.CartController;
import shop.db.requests.CartDB;

public class CartSmokeTest {
    private final static String TEST_SESSION = "TestSession";
    private CartController cartController = new CartController();

    @Test(expectedExceptions = Error.class, expectedExceptionsMessageRegExp = "There is not enough products in the store.")
    public void addInCartInvalid() {
        ProductDto product = new ProductDto(1, 100);

        cartController.modifyProductsInCart(product, TEST_SESSION);
    }

    @Test
    public void addInCartValid() {
        CartDB cartDB = new CartDB();
        ProductDto product = new ProductDto(1, 1);

        cartController.modifyProductsInCart(product, TEST_SESSION);
        Assert.assertNotNull(cartDB.getProductById(product.getId(), TEST_SESSION));

    }
}
