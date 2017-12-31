# 스트림

> 선언형(데이터를 임시 구현 코드 대신 질의로 표현)으로 컬렉션 데이터를 처리할 수 있으며 투명하게 병렬로 처리할 수 있는 기능.

- 연속된 요소 : 연속된 값의 집합
- 소스 : 컬렉션, 배열 I/O 자원 등의 데이터 제공 소스로부터 데이터를 소비한다 정렬된 컬렉션으로 스트림을 생성하면 정렬이 그대로 유지됌
- 데이터 처리 연산 : 스트림은 함수형 프로그래밍 언어에서 일반적으로 지원하는 연산과 데이터베이스와 비슷한 연산을 지원한다. 
- 파이프라이닝 : 대부분의 스트림 연산은 스트림 연산끼리 연결해서 커다란 파이프 라인을 구성할 수 있도록 스트림 자신을 반환하며, 게으름, 쇼트서킷과 같은 최적화도 얻을 수 있음
- 내부 반복 : 반복자를 이용해서 명시적으로 반복하는 컬렉션과 달리 스트림은 내부 반복을 지원함.
- 스트림은 반복자와 마찬가지로 한번만 탐색할 수 있다
- 내부반복을 사용한다(for-each를 사용하는 경우 외부반복, 반복을 알아서 처리하고 결과 값을 어딘가 저장하는 내부 반복을 사용)


```java
import static java.util.stream.Collector.toList;

List<String> dishNames =
        menu.stream() //스트림생성
                .filter(d -> d.getCalories() > 300) // 데이터 처리 연산
                .map((Dish d) -> d.getName()) 
                .limit(3)
                .collect(toList());

        System.out.println(dishNames);
```

---

<br>

# 스트림 만들기

- 스트림 만들기 

```java
Stream<String> stream = Stream.of("Java8","Lambda") //값으로 만들기

int[] numbers = {1,2,3,4}
Stream<Integer> stream = Arrays.stream(numbers) // 배열로 만들기

```

- 함수로 무한 스트림 만들기 : 고정된 스트림이 아닌 고정 되지않은 스트림을 만들수 있음


```java
/* iterate 메서드는 초깃값과 람다를 인수로 받아서 새로운 값을 끊임 없이 생산할 수 있다*/
Stream.iterate(0, n -> n + 2)
      .limit(10)
      .forEach(System.out::println);

Stream.generate(Math::random)
      .limit(5)
      .forEach(System.out::println);

```

---

<br>

- ### 스트림 연산
    - filter, map, limit는 서로 연결되어 파이프라인을 형성함
    - 중간연산 : filter 나 sorted 같은 연산은 다른 스트림을 반환한다
    - 최종연산 : 스트림 파이프라인에서 결과를 도출함.

<br>
    
---

# 스트림 활용

- filter : Predicate를 인수로 받아서 일치하는 모든 요소를 포함하는 스트림을 반환함.
- distinct : 고유 요소로 이루어진스트림을 반환함, 중복을 없엠
- limit : 스트림 축소, 처음 n개의 요소를 반환
- skip : 처음 n개의 요소를 제외한 스트림을 반환함
- map : 인수로 Functional을 받으며 새로운 요소로 매핑됌
- flatMap : flatMap 메서드는 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결하는 기능을 수행함(스트림 평면화)
- allMatch, anyMatch, noneMatch 등 검색과 매칭

<br>

---

# 리듀싱 

> reduce 연산을 이용해 스트림 요소를 처리해서 값으로 도출함

```java
/* 기존 방식 */
int sum = 0;
for (int x : number){
    sum += x;
}

/* 리듀스 연산 이용*/
int sum = number
    .stream()
    .reduce(0, // 첫번쨰 인수는 초기값
    (a,b)-> a+b); // 두번째 인수는 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>

/*최대값과 최소값 구하기*/
Optional<Integer> max = number.stream().reduce(Integer::max);

    
```

<br>

---

# 기본형 특화 스트림

- IntStream, DoubleStream, LongStream 의 세 가지 기본 특화형 스트림을 제공함

```java
int num = menu.stream()
            .mapToInt(Dish::getCalories) // IntStream 반환
            .sum();
```

- 객체스트림으로 복원하기

```java
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> stream = intStream.boxed() // 숫자스트림을 스트림으로 변환
```

- OptionalInt, OptionalDouble, OptionalLong

```java
OptionalInt maxCalories = menu.stream()
                                .mapToint(Dish::getCalories)
                                .max();

int max = maxCalories.orElse(1); // 값이 없을때 기본 최대값을 명시적으로 설정

```

- 숫자 범위

```java
IntStream evenNumbers = IntStream.rangeClosed(1, 100)// 1부터 100까지
                                    .filter( n -> n % 2 ==0);

```

# 스트림으로 데이터 수집

- Collectors 인터페이스의 구현은 스트림의 요소를 어떤 식으로 도출할지 지정한다
- Collectors 는 고급 리듀싱으로 다수준으로 그룹화 명령을 수행한다
- 스트림값에서 최댓값과 최솟값 검색

```java
Comparator<Dish> dc = Comparator.comparingInt(Dish::getCalories);
Optional<Dish> most = menu.stream().collect(maxBy(dc));
```

- Collectors.summingInt, averagingInt

```java
int totalCaloires = menu.stream().collect(summingInt(Dish::getCalories));
```

- IntSummaryStatics 클래스

```java
IntSummaryStatics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));

/*
* IntSummaryStatics 에 {count=9, sum=4300, min= 120, avertage= 477.778, max=800} 과 같이 저장된다*/

```

- joining() 문자열 연 : 각 객체에 toString 메서드를 호출해서 하나의 문자열로 연결해 반환한다

```java
String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));// ,로 구분한다
```

- 범용리듀싱 연산

```java
int totalCalories = menu.stream().collect(reducing(0,      //첫번째 인수는 리듀싱 연산의 시작값이거나 스트림에 인수가 없을 때의 반환값
                                                Dish::getCalories, //두번째 인수는 변환함수
                                                (i,j) -> i+j // 두 항목을 하나의 값으로 더하는 합계 함수
                                            ));
```

<br>

# 그룹화

> 데이터 집합을 하나 이상의 특성으로 분류해서 그룹화함

```java
Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
```

- 다수준 그룹화

```java
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = 
menu.stream().collect(
        groupingBy(Dish::getType), //첫번째 그룹화
            groupingBy(dish -> {
                if(dish.getCalories() <= 400){
                    return CaloricLevel.DIET;
                }else if (dish.getCalories() <= 700){
                    return CaloricLevel.NOMAL;
                }else{
                    return CaloricLevel.FAT;
                }
            })
)
```

- 서브그룹으로 데이터 수집

```java
```