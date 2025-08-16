package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.dto.ContagemNotIndexPorUnidadeDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoScrapingRepository extends JpaRepository<ProdutoScraping, Integer> {

    // JPQL para buscar a contagem de produtos não indexados (produto_referencia_id = 1)
    // e projetar o resultado no nosso DTO.
    @Query("SELECT new com.checkbuy.project.domain.dto.ContagemNotIndexPorUnidadeDTO(" +
            "   e.nome, " +
            "   u.id, " +
            "   u.nome, " +
            "   COUNT(ps.id)) " +
            "FROM ProdutoScraping ps " +
            "JOIN ps.unidade u " +
            "JOIN u.estabelecimento e " +
            "WHERE ps.produtoReferencia.id = 1 " + // Assumindo que você tem as entidades mapeadas
            "GROUP BY e.nome, u.id, u.nome " +
            "ORDER BY e.nome, u.nome")
    List<ContagemNotIndexPorUnidadeDTO> findContagemNaoIndexadosPorUnidade();

    Page<ProdutoScraping> findAllByProdutoReferencia(ProdutoReferencia produtoReferencia, Pageable pageable);
    Page<ProdutoScraping> findAllByProdutoReferenciaAndNomeContainingIgnoreCase(ProdutoReferencia produtoReferencia, String nome, Pageable pageable);

    List<ProdutoScraping> findAllByProdutoReferencia(ProdutoReferencia produtoReferencia);

    Optional<List<ProdutoScraping>> findAllByNome(String alias);


    Page<ProdutoScraping> findAllByProdutoReferenciaAndUnidade(ProdutoReferencia produtoReferencia, Unidade unidade, Pageable pageable);

    Page<ProdutoScraping> findAllByProdutoReferenciaAndNomeContainingIgnoreCaseAndUnidade(ProdutoReferencia produtoReferencia, String nome, Unidade unidade, Pageable pageable);
}