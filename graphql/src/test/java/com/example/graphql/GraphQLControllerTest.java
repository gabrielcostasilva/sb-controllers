package com.example.graphql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(GraphQLController.class)
public class GraphQLControllerTest {

    @Autowired
    private GraphQlTester tester;
    
    @Test
    public void findAllCustomers() {
        String document = 
        """
            query {
                customers {
                    id
                    name
                    orders {
                        id
                    }
                }
            }
        """;

        tester
            .document(document)
            .execute()
            .path("customers")
            .entityList(Customer.class)
            .hasSize(2);
    }

    @Test
    public void findCustomersByName() {

        String document =
        """
            query findCustomersByName($name: String) {
                customersByName(name: $name) {
                    id
                    name
                    orders {
                        id
                    }
                }
            }
        """;

        tester
            .document(document)
            .variable("name", "John Doe")
            .execute()
            .path("customersByName")
            .entityList(Customer.class)
            .hasSize(1);
    }

}
