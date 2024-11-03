import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the LoadGame window for loading unlocked levels.
 * It extends JFrame and provides user interface for loading levels.
 * @author Ricardo Veras
 */
public class LoadGame extends JFrame {
    public JPanel panelLoadGame;
    private JLabel pageTitle;
    public JLabel userID;
    private JButton switchUser;
    private JButton exitButton;
    private JButton level1Button;
    private JButton level4button;
    private JButton level3button;
    private JButton level2button;
    private JLabel label1;

    public int difficulty;
    /**
     * Constructs a LoadGame window with the specified difficulty.
     * @param difficulty The difficulty level.
     */

    public LoadGame(int difficulty) {
        userID.setText(SignIn.user.getText());      // Set userID label to current user
        this.difficulty = difficulty;               // Set difficulty to difficulty previously selected

        // If the exit button is pressed, return to previous screen
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        // If level 1 button selected, open next screen with difficulty set at level 1
        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SinglePlayer2 splayer2 = new SinglePlayer2(difficulty, 1);
                splayer2.setVisible(true);

                splayer2.setContentPane(splayer2.panelSinglePlayer2);
                splayer2.setTitle("New Game");
                splayer2.setSize(500, 500);
                splayer2.setVisible(true);
                splayer2.setDefaultCloseOperation(3);
            }
        });

        // If level 2 button selected, open next screen with difficulty set at level 2
        level2button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SinglePlayer2 splayer2 = new SinglePlayer2(difficulty, 2);
                splayer2.setVisible(true);

                splayer2.setContentPane(splayer2.panelSinglePlayer2);
                splayer2.setTitle("New Game");
                splayer2.setSize(500, 500);
                splayer2.setVisible(true);
                splayer2.setDefaultCloseOperation(3);
            }
        });

        // If level 3 button selected, open next screen with difficulty set at level 3
        level3button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SinglePlayer2 splayer2 = new SinglePlayer2(difficulty, 1);
                splayer2.setVisible(true);

                splayer2.setContentPane(splayer2.panelSinglePlayer2);
                splayer2.setTitle("New Game");
                splayer2.setSize(500, 500);
                splayer2.setVisible(true);
                splayer2.setDefaultCloseOperation(3);            }
        });

        // If level 4 button selected, open next screen with difficulty set at level 4
        level4button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SinglePlayer2 splayer2 = new SinglePlayer2(difficulty, 1);
                splayer2.setVisible(true);

                splayer2.setContentPane(splayer2.panelSinglePlayer2);
                splayer2.setTitle("New Game");
                splayer2.setSize(500, 500);
                splayer2.setVisible(true);
                splayer2.setDefaultCloseOperation(3);            }
        });

        // If switch user button selected, load up sign in screen to sign in to a new user
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
     * Main method to create a load game screen and display it.
     * @param args Command-line arguments.
     */    public static void main(String[] args) {
        LoadGame loadGame = new LoadGame(1);
        loadGame.setVisible(true);
        loadGame.setSize(500, 500);
        loadGame.setTitle("Load Game");
        loadGame.setContentPane(loadGame.panelLoadGame);
        loadGame.setDefaultCloseOperation(3);
    }


}
