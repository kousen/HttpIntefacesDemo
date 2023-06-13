package com.oreilly.httpinterfacesdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class HttpInterfacesDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpInterfacesDemoApplication.class, args);
    }

    @Bean
    public JsonPlaceholderService jsonPlaceholderService() {
        WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(
                WebClientAdapter.forClient(client)).build();
        return factory.createClient(JsonPlaceholderService.class);
    }

}
