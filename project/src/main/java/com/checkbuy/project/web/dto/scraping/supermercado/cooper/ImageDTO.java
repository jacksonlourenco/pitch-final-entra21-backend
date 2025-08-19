package com.checkbuy.project.web.dto.scraping.supermercado.cooper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageDTO(
        String urlOriginal
){}
