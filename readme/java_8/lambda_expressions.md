# Java8

## Lambda Expressions

[Definition](#definition)

[Syntax of Lambda Expressions](#syntax-of-lambda-expressions)

[Capturing Variables in Lambda Expressions](#capturing-variables-in-lambda-expressions)

[Interaction of Lambda Expressions with Enclosing Code](#interaction-of-lambda-expressions-with-enclosing-code)

[Working with Exceptions in Lambda Expressions](#working-with-exceptions-in-lambda-expressions)

[Method References](#method-references)

### Definition

A lambda expression is an anonymous method.

Functional Programming: passing code as data.

Lambda expression always implements Functional Interface.

### Syntax of Lambda Expressions

Regular method syntax:
* access specifier
* return type
* name
* parameter list
* body
```java
public int compare(Product p1, Product p2) {
    return p1.getPrice.compareTo(p2.getPrice());
}
```

Lambda Expression syntax:
* parameter list
  * No parameter: empty parentheses
  * Single parameter: parentheses are optional
  * Multiple parameters: types are optional
* arrow
* body (block {} or single expression)

```java
// no parameter
Runnable runnable = () -> System.out.println("Hello World");

// single paremeter
FileFilter filter = file -> file.isHidden();

// multiple parameters
(p1, p2) -> p1.getPrice().compareTo(p2.getPrice())
```

### Capturing Variables in Lambda Expressions

Captured local variables must be final or effectively final (variables can not be modified inside or outside lambda expressions)

Example 1:
```java
    interface ProductFilter {
        boolean accept(Product product);
    }

    // Print the products that are accepted by the filter.
    static void printProducts(List<Product> products, ProductFilter filter) {
        for (Product product : products) {
            if (filter.accept(product)) {
                System.out.println(product);
            }
        }
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        // This lambda expression captures the local variable priceLimit.
        // The variable must be effectively final; if it is not, an error will appear in the lambda expression.
        ProductFilter filter = product -> product.getPrice().compareTo(priceLimit) < 0;

        // Reassigning the variable, even after the lambda expression, makes it not effectively final.
        // priceLimit = new BigDecimal("6.00");

        printProducts(products, filter);
    }
```

Example 2:

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        int numberOfCheapProducts = 0;

        // Check if there are cheap products.
        for (Product product : products) {
            if (product.getPrice().compareTo(priceLimit) < 0) {
                numberOfCheapProducts++;
            }
        }

        // Because local variables are effectively final, you cannot modify them inside a lambda expression.
//        products.forEach(product -> {
//            if (product.getPrice().compareTo(priceLimit) < 0) {
//                numberOfCheapProducts++; // Error: Variable must be effectively final
//            }
//        });

        System.out.println("There are " + numberOfCheapProducts + " cheap products");
    }
```

Avoid side effects in lambda expressions like modifying the state of objects that comes from outside the lambda expression

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        List<Product> cheapProducts = new ArrayList<>();

        // BAD PRACTICE! Modifying cheapProducts inside the body of the lambda expression.
        // In general, avoid side effects such as modifying objects from captured variables in lambda expressions.
        products.forEach(product -> {
            if (product.getPrice().compareTo(priceLimit) < 0) {
                cheapProducts.add(product);
            }
        });

        // Print the cheap products.
        cheapProducts.forEach(product -> System.out.println(product));
    }
```

### Interaction of Lambda Expressions with Enclosing Code

**this** and **super** have the same meaning as in surrounding code

### Working with Exceptions in Lambda Expressions

Dealing with **exceptoins** can be inconvenient.

Something a simple for loop is a better solution

```java
    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        try (FileWriter writer = new FileWriter("products.txt")) {
//            for (Product product : products) {
//                writer.write(product.toString() + "\n");
//            }

            // According to forEach(), the lambda expression implements interface Consumer. The accept() method
            // of this interface does not declare any checked exceptions, so the lambda expression is not allowed
            // to throw any checked exceptions. We are forced to handle the IOException inside the lambda expression.
            products.forEach(product -> {
                try {
                    writer.write(product.toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException | RuntimeException e) {
            System.err.println("An exception occurred: " + e.getMessage());
        }
    }
```

### Method References

Use a [method reference](method_references.md) instead of lambda expression

Example:

```java
    interface ProductFactory {
        Product create(Category category, String name, BigDecimal price);
    }

    static boolean isExpensive(Product product) {
        return product.getPrice().compareTo(new BigDecimal("5.00")) >= 0;
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Instead of a lambda expression, you can use a method reference to refer to a method
        // that implements the relevant functional interface.
//        products.forEach(product -> System.out.println(product));
        products.forEach(System.out::println);

        // A method reference can refer to a static method, an instance method or a constructor.

        // Method reference to a static method.
        products.removeIf(LambdasExample07::isExpensive);

        // There are two types of method references that refer to an instance method:
        // - Product::getName refers to an instance method of class Product, but not for any particular Product object
        //   map() calls the getName() method on the Product object it receives
        // - System.out::println refers to an instance method of class PrintStream, for a particular PrintStream object;
        //   the one that the variable System.out refers to. forEach() calls the println() method on that PrintStream
        products.stream().map(Product::getName).forEach(System.out::println);

        // A method reference with 'new' after the double colon refers to a constructor.
        ProductFactory factory = Product::new;
        Product blueberries = factory.create(Category.FOOD, "Blueberries", new BigDecimal("6.95"));
        System.out.println(blueberries);
    }
```