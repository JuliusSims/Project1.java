import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
//import java.util.HashMap;

;// Set your daily calorie limit here
public class CalorieTracker extends JFrame 
{
    private Map<String, Integer> foodData = new HashMap<>();
    

    private JTextField foodTextField;
    private JTextField calorieTextField;
    private JLabel warningLabel;
    private JTextArea logTextArea;

    public CalorieTracker() 
    {
        setTitle("Calorie Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createComponents();
        addComponents();
        attachListeners();

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void createComponents() 
    {
        foodTextField = new JTextField();
        calorieTextField = new JTextField();
        warningLabel = new JLabel();
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setVisible(false);
        
    }

    private void addComponents() 
    {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Food Or Beaverage:"));
        inputPanel.add(foodTextField);
        inputPanel.add(new JLabel("Calories:"));
        inputPanel.add(calorieTextField);
        inputPanel.add(new JLabel(""));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel warningPanel = new JPanel(new FlowLayout());
        warningPanel.add(warningLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(warningPanel, BorderLayout.SOUTH);
        add(new JScrollPane(logTextArea), BorderLayout.SOUTH);
    }

    private void attachListeners() 
    {
        foodTextField.addActionListener(e -> calorieTextField.requestFocus());
        calorieTextField.addActionListener(e -> logFood());

        // Log Food Button Action
        JButton logButton = new JButton("Log Victuals");
        JButton showButton = new JButton ("Show Entry");
        logButton.addActionListener(e -> logFood());
        
        showButton.addActionListener(e -> showEntry());
       ((JPanel) getContentPane().getComponent(1)).add(logButton);
       ((JPanel) getContentPane().getComponent(1)).add(showButton);// Add the button to the buttonPanel
    }


    private void showEntry()
    {
    	logTextArea.setVisible(true);
    } 
    private void logFood() 
    {
        String food = foodTextField.getText().toLowerCase();
        int calories = 0;
        // to see if the user actually put integers for calories
        
        try 
        {
            calories = Integer.parseInt(calorieTextField.getText());
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for calories.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //water is 0 calories
        if(food.contains("Water") ==true || food.contains("water") == true)
        {
        	calories =0;
        }
        	
        logTextArea.append(food + ": " + calories + " calories\n");
        foodData.put(food, calories);
        int totalCalories = foodData.values().stream().mapToInt(Integer::intValue).sum();
     // Check if daily calorie limit is exceeded
        if (totalCalories > dailyCalorieLimit) 
        {
            JOptionPane.showMessageDialog(this, "You have exceeded your calorie limit");

        } 
        else 
        {	
        	int howManyCalories = dailyCalorieLimit - totalCalories;
        	JOptionPane.showMessageDialog(this, "Calorie Linit: " + howManyCalories);
        }
            
        foodTextField.setText("");
        calorieTextField.setText("");
        foodTextField.requestFocus();
    }

    static int dailyCalorieLimit;

    public static void main(String[] args) 
    { Scanner userInput = new Scanner(System.in);
    	String StringOutput;
    	 System.out.print("Enter the calorie daily limit:"); // ask the user for their input
 	    StringOutput = userInput.nextLine(); //gets the userinput before the code starts.
    	
 	    dailyCalorieLimit = Integer.parseInt(StringOutput);
 	    
        SwingUtilities.invokeLater(() -> new CalorieTracker());
    }
    }
    
