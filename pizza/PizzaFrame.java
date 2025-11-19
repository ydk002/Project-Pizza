import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class PizzaFrame extends JFrame {
  private Pizza decoratedPizza;
  private final PizzaPanel panel;
  private final List<String> selectedToppings;
  private final JLabel recipeLabel;
  private final JLabel priceLabel;

  public PizzaFrame() {
    super("PizzaFrame");

    selectedToppings = new ArrayList<>();
    decoratedPizza = new PizzaBase();
    panel = new PizzaPanel(decoratedPizza);

    createMenu();

    recipeLabel = new JLabel("Recipe: " + decoratedPizza.getIngredients());
    priceLabel = new JLabel("Price: $" + decoratedPizza.getPrice());

    updatePizzaInfo();

    JPanel infoPanel = new JPanel(new GridLayout(2, 1));
    infoPanel.add(recipeLabel);
    infoPanel.add(priceLabel);

    add(infoPanel, BorderLayout.NORTH);
    add(panel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(700, 700);
    setVisible(true);
  }

  private void createMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    JMenuItem saveItem = new JMenuItem("Save Pizza");
    JMenuItem loadItem = new JMenuItem("Load Pizza");

    fileMenu.add(saveItem);
    fileMenu.add(loadItem);

    JMenu toppingsMenu = new JMenu("Toppings");
    JCheckBoxMenuItem oliveCheckBox = new JCheckBoxMenuItem("Olive");
    JCheckBoxMenuItem salamiCheckBox = new JCheckBoxMenuItem("Salami");
    JCheckBoxMenuItem mushroomCheckBox = new JCheckBoxMenuItem("Mushroom");
    JCheckBoxMenuItem tomatoCheckBox = new JCheckBoxMenuItem("Tomato");
    JCheckBoxMenuItem cornCheckBox = new JCheckBoxMenuItem("Corn");

    oliveCheckBox.addActionListener(new ToppingMenuItemListener("Olive"));
    salamiCheckBox.addActionListener(new ToppingMenuItemListener("Salami"));
    mushroomCheckBox.addActionListener(new ToppingMenuItemListener("Mushroom"));
    tomatoCheckBox.addActionListener(new ToppingMenuItemListener("Tomato"));
    cornCheckBox.addActionListener(new ToppingMenuItemListener("Corn"));

    toppingsMenu.add(oliveCheckBox);
    toppingsMenu.add(salamiCheckBox);
    toppingsMenu.add(mushroomCheckBox);
    toppingsMenu.add(tomatoCheckBox);
    toppingsMenu.add(cornCheckBox);

    menuBar.add(fileMenu);
    menuBar.add(toppingsMenu);

    saveItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        savePizza();
      }
    });

    loadItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        loadPizza();
      }
    });

    setJMenuBar(menuBar);
  }

  private class ToppingMenuItemListener implements ActionListener {

    private final String topping;

    public ToppingMenuItemListener(String topping) {
      this.topping = topping;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JCheckBoxMenuItem sourceMenuItem = (JCheckBoxMenuItem) e.getSource();
      if (sourceMenuItem.isSelected()) {
        selectedToppings.add(topping);
      } else {
        selectedToppings.remove(topping);
      }

      decoratePizzaWithToppings();
      updatePizzaInfo();
    }
  }

  public void updatePizzaInfo() {
    recipeLabel.setText("Recipe: " + decoratedPizza.getIngredients());
    priceLabel.setText("Price: $" + decoratedPizza.getPrice());
  }
  private void savePizza() {
    JFileChooser fileChooser = new JFileChooser(new File("."));

    int result = fileChooser.showSaveDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String topping : selectedToppings) {
          writer.write(topping);
          writer.newLine();
        }
        JOptionPane.showMessageDialog(this, "Pizza saved successfully!");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void loadPizza() {
    JFileChooser fileChooser = new JFileChooser(new File("."));

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        selectedToppings.clear();
        String line;
        while ((line = reader.readLine()) != null) {
          selectedToppings.add(line);
        }

        decoratePizzaWithToppings();
        updatePizzaInfo();
        JOptionPane.showMessageDialog(this, "Pizza loaded successfully!");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void decoratePizzaWithToppings() {
    decoratedPizza = new PizzaBase();

    for (String topping : selectedToppings) {
      switch (topping) {
        case "Olive":
          decoratedPizza = new Olive(decoratedPizza);
          break;
        case "Salami":
          decoratedPizza = new Salami(decoratedPizza);
          break;
        case "Mushroom":
          decoratedPizza = new Mushroom(decoratedPizza);
          break;
        case "Tomato":
          decoratedPizza = new Tomato(decoratedPizza);
          break;
        case "Corn":
          decoratedPizza = new Corn(decoratedPizza);
          break;
      }
    }

    panel.setPizza(decoratedPizza);
    panel.repaint();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(PizzaFrame::new);
  }
}