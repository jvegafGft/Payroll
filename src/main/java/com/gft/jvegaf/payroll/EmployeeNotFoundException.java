package com.gft.jvegaf.payroll;


public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(Long id) {
      super("Can not found employee " + id);
  }
}
