package com.comp_customer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.comp_customer.dto.CustomerDto;
import com.comp_customer.dto.HeadersDto;

import reactor.core.publisher.Flux;

@Service 
public class CustomerService {
    
    @Value("${url.base.customer}")
    private String customerUrl;
    private final WebClient webClient;

    public CustomerService(WebClient webClient) {
        this.webClient = webClient;
    }



    public Flux<CustomerDto> findAll(HeadersDto headersDto) {
        return webClient.get()
                .uri(customerUrl)
                .retrieve()
                .bodyToFlux(CustomerDto.class);
    }

}
