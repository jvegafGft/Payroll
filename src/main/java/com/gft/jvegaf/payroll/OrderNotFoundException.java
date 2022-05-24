package com.gft.jvegaf.payroll;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(Long id) {
    super("Can not found order " + id);
  }
}
