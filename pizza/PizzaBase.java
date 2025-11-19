import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class PizzaBase implements Pizza {
  private final BufferedImage img;

  public PizzaBase() {
    try {
      img =  ImageIO.read(requireNonNull(getClass().getResourceAsStream("img/pizza_base.png")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void bake(Graphics g) {
    g.drawImage(img, 0, 0, null);
  }

  public int getPrice() {
    return 5;
  }

  public String getIngredients() {
    return "Tomato Sauce, Cheese";
  }
}
