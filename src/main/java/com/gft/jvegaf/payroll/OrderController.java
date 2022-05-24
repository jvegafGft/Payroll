package com.gft.jvegaf.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
public class OrderController {
  private final OrderRepository repository;
  private final OrderModelAssembler assembler;

  public OrderController(OrderRepository repository, OrderModelAssembler assembler) {
    this.repository = repository;
    this.assembler  = assembler;
  }

  @GetMapping("/orders")
  CollectionModel<EntityModel<Order>> all() {

    List<EntityModel<Order>> orders = repository.findAll().stream()
                                                      .map(assembler::toModel)
                                                      .collect(Collectors.toList());

    return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
  }

  @PostMapping("/orders")
  Order newOrder(@RequestBody Order newOrder) {
    return repository.save(newOrder);
  }

  @GetMapping("/orders/{id}")
  EntityModel<Order> one(@PathVariable Long id) throws OrderNotFoundException {
    Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

    return assembler.toModel(order);
  }

  @PutMapping("/orders/{id}")
  ResponseEntity<?> replaceOrder(@PathVariable Long id, @RequestBody Order newOrder) {
    Order updatedOrder = repository.findById(id) //
                                         .map(order -> {
                                           order.setStatus(newOrder.getStatus());
                                           order.setDescription(newOrder.getDescription());
                                           return repository.save(order);
                                         }) //
                                         .orElseGet(() -> {
                                           newOrder.setId(id);
                                           return repository.save(newOrder);
                                         });

    EntityModel<Order> entityModel = assembler.toModel(updatedOrder);

    return ResponseEntity //
                          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                          .body(entityModel);
  }


  @PutMapping("/orders/{id}/complete")
  ResponseEntity<?> complete(@PathVariable Long id) {

    Order order = repository.findById(id) //
                                 .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.COMPLETED);
      return ResponseEntity.ok(assembler.toModel(repository.save(order)));
    }

    return ResponseEntity //
                          .status(HttpStatus.METHOD_NOT_ALLOWED) //
                          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                          .body(Problem.create() //
                                       .withTitle("Method not allowed") //
                                       .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
  }

  @DeleteMapping("/orders/{id}")
  ResponseEntity<?> deleteOrder(@PathVariable Long id) {

    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/orders/{id}/cancel")
  ResponseEntity<?> cancel(@PathVariable Long id) {

    Order order = repository.findById(id) //
                                 .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.CANCELLED);
      return ResponseEntity.ok(assembler.toModel(repository.save(order)));
    }

    return ResponseEntity //
                          .status(HttpStatus.METHOD_NOT_ALLOWED) //
                          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                          .body(Problem.create() //
                                       .withTitle("Method not allowed") //
                                       .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
  }
}
