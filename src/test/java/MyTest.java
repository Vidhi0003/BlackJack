import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class MyTest {


	private BlackjackDealer dealer;

	@Test
	public void testDeckSize()
	{
		dealer = new BlackjackDealer();
		dealer.generateDeck();
		int wantedSize = 52;
		assertEquals(wantedSize, dealer.deckSize());
	}

	@Test
	public void testEmptyDeckSize()
	{
		dealer = new BlackjackDealer();
		assertEquals(0, dealer.deckSize());
	}

	@Test
	public void testEmptyShuffle()
	{
		dealer = new BlackjackDealer();
		dealer.shuffleDeck();

		assertEquals(0, dealer.deckSize());
	}

	@Test
	public void testSingleShuffle()
	{
		dealer = new BlackjackDealer();
		dealer.generateDeck();
		dealer.drawOne();
		dealer.shuffleDeck();

		assertEquals(51, dealer.deckSize());
	}

	@Test
	public void testDealHand()
	{
		dealer = new BlackjackDealer();
		dealer.generateDeck();

		ArrayList<Card> hand = dealer.dealHand();

		assertEquals(2, hand.size());
	}

	@Test
	public void testDrawEmptyDeck()
	{
		dealer = new BlackjackDealer();
		dealer.generateDeck();

		//This draws all the cards until the deck is empty
		while(dealer.deckSize() > 0)
		{
			dealer.drawOne();

		}

		assertNull(dealer.drawOne());
	}

	@Test
	public void testDrawOne()
	{
		dealer = new BlackjackDealer();
		dealer.generateDeck();
		int deckSize = dealer.deckSize();

		Card draw = dealer.drawOne();

		int newDeckSize = dealer.deckSize();

		assertEquals(deckSize - 1, newDeckSize);
	}

	@Test
	public void testCurrentBet()
	{
		BlackjackGame newGame = new BlackjackGame();
		newGame.setCurrentBet(60.0);

		assertEquals(60.0, newGame.getCurrentBet());
	}

	@Test
	public void testEvaluateWinningsWithBlackJack()
	{
		BlackjackGame newGame = new BlackjackGame();
		newGame.setCurrentBet(100.0);

		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("hearts", 1));
		playerHand.add(new Card("diamonds", 10));
		newGame.setPlayerHand(playerHand);

		ArrayList<Card> bankerHand = new ArrayList<>();
		bankerHand.add(new Card("spades", 10));
		bankerHand.add(new Card("clubs", 5));
		newGame.setBankerHand(bankerHand);

		double howMuch = newGame.evaluateWinnings();

		assertEquals(150.0, howMuch);
	}

	@Test
	public void testWhoWonPlayerBusts()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();

		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("hearts", 6));
		playerHand.add(new Card("diamonds", 9));
		playerHand.add(new Card("diamonds", 10));

		ArrayList<Card> bankerHand = new ArrayList<>();
		bankerHand.add(new Card("spades", 10));
		bankerHand.add(new Card("clubs", 5));

		assertEquals("dealer", game.whoWon(playerHand, bankerHand));
	}

	@Test
	public void testWhoWonBankerBusts()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();

		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("hearts", 7));
		playerHand.add(new Card("diamonds", 9));

		ArrayList<Card> bankerHand = new ArrayList<>();
		bankerHand.add(new Card("clubs", 10));
		bankerHand.add(new Card("spades", 8));
		bankerHand.add(new Card("diamonds", 6));

		assertEquals("player", game.whoWon(playerHand, bankerHand));
	}

	@Test
	public void testHandTotal()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();

		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("hearts", 7));
		playerHand.add(new Card("diamonds", 8));
		playerHand.add(new Card("club", 4));
		int expected = 19;
		int testHand = game.handTotal(playerHand);

		assertEquals(expected, testHand);
	}

	@Test
	public void testEvaluateBankerEqual16()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("hearts", 8));
		hand.add(new Card("diamonds", 8));
		boolean check16 = game.evaluateBankerDraw(hand);

		assertTrue(check16); //this should be true since it will keep drawing until its 17 or greater
	}

	@Test
	public void testEvaluateBankerGreater16()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("hearts", 9));
		hand.add(new Card("diamonds", 8));
		boolean checkGreater16 = game.evaluateBankerDraw(hand);

		assertFalse(checkGreater16);
	}

	@Test
	public void testEvaluateBankerLess16()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("hearts", 5));
		hand.add(new Card("diamonds", 8));
		boolean checkLess16 = game.evaluateBankerDraw(hand);

		assertTrue(checkLess16);
	}

	@Test
	public void testIsBlackJack()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();
		ArrayList<Card> hand = new ArrayList<>();

		Card cardOne = new Card("heart", 10);
		Card cardTwo = new Card("club", 1);

		hand.add(cardOne);
		hand.add(cardTwo);

		boolean check = game.isBlackJack(hand);

		assertTrue(check);
	}

	@Test
	public void testIsBlackJackFalse()
	{
		BlackjackGameLogic game = new BlackjackGameLogic();
		ArrayList<Card> hand = new ArrayList<>();

		Card cardOne = new Card("spades", 6);
		Card cardTwo = new Card("hearts", 2);

		hand.add(cardOne);
		hand.add(cardTwo);

		boolean check = game.isBlackJack(hand);

		assertFalse(check);
	}

}
