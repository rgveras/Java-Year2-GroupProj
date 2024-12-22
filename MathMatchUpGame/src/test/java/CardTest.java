import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import javax.swing.JButton;

public class CardTest {

    @Test
    public void testSetIdentity() {
        JButton button = new JButton();
        Card card = new Card(button);
        card.setIdentity("Ace of Spades");
        assertEquals("Ace of Spades", card.getIdentity());
    }

    @Test
    public void testFlipCard() {
        JButton button = new JButton();
        Card card = new Card(button);
        assertFalse(card.isFlipped());
        card.flipCard();
        assertTrue(card.isFlipped());
    }

    @Test
    public void testMatchCard() {
        JButton button = new JButton();
        Card card = new Card(button);
        assertFalse(card.isMatched());
        card.matchCard();
        assertTrue(card.isMatched());
    }

    @Test
    public void testReset() {
        JButton button = new JButton();
        Card card = new Card(button);
        card.flipCard();
        card.matchCard();
        assertTrue(card.isFlipped());
        assertTrue(card.isMatched());
        card.reset();
        assertFalse(card.isFlipped());
        assertFalse(card.isMatched());
    }

    @Test
    public void testIdentityPersistenceAfterFlip() {
        JButton button = new JButton();
        Card card = new Card(button);
        card.setIdentity("Ace of Spades");
        card.flipCard();
        assertEquals("Ace of Spades", card.getIdentity());
    }

    @Test
    public void testIdentityPersistenceAfterReset() {
        JButton button = new JButton();
        Card card = new Card(button);
        card.setIdentity("Ace of Spades");
        card.reset();
        assertEquals("Ace of Spades", card.getIdentity());
    }

    @Test
    public void testMultipleCards() {
        JButton button1 = new JButton();
        Card card1 = new Card(button1);
        card1.setIdentity("Card 1");

        JButton button2 = new JButton();
        Card card2 = new Card(button2);
        card2.setIdentity("Card 2");

        assertFalse(card1.isFlipped());
        assertFalse(card2.isFlipped());

        assertFalse(card1.isMatched());
        assertFalse(card2.isMatched());

        card1.flipCard();
        assertTrue(card1.isFlipped());
        assertFalse(card2.isFlipped());

        card2.matchCard();
        assertFalse(card1.isMatched());
        assertTrue(card2.isMatched());
    }

    @Test
    public void testNullIdentity() {
        JButton button = new JButton();
        Card card = new Card(button);
        card.setIdentity(null);
        assertNull(card.getIdentity());
    }
}
