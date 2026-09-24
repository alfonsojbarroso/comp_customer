package com.comp_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CustomerDto {

    private Integer id;
    private String name;
    private String phone;

}
