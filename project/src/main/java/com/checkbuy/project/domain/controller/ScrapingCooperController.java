package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraping/cooper")
public class ScrapingCooperController {

    @Autowired
    private ScrapingCooperService scrapingCooperService;

    @GetMapping("/{produto}")
    public void buscarPorNome(@PathVariable String produto){
        scrapingCooperService.scrapingPorTermo(produto);
    }
}
