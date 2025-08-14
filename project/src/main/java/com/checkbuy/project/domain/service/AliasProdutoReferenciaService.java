package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.exception.AliasProdutoReferenciaNaoEncontrado;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AliasProdutoReferenciaService {

    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;
    private final ProdutoReferenciaService produtoReferenciaService;

    public AliasProdutoReferenciaService(AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository,
                                         ProdutoReferenciaService produtoReferenciaService) {
        this.aliasProdutoReferenciaRepository = aliasProdutoReferenciaRepository;
        this.produtoReferenciaService = produtoReferenciaService;
    }

    public AliasProdutoReferencia obterProdutoReferenciaPeloAlias(String alias){
        Optional<AliasProdutoReferencia> aliasProdutoReferencia= aliasProdutoReferenciaRepository.findByAlias(alias);

        if(aliasProdutoReferencia.isEmpty()){
            throw new AliasProdutoReferenciaNaoEncontrado(alias);
        }

        return aliasProdutoReferencia.get();
    }

    @Transactional
    public void alterarProdutoReferenciaPeloAlias(String alias, Integer produtoReferenciaId){
        ProdutoReferencia produtoReferencia = produtoReferenciaService.buscarPorId(produtoReferenciaId);
        AliasProdutoReferencia aliasProdutoReferencia = obterProdutoReferenciaPeloAlias(alias);
        aliasProdutoReferencia.setProdutoReferencia(produtoReferencia);
    }
}
