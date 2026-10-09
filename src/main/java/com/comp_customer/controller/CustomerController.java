package com.comp_customer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comp_customer.dto.CustomerDto;
import com.comp_customer.dto.HeadersDto;
import com.comp_customer.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = { "http://127.0.0.1:4173", "http://localhost:4173" })
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public Flux<CustomerDto> rent(@RequestHeader(name = "flow") String flow) {
        HeadersDto headersDto = HeadersDto.builder().flow(flow).build();
        return customerService.findAll(headersDto);

    }

    @PostMapping("/customer")
    public Mono<CustomerDto> save(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }

}
