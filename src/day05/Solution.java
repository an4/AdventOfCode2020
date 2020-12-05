package day05;

import utils.Input;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Solution {
    private static int ROWS = 128;
    private static int COLS = 8;

    private static int getSeatId(int row, int col) {
        return row * 8 + col;
    }

    private static Map.Entry<Integer, Integer> getRowAndCol(String boardingPass) {
        int rowLowerBound = 0;
        int rowUpperBound = ROWS - 1;
        int colLowerBound = 0;
        int colUpperBound = COLS - 1;

        for(int i=0; i<7; i++) {
            if (boardingPass.charAt(i) == 'F') {
                rowUpperBound = rowUpperBound - (rowUpperBound-rowLowerBound)/2 - 1;
            } else {
                rowLowerBound = rowLowerBound + (rowUpperBound-rowLowerBound)/2 + 1;
            }
        }
        if(rowLowerBound != rowUpperBound) {
            System.out.println("Something is wrong");
        }

        for(int i=7; i<10; i++) {
            if (boardingPass.charAt(i) == 'L') {
                colUpperBound = colUpperBound - (colUpperBound-colLowerBound)/2 - 1;
            } else {
                colLowerBound = colLowerBound + (colUpperBound-colLowerBound)/2 + 1;
            }
        }
        return Map.entry(rowLowerBound, colLowerBound);
    }

    public static void main(String[] args) {
        List<String> boardingPasses = readInput();
        System.out.println(getHighestSeatId(boardingPasses));
        System.out.println(findMissingSeatId(boardingPasses));

    }

    private static int findMissingSeatId(List<String> boardingPasses) {
        Set<Integer> seatIdsSet = new TreeSet<>();
        for(String boardingPass : boardingPasses) {
            Map.Entry<Integer, Integer> rowAndCol = getRowAndCol(boardingPass);
            int seatId = getSeatId(rowAndCol.getKey(), rowAndCol.getValue());
            seatIdsSet.add(seatId);
        }
        Iterator<Integer> iterator = seatIdsSet.iterator();
        int previousId = iterator.next();
        while(iterator.hasNext()) {
            int currentId = iterator.next();
            if(previousId + 2 == currentId) {
                return previousId + 1;
            }
            previousId = currentId;
        }
        return -1;
    }

    private static int getHighestSeatId(List<String> boardingPasses) {
        int maxSeatId = 0;
        for(String boardingPass : boardingPasses) {
            Map.Entry<Integer, Integer> rowAndCol = getRowAndCol(boardingPass);
            int seatId = getSeatId(rowAndCol.getKey(), rowAndCol.getValue());
            if (seatId > maxSeatId) {
                maxSeatId = seatId;
            }
        }
        return maxSeatId;
    }

    private static List<String> readInput() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }
}
