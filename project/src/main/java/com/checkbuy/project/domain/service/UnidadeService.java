package com.checkbuy.project.domain.service;

import com.checkbuy.project.domain.exception.UnidadeNaoEncontrada;
import com.checkbuy.project.domain.model.Unidade;
import com.checkbuy.project.domain.repository.UnidadeRepository;
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
