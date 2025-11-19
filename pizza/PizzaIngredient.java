import java.awt.*;

abstract class PizzaIngredient implements Pizza {
  private final Pizza pizza;

  public PizzaIngredient(Pizza pizza) {
    this.pizza = pizza;
  }

  public void bake(Graphics g) {
    pizza.bake(g);
  }

  public int getPrice() {
    return pizza.getPrice();
  }

  public String getIngredients() {
    return pizza.getIngredients();
  }
}