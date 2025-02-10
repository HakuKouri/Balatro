package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class GameScreenController
{
    @FXML
    private ImageView roundScoreStakeImage;
    @FXML
    private AnchorPane smallBlindAnchor;
    @FXML
    private AnchorPane bigBlindAnchor;
    @FXML
    private AnchorPane bossBlindAnchor;
    @FXML
    private Label labelBlind;
    @FXML
    private StackPane HoldingHand;

    @FXML
    private StackPane spPlayedCards;
    @FXML
    private GridPane handButtonBox;
    @FXML
    private Label infoHand;
    @FXML
    private Label infoHandLevel;
    @FXML
    private Label infoHandChips;
    @FXML
    private Label infoHandMulti;
    //to beat elements
    @FXML
    private ImageView toBeatImage;
    @FXML
    private ImageView toBeatStake;
    @FXML
    private Label toBeatScore;
    @FXML
    private Label toBeatReward;
    @FXML
    private Label pointsScored;
    @FXML
    private AnchorPane gameScreenAnchor;
    @FXML
    private Label handCount;
    @FXML
    private Label discardCount;
    @FXML
    private ImageView imageViewDeckField;
    @FXML
    private VBox spaceTag;
    @FXML
    private StackPane spaceJoker;
    @FXML
    private StackPane spaceConsumable;
    @FXML
    private AnchorPane placeHolderShopReward;
    @FXML
    private Label displayRound;
    @FXML
    private Label displayAnte;
    @FXML
    private Label displayMoney;
    @FXML
    private HBox blindBox;


    //TEST
    @FXML
    private ImageView testImageView;

    //FXMLLOADER AND CONTROLLER
    private final FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("shop-part.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("reward-summary.fxml"));
    private BlindPickPanels smallController;
    private BlindPickPanels bigController;
    private BlindPickPanels bossController;
    private ShopPart shopController;
    private RewardSummarController rewardSummarController;
    private AnchorPane smallBlind = null;
    private AnchorPane bigBlind = null;
    private AnchorPane boss = null;
    private AnchorPane shop = null;
    private AnchorPane reward = null;

    //GAME MECHANICS VARIABLES
    static boolean blindsToggled = true;
    public static Random rand;
    static int selectedCardCounter = 0;
    static List<Blind> allBlindsList;
    private final ArrayList<Blind> gameBlindsList = new ArrayList<>();
    static List<Tag> allTagList;
    static List<Hand> allHandList;


    //GAME VARIABLES
    static final int maxAnte = 8;
    static int ante = 1;
    static int phase = 1;
    static int round = 1;
    static int money = 0;
    static int hands = 3;
    static int discards = 3;
    static int handsize = 8;
    static int baseChips = 0;
    static int baseMulti = 0;
    static BigDecimal scoreToReach = new BigDecimal(0);
    static int scored = 0;
    static Deck deck;
    static Stake stake;
    static boolean handButtonHidden = false;
    static String sortedBy = "rank";
    static BigDecimal[] chipRequirement = new BigDecimal[]{BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800), BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000), BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};

    private final List<PlayingCard> deckList = new ArrayList<>();
    private final List<PlayingCard> playingCardList = new ArrayList<>();
    private final List<PlayingCard> selectedCards = new ArrayList<>();
    private final List<PlayingCard> handCards = new ArrayList<>();
    private final List<Tag> blindTags = new ArrayList<>();
    private final List<Tag> tagQueueList = new ArrayList<>();


    //UI HANDLER
    public void initialize(){
        Balatro.gameScreenController = this;
        allTagList = Tag.setTagList();
        allHandList = Hand.setHandList();
        allBlindsList = Blind.setBlindList();

        //LOAD / READY PLACEHOLDER
        try {
            smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallController.setGameScreenController(this);
            smallBlindAnchor.getChildren().add(smallBlind);

            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigController.setGameScreenController(this);
            bigBlindAnchor.getChildren().add(bigBlind);

            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossController.setGameScreenController(this);
            bossBlindAnchor.getChildren().add(boss);

            blindBox.setTranslateY(600);

            shop = loaderShop.load();
            shopController = loaderShop.getController();
            shopController.setGameScreenController(this);

            reward = loaderReward.load();
            rewardSummarController = loaderReward.getController();
            rewardSummarController.setGameScreenController(this);

            placeHolderShopReward.setTranslateY(560);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setPlayingDeck();
        rand = new Random();
        hideHandButtons();
        clearHandInfo();
        toggleBlind();
    }

    private void setBlindPanels() {
        smallController.setBlind(gameBlindsList.get((ante-1)*3), allTagList.get(round-ante), 1);
        bigController.setBlind(gameBlindsList.get((ante-1)*3+1), allTagList.get(round-ante), 2);
        bossController.setBlind(gameBlindsList.get((ante-1)*3+2), allTagList.get(round-ante), 3);

        smallController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3).getBlindScoreMultiplier().replace("x base",""))));
        bigController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3 +1).getBlindScoreMultiplier().replace("x base",""))));
        bossController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3+2).getBlindScoreMultiplier().replace("x base",""))));

        toggleBlind();
    }

    private void setDeckImage() throws IOException {
        Image image = new Image("file:" + deck.getDeckCoverUrl());
        imageViewDeckField.setImage(image);
    }

    private void setStakeImage() throws IOException {
        roundScoreStakeImage.setImage(new Image("file:"+stake.getStakeImageChipUrl()));
    }

    public void toggleBlind() {
        blindsToggled = !blindsToggled;
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
        TranslateTransition transitionSmall = new TranslateTransition(Duration.seconds(.5), smallBlindAnchor);
        TranslateTransition transitionBig = new TranslateTransition(Duration.seconds(.5), bigBlindAnchor);
        TranslateTransition transitionBoss = new TranslateTransition(Duration.seconds(.5), bossBlindAnchor);
        if (blindsToggled) {
            transitionBlindBox.setToY(0);
            if(round%3 == 1) transitionSmall.setToY(-50);
            if(round%3 == 2) transitionBig.setToY(-50);
            if(round%3 == 0) transitionBoss.setToY(-50);
        } else {
            transitionBlindBox.setToY(600);
            transitionSmall.setToY(0);
            transitionBig.setToY(0);
            transitionBoss.setToY(0);
        }
        transitionBlindBox.play();
        transitionSmall.play();
        transitionBig.play();
        transitionBoss.play();
    }

    private void hideBlinds() {
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
    }

    private void showBlindds() {
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
    }

    public void hideHandButtons() {
        System.out.println("hide button");
        handButtonHidden = !handButtonHidden;
        if(handButtonHidden) {
            HoldingHand.setTranslateY(100);
            handButtonBox.setTranslateY(100);
        } else {
            HoldingHand.setTranslateY(0);
            handButtonBox.setTranslateY(0);
        }
    }

    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;
        int bestHandIndex = -1;
        for (Hand hand : allHandList) {
            if(hands.contains(hand.getName())) {
                System.out.println(hand.getName());
                int points = hand.getChips() * hand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    bestHandIndex = allHandList.indexOf(hand);
                }
            }
        }
        if(!selectedCards.isEmpty()) {
            infoHand.setText(allHandList.get(bestHandIndex).getName());
            infoHandLevel.setText("lvl. " + allHandList.get(bestHandIndex).getLevel());
            infoHandChips.setText(""+ allHandList.get(bestHandIndex).getChips());
            infoHandMulti.setText(""+ allHandList.get(bestHandIndex).getMulti());
        } else {
            clearHandInfo();
        }

    }

    private void clearHandInfo() {
        infoHand.setText("");
        infoHandLevel.setText("");
        infoHandChips.setText("0");
        infoHandMulti.setText("0");
    }

    private void clearScoredPoints() {
        scored = 0;
        pointsScored.setText("0");
    }

    private void setHandCounter() {
        handCount.setText(String.valueOf(hands));
    }

    private void setDiscardCounter() {
        discardCount.setText(String.valueOf(discards));
    }

    private void openShop() {
        if(placeHolderShopReward.getTranslateY() != 0) {
            placeHolderShopReward.setTranslateY(0);
        }
        placeHolderShopReward.getChildren().clear();
        placeHolderShopReward.getChildren().add(shop);
    }

    public void closeShop() {
        placeHolderShopReward.setTranslateY(560);
    }

    private void openSummary() {
        if(placeHolderShopReward.getTranslateY() != 0) {
            placeHolderShopReward.setTranslateY(0);
        }
        placeHolderShopReward.getChildren().clear();
        placeHolderShopReward.getChildren().add(reward);
    }

    private void closeSummary() {
        placeHolderShopReward.setTranslateY(560);
    }

    private void setDisplayRound() {
        displayRound.setText(String.valueOf(round));
    }

    private void setDisplayAnte() {
        displayAnte.setText((ante) + "/8");
    }

    private void setDisplayMoney() {
        displayMoney.setText((money) + "$");
    }


    //SETTING UP GAME
    private void setPlayingDeck() {
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                deckList.add(new PlayingCard(j,i));
            }
        }
        playingCardList.addAll(deckList);

        Collections.shuffle(playingCardList, new Random());
    }

    public void createBlindList() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0)
                    gameBlindsList.add(allBlindsList.get(0));
                else if(j == 1)
                    gameBlindsList.add(allBlindsList.get(1));
                else
                    gameBlindsList.add(allBlindsList.get(rand.nextInt(allBlindsList.size() - 2 + 1) + 1));
            }
        }
    }

    private void expandBlindTagList() {
        for (int i = 0; i < 2; i++) {
            Tag tag = allTagList.get(rand.nextInt(allTagList.size()));
            if(Integer.parseInt((tag.getMinAnte())) <= ante)
                blindTags.add(tag);
            else {
                i--;
            }
        }
    }


    //PLAYING CARD HANDLER
    public void drawCard() {
        //testImageView.setImage(playingCardList.get(0).getImage());
        playingCardList.get(0).setOnMouseClicked(mouseEvent -> {
            playingCardClicked((PlayingCard) mouseEvent.getSource());
        });
        playingCardList.get(0).setClickAble(true);

        HoldingHand.getChildren().add(playingCardList.get(0));
        handCards.add(playingCardList.get(0));
        playingCardList.remove(0);
        moveCards(HoldingHand);
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {drawCard();}
        if(Objects.equals(sortedBy, "rank")) sortRank();
        else sortSuit();
    }

    private void playingCardClicked(PlayingCard card) {
        if(!card.isClickAble()) return;

        if(card.getTranslateY() == 0 && selectedCardCounter < 5) {
            selectedCards.add(card);
            card.setTranslateY(-20);
            selectedCardCounter++;
        }
        else if(card.getTranslateY() == -20) {
            selectedCards.remove(card);
            card.setTranslateY(0);
            selectedCardCounter--;
        }
        setHandInfo(checkHand.evaluateHands(selectedCards));
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(selectedCardCounter != 0) {
            hideHandButtons();
            for(PlayingCard card : selectedCards) {
                card.setTranslateX(0);
                card.setClickAble(false);
            }

            spPlayedCards.getChildren().addAll(selectedCards);

            List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(selectedCards , infoHand.getText());
            for(int i = 0; i < spPlayedCards.getChildren().size(); i++) {

                if(countedCards.contains(spPlayedCards.getChildren().get(i))) {
                    spPlayedCards.getChildren().get(i).setTranslateY(-20);
                } else
                    spPlayedCards.getChildren().get(i).setTranslateY(0);
            }

            baseChips = allHandList.stream().filter(name -> Objects.equals(name.getName(), infoHand.getText())).collect(Collectors.toList()).get(0).getChips();
            baseMulti = allHandList.stream().filter(name -> Objects.equals(name.getName(), infoHand.getText())).collect(Collectors.toList()).get(0).getMulti();

            selectedCards.clear();
            moveCards(spPlayedCards);

            countPoints();

            delay(2000,() -> {spPlayedCards.getChildren().clear(); drawCards(8 - HoldingHand.getChildren().size());});

            moveCards(HoldingHand);
            selectedCardCounter = 0;
        }
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(selectedCardCounter != 0) {
            for(int i = 0; i < selectedCardCounter; i++) {
                HoldingHand.getChildren().remove(selectedCards.get(i));
                handCards.remove(selectedCards.get(i));
            }
        }
        selectedCardCounter = 0;
        selectedCards.clear();

        drawCards(8 - handCards.size());
        setHandInfo(new ArrayList<>());
    }

    public void moveCards(StackPane pane) {
        int cardsize = 140;
        int lastPos = 570;

        int cards = pane.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                pane.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                pane.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardsize/2 + i * cardsize - cards/2*cardsize + i * 5;
                } else {
                    pos = i * cardsize - cards/2*cardsize + i * 5;
                }
            }
            pane.getChildren().get(i).setTranslateX(pos);
        }
    }

    public void sortRank() {
        sortedBy = "rank";
        List<PlayingCard> cards = new ArrayList<>();
        for(var card : HoldingHand.getChildren())
            if(card instanceof PlayingCard)
                cards.add((PlayingCard) card);

        cards.sort(Comparator
                .comparingInt(PlayingCard::getOrderPosition)
                .thenComparingInt(PlayingCard::getSuitOrder));

        Collections.reverse(cards);

        HoldingHand.getChildren().clear();
        HoldingHand.getChildren().addAll(cards);

        moveCards(HoldingHand);
    }

    public void sortSuit() {
        sortedBy = "suit";
        List<PlayingCard> cards = new ArrayList<>();
        for(var card : HoldingHand.getChildren())
            if(card instanceof PlayingCard)
                cards.add((PlayingCard) card);

        cards.sort(Comparator
                .comparingInt(PlayingCard::getSuitOrder)
                .thenComparingInt(PlayingCard::getOrderPosition));

        Collections.reverse(cards);

        HoldingHand.getChildren().clear();
        HoldingHand.getChildren().addAll(cards);

        moveCards(HoldingHand);
    }

    private void clearHandCards() {
        handCards.clear();
        HoldingHand.getChildren().clear();
    }

    //GAME HANDLER
    public void startNewGame(GameSetup gameSetup) {
        rand = new Random();
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        ante = 1;
        phase = 1;
        round = 1;
        money = 0;

        createBlindList();
        setBlindPanels();
        BlindPickPanels.setStageImage(new Image("file:" + stake.getStakeImageChipUrl()));

        try {
            setDeckImage();
            setStakeImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRound(Blind blind, BigDecimal score) {
        hideHandButtons();
        scoreToReach = score;
        toggleBlind();
        labelBlind.setText(blind.getBlindName());
        drawCards(handsize);
        toBeatScore.setText(String.valueOf(scoreToReach));
        toBeatImage.setImage(new Image("file:"+blind.getBlindImageUrl()));
        toBeatStake.setImage(new Image("file:"+stake.getStakeImageChipUrl()));
        toBeatReward.setText("$$$");
        clearHandInfo();
    }

    public void nextRound() {
        closeShop();
        round++;
        toggleBlind();
    }

    public void skip(Tag tag) {
        round++;
        addTag(tag);
    }

    private void addTag(Tag tag) {
        tagQueueList.add(tag);
        spaceTag.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(tag.getTagImageUrl()))));
    }

    private void skipBlind(Tag tag) {
        nextPhase();
    }

    private void nextPhase() {
        phase++;
        if(phase > 2){ phase = 0; }
    }

    private void countPoints() {
        for(int i = 0; i < spPlayedCards.getChildren().size(); i++) {
            if(spPlayedCards.getChildren().get(i).getTranslateY() == -20) {
                baseChips += ((PlayingCard)spPlayedCards.getChildren().get(i)).getValue();
            }
        }

        scored += baseChips * baseMulti;
        pointsScored.setText(String.valueOf(scored));

        if(scoreReached()) {
            openSummary();
            setRound(round++);
            clearScoredPoints();
            clearHandCards();

            if(allBlindsList.get(round-1).getId() > 1) {
                setAnte(ante++);
            }
        } else {
            hideHandButtons();
        }

        clearHandInfo();
    }

    private void setAnte(int i) {
        ante = i;
        setDisplayAnte();
    }

    private void setRound(int i) {
        round = i;
        setDisplayRound();
    }

    private boolean scoreReached() {
        return BigDecimal.valueOf(scored).compareTo(scoreToReach) != -1;
    }

    public void addMoney(int reward) {
        money += reward;
        setDisplayMoney();
        openShop();
    }

    private void setHandsAndDiscards() {
        hands = 3;
        discards = 3;

        setHandCounter();
        setDiscardCounter();
    }




    //BACKGROUND HANDLER
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException ignored) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public int getRound() {
        return round;
    }





    //TEST AREA

    public void flip() {
//        if(testImageView.getImage().getUrl() == "file:" + deck.getDeckCoverUrl()) {
//            System.out.println("is flipped");
//        }
//        ScaleTransition smaller = new ScaleTransition(Duration.seconds(1), testImageView);
//        smaller.setToX(0);
//
//        ScaleTransition larger = new ScaleTransition(Duration.seconds(10), testImageView);
//        larger.setToX(1);
//
//        smaller.play();
//        delay(2000, setImage());
//        //testImageView.setImage(new Image("file:" + deck.getDeckCoverUrl()));
//        larger.play();
//
    }


//    private Runnable setImage() {
//        testImageView.setImage(new Image("file:" + deck.getDeckCoverUrl()));
//        return null;
//    }
}
