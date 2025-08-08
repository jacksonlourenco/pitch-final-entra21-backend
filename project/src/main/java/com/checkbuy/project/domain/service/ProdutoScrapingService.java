package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.FixProdutoNotIndexDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoScrapingService {

    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final ProdutoReferenciaRepository produtoReferenciaRepository;

    public ProdutoScrapingService(ProdutoScrapingRepository produtoScrapingRepository, ProdutoReferenciaRepository produtoReferenciaRepository) {
        this.produtoScrapingRepository = produtoScrapingRepository;
        this.produtoReferenciaRepository = produtoReferenciaRepository;
    }

    public void criarReferencia(ProdutoReferencia dto) {
        produtoReferenciaRepository.save(dto);
    }

    public List<ProdutoReferencia> listarProdutos() {
        return produtoReferenciaRepository.findAll();
    }

    public List<ProdutoNotIndexDTO> listarProdutoNotIndex() {
        return produtoScrapingRepository.listarProdutoNotIndex();
    }


    @Transactional
    public void fixProdutoNotIndex(FixProdutoNotIndexDTO dto) {

        // Verifica se PRODUTO REFERENCIA -> NOT INDEX existe.
        Optional<ProdutoReferencia> refNotIndex = produtoReferenciaRepository.findByNome("NOT INDEX");
        if (refNotIndex.isEmpty()) return;

        // Referência Nova
        Optional<ProdutoReferencia> refProduto = produtoReferenciaRepository.findById(dto.produtoReferenciaId());
        if (refProduto.isEmpty()) return;

        // Busca os produtos que precisam ser corrigidos
        Optional<List<ProdutoScraping>> listProdutosScraping =
                produtoScrapingRepository.findByNomeAndProdutoReferenciaAndUnidade_Estabelecimento_Nome(
                        dto.nomeProduto(), refNotIndex.get(), dto.estabelecimento());

        // Atualiza a referência dos produtos encontrados
        listProdutosScraping.ifPresent(produtos ->
                produtos.forEach(produto -> produto.setProdutoReferencia(refProduto.get()))
        );
    }
}
