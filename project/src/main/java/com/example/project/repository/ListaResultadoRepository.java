package com.example.project.repository;

import com.example.project.model.ListaResultado;
import com.example.project.model.ListaResultadoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaResultadoRepository extends JpaRepository<ListaResultado, ListaResultadoId> {
}
