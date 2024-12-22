import javax.swing.*;

/**
 * Represents a card used in a matching game
 * @author Ty William Bryson
 */
public class Card {

    /** The identity of the card. */
    public String identity = "";

    /** Indicates whether the card has been matched. */
    public boolean isMatched = false;

    /** Indicates whether the card is flipped. */
    public boolean isFlipped = false;

    /** The button associated with the card. */
    public JButton button;

    /**
     * Constructs a new Card object with the given JButton.
     *
     * @param button the JButton associated with the card
     */
    public Card(JButton button) {
        this.button = button;
    }

    /**
     * Sets the identity of the card.
     *
     * @param value the value to set as the identity
     */
    public void setIdentity(String value) {
        this.identity = value;
    }

    /**
     * Flips the card.
     */
    public void flipCard() {
        isFlipped = !isFlipped;
    }

    /**
     * Matches the card.
     */
    public void matchCard() {
        isMatched = !isMatched;
    }

    /**
     * Resets the card to its initial state.
     */
    public void reset() {
        isFlipped = false;
        isMatched = false;
    }

    /**
     * Checks if the card is matched.
     *
     * @return true if the card is matched, false otherwise
     */
    public boolean isMatched() {
        return isMatched;
    }

    /**
     * Checks if the card is flipped.
     *
     * @return true if the card is flipped, false otherwise
     */
    public boolean isFlipped() {
        return isFlipped;
    }

    /**
     * Gets the identity of the card.
     *
     * @return the identity of the card
     */
    public String getIdentity() {
        return identity;
    }
}
