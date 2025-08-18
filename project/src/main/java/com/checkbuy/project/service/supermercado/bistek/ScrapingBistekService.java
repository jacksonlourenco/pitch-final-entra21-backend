package com.checkbuy.project.service.supermercado.bistek;

import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.AliasUnidadeRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.service.supermercado.dto.ProdutoDTO;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class ScrapingBistekService {

    private final AliasUnidadeRepository aliasUnidadeRepository;
    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;
    private final ProdutoScrapingRepository produtoScrapingRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScrapingBistekService.class);

    private final WebDriver driver;


    public ScrapingBistekService(AliasUnidadeRepository aliasUnidadeRepository, AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository, ProdutoScrapingRepository produtoScrapingRepository) {
        this.aliasUnidadeRepository = aliasUnidadeRepository;
        this.aliasProdutoReferenciaRepository = aliasProdutoReferenciaRepository;
        this.produtoScrapingRepository = produtoScrapingRepository;

        String driverPath = Paths.get("project", "drivers", "geckodriver.exe")
                .toAbsolutePath()
                .toString();
        System.setProperty("webdriver.gecko.driver", driverPath);

        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("-headless");
        //driver = new FirefoxDriver(options);

        driver = new FirefoxDriver();
    }

    @Async
    public CompletableFuture<Void> biteskScrapingTermo(String termo) {
        var page = 1;
        var continuar = true;

        while (continuar) {
            var produtos = buscarTermo(termo, page);
            if (produtos.isEmpty()) {
                continuar = false;
                System.out.println("Busca terminou");
            } else {
                salvarProdutosBistek(produtos);
                System.out.println("Varrendo a pagina: " + page);
                page++;
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    private List<ProdutoDTO> buscarTermo(String termo, int page) {
        String url = "https://www.bistek.com.br/" + termo + "?page=" + page;
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try{
            if (page == 1) {
                var comoDesejaReceber = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bistek-avanti-menu-2-x-cepTrigger")));
                comoDesejaReceber.click();

                var retirarNaLoja = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bistek-avanti-menu-2-x-optionPickup > span:nth-child(2)")));
                retirarNaLoja.click();

                var seletor = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bistek-avanti-menu-2-x-pickupSelect")));
                seletor.click();

                var opcaoBlumenau = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.bistek-avanti-menu-2-x-pickupSelectOption:nth-child(4) > button:nth-child(1)")));
                opcaoBlumenau.click();

                var opcaoLoja = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bistek-avanti-menu-2-x-storeCard")));
                opcaoLoja.click();

                var confirmar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bistek-avanti-menu-2-x-pickupConfirmation")));
                confirmar.click();

                try {
                    Thread.sleep(Duration.ofSeconds(15));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (TimeoutException ex){
            System.out.println(ex.getMessage());
        }

        driver.get(url);

        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            // Rola até o final da página
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Espera um pouco para o novo conteúdo carregar
            try {
                Thread.sleep(5000); // 2 segundos de espera. Ajuste se necessário.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Calcula a nova altura da página
            long newHeight = (Long) js.executeScript("return document.body.scrollHeight");

            // Se a altura não mudou, significa que chegamos ao final.
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }

        // --- FIM DA LÓGICA DE ROLAGEM ---

        // Lista para armazenar os DTOs dos produtos
        List<ProdutoDTO> lista = new ArrayList<>();

        // 1. Seleciona todos os elementos de item de produto.
        List<WebElement> produtos = driver.findElements(By.cssSelector("#gallery-layout-container .vtex-search-result-3-x-galleryItem"));

        // 2. Itera sobre cada produto.
        for (WebElement produto : produtos) {
            double preco = 0.0;
            double precoEspecial = 0.0;

            String nome = produto.findElement(By.cssSelector(".vtex-product-summary-2-x-brandName")).getText();
            String urlImg = produto.findElement(By.cssSelector("img.vtex-product-summary-2-x-imageNormal")).getAttribute("src");

            // --- NOVA LÓGICA DE PREÇO HÍBRIDA E ROBUSTA ---

            // TENTATIVA 1: Procurar por preço de clube/promocional primeiro.
            List<WebElement> clusterPriceContainer = produto.findElements(By.cssSelector(".bistek-custom-apps-0-x-clusterPrice"));

            // TENTATIVA 2: Procurar pelos preços normais (de lista e de venda).
            List<WebElement> listPriceContainer = produto.findElements(By.cssSelector(".vtex-product-price-1-x-listPrice"));
            List<WebElement> sellingPriceContainer = produto.findElements(By.cssSelector(".vtex-product-price-1-x-sellingPrice"));

            if (!clusterPriceContainer.isEmpty()) {
                // CASO 1: Encontrou um preço de CLUBE/PROMOÇÃO.
                // O preço especial é o do clube. O preço normal será o "de lista", se existir.
                precoEspecial = extractPriceFromElement(clusterPriceContainer.get(0));

                if (!listPriceContainer.isEmpty()) {
                    preco = extractPriceFromElement(listPriceContainer.get(0));
                } else if (!sellingPriceContainer.isEmpty()) {
                    // Fallback: se não tiver "de", usa o preço de venda como base.
                    preco = extractPriceFromElement(sellingPriceContainer.get(0));
                } else {
                    // Se só existir o preço do clube, o preço normal será igual ao especial.
                    preco = precoEspecial;
                }

            } else if (!sellingPriceContainer.isEmpty()) {
                // CASO 2: NÃO tem preço de clube, mas tem os preços normais.
                if (listPriceContainer.isEmpty()) {
                    // Produto sem desconto
                    preco = extractPriceFromElement(sellingPriceContainer.get(0));
                    precoEspecial = preco;
                } else {
                    // Produto com desconto
                    preco = extractPriceFromElement(listPriceContainer.get(0));
                    precoEspecial = extractPriceFromElement(sellingPriceContainer.get(0));
                }
            } else {
                // CASO 3: Nenhuma das estruturas de preço foi encontrada. Produto indisponível.
                preco = 0.0;
                precoEspecial = 0.0;
                System.out.println("Produto indisponível (nenhum preço encontrado): " + nome);
            }
            // --- FIM DA NOVA LÓGICA ---

            // Cria o DTO com os valores corretos.
            if (nome.isEmpty()) {
                break;
            }

            ProdutoDTO dto = new ProdutoDTO(nome, urlImg, preco, precoEspecial);
            lista.add(dto);
        }

        return lista;
    }

    private void salvarProdutosBistek(List<ProdutoDTO> produtos) {
        var loja = "bistek-bnu";

        var unidade = aliasUnidadeRepository.findByAlias(loja);
        if (unidade.isEmpty()) {
            throw new NoSuchElementException("Não foi encontrado Loja cadastrada");
        }

        for (ProdutoDTO item : produtos) {
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
                AliasProdutoReferencia aliasProduto = new AliasProdutoReferencia(null ,produto.getNome(), notIndex.get().getProdutoReferencia());
                aliasProdutoReferenciaRepository.save(aliasProduto);
            } else {
                produto.setProdutoReferencia(produtoReferencia.get().getProdutoReferencia());
            }

            logger.info("Bistek -  Produto Cadastrado! - {}", produto.getNome());
            produtoScrapingRepository.save(produto);
        }
    }

    private double extractPriceFromElement(WebElement priceElement) {
        try {
            String integerPart = priceElement.findElement(By.cssSelector(".vtex-product-price-1-x-currencyInteger")).getText().replaceAll("[^\\d]", "");
            String fractionPart = priceElement.findElement(By.cssSelector(".vtex-product-price-1-x-currencyFraction")).getText().replaceAll("[^\\d]", "");

            // Se alguma parte estiver vazia, define como "0"
            if (integerPart.isEmpty()) integerPart = "0";
            if (fractionPart.isEmpty()) fractionPart = "00"; // dois dígitos para centavos

            String fullPriceStr = integerPart + "." + fractionPart;

            return Double.parseDouble(fullPriceStr);
        } catch (NoSuchElementException | NumberFormatException e) {
            logger.warn("Erro ao extrair ou converter preço: {} — retornando 0.0", e.getMessage());
            return 0.0;
        }
    }
}
