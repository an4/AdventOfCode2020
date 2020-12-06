package day06;

import utils.Input;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        List<String> groups = Input.getGroupsAsList(readInput());
        System.out.println(getAllGroupsAnsweredQuestionsCount(groups));
        System.out.println(getAllGroupsCommonAnsweredQuestionsCount(groups));
    }

    private static int getAllGroupsAnsweredQuestionsCount(List<String> groups) {
        int sum = 0;
        for (String group : groups) {
            sum += getGroupAnsweredQuestionCount(group);
        }
        return sum;
    }

    private static int getAllGroupsCommonAnsweredQuestionsCount(List<String> groups) {
        int sum = 0;
        for (String group : groups) {
            sum += getGroupCommonAnsweredQuestionsCount(group);
        }
        return sum;
    }

    private static int getGroupCommonAnsweredQuestionsCount(String group) {
        String[] memberAnswers = group.split(" ");
        if (memberAnswers.length == 1) {
            return memberAnswers[0].length();
        }
        Set<Character> answerSet = getMemberAnswerSet(memberAnswers[0]);
        for (String memberAnswer : memberAnswers) {
            Set<Character> memberAnswerSet = getMemberAnswerSet(memberAnswer);
            answerSet.retainAll(memberAnswerSet);
        }
        return answerSet.size();
    }

    private static Set<Character> getMemberAnswerSet(String memberAnswers) {
        Set<Character> answerSet = new HashSet<>();
        char[] answers = memberAnswers.toCharArray();
        for (char c: answers) {
            answerSet.add(c);
        }
        return answerSet;
    }

    private static int getGroupAnsweredQuestionCount(String group) {
        String[] memberAnswers = group.split(" ");
        Set<Character> answerSet = new HashSet<>();
        for (String memberAnswer : memberAnswers) {
            char[] answersArray = memberAnswer.toCharArray();
            for (char c : answersArray) {
                answerSet.add(c);
            }
        }
        return answerSet.size();
    }

    private static String readInput() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToString(f);
    }
}
