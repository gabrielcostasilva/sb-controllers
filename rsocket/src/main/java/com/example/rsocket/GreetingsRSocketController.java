package com.example.rsocket;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import reactor.core.publisher.Flux;

@Controller
public class GreetingsRSocketController {
    
    @MessageMapping("greetings")
    GreetingResponse greet(GreetingRequest request) {
        return new GreetingResponse("Hello, " + request.name() + " !");
    }

    @MessageMapping("infinite.{name}")
    Flux<GreetingResponse> greet(@DestinationVariable String name) {
        
        Assert.isTrue(Character.isUpperCase(name.charAt(0)), () -> "The name should start with a capital letter!");

        return Flux
                .fromStream(Stream.generate(() -> new GreetingResponse("Hello, " + name + " @ " + Instant.now() + "!")))
                .take(10)
                .delayElements(Duration.ofSeconds(1));
    }

    @MessageExceptionHandler
    String handleException(Exception e) {
        var message = "There's been a problem!" + NestedExceptionUtils.getMostSpecificCause(e);
        System.out.println("Error: " + e);

        return message;
    }
}
