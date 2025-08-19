package com.checkbuy.project.domain.unidade.service;

import com.checkbuy.project.domain.unidade.model.Unidade;
import com.checkbuy.project.domain.unidade.repository.UnidadeRepository;
import com.checkbuy.project.exception.UnidadeNaoEncontrada;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {
    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public Unidade obterUnidadePeloId(Integer id){
        var unidade = unidadeRepository.findById(id);

        if(unidade.isEmpty()){
            throw new UnidadeNaoEncontrada(id);
        }


        return unidade.get();
    }
}
