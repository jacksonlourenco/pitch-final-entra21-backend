package com.example.project.domain.repository;

import com.example.project.domain.model.ListaResultado;
import com.example.project.domain.model.ListaResultadoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaResultadoRepository extends JpaRepository<ListaResultado, ListaResultadoId> {
}
