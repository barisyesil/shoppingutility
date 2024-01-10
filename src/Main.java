
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ShoppingUtility {

    private ArrayList<Grocery> shoppingList;
    private ArrayList<Grocery> availableProducts;
    private String shoppingListName;
    private ShoppingListManager shoppingListManager;

    public ShoppingUtility(String shoppingListName) {
        this.shoppingListName = shoppingListName;
        shoppingList = new ArrayList<>();
        availableProducts = new ArrayList<>();
        initializeProducts();
        shoppingListManager = new ShoppingListManager(shoppingListName);
    }

    private void initializeProducts() {
        String projectDirectory = System.getProperty("user.dir");
        String filePath = projectDirectory +"/database"+ "/database.txt";
        Grocery[] groceries = Grocery.loadGroceriesFromFile(filePath);
        if (groceries != null) {
            for (Grocery grocery : groceries) {
                availableProducts.add(grocery);
            }
        } else {
            System.out.println("Failed to load groceries from the file.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Shopping Utility!");

        System.out.println("1. Create a new shopping list");
        System.out.println("2. Load an existing shopping list");
        System.out.print("Enter your choice: ");
        int listChoice = scanner.nextInt();

        String shoppingListName;
        ShoppingUtility shoppingUtility;

        if (listChoice == 1) {
            System.out.println("Enter the name of the new shopping list:");
            scanner.nextLine();
            shoppingListName = scanner.nextLine();
            shoppingUtility = new ShoppingUtility(shoppingListName);
        } else if (listChoice == 2) {
            System.out.println("Select an existing shopping list:");
            String[] savedLists = ShoppingListManager.getSavedShoppingLists();
            for (int i = 0; i < savedLists.length; i++) {
                System.out.println((i + 1) + ". " + savedLists[i]);
            }
            System.out.print("Enter the number of the list you want to load: ");
            int loadChoice = scanner.nextInt();

            if (loadChoice >= 1 && loadChoice <= savedLists.length) {
                shoppingListName = savedLists[loadChoice - 1];
                shoppingUtility = new ShoppingUtility(shoppingListName);
                shoppingUtility.loadShoppingList();
                System.out.println("Loaded shopping list: " + shoppingListName);
            } else {
                System.out.println("Invalid choice. Exiting...");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Invalid choice. Exiting...");
            scanner.close();
            return;
        }



        int choice;
        do {
            System.out.println("\nShopping Utility Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Show Shopping List");
            System.out.println("4. Save Shopping List");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    shoppingUtility.addProduct();
                    break;
                case 2:
                    shoppingUtility.removeProduct();
                    break;
                case 3:
                    shoppingUtility.showShoppingList();
                    break;
                case 4:
                    shoppingUtility.saveShoppingList();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    public void saveShoppingList() {
        shoppingListManager.saveShoppingList(shoppingList);
    }

    private Grocery findProductByName(String productName) {
        for (Grocery product : availableProducts) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
    public void addProductDB() {
        try {
            String projectDirectory = System.getProperty("user.dir");
            String filePath = projectDirectory +"/database"+ "/database.txt";

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            int numGroceries = Integer.parseInt(reader.readLine());
            Grocery[] groceries = new Grocery[numGroceries];
            for (int i = 0; i < numGroceries; i++) {
                String line = reader.readLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String description = parts[1];
                double price = Double.parseDouble(parts[2]);
                groceries[i] = new Grocery(name, description, price);
            }
            reader.close();


            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.valueOf(numGroceries + 1));
            writer.newLine();
            for (Grocery grocery : groceries) {
                writer.write(grocery.getName() + "," + grocery.getDescription() + "," + grocery.getPrice());
                writer.newLine();
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the product:");
            String name = scanner.nextLine();
            System.out.println("Enter the description of the product:");
            String description = scanner.nextLine();
            System.out.println("Enter the price of the product:");
            double price = scanner.nextDouble();
            writer.write(name + "," + description + "," + price);
            writer.newLine();
            writer.close();

            Grocery newProduct = new Grocery(name, description, price);
            availableProducts.add(newProduct);
            System.out.println("Product added successfully.");
            shoppingList.add(newProduct);
            System.out.println("Product added to the shopping list.");


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to add the product to the file.");
        }

    }
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product you want to add: ");
        String productName = scanner.nextLine();
        Grocery productToAdd = findProductByName(productName);
        if (productToAdd == null) {
            System.out.println("Product not found in the database. Please add the product first.");
            addProductDB();
        } else {
            shoppingList.add(productToAdd);
            System.out.println("Product added to the shopping list.");
            System.out.println(productToAdd);
        }

    }
    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product you want to remove: ");
        String productName = scanner.nextLine();
        Grocery productToRemove = null;
        for (Grocery product : shoppingList) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove == null) {
            System.out.println("Product not found in the shopping list.");
        } else {
            shoppingList.remove(productToRemove);
            System.out.println("Product removed from the shopping list.");
        }
    }
    public void saveShoppingListToManager() {
        shoppingListManager.saveShoppingList(shoppingList);
    }

    public void loadShoppingList() {
        List<Grocery> loadedList = shoppingListManager.loadShoppingList();
        if (loadedList != null) {
            shoppingList.clear();
            shoppingList.addAll(loadedList);
            System.out.println("Shopping list loaded successfully.");


            System.out.println("LOADED SHOPPING LIST:");
            System.out.println("----------------------------------------------------------------------------------");
            for (Grocery product : shoppingList) {
                System.out.println(product);
            }
            System.out.println("-----------------------------------------------------------------------------------");
        } else {
            System.out.println("Failed to load the shopping list.");
        }
    }

    public void showShoppingList() {
        if (shoppingList.isEmpty()) {
            System.out.println("Your shopping list is empty.");
        } else {
            double totalPrice = 0.0;
            System.out.println("YOUR SHOPPING LIST:");
            System.out.println("--------------------------------------------------------------------------------");
            for (Grocery product : shoppingList) {
                System.out.println(product);
                totalPrice += product.getPrice();
            }
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Total Price: " + totalPrice);
        }
    }}