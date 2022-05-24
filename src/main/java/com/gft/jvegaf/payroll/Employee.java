package com.gft.jvegaf.payroll;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
class Employee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long   id;
  private String name;
  private String role;
  private String firstName;
  private String lastName;

  public Employee() {}

  public Employee(String name, String role) {
    this.name      = name;
    this.role      = role;
    this.firstName = name.split(" ")[0];
    this.lastName  = name.split(" ")[1];
  }

  public Employee(String firstName, String lastName, String role) {
    this.firstName = firstName;
    this.lastName  = lastName;
    this.role      = role;
    this.name      = firstName + " " + lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;
    Employee employee = (Employee) o;
    return getId().equals(employee.getId()) &&
           getName().equals(employee.getName()) &&
           getRole().equals(employee.getRole()) &&
           getFirstName().equals(employee.getFirstName()) &&
           getLastName().equals(employee.getLastName());
  }

  @Override public int hashCode() {
    return Objects.hash(getId(), getName(), getRole(), getFirstName(), getLastName());
  }

  @Override public String toString() {
    return "Employee{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", role='" + role + '\'' +
           ", firstName='" + firstName + '\'' +
           ", lastName='" + lastName + '\'' +
           '}';
  }
}