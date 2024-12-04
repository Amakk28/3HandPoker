import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;

class MyTest {

	private Deck deck;
	private Dealer dealer;
	ArrayList<Card> dealerHand;

	@BeforeEach
	void setup() {
		deck = new Deck();
		dealer = new Dealer();
		dealerHand = new ArrayList<>();
	}

	@Test
	void ThreeCardLogicThreeOfAKindTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('H', 1));
		hand.add(new Card('C', 2));
		hand.add(new Card('S', 3));
		assertFalse(ThreeCardLogic.ThreeOfAKind(hand));
		hand.clear();
		hand.add(new Card('H', 1));
		hand.add(new Card('C', 3));
		hand.add(new Card('S', 4));
		assertFalse(ThreeCardLogic.ThreeOfAKind(hand));
		hand.clear();
		hand.add(new Card('H', 1));
		hand.add(new Card('C', 8));
		hand.add(new Card('S', 12));
		assertFalse(ThreeCardLogic.ThreeOfAKind(hand));
	}

	@Test
	void ThreeCardLogicThreeOfAKindTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('H', 1));
		hand.add(new Card('C', 1));
		hand.add(new Card('S', 1));
		assertTrue(ThreeCardLogic.ThreeOfAKind(hand));
		hand.clear();
		hand.add(new Card('H', 12));
		hand.add(new Card('S', 12));
		hand.add(new Card('S', 12));
		assertTrue(ThreeCardLogic.ThreeOfAKind(hand));
		hand.clear();
		hand.add(new Card('D', 8));
		hand.add(new Card('H', 8));
		hand.add(new Card('S', 8));
		assertTrue(ThreeCardLogic.ThreeOfAKind(hand));
	}

	@Test
	void ThreeCardLogicStraightFlushTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('C', 10));
		hand.add(new Card('C', 9));
		hand.add(new Card('C', 8));
		assertTrue(ThreeCardLogic.StraightFlush(hand));
		hand.clear();
		hand.add(new Card('D', 12));
		hand.add(new Card('D', 11));
		hand.add(new Card('D', 10));
		assertTrue(ThreeCardLogic.StraightFlush(hand));
	}

	@Test
	void ThreeCardLogicStraightFlushTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('H', 5));
		hand.add(new Card('D', 4));
		hand.add(new Card('H', 3));
		assertFalse(ThreeCardLogic.StraightFlush(hand));
		hand.clear();
		hand.add(new Card('H', 5));
		hand.add(new Card('C', 12));
		hand.add(new Card('H', 3));
		assertFalse(ThreeCardLogic.StraightFlush(hand));
	}

	@Test
	void ThreeCardLogicStraightTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 8));
		hand.add(new Card('C', 7));
		hand.add(new Card('D', 6));
		assertTrue(ThreeCardLogic.isStraight(hand));
		hand.clear();
		hand.add(new Card('D', 12));
		hand.add(new Card('C', 11));
		hand.add(new Card('D', 10));
		assertTrue(ThreeCardLogic.isStraight(hand));

	}

	@Test
	void ThreeCardLogicStraightTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('C', 7));
		hand.add(new Card('D', 6));
		assertFalse(ThreeCardLogic.isStraight(hand));
		hand.clear();
		hand.add(new Card('D', 12));
		hand.add(new Card('C', 7));
		hand.add(new Card('D', 5));
		assertFalse(ThreeCardLogic.isStraight(hand));
	}

	@Test
	void ThreeCardLogicFlushTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 7));
		hand.add(new Card('D', 6));
		assertTrue(ThreeCardLogic.isFlush(hand));
		hand.clear();
		hand.add(new Card('S', 3));
		hand.add(new Card('S', 4));
		hand.add(new Card('S', 6));
		assertTrue(ThreeCardLogic.isFlush(hand));
	}

	@Test
	void ThreeCardLogicFlushTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 7));
		hand.add(new Card('C', 6));
		assertFalse(ThreeCardLogic.isFlush(hand));
		hand.clear();
		hand.add(new Card('D', 2));
		hand.add(new Card('S', 3));
		hand.add(new Card('C', 4));
		assertFalse(ThreeCardLogic.isFlush(hand));
	}

	@Test
	void ThreeCardLogicPairTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 6));
		assertTrue(ThreeCardLogic.isPair(hand));
		hand.clear();
		hand.add(new Card('D', 6));
		hand.add(new Card('S', 9));
		hand.add(new Card('D', 9));
		assertTrue(ThreeCardLogic.isPair(hand));
	}

	@Test
	void ThreeCardLogicPairTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 7));
		hand.add(new Card('C', 6));
		assertFalse(ThreeCardLogic.isPair(hand));
		hand.clear();
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 10));
		hand.add(new Card('C', 5));
		assertFalse(ThreeCardLogic.isPair(hand));
	}

	@Test
	void ThreeCardLogicHighTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 7));
		hand.add(new Card('C', 6));
		assertTrue(ThreeCardLogic.High(hand));
	}

	@Test
	void ThreeCardLogicHighTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 6));
		assertFalse(ThreeCardLogic.High(hand));
	}

	@Test
	void ThreeCardLogicEvalHandTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 6));
		assertEquals(5, ThreeCardLogic.evalHand(hand)); //test for pair
		hand.clear();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 8));
		hand.add(new Card('C', 7));
		assertEquals(3, ThreeCardLogic.evalHand(hand)); //test for straight
		hand.clear();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 9));
		assertEquals(2, ThreeCardLogic.evalHand(hand)); //test for three of a kind

	}

	@Test
	void ThreeCardLogicEvalHandTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 8));
		hand.add(new Card('D', 7));
		assertEquals(1, ThreeCardLogic.evalHand(hand));
		hand.clear();
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 5));
		hand.add(new Card('D', 7));
		assertEquals(4, ThreeCardLogic.evalHand(hand));
		hand.clear();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 7));
		hand.add(new Card('C', 6));
		assertEquals(0, ThreeCardLogic.evalHand(hand));

	}

	@Test
	void ThreeCardLogicEvalPPWinningsTest1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 6));
		assertEquals(5, ThreeCardLogic.evalPPWinnings(hand, 5));
		hand.clear();
		hand.add(new Card('C', 10));
		hand.add(new Card('C', 9));
		hand.add(new Card('C', 8));
		assertEquals(40, ThreeCardLogic.evalPPWinnings(hand, 1));
		hand.clear();
		hand.add(new Card('C', 12));
		hand.add(new Card('C', 9));
		hand.add(new Card('C', 8));
		assertEquals(3, ThreeCardLogic.evalPPWinnings(hand, 1));
	}

	@Test
	void ThreeCardLogicEvalPPWinningsTest2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 9));
		hand.add(new Card('H', 9));
		hand.add(new Card('C', 6));
		assertEquals(5, ThreeCardLogic.evalPPWinnings(hand, 5));
	}

	@Test
	void ThreeCardLogicEvalPPWinningsTest3() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new Card('D', 11));
		hand.add(new Card('H', 11));
		hand.add(new Card('C', 11));
		assertNotEquals(5, ThreeCardLogic.evalPPWinnings(hand, 5));
		hand.add(new Card('D', 11));
		hand.add(new Card('H', 11));
		hand.add(new Card('C', 11));
		assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, 5));
	}

	@Test
	void ThreeCardLogicCompareHandsTest1() {
		ArrayList<Card> player = new ArrayList<Card>();
		player.add(new Card('D', 11)); //three of a kind
		player.add(new Card('H', 11));
		player.add(new Card('C', 11));
		assertEquals(2, ThreeCardLogic.evalHand(player));

		ArrayList<Card> dealer = new ArrayList<Card>(); //straight flush: dealer has better hand
		dealer.add(new Card('C', 10));
		dealer.add(new Card('C', 9));
		dealer.add(new Card('C', 8));
		assertEquals(1, ThreeCardLogic.compareHands(player, dealer));

	}

	@Test
	void ThreeCardLogicCompareHandsTest2() {
		ArrayList<Card> player = new ArrayList<Card>();
		player.add(new Card('C', 10)); //straight flush: player has better hand
		player.add(new Card('C', 9));
		player.add(new Card('C', 8));

		ArrayList<Card> dealer = new ArrayList<Card>(); //pair: dealer loses
		dealer.add(new Card('C', 10));
		dealer.add(new Card('H', 10));
		dealer.add(new Card('D', 8));
		assertEquals(2, ThreeCardLogic.compareHands(player, dealer));

	}

	@Test
	void ThreeCardLogicCompareHandsTest3() {
		ArrayList<Card> player = new ArrayList<Card>();
		player.add(new Card('C', 10)); //straight flush
		player.add(new Card('C', 9));
		player.add(new Card('C', 8));

		ArrayList<Card> dealer = new ArrayList<Card>(); //straight flush: dealer has better hand
		dealer.add(new Card('C', 12));
		dealer.add(new Card('C', 11));
		dealer.add(new Card('C', 10));
		assertEquals(1, ThreeCardLogic.compareHands(player, dealer));

	}

	// DECK AND DEALER CLASS TEST CASES -------------------------------------------------------------------------


	@Test
	void DeckClassTest1(){
		assertEquals(52, deck.newCards.size());
	}

	@Test
	void DeckClassTest2(){
		deck.newDeck();
		Deck deck2 = new Deck();
		assertEquals(52, deck.size());
		assertEquals(52, deck2.size());
		assertNotEquals(deck, deck2);
	}

	@Test
	void DeckClassTest3(){
		Set<Card> set = new HashSet<>(deck); //checking for uniqueness in cards by using treeset
		assertEquals(52, set.size());
	}

	@Test
	void DeckClassTest4(){
		deck.newDeck();
		deck.clear();
		assertEquals(0, deck.size());
	}

	@Test
	void DeckClassTest5(){
		deck.newDeck();
		deck.clear();
		deck.newDeck();
		assertEquals(52, deck.size());
	}

	@Test
	void DealerClassTest1(){
		assertEquals(52, dealer.theDeck.size());
	}

	@Test
	void DealerClassTest2(){
		assertEquals(3, dealer.dealHand().size());
	}

	@Test
	void DealerClassTest3(){
		ArrayList<Card> dealer1 = dealer.dealHand();
		assertEquals(3, dealer1.size());

	}

	@Test
	void DealerClassTest4(){
		Set<Card> set = new HashSet<>(dealer.dealHand()); //checking for uniqueness in dealer's hand
		assertEquals(3, set.size());
	}

	@Test
	void DealerClassTest5(){
		Dealer dealer2 = new Dealer();
		assertNotEquals(dealer, dealer2);
	}



}
