package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.exception.ListaNaoEncontada;
import com.checkbuy.project.domain.model.Lista;
import com.checkbuy.project.domain.model.Usuario;
import com.checkbuy.project.domain.repository.ListaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ListaService {

    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public Lista criarLista(String nome, Usuario usuario) {
        Lista lista = new Lista();
        lista.setNome(nome);
        lista.setDataCriacao(LocalDate.now());
        lista.setUsuario(usuario);
        return listaRepository.save(lista);
    }

    public List<Lista> listarMinhasListas(Usuario usuario) {
        return listaRepository.findByUsuarioId(usuario.getId());
    }

    public void deletarLista(Integer listaId, Usuario usuario) {

        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());
        if(lista.isEmpty()){
            throw new ListaNaoEncontada();
        }

        listaRepository.delete(lista.get());
    }

}
