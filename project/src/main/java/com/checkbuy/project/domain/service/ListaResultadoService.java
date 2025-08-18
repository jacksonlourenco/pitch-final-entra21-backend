package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.model.ListaResultado;
import com.checkbuy.project.domain.repository.ListaResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaResultadoService {
    private final ListaResultadoRepository listaResultadoRepository;

    public ListaResultadoService(ListaResultadoRepository listaResultadoRepository) {
        this.listaResultadoRepository = listaResultadoRepository;
    }

    public List<ListaResultado> obterItensDaLista(Integer id){
        return listaResultadoRepository.findById_ListaId(id);
    }
}
