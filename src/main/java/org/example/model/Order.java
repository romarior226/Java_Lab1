package org.example.model;

import java.util.List;

public record Order(long id, Customer customer, List<Product> products, OrderStatus status) {

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer=" + customer.name()
                + ", products=" + products.size() + ", status=" + status + "}";
    }
}
