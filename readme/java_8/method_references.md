# Java 8

## Method References

[Definition](#definition)

[Reference to a Static Method](#reference-to-a-static-method)

[Reference to an Instance Method](#reference-to-an-instance-method)

[Reference to an Instance Method of an Object of a Particular Type](#reference-to-an-instance-method-of-an-object-of-a-particular-type)

[Reference to a Constructor](#reference-to-a-constructor)

### Definition

Method reference can be used as a shorter and more readable alternative for a lambda expression that only calls an existing method.

### Reference to a Static Method

**ClassName::methodName**

```java
boolean isReal = list.stream().anyMatch(User::isRealUser);
```

### Reference to an Instance Method

**objectReference::methodName**

```java
User user = new User();
boolean isLegalName = list.stream().anyMatch(user::isLegalName);
```

### Reference to an Instance Method of an Object of a Particular Type

**ClassName::methodName**

```java
long count = list.stream().filter(String::isEmpty).count();
```

### Reference to a Constructor

**ClassName::new**

```java
Stream<User> stream = list.stream().map(User::new);
```
