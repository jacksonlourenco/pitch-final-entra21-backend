package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ProdutoReferenciaDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
            throw new NoSuchElementException("Produto Referência ID: " + id + " não foi encontrado!");
        }

        return produtoReferencia.get();
    }

    public ProdutoReferencia alterar(Integer id, ProdutoReferenciaDTO dto) {

    }
}














