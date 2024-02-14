# Java 8

## Interface Default and Static Methods

[Definition](#definition)

[Static Method](#static-method)

[Default Method](#default-method)

### Definition

Interfaces can have **static** and **default** methods that, despite being declared in an interface, have a defined behavior.

### Static Method

The static producer() method is available only through and inside of an interface. It can’t be overridden by an implementing class.

```java
public interface Vehicle() {
    static String producer() {
        return "C&T Vehicles";
    }
}
```

### Default Method

These methods are accessible through the instance of the implementing class and can be overridden.

```java
public interface Vehicle() {
    static String producer() {
        return "C&T Vehicles";
    }

    default String getOverview() {
        return "ATV made by " + producer();
    }
}
```

```java
public class VehicleImpl implements Vehicle() {}
```

```java
Vehicle vehicle = new VehicleImpl();
String overview = vehicle.getOverview();
```