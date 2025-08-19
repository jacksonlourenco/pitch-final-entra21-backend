package com.checkbuy.project.domain.aliasprodutoreferencia.repository;

import com.checkbuy.project.domain.aliasprodutoreferencia.model.AliasProdutoReferencia;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AliasProdutoReferenciaRepository extends JpaRepository<AliasProdutoReferencia, Integer> {

    Optional<AliasProdutoReferencia> findByAlias(String alias);

    List<AliasProdutoReferencia> findByProdutoReferencia(ProdutoReferencia notIndex);
}
