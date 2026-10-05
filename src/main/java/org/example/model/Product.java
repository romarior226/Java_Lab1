package org.example.model;

public record Product(long id, String name, ProductCategory category, double price) {

    @Override
    public String toString() {
        return "Product{id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + "}";
    }
}
