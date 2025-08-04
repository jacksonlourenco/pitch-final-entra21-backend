package com.example.project.domain.repository;

import com.example.project.domain.model.ProdutoReferencia;
import com.example.project.domain.model.ProdutoScraping;
import com.example.project.domain.dto.ProdutoNotIndexDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoScrapingRepository extends JpaRepository<ProdutoScraping, Integer> {

    @Query("""
                SELECT new com.example.project.domain.dto.ProdutoNotIndexDTO(
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
}
