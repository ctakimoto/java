# Java 5

## Generics

### Type Parameter vs Wildcard

#### Generics Classes

**We can’t use wildcards to define a generic class or interface.**

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```

#### Generic Methods - Method Parameters

To write a method with a generic type argument, we should use a type parameter.

```java
public static <T> void print(T item) {
    System.out.println(item);
}
```


**We can’t use wildcards directly to specify the type of a parameter in a method.**
The only place we can use wildcards is as part of a generic code, for example, as a generic type argument defined in the angle brackets.

There’s often a case when we can declare a generic method using either wildcards or type parameters. For instance, here are two possible declarations of a swap() method:

```java
public static <E> void swap(List<E> list, int src, int des);
public static void swap(List<?> list, int src, int des);
```

Whenever we have an unbounded generic type, we should prefer the second declaration.
**If a type parameter appears only once in the method declaration, we should consider replacing it with a wildcard.**

#### Generic Methods - Return Types

**When a generic method returns a generic type, we should use a type parameter instead of a wildcard:**

```java
public static <E> List<E> mergeTypeParameter(List<? extends E> listOne, List<? extends E> listTwo) {
    return Stream.concat(listOne.stream(), listTwo.stream())
            .collect(Collectors.toList());
}
```

#### Bounds - Upper-Bounded Types

```java
public static long sumWildcard(List<? extends Number> numbers) {
    return numbers.stream().mapToLong(Number::longValue).sum();
}
```

We could accomplish the same functionality using type parameters:

```java
public static <T extends Number> long sumTypeParameter(List<T> numbers) {
    return numbers.stream().mapToLong(Number::longValue).sum();
}
```

#### Bounds - Lower-Bounded Types

**This type of bound can be used only with a wildcard since type parameters don’t support them.**

```java
public static void addNumber(List<? super Integer> list, Integer number) {
    list.add(number);
}
```

#### Bounds - Multiple Bounds

A type parameter is useful when we want to restrict the type with multiple bounds.

```java
public static <T extends Number & Comparable<T>> void order(List<T> list) {
    list.sort(Comparable::compareTo);
}
```