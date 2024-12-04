import java.util.ArrayList;

public class Dealer {

    Deck theDeck; //deck of cards
    ArrayList<Card> dealersHand; //dealer's hand

    Dealer(){
        theDeck = new Deck();
    } //constructor

    public ArrayList<Card> dealHand(){
        dealersHand = new ArrayList<>(); //creates a new hand
        if(theDeck.size() < 34){ //if the deck is running low
            theDeck.newDeck(); //create a new deck
        }
        if(theDeck.newCards.size() < 3){ //if there are less than 3 cards in the deck
            theDeck.newDeck(); //create a new deck
        }

        for(int i = 0; i<3; i++){ //deal 3 cards
            dealersHand.add(theDeck.newCards.remove(0)); //add a card to the hand
        }
        return dealersHand; //return the hand

    }
}
