package com.oreilly.httpinterfacesdemo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonPlaceholderTest {
    @Autowired
    private WebTestClient client;

    @Test
    void getPosts() {
        client.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BlogPost.class)
                .hasSize(100);
    }

    @Test
    void getPost() {
        client.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BlogPost.class)
                .value(BlogPost::id, Matchers.equalTo(1));
    }

    @Test
    void post() {
        var post = new BlogPost(null, 1,
                "Test Post", "This is a test post.");
        client.post()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .bodyValue(post)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BlogPost.class)
                .value(BlogPost::id, Matchers.notNullValue());
    }

    @Test
    void put() {
        var post = new BlogPost(1, 1,
                "Test Post", "This is a test post.");
        client.put()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .bodyValue(post)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BlogPost.class)
                .value(BlogPost::id, Matchers.equalTo(1));
    }

    @Test
    void delete() {
        client.delete()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .exchange()
                //.expectStatus().isNoContent();
                .expectStatus().isOk();  // Service returns 200 when it should be 204
    }
}
