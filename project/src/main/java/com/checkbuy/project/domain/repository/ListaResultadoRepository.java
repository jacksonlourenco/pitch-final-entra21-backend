package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ListaResultado;
import com.checkbuy.project.domain.model.ListaResultadoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaResultadoRepository extends JpaRepository<ListaResultado, ListaResultadoId> {
}
