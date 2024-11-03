import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * HighScore class displays the high scores of the game.
 * @author Zih Yu Liao
 */
public class HighScore extends JFrame {
    /**
     * Constructs a new HighScore frame.
     */

    public HighScore() {
        // Set the frame size to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setUndecorated(true); // Remove window decorations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem backToMenuMenuItem = new JMenuItem("Back to Menu");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add action listeners for menu items
        backToMenuMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the TutorialPage window
                dispose();

                // Open the MainMenu window
                MainMenu mainMenu = new MainMenu();
                mainMenu.userID.setText(SignIn.user.getText());
                mainMenu.setContentPane(mainMenu.panelMainMenu);
                mainMenu.setTitle("Main Menu");
                mainMenu.setSize(500, 500);
                mainMenu.setVisible(true);
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });


        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Add action to exit the game
            }
        });

        // Add menu items to the menu
        menu.add(backToMenuMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Load and display high scores
        JPanel highScoresPanel = new JPanel();
        highScoresPanel.setLayout(new GridLayout(0, 3));
        //highScoresPanel.setBackground(Color.BLACK); // Set background color to black
        // Set background color
        Color backgroundColor = new Color(59, 154, 255); // #3b9aff
        highScoresPanel.setBackground(backgroundColor);

        // Add top row with headers
        JLabel rankHeader = new JLabel("RANK");
        rankHeader.setForeground(Color.WHITE);
        rankHeader.setFont(rankHeader.getFont().deriveFont(Font.BOLD, 20));
        rankHeader.setHorizontalAlignment(SwingConstants.CENTER);
        highScoresPanel.add(rankHeader);

        JLabel usernameHeader = new JLabel("USER");
        usernameHeader.setForeground(Color.WHITE);
        usernameHeader.setFont(usernameHeader.getFont().deriveFont(Font.BOLD, 20));
        usernameHeader.setHorizontalAlignment(SwingConstants.CENTER);
        highScoresPanel.add(usernameHeader);

        JLabel scoreHeader = new JLabel("USED SECOND");
        scoreHeader.setForeground(Color.WHITE);
        scoreHeader.setFont(scoreHeader.getFont().deriveFont(Font.BOLD, 20));
        scoreHeader.setHorizontalAlignment(SwingConstants.CENTER);
        highScoresPanel.add(scoreHeader);

        try {
            String content = new String(Files.readAllBytes(Paths.get("data/accdata.json")));
            JSONObject json = new JSONObject(content);
            JSONArray users = json.getJSONArray("users");
            int rank = 1;
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.has("highest_score")) {
                    String username = user.getString("username");
                    int highestScore = user.getInt("highest_score");
                    String rankImagePath = "";
                    if (rank == 1) {
                        rankImagePath = "img/rank1.png";
                    } else if (rank == 2) {
                        rankImagePath = "img/rank2.png";
                    } else if (rank == 3) {
                        rankImagePath = "img/rank3.png";
                    }
                    ImageIcon medalIcon = null;
                    if (!rankImagePath.isEmpty()) {
                        medalIcon = new ImageIcon(rankImagePath);
                        medalIcon = new ImageIcon(medalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
                    }
                    JLabel rankLabel = new JLabel("");
                    rankLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
                    if (rank <= 3) {
                        rankLabel.setIcon(medalIcon);
                    } else {
                        rankLabel.setText(String.valueOf(rank));
                        rankLabel.setForeground(Color.PINK); // Set text color to green
                        rankLabel.setFont(rankLabel.getFont().deriveFont(Font.BOLD, 16)); // Set font size and style
                    }
                    highScoresPanel.add(rankLabel);
                    JLabel userLabel = new JLabel(username);
                    userLabel.setForeground(Color.ORANGE); // Set text color to green
                    userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 16)); // Set font size and style
                    userLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
                    highScoresPanel.add(userLabel);
                    JLabel scoreLabel = new JLabel(String.valueOf(highestScore));
                    scoreLabel.setForeground(Color.ORANGE); // Set text color to green
                    scoreLabel.setFont(scoreLabel.getFont().deriveFont(Font.BOLD, 16)); // Set font size and style
                    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
                    highScoresPanel.add(scoreLabel);
                    rank++;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading high scores.");
            errorLabel.setForeground(Color.RED);
            highScoresPanel.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(highScoresPanel);
        scrollPane.setBounds(0, screenSize.height / 4, screenSize.width, screenSize.height / 2);

        add(scrollPane);

        // Display the frame in the middle of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Main method for running the HighScore frame.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HighScore::new);
    }
}
