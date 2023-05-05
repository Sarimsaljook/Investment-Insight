import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpenseUI extends JFrame {
    JTextField expense_title;
    JTextField expense_amount;
    JLabel l1,l2;
    public AddExpenseUI(String username) {
        //Frame Init
        setTitle("Add Expense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(370,240);
        setLocation(600,350);

        setLayout(null);

        // LABELS
        l1 = new JLabel("Expense Title:");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l1.setBounds(40,20,100,30);
        add(l1);

        l2 = new JLabel("Amount:");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l2.setBounds(40,70,100,30);
        add(l2);

        // TEXT FIELDS
        expense_title = new JTextField();
        expense_title.setBounds(150,20,150,30);
        add(expense_title);

        expense_amount = new JTextField();
        expense_amount.setBounds(150,70,150,30);
        add(expense_amount);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        addExpenseButton.setBounds(40, 130, 150, 40);
        addExpenseButton.setBackground(new Color(243, 0, 0, 141));
        addExpenseButton.setForeground(Color.white);
        add(addExpenseButton);

        addExpenseButton.addActionListener(e -> {
            addExpense(username, expense_title.getText(), expense_amount.getText());
            setVisible(false);
            new HomePage(username);
        }
        );

    }

    public void addExpense(String username, String expense_title, String expense_amount) {
        HttpURLConnection connection = null;

        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("MM/dd/yyyy");

        String expense_date = ft.format(dNow);

        String urlParameters = "{\n" +
                "   \"expense_title\":\""+expense_title+"\",\n" +
                "   \"expense_amount\":\""+expense_amount+"\",\n" +
                "   \"expense_date\":\""+expense_date+"\",\n" +
                "   \"user\":\""+username+"\"\n" +
                "}";


        try {
            //Create connection
            URL url = new URL("http://localhost:3001/addExpense");
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
            System.out.println(response);
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        JOptionPane.showMessageDialog(null, "EXPENSE ADDED SUCCESSFULLY!");
    }
}
