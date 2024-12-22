import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for entering additional usernames for multiplayer games.
 * It extends JFrame and provides fields for entering usernames and buttons for starting or exiting the game.
 * @author Ty William Bryson
 */public class Multiplayer2 extends JFrame {
    public JPanel panelMultiplayer2;
    private JLabel pageTitle;
    private JLabel userID;
    private JButton startButton;
    private JButton exitButton;
    public JTextField player2;
    public JTextField player3;
    public JTextField player4;
    public JLabel mainPlayer;
    private JLabel userID2;
    public int difficulty;
    /**
     * Constructs a Multiplayer2 object with the specified difficulty and level.
     * @param difficulty The difficulty level of the game.
     * @param level The level of the game.
     */
    public Multiplayer2(int difficulty, int level) {
        userID.setText(SignIn.user.getText());      // Set current username to label userID
        userID2.setText(SignIn.user.getText());     // Set current username to label userID2
        this.difficulty = difficulty;

        // If exit button is selected, user is returned to previous screen
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // If start button is pressed, the previously selected game type at the selected difficulty level is launched
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // If the level is set to 1, timer mode is launched
                if (level == 1) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            TwoPlayer frame = new TwoPlayer(level*2, difficulty, userID.getText(), player2.getText()); // Example: 4x4 grid, easy difficulty
                            frame.setVisible(true);

                        }
                    });
                }
                // If the level is set to 2, tournament mode is launched
                else if (level == 2) {
                    Tournament frame = new Tournament(level*2, difficulty, userID.getText(), player2.getText(), player3.getText(), player4.getText());
                    frame.setVisible(true);

                }
            }
        });

    }
}
