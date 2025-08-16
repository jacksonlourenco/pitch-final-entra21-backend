package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.dto.ContagemNotIndexPorUnidadeDTO;
import com.checkbuy.project.domain.dto.ProdutoScrapingChangeDTO;
import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.Unidade;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoScrapingService {

    private final ProdutoReferenciaService produtoReferenciaService;
    private final AliasProdutoReferenciaService aliasProdutoReferenciaService;
    private final UnidadeService unidadeService;
    private final ProdutoScrapingRepository produtoScrapingRepository;


    public ProdutoScrapingService(ProdutoReferenciaService produtoReferenciaService,
                                  AliasProdutoReferenciaService aliasProdutoReferenciaService,
                                  UnidadeService unidadeService,
                                  ProdutoScrapingRepository produtoScrapingRepository) {
        this.produtoReferenciaService = produtoReferenciaService;
        this.aliasProdutoReferenciaService = aliasProdutoReferenciaService;
        this.unidadeService = unidadeService;
        this.produtoScrapingRepository = produtoScrapingRepository;
    }

    public Page<ProdutoScraping> obterScrapingPelaReferencia(Integer produtoReferenciaId,
                                                             Pageable pageable,
                                                             String nome,
                                                             Integer unidadeId) {

        var paramNome = (nome!=null && !nome.isEmpty()); //SE ESTIVER PRESENTE
        var paramUnidadeId = (unidadeId != null); //SE ESTIVER PRESENTE
        Unidade unidade = null;
        if(paramUnidadeId){
            unidade = unidadeService.obterUnidadePeloId(unidadeId);
        }

        ProdutoReferencia produtoReferencia = produtoReferenciaService.buscarPorId(produtoReferenciaId);


        if(!paramNome && !paramUnidadeId){ //NÃO TIVER NENHUMA @PARAM
            return produtoScrapingRepository.findAllByProdutoReferencia(produtoReferencia, pageable);
        }else if(paramNome && !paramUnidadeId){ //TIVER SOMENTE @PARAM NOME
            return produtoScrapingRepository.findAllByProdutoReferenciaAndNomeContainingIgnoreCase(produtoReferencia, nome, pageable);
        }else if(paramUnidadeId && !paramNome){ //TIVER SOMENTE @PARAM UNIDADE ID
            return produtoScrapingRepository.findAllByProdutoReferenciaAndUnidade(produtoReferencia, unidade, pageable);
        }else{ // TIVER OS DOIS PARAMETROS
            return produtoScrapingRepository.findAllByProdutoReferenciaAndNomeContainingIgnoreCaseAndUnidade(produtoReferencia, nome, unidade, pageable);
        }

    }

    @Transactional
    public void alterarProdutoReferencia(ProdutoScrapingChangeDTO dto) {

        ProdutoReferencia  produtoReferencia = produtoReferenciaService.buscarPorId(dto.produtoReferenciaID());

        aliasProdutoReferenciaService.alterarProdutoReferenciaPeloAlias(dto.alias(), dto.produtoReferenciaID());

        List<ProdutoScraping> produtoScrapingList = obterScrapingPeloAlias(dto.alias());

        produtoScrapingList.forEach(p -> {
            p.setProdutoReferencia(produtoReferencia);
        });
    }

    public List<ProdutoScraping> obterScrapingPeloAlias(String alias) {
        var listaProdutosScraping = produtoScrapingRepository.findAllByNome(alias);
        return listaProdutosScraping.get();
    }


    public List<ContagemNotIndexPorUnidadeDTO> obterContagemPorUnidadeNotIndex() {
        return produtoScrapingRepository.findContagemNaoIndexadosPorUnidade();
    }
}
