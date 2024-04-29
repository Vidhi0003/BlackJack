import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer
{
    private ArrayList<Card> deckOfCards;

    public BlackjackDealer(){
        deckOfCards = new ArrayList<>();
    }
    public void generateDeck()
    {

        String[] suits = {"hearts", "spades", "clubs", "diamonds"};

        int ace = 1; //This value will be changed if necessary but when creating an ace card it will be 1
        int two = 2;
        int three = 3;
        int four = 4;
        int five = 5;
        int six = 6;
        int seven = 7;
        int eight = 8;
        int nine = 9;
        int ten = 10;
        int jack = 10;
        int king = 10;
        int queen = 10;

        int[] values = {two, three, four, five, six, seven, eight, nine, ten, king, queen, jack, ace};

        for(String suit : suits)
        {
            for(int value : values)
            {
                Card card = new Card(suit, value);
                deckOfCards.add(card);
            }
        }
    }


    //returns an arraylist of 2 cards
    public ArrayList<Card> dealHand()
    {
        ArrayList<Card> hand = new ArrayList<>();
        for(int i = 0; i < 2; i++)
        {
            Card card = drawOne();
            hand.add(card);
        }

        return hand;
    }

    public Card drawOne()
    {
        if(deckOfCards.isEmpty())
        {
            return null;
        }

        return deckOfCards.remove(deckSize() - 1);
    }

    public void shuffleDeck()
    {
        Collections.shuffle(deckOfCards);
    }


    public int deckSize()
    {
        return deckOfCards.size();
    }
}
