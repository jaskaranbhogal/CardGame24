# Card Game 24

Card Game 24 is a JavaFX application based on the classic 24 game. Four playing cards are selected at random, and the player must use all four card values exactly once in an arithmetic expression that evaluates to 24.

## Card Values

- Ace = 1
- Number cards = face value
- Jack = 11
- Queen = 12
- King = 13

## Supported Operations

The expression field supports:

- Addition (+)
- Subtraction (-)
- Multiplication (*)
- Division (/)
- Parentheses

Normal operator precedence is applied.

## Features

- Displays four randomly selected playing cards
- Refreshes the hand with four new cards
- Verifies that the entered expression uses the displayed values exactly once
- Evaluates the expression and reports whether it equals 24
- Finds a valid solution automatically when one exists
- Handles invalid expressions and division by zero with JavaFX alerts

## Running the Project

The project uses JDK 25 and JavaFX 25.

From the project directory:

```bash
./mvnw javafx:run
```
