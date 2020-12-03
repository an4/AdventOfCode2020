package day03;

import utils.Input;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<String> inputLineList = readInput();
        int[][] map = processInputAsMatrix(inputLineList);

        int a = countTreesOnPath(map, 1 , 1);
        int b = countTreesOnPath(map, 3 , 1);
        int c = countTreesOnPath(map, 5 , 1);
        int d = countTreesOnPath(map, 7 , 1);
        int e = countTreesOnPath(map, 1 , 2);

        System.out.println(b);
        System.out.println(a*b*c*d*e);

    }

    private static int countTreesOnPath(int[][] map, int direction_x, int direction_y) {
        int treeCount = 0;
        int x = 0;
        int y = 0;
        int width = map[0].length;

        while(y < map.length) {
            if(map[y][x] == 1) {
                treeCount++;
            }
            x = (x+direction_x)%width;
            y = y + direction_y;
        }
        return treeCount;
    }

    private static int[][] processInputAsMatrix(List<String> mapList) {
        int[][] map = new int[mapList.size()][mapList.get(0).length()];
        for(int i=0; i<mapList.size(); i++) {
            for(int j=0; j<mapList.get(i).length(); j++) {
                if (mapList.get(i).charAt(j) == '.') {
                    map[i][j] = 0;
                } else {
                    map[i][j] = 1;
                }
            }
        }
        return map;
    }

    private static List<String> readInput() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }

}
