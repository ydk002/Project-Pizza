import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

class Corn extends PizzaIngredient {
  private final BufferedImage img;

  public Corn(Pizza pizza) {
    super(pizza);
    try {
      img =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/corn.png")));
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
    return super.getPrice() + 1;
  }

  @Override
  public String getIngredients() {
    return super.getIngredients() + ", Corn";
  }
}