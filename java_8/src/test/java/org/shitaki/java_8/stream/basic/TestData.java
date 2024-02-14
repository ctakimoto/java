package org.shitaki.java_8.stream.basic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.shitaki.java_8.stream.basic.Category.*;

public class TestData {

    public static final Product PENCILS = new Product(OFFICE, "Pencils", new BigDecimal("5.79"));
    public static final Product APPLES = new Product(FOOD, "Apples", new BigDecimal("1.29"));
    public static final Product PLATES = new Product(UTENSILS, "Plates", new BigDecimal("12.95"));
    public static final Product SPAGHETTI = new Product(FOOD, "Spaghetti", new BigDecimal("2.79"));
    public static final Product KNIVES = new Product(UTENSILS, "Knives", new BigDecimal("9.95"));
    public static final Product FORKS = new Product(UTENSILS, "Forks", new BigDecimal("14.95"));
    public static final Product DETERGENT = new Product(CLEANING, "Detergent", new BigDecimal("3.79"));
    public static final Product SCOURER = new Product(CLEANING, "Scourer", new BigDecimal("2.29"));
    public static final Product DISH_BRUSH = new Product(CLEANING, "Dish brush", new BigDecimal("3.49"));
    public static final Product NOTEBOOK = new Product(OFFICE, "Notebook", new BigDecimal("4.99"));
    public static final Product BALLPOINT_PENS = new Product(OFFICE, "Ballpoint pens", new BigDecimal("6.79"));

    private static final List<Product> PRODUCTS = List.of(PENCILS, APPLES, PLATES, SPAGHETTI, KNIVES, FORKS, DETERGENT);

    public static List<Product> getProducts() {
        return new ArrayList<>(PRODUCTS);
    }
}
