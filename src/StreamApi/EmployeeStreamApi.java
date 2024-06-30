package StreamApi;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeStreamApi{
    static ArrayList<Employee> employees = new ArrayList<Employee>();
    static ArrayList<Integer> numbers = new ArrayList<Integer>();


    static Stream<Employee> stream;

    static void dataInit(){
        employees.add(new Employee("Bobby", 19, "Yandex"));
        employees.add(new Employee("Sam", 25, "Ozon"));
        employees.add(new Employee("Jimmy", 38, "Yandex"));
        employees.add(new Employee("Tom", 22, "Ozon"));
        employees.add(new Employee("Mikky", 41, "Yandex"));

        for(int i = 0; i < 10; i++)
            //numbers.add(-20 + (int)(Math.random() * 40));
            numbers.add(-10 + (int)(Math.random() * 20));
    }
    static public void Filter(){

        dataInit();

        stream = employees.stream();
//        stream.filter(e -> e.getName().length() > 3)
//                .forEach(e -> System.out.println(e.getName() + " " + e.getAge()));
//        stream.filter(e -> e.getAge() >= 25)
//                .forEach(e -> System.out.println(e.getName() + " " + e.getAge()));
        stream.filter(e -> e.getAge() >= 25)
                .forEach(e -> System.out.println(e.getName() + " " + e.getAge()));

    }
    static public void Maps() {
        dataInit();
//        employees.stream()
//                .map(e -> "Name: " + e.getName() + ", Age: " + e.getAge())
//                .forEach(s -> System.out.println(s));
//        employees.stream().flatMap(e -> Stream.of(e.getName(),
//                                                String.format("%d", e.getAge())))
//                .forEach(s -> System.out.println(s));

        employees.stream()
                .sorted(new EmployeeAgeComparator())
                .forEach(e -> System.out.println("Name: " + e.getName() + ", Age: " + e.getAge()));
//        numbers.stream()
//                .sorted()
//                .forEach(n -> System.out.print(n + " "));
//        System.out.println();
    }
    static public void SkipsLimitsTakes(){
        dataInit();
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

//        numbers.stream().takeWhile(n -> n >= 0)
//                .forEach(n -> System.out.print(n + " "));
//        System.out.println();
//        numbers.stream().dropWhile(n -> n >= 0)
//                .forEach(n -> System.out.print(n + " "));
//        System.out.println();

//        Stream<Integer> streamAdd = Stream.of(10, 20, 30, 40, 50);
//
//        Stream.concat(numbers.stream(), streamAdd)
//                .forEach(n -> System.out.print(n + " "));

//        numbers.stream()
//                .distinct()
//                .forEach(n -> System.out.print(n + " "));
//        System.out.println();

        int page = 0;
        int limit = 5;
        while((page + 1) * limit <= numbers.size()){
            numbers.stream()
                    .skip(page * limit)
                    .limit(limit)
                    .forEach(n -> System.out.print(n + " "));
            System.out.println("\n");
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            page++;
        }

    }
    static public void TerminalMethods() throws IllegalAccessException {
        dataInit();
//        numbers.stream().forEach(n -> System.out.print(n + " "));
        employees.stream().forEach(e-> System.out.println("Age: " + e.getAge()));
        System.out.println();

//        long count = numbers.stream().filter(n -> n > 25).count();
//        System.out.println(count);

//        Optional<Integer> first = numbers.stream().findFirst();
//        Optional<Integer> any = numbers.stream().findAny();
//        System.out.println(first.get());
//        System.out.println(any.get());


//        System.out.println(numbers.stream().max(Integer::compare).get());
//        System.out.println(numbers.stream().min(Integer::compare).get());
//        System.out.println(employees.stream().max(new EmployeeNameComparator()).get().getName());

//        System.out.println(numbers.stream().reduce((a, b) -> a + b).get());
//        System.out.println(numbers.stream().reduce(1000, (a, b) -> a + b));
//
//        Stream<String> words = Stream.of("one", "two", "three");
//        String result = words.reduce("Words:", (w1, w2) -> w1 + " " + w2);
//        System.out.println(result);

//        int ageSum = employees.stream()
//                .reduce(0,
//                        (a, b) -> a + b.getAge(),
//                        (a, b) -> a + b
//                        );
//        System.out.println(ageSum);

        Optional<Integer> result = numbers.stream().reduce((a, b) -> a + b);
        result.orElse(-1);
        Random random = new Random();
        result.orElseGet(() -> random.nextInt());
        result.orElseThrow(IllegalAccessException::new);

        if(result.isPresent())
            System.out.println(result.get());

        result.ifPresentOrElse(
                value -> System.out.println(value),
                () -> System.out.println("no result")
        );
    }
    static public void Collect(){
        dataInit();
//        numbers.stream().forEach(n -> System.out.print(n + " "));
//        employees.stream().forEach(e-> System.out.println("Age: " + e.getAge()));
        System.out.println();

//        List<Integer> intList = numbers.stream()
//                .filter(n -> n > 0)
//                .collect(Collectors.toList());
//
//        for(int item : intList)
//            System.out.print(item + " ");
//        System.out.println();
//
//        Set<Integer> intSet = numbers.stream()
//                .filter(n -> n > 0)
//                .collect(Collectors.toSet());
//
//
//        Map<String, Integer> emplMap = employees.stream()
//                .collect(Collectors.toMap(e -> e.getName(), e -> e.getAge()));
//
//        emplMap.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
//
//        HashSet<Employee> emplHashSet = employees.stream()
//                .collect(Collectors.toCollection(HashSet::new));
//
//        emplHashSet.forEach((e) -> System.out.println("Name: " + e.getName() + ", Value: " + e.getAge()));
        Map<String, List<Employee>> emplCompany = employees.stream()
                .parallel()
                .collect(Collectors.groupingBy(Employee::getCompany));

        Map<String, Long> compCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCompany, Collectors.summingLong(Employee::getAge)));

        for(Map.Entry<String, List<Employee>> item : emplCompany.entrySet()){
            System.out.println(item.getKey());
            for(Employee e : item.getValue())
                System.out.printf("\t%s %d\n", e.getName(), e.getAge());
        }

        System.out.println();

        Map<Boolean, List<Employee>> emplAges = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getAge() < 25));

        for(Map.Entry<Boolean, List<Employee>> item : emplAges.entrySet()){
            System.out.println(item.getKey());
            for(Employee e : item.getValue())
                System.out.printf("\t%s %d\n", e.getName(), e.getAge());
        }

    }

    static public void ArraysParallel(){
        dataInit();
//        Random random = new Random();
//        int[] numbers = new int[10];
//        Arrays.parallelSetAll(numbers, n -> random.nextInt());
//        Arrays.stream(numbers).forEach(n -> System.out.print(n + " "));
        employees.stream()
                        .forEach(e -> System.out.println(e.getName() + " " + e.getAge()));
        System.out.println();

        Employee[] employeesArr = employees.toArray(Employee[]::new);
        Arrays.parallelSetAll(employeesArr, i -> {
            employeesArr[i].setAge(employeesArr[i].getAge() + 1);
            return employeesArr[i];
        });
        Arrays.parallelSort(employeesArr);

        Arrays.stream(employeesArr)
                .forEach(e -> System.out.println(e.getName() + " " + e.getAge()));
        System.out.println();
    }
}
class Employee implements Comparable {
    private String name;
    private int age;
    private String company = "";

    public Employee(String name, int age, String company){
        this.name = name;
        this.age = age;
        this.company = company;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Employee)o).name);
    }
}

class EmployeeNameComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}

class EmployeeAgeComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getAge() - o2.getAge();
    }
}



