package org.example;

import java.util.Scanner;

public class TestAutomaton {
    public static String recognize(String text) {
        String state = "S";

        for (int index = 0; index < text.length(); index++) {
            char symbol = text.charAt(index);

            if ("F".equals(state)) {
                continue;
            }

            if (symbol == 'T') {
                state = "3".equals(state) ? "F" : "1";
            } else if ("1".equals(state) && symbol == 'E') {
                state = "2";
            } else if ("2".equals(state) && symbol == 'S') {
                state = "3";
            } else {
                state = "S";
            }
        }

        return state;
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            String text = input.hasNextLine() ? input.nextLine() : "";
            System.out.println(recognize(text));
        }
    }
}
