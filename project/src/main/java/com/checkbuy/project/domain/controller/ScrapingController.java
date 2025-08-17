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

    @GetMapping("/atualizar")
    public ResponseEntity<Void> atualizar(){
        scrapingService.scrapingPorTermo("arroz branco");
        scrapingService.scrapingPorTermo("feijão");
        scrapingService.scrapingPorTermo("açúcar");
        scrapingService.scrapingPorTermo("óleo");
        scrapingService.scrapingPorTermo("farinha");
        scrapingService.scrapingPorTermo("macarrão");
        scrapingService.scrapingPorTermo("café");
        scrapingService.scrapingPorTermo("leite");
        scrapingService.scrapingPorTermo("sardinha");
        scrapingService.scrapingPorTermo("biscoitos");
        scrapingService.scrapingPorTermo("milho");
        scrapingService.scrapingPorTermo("Sabonete");
        scrapingService.scrapingPorTermo("Papel higiênico");
        scrapingService.scrapingPorTermo("Sabão em barra");
        scrapingService.scrapingPorTermo("Detergente");
        scrapingService.scrapingPorTermo("Pasta de dente");

        return ResponseEntity.noContent().build();
    }
}
