package com.gft.jvegaf.payroll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EmployeeControllerTest {

  @Autowired WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void all() {
    webTestClient.get()
                 .uri("/employees")
                 .exchange()
                 .expectStatus().isOk()
                 .expectHeader().valueEquals("Content-Type", "application/hal+json")
                 .expectBody()
                 .jsonPath("$.length()").isEqualTo(2);
  }

  @Test
  void one() {
    webTestClient.get()
                 .uri("/employees/1")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.name").isEqualTo("Bilbo Baggins");
  }

  @Test
  void newEmployee() {

    Employee empl = new Employee("pedro botero", "boss");

    webTestClient.post()
                 .uri("/employees")
                 .body(Mono.just(empl), Employee.class)
                 .exchange()
                 .expectStatus().isOk();
  }

  @Test
  void replaceEmployee() {
    Employee empl = new Employee("pepe botero", "god");

    webTestClient.put()
                 .uri("/employees/1")
                 .body(Mono.just(empl), Employee.class)
                 .exchange()
                 .expectStatus().isCreated();
  }

  @Test
  void deleteEmployee() {
    webTestClient.delete()
                 .uri("/employees/2")
                 .exchange()
                 .expectStatus().isOk();
  }
}