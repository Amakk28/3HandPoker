import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {

    public Deck() {
        newDeck(); //creates a new deck of cards

    }
    ArrayList<Card> newCards; //new deck of cards
    public void newDeck() {
        this.clear(); //clears the deck
        newCards = new ArrayList<>(); //creates a new deck of cards
        char[] suits = {'C', 'D', 'H', 'S'}; //suits of the cards
        for (char suit : suits) { //for each suit
            for(int i = 2; i < 15; i++){ //for each value
                newCards.add(new Card(suit, i)); //add a new card to the deck
            }
        }
        Collections.shuffle(newCards); //shuffles the deck
        this.addAll(newCards); //adds the shuffled deck to the deck
    }
}
