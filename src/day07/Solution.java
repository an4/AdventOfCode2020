package day07;

import utils.Input;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        System.out.println(howManyBagsContain(getBags(getRules())));
        System.out.println(howManyBagsAreContained(getBags(getRules())));
    }

    private static int howManyBagsContain(Map<String, Bag> bags) {
        String targetBagName = "shiny gold";
        Bag targetBag = bags.get(targetBagName);
        Set<String> containers = new HashSet<>();
        Queue<String> queue = new LinkedList<>(targetBag.getAllContainers());
        while(!queue.isEmpty()) {
            String currentBag = queue.poll();
            if (!containers.contains(currentBag)) {
                containers.add(currentBag);
                if (bags.containsKey(currentBag)) {
                    queue.addAll(bags.get(currentBag).getAllContainers());
                }
            }
        }
        return containers.size();
    }

    private static int howManyBagsAreContained(Map<String, Bag> bags) {
        String targetBagName = "shiny gold";
        Bag targetBag = bags.get(targetBagName);
        return getContainingBagCount(targetBag, bags);
    }

    private static int getContainingBagCount(Bag bag, Map<String, Bag> bags) {
        int sum = 0;
        if (bag.getItems().size() == 0) {
            return 0;
        }
        for (String itemName : bag.getItems().keySet()) {
            int c = getContainingBagCount(bags.get(itemName), bags);
            sum = sum + bag.getItems().get(itemName) + bag.getItems().get(itemName) * c;
        }
        return sum;
    }

    private static Map<String, Bag> getBags(List<String> rules) {
        Map<String, Bag> map = new HashMap<>() {
        };
        for(String rule: rules) {
            String[] bagNameAndItems = rule.substring(0, rule.length()-1).split("contain");
            String bagName = bagNameAndItems[0].trim();
            bagName = bagName.substring(0, bagName.length()-5).trim();
            Bag currentBag = map.containsKey(bagName) ? map.get(bagName) : new Bag(bagName);
            String items = bagNameAndItems[1].trim();
            if (items.equals("no other bags")) {
                continue;
            } else {
                String[] itemArr = items.split(",");
                for (String item: itemArr) {
                    String[] itemCountAndName = item.trim().split(" ");
                    int count = Integer.parseInt(itemCountAndName[0]);
                    String itemName = itemCountAndName[1] + " " + itemCountAndName[2];
                    Bag itemBag = map.containsKey(itemName) ? map.get(itemName) : new Bag(itemName);
                    itemBag.setContainer(bagName, count);
                    map.put(itemName, itemBag);

                    currentBag.setItem(itemName, count);
                }
            }
            map.put(bagName, currentBag);
        }
        return map;
    }

    private static List<String> getRules() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToLineList(f);
    }
}
