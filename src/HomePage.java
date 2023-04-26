import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage() {
        //Frame Init
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setIconImage(new ImageIcon("II_logo.png").getImage());
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);
        setSize(810,600);
        setLocation(400,50);

        setLayout(null);
    }
}
