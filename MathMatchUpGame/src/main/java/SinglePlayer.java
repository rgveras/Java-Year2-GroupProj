import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Represents a screen for selecting easy, medium, or hard levels for a single-player game.
 * It extends JFrame and provides buttons for selecting the difficulty level.
 * @author Ty Willaim Bryson
 */
public class SinglePlayer extends JFrame {
    private JLabel pageTitle;
    private JLabel userID;
    private JButton switchUser;
    private JButton exitButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    public JPanel panelSinglePlayer;
    /**
     * Constructs a SinglePlayer window.
     */
    public SinglePlayer() {
        userID.setText(SignIn.user.getText());      // Set current usernamne to label userID

        // If easy button is selected, the load game screen is loaded with a parameter 1, identifying that easy difficulty was chosen
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGame loadGame = new LoadGame(1);
                loadGame.setVisible(true);

                loadGame.setContentPane(loadGame.panelLoadGame);
                loadGame.setTitle("New Game");
                loadGame.setSize(500, 500);
                loadGame.setVisible(true);
                loadGame.setDefaultCloseOperation(3);
            }
        });

        // If medium button is selected, the load game screen is loaded with a parameter 2, identifying that medium difficulty was chosen
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGame loadGame = new LoadGame(2);
                //loadGame.setVisible(true);

                loadGame.setContentPane(loadGame.panelLoadGame);
                loadGame.setTitle("New Game");
                loadGame.setSize(500, 500);
                loadGame.setVisible(true);
                loadGame.setDefaultCloseOperation(3);
            }
        });

        // If hard button is selected, the load game screen is loaded with a parameter 3, identifying that hard difficulty was chosen
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGame loadGame = new LoadGame(3);
                loadGame.setVisible(true);

                loadGame.setContentPane(loadGame.panelLoadGame);
                loadGame.setTitle("New Game");
                loadGame.setSize(500, 500);
                loadGame.setVisible(true);
                loadGame.setDefaultCloseOperation(3);

            }
        });

        // If exit button is selected, previous screen is returned to
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
     * Main method to create and display the SinglePlayer screen.
     */
    public static void main(String[] args) {

        SinglePlayer sPlayer = new SinglePlayer();
        sPlayer.setVisible(true);

        sPlayer.setContentPane(sPlayer.panelSinglePlayer);
        sPlayer.setTitle("New Game");
        sPlayer.setSize(500, 500);
        sPlayer.setVisible(true);
        sPlayer.setDefaultCloseOperation(3);
    }
}
