package com.example.project.service;

import com.example.project.model.ProdutoScraping;
import com.example.project.repository.AliasUnidadeRepository;
import com.example.project.repository.ProdutoScrapingRepository;
import com.example.project.service.dto.supemercado.cooper.ApiResponse;
import com.example.project.service.dto.supemercado.cooper.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Scraping {

    @Autowired
    private AliasUnidadeRepository aliasUnidadeRepository;

    @Autowired
    private ProdutoScrapingRepository produtoScrapingRepository;

    public void cooperScraping() {
        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();

        // Abre a página primeiro
        driver.get("https://minhacooper.com.br/loja/i.norte-bnu");

        // Executar uma requisição GET via fetch dentro da página
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object response = js.executeAsyncScript(
                "const callback = arguments[arguments.length - 1];" +
                        "fetch('https://minhacooper.com.br/store/api/v1/product-list/i.norte-bnu?page=1&itemsPerPage=2&category%5BreferenceCode%5D=1&category%5BisShopping%5D=true&showOnlyAvailable=true&order=createdAt-desc&returnFormat=json&sortOrder%5Bfield%5D=createdAt&sortOrder%5Border%5D=desc', {" +
                        "  method: 'GET'," +
                        "  headers: {" +
                        "    'X-Requested-With': 'XMLHttpRequest'," +
                        "    'Accept': '*/*'" +
                        "  }" +
                        "})" +
                        ".then(response => response.json())" +
                        ".then(data => callback(data))" +
                        ".catch(error => callback(error.toString()));"
        );



        // Mapeia o JSON para objetos Java
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        ApiResponse apiResponse = null;
        try {
            apiResponse = mapper.readValue(json, ApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        var unitario = apiResponse.getVariants().get(0);
        var teste = apiResponse.getVariants().get(0).getPrices().stream()
                .collect(Collectors.groupingBy(Price::getShoppingStoreReferenceCode));



        for (Map.Entry<String, List<Price>> entry : teste.entrySet()) {
            ProdutoScraping produto = new ProdutoScraping();
            produto.setNome(unitario.getPresentation());
            produto.setUrlImg(unitario.getUrl());
            produto.setDataScraping(LocalDateTime.now());

            String loja = entry.getKey();
            var unidade = aliasUnidadeRepository.findByAlias(loja);

            unidade.ifPresent(aliasUnidade -> System.out.println(aliasUnidade.getUnidade().getNome()));

            List<Price> precos = entry.getValue();

            precos.stream()
                    .filter(p -> "lista-de-preco-padrao".equalsIgnoreCase(p.getCriteriaReferenceCode()))
                    .findFirst()
                    .ifPresent(p -> produto.setPreco(p.getPrice()));

            precos.stream()
                    .filter(p -> "ListaPrecoCooperado".equalsIgnoreCase(p.getCriteriaReferenceCode()))
                    .findFirst()
                    .ifPresent(pr -> produto.setPrecoEspecial(pr.getPrice()));

            if(unidade.isPresent()){
                produto.setUnidade(unidade.get().getUnidade());
                produtoScrapingRepository.save(produto);
            }
        }

    }

}
