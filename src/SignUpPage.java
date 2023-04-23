import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Random;

public class SignUpPage extends JFrame {
    JLabel l1,l2, l3, l4, l5;
    JTextField username;
    JPasswordField password;
    JTextField name;
    JTextField email;
    JTextField phoneNumber;
    JButton b1,b2;

    public SignUpPage() throws ParseException {
        //Frame Init
        setTitle("Sign Up");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(610,400);
        setLocation(600,350);

        setLayout(null);

        // LABELS

        l1 = new JLabel("Username:");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l1.setBounds(40,20,100,30);
        add(l1);

        l2 = new JLabel("Password:");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l2.setBounds(40,70,100,30);
        add(l2);

        l3 = new JLabel("Name:");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l3.setBounds(40,120,100,30);
        add(l3);

        l4 = new JLabel("Email:");
        l4.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l4.setBounds(40,170,100,30);
        add(l4);

        l5 = new JLabel("Phone No. : ");
        l5.setFont(new Font("Times New Roman", Font.BOLD, 15));
        l5.setBounds(40,220,100,30);
        add(l5);

        // TEXT FIELDS

        username = new JTextField();
        username.setBounds(150,20,150,30);
        add(username);

        password = new JPasswordField();
        password.setBounds(150,70,150,30);
        add(password);

        name = new JTextField();
        name.setBounds(150,120,150,30);
        add(name);

        email = new JTextField();
        email.setBounds(150,170,150,30);
        add(email);

        phoneNumber = new JFormattedTextField(new MaskFormatter("###-###-####"));
        phoneNumber.setBounds(150,220,150,30);
        add(phoneNumber);

        ImageIcon i1 = new ImageIcon("signUpIMG.png");
        Image i2 = i1.getImage().getScaledInstance(250,200,Image.SCALE_DEFAULT);
        ImageIcon i3 =  new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(350,50,200,200);
        add(l3);


        b1 = new JButton("Sign Me Up!");
        b1.setBounds(40,290,120,30);
        b1.setFont(new Font("serif",Font.BOLD,15));
        b1.addActionListener(e1 -> {

            Random random = new Random();
            long userID = 1000000000L + (long)(random.nextDouble() * 9000000000L);

            executePost("http://localhost:3001/addUser", "{\n" +
                    " \"_id\": \""+ userID +"\",\n" +
                    " \"username\":\""+ username.getText() +"\",\n" +
                    " \"password\":\""+ password.getText() +"\",\n" +
                    " \"name\":\""+ name.getText() +"\",\n" +
                    " \"email\":\""+ email.getText() +"\",\n" +
                    " \"phone_number\":\""+ phoneNumber.getText() +"\"\n" +
                    "}");

            System.out.println("USER ADDED!");

            JOptionPane.showMessageDialog(null, "Your All Signed Up!", null, JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "How Login with your new username and password", null, JOptionPane.INFORMATION_MESSAGE);

            setVisible(false);
            new LoginPage();
        });
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        add(b1);

        b2=new JButton("Cancel");
        b2.setBounds(180,290,120,30);
        b2.setFont(new Font("serif",Font.BOLD,15));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        add(b2);

        b2.addActionListener(e2 -> setVisible(false));
    }
    public static void executePost(String targetURL, String urlParameters) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
