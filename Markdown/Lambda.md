# 람다

> 람다 표현식은 메서드로 전달할 수 있는 익명함수를 단순화한 것

### 특징

- 익명 : 이름이 없는 익명이다
- 함수 : 메서드처럼 특정 클래스에 종속되지 않기에 함수
- 전달 : 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다
- 간결성 : 익명 클래스의 보일러 플레이트가 없

### 구성

- 파라미터 리스트 
- 화살표 : 파라미터 리스트와 바디를 구분
- 람다의 바디

```java
/* 파라미터 리스트       화살표    람다의 바디*/
(Apple a1, Apple a2)  ->   a1.getWeight();
```


### 예제

```java
(String s) -> s.length()  // 파라미터 하나를 가지고 int를 반환, 람다식에는 return이 함축되어있음
(Apple a) -> a.getWeight()
(int x, int y) -> {
    System.out.println("result");
    System.out.println(x+y);
}
() -> 42 
() -> {42} //에러, 42 는 구문이아닌 표현식, 이경우 {return 42;} 로 명시해주어야함 
```


<br>

---

### 함수형 인터페이스

> 인터페이스로 필터 메서드를 파라미터화한 것 하나의 추상메서드를 지정하는 인터페이스, 많은 메서드가 있어도 추상 메서드가 오직 하나면 함수형 인터페이스

- function 패키지
    - Predicate : java.util.function.Predicate<T> 인터페이스는 boolean값을 리턴하는 test라는 추상 메서드를 정의함
    - Consumer : java.util.function.Consumer<T> 는 void를 반환하는 accept라는 추상메서드를 정의
    - Function : java.util.function.Function<T,R> 은 제너릭 T를받아 R을 반환하는 apply라는 추상메서드를 정의함.
    - Supplier : java.util.function.Supplier<T> 는 매개변수가 없고, T를 반환함 


---

### 함수 디스크립터

> 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 함

### @FunctionalInterface

> 함수형 인터페이스로 선언하며 실제로 함수형 인터페이스가 아니면 컴파일 에러를 발생시킴

### 형식 검사

> 람다가 사용되는 콘텍스트를 이용해 람다의 형식을 추론할 수 있음, 콘텍스트에서 기대되는 람다 표현식의 형식을 대상형식이라함.

### 형식 추론

> 자바 컴파일러는 람다 표현식이 사용된 컨텍스트를 이용해서 람다 표현식과 관련된 함수형 인터페이스를 자동으로 추론함

### 람다 캡쳐링

> 파라미터로 넘겨진 변수가 아닌 외부에서 활용하는 자유 변수를 활용하는 것, 다만 final로 선언되어 있어야한다.

### 메서드 레퍼런스

> 메서드를 직접 호출하는 것이 아니라 메서드명을 직접 참조시키는것, 람다의 축약형 같은 느낌

```java
(Apple a) -> a.getWeight(); // Apple::getWeight
() -> Thread.currentThread().dumpStack() // Thread.currentThread()::dumpStack
(str, i) -> str.substring(i) // String::subString
(String s) -> System.out.println(s) // System.out::println
```

```java
inventory.sort((Apple a1,Apple a2)->{ a1.getWeight().campareTo(a2.getWeight())});

inventory.sort(comparing(Apple::getWeight));
```

### 생성자 레퍼런스

> 메소드 레퍼런스처럼, 생성자를 직접 참조시키는 것.


<br>

---

# 람다, 메서드레퍼런스 활용

1. 코드 전달

```java

     public static class AppleComparator implements Comparator<Apple>{
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
            public static void test(){
                inventory.sort(new AppleComparator());
            }
        }
        
```

2. 익명 객체 사용

```java

        /*익명 객체 사용*/
        public static void test(){
            inventory.sort(new Comparator<Apple>() {
                @Override
                public int compare(Apple o1, Apple o2) {
                    return o1.getWeight().compareTo(o2.getWeight());
                }
            });
        }
```

3. 람다 사용

```java

        public static void test2(){
            inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        }
```

4. 메서드 레퍼런스 사용

```java
        public static void test3(){
            inventory.sort(comparing(Apple::getWeight));
        }
```