package day07;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bag {
    private String name;
    private Map<String, Integer> containers = new HashMap<>();
    private Map<String, Integer> items = new HashMap<>();

    public Bag(String name) {
        this.name = name;
    }

    public void setContainer(String bagName, int quantity) {
        containers.put(bagName, quantity);
    }

    public void setItem(String bagName, int quantity) {
        items.put(bagName, quantity);
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public Set<String> getAllContainers() {
        return containers.keySet();
    }


}
