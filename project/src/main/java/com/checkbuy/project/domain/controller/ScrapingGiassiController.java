package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.giassi.scraping.ScrapingGiassiService;
import com.checkbuy.project.service.supermercado.komprao.dto.ProdutoKompraoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/scraping/giassi")
public class ScrapingGiassiController {

    private final ScrapingGiassiService scrapingGiassiService;

    public ScrapingGiassiController(ScrapingGiassiService scrapingGiassiService) {
        this.scrapingGiassiService = scrapingGiassiService;
    }

    @GetMapping("/{termo}/{page}")
    public void buscarTermo(@PathVariable String termo,@PathVariable int page) {
        var lista = scrapingGiassiService.buscarTermo(termo, page);
        for (ProdutoKompraoDTO produto : lista) {
            System.out.println(produto);
        }
    }
}
