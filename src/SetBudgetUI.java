import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class SetBudgetUI extends JFrame {
    JTextField budget_amount;
    JLabel l1;
    public SetBudgetUI(String username) {
        //Frame Init
        setTitle("Set Budget");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(250,260);
        setLocation(600,350);

        setLayout(null);

        // LABELS
        l1 = new JLabel("Monthly Budget Target");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l1.setBounds(40,20,200,30);
        add(l1);

        // TEXT FIELDS
        budget_amount = new JTextField();
        budget_amount.setBounds(40,60,150,30);
        add(budget_amount);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        setBudgetButton.setBounds(40, 130, 150, 40);
        setBudgetButton.setBackground(new Color(237, 255, 0, 189));
        setBudgetButton.setForeground(Color.BLACK);
        add(setBudgetButton);

        setBudgetButton.addActionListener(e -> {
                    setBudget(budget_amount.getText(), username);
                    setVisible(false);
                    new HomePage(username);
                }
        );

    }

    public void setBudget(String budget_amount, String username) {
        HttpURLConnection connection = null;

        String urlParameters = "{\n" +
                "   \"budget_amount\": \""+budget_amount+"\",\n" +
                "   \"username\": \""+username+"\"\n" +
                "}";

        try {
            //Create connection
            URL url = new URL("http://localhost:3001/setBudget");
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

        JOptionPane.showMessageDialog(null, "MONTHLY BUDGET SET SUCCESSFULLY!");
    }
}

