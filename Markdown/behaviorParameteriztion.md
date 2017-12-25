# 동작 파라미터화

> 시시각각 바뀌는 요구사항에 효과적으로 대응할 수 있도록 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미

1. 한개의 속성을 필터링

```java
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
```
2. 속성 자체를 필터링

```java
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

```

3. 가능한 모든 속성을 필터링

```java
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

```



4. 추상적 조건으로 필터링

- strategy 패턴을 위한 interface 정의

```java
public interface ApplePredicate {
    boolean test (Apple apple);
}
```

- 구현

```java
/**
     * Predicate 역할을 한는 인터페이스를 구현한 클래스
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
```

- 적용

```java

    /**
     * strategy design pattern 을 활용한 추상적 조건으로 필터링
     * @param inventory
     * @param p
     * @return
     */
 
    public static List<Apple> filterAplles(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

```

5. 익명클래스 사용

```java
List<Apple> apples = new ArrayList<>();
        List<Apple> result = 
                filterApples(apples, new ApplePredicate() {
                    @Override
                    public boolean test(Apple apple) {
                        return false;
                    }
                });
```

6. 람다식 사용

```java

    public void testAppleFilter2(){
        List<Apple> apples = new ArrayList<>();
        List<Apple> result =
                filterApples(apples, (apple) -> "red".equals(apple.getColor()));
    }
```

7. 제너릭 사용

- 인터페이스 변경

```java
public interface Predicate<T> {
    boolean test(T t);
}
```

- 구현

```java
public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for (T e : list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }
```  

- 실행

```java
public void testAppleFilter3(){
        List<Apple> apples = new ArrayList<>();
        List<Apple> result =
                filter(apples, (Apple apple) -> "red".equals(apple.getColor()));
    }
    
```