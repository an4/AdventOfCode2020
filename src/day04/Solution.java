package day04;

import utils.Input;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        String input = readInput();
        List<String> passportsData = Input.getGroupsAsList(input);
        List<Passport> passports = getPassports(passportsData);
        System.out.println(countValidPassports(passports));

    }

    private static long countValidPassports(List<Passport> passports) {
        return passports.stream().filter(Passport::containsAllRequiredFields).count();
    }

    private static List<Passport> getPassports(List<String> passportsData) {
        List<Passport> passports = new ArrayList<>();
        for (String passportData : passportsData) {
            passports.add(getPassport(passportData));
        }
        return passports;
    }

    private static Passport getPassport(String passportData) {
        String[] keyValuePairs = passportData.split(" ");
        Passport passport = new Passport();
        for(String keyValuePair : keyValuePairs) {
            String[] keyAndValue = keyValuePair.trim().split(":");
            passport.setValue(keyAndValue[0], keyAndValue[1]);
        }
        return passport;
    }

    private static String readInput() {
        URL path = Solution.class.getResource("input.txt");
        File f = new File(path.getFile());
        return Input.fileToString(f);
    }
}