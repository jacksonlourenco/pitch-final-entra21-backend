package com.checkbuy.project.domain.produtoreferencia.repository;

import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoReferenciaRepository extends JpaRepository<ProdutoReferencia, Integer> {
    Optional<ProdutoReferencia> findByNome(String notIndex);
}
