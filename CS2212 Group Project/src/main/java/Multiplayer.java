import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Represents the Multiplayer window for selecting multiplayer game settings.
 * It extends JFrame and provides user interface elements for selecting difficulty levels.
 * @author Ty William Bryson
 */
public class Multiplayer extends JFrame {
    private JLabel pageTitle;
    private JLabel userID;
    private JButton switchUser;
    private JButton exitButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    public JPanel panelMultiplayer;
    /**
     * Constructs a Multiplayer window.
     */
    public Multiplayer() {
        userID.setText(SignIn.user.getText());      // Current username is set to userID

        // If the easy level button is selected, the next multiplayer screen is loaded with the easy setting as a parameter
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Multiplayer3 mPlayer3 = new Multiplayer3(1);
                mPlayer3.setContentPane(mPlayer3.panelMultiplayer3);
                mPlayer3.setTitle("New Game");
                mPlayer3.setSize(500, 500);
                mPlayer3.setVisible(true);
                mPlayer3.setDefaultCloseOperation(3);

            }
        });

        // If the medium level button is selected, the next multiplayer screen is loaded with the medium setting as a parameter
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Multiplayer3 mPlayer3 = new Multiplayer3(2);
                mPlayer3.setContentPane(mPlayer3.panelMultiplayer3);
                mPlayer3.setTitle("New Game");
                mPlayer3.setSize(500, 500);
                mPlayer3.setVisible(true);
                mPlayer3.setDefaultCloseOperation(3);

            }
        });

        // If the hard level button is selected, the next multiplayer screen is loaded with the hard setting as a parameter
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Multiplayer3 mPlayer3 = new Multiplayer3(3);
                mPlayer3.setContentPane(mPlayer3.panelMultiplayer3);
                mPlayer3.setTitle("New Game");
                mPlayer3.setSize(500, 500);
                mPlayer3.setVisible(true);
                mPlayer3.setDefaultCloseOperation(3);

            }
        });

        // If the exit button is selected, you are returned to the previous screen
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        // If switch user is selected, the sign in screen is loaded to sign into a new user account
        switchUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SignIn signIn = new SignIn();
                signIn.setContentPane(signIn.panelSignIn);
                signIn.setTitle("New Game");
                signIn.setSize(500, 500);
                signIn.setVisible(true);
                signIn.setDefaultCloseOperation(3);
            }
        });
    }

    /**
     * Main method to create and display a multiplayer screen for selecting difficulty levels.
     * @param args Command-line arguments.
     */    public static void main(String[] args) {

        Multiplayer mPlayer = new Multiplayer();
        mPlayer.setContentPane(mPlayer.panelMultiplayer);
        mPlayer.setTitle("New Game");
        mPlayer.setSize(500, 500);
        mPlayer.setVisible(true);
        mPlayer.setDefaultCloseOperation(3);
    }
}
