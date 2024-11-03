import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents a Tournament game mode extending JFrame.
 * Allows multiple players to compete against each other.
 * @author Ty William Bryson
 */
public class Tournament extends JFrame {
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
    private String player3;
    private String player4;
    private int gameState;
    private List<Integer> playerTimes = new ArrayList<>();
    private List<String> winningPlayers = new ArrayList<>();

    Font labelFont = new Font("Arial", Font.BOLD, 34);
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    Font questionFont = new Font("Arial", Font.BOLD, 100);
    Color forestGreen = new Color(34, 139, 34);
    Color skyBlue = new Color(69, 141, 224);

    /**
     * Constructs a Tournament instance.
     *
     * @param size       The size of the game board.
     * @param difficulty The difficulty level of the game.
     * @param player1    The name of player 1.
     * @param player2    The name of player 2.
     * @param player3    The name of player 3.
     * @param player4    The name of player 4.
     */
    public Tournament(int size, int difficulty, String player1, String player2, String player3, String player4) {
        // Initialize the game board
        this.size = size;
        this.difficulty = difficulty;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        gameBoard = new GameBoard(size, difficulty);
        // Setup the JFrame
        setTitle("Tournament Mode");
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
     * Displays a pop-up dialog box to start the game for each round of the tournament.
     * The dialog includes instructions and a start button tailored to the current round.
     * Instructions are based on the number of saved times and the current game state.
     */
    private void showStartGamePopUp() {
        System.out.print("number of saved times: " + playerTimes.size());
        System.out.print("gameState: " + gameState);


        final JDialog popUp = new JDialog(this, "Start Game", true);
        popUp.setLayout(new BorderLayout(10, 10));

        // Instructions
        if (playerTimes.size() == 0 && gameState == 0) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Round 1, " + player1 + " vs " + player2 + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player1 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 1 && gameState == 0) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Round 1, " + player1 + " vs " + player2 + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player2 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 0 && gameState == 1) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Round 2, " + player3 + " vs " + player4 + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player3 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 1 && gameState == 1) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Round 1, " + player3 + " vs " + player4 + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    player4 + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 0 && gameState == 2) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Final Round, " + winningPlayers.get(0) + " vs " + winningPlayers.get(1) + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    winningPlayers.get(0) + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
            popUp.add(instructions, BorderLayout.CENTER);
        }
        if (playerTimes.size() == 1 && gameState == 2) {
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    "Final Round, " + winningPlayers.get(1) + " vs " + winningPlayers.get(0) + "<br>" +
                    "How to play: match all equations to their values as quick as possible,<br>" +
                    "Whichever player has the lower time wins. <br>" +
                    winningPlayers.get(1) + ", Click start to begin your turn, Good luck!</div></html>", SwingConstants.CENTER);
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
     * Displays a pop-up dialog box at the end of each round or the tournament.
     * The dialog includes information about the game outcome and options for the next step.
     * Instructions and buttons are tailored to the current game state and player times.
     */
    private void showEndGamePopUp() {
        int tieVariable = 0;
        if (playerTimes.size() == 1 && gameState == 0) {
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
        if (playerTimes.size() == 1 && gameState == 1) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    player3 + " You've completed the game in " + playerTimes.get(0) + " seconds. <br>" +
                    "Now, it is " + player4 + "'s turn. </div></html>", SwingConstants.CENTER);
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
        if (playerTimes.size() == 1 && gameState == 2) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                    winningPlayers.get(0) + " You've completed the game in " + playerTimes.get(0) + " seconds. <br>" +
                    "Now, it is " + winningPlayers.get(1) + "'s turn. </div></html>", SwingConstants.CENTER);
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
        if (playerTimes.size() == 2 && gameState == 0) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            if (playerTimes.get(0) < playerTimes.get(1)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player1 + ", you beat " + player2 + "'s time of " + playerTimes.get(1) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(0) + " seconds. <br>" + player1 + " has advanced to the final round!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                winningPlayers.add(player1);
                gameState ++;
            }
            if (playerTimes.get(1) < playerTimes.get(0)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player2 + ", you beat " + player1 + "'s time of " + playerTimes.get(0) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(1) + " seconds. <br>" + player2 + "has advanced to the final round!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                winningPlayers.add(player2);
                gameState ++;
            }
            if (playerTimes.get(0) == playerTimes.get(1)){
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "You both tied eachother with a time of " + playerTimes.get(0) + " seconds. <br>" +
                        "Play again to break the tie</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                tieVariable = 1;
            }

            popUp.add(buttonPanel, BorderLayout.SOUTH);
            System.out.print(tieVariable);
            if (tieVariable == 1) {
                JButton rematchButton = new JButton("Rematch");
                rematchButton.addActionListener(e -> {
                    resetGame();
                    popUp.dispose();
                });
                buttonPanel.add(rematchButton, BorderLayout.SOUTH);
                popUp.pack();
                popUp.setLocationRelativeTo(this);
                popUp.setVisible(true);

            }
            else {
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


            playerTimes.clear();
        }
        if (playerTimes.size() == 2 && gameState == 1) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            if (playerTimes.get(0) < playerTimes.get(1)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player3 + ", you beat " + player4 + "'s time of " + playerTimes.get(1) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(0) + " seconds. <br>" + player3 + " has advanced to the final round!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                winningPlayers.add(player3);
                gameState ++;
            }
            if (playerTimes.get(1) < playerTimes.get(0)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + player4 + ", you beat " + player3 + "'s time of " + playerTimes.get(0) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(1) + " seconds. <br>" + player4 + " has advanced to the final round!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                winningPlayers.add(player4);
                gameState ++;
            }
            if (playerTimes.get(0) == playerTimes.get(1)){
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "You both tied eachother with a time of " + playerTimes.get(0) + " seconds. <br>" +
                        "Play again to break the tie</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                tieVariable = 1;

            }
            popUp.add(buttonPanel, BorderLayout.SOUTH);
            System.out.print(tieVariable);
            // Start button
            if (tieVariable == 1) {
                JButton rematchButton = new JButton("Rematch");
                rematchButton.addActionListener(e -> {
                    resetGame();
                    popUp.dispose();
                });
                buttonPanel.add(rematchButton, BorderLayout.SOUTH);
                popUp.pack();
                popUp.setLocationRelativeTo(this);
                popUp.setVisible(true);

            }
            else {
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


            playerTimes.clear();
        }
        if (playerTimes.size() == 2 && gameState == 2) {
            final JDialog popUp = new JDialog(this, "Game Over", true);
            popUp.setLayout(new BorderLayout(10, 10));
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Instructions
            if (playerTimes.get(0) < playerTimes.get(1)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + winningPlayers.get(0) + ", you beat " + winningPlayers.get(1) + "'s time of " + playerTimes.get(1) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(0) + " seconds. <br>" + winningPlayers.get(0) + " has won the tournament!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                gameState ++;
            }
            if (playerTimes.get(1) < playerTimes.get(0)) {
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "Congratulations " + winningPlayers.get(1) + ", you beat " + winningPlayers.get(0) + "'s time of " + playerTimes.get(0) + " seconds <br>" +
                        "With your lower time of " + playerTimes.get(1) + " seconds. <br>" + player4 + " has won the tournament!</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                gameState ++;
            }

            if (playerTimes.get(0) == playerTimes.get(1)){
                JLabel instructions = new JLabel("<html><div style='text-align: center;'>" +
                        "You both tied eachother with a time of " + playerTimes.get(0) + " seconds. <br>" +
                        "Play again to break the tie</div></html>",
                        SwingConstants.CENTER);
                popUp.add(instructions, BorderLayout.CENTER);
                tieVariable = 1;

            }
            popUp.add(buttonPanel, BorderLayout.SOUTH);
            // Start button
            if (tieVariable == 1) {
                System.out.print(tieVariable);
                System.out.print(playerTimes.size());
                System.out.print(gameState);

                JButton rematchButton = new JButton("Rematch");
                rematchButton.addActionListener(e -> {
                    playerTimes.clear();
                    resetGame();
                    popUp.dispose();
                });
                buttonPanel.add(rematchButton, BorderLayout.SOUTH);
                popUp.pack();
                popUp.setLocationRelativeTo(this);
                popUp.setVisible(true);

            }
            else {
                JButton replayButton = new JButton("Replay Tournament");
                replayButton.addActionListener(e -> {
                    resetGame();
                    gameState = 0;
                    playerTimes.clear();
                    winningPlayers.clear();
                    popUp.dispose();
                });
                JButton quitButton = new JButton("Return To Menu");
                quitButton.addActionListener(e -> {

                    popUp.dispose();
                    this.dispose();
                });
                buttonPanel.add(quitButton, BorderLayout.EAST);
                buttonPanel.add(replayButton, BorderLayout.WEST);
                popUp.pack();
                popUp.setLocationRelativeTo(this);
                popUp.setVisible(true);

            }

        }
    }
    /**
     * Initializes the game grid by setting up key listener for cheat activation
     * and action listener for card buttons to handle flipping and matching.
     * Also configures fonts, colors, and initial text for card buttons.
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
     * Starts the countdown timer for the game. It updates the timer label every second until all cards are matched,
     * at which point it stops the timer.
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
     * Checks if all cards are matched. If so, it triggers the end of the game.
     */
    private void checkForWin() {
        if (gameBoard.areAllCardsMatched()) {
            endGame();
        }
    }
    /**
     * Reveals all cards on the game board by setting their text to their identities.
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
     * Resets the game board by removing all components, creating a new game board, initializing the game grid, and
     * resetting all game-related variables such as flipped cards, time elapsed, and timer label.
     */
    private void resetGame() {

        gridPanel.removeAll(); // Remove all components from gridPanel
        gridPanel.revalidate(); // Revalidate to clear removed components from UI
        gridPanel.repaint();
        gameBoard = new GameBoard(size, difficulty);
        initializeGameGrid();
        System.out.print(playerTimes.size());
        System.out.print(gameState);


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
     * Stops the countdown timer, adds the time elapsed to the player times list, and triggers the display of the end game pop-up.
     */
    private void endGame() {
        // Options for the dialog
        countdownTimer.stop();
        playerTimes.add(timeElapsed);
        timeElapsed = 0;

        showEndGamePopUp();

    }

    /**
     * Entry point of the application. It creates an instance of the {@code Tournament} class with specified parameters
     * and makes the frame visible.
     *
     * @param args The command-line arguments passed to the application (not used in this method).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tournament frame = new Tournament(2, 2, "tbryson5", "anshgupta", "RoyalG", "Olivia"); // Example: 4x4 grid, easy difficulty
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }
}