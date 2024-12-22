import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Represents a window for entering a password.
 * It extends JFrame and provides a password field and a button for exiting the window.
 * @author Ricardo Veras
 */
public class Password extends JFrame {
    private JPanel panel1;
    private JPasswordField passwordField;
    public JPanel panelPassword;
    private JButton exitButton;
    /**
     * Constructs a Password object with a specified parameter.
     * @param i The parameter to determine the type of password to be set.
     */
    public Password(int i) {
        if (i == 1) passwordField.setText("instructor");
        if (i == 2) passwordField.setText("developer");

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String password = new String(passwordField.getPassword());
                if ("instructor".equals(password)) {

                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new Instructor().setVisible(true);
                        }
                    });
                } else if ("developer".equals(password)) {

                    dispose();
                    SwingUtilities.invokeLater(() -> new Developer().setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(panelPassword, "Incorrect Password. Try again.");
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
