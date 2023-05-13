import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteExpenseUI extends JFrame {
    JTextField expense_title;
    JLabel l1;

    public DeleteExpenseUI(String username) {
        //Frame Init
        setTitle("Delete Expense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(250,260);
        setLocation(600,350);

        setLayout(null);

        // LABELS
        l1 = new JLabel("Remove Expense");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l1.setBounds(40,20,200,30);
        add(l1);

        // TEXT FIELDS
        expense_title = new JTextField();
        expense_title.setBounds(40,60,150,30);
        add(expense_title);

        JButton deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        deleteExpenseButton.setBounds(40, 130, 150, 40);
        deleteExpenseButton.setBackground(new Color(90, 213, 255, 244));
        deleteExpenseButton.setForeground(Color.BLACK);
        add(deleteExpenseButton);

        deleteExpenseButton.addActionListener(e -> {
                    deleteExpense(expense_title.getText());
                    setVisible(false);
                    new HomePage(username);
                }
        );

    }

    public void deleteExpense(String expense_title) {
        HttpURLConnection connection = null;

        String urlParameters = "{\n" +
                "   \"expense_title\": \""+expense_title+"\"\n" +
                "}";

        try {
            //Create connection
            URL url = new URL("http://localhost:3001/deleteExpense");
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

        JOptionPane.showMessageDialog(null, "EXPENSE REMOVED SUCCESSFULLY!");
    }

}
