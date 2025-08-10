package com.checkbuy.project.service.supermercado.komprao.scraping;

import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.AliasUnidadeRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.service.supermercado.komprao.dto.ProdutoKompraoDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ScrapingKompraoService {

    private final AliasUnidadeRepository aliasUnidadeRepository;
    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;

    private final WebDriver driver;

    public ScrapingKompraoService(
            AliasUnidadeRepository aliasUnidadeRepository,
            ProdutoScrapingRepository produtoScrapingRepository,
            AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository) {

        this.aliasUnidadeRepository = aliasUnidadeRepository;
        this.produtoScrapingRepository = produtoScrapingRepository;
        this.aliasProdutoReferenciaRepository = aliasProdutoReferenciaRepository;


        String driverPath = Paths.get("project", "drivers", "geckodriver.exe")
                .toAbsolutePath()
                .toString();
        System.setProperty("webdriver.gecko.driver", driverPath);

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        driver = new FirefoxDriver(options);

        //driver = new FirefoxDriver();
    }

    private List<ProdutoKompraoDTO> buscarTermo(String termo, int page) {
        List<ProdutoKompraoDTO> lista = new ArrayList<>();
        String url = "https://www.superkoch.com.br/catalogsearch/result/index/?p=" + page + "&q=" + termo;

        driver.get(url);

        if(page == 1){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Espera o link de seleção de cidade ficar clicável
            WebElement linkSelecionarCidade = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".store-picker-link"))
            );

            linkSelecionarCidade.click();


            // Espera o select ficar clicável
            WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("stores")));

            // Cria objeto Select para manipular o combo
            Select selectCidade = new Select(selectElement);

            // Seleciona a opção pelo texto visível
            selectCidade.selectByVisibleText("Blumenau");

            driver.get(url);
        }

        if(Objects.equals(driver.getCurrentUrl(), "https://www.superkoch.com.br/" + termo)){
            System.out.println("BATEUUU!");
            driver.get("https://www.superkoch.com.br/"+termo);
        }




        List<WebElement> produtos = driver.findElements(By.cssSelector("ol.products li.product-item"));

        for (WebElement produto : produtos) {
            double preco = 0.0;
            double precoEspecial = 0.0;
            String nome = produto.findElement(By.cssSelector(".product-item-link")).getText();
            String urlImg = produto.findElement(By.cssSelector(".product-image-photo")).getAttribute("src");

            List<WebElement> oldPriceEls = produto.findElements(By.cssSelector(".old-price .price"));
            List<WebElement> specialPriceEls = produto.findElements(By.cssSelector(".special-price .price"));

            if (oldPriceEls.isEmpty() && specialPriceEls.isEmpty()) {

                List<WebElement> precoSemDesconto = produto.findElements(By.cssSelector(".price"));

                if (precoSemDesconto.isEmpty()) {
                    throw new NoSuchElementException("Preço Não foi encontrado!");
                }

                preco = valueToDouble(precoSemDesconto);
                precoEspecial = preco;
            } else {
                preco = valueToDouble(oldPriceEls);
                precoEspecial = valueToDouble(specialPriceEls);
            }

            ProdutoKompraoDTO dto = new ProdutoKompraoDTO(nome, urlImg, preco, precoEspecial);
            lista.add(dto);
        }

        return lista;
    }

    @Async
    public CompletableFuture<Void> kochScrapingTermo(String termo) {
        int page = 1;
        boolean continuar = true;
        ProdutoKompraoDTO produtoKompraoDTO = null;

        while (continuar) {

            var produtos = buscarTermo(termo, page);

            if (page == 1) {
                produtoKompraoDTO = new ProdutoKompraoDTO(produtos.getFirst().nome(), produtos.getFirst().urlImg(), produtos.getFirst().preco(), produtos.getFirst().precoEspecial());
            }

            if (page != 1 && produtos.getFirst().nome().equals(produtoKompraoDTO.nome())) {
                continuar = false;
                System.out.println("Log: Scraping Koch - para termo de busca: " + termo + " Foi Finalizado!!");
            } else {
                salvarProdutosKomprao(produtos);
                System.out.println("Log: Scraping Koch - Page: " + page + " para termo de busca: " + termo);
                page++;
            }


        }
        return CompletableFuture.completedFuture(null);
    }

    private List<ProdutoKompraoDTO> buscarPorCategoria(String categoria, int page) {

        List<ProdutoKompraoDTO> lista = new ArrayList<>();

        driver.get("https://www.superkoch.com.br/" + categoria + "?p=" + page);

        List<WebElement> produtos = driver.findElements(By.cssSelector("ol.products li.product-item"));

        for (WebElement produto : produtos) {
            double preco = 0.0;
            double precoEspecial = 0.0;
            String nome = produto.findElement(By.cssSelector(".product-item-link")).getText();
            String urlImg = produto.findElement(By.cssSelector(".product-image-photo")).getAttribute("src");

            List<WebElement> oldPriceEls = produto.findElements(By.cssSelector(".old-price .price"));
            List<WebElement> specialPriceEls = produto.findElements(By.cssSelector(".special-price .price"));

            if (oldPriceEls.isEmpty() && specialPriceEls.isEmpty()) {

                List<WebElement> precoSemDesconto = produto.findElements(By.cssSelector(".price"));

                if (precoSemDesconto.isEmpty()) {
                    throw new NoSuchElementException("Preço Não foi encontrado!");
                }

                preco = valueToDouble(precoSemDesconto);
                precoEspecial = preco;
            } else {
                preco = valueToDouble(oldPriceEls);
                precoEspecial = valueToDouble(specialPriceEls);
            }

            ProdutoKompraoDTO dto = new ProdutoKompraoDTO(nome, urlImg, preco, precoEspecial);
            lista.add(dto);
        }

        return lista;
    }

    private void salvarProdutosKomprao(List<ProdutoKompraoDTO> produtos) {
        var loja = "koch-bnu";

        var unidade = aliasUnidadeRepository.findByAlias(loja);
        if (unidade.isEmpty()) {
            throw new NoSuchElementException("Não foi encontrado Loja cadastrada");
        }

        for (ProdutoKompraoDTO item : produtos) {
            ProdutoScraping produto = new ProdutoScraping();
            produto.setNome(item.nome());
            produto.setUrlImg(item.urlImg());
            produto.setDataScraping(LocalDateTime.now());
            produto.setPreco(item.preco());
            produto.setPrecoEspecial(item.precoEspecial());
            produto.setUnidade(unidade.get().getUnidade());

            var produtoReferencia = aliasProdutoReferenciaRepository.findByAlias(item.nome());

            if (produtoReferencia.isEmpty()) {
                var notIndex = aliasProdutoReferenciaRepository.findByAlias("NOT INDEX");
                produto.setProdutoReferencia(notIndex.orElseThrow().getProdutoReferencia());

                //CADASTRA Alias COM REFERENCIA PARA NOT-INDEX
                AliasProdutoReferencia aliasProduto = new AliasProdutoReferencia(produto.getNome(), notIndex.get());
                aliasProdutoReferenciaRepository.save(aliasProduto);
            } else {
                produto.setProdutoReferencia(produtoReferencia.get().getProdutoReferencia());
            }

            System.out.println("Komprao -  Produto Cadastrado! " + produto.getNome());
            produtoScrapingRepository.save(produto);
        }

    }

    public void kochScraping(String categoria, int page) {
        var produtos = buscarPorCategoria(categoria, page);
        salvarProdutosKomprao(produtos);
    }

    private double valueToDouble(List<WebElement> Element) {
        return Double.parseDouble(Element.getFirst().getText().replace("R$", "").replace(",", ".").trim());
    }


}
