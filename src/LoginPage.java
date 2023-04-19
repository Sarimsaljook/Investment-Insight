import javax.swing.*;
import java.awt.*;
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
                new SignUpPage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
