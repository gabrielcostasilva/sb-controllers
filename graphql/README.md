# GraphQL Example
This project is based on Josh Long's excellent intro to [Spring support for GraphQL](https://www.youtube.com/watch?v=eVqmB2hsIVk).

GraphQL is a [query language](https://graphql.org). It enables one to describe and map data from several sources as one single request. It is an alternative to REST-based APIs.

[Spring support for GraphQL](https://docs.spring.io/spring-graphql/docs/1.0.0-M5/reference/html/) works over HTTP - both Spring MVC and WebFlux - and WebSocket.

## Project Overview
As of February 2022, there is no stable support in Spring for GraphQL available. Therefore, one we use Spring `2.7.0-M1`. This project also relies on `spring-boot-starter-graphql` and `spring-boot-starter-webflux` dependencies.

`Customer` and `Order` are Java `record`s that represent data entities whereas `GraphQLController` handles requests through `@Controller` annotation. 

In addition, a schema describing the data structure is necessary. By default, schemas are placed in `resources/graphql/` folder, with the `.graphqls` extension. The code below fully shows our schema. It is beyound our goal to explain schema details, but feel free to [learn more about it](https://graphql.org/learn/).

```graphql
type Query {
    customers: [Customer]
    customersByName(name: String): [Customer]
}

type Customer {
    id: ID
    name: String
    orders: [Order]
}

type Order {
    id: ID
    customerId: ID
}
```
Basically, our schema enables one single operation type: querying data. The schema sets two queries. Whereas `customer` has no argument, `customersByName` has one `String` argument. Both queries possibly return a list of `Customer`. The schema also defines the type `Customer`. Notice that there are a list of [allowed types](https://graphql.org/learn/schema/).

Our controller has three methods. The `GraphQLController.customers()` method returns a list of `Customers`, as the snippet below shows. The `org.springframework.graphql.data.method.annotation.SchemaMapping` annotation maps the data schema and its field with the method.

```java
@SchemaMapping(typeName = "Query", field = "customers")
public Flux<Customer> customers() {
    return Flux.fromIterable(db);
}
```
As the method above, the `customersByName(String)` method maps the method with the second query in the schema. Notice that the `customersByName(String)` method has an argument, as the schema. Therefore, the need for `org.springframework.graphql.data.method.annotation.Argument` annotation. 

Different from the `customers()` method, `customersByName(String)` is annotated with `org.springframework.graphql.data.method.annotation.QueryMapping`. This is  a convinence annotation to use when there is a direct mapping between the schema and the Java method.

```java
@QueryMapping
public Flux<Customer> customersByName(@Argument String name) {
    return Flux.fromIterable(db).filter(item -> item.name().equals(name));
}
```

Finally, the `orders(Customer)` method returns a list of `Order`s. Notice that `Order` is a property of `Customer`. In this example, it means that `Order` could be retrieved from a different source. Therefore, the `@SchemaMapping` annotation maps the type `Customer`, not `Order`.

### Run the project
In order to run the project with annecdotal data, one can use the GraphiQL. Notice the "i" in the name. This is a query editor that one can use from the browser. In order to activate, add `spring.graphql.graphiql.enabled=true` to the `application.properties` file. The default URL is `/graphiql`.

Open your browser on `http://localhost:8080/graphiql?path=/graphql` to run a query.
