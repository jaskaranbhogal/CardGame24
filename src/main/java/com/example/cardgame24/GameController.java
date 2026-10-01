package com.example.cardgame24;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameController {

    @FXML
    private ImageView cardImage1;

    @FXML
    private ImageView cardImage2;

    @FXML
    private ImageView cardImage3;

    @FXML
    private ImageView cardImage4;

    private final List<Card> deck = new ArrayList<>();
    private final List<Card> displayedCards = new ArrayList<>();

    @FXML
    private void initialize() {
        createDeck();
        dealCards();
    }

    private void createDeck() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] ranks = {
                "ace", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "jack", "queen", "king"
        };
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                String imagePath = "/com/example/cardgame24/cards/"
                        + ranks[i] + "_of_" + suit + ".png";

                deck.add(new Card(ranks[i], suit, values[i], imagePath));
            }
        }
    }

    private void dealCards() {
        List<Card> shuffledDeck = new ArrayList<>(deck);
        Collections.shuffle(shuffledDeck);

        displayedCards.clear();
        displayedCards.addAll(shuffledDeck.subList(0, 4));

        setCardImage(cardImage1, displayedCards.get(0));
        setCardImage(cardImage2, displayedCards.get(1));
        setCardImage(cardImage3, displayedCards.get(2));
        setCardImage(cardImage4, displayedCards.get(3));
    }

    private void setCardImage(ImageView imageView, Card card) {
        String imageUrl = Objects.requireNonNull(
                getClass().getResource(card.getImagePath())
        ).toExternalForm();

        imageView.setImage(new Image(imageUrl));
    }
}
