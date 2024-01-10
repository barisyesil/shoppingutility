import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingListManager {

    private String shoppingListName;

    public ShoppingListManager(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public void saveShoppingList(List<Grocery> shoppingList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SaveFiles/" +shoppingListName + ".txt"))) {
            writer.write(shoppingList.size() + "\n");
            for (Grocery product : shoppingList) {
                writer.write(product.getName() + "," + product.getDescription() + "," + product.getPrice() + "\n");
            }
            System.out.println("Shopping list saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the shopping list.");
        }
    }

    public List<Grocery> loadShoppingList() {
        List<Grocery> loadedList = new ArrayList<>();
        String filePath = ("SaveFiles/" + shoppingListName + ".txt");

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int numProducts = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < numProducts && scanner.hasNextLine(); i++) {
                String line = scanner.nextLine();

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String description = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    loadedList.add(new Grocery(name, description, price));
                } else {
                    System.out.println("Invalid line format in the file.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error occurred while loading the shopping list.");
            e.printStackTrace();
            return null;
        }

        return loadedList;
    }




    public static String[] getSavedShoppingLists() {
        String projectDirectory = System.getProperty("user.dir");
        File saveFilesDirectory = new File(projectDirectory, "SaveFiles"); // Specify the "SaveFiles" folder
        File[] files = saveFilesDirectory.listFiles();
        List<String> shoppingLists = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String shoppingListName = file.getName().replace(".txt", "");
                    shoppingLists.add(shoppingListName);
                }
            }
        }

        return shoppingLists.toArray(new String[0]);
    }
}