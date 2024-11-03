import static org.junit.Assert.*;
import org.junit.Test;

import javax.swing.*;

public class GameBoardTest {

    @Test
    public void testResetBoard() {
        GameBoard gameBoard = new GameBoard(4, 1);

        // Flip some cards and make them matched
        gameBoard.flipCard(0, 0);
        gameBoard.flipCard(0, 1);
        gameBoard.getBoard()[0][0].matchCard();
        gameBoard.getBoard()[0][1].matchCard();

        // Reset the board
        gameBoard.resetBoard();

        // Check if all cards are not flipped and not matched
        for (int i = 0; i < gameBoard.size; i++) {
            for (int j = 0; j < gameBoard.size; j++) {
                assertFalse(gameBoard.getBoard()[i][j].isFlipped());
                assertFalse(gameBoard.getBoard()[i][j].isMatched());
            }
        }
    }

    @Test
    public void testFlipCard() {
        GameBoard gameBoard = new GameBoard(4, 1);

        // Flip a card and check if it's flipped
        gameBoard.flipCard(0, 0);
        assertTrue(gameBoard.getBoard()[0][0].isFlipped());
    }

    @Test
    public void testAreAllCardsMatched() {
        // Create a game board with a known size and difficulty
        int size = 4;
        int difficulty = 1;
        GameBoard gameBoard = new GameBoard(size, difficulty);

        // Ensure initially all cards are not matched
        assertFalse(gameBoard.areAllCardsMatched());

        // Flip all cards to matched
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard.getBoard()[i][j].isMatched = true;
            }
        }

        // Ensure now all cards are matched
        assertTrue(gameBoard.areAllCardsMatched());
    }

    @Test
    public void testMatchCard() {
        // Create a game board with known size and difficulty
        int size = 4;
        int difficulty = 1;
        GameBoard gameBoard = new GameBoard(size, difficulty);

        // Create two cards with known identities
        Card equationCard = new Card(new JButton());
        equationCard.setIdentity("1 + 1");
        Card valueCard = new Card(new JButton());
        valueCard.setIdentity("2");

        // Match the cards
        boolean matched = gameBoard.MatchCard(equationCard, valueCard);

        // Ensure the cards are matched
        assertTrue(matched);
        assertTrue(equationCard.isMatched);
        assertTrue(valueCard.isMatched);

        // Create two cards that do not match
        Card equationCard2 = new Card(new JButton());
        equationCard2.setIdentity("1 + 1");
        Card valueCard2 = new Card(new JButton());
        valueCard2.setIdentity("3");

        // Attempt to match the cards
        boolean notMatched = gameBoard.MatchCard(equationCard2, valueCard2);

        // Ensure the cards are not matched
        assertFalse(notMatched);
        assertFalse(equationCard2.isMatched);
        assertFalse(valueCard2.isMatched);
    }



}
