import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Represents a sign-in window for the main player to enter their username.
 * It extends JFrame and provides a text field for entering the username and a button to proceed.
 */
public class SignIn extends JFrame {
    private JPanel panel1;
    public JPanel panelSignIn;
    private JLabel enterUser;
    public JTextField usernameText;
    private JButton enter;
    private JLabel LABEL;

    public static JTextField user;
    /**
     * Constructs a SignIn window.
     */
    public SignIn() {

        // If enter button is selected, the main menu will show up and the username typed in will be logged in
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                MainMenu mainMenu = new MainMenu();
                mainMenu.userID.setText(usernameText.getText());

                mainMenu.setContentPane(mainMenu.panelMainMenu);
                mainMenu.setTitle("Main Menu");
                mainMenu.setSize(500, 500);
                mainMenu.setVisible(true);
                mainMenu.setDefaultCloseOperation(3);

            }
        });

        user = usernameText;    // Static variable user is set, corresponding to the logged in userID, to be accessed from other screens

        // If enter key is pressed while typing username, the main menu will be displayed with what has been typed as the username
        usernameText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    MainMenu mainMenu = new MainMenu();
                    mainMenu.userID.setText(usernameText.getText());

                    mainMenu.setContentPane(mainMenu.panelMainMenu);
                    mainMenu.setTitle("Main Menu");
                    mainMenu.setSize(500, 500);
                    mainMenu.setVisible(true);
                    mainMenu.setDefaultCloseOperation(3);

                }
        });
    }

    /**
     * Main method to create and display the sign-in screen.
     */
    public static void main(String[] args) {
        SignIn signIn = new SignIn();
        signIn.setContentPane(signIn.panelSignIn);
        signIn.setTitle("Sign In");
        signIn.setSize(500, 500);
        signIn.setVisible(true);
        signIn.setDefaultCloseOperation(3);
    }
}
