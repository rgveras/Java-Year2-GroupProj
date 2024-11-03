import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A graphical user interface for displaying tutorial instructions with navigation buttons.
 * @author Zih Yu Liao
 */
public class TutorialPage extends JFrame {
    /**
     * Constructs a new TutorialPage window.
     */
    private int currentInstruction = 1; // Track current instruction

    public TutorialPage() {
        // Set the frame size to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setUndecorated(true); // Remove window decorations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load initial background image
        ImageIcon backgroundImage = new ImageIcon("img/inst1.png"); // Assuming inst1.png is the first instruction image
        Image scaledImage = backgroundImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        // Create "NEXT" button
        JButton nextButton = new JButton("NEXT");
        nextButton.setForeground(Color.BLACK);
        nextButton.setBackground(new Color(255, 165, 0)); // Dodger Blue
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextButton.setFocusPainted(false);
        nextButton.setBounds(screenSize.width - 150, screenSize.height - 100, 100, 40);


        // Create "PREVIOUS" button
        JButton prevButton = new JButton("PREVIOUS");
        prevButton.setForeground(Color.BLACK);
        prevButton.setBackground(new Color(255, 165, 0)); // Dodger Blue
        prevButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        prevButton.setFocusPainted(false);
        prevButton.setBounds(50, screenSize.height - 100, 120, 40);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentInstruction++;
                if (currentInstruction <= 5) {
                    // Load next instruction image
                    ImageIcon nextImage = new ImageIcon("img/inst" + currentInstruction + ".png");
                    Image scaledNextImage = nextImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
                    nextImage = new ImageIcon(scaledNextImage);
                    backgroundLabel.setIcon(nextImage);
                    prevButton.setEnabled(true); // Enable previous button if it was disabled
                } else {
                    nextButton.setEnabled(false); // Disable next button
                }
            }
        });


        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentInstruction > 1) {
                    currentInstruction--;
                    // Load previous instruction image
                    ImageIcon prevImage = new ImageIcon("img/inst" + currentInstruction + ".png");
                    Image scaledPrevImage = prevImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
                    prevImage = new ImageIcon(scaledPrevImage);
                    backgroundLabel.setIcon(prevImage);
                    nextButton.setEnabled(true); // Enable next button if it was disabled
                }else {
                    prevButton.setEnabled(false); // Disable next button
                }
            }
        });

        // Add "NEXT" and "PREVIOUS" buttons to the frame
        add(nextButton);
        add(prevButton);

        // Add bottom bar similar to the main page
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

        // Set menu bar to the frame
        setJMenuBar(menuBar);

        // Add background label behind all other components
        add(backgroundLabel);

        // Display the frame
        setVisible(true);
    }
    /**
     * Main method to run the TutorialPage.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TutorialPage();
            }
        });
    }
}