package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import com.checkbuy.project.domain.dto.UpdateAliasProdutoReferenciaDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoReferenciaService {

    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final ProdutoReferenciaRepository produtoReferenciaRepository;
    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;

    public ProdutoReferenciaService(ProdutoScrapingRepository produtoScrapingRepository,
                                    ProdutoReferenciaRepository produtoReferenciaRepository,
                                    AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository) {

        this.produtoScrapingRepository = produtoScrapingRepository;
        this.produtoReferenciaRepository = produtoReferenciaRepository;
        this.aliasProdutoReferenciaRepository = aliasProdutoReferenciaRepository;
    }

    //CRUD

    public void create(ProdutoReferencia dto) {
        produtoReferenciaRepository.save(dto);
    }

    public List<AliasProdutoReferencia> listarNotIndex(){
        var notIndex = produtoReferenciaRepository.findByNome("NOT INDEX").orElseThrow();

        return aliasProdutoReferenciaRepository.findByProdutoReferencia(notIndex);
    }

    @Transactional
    public void alterar(UpdateAliasProdutoReferenciaDTO dto) {

        //PEGA NOVA REFERENCIA
        var novaReferencia = produtoReferenciaRepository.findById(dto.produtoReferenciaId()).orElseThrow();

        //PEGA ALIAS DO PRODUTO A SER MODIFICADO
        var aliasProduto = aliasProdutoReferenciaRepository.findByAlias(dto.nomeProduto()).orElseThrow();

        // ALTERA REFERENCIA PARA ALIAS PASSADO NO DTO
        aliasProduto.setProdutoReferencia(novaReferencia);

        // Busca os produtos que precisam ser corrigidos
        List<ProdutoScraping> listProdutosScraping =
                produtoScrapingRepository.findByNome(aliasProduto.getAlias());

        listProdutosScraping.forEach(p -> {
            p.setProdutoReferencia(novaReferencia);
        });

    }


    public List<ProdutoReferencia> listarProdutos() {
        return produtoReferenciaRepository.findAll();
    }

}
