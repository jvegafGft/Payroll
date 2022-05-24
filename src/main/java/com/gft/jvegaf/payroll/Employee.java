package com.gft.jvegaf.payroll;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
class Employee {
  private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long   id;
  private                                                         String name;
  private                                                         String role;

  public Employee() {}

  public Employee(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override public String toString() {
    return "Employee{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", role='" + role + '\'' +
           '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;
    Employee employee = (Employee) o;
    return getId().equals(employee.getId()) &&
           getName().equals(employee.getName()) &&
           getRole().equals(employee.getRole());
  }

  @Override public int hashCode() {
    return Objects.hash(getId(), getName(), getRole());
  }
}