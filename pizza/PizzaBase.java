import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class PizzaBase implements Pizza {
    // 1. STATIC ATTRIBUTE (Checklist Item)
    // This belongs to the class, not a specific pizza. It counts ALL pizzas.
    private static int pizzasCreated = 0;

    private final BufferedImage img;
    private final int basePrice;

    // 2. OVERLOADED CONSTRUCTOR #1 (Checklist Item)
    // Default constructor: assumes price is $5
    public PizzaBase() {
        this(5); // Calls the second constructor
    }

    // 2. OVERLOADED CONSTRUCTOR #2 (Checklist Item)
    // Custom constructor: allows setting a different base price
    public PizzaBase(int basePrice) {
        this.basePrice = basePrice;
        incrementCount(); // Call our static method

        try {
            img =  ImageIO.read(requireNonNull(getClass().getResourceAsStream("img/pizza_base.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 3. STATIC METHOD (Checklist Item)
    private static void incrementCount() {
        pizzasCreated++;
        System.out.println("Total Pizzas Created So Far: " + pizzasCreated);
    }

    @Override
    public void bake(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public int getPrice() {
        return basePrice; // Returns the variable price instead of hardcoded 5
    }

    @Override
    public String getIngredients() {
        return "Tomato Sauce, Cheese";
    }

    // 4. OVERRIDDEN TOSTRING (Checklist Item)
    @Override
    public String toString() {
        return "Basic Pizza Base ($" + basePrice + ")";
    }
}
