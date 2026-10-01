package com.example.cardgame24;

/**
 * Represents one physical playing card and the value used by Card Game 24.
 */
public class Card {

    private final String rank;
    private final String suit;
    private final int value;
    private final String imagePath;

    public Card(String rank, String suit, int value, String imagePath) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.imagePath = imagePath;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }
}
