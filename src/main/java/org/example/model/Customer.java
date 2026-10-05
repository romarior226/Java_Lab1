package org.example.model;

public record Customer(long id, String name, String email) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer other)) {
            return false;
        }
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", name=" + name + ", email=" + email + "}";
    }
}
