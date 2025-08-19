package com.checkbuy.project.domain.estabelecimento.repository;

import com.checkbuy.project.domain.estabelecimento.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {
}
