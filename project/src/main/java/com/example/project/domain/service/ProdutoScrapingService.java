package com.example.project.domain.service;

import com.example.project.domain.dto.FixProdutoNotIndexDTO;
import com.example.project.domain.model.ProdutoReferencia;
import com.example.project.domain.model.Unidade;
import com.example.project.domain.repository.ProdutoReferenciaRepository;
import com.example.project.domain.repository.ProdutoScrapingRepository;
import com.example.project.domain.dto.ProdutoNotIndexDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
