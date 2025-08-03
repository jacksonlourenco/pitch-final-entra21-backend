package com.example.project.service.dto.supemercado.cooper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private int id;
    private int quantity;
    private String referenceCode;
    private String postcode;
}
