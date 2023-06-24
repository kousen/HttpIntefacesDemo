package com.kousenit.httpinterfacesdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@SuppressWarnings("CommentedOutCode")
@SpringBootApplication
public class HttpInterfacesDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpInterfacesDemoApplication.class, args);
    }

    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        // Easy way (no error handling)
//        WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");

        // Hard way (or at least harder way, with some error handling)
        WebClient client = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultStatusHandler(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new WebClientResponseException(
                                                response.statusCode().value(),
                                                "On the charge of Conduct Unbecoming, we find you guilty as charged",
                                                response.headers().asHttpHeaders(),
                                                body.getBytes(),
                                                Charset.defaultCharset()))))
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(
                WebClientAdapter.forClient(client)).build();
        return factory.createClient(JsonPlaceholderService.class);
    }

}
