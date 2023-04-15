import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Hello and Welcome To Investment Insight!", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "Login or Sign Up if you don't have an account", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
        new LoginPage();
    }
}