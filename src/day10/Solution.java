package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        System.out.println(findDifferencesProduct(getAdaptersList()));
        System.out.println(countAllCombinations(getAdaptersList()));
    }

    public static long countAllCombinations(List<Integer> adapters) {
        adapters.add(0);
        adapters.sort(Collections.reverseOrder());
        long[] combinations = new long[adapters.size()];
        for(int i = 0; i < adapters.size(); i++) {
            if(i == 0) {
                combinations[i] = 1;
                continue;
            }
            if(i == 1) {
                combinations[i] = 1;
                continue;
            }
            if(i == 2) {
                if(adapters.get(0) - adapters.get(i) <=3) {
                    combinations[i] = 2;
                } else {
                    combinations[i] = 1;
                }
                continue;
            }
            combinations[i] = 0;
            if (adapters.get(i-1) - adapters.get(i) <= 3) {
                combinations[i] +=  combinations[i-1];
            }
            if (adapters.get(i-2) - adapters.get(i) <= 3) {
                combinations[i] +=  combinations[i-2];
            }
            if (adapters.get(i-3) - adapters.get(i) <= 3) {
                combinations[i] +=  combinations[i-3];
            }
        }
        return combinations[combinations.length-1];
    }


    public static int countAllCombinations_recursive(List<Integer> adapters) {
        adapters.add(0);
        Collections.sort(adapters);
        return checkChain(adapters, 0);
    }

    public static int checkChain(List<Integer> adapters, int pos) {
        if (pos == adapters.size() - 1) {
            return 1;
        }
        int sum = 0;
        if (pos + 1 < adapters.size() && adapters.get(pos + 1) - adapters.get(pos) <= 3) {
            sum += checkChain(adapters, pos+1);
        }
        if (pos + 2 < adapters.size() && adapters.get(pos + 2) - adapters.get(pos) <= 3) {
            sum += checkChain(adapters, pos+2);
        }
        if (pos + 3 < adapters.size() && adapters.get(pos + 3) - adapters.get(pos) <= 3) {
            sum += checkChain(adapters, pos+3);
        }
        return sum;
    }

    private static int findDifferencesProduct(List<Integer> adapters) {
        int oneDiffCount = 0;
        int threeDiffCount = 0;
        Collections.sort(adapters);
        int currentJoltage = 0;
        for(int adapter : adapters) {
            int diff = adapter - currentJoltage;
            if (diff == 3) threeDiffCount++;
            if (diff == 1) oneDiffCount++;
            currentJoltage = adapter;
        }
        return oneDiffCount * (threeDiffCount + 1);
    }

    private static List<Integer> getAdaptersList() {
        List<Integer> numbers = new ArrayList<>();
        for (String line : getData()) {
            numbers.add(Integer.parseInt(line));
        }
        return numbers;
    }

    private static List<String> getData() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return fileToLineList(f);
    }

    private static List<String> fileToLineList(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
