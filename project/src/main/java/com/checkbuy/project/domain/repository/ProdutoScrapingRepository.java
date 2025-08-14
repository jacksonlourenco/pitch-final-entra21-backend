package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.dto.ProdutoScrapingChangeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoScrapingRepository extends JpaRepository<ProdutoScraping, Integer> {

    Optional<List<ProdutoScraping>> findByNomeAndProdutoReferenciaAndUnidade_Estabelecimento_Nome(String s, ProdutoReferencia p,String estabelecimento);

    Page<ProdutoScraping> findAllByProdutoReferencia(ProdutoReferencia produtoReferencia, Pageable pageable);

    List<ProdutoScraping> findAllByProdutoReferencia(ProdutoReferencia produtoReferencia);

    Optional<List<ProdutoScraping>> findAllByNome(String alias);
}