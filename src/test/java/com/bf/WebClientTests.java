package com.bf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.reactive.function.client.WebClient;

import com.bf.app.entities.Authority;

public class WebClientTests {
    
    private static final WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080/demo/")
            .build();
    
    public static void main(String[] args) throws Exception {
        client.get()
            .uri("authority?parentId=-1")
            .retrieve()
            .bodyToFlux(Authority.class)
            .collectList()
            .block()
            .forEach(System.out::println);
    }
    
    public static void pending() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null && !"quit".equals(line)) {
            System.out.println(line);
        }
    }

}
