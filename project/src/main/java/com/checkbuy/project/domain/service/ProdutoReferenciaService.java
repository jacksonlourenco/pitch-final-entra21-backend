package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.repository.ProdutoReferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoReferenciaService {

    private final ProdutoReferenciaRepository produtoReferenciaRepository;

    public ProdutoReferenciaService(ProdutoReferenciaRepository produtoReferenciaRepository) {
        this.produtoReferenciaRepository = produtoReferenciaRepository;
    }


    public List<ProdutoReferencia> listarProdutoReferencia(){
        return produtoReferenciaRepository.findAll();
    }

    public void create(ProdutoReferencia dto) {
        produtoReferenciaRepository.save(dto);
    }
}
