package com.gft.jvegaf.payroll;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
  private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long   Id;
  private                                                         String description;
  private                                                         Status status;

  public Order() {}

  public Order(String description, Status status) {
    this.description = description;
    this.status      = status;
  }

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Order)) return false;
    Order order = (Order) o;
    return Objects.equals(getId(), order.getId()) &&
           Objects.equals(getDescription(),
                          order.getDescription()) &&
           getStatus() == order.getStatus();
  }

  @Override public int hashCode() {
    return Objects.hash(getId(), getDescription(), getStatus());
  }

  @Override public String toString() {
    return "Order{" +
           "Id=" + Id +
           ", description='" + description + '\'' +
           ", status=" + status +
           '}';
  }
}
