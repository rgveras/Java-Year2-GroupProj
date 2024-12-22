import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents a two-player memory game.
 * @author Ty William Bryson
 */
public class TwoPlayer extends JFrame {
    private String typedCharacters = "";
    private GameBoard gameBoard;
    private JPanel gridPanel;
    private List<Card> flippedCards = new ArrayList<>();
    private boolean isWaiting = false;
    private Timer countdownTimer;
    private int timeElapsed = 0;
    private JLabel timerLabel;
    private int size;
    private int difficulty;
    private String player1;
    private String player2;
    private List<Integer> playerTimes = new ArrayList<>();
    Font labelFont = new Font("Arial", Font.BOLD, 34);
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    Font questionFont = new Font("Arial", Font.BOLD, 100);
    Color forestGreen = new Color(34, 139, 34);
    Color skyBlue = new Color(69, 141, 224);
    /**
     * Constructs a new instance of the TwoPlayer class with the specified parameters.
     *
     * @param size       The size of the game board (number of rows and columns).
     * @param difficulty The difficulty level of the game.
     * @param player1    The name of the first player.
     * @param player2    The name of the second player.
     */
    public TwoPlayer(int size, int difficulty, String player1, String player2) {
        // Initialize the game board
        this.size = size;
        this.difficulty = difficulty;
        this.player1 = player1;
        this.player2 = player2;
        gameBoard = new GameBoard(size, difficulty);
        // Setup the JFrame
        setTitle("TwoPlayer Mode");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Reset button

        // Panel to hold the reset button, aligned to the right
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        // Main panel with BorderLayout to arrange buttonPanel and gridPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START); // Adds the button panel to the top

        timerLabel = new JLabel("Time = 0");
        timerLabel.setFont(labelFont);
        buttonPanel.add(timerLabel, BorderLayout.EAST); // Add the timerLabel to your buttonPanel or another suitable panel
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
     * Displays a pop-up window with instructions and a start button for the game.
     * If no players have started, the instructions are displayed for player1.
     * If one player has started, the instructions are displayed for player2.
     */
    private void showStartGamePopUp() {
        final JDialog popUp = new JDialog(this, "Start Game", true);
        popUp.setLayout(new BorderLayout(10, 10));

        // Instructions
        if (playerTimes.size() == 0) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player1 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 1) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player2 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }



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
     * Displays a pop-up window at the end of the game showing game over message,
     * player's completion time, and options to retry or quit.
     * If one player has completed the game, it shows a message indicating the completion time
     * and prompts for the next player's turn.
     * If both players have completed the game, it shows a message indicating the winner based on completion time,
     * and offers options for a rematch or quitting the game.
     */
    private void showEndGamePopUp() {
        if (playerTimes.size() == 1) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    player1 + " You've completed the game in " + playerTimes.get(0) + " seconds. <br>" +
                    "Now, it is " + player2 + "'s turn. </div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
            popUp.add(buttonPanel, BorderLayout.SOUTH);
            // Start button
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                resetGame();
                popUp.dispose();
            });

            buttonPanel.add(nextButton, BorderLayout.SOUTH);
            popUp.pack();
            popUp.setLocationRelativeTo(this);
            popUp.setVisible(true);
        }
        if (playerTimes.size() == 2) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            if (playerTimes.get(0) < playerTimes.get(1)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player1 + ", you beat " + player2 + "'s time of " + playerTimes.get(1) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(0) + " seconds. <br>" + "Would you like to have a rematch?</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
            }
            if (playerTimes.get(1) < playerTimes.get(0)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player2 + ", you beat " + player1 + "'s time of " + playerTimes.get(0) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(1) + " seconds. <br>" + "Would you like to have a rematch?</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
            }
            if (playerTimes.get(0) == playerTimes.get(1)){
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "You both tied eachother with a time of " + playerTimes.get(0) + " seconds. <br>" +
                        "Would you like to have a rematch?</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
            }
            popUp.add(buttonPanel, BorderLayout.SOUTH);
            // Start button
            JButton quitButton = new JButton("Quit");
            JButton retryButton = new JButton("Retry");
            retryButton.addActionListener(e -> {
                playerTimes.clear();
                resetGame();
                popUp.dispose();
            });
            quitButton.addActionListener(ext -> {
                this.dispose();
            });
            buttonPanel.add(quitButton, BorderLayout.WEST);
            buttonPanel.add(retryButton,BorderLayout.EAST);
            popUp.pack();
            popUp.setLocationRelativeTo(this);
            popUp.setVisible(true);
        }

    }

    /**
     * Initializes the game grid by adding key listener for cheat code detection,
     * setting up card buttons, and adding action listeners to handle card flipping.
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
                                        c.button.requestFocusInWindow();
                                    });

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

                                } else {
                                    // If not a match, start a timer to flip cards back after 1.5 seconds
                                    Timer timer = new Timer(1000, evt -> {
                                        flippedCards.forEach(c -> {
                                            c.flipCard();
                                            c.button.setFont(questionFont);
                                            c.button.setForeground(Color.orange);
                                            c.button.setText("?"); // Reset to initial state
                                            c.button.requestFocusInWindow();
                                        });
                                        flippedCards.clear();
                                        isWaiting = false;

                                    });

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
     * Starts the game by initializing a countdown timer to track the elapsed time,
     * updating the timer label, and stopping the timer if all cards are matched.
     * If the timer is not already running, it starts it.
     */
    private void startGame() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                timerLabel.setText("Time: " + timeElapsed + " seconds");
                if (gameBoard.areAllCardsMatched()) {
                    countdownTimer.stop();

                }
            }
        });
        countdownTimer.start();
        // Check if the timer is already running
        if (!countdownTimer.isRunning()) {
            countdownTimer.start();
        }
    }
    /**
     * Checks if all cards are matched, and if so, calls the endGame() method.
     */
    private void checkForWin() {
        if (gameBoard.areAllCardsMatched()) {
            endGame();
        }
    }
    /**
     * Activates cheat mode by revealing all cards on the game board.
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
     * Resets the game by removing all components from the grid panel,
     * reinitializing the game board, displaying the start game pop-up,
     * resetting card states, and clearing timers and lists.
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
        timeElapsed = 0;
        timerLabel.setText("Time = 0");
        countdownTimer.stop();
        flippedCards.clear();

    }
    /**
     * Ends the game by stopping the countdown timer, recording the elapsed time,
     * and displaying the end game pop-up dialog.
     */
    private void endGame() {
        // Options for the dialog
        countdownTimer.stop();
        playerTimes.add(timeElapsed);
        timeElapsed = 0;

        showEndGamePopUp();
    }

    /**
     * Main method to start the TwoPlayer game.
     * It creates a new instance of TwoPlayer, sets its location, and makes it visible.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TwoPlayer frame = new TwoPlayer(2, 2, "tbryson5", "anshgupta"); // Example: 4x4 grid, easy difficulty

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }
}