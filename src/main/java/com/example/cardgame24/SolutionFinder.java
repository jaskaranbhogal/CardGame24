package com.example.cardgame24;

import java.util.ArrayList;
import java.util.List;

public class SolutionFinder {

    private static final double TARGET = 24.0;
    private static final double EPSILON = 0.000000001;

    public String findSolution(List<Integer> values) {
        List<Entry> entries = new ArrayList<>();

        for (int value : values) {
            entries.add(new Entry(value, String.valueOf(value)));
        }

        return search(entries);
    }

    private String search(List<Entry> entries) {
        if (entries.size() == 1) {
            if (Math.abs(entries.getFirst().value - TARGET) < EPSILON) {
                return entries.getFirst().expression;
            }

            return null;
        }

        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                Entry first = entries.get(i);
                Entry second = entries.get(j);

                List<Entry> remaining = new ArrayList<>();

                for (int k = 0; k < entries.size(); k++) {
                    if (k != i && k != j) {
                        remaining.add(entries.get(k));
                    }
                }

                List<Entry> combinations = createCombinations(first, second);

                for (Entry combination : combinations) {
                    remaining.add(combination);

                    String solution = search(remaining);

                    if (solution != null) {
                        return solution;
                    }

                    remaining.removeLast();
                }
            }
        }

        return null;
    }

    private List<Entry> createCombinations(Entry first, Entry second) {
        List<Entry> combinations = new ArrayList<>();

        combinations.add(new Entry(
                first.value + second.value,
                "(" + first.expression + " + " + second.expression + ")"
        ));

        combinations.add(new Entry(
                first.value * second.value,
                "(" + first.expression + " * " + second.expression + ")"
        ));

        combinations.add(new Entry(
                first.value - second.value,
                "(" + first.expression + " - " + second.expression + ")"
        ));

        combinations.add(new Entry(
                second.value - first.value,
                "(" + second.expression + " - " + first.expression + ")"
        ));

        if (Math.abs(second.value) >= EPSILON) {
            combinations.add(new Entry(
                    first.value / second.value,
                    "(" + first.expression + " / " + second.expression + ")"
            ));
        }

        if (Math.abs(first.value) >= EPSILON) {
            combinations.add(new Entry(
                    second.value / first.value,
                    "(" + second.expression + " / " + first.expression + ")"
            ));
        }

        return combinations;
    }

    private static class Entry {
        private final double value;
        private final String expression;

        private Entry(double value, String expression) {
            this.value = value;
            this.expression = expression;
        }
    }
}
