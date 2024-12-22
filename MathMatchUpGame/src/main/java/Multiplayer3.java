import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for selecting the game mode in multiplayer mode.
 * It extends JFrame and provides buttons for selecting between different game modes (timed, tournament, pyramid).
 * @author Ty William Bryson
 */public class Multiplayer3 extends JFrame {
    public JPanel panelMultiplayer3;
    private JButton exitButton;
    private JLabel pageTitle;
    private JLabel userID;
    private JButton timedGame;
    private JButton tournamentGame;
    private JButton pyramidGame;
    public int difficulty;
    /**
     * Constructs a Multiplayer3 object with the specified difficulty level.
     * @param difficulty The difficulty level of the game.
     */
    public Multiplayer3(int difficulty) {
        userID.setText(SignIn.user.getText());      // Current username is set to userID
        this.difficulty = difficulty;               // Previously selected difficulty level is set to variable difficulty

        // If exit button is pressed, previous screen is loaded
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // If timed mode is selected, the next screen is loaded with a parameter value of 1, identifying that timed mode was selected
        timedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Multiplayer2 mplayer2 = new Multiplayer2(difficulty, 1);
                mplayer2.setContentPane(mplayer2.panelMultiplayer2);
                mplayer2.setTitle("New Game");
                mplayer2.setSize(500, 500);
                mplayer2.player3.setVisible(false);
                mplayer2.player4.setVisible(false);
                mplayer2.setVisible(true);
                mplayer2.setDefaultCloseOperation(3);
            }
        });

        // If tournament mode is selected, the next screen is loaded with a parameter value of 2, identifying that tournament mode was selected
        tournamentGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Multiplayer2 mplayer2 = new Multiplayer2(difficulty, 2);
                mplayer2.setContentPane(mplayer2.panelMultiplayer2);
                mplayer2.setTitle("New Game");
                mplayer2.setSize(500, 500);
                mplayer2.setVisible(true);
                mplayer2.setDefaultCloseOperation(3);
            }
        });

    }
}
