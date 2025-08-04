package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.alias.AliasUnidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AliasUnidadeRepository extends JpaRepository<AliasUnidade, Integer> {
    Optional<AliasUnidade> findByAlias(String loja);
}
