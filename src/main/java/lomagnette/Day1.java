package be.lomagnette;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author lma
 * @date 04/12/2023
 */
public class Day1 {

    private static final Map<String, Integer> INTEGER_MAP= Map.of(
        "one", 1,
        "two", 2,
        "three", 3,
        "four", 4,
        "five", 5,
        "six", 6,
        "seven", 7,
        "eight", 8,
        "nine", 9
    );

    public static void main(String[] args) throws IOException {
        var data = Files.readString(Path.of("/Users/lma/sandbox/lab-java/AdventOfCode/src/main/resources/day-1.txt"));
        var data2 = stringToNumber(data).split("\n");
        var dataset = List.of(data2);
        List<Integer> list = dataset.stream().map(be.lomagnette.Day1::getNumber).toList();

        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
    }

    private static String stringToNumber(String value) {
        var convertedValue = new String(value);
        for(Map.Entry<String,Integer> e : INTEGER_MAP.entrySet()) {
            convertedValue = convertedValue.replaceAll(e.getKey(), e.getKey()+e.getValue()+e.getKey());
        }
        return convertedValue;
    }

    private static int getNumber(String value) {
        var numbers= value.chars().filter(Character::isDigit).boxed().map(Character::getNumericValue).toList();
        var first = numbers.get(0);
        var last = numbers.get(numbers.size()-1);
        var numberFound = Integer.parseInt("" + first + last);
        return numberFound;
    }



}
