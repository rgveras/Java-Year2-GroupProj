import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a screen for selecting single-player game modes.
 * It extends JFrame and provides buttons for selecting between countdown and timed game modes.
 * @author Ty William Bryson
 */
public class SinglePlayer2 extends JFrame{
    public JPanel panelSinglePlayer2;
    private JButton exitButton;
    private JButton switchUser;
    private JLabel userID;
    private JLabel pageTitle;
    private JButton countdownGame;
    private JButton timedGame;
    public int difficulty;
    public int level;
    /**
     * Constructs a SinglePlayer2 window.
     * @param difficulty The difficulty level
     * @param level The level
     */
    public SinglePlayer2(int difficulty, int level) {
        userID.setText(SignIn.user.getText());      // Current username is assigned to label userID
        this.difficulty = difficulty;               // Difficulty level is set from provided parameter
        this.level = level;                         // Level is set from provided parameter

        // If exit button is selected, the previous screen is returned to
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // If countdown mode is selected, the countdown game mode is launched with the difficulty and level parameters chosen earlier
        countdownGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        TimerMode frame = new TimerMode(level*2, difficulty, userID.getText()); // Example: 4x4 grid, easy difficulty
                        frame.setVisible(true);

                    }
                });
            }
            });

        // If timed mode is selected, the timed game mode is launched with the difficulty and level parameters chosen earlier
        timedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Countdown frame = new Countdown(level*2, difficulty); // Example: 4x4 grid, easy difficulty
                        frame.setVisible(true);
                    }
                });
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
}