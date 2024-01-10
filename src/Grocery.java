import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Grocery {
    private String name;
    private String description;
    private double price;

    public Grocery(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ": " + description + " - Price: " + price;
    }

    public static Grocery[] loadGroceriesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int numGroceries = Integer.parseInt(reader.readLine());
            Grocery[] groceries = new Grocery[numGroceries];
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String description = parts[1];
                double price = Double.parseDouble(parts[2]);
                groceries[index] = new Grocery(name, description, price);
                index++;
            }

            return groceries;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }}