package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoScrapingService {

    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final ProdutoReferenciaRepository produtoReferenciaRepository;

    public ProdutoScrapingService(ProdutoScrapingRepository produtoScrapingRepository,
                                  ProdutoReferenciaRepository produtoReferenciaRepository) {
        this.produtoScrapingRepository = produtoScrapingRepository;
        this.produtoReferenciaRepository = produtoReferenciaRepository;
    }

    public List<ProdutoScraping> listarProdutosScraping(){
        return produtoScrapingRepository.findAll();
    }

    public List<ProdutoScraping> listarProdutosScraping(int produtoReferenciaId) {

        var produtoReferencia = produtoReferenciaRepository.findById(produtoReferenciaId);

        if(produtoReferencia.isEmpty()){
            throw new NoSuchElementException("Produto Referência ID:"+produtoReferenciaId + " não encontrado!");
        }

        return produtoScrapingRepository.findAllByProdutoReferencia(produtoReferencia.get());
    }


    public List<ProdutoNotIndexDTO> listarProdutoNotIndex() {
        return produtoScrapingRepository.listarProdutoNotIndex();
    }

    @Transactional
    public void fixProdutoNotIndex(ProdutoNotIndexDTO fixProdutoNotIndexDTO){
        var produtoReferencia = produtoReferenciaRepository.findById(1);

        produtoReferencia.ifPresent(produtoReferenciaNotIndex -> {
            var produtoReferenciaNEW = produtoReferenciaRepository.findById(fixProdutoNotIndexDTO.produtoReferenciaId());
            produtoReferenciaNEW.ifPresent(t -> {
                var produtosNotIndex = produtoScrapingRepository.findByNomeAndProdutoReferenciaAndUnidade_Estabelecimento_Nome(fixProdutoNotIndexDTO.nomeProduto(), produtoReferenciaNotIndex ,fixProdutoNotIndexDTO.nomeEstabelecimento());
                produtosNotIndex.ifPresent(produtos -> {
                    produtos.forEach(item -> {
                        item.setProdutoReferencia(t);
                    });
                });
            });
        });

    }


}
