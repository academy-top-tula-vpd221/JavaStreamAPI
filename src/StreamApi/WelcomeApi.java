package StreamApi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.*;

public class WelcomeApi {
    static public void WelcomeOne()
    {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for(int i = 0; i < 10; i++)
            numbers.add(-20 + (int)(Math.random() * 40));

        for(int i = 0; i < numbers.size(); i++)
            System.out.print(numbers.get(i) + " ");
        System.out.println();

        long count = 0;
//        for(int i = 0; i < numbers.length; i++)
//        {
//            if(numbers[i] > 0)
//                count++;
//        }
//        System.out.println("positive numbers: " + count);

        // stream from array
//        count = IntStream.of(numbers)
//                         .filter(n -> n > 0)
//                         .count();

        Stream<Integer> stream;
        stream = numbers.stream();
        count = stream.filter(n -> n > 0)
                      .count();
        System.out.println("positive numbers: " + count);

        stream = numbers.stream();
        System.out.println(stream.allMatch(n -> n > 0));

        stream = numbers.stream();
        System.out.println(stream.anyMatch(n -> n > 0));

        stream = numbers.stream();
        stream.filter(n -> n > 0)
              .forEach(n -> System.out.print(n + " "));
        System.out.println();
    }

}
