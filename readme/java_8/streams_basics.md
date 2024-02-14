# Java 8

## Streams

[Definition](#definition)

[Understanding Streams](#understanding-streams)

[Differences between Streams and Collections](#differences-between-streams-and-collections)

[Obtaining Streams](#obtaining-streams)

[Filtering and Transforming Streams](#filtering-and-transforming-streams)

[Search in Streams](#search-in-streams)

[Reducing and Collecting Streams](#reducing-and-collecting-streams)

### Definition

Its propose is to process a sequence of elements by executing different kinds of operations on the elements.

Example:

```java
products.stream()
        .filter(product -> product.getCategory() == Category.FOOD)
        .map(Product::getName)
        .foreach(System.out::println);    
```

### Understanding Streams

There are two kind of operations:
* intermediate (filter and map in the example)
* terminal (for each in the example)

Stream processing is Lazy: it doesn't produce elements if there's no terminal operation.

Example:

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Streams are lazy - without a terminal operation, no work is done
        // When you call intermediate operations on a stream, you're only building the pipeline;
        // no elements are flowing through it yet
        Stream<Product> stream = products.stream().map(product -> {
            System.out.println(product);
            return product;
        });

        // When you call a terminal operation, the stream will do the work
        stream.forEach(product -> {});
    }
```

### Differences between Streams and Collections

![stream_basics.png](images%2Fstream_basics.png)

![stream_basics2.png](images%2Fstream_basics2.png)

### Obtaining Streams

Most common way to get a Stream is from a Collection

Example:

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();
    
        // Get a stream from a collection
        Stream<Product> stream1 = products.stream();
    
        // Get a stream from an array
        String[] array = new String[]{"one", "two", "three"};
        Stream<String> stream2 = Arrays.stream(array);
    
        // Create a Stream from elements directly
        Stream<String> stream3 = Stream.of("one", "two", "three");
    
        // Create a Stream with zero or one elements with ofNullable()
        Stream<String> stream4 = Stream.ofNullable("four");
    
        // Create an empty Stream with Stream.empty()
        Stream<?> stream5 = Stream.empty();
    
        // Returns an infinite stream of random numbers between 1 (inclusive) and 7 (exclusive)
        IntStream dice = new Random().ints(1, 7);
    
        // There are more methods in the Java standard library that create streams, for example BufferedReader.lines()
        try (BufferedReader in = new BufferedReader(new FileReader("README.md", StandardCharsets.UTF_8))) {
            in.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

### Filtering and Transforming Streams

Example:
```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();
    
        // The filter() intermediate operation filters elements from the stream
        products.stream()
                .filter(product -> product.getCategory() == Category.FOOD)
                .forEach(System.out::println);
    
        // The map() intermediate operation does a one-to-one transformation on each element
        products.stream()
                .map(product -> String.format("The price of %s is $ %.2f", product.getName(), product.getPrice()))
                .forEach(System.out::println);
    
        // The flatMap() intermediate operation does a one-to-N transformation on each element
        // The function passed to flatMap() must return a Stream that contains the output elements
        // The streams returned by the calls to the function are "flatted" into a single output stream
        Pattern spaces = Pattern.compile("\\s+");
        products.stream()
                .flatMap(product -> spaces.splitAsStream(product.getName()))
                .forEach(System.out::println);
    }
```

### Search in Streams

Find a particular element:
* filter()
* findFirst()
* findAny()

Check if element exist:
* anyMatch()
* allMatch()
* noneMatch()

Example:
```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();
    
        // findFirst() will return an Optional with the first element in the stream, or an empty Optional
        // This is often used together with filter() to search for an element on arbitrary criteria
        Optional<Product> opt = products.stream()
                .filter(product -> product.getCategory() == Category.OFFICE)
                .findFirst();
        opt.ifPresent(System.out::println);
    
        // If you only want to check if the stream contains an element that matches your search criteria,
        // then you can use anyMatch(), which will return a boolean result
        // Unlike findFirst() and findAny(), you don't have to filter first - anyMatch() takes a Predicate
        boolean foundOfficeProduct = products.stream()
                .anyMatch(product -> product.getCategory() == Category.OFFICE);
        System.out.println("Does the list contain at least one office product? " + foundOfficeProduct);
    
        // To check if all elements of the stream match specific criteria, use allMatch()
        BigDecimal priceLimit = new BigDecimal("10.00");
        boolean allProductsAreCheap = products.stream()
                .allMatch(product -> product.getPrice().compareTo(priceLimit) < 0);
        System.out.println("Are all products cheap? " + allProductsAreCheap);
    
        // noneMatch() returns the opposite of anyMatch()
        boolean allProductsAreExpensive = products.stream()
                .noneMatch(product -> product.getPrice().compareTo(priceLimit) < 0);
        System.out.println("Are all products expensive? " + allProductsAreExpensive);
    }
```

### Reducing and Collecting Streams

Example:
```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // This is not a good way to get the elements of a stream into a list
//        List<String> foodProductNames = new ArrayList<>();
//        products.stream()
//                .filter(product -> product.getCategory() == Category.FOOD)
//                .map(Product::getName)
//                .forEach(foodProductNames::add);

        // Using collect() you can collect the elements of a stream into a collection
        // Class Collectors contains factory methods for collectors that create different kinds of collections
        List<String> foodProductNames = products.stream()
                .filter(product -> product.getCategory() == Category.FOOD)
                .map(Product::getName)
                .collect(Collectors.toList());

        System.out.println(foodProductNames);

        // Collectors.joining(...) returns a collector to reduce stream elements into a string
        String categories = products.stream()
                .map(Product::getCategory)
                .distinct()
                .map(Category::name)
                .collect(Collectors.joining("; "));

        System.out.println(categories);
    }
```
