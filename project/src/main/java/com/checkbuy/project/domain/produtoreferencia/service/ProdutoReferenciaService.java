package com.checkbuy.project.domain.produtoreferencia.service;

import com.checkbuy.project.web.dto.produtoreferencia.ProdutoReferenciaDTO;
import com.checkbuy.project.web.dto.produtoreferencia.ProdutoReferenciaSimilaridadeDTO;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import com.checkbuy.project.domain.produtoreferencia.repository.ProdutoReferenciaRepository;
import com.checkbuy.project.exception.ProdutoReferenciaNaoEncontrado;
import com.checkbuy.project.exception.ProdutoReferenciaNotIndexImutavel;
import jakarta.transaction.Transactional;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoReferenciaService {

    private final ProdutoReferenciaRepository produtoReferenciaRepository;
    private final JaroWinklerSimilarity similarity;


    public ProdutoReferenciaService(ProdutoReferenciaRepository produtoReferenciaRepository) {
        this.produtoReferenciaRepository = produtoReferenciaRepository;
        this.similarity = new JaroWinklerSimilarity();
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

    public List<ProdutoReferencia> listar(){
        return produtoReferenciaRepository.findAll();
    }

    public void verificarIntegridadeNotIndex(Integer id){
        if(id == 1){
            throw new ProdutoReferenciaNotIndexImutavel(id);
        }
    }

    public List<ProdutoReferenciaSimilaridadeDTO> sugerir(String alias) {

        List<ProdutoReferencia> produtoReferenciaList = listar();

        return produtoReferenciaList.stream()
                .map(produtoReferencia -> {
                    Double similaridade = similarity.apply(produtoReferencia.getNome().toUpperCase(), alias.toUpperCase());
                    return new ProdutoReferenciaSimilaridadeDTO(produtoReferencia, similaridade);
                })
                .sorted(Comparator.comparingDouble(ProdutoReferenciaSimilaridadeDTO::similaridade).reversed())
                .collect(Collectors.toList());
    }

}














