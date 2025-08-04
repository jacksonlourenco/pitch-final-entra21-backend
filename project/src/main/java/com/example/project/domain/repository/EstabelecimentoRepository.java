package com.example.project.domain.repository;

import com.example.project.domain.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {
}
