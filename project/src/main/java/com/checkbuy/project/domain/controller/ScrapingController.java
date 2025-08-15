package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.service.ScrapingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/scraping")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping("/{termo}")
    public ResponseEntity<Void> scrapingPorTermo(@PathVariable String termo){
        scrapingService.scrapingPorTermo(termo);

        return ResponseEntity.noContent().build();
    }
}
