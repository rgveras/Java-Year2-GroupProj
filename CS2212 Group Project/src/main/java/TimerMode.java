import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * TimerMode class represents a memory matching game with a timer mode.
 * @author Ty William Bryson
 */
public class TimerMode extends JFrame {
    private String typedCharacters = "";
    private GameBoard gameBoard;
    private JPanel gridPanel;
    private List<Card> matchedCards = new ArrayList<>();
    private List<Card> flippedCards = new ArrayList<>();
    private boolean isWaiting = false;
    private String User;
    private Timer countdownTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft);
            if (timeLeft <= 0) {
                countdownTimer.stop();
                endGame();
            }
        }
    });
    private int timeLeft = 60;
    private JLabel timerLabel;
    private JLabel comboLabel;
    private JLabel timeIncrease;
    private int size;
    private int difficulty;
    Font labelFont = new Font("Arial", Font.BOLD, 34);
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    Font questionFont = new Font("Arial", Font.BOLD, 100);
    Color forestGreen = new Color(34, 139, 34);
    Color skyBlue = new Color(69, 141, 224);
    /**
     * Constructs a TimerMode game with the specified size, difficulty, and username.
     * @param size The size of the game grid.
     * @param difficulty The difficulty level of the game.
     * @param User The username of the player.
     */
    public TimerMode(int size, int difficulty, String User) {
        // Initialize the game board
        this.User = User;
        this.size = size;
        this.difficulty = difficulty;
        switch (size) {
            case 2: // Easy
                setTitle("Timer Mode (Level 1)");
                timeLeft = 60;
                timerLabel = new JLabel("Time left: 60");
                break;
            case 4: // Medium
                setTitle("Timer Mode (Level 2)");
                timeLeft = 90;
                timerLabel = new JLabel("Time left: 90");
                break;
            case 6: // Hard
                setTitle("Timer Mode (Level 3)");
                timeLeft = 120;
                timerLabel = new JLabel("Time left: 120");
                break;
            case 8:
                setTitle("Timer Mode (Level 4)");
                timeLeft = 150;
                timerLabel = new JLabel("Time left: 150");
            default:
                throw new IllegalArgumentException("Invalid difficulty level");
        }
        gameBoard = new GameBoard(size, difficulty);
        // Setup the JFrame

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Panel to hold the reset button, aligned to the right
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Main panel with BorderLayout to arrange buttonPanel and gridPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START); // Adds the button panel to the top

        comboLabel = new JLabel("Combo: " + matchedCards.size(), JLabel.LEFT);
        timeIncrease = new JLabel("");
        timeIncrease.setFont(labelFont);
        timeIncrease.setForeground(forestGreen);
        comboLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        buttonPanel.add(timerLabel, BorderLayout.EAST);
        buttonPanel.add(timeIncrease); // Add the timerLabel to your buttonPanel or another suitable panel
        buttonPanel.add(comboLabel, BorderLayout.WEST);
        // Grid panel for the game
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(size, size)); // Setup grid layout based on the size
        mainPanel.add(gridPanel, BorderLayout.CENTER); // Adds the grid panel to the center
        gridPanel.setBackground(skyBlue);
        // Add mainPanel to JFrame
        setContentPane(mainPanel);

        initializeGameGrid();
        SwingUtilities.invokeLater(this::showStartGamePopUp);
    }
    /**
     * Displays a pop-up window to prompt the user to start the game.
     */
    private void showStartGamePopUp() {
        final JDialog popUp = new JDialog(this, "Start Game", true);
        popUp.setLayout(new BorderLayout(10, 10));

        // Instructions
        JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                "How to play: match all equations to their values within the time limit to win.<br>" +
                "Click start to begin, Good luck!</div></html>", SwingConstants.CENTER);
        popUp.add(instructions, BorderLayout.CENTER);

        // Start button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startGame();
            popUp.dispose();
        });
        popUp.add(startButton, BorderLayout.SOUTH);

        popUp.pack();
        popUp.setLocationRelativeTo(this);
        popUp.setVisible(true);
    }
    /**
     * Initializes the game grid by setting up card buttons and their corresponding action listeners.
     * Enables cheat activation by tracking typed characters and triggering the cheat method when "cheat" is typed.
     */
    private void initializeGameGrid() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Append the typed character to the tracked string
                typedCharacters += e.getKeyChar();

                // Check if the last 5 characters match "cheat"
                if (typedCharacters.length() > 5) {
                    // Remove the oldest character to keep track of the last 5 characters only
                    typedCharacters = typedCharacters.substring(1);
                }

                if ("cheat".equals(typedCharacters)) {
                    // Call the method you want to trigger
                    cheatActivated();
                    // Optionally reset typedCharacters if you want to wait for a new "cheat" input
                    typedCharacters = "";
                }
            }
        });
        for (int i = 0; i < gameBoard.size; i++) {
            for (int j = 0; j < gameBoard.size; j++) {

                Card card = gameBoard.getBoard()[i][j]; // Get the Card object from the gameBoard
                card.button.setFont(questionFont);
                card.button.setForeground(Color.orange);

                card.button.setText("?");
                card.button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!card.isFlipped() && !isWaiting) { // Check the flag here
                            card.flipCard();
                            card.button.setFont(buttonFont);
                            card.button.setForeground(Color.black);
                            card.button.setText(card.getIdentity());

                            flippedCards.add(card);

                            if (flippedCards.size() == 2) {
                                isWaiting = true; // Set the flag to disable further flipping

                                // In the section where you handle matched cards:
                                if (gameBoard.MatchCard(flippedCards.get(0), flippedCards.get(1))) {
                                    flippedCards.forEach(c -> {
                                        c.button.setForeground(forestGreen);
                                        c.button.setText(c.getIdentity());

                                        c.isMatched = true;
                                    });
                                    matchedCards.add(flippedCards.get(0));
                                    if (matchedCards.size() >= 2) {
                                        timeLeft += 5;
                                        timeIncrease.setText("\t COMBO! + 5 Seconds!");
                                    }
                                    comboLabel.setText("Combo: " + matchedCards.size());

                                    // Start a timer to hide the cards and check for a win after 750 milliseconds
                                    Timer timer = new Timer(750, evt -> {
                                        flippedCards.forEach(c -> c.button.setVisible(false)); // Hide the card
                                        flippedCards.clear();
                                        isWaiting = false;


                                        // After hiding cards and clearing the list, check for a win
                                        checkForWin();
                                    });
                                    timer.setRepeats(false);
                                    timer.start();
                                    Timer timer2 = new Timer(1500, evt2 -> {

                                        timeIncrease.setText("");
                                    });
                                    timer2.setRepeats(false);
                                    timer2.start();

                                } else {
                                    // If not a match, start a timer to flip cards back after 1.5 seconds
                                    Timer timer = new Timer(1000, evt -> {
                                        flippedCards.forEach(c -> {
                                            c.flipCard();
                                            c.button.setFont(questionFont);
                                            c.button.setForeground(Color.orange);
                                            c.button.setText("?"); // Reset to initial state
                                        });
                                        flippedCards.clear();
                                        isWaiting = false;

                                    });
                                    matchedCards.clear();
                                    comboLabel.setText("Combo: " + matchedCards.size());
                                    timer.setRepeats(false);
                                    timer.start();
                                }
                            }
                        }
                    }
                });
                gridPanel.add(card.button);
            }
        }
    }
    /**
     * Starts the game by starting the countdown timer.
     */
    private void startGame() {

        countdownTimer.start();
        // Check if the timer is already running

    }
    /**
     * Reads the content of the JSON file "accdata.json" and returns a JSONObject representing its contents.
     *
     * @return The JSONObject representing the content of the JSON file.
     * @throws Exception If an error occurs while reading the JSON file.
     */
    private JSONObject readJsonFile() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("data/accdata.json")));
        return new JSONObject(content);
    }
    /**
     * Unlocks the next level based on the current level, size, and user.
     * It reads the user data from the JSON file, updates the level if necessary, and saves the changes back to the file.
     */
    private void unlockLevel() {
        try {
            JSONObject jsonObj = readJsonFile();
            JSONArray users = jsonObj.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(User)) {
                    if (user.getInt("level")==(1) && size == 2) {
                        user.put("level", 2);
                    }
                    if (user.getInt("level")==(2) && size == 4) {
                        user.put("level", 3);
                    }
                    if (user.getInt("level")==(3) && size == 6) {
                        user.put("level", 4);
                    }
                    saveJsonFile(jsonObj);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the provided JSONObject to the JSON file "accdata.json".
     *
     * @param jsonObj The JSONObject to be saved to the file.
     * @throws Exception If an error occurs while writing to the JSON file.
     */
    private void saveJsonFile(JSONObject jsonObj) throws Exception {
        try (FileWriter file = new FileWriter("data/accdata.json")) {
            file.write(jsonObj.toString());
        }
    }
    /**
     * Checks if the player has won the game by matching all cards.
     * If all cards are matched:
     *   - Stops the countdown timer.
     *   - Unlocks the next level if applicable.
     *   - Displays a congratulatory message and options to replay the level, proceed to the next level, or quit.
     */
    private void checkForWin() {
        if (gameBoard.areAllCardsMatched()) {
            countdownTimer.stop();
            unlockLevel();
            if (size == 8) {

                Object[] options = {"Replay", "Exit"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Congratulations! You've beaten the final level. \n Play again or return to menu.",
                        "You Won!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);

                // React based on user selection
                if (choice == JOptionPane.YES_OPTION) {
                    // User chose to retry
                    resetGame();

                } else {
                    // User chose to exit, or closed the dialog
                    // Here you can close the game window or perform other cleanup actions.
                    this.dispose(); // This closes the TimerMode window. Adjust as necessary.
                }
                // Ens
            }
            else {
                final JDialog popUp = new JDialog(this, "You Won!", true);
                popUp.setLayout(new BorderLayout(10, 10));
                JPanel buttonPanel = new JPanel(new BorderLayout());
                popUp.add(buttonPanel, BorderLayout.SOUTH);
                // Instructions
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations, you beat the level!<br>" + "A new level has been unlocked.",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);

                // Start button
                JButton replayButton = new JButton("Replay");
                JButton nextLevel = new JButton("Next Level");
                JButton quitButton = new JButton("Quit");

                replayButton.addActionListener(e -> {
                    startGame();
                    popUp.dispose();
                });
                nextLevel.addActionListener(next -> {
                    size += 2;
                    this.dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            TimerMode frame = new TimerMode(size, difficulty, User); // Example: 4x4 grid, easy difficulty
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);

                        }
                    });
                });
                quitButton.addActionListener(quit -> {
                    this.dispose();
                });
                buttonPanel.add(replayButton, BorderLayout.WEST);
                buttonPanel.add(nextLevel, BorderLayout.CENTER);
                buttonPanel.add(quitButton, BorderLayout.EAST);


                popUp.pack();
                popUp.setLocationRelativeTo(this);
                popUp.setVisible(true);

            }

        }
    }
    /**
     * Activates the cheat mode, revealing all card identities on the game board.
     * This method is triggered when the user types "cheat" during the game.
     */
    private void cheatActivated(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Card card = gameBoard.getBoard()[i][j];
                card.button.setFont(buttonFont);
                card.button.setForeground(Color.black);
                card.button.setText(card.getIdentity());
            }
        }
    }
    /**
     * Resets the game to its initial state.
     * This method is called when the user chooses to restart the game.
     */
    private void resetGame() {

        gridPanel.removeAll(); // Remove all components from gridPanel
        gridPanel.revalidate(); // Revalidate to clear removed components from UI
        gridPanel.repaint();
        gameBoard = new GameBoard(size, difficulty);
        initializeGameGrid();
        SwingUtilities.invokeLater(this::showStartGamePopUp);
        for (int i = 0; i < gameBoard.size; i++) {
            for (int j = 0; j < gameBoard.size; j++) {
                gameBoard.getBoard()[i][j].isFlipped = false;
                gameBoard.getBoard()[i][j].isMatched = false;
                gameBoard.getBoard()[i][j].button.setFont(questionFont);
                gameBoard.getBoard()[i][j].button.setForeground(Color.orange);
                gameBoard.getBoard()[i][j].button.setText("?"); // Reset to initial state
                gameBoard.getBoard()[i][j].button.setVisible(true);
            }
        }
        timeLeft = 60;
        timerLabel.setText("Time left: 60");
        countdownTimer.stop();
        flippedCards.clear();
        matchedCards.clear();
        comboLabel.setText("Combo: " + 0);

    }

    /**
     * Ends the game when the time limit is reached.
     * This method displays a dialog box with options to retry, proceed to the next level, or exit the game.
     */
    private void endGame() {
        Object[] options = {"Retry", "Next Level", "Exit"};
        int choice = JOptionPane.showOptionDialog(this,
                "Time's up! Game over.",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        // React based on user selection
        if (choice == JOptionPane.YES_OPTION) {
            // User chose to retry
            resetGame();
        } else {
            // User chose to exit, or closed the dialog
            // Here you can close the game window or perform other cleanup actions.
            this.dispose(); // This closes the TimerMode window. Adjust as necessary.
        }
    }
    /**
     * The entry point for the TimerMode game.
     * Initializes the game with a 4x4 grid and easy difficulty level for the specified user.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimerMode frame = new TimerMode(4, 2, "user1"); // Example: 4x4 grid, easy difficulty
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }
}