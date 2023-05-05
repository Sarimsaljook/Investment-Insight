import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage(String username) {
        //Frame Init
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(810, 600);
        setLocation(400, 50);

        setLayout(null);

        JLabel label1 = new JLabel("My Monthly Expenses: ");
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        label1.setBounds(45, 15, 400, 100);
        add(label1);

        // Create the JTable with 3 columns
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Title", "Amount", "Date"}, 0);
        JTable jTable = new JTable(model);

        // Populate the JTable with data from the API
      /*  for (JSONObject jsonObj : jsonData) {
            String title = jsonObj.getString("expense_title");
            double amount = jsonObj.getDouble("expense_amount");
            String date = jsonObj.getString("expense_date");
            model.addRow(new Object[]{title, amount, date});
        } */

        // Add the JTable to the JFrame
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(45, 100, 700, 300);
        add(scrollPane, BorderLayout.CENTER);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        addExpenseButton.setBounds(45, 425, 180, 50);
        addExpenseButton.setBackground(new Color(0, 173, 139, 141));
        addExpenseButton.setForeground(Color.white);
        add(addExpenseButton);

        addExpenseButton.addActionListener(e -> {
            setVisible(false);
            new AddExpenseUI(username);
        });
    }
}
