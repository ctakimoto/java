# Java 8

## Stream in Depth

[Generating and Building Streams](#generating-and-building-streams)

[Reducing Streams in Detail](#reducing-streams-in-detail)

[Collection Streams in Detail](#collection-streams-in-detail)

[Working with Collectors](#working-with-collectors)

[Grouping Stream Elements](#grouping-stream-elements)

[Partitioning Stream Elements](#partitioning-stream-elements)

[Parallel Stream](#parallel-stream)

[Specialized Streams](#specialized-streams)

### Generating and Building Streams

#### Generate

Example:
```java
        // generate() takes a Supplier and creates an infinite stream from values returned by the supplier
        Stream<UUID> uuids = Stream.generate(UUID::randomUUID);
        uuids.limit(10).forEach(System.out::println);
```

#### Iterate

Example 1:
```java
        // The first version of iterate() takes a seed value and an unary operator and generates an infinite stream of values
        // by first returning the seed, and then applying the operator to each previous element, so the stream
        // will contain: seed, f(seed), f(f(seed)), etc.
        Stream<BigInteger> powersOfTwo = Stream.iterate(BigInteger.ONE, n -> n.multiply(BigInteger.TWO));
        powersOfTwo.limit(20).forEach(System.out::println);
```

Example 2:
```java
        // The second version of iterate() takes an extra parameter, which is a predicate that indicates if there is a next element
        // When the predicate returns false, the stream ends
        // The three parameters are just like the three parts of a for-loop
        Stream<Character> alphabet = Stream.iterate('A', letter -> letter <= 'Z', letter -> (char) (letter + 1));
        alphabet.forEach(System.out::print);
```

#### Builder

Example:
```java
        // You can construct a stream by creating a builder and adding elements to the builder
        Stream.Builder<String> builder = Stream.builder();
        builder.add("one");
        builder.add("two");
        builder.add("three");
        Stream<String> stream = builder.build();
        stream.forEach(System.out::println);
```

### Reducing Streams in Detail

Example 1:
```java
        // The first version of reduce() takes a BinaryOperator to accumulate elements into a final result
        // It returns an Optional; if the stream is empty, the result is an empty Optional
        Optional<BigDecimal> opt = products.stream()
                .map(Product::getPrice)
                .reduce((result, element) -> result.add(element)); // Can also be written with a method reference: BigDecimal::add

        opt.ifPresentOrElse(
                total -> System.out.printf("The total value of all products is: $ %.2f%n", total),
                () -> System.out.println("There are no products"));
```

Example 2:
```java
        // The second version of reduce() takes a starting value and a BinaryOperator
        // It returns a value instead of an Optional; if the stream is empty, the result will be the starting value
        BigDecimal total = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("The total value of all products is: $ %.2f%n", total);
```

Example 3:
```java
        // The third version of reduce() is the most general
        // The type of its result value may be different than the type of the elements in the stream
        // The third parameter is a combiner function to combine intermediate results; this is useful for example for a parallel
        // stream, where different threads compute intermediate results that have to be combined into a final result
        BigDecimal total2 = products.stream()
                .reduce(BigDecimal.ZERO, (result, product) -> result.add(product.getPrice()), BigDecimal::add);
        System.out.printf("The total value of all products is: $ %.2f%n", total2);
```

### Collection Streams in Detail

Collection is a mutable reduction. A collection operation reduces a stream into a mutable result container.

![stream_in_depth.png](images%2Fstream_in_depth.png)

#### Collection and Reduction

![stream_in_depth2.png](images%2Fstream_in_depth2.png)

![stream_in_depth3.png](images%2Fstream_in_depth3.png)

Example:
```java
        // You can reduce stream elements into an ArrayList with reduce(), but this is inefficient because reduce()
        // is designed for the result container to be immutable; so you need to create intermediate lists and
        // copy elements between them.
        List<String> productNames1 = products.stream().reduce(
                new ArrayList<>(),
                (list, product) -> {
                    ArrayList<String> newList = new ArrayList<>(list);
                    newList.add(product.getName());
                    return newList;
                },
                (list1, list2) -> {
                    ArrayList<String> newList = new ArrayList<>(list1);
                    newList.addAll(list2);
                    return newList;
                });

        // Collection is mutable reduction. This is much more efficient if you have a mutable result container
        // such as an ArrayList, avoiding most of the copying that needs to be done when using reduce().
        List<String> productNames2 = products.stream().collect(
                ArrayList::new,
                (list, product) -> list.add(product.getName()),
                ArrayList::addAll);
```

### Working with Collectors

![stream_in_depth4.png](images%2Fstream_in_depth4.png)

Avaiable implementation of Collectors:

![stream_in_depth5.png](images%2Fstream_in_depth5.png)

Example:
```java
        // Using Collectors.toMap() to compute the total price of products per category.
        Map<Category, BigDecimal> totalPerCategory = products.stream()
                .collect(Collectors.toMap(
                        Product::getCategory,   // Key mapper function
                        Product::getPrice,      // Value mapper function
                        BigDecimal::add));      // Merge function
        System.out.println(totalPerCategory);
```

### Grouping Stream Elements

![stream_in_depth6.png](images%2Fstream_in_depth6.png)

Example:
```java
        // Group products by category. The classifier function that you pass to groupingBy() determines how to split the products into groups.
        Map<Category, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        // Suppose that we want to get a map of product names per category (instead of products).
        // Mapping products to product names with map(...) won't work, because the result of the map(...) operation is a stream of strings.
        // The information about the categories will have been thrown away, so you can't group on categories anymore in the collector.
//        Map<Category, List<String>> productNamesByCategory = products.stream()
//                .map(Product::getName)
//                .collect(Collectors.groupingBy(...));

        // Create a Map of product names grouped by category.
        // We use a downstream collector, created by Collectors.mapping(...), to map grouped products to product names.
        // Note that Collectors.mapping(...) takes a mapping function and another downstream collector to determine how to collect its result.
        Map<Category, List<String>> productNamesByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toList())));

        // Print the product names for each category.
        productNamesByCategory.forEach((category, names) -> {
            System.out.println(category);
            names.forEach(name -> System.out.println("- " + name));
        });

        // Challenge: This line calculates the total price of products per category, just like in the previous example (AdvancedStreamsExample04),
        // but by using groupingBy() and multiple downstream collectors. Can you explain exactly how this works?
        // Use the API documentation of class Collectors to learn about the factory methods.
        Map<Category, BigDecimal> totalPerCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
```

### Partitioning Stream Elements

Is a special case of grouping.

Example: 
```java
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        // Partitioning is a special case of grouping, where the classifier function is a Predicate
        // and the stream elements are divided into two groups.
        // The result is a map with two entries, with boolean values 'true' and 'false' as keys.
        Map<Boolean, List<Product>> partitionedProducts = products.stream()
                .collect(Collectors.partitioningBy(product -> product.getPrice().compareTo(priceLimit) < 0));

        System.out.println("Cheap products: ");
        partitionedProducts.get(true).forEach(System.out::println);

        System.out.println("Expensive products: ");
        partitionedProducts.get(false).forEach(System.out::println);
```

### Parallel Stream

![stream_in_depth7.png](images%2Fstream_in_depth7.png)

![stream_in_depth8.png](images%2Fstream_in_depth8.png)

Not a magical solution. Need to avoid get unexpected results.

![stream_in_depth9.png](images%2Fstream_in_depth9.png)

When grouping a parallel stream is better to use groupingByConcurrent.

![stream_in_depth10.png](images%2Fstream_in_depth10.png)

### Specialized Streams

Specialized Streams for primitive types.

![stream_in_depth11.png](images%2Fstream_in_depth11.png)

Example:
```java
        // DoubleStream is a stream that contains primitive double values.
        // Compared to Stream<Double>, this avoids boxing and unboxing primitive double values into and out of Double wrapper objects.
        DoubleStream prices = products.stream()
                .mapToDouble(product -> product.getPrice().doubleValue());

        // There are some methods that are not available on regular streams, such as sum().
        double total = prices.sum();
        System.out.printf("The sum of all product prices is $ %.2f%n", total);

        // The method summaryStatistics() provides statistics about the elements in the stream.
        // See the API documentation of the specialized streams for more methods.
        DoubleSummaryStatistics statistics = products.stream()
                .mapToDouble(product -> product.getPrice().doubleValue())
                .summaryStatistics();

        System.out.printf("Count  : %d%n", statistics.getCount());
        System.out.printf("Sum    : $ %.2f%n", statistics.getSum());
        System.out.printf("Minimum: $ %.2f%n", statistics.getMin());
        System.out.printf("Maximum: $ %.2f%n", statistics.getMax());
        System.out.printf("Average: $ %.2f%n", statistics.getAverage());
```
