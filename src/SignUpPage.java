import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

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

}
