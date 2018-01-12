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

```text
소스                 분해성
ArrayList           A
LinkedList          F
IntStream.range     A
Stream.iterate      F
HashSet             A
TreeSet             A
```
<br>

---

# 포크/조인 프레임워크

> 서브태스크를 스레드 풀 작업자 스레드에 분산 할당하는 ExecutorService 인터페이스를 구현함

- 주의점
    - join 메서드를 태스크에 호출하면 테스크가 생산하는 결과가 준비될때까지 호출자를 블록시킴
    - RecursiveTask 내에서는 ForkJoinPool의 invoke 메서드를 사용하지 말아야한다. 순차코드에서 병렬 계산을 시작할때만 사용
    - 서브태스크의 fork 메서드를 호출해서 ForkJoinPool의 일정을 조절할 수 있다. 양쪽다 fork를 호출하는 것 보다는 compute를 호출하는 것이 효율적
    - 포커조인 프레임워크를 사용하는 병렬 계산은 디버깅하기 어렵다
    - 무조건 병렬 스트림이 순차 처리보다 빠를 거라는 생각은 버려야한다
    
```java
public class ForkJoinSumCalculator extends RecursiveTask<Long> { //RecursiveTask<T>를 상속

    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {// 재귀생성자
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() { // RecursiveTask의 추상메서드 오버라이드
        int length = end - start;
        if (length <= THRESHOLD) { // 만개 이하일땐 
            return computeSequentially();   //순차적으로 계산
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2); 
        leftTask.fork(); //새로운 스레드로 생성
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        Long rightResult = rightTask.compute(); // 두 번째 테스크는 동기실행함, 추가 분할이 일어남
        Long leftResult = leftTask.join(); // 첫번째 서브태스크의 결과를 읽거나 아직 결과가 없으면 기다린다.
        return leftResult + rightResult; // 두개의 값을 더한다
    }

    private long computeSequentially() { // 더 분할할 수 없을 때 서브태스크의 결과를 계산
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}
```

### 작업 훔치기

> ForkJoinPool의 모든 스레드를 거의 공정하게 분할함.
