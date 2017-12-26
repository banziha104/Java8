package Lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Comparator.*;
import java.util.List;
import Lambda.Lambda.Apple;
public class Lambda {

    static List<Apple> inventory = new ArrayList<>();

    public Lambda() {

    }
    @FunctionalInterface
    public interface BufferedReaderProcessor<T> {
        T process(T t) throws IOException;
    }

    public static String processFile(BufferedReaderProcessor<String> p) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }

    public static class AppleComparator implements Comparator<Apple>{
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
        /*익명 객체 사용*/
        public static void test(){
            inventory.sort(new Comparator<Apple>() {
                @Override
                public int compare(Apple o1, Apple o2) {
                    return o1.getWeight().compareTo(o2.getWeight());
                }
            });
        }
        public static void test2(){
            inventory.sort((Apple a1, Apple a2)-> a1.getWeight().compareTo(a2.getWeight()));
        }

        public static void test3(){
            inventory.sort(comparing(Apple::getWeight));
        }
    }

    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
 }
