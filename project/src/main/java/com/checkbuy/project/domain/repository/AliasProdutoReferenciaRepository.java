package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AliasProdutoReferenciaRepository extends JpaRepository<AliasProdutoReferencia, Integer> {

    Optional<AliasProdutoReferencia> findByAlias(String alias);

    List<AliasProdutoReferencia> findByProdutoReferencia(ProdutoReferencia notIndex);
}
