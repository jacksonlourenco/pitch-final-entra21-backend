package com.example.project.repository;

import com.example.project.model.alias.AliasUnidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AliasUnidadeRepository extends JpaRepository<AliasUnidade, Integer> {
    Optional<AliasUnidade> findByAlias(String loja);
}
