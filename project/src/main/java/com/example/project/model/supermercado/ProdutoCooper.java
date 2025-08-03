package com.example.project.model.supermercado;

import com.example.project.model.ProdutoReferencia;
import com.example.project.model.Unidade;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ProdutoCooper {

    private String nome;
    private Double preco;
    private Double precoEspecial;
    private LocalDateTime dataScraping;
    private String urlImg;
    private ProdutoReferencia produtoReferencia;
    private Unidade unidade;
}
