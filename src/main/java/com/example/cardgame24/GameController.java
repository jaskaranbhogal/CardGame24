package com.example.cardgame24;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @FXML
    private ImageView cardImage1;

    @FXML
    private ImageView cardImage2;

    @FXML
    private ImageView cardImage3;

    @FXML
    private ImageView cardImage4;

    @FXML
    private TextField expressionField;

    @FXML
    private TextField solutionField;

    private final List<Card> deck = new ArrayList<>();
    private final List<Card> displayedCards = new ArrayList<>();
    private final ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
    private final SolutionFinder solutionFinder = new SolutionFinder();

    @FXML
    private void initialize() {
        createDeck();
        dealCards();
    }

    @FXML
    private void handleRefresh() {
        dealCards();
        expressionField.clear();
        solutionField.clear();
    }

    /**
     * Validates the user's expression before evaluating it. A valid attempt must
     * contain only supported arithmetic symbols and use each displayed card value
     * exactly once.
     */
    @FXML
    private void handleVerify() {
        String expression = expressionField.getText().trim();

        if (expression.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expression",
                    "Enter an arithmetic expression.");
            return;
        }

        if (!expression.matches("[0-9+\\-*/()\\s]+")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expression",
                    "Use only numbers, +, -, *, /, and parentheses.");
            return;
        }

        if (!usesDisplayedValues(expression)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expression",
                    "Use each displayed card value exactly once.");
            return;
        }

        try {
            double result = expressionEvaluator.evaluate(expression);

            if (Math.abs(result - 24.0) < 0.000000001) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Your expression equals 24!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Incorrect",
                        "Your expression evaluates to " + formatNumber(result) + ", not 24.");
            }
        } catch (IllegalArgumentException | ArithmeticException exception) {
            showAlert(Alert.AlertType.ERROR, "Invalid Expression",
                    exception.getMessage());
        }
    }

    @FXML
    private void handleFindSolution() {
        List<Integer> values = new ArrayList<>();

        for (Card card : displayedCards) {
            values.add(card.getValue());
        }

        String solution = solutionFinder.findSolution(values);

        if (solution == null) {
            solutionField.setText("No solution found");
        } else {
            solutionField.setText(solution);
        }
    }

    /**
     * Builds a standard 52-card deck. Face cards use the assignment values
     * Jack = 11, Queen = 12, King = 13, while Ace = 1.
     */
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

    /**
     * Shuffles a copy of the deck and displays the first four cards, ensuring
     * that the same physical card cannot appear twice in one hand.
     */
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

    /**
     * Compares the numbers typed in the expression with the displayed card
     * values as sorted lists. This also handles hands containing duplicate ranks.
     */
    private boolean usesDisplayedValues(String expression) {
        List<Integer> enteredValues = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(expression);

        while (matcher.find()) {
            enteredValues.add(Integer.parseInt(matcher.group()));
        }

        List<Integer> cardValues = new ArrayList<>();

        for (Card card : displayedCards) {
            cardValues.add(card.getValue());
        }

        Collections.sort(enteredValues);
        Collections.sort(cardValues);

        return enteredValues.equals(cardValues);
    }

    private void setCardImage(ImageView imageView, Card card) {
        String imageUrl = Objects.requireNonNull(
                getClass().getResource(card.getImagePath())
        ).toExternalForm();

        imageView.setImage(new Image(imageUrl));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatNumber(double value) {
        if (Math.abs(value - Math.rint(value)) < 0.000000001) {
            return String.valueOf((long) Math.rint(value));
        }

        return String.format("%.4f", value)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }
}
