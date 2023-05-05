import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class LoginPage extends JFrame {
    JLabel l1,l2;
    JTextField username;
    JPasswordField password;
    JButton b1,b2;

    public LoginPage() {
        //Frame Init
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(550,250);
        setLocation(600,350);

        setLayout(null);

        l1 = new JLabel("Username:");
        l1.setBounds(40,20,100,30);
        add(l1);

        l2 = new JLabel("Password:");
        l2.setBounds(40,70,100,30);
        add(l2);

        username = new JTextField();
        username.setBounds(150,20,150,30);
        add(username);

        password = new JPasswordField();
        password.setBounds(150,70,150,30);
        add(password);

        ImageIcon i1 = new ImageIcon("II_logo.png");
        Image i2 = i1.getImage().getScaledInstance(250,200,Image.SCALE_DEFAULT);
        ImageIcon i3 =  new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(350,10,150,150);
        add(l3);


        b1 = new JButton("Login");
        b1.setBounds(40,140,120,30);
        b1.setFont(new Font("serif",Font.BOLD,15));
        b1.addActionListener(e1 -> {
           if(loginUser("http://localhost:3001/loginUser", "{\n" +
                    " \"username\":\""+ username.getText() +"\",\n" +
                    " \"password\":\""+ password.getText() +"\"\n" +
                    "}")) {
               setVisible(false);
               new HomePage(username.getText());
           } else {
               username.setText("");
               password.setText("");
           }
        });
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        add(b1);

        b2=new JButton("Sign Up");
        b2.setBounds(180,140,120,30);
        b2.setFont(new Font("serif",Font.BOLD,15));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        add(b2);

        b2.addActionListener(e2 -> {
            try {
                setVisible(false);
                new SignUpPage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public static boolean loginUser(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
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
            rd.close();

            System.out.println(response);

            if(response.toString().charAt(16) == 'F') {
                JOptionPane.showMessageDialog(null, "WELCOME BACK!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "User Not Found", "Login Unsuccessful", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }
}
