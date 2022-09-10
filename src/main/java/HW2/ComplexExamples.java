package HW2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ComplexExamples {
    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /**
        Task1
         */

        Map<Object, Long> result = Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull) // добавила фильтры на null
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));

        result.forEach((key, value) -> System.out.println("Key: " + key + "\nValue: " + value));

        /**
         Task2
         */

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Пара, дающая сумму - 10");
        System.out.println();

        List<Integer> numbers = List.of(3, 4, 2, 7);

        final String outputFormat = "[%s, %s]";

        AbstractMap.SimpleEntry<Integer, Integer> entry = numbers.stream()
                .flatMap(i -> numbers.stream().map(j -> new AbstractMap.SimpleEntry<>(Math.min(i, j), Math.max(i, j))))
                .filter(e -> e.getKey() + e.getValue() == 10)
                .filter(Objects::nonNull) // добавила фильтры на null
                .findFirst()
                .orElse(new AbstractMap.SimpleEntry<>(null, null));
        System.out.printf(outputFormat, entry.getKey(), entry.getValue());

        System.out.println("\n\n**************************************************");
        System.out.println();
        System.out.println("Функция нечеткого поиска:");
        System.out.println();

        /**
         Task3
         */

        Stream.of(
                fuzzySearch("car", "ca6$$#_rtwheel"),
                fuzzySearch("cwhl", "cartwheel"),
                fuzzySearch("cwhee", "cartwheel"),
                fuzzySearch("cartwheel", "cartwheel"),
                fuzzySearch("cwheeel", "cartwheel"),
                fuzzySearch("lw", "cartwheel")
        ).forEach(System.out::println);


    }

    public static boolean fuzzySearch(String expected, String stringToSearch) {
        if ((expected == null || stringToSearch == null) || (expected == null && stringToSearch == null)) {
            return false;
        }
        int index = 0;
        for (int i = 0; i < stringToSearch.length(); i++) {
            if (expected.charAt(index) == stringToSearch.charAt(i)) {
                index++;
            }
            if (index == expected.length()) {
                return true;
            }
        }
        return false;
    }
}