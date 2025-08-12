package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.dto.ProdutoNotIndexDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoScrapingRepository extends JpaRepository<ProdutoScraping, Integer> {

    @Query("""
                SELECT new com.checkbuy.project.domain.dto.ProdutoNotIndexDTO(
                    e.nome,
                    p.nome,
                    p.urlImg,
                    p.produtoReferencia.id)
                FROM ProdutoScraping p
                JOIN p.unidade u
                JOIN u.estabelecimento e
                WHERE p.produtoReferencia.id = 1
                GROUP BY e.nome, p.nome, p.urlImg, p.produtoReferencia.id
            """)
    List<ProdutoNotIndexDTO> listarProdutoNotIndex();

    Optional<List<ProdutoScraping>> findByNomeAndProdutoReferenciaAndUnidade_Estabelecimento_Nome(String s, ProdutoReferencia p,String estabelecimento);


    List<ProdutoScraping> findAllByProdutoReferencia(ProdutoReferencia produtoReferencia);
}
