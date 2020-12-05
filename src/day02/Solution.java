package day02;

import utils.Input;

import java.io.*;
import java.net.URL;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        List<String> input = processInput();
        countValidPasswordsWithCount(input);
        countValidPasswordsWithPosition(input);
    }

    private static void countValidPasswordsWithCount(List<String> list) {
        System.out.println(list.stream().filter(Solution::isValidPasswordWithCount).count());
    }

    private static void countValidPasswordsWithPosition(List<String> list) {
        System.out.println(list.stream().filter(Solution::isValidPasswordWithPosition).count());
    }

    private static boolean isValidPasswordWithCount(String input) {
        String[] inputItems = input.split(":");
        assert(inputItems.length == 2);
        String password = inputItems[1].trim();
        String policy = inputItems[0];
        String[] policyItems = policy.split(" ");
        assert(policyItems.length == 2);
        String count = policyItems[0].trim();
        String[] bounds = count.split("-");
        assert(bounds.length == 2);
        int lowerBound = Integer.parseInt(bounds[0]);
        int upperBound = Integer.parseInt(bounds[1]);
        String character = policyItems[1].trim();
        assert (character.length() == 1);
        char letter = character.charAt(0);

        int letterCount = 0;
        for (int i=0; i<password.length(); i++) {
            if (password.charAt(i) == letter) {
                letterCount++;
            }
        }
        return letterCount >= lowerBound && letterCount <= upperBound;
    }

    private static boolean isValidPasswordWithPosition(String input) {
        String[] inputItems = input.split(":");
        assert(inputItems.length == 2);
        String password = inputItems[1].trim();
        String policy = inputItems[0];
        String[] policyItems = policy.split(" ");
        assert(policyItems.length == 2);
        String count = policyItems[0].trim();
        String[] bounds = count.split("-");
        assert(bounds.length == 2);
        int position1 = Integer.parseInt(bounds[0]) - 1;
        int position2 = Integer.parseInt(bounds[1]) - 1;
        String character = policyItems[1].trim();
        assert (character.length() == 1);
        char letter = character.charAt(0);

        boolean isLetterInFirstPosition = password.charAt(position1) == letter;
        boolean isLetterInSecondPosition = password.charAt(position2) == letter;
        return isLetterInFirstPosition != isLetterInSecondPosition;
    }

    private static List<String> processInput() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }
}