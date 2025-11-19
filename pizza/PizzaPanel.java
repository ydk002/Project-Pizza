import java.awt.*;
import javax.swing.JPanel;

class PizzaPanel extends JPanel {
  private Pizza pizza;

  public PizzaPanel(Pizza pizza) {
    this.pizza = pizza;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    pizza.bake(g);
  }

  public Pizza getPizza() {
    return pizza;
  }


  public void setPizza(Pizza pizza) {
    this.pizza = pizza;
  }
}
