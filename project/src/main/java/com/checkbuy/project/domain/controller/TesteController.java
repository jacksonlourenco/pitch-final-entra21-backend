package com.checkbuy.project.domain.controller;

import com.checkbuy.project.domain.model.*;
import com.checkbuy.project.domain.repository.*;
import com.checkbuy.project.service.supermercado.cooper.scraping.ScrapingCooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private ProdutoScrapingRepository produtoScrapingRepository;

    @Autowired
    private ProdutoReferenciaRepository produtoReferenciaRepository;

    @Autowired
    private ListaResultadoRepository listaResultadoRepository;

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ScrapingCooperService scraping;


    @GetMapping("/usuario")
    public List<Usuario> usuario(){
        return  usuarioRepository.findAll();
    }

    @GetMapping("/unidade")
    public List<Unidade> unidade(){
        return  unidadeRepository.findAll();
    }

    @GetMapping("/produtoscraping")
    public List<ProdutoScraping> produtoScraping(){
        return  produtoScrapingRepository.findAll();
    }

    @GetMapping("/produtoreferencia")
    public List<ProdutoReferencia> produtoReferencia(){
        return  produtoReferenciaRepository.findAll();
    }

    @GetMapping("/listaresultado")
    public List<ListaResultado> listaResultado(){
        return  listaResultadoRepository.findAll();
    }

    @GetMapping("/lista")
    public List<Lista> lista(){
        return  listaRepository.findAll();
    }

    @GetMapping("/estabelecimento")
    public List<Estabelecimento> estabelecimento(){
        return  estabelecimentoRepository.findAll();
    }


}
