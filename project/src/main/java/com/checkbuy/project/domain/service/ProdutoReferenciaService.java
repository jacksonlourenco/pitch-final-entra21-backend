package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ProdutoReferenciaDTO;
import com.checkbuy.project.domain.exception.ProdutoReferenciaNaoEncontrado;
import com.checkbuy.project.domain.exception.ProdutoReferenciaNotIndexImutavel;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoReferenciaService {

    private final ProdutoReferenciaRepository produtoReferenciaRepository;

    public ProdutoReferenciaService(ProdutoReferenciaRepository produtoReferenciaRepository) {
        this.produtoReferenciaRepository = produtoReferenciaRepository;
    }

    public ProdutoReferencia criar(ProdutoReferenciaDTO dto){
        ProdutoReferencia produtoReferencia = new ProdutoReferencia(dto);
        return produtoReferenciaRepository.save(produtoReferencia);
    }

    public ProdutoReferencia buscarPorId(Integer id) {
        Optional<ProdutoReferencia> produtoReferencia = produtoReferenciaRepository.findById(id);

        if(produtoReferencia.isEmpty()){
            throw new ProdutoReferenciaNaoEncontrado(id);
        }

        return produtoReferencia.get();
    }

    @Transactional
    public ProdutoReferencia alterar(Integer id, ProdutoReferenciaDTO dto) {
        verificarIntegridadeNotIndex(id);
        ProdutoReferencia produtoReferencia = buscarPorId(id);
        produtoReferencia.alterar(dto);
        return produtoReferencia;
    }

    public void excluir(Integer id) {
        verificarIntegridadeNotIndex(id);
        buscarPorId(id);
        produtoReferenciaRepository.deleteById(id);
    }

    public Page<ProdutoReferencia> listar(Pageable pageable){
        return produtoReferenciaRepository.findAll(pageable);
    }

    public void verificarIntegridadeNotIndex(Integer id){
        if(id == 1){
            throw new ProdutoReferenciaNotIndexImutavel(id);
        }
    }
}














