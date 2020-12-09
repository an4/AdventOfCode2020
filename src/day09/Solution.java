package day09;

import utils.Input;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        List<Long> numbers = getNumbers();
        long target = findFirstNumber(numbers);
        System.out.println(target);
        Map.Entry<Integer, Integer> range = getSumRange(numbers, target);
        System.out.println(getSumOfMinAndMaxOfRange(numbers, range.getKey(), range.getValue()));
    }

    private static long getSumOfMinAndMaxOfRange(List<Long> numbers, int start, int end) {
        long  max = numbers.get(start);
        long  min = numbers.get(start);
        for(int i=start+1; i<end; i++) {
            if (min > numbers.get(i)) {
                min = numbers.get(i);
            }
            if (max < numbers.get(i)) {
                max = numbers.get(i);
            }
        }
        return min+max;
    }

    private static Map.Entry<Integer, Integer> getSumRange(List<Long> numbers, long target) {
        int start = 0;
        int end = 0;
        long sum = 0;
        while(start <= end && end < numbers.size() && sum != target) {
            if (sum < target) {
                sum += numbers.get(end);
                end++;
            }
            if (sum > target) {
                sum -= numbers.get(start);
                start++;
            }
        }
        return Map.entry(start, end);
    }

    private static long findFirstNumber(List<Long> numbers) {
        int start = 0;
        int end = 25;
        for(int i=25; i<numbers.size(); i++) {
            boolean found = false;
            Set<Long> set = new HashSet<>();
            for(int j=start; j<end; j++) {
                if (set.contains(numbers.get(i) - numbers.get(j))) {
                    found = true;
                    break;
                } else {
                    set.add(numbers.get(j));
                }
            }
            if (!found) {
                return numbers.get(i);
            }
            start++;
            end++;
        }
        return 0;
    }

    private static List<Long> getNumbers() {
        List<Long> numbers = new ArrayList<>();
        for (String line : getData()) {
            numbers.add(Long.parseLong(line));
        }
        return numbers;
    }

    private static List<String> getData() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }
}
