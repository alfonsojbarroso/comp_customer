package com.comp_customer.config;

import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient getWebClient() {
        // 1. Configuración del Pool de Conexiones (Evita fugas de memoria y agotamiento
        // de sockets)
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom-provider")
                .maxConnections(500) // Máximo de conexiones totales
                .pendingAcquireMaxCount(1000) // Solicitudes en cola si el pool está lleno
                .pendingAcquireTimeout(Duration.ofSeconds(20)) // Tiempo de espera para obtener conexión del pool
                .maxIdleTime(Duration.ofSeconds(20)) // Tiempo de vida de una conexión ociosa
                .build();

        // 2. Configuración del HttpClient de Netty (Timeouts de red)
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Timeout de conexión (5s)
                .responseTimeout(Duration.ofSeconds(10)) // Timeout de respuesta global (10s)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS)) // Timeout de lectura
                        .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS))); // Timeout de escritura

        // 3. Construcción del WebClient apuntando al microservicio destino
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }

}