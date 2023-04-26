package org.example.cq;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class HandlerTest {

    @Inject
    Handler handler;

    @Test
    void handlerTest() {
        Map<String, Object> response = handler.handleRequest(new HashMap<>(), null);
        assertEquals( 200, response.get("statusCode"), "Response successful");
    }
}