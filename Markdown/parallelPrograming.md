# 병렬 데이터 처리와 성능

> 컴퓨터의 멀티코어를 활용해서 파이프라인 연산을 실행살 수 있음

- 병렬 스트림 생성

```java
menu.parallelStream() // 병렬 스트림 생성
```

- 순차 스트림을 병렬 스트림으로 변경

```java
    Stream.iterate(1L , i -> i +1)
          .limit(n)
          .parallel() //병렬스트림으로 전환
          .sequential() // 순차스트림으로 전환
          .reduce(0L, Long::sum)
```

<br>

---

# 병렬 스트림 사용

- 데이터 레이스 문제가 일어나지 않도록 한다(상태 공유에 따른 부작용을 피해야한다)

```java
public static long sideEffectsum(long n){
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1,n).forEach(accumulator::add);
    return accumulator.total;   // 다수의 스레드가 동시에 데이터에 접근하는 '데이터 레이스' 현상 발생
    
public class Accumulator {
    public long total = 0;
    public void add(long value) { total += value;} 
}

```

- 직접 벤치마크하고 성능을 측정한다
- 박싱을 주의 자동 박싱과 언박싱은 성능을 크게 저하시킬 수 있다.
- 되도록이면 기본형 특화 스트림을 사용하는 것이 좋다
- 병렬 스트림은 limit나 findFirst처럼 요소의 순서에 의존하는 연산에 성능이 떨어진다
- 하나의 요소를 처리하는데 비용이 많이 든다면, 이는 병렬 스트림을 사용해 성능을 개선할 수 있음을 의미한다
- 소량의 데이터에서는 병렬 스트림이 도움 되지 않는다.
- 스트림을 구성하는 자료구조가 적절한지 확인할 것
- 스트림의 특성과 파이프라인의 중간 연산이 스트림의 특성을 어떻게 바꾸는지에 따라 분해 과정의 성능이 달라질 수 있다

<br>

---

# 포크/조인 프레임워크
