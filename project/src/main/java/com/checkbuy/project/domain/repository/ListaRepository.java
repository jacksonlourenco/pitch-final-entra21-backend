package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaRepository extends JpaRepository<Lista, Integer> {
    // Lista todas as listas de um usuário
    List<Lista> findByUsuarioId(Integer usuarioId);

    // Busca uma lista específica de um usuário
    Optional<Lista> findByIdAndUsuarioId(Integer id, Integer usuarioId);
}
