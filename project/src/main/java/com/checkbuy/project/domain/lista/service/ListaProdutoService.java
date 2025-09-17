package com.checkbuy.project.domain.lista.service;

import com.checkbuy.project.domain.lista.model.Lista;
import com.checkbuy.project.domain.listaresultado.model.ListaResultado;
import com.checkbuy.project.domain.listaresultado.model.ListaResultadoId;
import com.checkbuy.project.domain.listaresultado.repository.ListaResultadoRepository;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import com.checkbuy.project.domain.produtoreferencia.service.ProdutoReferenciaService;
import com.checkbuy.project.domain.usuario.model.Usuario;
import com.checkbuy.project.exception.ProdutoJaCadastroEmLista;
import com.checkbuy.project.exception.ProdutoReferenciaNaoEncontradoEmLista;
import com.checkbuy.project.exception.ProibidoInserirProdutoNotIndexEmLista;
import com.checkbuy.project.exception.ProibidoInserirQuantidadeZeroEmLista;
import com.checkbuy.project.web.dto.lista.ListaManipulacaoProdutoDTO;
import org.springframework.stereotype.Service;

@Service
public class ListaProdutoService {

    private final ListaResultadoRepository listaResultadoRepository;
    private final ProdutoReferenciaService produtoReferenciaService;
    private final ListaService listaService;

    public ListaProdutoService(ProdutoReferenciaService produtoReferenciaService,
                               ListaResultadoRepository listaResultadoRepository,
                               ListaService listaService) {
        this.produtoReferenciaService = produtoReferenciaService;
        this.listaResultadoRepository = listaResultadoRepository;
        this.listaService = listaService;
    }

    public void inserirProduto(Integer listaId, ListaManipulacaoProdutoDTO dto, Usuario usuario) {
        Lista lista = listaService.obterOuVerificarLista(listaId, usuario);
        verificarInsercaoOuAlteracao(dto);
        VerificarProdutoJaCadastroEmLista(listaId, dto);
        var produtoReferencia = produtoReferenciaService.buscarPorId(dto.produtoReferenciaId());
        var ListaResultado = criarListaResultado(lista, produtoReferencia, dto);
        listaResultadoRepository.save(ListaResultado);
    }

    public void excluirProduto(Integer listaId, Integer produtoReferenciaId, Usuario usuario) {
        listaService.obterOuVerificarLista(listaId, usuario);
        produtoReferenciaService.buscarPorId(produtoReferenciaId);

        var listaResultado = obterProdutoEmLista(listaId, produtoReferenciaId);

        listaResultadoRepository.delete(listaResultado);
    }

    public void alterarProduto(Integer listaId, ListaManipulacaoProdutoDTO dto, Usuario usuario) {
        Lista lista = listaService.obterOuVerificarLista(listaId, usuario);
        produtoReferenciaService.buscarPorId(dto.produtoReferenciaId());
        verificarInsercaoOuAlteracao(dto);
        var listaResultado = obterProdutoEmLista(listaId, dto.produtoReferenciaId());

        listaResultado.setQuantidade(dto.quantidade());
        listaResultadoRepository.save(listaResultado);
    }

    private void verificarInsercaoOuAlteracao(ListaManipulacaoProdutoDTO dto){
        if(dto.produtoReferenciaId() == 1){
            throw new ProibidoInserirProdutoNotIndexEmLista();
        }

        if(dto.quantidade() <= 0){
            throw new ProibidoInserirQuantidadeZeroEmLista(dto.produtoReferenciaId());
        }
    }

    private void VerificarProdutoJaCadastroEmLista(Integer listaId, ListaManipulacaoProdutoDTO dto){
        var id = new ListaResultadoId(listaId, dto.produtoReferenciaId());

        if(listaResultadoRepository.existsById(id)){
            throw new ProdutoJaCadastroEmLista(listaId, dto.produtoReferenciaId());
        }

    }

    private ListaResultado obterProdutoEmLista(Integer listaId, Integer produtoReferenciaId){
        var id = new ListaResultadoId(listaId, produtoReferenciaId);

        return listaResultadoRepository.findById(id)
                .orElseThrow(() -> new ProdutoReferenciaNaoEncontradoEmLista(produtoReferenciaId, listaId));
    }

    private ListaResultado criarListaResultado(Lista lista, ProdutoReferencia produtoReferencia, ListaManipulacaoProdutoDTO dto){
        ListaResultadoId id = new ListaResultadoId(lista.getId(), produtoReferencia.getId());
        return new ListaResultado(id, lista, produtoReferencia, dto.quantidade());
    }
}
