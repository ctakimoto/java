# Java 8

## Optional&lt;T&gt;

Java 8 Optional&lt;T&gt; class can help to handle situations where there is a possibility of getting the NPE. It works as a container for the object of type T. It can return a value of this object if this value is not a null. When the value inside this container is null, it allows doing some predefined actions instead of throwing NPE.

### Creation of the Optional&lt;T&gt;

Empty Optional

```java
Optional<String> optional = Optional.empty();
```

Non-null value Optional

```java
String str = "value";
Optional<String> optional = Optional.of(str);
```

Empty or specific value Optional

```java
Optional<String> optional = Optional.ofNullable(getString());
```

### Optional&lt;T&gt; Usage

#### Example 1

Let’s say we expect to get a List&lt;String&gt;, and in the case of null, we want to substitute it with a new instance of an ArrayList&lt;String&gt;.

Before Java 8
```java
List<String> list = getList();
List<String> listOpt = list != null ? list : new ArrayList<>();
```

With Java 8
```java
List<String> listOpt = getList().orElseGet(() -> new ArrayList<>());
```

#### Example 2

Assume we have an object of type User that has a field of type Address with a field street of type String, and we need to return a value of the street field if some exist or a default value if street is null:

Before Java 8
```java
User user = getUser();
if (user != null) {
    Address address = user.getAddress();
    if (address != null) {
        String street = address.getStreet();
        if (street != null) {
            return street;
        }
    }
}
return "not specified";
```

With Java 8
```java
Optional<User> user = Optional.ofNullable(getUser());
String result = user
  .map(User::getAddress)
  .map(Address::getStreet)
  .orElse("not specified");
```

In this example, we used the map() method to convert results of calling the getAdress() to the Optional&lt;Address&gt; and getStreet() to Optional&lt;String&gt;. If any of these methods returned null, the map() method would return an empty Optional.

Now imagine that our getters return Optional&lt;T&gt;. In this case, we should use the flatMap() method instead of the map():

```java
Optional<OptionalUser> optionalUser = Optional.ofNullable(getOptionalUser());
String result = optionalUser
  .flatMap(OptionalUser::getAddress)
  .flatMap(OptionalAddress::getStreet)
  .orElse("not specified");
```

#### Example 3

Another use case of Optional is changing NPE with another exception.

Before Java 8
```java
String value = null;
String result = "";
try {
    result = value.toUpperCase();
} catch (NullPointerException exception) {
    throw new CustomException();
}
```

With Java 8
```java
String value = null;
Optional<String> valueOpt = Optional.ofNullable(value);
String result = valueOpt.orElseThrow(CustomException::new).toUpperCase();
```