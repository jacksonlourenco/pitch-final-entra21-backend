package com.checkbuy.project.service.supermercado.cooper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Image(String urlOriginal) {
}
