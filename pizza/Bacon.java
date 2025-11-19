import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

// 1. Change Class Name
class Bacon extends PizzaIngredient {
    private final BufferedImage img;

    // 2. Update Constructor Name
    public Bacon(Pizza pizza) {
        super(pizza);
        try {
            // 3. Update Image Filename
            img =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/bacon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void bake(Graphics g) {
        super.bake(g);
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public int getPrice() {
        // Set your custom price (Example: +2)
        return super.getPrice() + 2;
    }

    @Override
    public String getIngredients() {
        // 4. Update Ingredient Name
        return super.getIngredients() + ", Bacon";
    }
}