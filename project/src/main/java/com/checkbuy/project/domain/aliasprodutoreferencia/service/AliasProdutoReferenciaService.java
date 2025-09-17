package com.checkbuy.project.domain.aliasprodutoreferencia.service;

import com.checkbuy.project.domain.aliasprodutoreferencia.model.AliasProdutoReferencia;
import com.checkbuy.project.domain.aliasprodutoreferencia.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.produtoscraping.model.ProdutoScraping;
import com.checkbuy.project.exception.AliasProdutoReferenciaNaoEncontrado;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import com.checkbuy.project.domain.produtoreferencia.service.ProdutoReferenciaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<AliasProdutoReferencia> findAllByProdutoReferencia() {
        Integer notIndex = 1;
        ProdutoReferencia produtoReferencia = produtoReferenciaService.buscarPorId(1);
        return aliasProdutoReferenciaRepository.findAllByProdutoReferencia(produtoReferencia);
    }
}
