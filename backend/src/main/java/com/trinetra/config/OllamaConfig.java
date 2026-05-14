package com.trinetra.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class OllamaConfig {

    // Native SLF4J logger initialization bypasses the broken Lombok compiler daemon
    private static final Logger log = LoggerFactory.getLogger(OllamaConfig.class);

    @Value("${ollama.base-url}")
    private String ollamaBaseUrl;

    @Value("${ollama.timeout-seconds}")
    private int timeoutSeconds;

    @Bean
    public WebClient ollamaWebClient() {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutSeconds * 1000)
                .responseTimeout(Duration.ofSeconds(timeoutSeconds))
                .doOnConnected(connection ->
                        connection.addHandlerLast(
                                new ReadTimeoutHandler(timeoutSeconds, TimeUnit.SECONDS)
                        )
                );

        log.info("Initializing Ollama WebClient with timeout: {} seconds", timeoutSeconds);

        return WebClient.builder()
                .baseUrl(ollamaBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
