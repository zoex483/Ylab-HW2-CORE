package HW2;

import java.util.*;
import java.util.stream.Collectors;


public class ComplexExamples {
    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Дубликаты отфильтрованы, сгруппированы по имени, отсортированы по имени и идентификатору:");
        System.out.println();

        Map<Object, Long> groupingMap = Arrays.stream(Optional.of(RAW_DATA).orElseThrow(NullPointerException::new))
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(v -> v.id))
                .collect(Collectors.groupingBy(Person::name, Collectors.counting()));
        groupingMap.forEach((key, value) ->
        {
            System.out.println("Key: " + key);
            System.out.println("Value: " + value);
        });

        System.out.println("**************************************************");

        System.out.println(findPairOfNumbers(Arrays.asList(3, 4, 2, 7), 10));
        System.out.println("**************************************************");

        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel")); // true
        System.out.println(fuzzySearch("cwhl", "cartwheel")); // true
        System.out.println(fuzzySearch("cwhee", "cartwheel")); // true
        System.out.println(fuzzySearch("cartwheel", "cartwheel")); // true
        System.out.println(fuzzySearch("cwheeel", "cartwheel")); // false
        System.out.println(fuzzySearch("lw", "cartwheel")); // false
    }

    private static final Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"),
            new Person(1, "Harry"),
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

    public static boolean fuzzySearch(String expected, String stringToSearch) {
        if((expected == null || stringToSearch == null) || (expected == null && stringToSearch == null)){
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

    public static List<Integer> findPairOfNumbers(List<Integer> array, int sum) {
        List<Integer> list = array.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println(list);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (list.get(i) + list.get(j) == sum) {
                    result.add(list.get(i));
                    result.add(list.get(j));
                    return result;
                }
            }
        }
        return null;
    }

    record Person(int id, String name) {

        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Person person)) return false;
                return id() == person.id() && name().equals(person.name());
            }

            @Override
            public int hashCode() {
                return Objects.hash(id(), name());
            }

            @Override
            public String toString() {
                return "Person{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }
}
