package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.FixProdutoNotIndexDTO;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoScrapingService {

    @Autowired
    private ProdutoScrapingRepository produtoScrapingRepository;

    @Autowired
    private ProdutoReferenciaRepository produtoReferenciaRepository;

    public List<ProdutoNotIndexDTO> listarProdutoNotIndex() {
        return produtoScrapingRepository.listarProdutoNotIndex();
    }

    @Transactional
    public void fixProdutoNotIndex(FixProdutoNotIndexDTO fixProdutoNotIndexDTO){
        var produtoReferencia = produtoReferenciaRepository.findById(1);

        produtoReferencia.ifPresent(produtoReferenciaNotIndex -> {
            var produtoReferenciaNEW = produtoReferenciaRepository.findById(fixProdutoNotIndexDTO.produtoReferenciaId());
            produtoReferenciaNEW.ifPresent(t -> {
                var produtosNotIndex = produtoScrapingRepository.findByNomeAndProdutoReferenciaAndUnidade_Estabelecimento_Nome(fixProdutoNotIndexDTO.nomeProduto(), produtoReferenciaNotIndex ,fixProdutoNotIndexDTO.estabelecimento());
                produtosNotIndex.ifPresent(produtos -> {
                    produtos.forEach(item -> {
                        item.setProdutoReferencia(t);
                    });
                });
            });
        });

    }
}
