package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoReferenciaRepository extends JpaRepository<ProdutoReferencia, Integer> {
    Optional<ProdutoReferencia> findByNome(String notIndex);
}
