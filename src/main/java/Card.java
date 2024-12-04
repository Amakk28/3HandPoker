

public class Card {

    char suit; // C, D, H, S
    int value; // 2-14

    Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value;
    } //getter for value
    public char getSuit() {
        return suit;
    } //getter for suit
}
