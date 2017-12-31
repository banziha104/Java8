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



