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
  private final List<Pizza> orderHistory = new ArrayList<>();

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
    JCheckBoxMenuItem pineappleCheckBox = new JCheckBoxMenuItem("Pineapple");
    JCheckBoxMenuItem chickenCheckBox = new JCheckBoxMenuItem("Chicken");
    JCheckBoxMenuItem hamCheckBox = new JCheckBoxMenuItem("Ham");
    JCheckBoxMenuItem greenPepperCheckBox = new JCheckBoxMenuItem("Green Pepper");
    JCheckBoxMenuItem sausageCheckBox = new JCheckBoxMenuItem("Sausage");
    JCheckBoxMenuItem extraCheeseCheckBox = new JCheckBoxMenuItem("Extra Cheese");
    JCheckBoxMenuItem baconCheckBox = new JCheckBoxMenuItem("Bacon");
    JCheckBoxMenuItem onionCheckBox = new JCheckBoxMenuItem("Onion");

    oliveCheckBox.addActionListener(new ToppingMenuItemListener("Olive"));
    salamiCheckBox.addActionListener(new ToppingMenuItemListener("Salami"));
    mushroomCheckBox.addActionListener(new ToppingMenuItemListener("Mushroom"));
    tomatoCheckBox.addActionListener(new ToppingMenuItemListener("Tomato"));
    cornCheckBox.addActionListener(new ToppingMenuItemListener("Corn"));
    pineappleCheckBox.addActionListener(new ToppingMenuItemListener("Pineapple"));
    chickenCheckBox.addActionListener(new ToppingMenuItemListener("Chicken"));
    hamCheckBox.addActionListener(new ToppingMenuItemListener("Ham"));
    greenPepperCheckBox.addActionListener(new ToppingMenuItemListener("Green Pepper"));
    sausageCheckBox.addActionListener(new ToppingMenuItemListener("Sausage"));
    extraCheeseCheckBox.addActionListener(new ToppingMenuItemListener("Extra Cheese"));
    baconCheckBox.addActionListener(new ToppingMenuItemListener("Bacon"));
    onionCheckBox.addActionListener(new ToppingMenuItemListener("Onion"));

    toppingsMenu.add(oliveCheckBox);
    toppingsMenu.add(salamiCheckBox);
    toppingsMenu.add(mushroomCheckBox);
    toppingsMenu.add(tomatoCheckBox);
    toppingsMenu.add(cornCheckBox);
    toppingsMenu.add(pineappleCheckBox);
    toppingsMenu.add(chickenCheckBox);
    toppingsMenu.add(hamCheckBox);
    toppingsMenu.add(greenPepperCheckBox);
    toppingsMenu.add(sausageCheckBox);
    toppingsMenu.add(extraCheeseCheckBox);
    toppingsMenu.add(baconCheckBox);
    toppingsMenu.add(onionCheckBox);

    menuBar.add(fileMenu);
    menuBar.add(toppingsMenu);

    saveItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                savePizza();
            } catch (PizzaException ex) {
                  // 2. CATCH THE EXCEPTION (Checklist Item)
                  // Show a popup with the error message ("You cannot save a pizza...")
                  JOptionPane.showMessageDialog(PizzaFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
              }
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
    private void savePizza() throws PizzaException {

      if (selectedToppings.isEmpty()) {
          throw new PizzaException("You cannot save a pizza with no toppings!");
      }

    JFileChooser fileChooser = new JFileChooser(new File("."));

    int result = fileChooser.showSaveDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String topping : selectedToppings) {
          writer.write(topping);
          writer.newLine();
        }
          orderHistory.add(decoratedPizza);
          System.out.println("Order history size: " + orderHistory.size());
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

                    //Using .equals()
                    // This prevents loading blank lines
                    if (!line.trim().equals("")) {
                        selectedToppings.add(line);
                    }
                }

                decoratePizzaWithToppings();
                updatePizzaInfo();

                // Using overload method
                logAction("Pizza loaded from file");

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
          case "Pineapple":
              decoratedPizza = new Pineapple(decoratedPizza);
              break;
          case "Chicken":
              decoratedPizza = new Chicken(decoratedPizza);
              break;
          case "Ham":
              decoratedPizza = new Ham(decoratedPizza);
              break;
          case "Green Pepper":
              decoratedPizza = new Greenpeppers(decoratedPizza);
              break;
          case "Sausage":
              decoratedPizza = new Sausage(decoratedPizza);
              break;
          case "Extra Cheese":
              decoratedPizza = new Extracheese(decoratedPizza);
              break;
          case "Bacon":
              decoratedPizza = new Bacon(decoratedPizza);
              break;
          case "Onion":
              decoratedPizza = new Onions(decoratedPizza);
              break;
      }
    }

    panel.setPizza(decoratedPizza);
    panel.repaint();
  }
    // Overloaded Method #1
    private void logAction(String message) {
        System.out.println("LOG: " + message);
    }

    // Overloaded Method #2
    private void logAction(String message, int value) {
        System.out.println("LOG: " + message + " | Value: " + value);
    }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(PizzaFrame::new);
  }
}

