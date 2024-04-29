import java.util.ArrayList;

public class BlackjackGame {
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BlackjackDealer theDealer;
    BlackjackGameLogic gameLogic;
    private double currentBet;
    //the amount currently bet from the user
    private double totalWinnings;
    //the total amount of value that the user has.
    public BlackjackGame(){
        currentBet = 0;
        totalWinnings = 0;

        theDealer = new BlackjackDealer();
        gameLogic = new BlackjackGameLogic();
    }
    public double getCurrentBet(){
        return currentBet;
    }
    public BlackjackDealer getDealer(){
        return theDealer;
    }
    public BlackjackGameLogic getGameLogic(){
        return gameLogic;
    }
    public void setPlayerHand(ArrayList<Card> pHand){
        playerHand = pHand;
    }
    public void setBankerHand(ArrayList<Card> bHand){
        bankerHand = bHand;
    }
    public double getTotalWinnings(){
        return totalWinnings;
    }
    public void setCurrentBet(double money){
        currentBet = money;
    }
    public void setTotalWinnings(double money){
        totalWinnings = money;
    }
    public double evaluateWinnings()
    {
        String winner = gameLogic.whoWon(playerHand, bankerHand);
        if(winner.equals("player"))
        {
            //if the player wins by blackjack
            if(gameLogic.isBlackJack(playerHand)){
                return 1.5 * currentBet;
            }
            return currentBet;
        }
        else if(winner.equals("dealer"))
        {
            if(gameLogic.isBlackJack(bankerHand)){
                return -(1.5 * currentBet);
            }
            return -currentBet;
        }
        return 0;
    }
    //This method will determine if the user won or lost their bet and return the amount won or lost based on the value in currentBet.


}



