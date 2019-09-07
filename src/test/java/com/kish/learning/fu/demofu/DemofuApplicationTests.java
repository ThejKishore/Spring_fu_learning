package com.kish.learning.fu.demofu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class DemofuApplicationTests {

    private WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:9998").build();

    private ConfigurableApplicationContext context;

    @BeforeAll
    void beforeAll() {
        context = DemofuApplication.app.run("test");
    }

    @Test
    void requestHttpApiEndpoint() {
        client.get().uri("/").exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }


    @AfterAll
    void afterAll() {
        context.stop();
    }
}
