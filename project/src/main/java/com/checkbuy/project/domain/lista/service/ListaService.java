package com.checkbuy.project.domain.lista.service;

import com.checkbuy.project.domain.lista.model.Lista;
import com.checkbuy.project.domain.lista.repository.ListaRepository;
import com.checkbuy.project.domain.listaresultado.model.ListaResultado;
import com.checkbuy.project.domain.listaresultado.model.ListaResultadoId;
import com.checkbuy.project.domain.listaresultado.repository.ListaResultadoRepository;
import com.checkbuy.project.domain.listaresultado.service.ListaResultadoService;
import com.checkbuy.project.domain.produtoreferencia.service.ProdutoReferenciaService;
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
    private final ProdutoReferenciaService produtoReferenciaService;
    private final ListaResultadoRepository listaResultadoRepository;

    public ListaService(ListaRepository listaRepository, ListaResultadoService listaResultadoService, ProdutoReferenciaService produtoReferenciaService, ListaResultadoRepository listaResultadoRepository) {
        this.listaRepository = listaRepository;
        this.listaResultadoService = listaResultadoService;
        this.produtoReferenciaService = produtoReferenciaService;
        this.listaResultadoRepository = listaResultadoRepository;
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

        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());
        if(lista.isEmpty()){
            throw new ListaNaoEncontada(listaId, usuario.getId());
        }

        listaRepository.delete(lista.get());
    }

    public List<ListaResultadoDTO> obterLista(Integer listaId, Usuario usuario) {
        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());

        if(lista.isEmpty()){
            throw new ListaNaoEncontada(listaId, usuario.getId());
        }

        var listaResultado = listaResultadoService.obterItensDaLista(listaId);

        return listaResultado.stream()
                .map(ListaResultadoDTO::new)
                .collect(Collectors.toList());

    }

    public void inserirProduto(Integer listaId, Integer produtoReferenciaId, Integer quantidade, Usuario usuario) {
        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());

        if(lista.isEmpty()){
            throw new ListaNaoEncontada(listaId, usuario.getId());
        }

        if(produtoReferenciaId == 1){
            throw new ProibidoInserirProdutoNotIndexEmLista();
        }

        if(quantidade <= 0){
            throw new ProibidoInserirQuantidadeZeroEmLista(produtoReferenciaId);
        }

        var id = new ListaResultadoId(listaId, produtoReferenciaId);


        if(listaResultadoRepository.existsById(id)){
            throw new ProdutoJaCadastroEmLista(listaId, produtoReferenciaId);
        }

        var produtoReferencia = produtoReferenciaService.buscarPorId(produtoReferenciaId);
        var insercaoProduto = new ListaResultado(id, lista.get(), produtoReferencia, quantidade);

        listaResultadoRepository.save(insercaoProduto);
    }

    public void excluirProduto(Integer listaId, Integer produtoReferenciaId, Usuario usuario) {
        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());
        produtoReferenciaService.buscarPorId(produtoReferenciaId);

        if(lista.isEmpty()){
            throw new ListaNaoEncontada(listaId, usuario.getId());
        }

        var listaResultado = listaResultadoRepository.findById_ListaIdAndId_ProdutoReferenciaId(listaId, produtoReferenciaId);

        if(listaResultado.isEmpty()){
            throw new ProdutoReferenciaNaoEncontrado(produtoReferenciaId);
        }

        listaResultadoRepository.delete(listaResultado.get());
    }

    public void alterarProduto(Integer listaId, Integer produtoReferenciaId, Integer quantidade, Usuario usuario) {
        Optional<Lista> lista = listaRepository.findByIdAndUsuarioId(listaId, usuario.getId());

        if(lista.isEmpty()){
            throw new ListaNaoEncontada(listaId, usuario.getId());
        }

        if(produtoReferenciaId == 1){
            throw new ProibidoInserirProdutoNotIndexEmLista();
        }

        if(quantidade <= 0){
            throw new ProibidoInserirQuantidadeZeroEmLista(produtoReferenciaId);
        }

        var check = listaResultadoRepository.findById_ListaIdAndId_ProdutoReferenciaId(listaId, produtoReferenciaId);

        if(check.isEmpty()){
            throw new ProdutoReferenciaNaoEncontrado(produtoReferenciaId);
        }

        produtoReferenciaService.buscarPorId(produtoReferenciaId);

        var id = new ListaResultadoId(listaId, produtoReferenciaId);
        var listaResultado = listaResultadoRepository.findById(id);

        if(listaResultado.isEmpty()){
            throw new RuntimeException();
        }

        listaResultado.get().setQuantidade(quantidade);

        listaResultadoRepository.save(listaResultado.get());
    }
}
