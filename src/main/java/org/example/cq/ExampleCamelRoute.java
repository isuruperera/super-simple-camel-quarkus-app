package org.example.cq;

import org.apache.camel.builder.RouteBuilder;

public class ExampleCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:incomingRoute")
                .log("Incoming request ${body}")
                .to("https://www.google.com")
                .convertBodyTo(String.class)
                .process(exchange -> {
                    String response = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(response.substring(0, 10) + "...(" + (response.length() - 10) + " characters more)");
                }).end();
    }
}
