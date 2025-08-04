package com.example.project.domain.controller;

import com.example.project.domain.dto.FixProdutoNotIndexDTO;
import com.example.project.domain.dto.ProdutoNotIndexDTO;
import com.example.project.domain.model.ProdutoReferencia;
import com.example.project.domain.repository.ProdutoReferenciaRepository;
import com.example.project.domain.service.ProdutoScrapingService;
import com.example.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scraping")
@CrossOrigin
public class ScrapingController {

    @Autowired
    private ScrapingCooperService scraping;

    @Autowired
    private ProdutoScrapingService produtoScrapingService;

    @Autowired
    private ProdutoReferenciaRepository produtoReferenciaRepository;

    @PostMapping("/criar/referencia")
    private void criarReferencia(@RequestBody ProdutoReferencia produtoReferencia){
        produtoReferenciaRepository.save(produtoReferencia);
    }

    @GetMapping("/cooper/{page}&{item}&{categoria}")
    public ResponseEntity<Object> scrapingCooper(@PathVariable int page, @PathVariable int item, @PathVariable int categoria){
        scraping.cooperScraping(page, item, categoria);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/produtos-not-index")
    public List<ProdutoNotIndexDTO> listarProdutoNotIndex(){
        return produtoScrapingService.listarProdutoNotIndex();
    }

    @PutMapping("/produtos-not-index")
    public void fixProdutoNotIndex(@Valid @RequestBody FixProdutoNotIndexDTO fixProdutoNotIndexDTO){
        produtoScrapingService.fixProdutoNotIndex(fixProdutoNotIndexDTO);
    }

    @GetMapping("produtos-referencia")
    public List<ProdutoReferencia> listarProdutos(){
        return produtoReferenciaRepository.findAll();
    }

}
