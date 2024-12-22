//import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for selecting the game mode (single player or multiplayer) in a new game.
 * It extends JFrame and provides buttons for selecting between single player and multiplayer options.
 * @author Ricardo Veras
 */public class NewGame extends JFrame {
    public JPanel panelNewGame;
    private JLabel pageTitle;
    private JButton switchUser;
    private JButton exitButton;
    private JButton singleButton;
    private JButton multiButton;
    private JLabel userID;

    /**
     * Constructs a NewGame window.
     */
    public NewGame() {
        userID.setText(SignIn.user.getText());      // Current username is set to userID label

        // If single player is selected, the next single player screen options are provided in a new screen
        singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SinglePlayer sPlayer = new SinglePlayer();
                sPlayer.setContentPane(sPlayer.panelSinglePlayer);
                sPlayer.setTitle("New Game");
                sPlayer.setSize(500, 500);
                sPlayer.setVisible(true);
                sPlayer.setDefaultCloseOperation(3);
            }
        });

        // If the multiplayer button is selected, the following screens with multiplayer options are provided
        multiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Multiplayer mPlayer = new Multiplayer();
                mPlayer.setContentPane(mPlayer.panelMultiplayer);
                mPlayer.setTitle("New Game");
                mPlayer.setSize(500, 500);
                mPlayer.setVisible(true);
                mPlayer.setDefaultCloseOperation(3);

            }
        });

        // If exit button is selected, the previous screen is returned to
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        // If switch user is selected, the sign in screen is loaded to sign in to a new user account
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
     * Main method to create and display a new game screen.
     */
    public static void main(String[] args) {


        NewGame newGame = new NewGame();
        newGame.setContentPane(newGame.panelNewGame);
        newGame.setTitle("New Game");
        newGame.setSize(500, 500);
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(3);

    }

}