import java.util.ArrayList;
public class BlackjackGameLogic
{
    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand)
    {
        int playerHandTotal = handTotal(playerHand1);
        int dealerHandTotal = handTotal(dealerHand);

        //bust cases
        if(playerHandTotal > 21)
        {
            return "dealer";
        }
        else if(dealerHandTotal > 21)
        {
            return "player";
        }
        //higher total cases
        else if(playerHandTotal > dealerHandTotal)
        {
            return "player";
        }
        else if(dealerHandTotal > playerHandTotal)
        {
            return "dealer";
        }
        //draw
        else
        {
            if(isBlackJack(playerHand1) && !isBlackJack(dealerHand)){
                return "player";
            }
            else if (!isBlackJack(playerHand1) && isBlackJack(dealerHand)){
                return "dealer";
            }
            else{
                return "push";
            }
        }
    }

    //this is a helper function that checks if a given hand is a blackjack
    public boolean isBlackJack(ArrayList<Card> hand){
        return handTotal(hand) == 21 && hand.size() == 2 &&
                ((hand.get(0).getValue() == 1 && hand.get(1).getValue() == 10) || (hand.get(1).getValue() == 1 && hand.get(0).getValue() == 10));
    }

    public int handTotal(ArrayList<Card> hand)
    {
        int total = 0;
        int numberOfAces = 0;

        for(Card card : hand)
        {
            int value = card.getValue();
            if(value == 1)
            {
                numberOfAces += 1;
                total += 11;
            }
            else if(value > 10)
            {
                total += 10;
            }
            else
            {
                total += value;
            }
        }


        while(total > 21 && numberOfAces > 0)
        {
            total -= 10;
            numberOfAces -= 1;
        }
        return total;

    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand)
    {
        int total = handTotal(hand);

        return total <= 16;
    }
}


