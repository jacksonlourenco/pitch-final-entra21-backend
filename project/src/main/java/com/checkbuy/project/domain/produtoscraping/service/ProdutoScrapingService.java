package com.checkbuy.project.domain.produtoscraping.service;

import com.checkbuy.project.domain.aliasprodutoreferencia.model.AliasProdutoReferencia;
import com.checkbuy.project.domain.aliasprodutoreferencia.service.AliasProdutoReferenciaService;
import com.checkbuy.project.domain.produtoreferencia.service.ProdutoReferenciaService;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import com.checkbuy.project.domain.produtoscraping.model.ProdutoScraping;
import com.checkbuy.project.domain.produtoscraping.repository.ProdutoScrapingRepository;
import com.checkbuy.project.domain.unidade.service.UnidadeService;
import com.checkbuy.project.domain.unidade.model.Unidade;
import com.checkbuy.project.web.dto.produtoreferencia.ProdutoScrapingSimilaridadeDTO;
import com.checkbuy.project.web.dto.produtoscraping.ContagemNotIndexPorUnidadeDTO;
import com.checkbuy.project.web.dto.produtoscraping.ProdutoScrapingChangeDTO;
import com.checkbuy.project.web.dto.produtoscraping.ProdutoScrapingOfertaRecentesDTO;
import jakarta.transaction.Transactional;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProdutoScrapingOfertaRecentesDTO> ofertasMaisRecentes(Integer produtoReferenciaId) {
        produtoReferenciaService.buscarPorId(produtoReferenciaId);

        var listaProdutos = produtoScrapingRepository.ofertasMaisRecentes(produtoReferenciaId);

        return listaProdutos.stream()
                .sorted(Comparator.comparingDouble(ProdutoScrapingOfertaRecentesDTO::precoEspecial))
                .collect(Collectors.toList());
    }

    public List<ProdutoScrapingSimilaridadeDTO> sugerirScraping(Integer referenciaId) {
        JaroWinklerSimilarity jwSimilarity = new JaroWinklerSimilarity();

        ProdutoReferencia referencia = produtoReferenciaService.buscarPorId(referenciaId);
        String nomeReferencia = referencia.getNome();
        List<AliasProdutoReferencia> naoIndexados = aliasProdutoReferenciaService.findAllByProdutoReferencia();

        return naoIndexados.stream()
                .map(scraping -> {

                    var produtoScraping = produtoScrapingRepository.findAllByNome(scraping.getAlias());

                    if(!produtoScraping.get().isEmpty()){
                        var first = produtoScraping.get().getFirst();
                        double similaridade = jwSimilarity.apply(nomeReferencia.toLowerCase(), scraping.getAlias().toLowerCase());
                        return new ProdutoScrapingSimilaridadeDTO(first, similaridade);
                    }

                    return new ProdutoScrapingSimilaridadeDTO();

                })
                .filter(dto -> dto.id() != -1)
                // Filtra para manter apenas os que têm uma similaridade mínima (ex: > 50%)
                .filter(dto -> dto.similaridade() > 0.5)
                // Ordena a lista pela maior similaridade
                .sorted(Comparator.comparing(ProdutoScrapingSimilaridadeDTO::similaridade).reversed())
                // Limita a um número razoável de sugestões (ex: 10)
                .limit(50)
                // Coleta o resultado em uma lista
                .collect(Collectors.toList());
    }
}
