package org.example.cq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;

@Testcontainers
class HandlerIT {

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
            .withServices(LocalStackContainer.Service.LAMBDA)
            .withEnv("DEFAULT_REGION", "us-east-1")
            .withCopyFileToContainer(MountableFile.forHostPath("target/function.zip"), "/opt/code/localstack/function.zip");

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        String runtime = isNativeEnabled() ? "provided.al2" : "java11";
        String handler = isNativeEnabled() ? "not.used.in.provided.runtime" : "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest";

        localStack.execInContainer(
                "awslocal", "lambda", "create-function",
                "--function-name", "super-simple-camel-quarkus-app",
                "--runtime", runtime,
                "--region", "us-east-1",
                "--handler", handler,
                "--zip-file", "fileb://function.zip",
                "--role", "arn:aws:iam::123456789012:role/any",
                "--timeout", "300"
        );
        localStack.execInContainer(
                "awslocal", "lambda", "wait", "function-active-v2",
                "--function-name", "super-simple-camel-quarkus-app"
        );
    }

    @Test
    void handlerTest() throws IOException, InterruptedException {
        localStack.execInContainer(
                "awslocal", "lambda", "invoke", "--function-name", "super-simple-camel-quarkus-app", "--payload", "{}", "result.json"
        );

        org.testcontainers.containers.Container.ExecResult lambdaResult = localStack.execInContainer(
                "cat", "result.json"
        );
        String response = lambdaResult.getStdout();
        System.out.println("Lambda result: " + response);
        Assertions.assertTrue(response.contains("200 OK"));
    }

    private static boolean isNativeEnabled() {
        return System.getProperty("build.profile.name") != null && System.getProperty("build.profile.name").equals("native");
    }
}
