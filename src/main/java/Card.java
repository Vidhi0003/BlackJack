import java.util.ArrayList;
import java.util.Random;

public class Card {
    private String suit;
    private int value;

    public Card(String theSuit, int theValue){
        suit = theSuit;
        value = theValue;
    }
    public int getValue(){
        return value;
    }

    //this function is to get the image of the card and display it accordingly
    public String getCard(ArrayList<String> faceCards){
        if(value == 1 || value == 11){
            return "ace_of_" + suit + ".png";
        }
        else if (value == 10){
            //faceCards has 12 cards (three face cards and 4 suits) so this gets a random face card (queen, king or jack)
            Random rand = new Random();
            int randIndex = rand.nextInt(faceCards.size());
            String randFace = faceCards.get(randIndex);
            faceCards.remove(randFace);
            //it will display the face card depending on what suit this card is
            return randFace + "_of_" + suit + ".png";
        }
        return value + "_of_" + suit + ".png";
    }

}
