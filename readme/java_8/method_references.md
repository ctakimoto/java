# Java 8

## Method References

Method reference can be used as a shorter and more readable alternative for a lambda expression that only calls an existing method.

### Reference to a Static Method

**ContainingClass::methodName**

```java
boolean isReal = list.stream().anyMatch(User::isRealUser);
```

### Reference to an Instance Method

**containingInstance::methodName**

```java
User user = new User();
boolean isLegalName = list.stream().anyMatch(user::isLegalName);
```

### Reference to an Instance Method of an Object of a Particular Type

**ContainingType::methodName**

```java
long count = list.stream().filter(String::isEmpty).count();
```

### Reference to a Constructor

**ClassName::new**

```java
Stream<User> stream = list.stream().map(User::new);
```
