package com.example.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class GraphQLController {

    private List<Customer> db = List.of(new Customer(1, "John Doe"), new Customer(2, "Anna Doe"));
    
    @SchemaMapping(typeName = "Query", field = "customers")
    public Flux<Customer> customers() {
        return Flux.fromIterable(db);
    }

    @QueryMapping
    public Flux<Customer> customersByName(@Argument String name) {
        return Flux.fromIterable(db).filter(item -> item.name().equals(name));
    }

    @SchemaMapping(typeName = "Customer")
    public List<Order> orders(Customer customer) {
        return List.of(new Order(1, customer.id()), new Order(2, customer.id()), new Order(3, customer.id()));
    }
}
