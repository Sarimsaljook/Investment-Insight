import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage extends JFrame {
    public HomePage(String username) {
        //Frame Init
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(810, 680);
        setLocation(400, 50);

        setLayout(null);

        JLabel label1 = new JLabel("My Monthly Expenses: ");
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        label1.setBounds(45, 15, 400, 100);
        add(label1);

        // Create the JTable with 3 columns
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Expense", "Amount", "Date"}, 0);
        JTable jTable = new JTable(model);

        JSONArray userExpenses = getUserExpenses(username);

        // Populate the JTable with data from the API
        for (Object userExpense : userExpenses) {
            JSONObject jsonObj = (JSONObject) userExpense;
            String title = (String) jsonObj.get("expense_title");
            String amount = (String) jsonObj.get("expense_amount");
            String date = (String) jsonObj.get("expense_date");
            model.addRow(new Object[]{title, amount, date});
        }

        // Add the JTable to the JFrame
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(45, 100, 700, 300);
        add(scrollPane, BorderLayout.CENTER);

        JButton addExpenseButton = new JButton("Add Expense +");
        addExpenseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        addExpenseButton.setBounds(45, 425, 180, 50);
        addExpenseButton.setBackground(new Color(0, 173, 139, 141));
        addExpenseButton.setForeground(Color.white);
        add(addExpenseButton);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        setBudgetButton.setBounds(45, 500, 180, 50);
        setBudgetButton.setBackground(new Color(224, 21, 87, 229));
        setBudgetButton.setForeground(Color.white);
        add(setBudgetButton);

        JButton removeExpenseButton = new JButton("Remove Expense X");
        removeExpenseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        removeExpenseButton.setBounds(45, 575, 180, 50);
        removeExpenseButton.setBackground(new Color(185, 71, 0, 229));
        removeExpenseButton.setForeground(Color.white);
        add(removeExpenseButton);

        addExpenseButton.addActionListener(e -> {
            setVisible(false);
            new AddExpenseUI(username);
        });

        setBudgetButton.addActionListener(e2 -> {
            setVisible(false);
            new SetBudgetUI(username);
        });

        removeExpenseButton.addActionListener(e3 -> {
            setVisible(false);
            new DeleteExpenseUI(username);
        });

        JLabel label2 = new JLabel("This Month's Budget: " + getUserBudget(username));
        label2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        label2.setBounds(340, 415, 400, 100);
        add(label2);

        int columnIndex = 1; // Specify the column index to sum (starting from 0)
        double sum = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            String value = model.getValueAt(i, columnIndex).toString();
            value = value.replace("$", ""); // Remove dollar sign
            sum += Double.parseDouble(value); // Convert to double and add to sum
        }

        System.out.println("Sum of budget column = $" + sum);

        String userBudget = getUserBudget(username);
        userBudget = userBudget.replace("$", "");
        userBudget = userBudget.replace(",", "");

        boolean overBudget = (sum > Double.parseDouble(userBudget));

        if (overBudget) {
            //DISPLAY OVER SPENDING WARNINGS
            JOptionPane.showMessageDialog(null, "YOU HAVE GONE OVER BUDGET FOR THIS MONTH", null, JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null, "AS A RESULT YOUR ADD EXPENSE OPTION HAS BEEN DISABLES UNTIL NEXT MONTH");
            JOptionPane.showMessageDialog(null, "THIS IS SO YOU CAN LEARN TO CONTROL YOUR SPENDING HABITS");

            //Disable Button
            addExpenseButton.setEnabled(false);
        }

        JLabel label3 = new JLabel("Current Total Expense: $" + sum);
        label3.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        label3.setBounds(340, 475, 400, 100);
        add(label3);

    }

    public JSONArray getUserExpenses(String username) {
        HttpURLConnection connection = null;
        String finalResData;

        JSONArray userExpenses = null;

        String urlParameters = "{\n" +
                "   \"user\": \""+username+"\"\n" +
                "}";

        try {
            //Create connection
            URL url = new URL("http://localhost:3001/getUserExpenses");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            finalResData = response.toString();

            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(finalResData);

            userExpenses = (JSONArray) jobj.get("User's Expenses");

            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
                System.out.println("User's Expense Data retrieved");
            }
    }
        return userExpenses;
    }

    public String getUserBudget(String username) {
        HttpURLConnection connection = null;
        String finalResData;

        String userBudget = null;

        String urlParameters = "{\n" +
                "   \"user\": \""+username+"\"\n" +
                "}";

        try {
            //Create connection
            URL url = new URL("http://localhost:3001/getUserBudget");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            finalResData = response.toString();

            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(finalResData);

            userBudget = jobj.get("User's Budget").toString();

            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
                System.out.println("User's Budget retrieved");
            }
        }
        return userBudget;
    }
}
