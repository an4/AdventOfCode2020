package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        System.out.println(partOne(getSeatMatrix(getData())));
        System.out.println(partTwo(getSeatMatrix(getData())));
    }

    private static void printMatrix(char[][] matrix) {
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int partOne(char[][] matrix) {
        char[][] newMatrix = runOneRoundAdjacent(matrix);
        while (!isEqual(newMatrix, matrix)) {
            matrix = Arrays.stream(newMatrix).map(char[]::clone).toArray(char[][]::new);
            newMatrix = runOneRoundAdjacent(matrix);
        }
        return countOccupiedSeats(matrix);
    }

    private static int partTwo(char[][] matrix) {
        char[][] newMatrix = runOneRound(matrix);
        while (!isEqual(newMatrix, matrix)) {
            matrix = Arrays.stream(newMatrix).map(char[]::clone).toArray(char[][]::new);
            newMatrix = runOneRound(matrix);
        }
        return countOccupiedSeats(matrix);
    }

    private static boolean isEqual(char[][] a, char[][] b) {
        if(a.length != b.length || a[0].length != b[0].length) {
            return false;
        }
        for (int i=0; i<a.length; i++) {
            for (int j=0; j<a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char[][] runOneRoundAdjacent(char[][] matrix) {
        char[][] newMatrix = new char[matrix.length][matrix[0].length];
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                fillPositionUsingNeighbors(matrix, newMatrix, i, j);
            }
        }
        return newMatrix;
    }

    private static char[][] runOneRound(char[][] matrix) {
        char[][] newMatrix = new char[matrix.length][matrix[0].length];
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                fillPosition(matrix, newMatrix, i, j);
            }
        }
        return newMatrix;
    }

    private static void fillPosition(char[][] originalMatrix, char[][] newMatrix, int cr, int cc) {
        if (originalMatrix[cr][cc] == '.') {
            newMatrix[cr][cc] = '.';
            return;
        }

        int[] row = new int[]{-1, 0, 1};
        int[] col = new int[]{-1, 0, 1};

        int rows = originalMatrix.length;
        int cols = originalMatrix[0].length;

        int occupiedChairs = 0;

        for(int i=0; i<row.length; i++) {
            for(int j=0;j<col.length; j++) {
                if(row[i] == 0 && col[j] == 0) {
                    // skip the current element
                    continue;
                }
                int r = cr + row[i];
                int c = cc + col[j];
                while (r >= 0 && r < rows && c >=0 && c < cols && originalMatrix[r][c] == '.') {
                    r += row[i];
                    c += col[j];
                }
                if (r >= 0 && r < rows && c >=0 && c < cols && originalMatrix[r][c] == '#') {
                    occupiedChairs++;
                }
            }
        }

        if (originalMatrix[cr][cc] == 'L' && occupiedChairs == 0) {
            newMatrix[cr][cc] = '#';
        } else if(originalMatrix[cr][cc] == '#' && occupiedChairs >= 5) {
            newMatrix[cr][cc] = 'L';
        } else {
            newMatrix[cr][cc] = originalMatrix[cr][cc];
        }
    }

    private static void fillPositionUsingNeighbors(char[][] originalMatrix, char[][] newMatrix, int currentRow, int currentColumn) {
        if (originalMatrix[currentRow][currentColumn] == '.') {
            newMatrix[currentRow][currentColumn] = '.';
            return;
        }

        int[] row = new int[]{-1, 0, 1};
        int[] col = new int[]{-1, 0, 1};

        int validCells = 0;
        int floor = 0;
        int emptyChairs = 0;
        int occupiedChairs = 0;

        int rows = originalMatrix.length;
        int cols = originalMatrix[0].length;

        for(int i=0; i<row.length; i++) {
            for(int j=0; j<col.length; j++) {
                int r = currentRow + row[i];
                int c = currentColumn + col[j];
                if (row[i] == 0 && row[j] == 0) {
                    continue;
                }
                if (r >= 0 && r < rows && c >=0 && c < cols) {
                    if (originalMatrix[r][c] == '.') {
                        floor++;
                    } else if (originalMatrix[r][c] == 'L') {
                        emptyChairs++;
                    } else if (originalMatrix[r][c] == '#') {
                        occupiedChairs++;
                    } else {
                        System.out.println("Unknown character");
                    }
                    validCells++;
                }
            }
        }

        if (originalMatrix[currentRow][currentColumn] == 'L' && occupiedChairs == 0) {
            // If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
            newMatrix[currentRow][currentColumn] = '#';
        } else if(originalMatrix[currentRow][currentColumn] == '#' && occupiedChairs >= 4) {
            // If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
            newMatrix[currentRow][currentColumn] = 'L';
        } else {
            newMatrix[currentRow][currentColumn] = originalMatrix[currentRow][currentColumn];
        }
    }

    private static int countOccupiedSeats(char[][] matrix) {
        int count = 0;
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private static char[][] getSeatMatrix(List<String> rowsList) {
        char[][] matrix = new char[rowsList.size()][rowsList.get(0).length()];
        for(int i=0; i<matrix.length; i++) {
            String row = rowsList.get(i);
            for(int j=0; j<matrix[0].length; j++) {
                if (row.charAt(j) == 'L') {
                    matrix[i][j] = 'L';
                } else {
                    matrix[i][j]= '.';
                }
            }
        }
        return matrix;
    }

    private static List<String> getData() {
        URL path =Solution.class.getResource("input.txt");
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
