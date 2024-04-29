import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class JavaFX extends Application {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    //source of card pngs: https://opengameart.org/content/playing-cards-vector-png
    final int cardWidth = 100;
    final int cardHeight = 147;
    BlackjackGame game = new BlackjackGame();
    BlackjackGameLogic gameLogic;
    BlackjackDealer dealer;
    Text titleText, rulesText, dealerValue;
    TextField moneyText, betText;
    HBox titleBox, rulesBox, playBox, dealerCards;
    VBox homeBox;
    BorderPane mainPane;
    Button playButton, readyButton, placeBetsButton;
    HashMap<String, Scene> sceneMap;
    ArrayList<Card> userHand, dealerHand;
    ArrayList<String> faceCards;
    ImageView dealerCard, flippedCard;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //a hashmap that contains all the scenes
        sceneMap = new HashMap<>();

        //TEXTS
        titleText = new Text("BLACKJACK");
        titleBox = new HBox(titleText);
        titleBox.setStyle("-fx-background-color: #171a1f");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(25,0,0,0));

        titleText.setStyle("-fx-font-size: 56; -fx-fill: #ef5252; -fx-font-family: Broadway;");
        titleText.setTextAlignment(TextAlignment.CENTER);

        rulesText = new Text( "\nHow to play:\n" +
                "   At the start of the game, type in the integer values of much money you have and how much money you will be betting.\n" +
                "   After placing your bets, the game begins. Two cards are dealt to each player. The second card of the dealer is flipped down.\n" +
                "   The total value of your cards are determined by adding them.\n" +
                "       Value of each type of card:\n"+
                "       Numbered cards = what number it is\n" +
                "       Face cards = 10\n" +
                "       Aces = 1 or 11.\n\n" +

                "   On the left side are your cards and on the right are the dealer's. You have two options:\n" +
                "       ”Hit!” - you will receive a random card from the deck and your total value changes.\n" +
                "              - You may continue to ”hit” making sure your total value doesn't go over 22.\n" +
                "              - If it does, it's a ”bust” and you automatically lose regardless of the dealer’s cards.\n" +
                "       ”Stay” - keep the current cards you have and start the dealer’s turn.\n\n" +

                "   Whichever player has the higher total value that is not a ”bust” wins.\n" +
                "   If you have the same value as the dealer's, it is a draw and neither gets any money.\n"+
                "   If either player hits 21 on two cards by having an Ace and a card with a value of 10,\n" +
                "   then that player has ”blackjack” and wins 150% of their winnings (Rounded to nearest dollar).\n" +
                "   If both players has ”blackjack” then it's also a draw." +

                "\n\nGood luck!\n");
        rulesText.setStyle("-fx-font-size: 16; -fx-fill: #f3f0f0");
        rulesText.setTextAlignment(TextAlignment.LEFT);
        rulesBox = new HBox(rulesText);
        rulesBox.setStyle("-fx-background-color: #262626");
        rulesBox.setAlignment(Pos.CENTER);

        //HOME BUTTON
        playButton = new Button("PLAY!");
        playBox = new HBox(playButton);
        playButton.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: white");
        playBox.setAlignment(Pos.CENTER);
        playButton.setOnAction(e -> {
            primaryStage.setScene((Scene)sceneMap.get("setup"));
        });

        homeBox = new VBox(30, titleBox, rulesBox, playBox);

        mainPane = new BorderPane();
        mainPane.setCenter(homeBox);
        mainPane.setStyle("-fx-background-color: #171a1f");

        primaryStage.setTitle("Blackjack Game");

        Scene homeScene = new Scene(mainPane, 1330,750);
        sceneMap.put("home", homeScene);
        sceneMap.put("setup", createSetupScene(primaryStage));

        primaryStage.setScene(sceneMap.get("home"));
        primaryStage.show();
    }


    //this creates the scene where the user will input the starting money and betting money
    public Scene createSetupScene(Stage primaryStage){
        BorderPane mainPane = new BorderPane();

        //top text
        Text moneyRules = new Text("Enter integer values only");
        moneyRules.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0");
        moneyRules.setTextAlignment(TextAlignment.CENTER);
        HBox h = new HBox(moneyRules);
        h.setAlignment(Pos.CENTER);
        h.setPadding(new Insets(50,0,80,0));

        //text field labels
        Text moneyLabel = new Text("Starting Money: $ ");
        moneyText = new TextField("");
        moneyText.setPromptText("Enter starting money");
        moneyText.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0;");
        moneyLabel.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0");
        moneyLabel.setTextAlignment(TextAlignment.LEFT);

        HBox moneyBox = new HBox(moneyLabel, moneyText);
        moneyBox.setStyle("-fx-background-color: #171a1f");
        moneyBox.setAlignment(Pos.CENTER);

        Text betLabel = new Text("Bet: $ ");
        betText = new TextField("");
        betText.setPromptText("Enter bet money");
        betLabel.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0");
        betText.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0");
        betLabel.setTextAlignment(TextAlignment.LEFT);

        HBox betBox = new HBox(betLabel, betText);
        betBox.setStyle("-fx-background-color: #171a1f");
        betBox.setAlignment(Pos.CENTER);

        //SETUP BUTTONS
        readyButton = new Button("Ready!");
        readyButton.setVisible(false);
        readyButton.setAlignment(Pos.CENTER);
        readyButton.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: white");

        //this is used to confirm that the money inputs are valid
        placeBetsButton = new Button("Place My Bets");
        placeBetsButton.setAlignment(Pos.CENTER);
        placeBetsButton.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: white");
        placeBetsButton.setDisable(true);

        //these makes sure that the text fields have some input in it to enable the place bets button
        moneyText.textProperty().addListener(e->{
            placeBetsButton.setDisable(moneyText.getText().isEmpty() || betText.getText().isEmpty());
        });
        betText.textProperty().addListener(e->{
            placeBetsButton.setDisable(moneyText.getText().isEmpty() || betText.getText().isEmpty());
        });

        placeBetsButton.setOnAction(e-> {
            //this is to make sure that the text in the text fields are integer values
            if(!betText.getText().matches("\\d+")){
                betText.setText("Enter integer values");
            }
            else if (!moneyText.getText().matches("\\d+")){
                moneyText.setText("Enter integer values");
            }
            //making sure that the values typed are valid
            else if(Integer.parseInt(betText.getText()) <= Integer.parseInt(moneyText.getText())){
                readyButton.setVisible(true);
            }
            else if(Integer.parseInt(betText.getText()) > Integer.parseInt(moneyText.getText())){
                betText.setText("cannot be greater than starting money");
            }
        });

        //this sets up the currentbet and totalwinnings variables and starts the game
        readyButton.setOnAction(e -> {
            game.setCurrentBet(Double.parseDouble(betText.getText()));
            game.setTotalWinnings(Double.parseDouble(moneyText.getText()));
            sceneMap.put("gameplay", createGameplayScene(primaryStage));
            primaryStage.setScene((Scene)sceneMap.get("gameplay"));
        });

        HBox buttonsBox = new HBox(50, placeBetsButton, readyButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox setupBox = new VBox(50, h, moneyBox, betBox, buttonsBox);
        mainPane.setCenter(setupBox);
        mainPane.setStyle("-fx-background-color: #171a1f");
        return new Scene(mainPane, 1330, 750);
    }

    //this creates the gameplay scene which is split into two sides, the user's and the dealer's
    public Scene createGameplayScene(Stage primaryStage){
        //deck of cards
        dealer = game.getDealer();
        dealer.generateDeck();
        dealer.shuffleDeck();
        gameLogic = game.getGameLogic();

        //this arraylist is to display the face cards pictures based on the file name
        faceCards = new ArrayList<>();
        String[] faces = {"queen", "king", "jack"};
        for(int i=0; i<4; ++i){
            Collections.addAll(faceCards, faces);
        }

        Image flippedCardImage = new Image("back.png");

        //USER SIDE
        Text userLabel = new Text("YOU");
        userLabel.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0;");
        HBox nameBox = new HBox(userLabel);
        nameBox.setAlignment(Pos.CENTER);

        Text currentMoney = new Text("Current Money: $" + game.getTotalWinnings());
        currentMoney.setStyle("-fx-font-size: 28; -fx-fill: #f3f0f0;");
        currentMoney.setTextAlignment(TextAlignment.LEFT);

        Text betMoney = new Text("Bet: $" + game.getCurrentBet());
        betMoney.setStyle("-fx-font-size: 28; -fx-fill: #f3f0f0;");

        //USER'S HAND
        userHand = dealer.dealHand();
        Text valueText = new Text("Value: " + gameLogic.handTotal(userHand));
        valueText.setStyle("-fx-font-size: 28; -fx-fill: #f3f0f0;");

        HBox moneyAndValue = new HBox(50, currentMoney, betMoney, valueText);
        moneyAndValue.setPadding(new Insets(0,0,50,0));

        ImageView userCard1 = showCard(userHand.get(0).getCard(faceCards));
        userCard1.setFitHeight(cardHeight);
        userCard1.setFitWidth(cardWidth);
        ImageView userCard2 = showCard(userHand.get(1).getCard(faceCards));
        userCard2.setFitHeight(cardHeight);
        userCard2.setFitWidth(cardWidth);

        HBox userCards = new HBox(5, userCard1, userCard2);

        //USER BUTTONS

        Button hitButton = new Button("HIT!");
        Button stayButton = new Button("STAY");
        HBox userButtons = new HBox(50, hitButton, stayButton);
        hitButton.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: #ef5252");
        stayButton.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: white");
        userButtons.setAlignment(Pos.CENTER);
        userButtons.setPadding(new Insets(50,0,0,0));

        hitButton.setOnAction(e ->{
            Card newCard = dealer.drawOne();
            ImageView hitCard = showCard(newCard.getCard(faceCards));
            userHand.add(newCard);
            userCards.getChildren().add(hitCard);
            valueText.setText("Value: " + gameLogic.handTotal(userHand));
            if(gameLogic.handTotal(userHand) > 21){
                game.setPlayerHand(userHand);
                hitButton.setVisible(false);
                stayButton.setVisible(false);
                //start dealer's turn
                dealerTurn(primaryStage);
            }
        });

        stayButton.setOnAction(e ->{
            game.setPlayerHand(userHand);
            hitButton.setVisible(false);
            stayButton.setVisible(false);
            //start dealer's turn
            dealerTurn(primaryStage);
        });

        //the left side of the scene
        VBox userBox = new VBox(10, nameBox, moneyAndValue, userCards, userButtons);
        userBox.setStyle("-fx-background-color: #171a1f");
        userBox.setPadding(new Insets(80,50,50,50));
        userBox.setMaxSize(650, 720);

        //DEALER SIDE
        dealerHand = dealer.dealHand();
        dealerCard = showCard(dealerHand.get(0).getCard(faceCards));
        flippedCard = new ImageView(flippedCardImage);
        dealerCard.setFitHeight(cardHeight);
        dealerCard.setFitWidth(cardWidth);
        flippedCard.setFitHeight(cardHeight);
        flippedCard.setFitWidth(cardWidth);

        Text dealerLabel = new Text("DEALER");
        dealerLabel.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0;");
        HBox dealerNameBox = new HBox(dealerLabel);
        dealerNameBox.setAlignment(Pos.CENTER);

        dealerValue = new Text("Value: ?");
        dealerValue.setStyle("-fx-font-size: 28; -fx-fill: #f3f0f0;");
        HBox dealerValueBox = new HBox(dealerValue);
        dealerValueBox.setAlignment(Pos.CENTER);

        dealerCards = new HBox(5, dealerCard, flippedCard);
        dealerCards.setAlignment(Pos.CENTER);
        dealerCards.setPadding(new Insets(50,0,0,0));

        VBox dealerBox = new VBox(10, dealerNameBox, dealerValueBox, dealerCards);
        dealerBox.setStyle("-fx-background-color: #2e3822");
        dealerBox.setPadding(new Insets(80,30,20,30));
        dealerBox.setPrefSize(630, 720);

        BorderPane mainPane = new BorderPane();
        mainPane.setLeft(userBox);
        mainPane.setRight(dealerBox);
        mainPane.setStyle("-fx-background-color: #171b28");

        return new Scene(mainPane, 1330, 750);
    }
    //this helper function is to show the image of a card based on the given String filename
    public ImageView showCard(String cardName){
        Image cardImage = new Image(cardName);
        ImageView card = new ImageView(cardImage);
        card.setFitHeight(cardHeight);
        card.setFitWidth(cardWidth);
        return card;
    }

    //this is the function that showcases the dealer's turn where there are pauses for each draw of a card
    public void dealerTurn(Stage primaryStage){
        PauseTransition pause = new PauseTransition(Duration.seconds(2.0));
        pause.setOnFinished(e->{
            primaryStage.setScene((Scene)sceneMap.get("gameplay"));
            if(gameLogic.evaluateBankerDraw(dealerHand)){
                //dealer draw
                Card newCard = dealer.drawOne();
                ImageView hitCard = showCard(newCard.getCard(faceCards));
                dealerHand.add(newCard);
                dealerCards.getChildren().add(hitCard);
                dealerValue.setText("Value: " + gameLogic.handTotal(dealerHand));
                pause.play();
            }
            else{
                game.setBankerHand(dealerHand);
                sceneMap.put("result", createResultsScene(primaryStage));
                primaryStage.setScene((Scene)sceneMap.get("result"));
            }
        });
        //show other dealer's card
        flippedCard = showCard(dealerHand.get(1).getCard(faceCards));
        dealerCards.getChildren().set(1, flippedCard);
        dealerValue.setText("Value: " + gameLogic.handTotal(dealerHand));
        pause.play();

    }
    //this is the results screen which shows how much money the player earned/lost and whether they lost/won or drew
    public Scene createResultsScene(Stage primaryStage){
        Text winnerText = new Text();
        Text resultsText = new Text();
        winnerText.setStyle("-fx-font-size: 54; -fx-fill: white; ");

        HBox totalBox = new HBox(resultsText);
        totalBox.setAlignment(Pos.CENTER);

        Button playAgain = new Button("Play Again?");
        HBox playAgainBox = new HBox(playAgain);
        playAgain.setStyle("-fx-font-size: 32; -fx-background-color: black; -fx-text-fill: white");
        playAgainBox.setAlignment(Pos.CENTER);
        playAgain.setOnAction(e -> {
            if(game.getTotalWinnings() == 0.0){
                sceneMap.put("setup", createSetupScene(primaryStage));
                primaryStage.setScene((Scene)sceneMap.get("home"));
            }
            else{
                moneyText.setText((int)(Math.round(game.getTotalWinnings()))+"");
                moneyText.setPromptText(game.getTotalWinnings()+"");
                moneyText.setEditable(false);
                readyButton.setVisible(false);
                primaryStage.setScene((Scene)sceneMap.get("setup"));
            }
        });

        VBox resultsBox = new VBox(50, winnerText, totalBox, playAgainBox);
        resultsBox.setAlignment(Pos.CENTER);
        double totalMoney = game.getTotalWinnings()+game.evaluateWinnings();
        if(gameLogic.whoWon(userHand, dealerHand).equals("player")){
            winnerText.setText("YOU WON!");
            resultsText.setText("Starting money: $" + game.getTotalWinnings() +
                                "\nMoney earned:  $" + game.evaluateWinnings() +
                                "\nTotal money:    $" + (totalMoney) +
                                "\n\nYour hand: " + gameLogic.handTotal(userHand) + "    Dealer's hand: " + gameLogic.handTotal(dealerHand) );
            resultsBox.setStyle("-fx-background-color: #2e3822");
            game.setTotalWinnings(game.getTotalWinnings()+game.evaluateWinnings());
        }
        else if (gameLogic.whoWon(userHand, dealerHand).equals("dealer")){
            if(totalMoney < 0){
                totalMoney = 0;
            }
            winnerText.setText("YOU LOST!");
            resultsText.setText("Starting money: $" + game.getTotalWinnings() +
                              "\nMoney lost:     $" + game.evaluateWinnings() +
                              "\nTotal money:    $" + (totalMoney) +
                    "\n\nYour hand: " + gameLogic.handTotal(userHand) + "    Dealer's hand: " + gameLogic.handTotal(dealerHand) );
            resultsBox.setStyle("-fx-background-color: #ef5252");
            game.setTotalWinnings(totalMoney);
        }
        else{
            winnerText.setText("DRAW!");
            resultsText.setText("Starting money: $" + game.getTotalWinnings() +
                              "\nTotal money:    $" + (game.getTotalWinnings() + game.evaluateWinnings()) +
                    "\n\nYour hand: " + gameLogic.handTotal(userHand) + "    Dealer's hand: " + gameLogic.handTotal(dealerHand) );
            resultsBox.setStyle("-fx-background-color: #171b28");
        }
        resultsText.setStyle("-fx-font-size: 42; -fx-fill: #f3f0f0;");
        resultsText.setTextAlignment(TextAlignment.LEFT);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(resultsBox);
        mainPane.setStyle("-fx-background-color: #171a1f");
        return new Scene(mainPane, 1330, 750);
    }

}
