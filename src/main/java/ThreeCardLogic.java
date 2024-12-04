import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.util.List;

public class ThreeCardLogic {

    public static boolean ThreeOfAKind(ArrayList<Card>hand){ //if three of a kind
        if((hand.get(0).getValue() == hand.get(1).getValue()) && (hand.get(1).getValue() == hand.get(2).getValue()) && (hand.get(0).getValue() == hand.get(2).getValue())){
            return true;
        }
        return false;
    }

    public static boolean isStraight(ArrayList<Card>hand){ //if straight
        TreeSet<Integer> sortHand = new TreeSet<>(); //create a treeSet to sort the hand
        sortHand.add(hand.get(0).getValue()); //add the first card to the treeSet
        sortHand.add(hand.get(1).getValue()); //add the second card to the treeSet
        sortHand.add(hand.get(2).getValue()); //add the third card to the treeSet

        if(sortHand.size() != 3){ //if there are not 3 unique cards
            return false; //return false
        }

        Integer[] valArr = sortHand.toArray(new Integer[0]); //create an array of the treeSet
        if(valArr[1] == valArr[0]+1){ //if the second card is one more than the first card
            if(valArr[2] == valArr[1]+1){ //if the third card is one more than the second card
                return true; //if all three cards are in order
            }
        }
        //special case for A-2-3
        if(valArr[0] == 14 && valArr[1] == 2 && valArr[2] == 3){
            return true;
        }
        if(valArr[0] == 2 && valArr[1] == 14 && valArr[2] == 13){
            return true;
        }
        if(valArr[0] == 3 && valArr[1] == 2 && valArr[2] == 14){
            return true;
        }

        return false; //if none of the above
    }


    public static boolean isFlush(ArrayList<Card>hand){ //if flush
        if(hand.get(0).getSuit() == hand.get(1).getSuit()){ //if first two cards are the same suit
            if(hand.get(1).getSuit() == hand.get(2).getSuit()){ //if last two cards are the same suit
                return true; //if all three cards are the same suit
            }
        }
        return false; //if none of the above
    }

    public static boolean StraightFlush(ArrayList<Card>hand){ //if straight flush
        return isFlush(hand) && isStraight(hand); //if both straight and flush
    }

    public static boolean isPair(ArrayList<Card>hand){ //if pair
        if(hand.get(0).getValue() == hand.get(1).getValue()){ //if first two cards are the same
            return true;
        }
        if(hand.get(1).getValue() == hand.get(2).getValue()){ //if last two cards are the same
            return true;
        }
        if(hand.get(2).getValue() == hand.get(0).getValue()){ //if first and last cards are the same
            return true;
        }
        return false; //if none of the above
    }

    public static boolean High(ArrayList<Card>hand){ //if high card
        return !isPair(hand) && !isStraight(hand) && !isFlush(hand) && !ThreeOfAKind(hand) && !StraightFlush(hand); //if none of the above
    }



    public static int evalHand(ArrayList<Card> hand) {

        if(High(hand)){ //if high card
            return 0;
        }
        if(StraightFlush(hand)){ //if straight flush
            return 1;
        }
        if(ThreeOfAKind(hand)){ //if three of a kind
            return 2;
        }
        if(isStraight(hand)){ //if straight
            return 3;
        }
        if(isFlush(hand)){ //if flush
            return 4;
        }
        if(isPair(hand)){ //if pair
            return 5;
        }
        return -1; //if none of the above
    }

    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        switch(evalHand(hand)){
            case 1: return bet * 40; //straight flush
            case 2: return bet * 30; //three of a kind
            case 3: return bet * 6; //straight
            case 4: return bet * 3; //flush
            case 5: return bet * 1; //pair
            default: return 0;
        }
    }

    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {

        int dealerHand = evalHand(dealer);
        int playerHand = evalHand(player);


        if ((dealerHand == 0 && playerHand == 0) || (dealerHand == 5 && playerHand == 5)) { //both high or pair
            //get the highest card from both hands
            int dealerHigh = 0;
            int playerHigh = 0;
            for (int i = 0; i < dealer.size(); i++) { //get the highest card from both hands
                if (dealer.get(i).getValue() > dealerHigh) { //if dealer has a higher card
                    dealerHigh = dealer.get(i).getValue(); //set dealerHigh to that card
                }
                if (player.get(i).getValue() > playerHigh) { //if player has a higher card
                    playerHigh = player.get(i).getValue(); //set playerHigh to that card
                }
            }
            if (playerHigh > dealerHigh) { //if player has a higher card
                return 2; //player wins
            } else {
                return 1; //dealer wins
            }
        }
        else if ((dealerHand == 4 && playerHand == 4) || (dealerHand == 3 && playerHand == 3) || (dealerHand == 1 && playerHand == 1)) { // flush tie breaker
            int dealerSum = 0;
            int playerSum = 0;
            for (int i = 0; i < dealer.size(); i++) {
                dealerSum += dealer.get(i).getValue();
                playerSum += player.get(i).getValue();
            }
            if (playerSum > dealerSum) { //if player beats tie breaker k lets test
                return 2;
            } else {
                return 1;
            }
        }
        else if(dealerHand == 0 && playerHand > 0){ //when dealer gets a high card, and player has any greater hand
            return 2;
        }
        else if(playerHand == 0 && dealerHand > 0){ //when player gets a high card, and dealer has any greater hand
            return 1;
        }
        else if(dealerHand > playerHand && dealerHand != 0 && playerHand != 0){ //PLAYER WON
            return 2;//return 2 means player won
        }
        else if(dealerHand < playerHand && dealerHand != 0 && playerHand != 0){ //DEALER WON
            return 1; //return 1 means dealer won
        }

        return dealerHand > playerHand ? 1 : 2;
    }

}
