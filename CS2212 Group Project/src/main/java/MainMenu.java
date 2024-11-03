import javax.swing.*;
import java.awt.event.*;

/**
 * Represents the MainMenu window for the main menu screen.
 * It extends JFrame and provides user interface for the main menu options.
 * @author Ricardo Veras
 */public class MainMenu extends JFrame {
    public JPanel panelMainMenu;
    private JButton switchUser;
    private JButton exitButton;
    private JLabel pageTitle;
    private JLabel gameTitle;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton highScoresButton;
    private JButton tutorialButton;

    public JLabel userID;
    private JButton instructorButton;
    private JButton developersButton;


    public MainMenu() {

        // If new game is selected, the corresponding screen opens
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                NewGame newGame = new NewGame();
                newGame.setContentPane(newGame.panelNewGame);
                newGame.setTitle("New Game");
                newGame.setSize(500, 500);
                newGame.setVisible(true);
                newGame.setDefaultCloseOperation(3);
            }
        });

        // If load game is selected, the load game screen loads
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SinglePlayer splayer = new SinglePlayer();
                splayer.setContentPane(splayer.panelSinglePlayer);
                splayer.setTitle("Single Player");
                splayer.setSize(500, 500);
                splayer.setVisible(true);
                splayer.setDefaultCloseOperation(3);
            }
        });

        // If the exit button is pressed, you are returned to the previous screen
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });


        // If the switch user button is selected, the sign in screen is loaded to sign in to a new user account
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
        instructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Password password = new Password(10);
                password.setContentPane(password.panelPassword);
                password.setTitle("Password");
                password.setSize(500, 500);
                password.setVisible(true);
                password.setDefaultCloseOperation(3);
            }
        });

        developersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Password password = new Password(10);
                password.setContentPane(password.panelPassword);
                password.setTitle("Password");
                password.setSize(500, 500);
                password.setVisible(true);
                password.setDefaultCloseOperation(3);
            }
        });


        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the MainMenu window
                dispose();

                // Open the HighScore window
                HighScore highScore = new HighScore();
                highScore.setTitle("High Score");
                //highScore.setSize(500, 500);
                highScore.setVisible(true);
                highScore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the MainMenu window
                dispose();

                // Open the TutorialPage window
                TutorialPage tutorialPage = new TutorialPage();
                tutorialPage.setTitle("Tutorial");
                tutorialPage.setVisible(true);
                tutorialPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });


    }



    /**
     * Main method to create a main menu screen and display it.
     * @param args Command-line arguments.
     */    public static void main(String[] args) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.setContentPane(mainMenu.panelMainMenu);
        mainMenu.setTitle("Main Menu");
        mainMenu.setSize(500, 500);
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(3);

    }
}
