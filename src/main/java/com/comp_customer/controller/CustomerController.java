package com.comp_customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comp_customer.dto.CustomerDto;
import com.comp_customer.dto.HeadersDto;
import com.comp_customer.service.CustomerService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDto> rent(@RequestHeader(name = "flow") String flow) {
        HeadersDto headersDto = HeadersDto.builder().flow(flow).build();
        return customerService.findAll(headersDto);

    }

}
