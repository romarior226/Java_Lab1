package org.example.service;

import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Product;

import java.util.*;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        if (findOrderById(order.id()) != null) {
            System.out.println("Order with id " + order.id() + " already exists");
            return;
        }
        orders.add(order);
    }

    public void removeOrder(long orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).id() == orderId) {
                orders.remove(i);
                return;
            }
        }
        System.out.println("Order with id " + orderId + " not found");
    }

    public Order findOrderById(long orderId) {
        for (Order order : orders) {
            if (order.id() == orderId) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findOrdersByCustomer(long customerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.customer().id() == customerId) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Order> findOrdersByStatus(OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.status() == status) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Order> findOrdersContainingProduct(long productId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            for (Product product : order.products()) {
                if (product.id() == productId) {
                    result.add(order);
                    break;
                }
            }
        }
        return result;
    }

    public double calculateOrderTotal(long orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("Order with id " + orderId + " not found");
            return 0;
        }
        double total = 0;
        for (Product product : order.products()) {
            total += product.price();
        }
        return total;
    }

    public Set<Customer> findCustomersWithOrders() {
        Set<Customer> result = new HashSet<>();
        for (Order order : orders) {
            result.add(order.customer());
        }
        return result;
    }

    public List<Order> sortOrdersByTotalPrice() {
        List<Order> sorted = new ArrayList<>(orders);
        sorted.sort((a, b) -> Double.compare(calculateOrderTotal(a.id()), calculateOrderTotal(b.id())));
        return sorted;
    }

    public Map<OrderStatus, Long> countOrdersByStatus() {
        Map<OrderStatus, Long> result = new HashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            result.put(status, (long) findOrdersByStatus(status).size());
        }
        return result;
    }
}
