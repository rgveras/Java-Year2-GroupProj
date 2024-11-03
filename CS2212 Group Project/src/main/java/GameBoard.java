import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 * Represents the game board for the matching game.
 * @author Ty William Bryson
 * @author Ansh Gupta
 */
public class GameBoard {
    Card[][] gameBoard;
    int size;
    int difficulty;

    String[][] EasyQuestions = {{"0 + 1", "1"},
            {"1 + 1", "2"}, {"1 + 2", "3"}, {"2 + 2", "4"}, {"3 + 2", "5"}, {"1 + 5", "6"}, {"3 + 4", "7"}, {"1 + 7", "8"}, {"4 + 5", "9"}, {"1 + 9", "10"},
            {"1 + 10", "11"}, {"10 + 2", "12"}, {"12 + 1", "13"}, {"13 + 1", "14"}, {"14 + 1", "15"}, {"9 + 7", "16"}, {"4 + 13", "17"}, {"7 + 11", "18"},
            {"7 + 12", "19"}, {"17 + 3", "20"}, {"15 + 6", "21"}, {"15 + 7", "22"}, {"22 + 1", "23"}, {"22 + 2", "24"}, {"5 + 20", "25"}, {"18 + 8", "26"},
            {"10 + 17", "27"}, {"24 + 4", "28"}, {"26 + 3", "29"}, {"18 + 12", "30"}, {"21 + 10", "31"}, {"12 + 20", "32"}, {"28 + 5", "33"}, {"5 + 29", "34"},
            {"2 + 33", "35"}, {"1 + 35", "36"}, {"2 + 35", "37"}, {"33 + 5", "38"}, {"16 + 23", "39"}, {"20 + 20", "40"}, {"28 + 13", "41"}, {"3 + 39", "42"},
            {"30 + 13", "43"}, {"19 + 25", "44"}, {"44 + 1", "45"}, {"39 + 7", "46"}, {"23 + 24", "47"}, {"6 + 42", "48"}, {"23 + 26", "49"}, {"31 + 19", "50"}
    };
    String[][] MediumQuestions = {{"0 - 1", "-1"},
            {"1 - 1", "0"}, {"7 - 2", "5"}, {"5 - 3", "2"}, {"9 - 3", "6"}, {"2 + 6","8"}, {"3 + 7", "10"}, {"4 - 1","3"}, {"6 + 3", "9"}, {"8 - 7", "1"},
            {"11 - 1", "10"}, {"16 - 5", "11"}, {"15 - 3", "12"}, {"19 - 14", "5"}, {"12 + 6","18"}, {"13 + 6", "19"}, {"14 - 21","-7"}, {"12 + 4", "16"}, {"18 - 16", "2"},
            {"17 - 4", "13"}, {"29 - 11", "18"}, {"19 - 3", "16"}, {"27 - 8", "19"}, {"22 + 4","26"}, {"23 + 10", "33"}, {"28 - 11","17"}, {"20 + 14", "34"}, {"38 - 19", "19"},
            {"40 - 11", "29"}, {"53 - 23", "30"}, {"61 - 30", "31"}, {"39 - 14", "25"}, {"16 + 17","33"}, {"20 + 14", "34"}, {"56 - 21","35"}, {"50 + 4", "54"}, {"58 - 21", "37"},
            {"70 - 32", "38"}, {"96 - 57", "39"}, {"65 - 25", "40"}, {"69 - 24", "45"}, {"32 + 10","42"}, {"13 + 30", "43"}, {"74 - 30","44"}, {"31 + 14", "45"}, {"68 - 22", "46"},
            {"99 - 52", "47"}, {"86 - 38", "48"}, {"55 - 6", "49"}, {"100 - 50", "50"},
    };
    String[][] HardQuestions = {{"1 / 1", "1"}, {"16 / 8", "2"}, {"40 - 37", "3"}, {"24 / 6", "4"}, {"5 * 1", "5"}, {"6 / 1", "6"}, {"14 - 7", "7"},
            {"27 - 19", "8"}, {"2 + 7", "9"}, {"6 + 4", "10"}, {"22 / 2", "11"}, {"72 / 6", "12"}, {"52 / 4", "13"}, {"7 * 2", "14"}, {"34 - 19", "15"},
            {"2 * 8", "16"}, {"41 - 24", "17"}, {"25 - 7", "18"}, {"1 * 19", "19"}, {"5 * 4", "20"}, {"13 + 8", "21"}, {"17 + 5", "22"}, {"19 + 4", "23"},
            {"23 + 1", "24"}, {"5 * 5", "25"}, {"1 + 25", "26"}, {"10 + 17", "27"}, {"40 - 12", "28"}, {"7 + 22", "29"}, {"31 - 1", "30"}, {"42 - 11", "31"},
            {"2 * 16", "32"}, {"16 + 17", "33"}, {"34 - 0", "34"}, {"70 / 2", "35"}, {"46 - 10", "36"}, {"74 / 2", "37"}, {"38 * 1", "38"}, {"39 / 1", "39"},
            {"41 - 11", "40"}, {"46 - 5", "41"}, {"48 - 6", "42"}, {"38 + 5", "43"}, {"15 + 29", "44"}, {"42 + 3", "45"}, {"46 / 1", "46"}, {"94 / 2", "47"},
            {"2 * 24", "48"}, {"28 + 21", "49"}, {"2 * 25", "50"}
    };
    /**
     * Initializes the game board with the specified size and difficulty level.
     *
     * @param size       the size of the game board (number of rows and columns)
     * @param difficulty the difficulty level of the game (1: Easy, 2: Medium, 3: Hard)
     */
    public GameBoard(int size, int difficulty) {
        this.size = size;
        this.difficulty = difficulty;
        this.gameBoard = new Card[size][size];
        createBoard();
    }
    private void createBoard() {
        // assign card equation and values in random order to each card in the grid
        List<String> values = generateCardValues();
        int Counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton();
                gameBoard[i][j] = new Card(button);
                gameBoard[i][j].setIdentity(values.get(Counter));

                Counter++;
            }
        }
    }
    /**
     * Generates a list of random card values based on the game difficulty.
     *
     * @return a list of random card values
     */
    private List<String> generateCardValues() {
        Set<Integer> uniqueIntegers = new HashSet<>();
        Random rand = new Random();
        int targetSize = (size * size) / 2; // Assuming you want half the number of grid spaces, unique pairs

        // Keep generating random numbers until the set is the desired size
        while (uniqueIntegers.size() < targetSize) {
            int randomNumber = rand.nextInt(50); // Adjust the 50 if you need a different range
            uniqueIntegers.add(randomNumber);
        }

        // Convert the set to a list (if order matters, a Set doesn't guarantee order)
        List<Integer> randomIntList = new ArrayList<>(uniqueIntegers);

        List<String> values = new ArrayList<>();
        int Counter = 0;
        for (int i = 0; i < ((size * size) / 2); i++) {
            if (difficulty == 1) {
                values.add(EasyQuestions[randomIntList.get(Counter)][0]);
                values.add(EasyQuestions[randomIntList.get(Counter)][1]);
                Counter++;
            }
            if (difficulty == 2) {
                values.add(MediumQuestions[randomIntList.get(Counter)][0]);
                values.add(MediumQuestions[randomIntList.get(Counter)][1]);
                Counter++;
            }
            if (difficulty == 3) {
                values.add(HardQuestions[randomIntList.get(Counter)][0]);
                values.add(HardQuestions[randomIntList.get(Counter)][1]);
                Counter++;
            }
        }
        Collections.shuffle(values);
        return values;
    }
    /**
     * Resets all cards in the game board to not flipped and not matched.
     */
    public void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j].isFlipped = false;
                gameBoard[i][j].isMatched = false;
            }
        }
    }
    /**
     * Flips the card at the specified row and column.
     *
     * @param row the row index of the card
     * @param col the column index of the card
     */
    public void flipCard(int row, int col) {
        gameBoard[row][col].flipCard();
    }
    /**
     * Gets the game board.
     *
     * @return the 2D array representing the game board
     */
    public Card[][] getBoard() {
        return gameBoard;
    }
    /**
     * Checks if all cards on the game board are matched.
     *
     * @return true if all cards are matched, false otherwise
     */
    public boolean areAllCardsMatched() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!gameBoard[i][j].isMatched) {
                    return false; // Found an unmatched card
                }
            }
        }
        return true; // All cards are matched
    }

    /**
     * Matches the equation card with the value card.
     *
     * @param equation the equation card
     * @param value    the value card
     * @return true if the cards match, false otherwise
     */
    public boolean MatchCard(Card equation, Card value) {
        // given two cards, check if the value card matches the equations [equation][1] value from the difficulty question array.
        // set both cards to matched (ismatched()) if true, break if false
        String[][] questions = difficulty == 1 ? EasyQuestions :
                difficulty == 2 ? MediumQuestions : HardQuestions;

        // Find the equation in the selected question set
        for (String[] question : questions) {
            // Check if the equation card's identity matches the question part [0]
            // and the value card's identity matches the answer part [1]
            if (equation.getIdentity().equals(question[0]) && value.getIdentity().equals(question[1])) {
                // If both match, set both cards to matched
                equation.isMatched = true;
                value.isMatched = true;
                System.out.println("Match found: " + question[0] + " = " + question[1]);
                return true;
            }
            if (equation.getIdentity().equals(question[1]) && value.getIdentity().equals(question[0])) {
                equation.isMatched = true;
                value.isMatched = true;
                System.out.println("Match found: " + question[0] + " = " + question[1]);
                return true;
            }
        }
        // If no match is found (though as per game design, there should always be a match)
        System.out.println("No match found for the cards.");
        return false;

    }
    /**
     * Main method for testing the game board.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        GameBoard testBoard = new GameBoard(4, 1);
        for (int i = 0; i < testBoard.size; i++) {
            for (int j = 0; j < testBoard.size; j++) {
                System.out.print(testBoard.getBoard()[i][j].getIdentity() + " ");
            }
            System.out.print("\n");
        }
    }


}