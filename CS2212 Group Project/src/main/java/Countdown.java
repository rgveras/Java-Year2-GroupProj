import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents a countdown mode matching game.
 * Extends JFrame
 * @author Ty William Bryson
 */
public class Countdown extends JFrame {
    private GameBoard gameBoard;
    private JPanel gridPanel;
    private List<Card> flippedCards = new ArrayList<>();
    private boolean isWaiting = false;
    private Timer countdownTimer;
    private int timeElapsed = 0;
    private JLabel timerLabel;
    private JButton startButton;
    private int size;
    private int difficulty;
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    Font questionFont = new Font("Arial", Font.BOLD, 100);
    /**
     * Constructs a Countdown object with the specified size and difficulty.
     *
     * @param size       the size of the game grid
     * @param difficulty the difficulty level of the game
     */
    public Countdown(int size, int difficulty) {
        // Initialize the game board
        this.size = size;
        this.difficulty = difficulty;
        gameBoard = new GameBoard(size, difficulty);
        // Setup the JFrame
        setTitle("Countdown Mode");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame(); // Implement the reset functionality
            }
        });

        // Panel to hold the reset button, aligned to the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(resetButton);
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Main panel with BorderLayout to arrange buttonPanel and gridPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START); // Adds the button panel to the top

        timerLabel = new JLabel("Time = 0");

        buttonPanel.add(timerLabel); // Add the timerLabel to your buttonPanel or another suitable panel
        // Grid panel for the game
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(size, size)); // Setup grid layout based on the size
        mainPanel.add(gridPanel, BorderLayout.CENTER); // Adds the grid panel to the center
        gridPanel.setBackground(Color.BLUE);
        // Add mainPanel to JFrame
        setContentPane(mainPanel);

        initializeGameGrid();
        SwingUtilities.invokeLater(this::showStartGamePopUp);
    }

    /**
     * Displays a pop-up window with game instructions and a start button.
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
     * Initializes the game grid by creating and adding buttons for each card.
     */
    private void initializeGameGrid() {

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
                                        c.button.setForeground(Color.green);
                                        c.button.setText("Matched!");
                                        c.isMatched = true;
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
                                    Timer timer = new Timer(1500, evt -> {
                                        flippedCards.forEach(c -> {
                                            c.flipCard();
                                            c.button.setFont(questionFont);
                                            c.button.setForeground(Color.orange);
                                            c.button.setText("?"); // Reset to initial state
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
     * Starts the game by initializing the countdown timer.
     */
    private void startGame() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                timerLabel.setText("Time: " + timeElapsed + " seconds");
                if (gameBoard.areAllCardsMatched()) {
                    countdownTimer.stop();
                    endGame(timeElapsed);
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
     * Checks for a win condition in the game.
     */
    private void checkForWin() {
        if (gameBoard.areAllCardsMatched()) {

            endGame(timeElapsed);
        }
    }
    /**
     * Resets the game to its initial state.
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
     * Ends the game and displays a dialog with the result.
     *
     * @param timeElapsed the time taken to complete the game
     */
    private void endGame(long timeElapsed) {
        // Options for the dialog
        countdownTimer.stop();
        Object[] options = {"Retry", "Exit"};

        // Create the message to display, including the time elapsed
        String message = "Congratulations! You've completed the game in " + timeElapsed + " seconds.";

        // Show option dialog with customized message and title
        int choice = JOptionPane.showOptionDialog(this,
                message, // Updated message
                "Game Over", // Keep or change this title based on your preference
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        // React based on user selection
        if (choice == JOptionPane.YES_OPTION) {
            // User chose to retry
            resetGame();
        } else {
            // User chose to exit or closed the dialog
            this.dispose(); // Adjust as necessary based on how you handle game exit.
        }
    }
    /**
     * Unlocks the next level.
     */

    private void unlockLevel() {

    }

    /**
     * The main method to start the Countdown game.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Countdown frame = new Countdown(4, 2); // Example: 4x4 grid, easy difficulty
                frame.setVisible(true);

            }
        });
    }
}