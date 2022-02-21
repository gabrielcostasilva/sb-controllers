# RSocket Example
This project is based on [Josh Long's excellent intro to RSocket](https://www.youtube.com/watch?v=d4HAqS_VfkQ).

As WebSocket, RSocket is a protocol that [enables duplex communication over TCP](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#rsocket). However, RSocket has some advantages over WebSocket, including the fact that it is reactive in its core (in its Java implementation version).

In my opinion, RSocket is simpler to implement than WebSocket.

## Project Overview

As the [WebSocket example](../websockets/), this example sends a name as a request, and receives a message as response. The implementation of this interaction relies on `Controller` and `org.springframework.messaging.handler.annotation.MessageMapping` annotations. Whereas the former sets the application  controller, the latter sets the URL that deals with the request. The snipet below is part of `com.example.rsocket.GreetingsRSocketController` controller.

```java
@MessageMapping("greetings")
GreetingResponse greet(GreetingRequest request) {
    return new GreetingResponse("Hello, " + request.name() + " !");
}
```
We need to add the target port in the `application.properties` file, like this: `spring.rsocket.server.port=8181`.

Different from the WebSocket example, we can test the controller by using [_rsc_ utility tool](https://github.com/making/rsc), like the code snipet below:

```bash
rsc tcp://localhost:8181 --route greetings -d'{"name": "John Doe"}'
```

Yet like the WebSocket example, we may use `org.springframework.messaging.handler.annotation.MessageExceptionHandler` to mark an exception handler method. 

RSocket supports [four types of interactions](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#rsocket-overview). Whereas the first example shown a request-response type, the following example shows a request-stream type.

```java
@MessageMapping("infinite.{name}")
Flux<GreetingResponse> greet(@DestinationVariable String name) {
    
    Assert.isTrue(Character.isUpperCase(name.charAt(0)), () -> "The name should start with a capital letter!");

    return Flux
            .fromStream(Stream.generate(() -> new GreetingResponse("Hello, " + name + " @ " + Instant.now() + "!")))
            .take(10)
            .delayElements(Duration.ofSeconds(1));
}
```
Notice that the first example relies on a `GreetingRequest` object, but the code above uses a single variable `name`, mapped with `org.springframework.messaging.handler.annotation.DestinationVariable`. Also note that the `name` is part of the URL.

To make the inital request one can:

```bash
rsc tcp://localhost:8181 --stream -route infinite.Jane
```

## Further Reference
- [Getting started with RSocket](https://spring.io/blog/2020/03/02/getting-started-with-rsocket-spring-boot-server)
- [RSocket using spring boot](https://www.baeldung.com/spring-boot-rsocket)
