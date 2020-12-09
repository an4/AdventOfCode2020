package day08;

import utils.Input;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        System.out.println(findValueAtStartOfLoop(getInstructions()));
        System.out.println(fixLoop(getInstructions()));
    }

    private static int fixLoop(List<String> instructions) {
        for(int i=0; i<instructions.size(); i++) {
            String[] opArg = instructions.get(i).split(" ");
            String op = opArg[0].trim();
            switch (op) {
                case "nop":
                    op = "jmp " + opArg[1].trim();
                    break;
                case "jmp":
                    op = "nop " + opArg[1].trim();
                    break;
                default:
                    continue;
            }
            List<String> newList = new ArrayList<>(instructions);
            newList.set(i, op);
            int acc = run(newList);
            if (acc != 0) {
                return acc;
            }
        }
        return 0;
    }

    private static int findValueAtStartOfLoop(List<String> instructions) {
        int acc = 0;
        int pos = 0;
        Set<Integer> executed = new HashSet<>();

        while(!executed.contains(pos)) {
            executed.add(pos);
            String[] opArg = instructions.get(pos).split(" ");
            String op = opArg[0].trim();
            switch (op) {
                case "nop":
                    pos++;
                    continue;
                case "acc":
                    int arg = Integer.parseInt(opArg[1]);
                    acc += arg;
                    pos++;
                    continue;
                case "jmp":
                    arg = Integer.parseInt(opArg[1]);
                    pos += arg;
                    break;
                default:
                    break;
            }
        }
        return acc;
    }

    private static int run(List<String> instructions) {
        int acc = 0;
        int pos = 0;
        Set<Integer> executed = new HashSet<>();

        while(!executed.contains(pos) && pos<instructions.size()) {
            executed.add(pos);
            String[] opArg = instructions.get(pos).split(" ");
            String op = opArg[0].trim();
            switch (op) {
                case "nop":
                    pos++;
                    continue;
                case "acc":
                    int arg = Integer.parseInt(opArg[1]);
                    acc += arg;
                    pos++;
                    continue;
                case "jmp":
                    arg = Integer.parseInt(opArg[1]);
                    pos += arg;
                    break;
                default:
                    break;
            }
        }
        if (pos == instructions.size()) {
            return acc;
        }
        return 0;
    }

    private static List<String> getInstructions() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }
}
