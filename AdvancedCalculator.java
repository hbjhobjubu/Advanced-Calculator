import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdvancedCalculator extends JFrame implements ActionListener {
    
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private boolean newCalculation = true;
    
    public AdvancedCalculator() {
        setTitle("Advanced Java Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        createAdvancedUI();
        setVisible(true);
    }
    
    private void createAdvancedUI() {
        // Create display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        
        // Button texts
        String[][] buttons = {
            {"C", "CE", "⌫", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"±", "0", ".", "="},
            {"√", "x²", "1/x", "%"}
        };
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add buttons
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton button = new JButton(buttons[i][j]);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.addActionListener(this);
                
                // Color coding for different button types
                if (buttons[i][j].matches("[÷×\\-+=]")) {
                    button.setBackground(new Color(255, 149, 0));
                    button.setForeground(Color.WHITE);
                } else if (buttons[i][j].matches("[CCE⌫]")) {
                    button.setBackground(new Color(200, 200, 200));
                } else if (buttons[i][j].matches("[√x²1/x%±]")) {
                    button.setBackground(new Color(100, 100, 100));
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(new Color(50, 50, 50));
                    button.setForeground(Color.WHITE);
                }
                
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                
                buttonPanel.add(button);
            }
        }
        
        // Set layout
        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                if (newCalculation || display.getText().equals("0")) {
                    display.setText(command);
                    newCalculation = false;
                } else {
                    display.setText(display.getText() + command);
                }
                break;
                
            case ".":
                if (newCalculation) {
                    display.setText("0.");
                    newCalculation = false;
                } else if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                break;
                
            case "C":
                display.setText("0");
                num1 = num2 = result = 0;
                newCalculation = true;
                break;
                
            case "CE":
                display.setText("0");
                newCalculation = true;
                break;
                
            case "⌫":
                if (display.getText().length() > 1) {
                    display.setText(display.getText().substring(0, display.getText().length() - 1));
                } else {
                    display.setText("0");
                    newCalculation = true;
                }
                break;
                
            case "±":
                if (!display.getText().equals("0")) {
                    double value = Double.parseDouble(display.getText());
                    value = -value;
                    display.setText(String.valueOf(value));
                }
                break;
                
            case "+": case "-": case "×": case "÷":
                if (!newCalculation) {
                    num1 = Double.parseDouble(display.getText());
                    operator = convertOperator(command);
                    newCalculation = true;
                }
                break;
                
            case "=":
                if (!newCalculation) {
                    num2 = Double.parseDouble(display.getText());
                    calculate();
                    newCalculation = true;
                }
                break;
                
            case "√":
                double sqrtValue = Double.parseDouble(display.getText());
                if (sqrtValue >= 0) {
                    result = Math.sqrt(sqrtValue);
                    display.setText(formatResult(result));
                } else {
                    display.setText("Error");
                }
                newCalculation = true;
                break;
                
            case "x²":
                double squareValue = Double.parseDouble(display.getText());
                result = squareValue * squareValue;
                display.setText(formatResult(result));
                newCalculation = true;
                break;
                
            case "1/x":
                double reciprocalValue = Double.parseDouble(display.getText());
                if (reciprocalValue != 0) {
                    result = 1 / reciprocalValue;
                    display.setText(formatResult(result));
                } else {
                    display.setText("Error");
                }
                newCalculation = true;
                break;
                
            case "%":
                double percentValue = Double.parseDouble(display.getText());
                result = percentValue / 100;
                display.setText(formatResult(result));
                newCalculation = true;
                break;
        }
    }
    
    private char convertOperator(String op) {
        switch (op) {
            case "×": return '*';
            case "÷": return '/';
            default: return op.charAt(0);
        }
    }
    
    private void calculate() {
        switch (operator) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/': 
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Error: Division by zero");
                    return;
                }
                break;
        }
        display.setText(formatResult(result));
    }
    
    private String formatResult(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdvancedCalculator());
    }
}