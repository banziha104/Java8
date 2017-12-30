package Stream;

import java.util.*;
import java.util.stream.*;

import static Stream.Dish.menu;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;



public class StreamBasic {

    public static void main(String...args) {
        List<String> dishNames =
        menu.stream() //스트림생성
                .filter(d -> d.getCalories() > 300) // 데이터 처리 연산
                .map((Dish d) -> d.getName())
                .limit(3)
                .collect(toList());

        System.out.println(dishNames);
    }
}
