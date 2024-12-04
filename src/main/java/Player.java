import java.util.ArrayList;

public class Player {

    ArrayList<Card> hand;
    int anteBet; //bet for the ante
    int playBet; //bet for the play
    int pairPlusBet; //bet for the pair plus
    int totalWinnings; //total winnings
    boolean folded = false; //if the player has folded
    Player(){
        hand = new ArrayList<>(); //creates a new hand
        anteBet = 0; //initializes the ante bet
        playBet = 0; //initializes the play bet
        pairPlusBet = 0; //initializes the pair plus bet
        totalWinnings = 0; //initializes the total winnings
    }

}
