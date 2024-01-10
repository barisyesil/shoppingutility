import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ShoppingUtilityTest {

    public static void main(String[] args) {
        // Create a new shopping list
        ShoppingUtility shoppingUtility = new ShoppingUtility("TestList");

        // Simulate user input to add products programmatically
        simulateAddProduct(shoppingUtility, "apple");
        simulateAddProduct(shoppingUtility, "orange");
        simulateAddProduct(shoppingUtility, "watermelon");

        // Show shopping list before removal
        System.out.println("Shopping List before removal:");
        shoppingUtility.showShoppingList();

        // Simulate user input to remove a product programmatically
        simulateRemoveProduct(shoppingUtility, "apple");

        // Show shopping list after removal
        System.out.println("Shopping List after removal:");
        shoppingUtility.showShoppingList();

        // Save shopping list
        shoppingUtility.saveShoppingList();

        // Load back the shopping list
        ShoppingUtility loadedShoppingUtility = new ShoppingUtility("TestList");
        loadedShoppingUtility.loadShoppingList();

        // Verify that the loaded shopping list is correct
        System.out.println("Loaded Shopping List:");
        loadedShoppingUtility.showShoppingList();

        // Optional: Manually check the console output for correctness
    }

    private static void simulateAddProduct(ShoppingUtility shoppingUtility, String productName) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream((productName + "\n").getBytes()));

        try {
            shoppingUtility.addProduct();
        } finally {
            System.setIn(originalIn);
        }
    }

    private static void simulateRemoveProduct(ShoppingUtility shoppingUtility, String productName) {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream((productName + "\n").getBytes()));

        try {
            shoppingUtility.removeProduct();
        } finally {
            System.setIn(originalIn);
        }
    }
}
