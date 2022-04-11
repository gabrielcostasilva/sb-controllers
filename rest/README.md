# MVC Example
This project is based on [Josh Long's short review of Spring REST](https://www.youtube.com/watch?v=V9KVOHkcxPo).

## Project Overview
This project is a REST version of the [MVC project](../mvc/). Instead of webpages receiving and delivering content, we have REST requests and responses. This feature is essential for integrating systems.

Differences start with the `@Controller` annotation. As the [official documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html) points out, `org.springframework.web.bind.annotation.RestController` is a convenience annotation for `@Controller` and `@ResponseBody`. 

Another difference is the method return type. `org.springframework.http.ResponseEntity` is a wrapper object that carries headers, response type, data and other information usually returned in a REST response. The object provides convenience methods to simplify the response construction.

This difference also impacts the return construction. Whereas the MVC version returned the template name, the REST version returns the `ResponseEntity` object with its data.

Finally, the data injestion also changes. In this example, a variable (_name_) is sent as part of the application path. That is captured by the following code: `@PostMapping("/{name}")`. The method captures the variable value by setting a parameter `String name` annotated with `org.springframework.web.bind.annotation.PathVariable`.

One thing interesting here is that the exception handler changed very little. The only change necessary is the method return. Notice the reponse wrappers the exception: `ResponseEntity.badRequest().body(e.getMessage())`.
