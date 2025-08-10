package com.checkbuy.project.domain.controller;

import com.checkbuy.project.service.supermercado.bistek.scraping.ScrapingBistek;
import com.checkbuy.project.service.supermercado.komprao.dto.ProdutoKompraoDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scraping/bistek")
@CrossOrigin
public class ScrapingBistekController {

    private final ScrapingBistek scrapingBistek;

    public ScrapingBistekController(ScrapingBistek scrapingBistek) {
        this.scrapingBistek = scrapingBistek;
    }

    @GetMapping("{produto}")
    public List<ProdutoKompraoDTO> scrapingItens(@PathVariable String produto){
        return scrapingBistek.biteskScrapingTermo(produto);
    }
}
