package behaviorParameteriztion;

import java.util.ArrayList;
import java.util.List;

public class behaviorParameterization{
    /**
     * 단순필터링(노가다)
     * @param inventory
     * @return
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 색을 파라미터화
     * @param inventory
     * @param color
     * @return
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory ){
            if(apple.getColor().equals(color)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 가능한 모든것을 파라미터화
     * 문제점 1. 플래그의 역할이 애매함
     * 문제점 2. 둘 중하나만 필터링할떄 유연하게 대처할수 없음
     * @param inventory
     * @param color
     * @param weight
     * @param flag : 색과 컬러
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if((flag && apple.getColor().equals(color) ||
                    (!flag && apple.getWeight() > weight))){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * strategy design pattern 을 활용한 추상적 조건으로 필터링
     * @param inventory
     * @param p
     * @return
     */

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * Predicate 역할을 하는 인터페이스를 구현한 클래스
     */
    public class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(behaviorParameterization.Apple apple) {
            return apple.getWeight() > 150;
        }
    }
    public class AppleGreenColorPredicte implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    public void testAppleFilter(){
        List<Apple> apples = new ArrayList<>();
        List<Apple> result =
                filterApples(apples, new ApplePredicate() {
                    @Override
                    public boolean test(Apple apple) {
                        return false;
                    }
                });
    }
    public void testAppleFilter2(){
        List<Apple> apples = new ArrayList<>();
        List<Apple> result =
                filterApples(apples, (apple) -> "red".equals(apple.getColor()));
    }
    public void testAppleFilter3(){
        List<Apple> apples = new ArrayList<>();
        List<Apple> result =
                filter(apples, (Apple apple) -> "red".equals(apple.getColor()));
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for (T e : list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }
    public interface Predicate<T> {
        boolean test(T t);
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
