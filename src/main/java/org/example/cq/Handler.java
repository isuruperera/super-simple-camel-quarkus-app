package org.example.cq;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.engine.DefaultFluentProducerTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named("exampleLambdaHandler")
public class Handler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Inject
    ProducerTemplate producerTemplate;

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> stringObjectMap, Context context) {

        String response = DefaultFluentProducerTemplate.on(producerTemplate.getCamelContext())
                .to("direct:incomingRoute")
                .request(String.class);

        return Map.of(
                "statusCode", 200,
                "statusDescription", "200 OK",
                "isBase64Encoded", false,
                "headers", Map.of("Content-Type", "application/json; charset=utf-8"),
                "body", Map.of("message", response)
        );
    }
}