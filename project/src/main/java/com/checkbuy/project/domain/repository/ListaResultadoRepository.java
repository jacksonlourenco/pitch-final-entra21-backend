package com.checkbuy.project.domain.repository;

import com.checkbuy.project.domain.model.ListaResultado;
import com.checkbuy.project.domain.model.ListaResultadoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaResultadoRepository extends JpaRepository<ListaResultado, ListaResultadoId> {
    List<ListaResultado> findById_ListaId(Integer id);

    Optional<ListaResultado> findById_ListaIdAndId_ProdutoReferenciaId(Integer listaId, Integer produtoReferenciaId);
}
