package com.checkbuy.project.domain.model;

import com.checkbuy.project.domain.dto.ProdutoReferenciaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "produto_referencia")
public class ProdutoReferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String urlImg;
    private String marca;
    private String descricao;
    private String unidadeMedida;
    private Double valorMedida;
    private String codigoBarra;

    public ProdutoReferencia(ProdutoReferenciaDTO dto) {
        this.nome = dto.nome();
        this.urlImg = dto.urlImg();
        this.marca = dto.marca();
        this.descricao = dto.descricao();
        this.unidadeMedida = dto.unidadeMedida();
        this.valorMedida = dto.valorMedida();
        this.codigoBarra = dto.codigoBarra();
    }

    public void alterar(ProdutoReferenciaDTO dto){
        this.nome = dto.nome();
        this.urlImg = dto.urlImg();
        this.marca = dto.marca();
        this.descricao = dto.descricao();
        this.unidadeMedida = dto.unidadeMedida();
        this.valorMedida = dto.valorMedida();
        this.codigoBarra = dto.codigoBarra();
    }

}
