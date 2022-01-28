package com.example.websockets;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Controller
public class GreetingsWebsocketController {

	@MessageExceptionHandler
	@SendTo("/topic/errors")
	String handleException(Exception e) {
		var message = "Something went wrong: " + NestedExceptionUtils.getMostSpecificCause(e);
		System.out.println(message);

		return message;
	}

	@MessageMapping("/chat")
	@SendTo("/topic/greetings")
	GreetingsResponse greet(GreetingsRequest request) throws Exception {
		
        Assert.isTrue(Character.isUpperCase(request.name().charAt(0)), () -> "The name must start with a capital letter");
		Thread.sleep(1_000);
		
        return new GreetingsResponse("Hello, " + request.name() + " !");
	}
}
