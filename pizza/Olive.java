import java.awt.Graphics;
import java.util.Objects;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

class Olive extends PizzaIngredient {
  private final BufferedImage img;

  public Olive(Pizza pizza) {
    super(pizza);
    try {
      img =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/olive.png")));
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
    return super.getIngredients() + ", Olive";
  }
}