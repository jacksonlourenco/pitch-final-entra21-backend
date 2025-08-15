package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ProdutoScrapingChangeDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoScrapingService {

    private final ProdutoReferenciaService produtoReferenciaService;
    private final AliasProdutoReferenciaService aliasProdutoReferenciaService;
    private final ProdutoScrapingRepository produtoScrapingRepository;

    public ProdutoScrapingService(ProdutoReferenciaService produtoReferenciaService,
                                  AliasProdutoReferenciaService aliasProdutoReferenciaService,
                                  ProdutoScrapingRepository produtoScrapingRepository) {
        this.produtoReferenciaService = produtoReferenciaService;
        this.aliasProdutoReferenciaService = aliasProdutoReferenciaService;
        this.produtoScrapingRepository = produtoScrapingRepository;
    }

    public Page<ProdutoScraping> obterScrapingPelaReferencia(Integer produtoReferenciaId, Pageable pageable) {
        ProdutoReferencia produtoReferencia = produtoReferenciaService.buscarPorId(produtoReferenciaId);
        return produtoScrapingRepository.findAllByProdutoReferencia(produtoReferencia, pageable);
    }

    @Transactional
    public void alterarProdutoReferencia(ProdutoScrapingChangeDTO dto) {

        ProdutoReferencia  produtoReferencia = produtoReferenciaService.buscarPorId(dto.produtoReferenciaID());

        aliasProdutoReferenciaService.alterarProdutoReferenciaPeloAlias(dto.alias(), dto.produtoReferenciaID());

        List<ProdutoScraping> produtoScrapingList = obterScrapingPeloAlias(dto.alias());

        produtoScrapingList.forEach(p -> {
            p.setProdutoReferencia(produtoReferencia);
        });
    }

    public List<ProdutoScraping> obterScrapingPeloAlias(String alias) {
        var listaProdutosScraping = produtoScrapingRepository.findAllByNome(alias);
        return listaProdutosScraping.get();
    }

}
