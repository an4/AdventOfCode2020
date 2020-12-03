package utils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Input {
    public static List<String> fileToLineList(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
