# Java 8

## Functional Interfaces

[Definition](#definition)

[Common Standard Functional Interfaces](#common-standard-functional-interfaces)

[Practical Examples of Standard Functional Interfaces](#practical-examples-of-standard-functional-interfaces)

[Functional Composition](#functional-composition)

[Specialized Standard Functional Interfaces](#specialized-standard-functional-interfaces)

### Definition

Functional Interface is an interface that contains only one abstract method. However, they can include any number of default and static methods.

Examples:
* Comparator
* Runnable
* Consumer
* Supplier

Standard functional interfaces in package: **java.util.function**

@FunctionalInterface Annotation: it's not required. It express that an interface is intendend to be used as a functional interface.

### Common Standard Functional Interfaces

* Function
* Consumer
* Suplier
* Predicate

![functional_interfaces.png](images%2Ffunctional_interfaces.png)

### Practical Examples of Standard Functional Interfaces

#### Find if an item is in a list 

````java
// Go through a list of products, and return the first product for which the predicate returns true.
static Optional<Product> findProduct(List<Product> products, Predicate<Product> predicate) {
    for (Product product : products) {
        if (predicate.test(product)) {
            return Optional.of(product);
        }
    }
    return Optional.empty();
}

public static void main(String[] args) {
    List<Product> products = ExampleData.getProducts();

    String name = "Spaghetti";

    // A combination of functional interfaces implemented by lambda expressions and method references is used here:
    // - findProduct() takes a Predicate<Product> to find the product with the specified name
    // - map() takes a Function<Product, BigDecimal> to get the price of the product
    // - ifPresentOrElse() takes a Consumer<Product> and a Runnable and calls one of them, depending on whether
    //      the Optional contains a value or is empty

    findProduct(products, product -> product.getName().equals(name))
            .map(Product::getPrice)
            .ifPresentOrElse(
                    price -> System.out.printf("The price of %s is $ %.2f%n", name, price),
                    () -> System.out.printf("%s is not available%n", name));
}
````

#### Transform a List of Object into a Map

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        Map<Category, List<Product>> productsByCategory = new HashMap<>();

        // Without functional interfaces and lambda expressions.
        for (Product product : products) {
            Category category = product.getCategory();

            // Check if the map already has a List for this category; if not, add one.
            if (!productsByCategory.containsKey(category)) {
                productsByCategory.put(category, new ArrayList<>());
            }

            // Add the product to the appropriate list in the map.
            productsByCategory.get(category).add(product);
        }

        // With Map.computeIfAbsent()
        for (Product product : products) {
            Category category = product.getCategory();

            // computeIfAbsent() gets the existing List for the category, or calls the given
            // Function<Category, List<Product>> to create the List if there is no entry in the Map for the category.
            productsByCategory.computeIfAbsent(category, c -> new ArrayList<>()).add(product);
        }

        // Map.forEach() takes a BiConsumer (a Consumer which takes two parameters); the key and value of each entry.
        productsByCategory.forEach((category, ps) -> {
            System.out.println("Category: " + category);
            ps.forEach(product -> System.out.println("- " + product.getName()));
        });
    }
```

### Functional Composition

It's combining Function into new Function

![functional_interfaces2.png](images%2Ffunctional_interfaces2.png)

#### Example 1:

```java
    // Go through a list of products, and return the first product for which the predicate returns true.
    static Optional<Product> findProduct(List<Product> products, Predicate<Product> predicate) {
        for (Product product : products) {
            if (predicate.test(product)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        String name = "Spaghetti";

        Function<Product, BigDecimal> productToPrice = Product::getPrice;
        Function<BigDecimal, String> priceToMessage = price -> String.format("The price of %s is $ %.2f%n", name, price);

        // Compose a new function out of the two functions above by using andThen(...)
        Function<Product, String> productToMessage = productToPrice.andThen(priceToMessage);

        // Alternative: use compose(...), which is the same as andThen(...) except that the order of the functions is reversed
//        Function<Product, String> productToMessage = priceToMessage.compose(productToPrice);

        findProduct(products, product -> product.getName().equals(name))
                .map(productToMessage)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.printf("%s is not available%n", name));
    }
```

#### Example 2:

```java
    // Go through a list of products, and return the first product for which the predicate returns true.
    static Optional<Product> findProduct(List<Product> products, Predicate<Product> predicate) {
        for (Product product : products) {
            if (predicate.test(product)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("2.00");

        // Two simple predicates that can be combined using the functional composition methods in interface Predicate.
        Predicate<Product> isFood = product -> product.getCategory() == Category.FOOD;
        Predicate<Product> isCheap = product -> product.getPrice().compareTo(priceLimit) < 0;

        findProduct(products, isFood.and(isCheap)) // Combining the predicates with and(...)
                .map(product -> String.format("%s for $ %.2f", product.getName(), product.getPrice()))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("There are no cheap food products"));
    }
```

### Specialized Standard Functional Interfaces

Issue
![functional_interfaces3.png](images%2Ffunctional_interfaces3.png)

Types of Specialized Interfaces
![functional_interfaces4.png](images%2Ffunctional_interfaces4.png)