package com.checkbuy.project.domain.lista.service;

import com.checkbuy.project.domain.lista.model.Lista;
import com.checkbuy.project.domain.lista.repository.ListaRepository;
import com.checkbuy.project.domain.listaresultado.service.ListaResultadoService;
import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.exception.*;
import com.checkbuy.project.web.dto.lista.ListaCriarDTO;
import com.checkbuy.project.web.dto.lista.ListaDTO;
import com.checkbuy.project.web.dto.listaresultado.ListaResultadoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListaService {

    private final ListaRepository listaRepository;
    private final ListaResultadoService listaResultadoService;


    public ListaService(ListaRepository listaRepository,
                        ListaResultadoService listaResultadoService) {
        this.listaRepository = listaRepository;
        this.listaResultadoService = listaResultadoService;
    }

    public ListaDTO criarLista(ListaCriarDTO dto, Usuario usuario) {
        Lista lista = new Lista();
        lista.setNome(dto.nome());
        lista.setDataCriacao(LocalDate.now());
        lista.setUsuario(usuario);
        listaRepository.save(lista);
        return new ListaDTO(lista);
    }

    public List<ListaDTO> listarMinhasListas(Usuario usuario) {

        var lista =  listaRepository.findByUsuarioId(usuario.getId());

        return lista.stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
    }

    public void deletarLista(Integer listaId, Usuario usuario) {

        Lista lista = obterOuVerificarLista(listaId, usuario);
        listaRepository.delete(lista);
    }

    public List<ListaResultadoDTO> obterLista(Integer listaId, Usuario usuario) {

        obterOuVerificarLista(listaId, usuario);

        var listaResultado = listaResultadoService.obterItensDaLista(listaId);

        return listaResultado.stream()
                .map(ListaResultadoDTO::new)
                .collect(Collectors.toList());

    }

    protected Lista obterOuVerificarLista(Integer listaId, Usuario usuario){
        return listaRepository.findByIdAndUsuarioId(listaId, usuario.getId())
                .orElseThrow(() -> new ListaNaoEncontada(listaId, usuario.getId()));
    }
}
