package day04;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Passport {
    private String birthYear = "byr";
    private String issueYear = "iyr";
    private String expirationYear = "eyr";
    private String height = "hgt";
    private String hairColor = "hcl";
    private String eyeColor = "ecl";
    private String passportId = "pid";
    private String countryId = "cid";

    Map<String, String> keyValue = new HashMap<>();

    public void setValue(String key, String value) {
        Set<String> validKeys = getValidKeys();
        if (validKeys.contains(key) && isValidValue(key, value)) {
            keyValue.put(key, value);
        }
    }

    public boolean containsAllRequiredFields() {
        return keyValue.size() == 8 || (keyValue.size() == 7 && keyValue.get(countryId) == null);
    }

    private boolean isValidValue(String key, String value) {
        if (key.equals(birthYear)) {
            int year = Integer.parseInt(value);
            return 1920 <= year && year <= 2002;
        }
        if (key.equals(issueYear)) {
            int year = Integer.parseInt(value);
            return 2010 <= year && year <= 2020;
        }
        if (key.equals(expirationYear)) {
            int year = Integer.parseInt(value);
            return 2020 <= year && year <= 2030;
        }
        if (key.equals(height)) {
            if (value.length() <= 3) return false;
            String unit = value.substring(value.length()-2);
            int height = Integer.parseInt(value.substring(0, value.length() - 2));
            if (unit.equals("cm")) {
                return 150<= height && height <= 193;
            }
            if (unit.equals("in")) {
                return 59<= height && height <=76;
            }
            return false;
        }
        if (key.equals(hairColor)) {
            Pattern p = Pattern.compile("#[a-f0-9]{6}");
            Matcher m = p.matcher(value);
            return m.matches();
        }
        if (key.equals(eyeColor)) {
            Set<String> validEyeColor = new HashSet<>();
            validEyeColor.add("amb");
            validEyeColor.add("blu");
            validEyeColor.add("brn");
            validEyeColor.add("gry");
            validEyeColor.add("grn");
            validEyeColor.add("hzl");
            validEyeColor.add("oth");
            return validEyeColor.contains(value);
        }
        if (key.equals(passportId)) {
            Pattern p = Pattern.compile("[0-9]{9}");
            Matcher m = p.matcher(value);
            return m.matches();
        }
        return key.equals(countryId);
    }


    private HashSet<String> getValidKeys() {
        HashSet<String> set = new HashSet<>();
        set.add(birthYear);
        set.add(issueYear);
        set.add(expirationYear);
        set.add(height);
        set.add(hairColor);
        set.add(eyeColor);
        set.add(passportId);
        set.add(countryId);
        return set;
    }
}
