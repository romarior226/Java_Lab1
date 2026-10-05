package org.example;

import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Product;
import org.example.model.ProductCategory;
import org.example.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        OrderService service = new OrderService();

        Customer ivan = new Customer(1, "Ivan Petrenko", "ivan@example.com");
        Customer olena = new Customer(2, "Olena Kovalenko", "olena@example.com");
        Customer maksym = new Customer(3, "Maksym Shevchenko", "maksym@example.com");

        Product laptop = new Product(101, "Laptop", ProductCategory.ELECTRONICS, 1200.00);
        Product phone = new Product(102, "Smartphone", ProductCategory.ELECTRONICS, 800.50);
        Product jacket = new Product(103, "Winter jacket", ProductCategory.CLOTHING, 150.00);
        Product book = new Product(104, "Java book", ProductCategory.BOOKS, 45.99);
        Product lamp = new Product(105, "Desk lamp", ProductCategory.HOME, 29.90);
        Product giftCard = new Product(106, "Gift card", ProductCategory.OTHER, 50.00);

        Order order1 = new Order(1, ivan, Arrays.asList(laptop, book), OrderStatus.NEW);
        Order order2 = new Order(2, olena, Arrays.asList(jacket, lamp), OrderStatus.PROCESSING);
        Order order3 = new Order(3, ivan, List.of(phone), OrderStatus.SHIPPED);
        Order order4 = new Order(4, maksym, Arrays.asList(book, giftCard, lamp), OrderStatus.DELIVERED);
        Order order5 = new Order(5, olena, Arrays.asList(laptop, phone, jacket), OrderStatus.CANCELLED);
        Order order6 = new Order(6, ivan, List.of(lamp), OrderStatus.NEW);

        System.out.println("    1. addOrder    ");
        service.addOrder(order1);
        service.addOrder(order2);
        service.addOrder(order3);
        service.addOrder(order4);
        service.addOrder(order5);
        service.addOrder(order6);
        System.out.println("Added 6 orders");
        System.out.println("Adding order with duplicate id 1:");
        service.addOrder(new Order(1, maksym, List.of(giftCard), OrderStatus.NEW));

        System.out.println();
        System.out.println("    2. findOrderById    ");
        System.out.println("Order with id 4: " + service.findOrderById(4));
        System.out.println("Order with id 999: " + service.findOrderById(999));

        System.out.println();
        System.out.println("    3. findOrdersByCustomer    ");
        System.out.println("Customer Ivan (id = 1):");
        printOrders(service.findOrdersByCustomer(1));
        System.out.println("Customer Olena (id = 2):");
        printOrders(service.findOrdersByCustomer(2));
        System.out.println("Customer Maksym (id = 3):");
        printOrders(service.findOrdersByCustomer(3));
        System.out.println("Customer with id = 42:");
        printOrders(service.findOrdersByCustomer(42));

        System.out.println();
        System.out.println("    4. findOrdersByStatus    ");
        OrderStatus[] statuses = OrderStatus.values();
        for (OrderStatus status : statuses) {
            System.out.println("Status " + status + ":");
            printOrders(service.findOrdersByStatus(status));
        }

        System.out.println();
        System.out.println("    5. findOrdersContainingProduct    ");
        System.out.println("Desk lamp (id = 105):");
        printOrders(service.findOrdersContainingProduct(105));
        System.out.println("Laptop (id = 101):");
        printOrders(service.findOrdersContainingProduct(101));
        System.out.println("Product with id = 999:");
        printOrders(service.findOrdersContainingProduct(999));

        System.out.println();
        System.out.println("    6. calculateOrderTotal    ");
        for (long id = 1; id <= 6; id++) {
            System.out.println("Order " + id + " total: " + service.calculateOrderTotal(id));
        }
        System.out.println("Order 999 total: " + service.calculateOrderTotal(999));

        System.out.println();
        System.out.println("    7. findCustomersWithOrders    ");
        Set<Customer> customers = service.findCustomersWithOrders();
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        System.out.println();
        System.out.println("    8. sortOrdersByTotalPrice    ");
        List<Order> sorted = service.sortOrdersByTotalPrice();
        for (Order order : sorted) {
            System.out.println(order + ", total = " + service.calculateOrderTotal(order.id()));
        }

        System.out.println();
        System.out.println("    9. countOrdersByStatus    ");
        printCounts(service.countOrdersByStatus());

        System.out.println();
        System.out.println("    10. removeOrder    ");
        System.out.println("Removing orders 5 and 2");
        service.removeOrder(5);
        service.removeOrder(2);
        System.out.println("Removing order 5 again:");
        service.removeOrder(5);
        System.out.println("Orders after removal:");
        printOrders(service.sortOrdersByTotalPrice());
        System.out.println("Counts after removal:");
        printCounts(service.countOrdersByStatus());
        System.out.println("Customers after removal:");
        Set<Customer> customersAfter = service.findCustomersWithOrders();
        for (Customer customer : customersAfter) {
            System.out.println(customer);
        }
    }

    private static void printOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders");
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private static void printCounts(Map<OrderStatus, Long> counts) {
        for (OrderStatus status : OrderStatus.values()) {
            Long count = counts.get(status);
            if (count == null) {
                count = 0L;
            }
            System.out.println(status + ": " + count);
        }
    }
}
