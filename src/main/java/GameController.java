import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.media.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/*

CS 342 - Project 2 Poker Game

Names: Fathuma Shimra Ahamed Hazmi, faham2
       Abrar Makki, amakk3

Instructor: Professor Hallenbeck

*/

//make controller class for the game page
public class GameController implements Initializable {
    //data members
    private static final int maxWager = 25;
    Player playerOne;
    Player playerTwo;
    Dealer theDealer;
    @FXML
    public Button continueButton;
    public Label player2Label;
    AudioClip cardRevealSound = new AudioClip(getClass().getResource("Card-flip-sound-effect.mp3").toExternalForm());

    //labels for the player and dealer cards
    public Label p1Card1, p1Card2, p1Card3, p2Card1, p2Card2, p2Card3, dCard1, dCard2, dCard3, playerLabel;
    //Label[] labels = {p1Card1, p1Card2, p1Card3, p2Card1, p2Card2, p2Card3};
    public TextArea log; //log to display the game progress
    //labels for the player bets
    public Label anteValue, anteValue2;
    public Label pairPlusValue, pairPlusValue2;
    public Label wagerValue, wagerValue2;
    public Label totalWins, totalWins2;
    public MenuItem freshStart;
    public boolean isGameStarted = false;
    public Boolean isNewLook = false;
    public MenuItem newLook;
    public MenuItem exitGame;
    //bools for the game (like checking for if player folded or not)
    public Boolean player1 = true;
    public Boolean p1lost = false;
    public Boolean p2lost = false;
    public Boolean player1folded = false;
    public Boolean player2folded = false;
    public Boolean player1dealt = false;
    public Boolean player2dealt = false;
    public Boolean dealerQueenHigh = false;
    public BorderPane gameScreen;
    public int roundCounter = 0;
    @FXML
    private Button foldButton; //for the fold button
    @FXML
    private Button dealButton; //for the deal button
    @FXML
    private List<Integer> bothPlayerAntes = new ArrayList<>(); //array to store the ante bets for both players
    private List<Integer> bothPlayerPP = new ArrayList<>(); //array to store the pair plus bets for both players
    @FXML
    private Button startButton; //for the start button
    @FXML
    private TextField text1;

    //initialize the game screen
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //initialize the game screen
        gameScreen.setOpacity(0);
        foldButton.setDisable(true);
        foldButton.setVisible(false);
        dealButton.setDisable(true);
        dealButton.setVisible(false);
        continueButton.setDisable(true);
        continueButton.setVisible(false);
        playerLabel.setVisible(false);
        player2Label.setVisible(false);
        setAllcardsInvisible();
        log.setStyle("-fx-background-color: linear-gradient(to top, rgba(255,255,255,0), rgba(255,255,255,1));");
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2));
        //make the background of the log into a gradient from bottom to top; transparent to not transparent with default background color white
        fadeIn.setNode(gameScreen);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
    //start the game
    @FXML
    private void setStartButton(ActionEvent actionEvent){ //start button to start the game
        //set button unclickable
        isGameStarted = true;
        startGame();
        askAnteWager(2); //ask the ante wager for both players
        askPPWager(2); //ask the pair plus bet for both players
        theGame(); //start the game
        startButton.setDisable(true);
        startButton.setVisible(false);
        foldButton.setDisable(false);
        foldButton.setVisible(true);
        dealButton.setDisable(false);
        dealButton.setVisible(true);
        continueButton.setVisible(true);
        playerLabel.setVisible(true);
        player2Label.setVisible(true);
        animateCards(); //animate the revealing of the cards
    }

    public void startGame() { //start the game
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();
        pickCards();

        DisplayCards();
    }

    private void setAllcardsInvisible() { //set all cards invisible
        p1Card1.setOpacity(0);
        p1Card2.setOpacity(0);
        p1Card3.setOpacity(0);
        p2Card1.setOpacity(0);
        p2Card2.setOpacity(0);
        p2Card3.setOpacity(0);
        dCard1.setOpacity(0);
        dCard2.setOpacity(0);
        dCard3.setOpacity(0);
    }


    //Shuffle the cards for both player and dealer
    public void pickCards(){
        //get the player's hand;
        playerOne.hand = theDealer.dealHand(); //get the 3 cards for each player and dealer
        playerTwo.hand = theDealer.dealHand();
        theDealer.dealersHand = theDealer.dealHand();
        //display the player's hand
        System.out.println("Player 1's hand: " + playerOne.hand.get(0).getValue() + " " + playerOne.hand.get(0).getSuit());
        System.out.println("Player 2's hand: " + playerTwo.hand.get(0).getValue() + " " + playerTwo.hand.get(0).getSuit());
        System.out.println("Dealer's hand: " + theDealer.dealersHand.get(0).getValue() + " " + theDealer.dealersHand.get(0).getSuit());
        //inform that both players hands are now assigned
        log.appendText("Player 1's hand assigned! " + "\n");
        log.appendText("Player 2's hand assigned! " + "\n");
    }

    //Display the cards for both players
    private void DisplayCards(){
        p1Card1.setText(playerOne.hand.get(0).getValue() + " " + playerOne.hand.get(0).getSuit() + ", ");
        p1Card2.setText(playerOne.hand.get(1).getValue() + " " + playerOne.hand.get(1).getSuit() + ", ");
        p1Card3.setText(playerOne.hand.get(2).getValue() + " " + playerOne.hand.get(2).getSuit() + ",");

        p2Card1.setText(playerTwo.hand.get(0).getValue() + " " + playerTwo.hand.get(0).getSuit() + ", ");
        p2Card2.setText(playerTwo.hand.get(1).getValue() + " " + playerTwo.hand.get(1).getSuit() + ", ");
        p2Card3.setText(playerTwo.hand.get(2).getValue() + " " + playerTwo.hand.get(2).getSuit() + ", ");
    }

    public void askAnteWager(int playernums) { //ask the ante wager for both players
        for (int i = 0; i < playernums; i++) { //loop for both players
            boolean valid = false; //set the boolean to false
            while (!valid) { //while the value is not valid
                TextInputDialog dialog = new TextInputDialog(); //prompt both users to enter their antes
                //style the dialog box
                dialog.setTitle("Ante Wager for Player " + (i + 1));
                dialog.setHeaderText("Enter ante wager between 5 and 25 please");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) { //if the user enters a value, then check the values
                    try {
                        int ante = Integer.parseInt(result.get());
                        if (ante >= 5 && ante <= 25) {
                            bothPlayerAntes.add(ante); //add to the array if the values are valid
                            if (i == 0) {
                                updateAnteLabelP1(); //update the ante label for player 1
                            } else {
                                updateAnteLabelP2(); //update the ante label for player 2
                            }

                            valid = true; //set the boolean to true
                        } else {
                            Errormsg("Please enter a valid number between 5 and 25"); //if not, show the error message and prompt again
                        }
                    } catch (NumberFormatException e) {
                        Errormsg("Error!");
                    }
                } else {
                    Errormsg("Please enter a valid number between 5 and 25");
                }
            }
        }
    }

    public void askPPWager(int playernums) { //ask the pair plus bet for both players
        for (int i = 0; i < playernums; i++) { //loop for both players
            boolean valid = false;
            while (!valid) {
                TextInputDialog dialog = new TextInputDialog(); //prompt both users to enter their pp bets
                dialog.setTitle("Pair Plus Wager for Player " + (i + 1));
                dialog.setHeaderText("Enter Pair Plus wager between 5 and 25 please");
                dialog.setContentText("Player " + (i + 1) + " pair plus wager:");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) { //if the user enters a value, then check the values
                    try {
                        int PPWager = Integer.parseInt(result.get());
                        if ((PPWager >= 5 && PPWager <= 25) || PPWager == 0) { //its ok if the pair plus is 0 because its an optional bet
                            bothPlayerPP.add(PPWager); //add to the array if the values are valid
                            if (i == 0) {
                                updatePPLabelP1(); //update the pp label for player 1
                            } else {
                                updatePPLabelP2(); //update the pp label for player 2
                            }

                            valid = true; //set the boolean to true
                        } else {
                            Errormsg("Please enter a valid number between 5 and 25 (or 0 if you don't want to bet pair plus)"); //if not, show the error message and prompt again
                        }
                    } catch (NumberFormatException e) {
                        Errormsg("Error!");
                    }
                } else {
                    Errormsg("Please enter a valid number between 5 and 25");
                }
            }
        }
    }

    //update the ante label for player 1
    private void updateAnteLabelP1() {
        playerOne.anteBet = bothPlayerAntes.get(0);
        anteValue.setText(bothPlayerAntes.get(0).toString());
    }
    //update the ante label for player 2
    private void updateAnteLabelP2() {
        playerTwo.anteBet = bothPlayerAntes.get(1);
        anteValue2.setText(bothPlayerAntes.get(1).toString());
    }

    //update the pair plus label for player 1
    private void updatePPLabelP1() {
        playerOne.pairPlusBet = bothPlayerPP.get(0);
        pairPlusValue.setText(bothPlayerPP.get(0).toString());
    }
    //update the pair plus label for player 2
    private void updatePPLabelP2() {
        playerTwo.pairPlusBet = bothPlayerPP.get(1);
        pairPlusValue2.setText(bothPlayerPP.get(1).toString());
    }
    //update the play bet label for player 1
    private void updatePlayBetLabelP1() {
        playerOne.playBet = bothPlayerAntes.get(0);
        wagerValue.setText(bothPlayerAntes.get(0).toString());
    }
    //update the play bet label for player 2
    private void updatePlayBetLabelP2() {
        playerTwo.playBet = bothPlayerAntes.get(1);
        wagerValue2.setText(bothPlayerAntes.get(1).toString());
    }
    //error message function
    private void Errormsg(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }


    public void setDealButton(ActionEvent actionEvent) {
        roundCounter++; //increment the round counter
        if (player1) { //if player 1 is playing
            player1dealt = true;
            player1 = false; //switch to player 2
            updatePlayBetLabelP1(); //update the play bet label for player 1
            log.appendText("Player 1 made play bet! \n");

        } else { //if player 2 is playing
            player2dealt = true; //set player 2 dealt to true
            updatePlayBetLabelP2(); //update the play bet label for player 2

            log.appendText("Player 2 made play bet! \n"); //log the play bet
            player1 = true; //switch back to player 1

        }
        if (roundCounter == 2) { //if the round counter is 2
            dealButton.setDisable(true); //disable the deal button
            foldButton.setDisable(true); //disable the fold button
            continueButton.setDisable(false); //enable the continue button
        }
        theGame(); //start the game
    }


    @FXML
    private void setFoldButton(ActionEvent actionEvent) {
        roundCounter++; //increment the round counter
        if (player1) { //if player 1 is playing
            log.appendText("Player 1 folded! \n");
            playerOne.folded = true; //set player 1 folded to true
            anteValue.setText("0"); //set the ante value to 0
            pairPlusValue.setText("0"); //set the pair plus value to 0
            player1 = false; //switch to player 2
            player1folded = true; //set player 1 folded to true

        } else {
            log.appendText("Player 2 folded! \n");
            playerTwo.folded = true; //set player 2 folded to true
            anteValue2.setText("0"); //set the ante value to 0
            pairPlusValue2.setText("0"); //set the pair plus value to 0
            player2folded = true; //set player 2 folded to true
            player1 = true; //switch back to player 1
        }

        if (roundCounter == 2) { //if the round counter is 2
            dealButton.setDisable(true); //disable the deal button
            foldButton.setDisable(true); //disable the fold button
            continueButton.setDisable(false); //enable the continue button
        }
        theGame();

    }

    private void theGame(){

        if(player1){ //if player 1 is playing
            playerLabel.setText("Player 1"); //set the player label to player 1
            player2Label.setText("Player 2"); //set the player 2 label to player 2
            log.appendText("Player 1's turn! \n");
            p2Card1.setText(playerTwo.hand.get(0).getValue() + " " + playerTwo.hand.get(0).getSuit() + ", "); //display player 2's cards
            p2Card2.setText(playerTwo.hand.get(1).getValue() + " " + playerTwo.hand.get(1).getSuit() + ", ");
            p2Card3.setText(playerTwo.hand.get(2).getValue() + " " + playerTwo.hand.get(2).getSuit() + ", ");

            p1Card1.setText(playerOne.hand.get(0).getValue() + " " + playerOne.hand.get(0).getSuit() + ", "); //display player 1's cards
            p1Card2.setText(playerOne.hand.get(1).getValue() + " " + playerOne.hand.get(1).getSuit() + ", ");
            p1Card3.setText(playerOne.hand.get(2).getValue() + " " + playerOne.hand.get(2).getSuit() + ", ");

        }
        else{ //if player 2 is playing
            playerLabel.setText("Player 2"); //set the player label to player 2
            player2Label.setText("Player 1"); //set the player 2 label to player 1
            log.appendText("Player 2's turn! \n");
            p2Card1.setText(playerOne.hand.get(0).getValue() + " " + playerOne.hand.get(0).getSuit() + ", "); //display player 1's cards
            p2Card2.setText(playerOne.hand.get(1).getValue() + " " + playerOne.hand.get(1).getSuit() + ", ");
            p2Card3.setText(playerOne.hand.get(2).getValue() + " " + playerOne.hand.get(2).getSuit() + ", ");

            p1Card1.setText(playerTwo.hand.get(0).getValue() + " " + playerTwo.hand.get(0).getSuit() + ", "); //display player 2's cards
            p1Card2.setText(playerTwo.hand.get(1).getValue() + " " + playerTwo.hand.get(1).getSuit() + ", ");
            p1Card3.setText(playerTwo.hand.get(2).getValue() + " " + playerTwo.hand.get(2).getSuit() + ", ");

        }

    }


    //menu bar functions
    @FXML
    private void onExit() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ExitPage.fxml")));
        Stage stage = (Stage) gameScreen.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        //if cancel button is pressed in exit page, return to game screen
        Button cancelButton = (Button) scene.lookup("#cancelButton");
        cancelButton.setOnAction(e -> {
            stage.setScene(gameScreen.getScene());
        });
    }

    public void onNewLook(ActionEvent actionEvent) {
        //Change the style of the game screen by loading in new css file
        if (isNewLook == false) {
            gameScreen.getStylesheets().clear();
            gameScreen.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles2.css")).toExternalForm());
            log.setStyle("-fx-background-color: linear-gradient(to top, rgba(234,231,231,0.7), rgb(80,78,78));");
            isNewLook = true;
        } else {
            gameScreen.getStylesheets().clear();
            gameScreen.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
            log.setStyle("-fx-background-color: linear-gradient(to top, rgba(255,255,255,0), rgba(255,255,255,1));");
            isNewLook = false;
        }

    }

    //reset everything if player chooses to start fresh
    public void onFreshStart(ActionEvent actionEvent) {
        System.out.println("Fresh Start");
        if (isGameStarted) { //if the game has started
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //ask the user if they want to start a new game
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            alert.setTitle("Fresh Start");
            alert.setHeaderText("Are you sure you want to start a new game?"); //confirm the user's choice
            alert.setContentText("All progress will be lost"); //inform the user that all progress will be lost
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //reset the game
                startButton.setDisable(false);
                startButton.setVisible(true);
                foldButton.setDisable(true);
                foldButton.setVisible(false);
                dealButton.setDisable(true);
                dealButton.setVisible(false);
                setAllcardsInvisible();
                continueButton.setDisable(true);
                continueButton.setVisible(false);
                playerLabel.setVisible(false);
                player2Label.setVisible(false);
                log.clear();
                bothPlayerAntes.clear();
                bothPlayerPP.clear();
                anteValue.setText("0");
                anteValue2.setText("0");
                pairPlusValue.setText("0");
                pairPlusValue2.setText("0");
                totalWins.setText("0");
                totalWins2.setText("0");
                playerOne = null;
                playerTwo = null;
                theDealer = null;
                player1 = true;
                player1folded = false;
                playerLabel.setText("Player 1");
                player2Label.setText("Player 2");
                roundCounter = 0;
                wagerValue.setText("0");
                wagerValue2.setText("0");
                isGameStarted = false;
            }
        }
    }


    //function to evaluate the hands and determine the winner
    public void doesDealerQualify(){

        dealerQueenHigh = false; //set dealer queen high to false
        int dealerHandType = ThreeCardLogic.evalHand(theDealer.dealersHand); //evaluate the dealer's hand

        for(int i = 0; i < 3; i++){ //loop through the dealer's hand
            if(theDealer.dealersHand.get(i).getValue() >= 12 || dealerHandType != 0){ //if the dealer has a queen high or a pair or better
                dealerQueenHigh = true; //set dealer queen high to true
                break; //break the loop
            }
        }

        if(!dealerQueenHigh) { //if the dealer does not have a queen high
            if(!player1folded){ //if player 1 did not fold
                updatePlayBetLabelP1();
                log.appendText("Play bet returned to player 1 \n"); //return the play bet to player 1
            }
            if(!player2folded){ //if player 2 did not fold
                updatePlayBetLabelP2();
                log.appendText("Play bet returned to player 2 \n"); //return the play bet to player 2
            }
        }

        else{ //if the dealer has a queen high
            if(!player1folded){ //if player 1 did not fold
                int result = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand); //compare the hands
                if(result == 2){ //if player 1 wins
                    log.appendText("Player 1 wins! \n");
                    playerOne.totalWinnings += ((playerOne.anteBet*2) + (playerOne.playBet*2)); //update the total winnings
                    totalWins.setText(Integer.toString(playerOne.totalWinnings)); //update the total winnings label
                }
                else if(result == 1){ //if player 1 loses
                    log.appendText("Player 1 loses! \n");
                    p1lost = true;
                    playerOne.totalWinnings -= (playerOne.anteBet + playerOne.playBet); //update the total winnings
                    totalWins.setText(Integer.toString(playerOne.totalWinnings)); //update the total winnings label
                }
                else if(result == 0){
                    log.appendText("Player 1 and dealer tie! \n");
                }
            }
            if(!player2folded){ //if player 2 did not fold
                int result = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand); //compare the hands
                if(result == 2){
                    log.appendText("Player 2 wins! \n");
                    playerTwo.totalWinnings += ((playerTwo.anteBet*2) + (playerTwo.playBet*2)); //update the total winnings
                    totalWins2.setText(Integer.toString(playerTwo.totalWinnings)); //update the total winnings label
                }
                else if(result == 1){
                    log.appendText("Player 2 loses! \n");
                    p2lost = true;
                    playerTwo.totalWinnings -= (playerTwo.anteBet + playerTwo.playBet); //update the total winnings
                    totalWins2.setText(Integer.toString(playerTwo.totalWinnings)); //update the total winnings label
                }
                else if(result == 0){
                    log.appendText("Player 2 and dealer tie! \n");
                }
            }
        }
    }

    //function to continue the game
    public void setContinueButton(ActionEvent actionEvent) {
        roundCounter = 0; //reset the round counter
        foldButton.setDisable(false);
        dealButton.setDisable(false);
        //reveal dealer's hand
        dCard1.setVisible(true);
        dCard2.setVisible(true);
        dCard3.setVisible(true);
        //do the rest of the stuff before we can reset to a new round like evaluate the hands
        continueButton.setDisable(true); //disable the continue button
        isPairPlus(); //check if player 1 won pair plus
        isPairPlusP2(); //check if player 2 won pair plus
        totalWins.setText(Integer.toString(playerOne.totalWinnings)); //update the total winnings label for player 1
        totalWins2.setText(Integer.toString(playerTwo.totalWinnings)); //update the total winnings label for player 2
        doesDealerQualify(); //check if the dealer qualifies
        if(player1folded){ //if player 1 folded
            anteValue.setText(Integer.toString(playerOne.anteBet)); //give back the ante and pair plus in the new round
            pairPlusValue.setText(Integer.toString(playerOne.pairPlusBet));
        }
        if(player2folded){
            anteValue2.setText(Integer.toString(playerTwo.anteBet)); //give back the ante and pair plus in the new round
            pairPlusValue2.setText(Integer.toString(playerTwo.pairPlusBet));
        }
        //make popup revealing the dealer's hand
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        alert.setTitle("Dealer's Hand");
        alert.setHeaderText("Dealer's Hand");
        alert.setContentText("Dealer's Hand: " + theDealer.dealersHand.get(0).getValue() + " " + theDealer.dealersHand.get(0).getSuit() + ", " +
                theDealer.dealersHand.get(1).getValue() + " " + theDealer.dealersHand.get(1).getSuit() + ", " +
                theDealer.dealersHand.get(2).getValue() + " " + theDealer.dealersHand.get(2).getSuit());
        alert.showAndWait();
        pickCards(); //pick new cards for the players
        //set cards invisible
        setAllcardsInvisible();
        DisplayCards();
        //animate the revealing of the player's cards
        //set all cards visible
        animateCards();
        player1folded = false;
        player2folded = false;
    }

    //function to animate the revealing of the cards
    private void animateCards() {
        //create a card flip sound when each card is revealed

        SequentialTransition sequence = new SequentialTransition(); //create a sequential transition
        FadeTransition fade1 = new FadeTransition(Duration.millis(500), p1Card1); //fade transition for player 1 card 1
        fade1.setFromValue(0); //set the from value
        fade1.setToValue(1); //set the to value
        fade1.setOnFinished(event -> playRevealSound());
        FadeTransition fade2 = new FadeTransition(Duration.millis(500), p1Card2);
        fade2.setFromValue(0);
        fade2.setToValue(1);
        fade2.setOnFinished(event -> playRevealSound());
        FadeTransition fade3 = new FadeTransition(Duration.millis(500), p1Card3);
        fade3.setFromValue(0);
        fade3.setToValue(1);
        fade3.setOnFinished(event -> playRevealSound());
        FadeTransition fade4 = new FadeTransition(Duration.millis(500), p2Card1);
        fade4.setFromValue(0);
        fade4.setToValue(1);
        fade4.setOnFinished(event -> playRevealSound());
        FadeTransition fade5 = new FadeTransition(Duration.millis(500), p2Card2);
        fade5.setFromValue(0);
        fade5.setToValue(1);
        fade5.setOnFinished(event -> playRevealSound());
        FadeTransition fade6 = new FadeTransition(Duration.millis(500), p2Card3);
        fade6.setFromValue(0);
        fade6.setToValue(1);
        fade6.setOnFinished(event -> playRevealSound()); //play the reveal sound
        sequence.getChildren().addAll(fade1, fade2, fade3, fade4, fade5, fade6); //add the fade transitions to the sequence
        sequence.play(); //play the sequence

    }
    private void playRevealSound() { //play the reveal sound
        cardRevealSound.play();
    }

    public void isPairPlus(){ //check if player 1 won pair plus
        if(!player1folded){ //if player 1 did not fold
            int ppwinnings = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet); //evaluate the pair plus winnings
            if(ppwinnings > 0){ //if the winnings are greater than 0
                log.appendText("Player 1 won pair plus! \n"); //log that player 1 won pair plus
                playerOne.totalWinnings += ppwinnings; //update the total winnings
            }
            else{ //if the winnings are 0
                log.appendText("Player 1 lost pair plus! \n"); //log that player 1 lost pair plus
                playerOne.totalWinnings -= playerOne.pairPlusBet; //update the total winnings
            }
        }
    }

    public void isPairPlusP2(){ //check if player 2 won pair plus
        if (!player2folded) { //if player 2 did not fold

            int ppwinnings = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet); //evaluate the pair plus winnings
            if(ppwinnings > 0){
                log.appendText("Player 2 won pair plus! \n"); //log that player 2 won pair plus
                playerTwo.totalWinnings += ppwinnings; //update the total winnings
            }
            else{
                log.appendText("Player 2 lost pair plus! \n"); //log that player 2 lost pair plus
                playerTwo.totalWinnings -= playerTwo.pairPlusBet; //update the total winnings
            }
        }
    }
}


