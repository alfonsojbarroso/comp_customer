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
@RequestMapping("${url.customer.endpoint}")
@CrossOrigin(origins = { "http://127.0.0.1:4173", "http://localhost:4173" })
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDto> rent(@RequestHeader(name = "flow") String flow) {
        HeadersDto headersDto = new HeadersDto(flow);
        return customerService.findAll(headersDto);

    }

    @PostMapping
    public Mono<CustomerDto> save(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }

}
