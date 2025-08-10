package com.checkbuy.project.service.supermercado.giassi.scraping;

import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.AliasUnidadeRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.service.supermercado.giassi.dto.ProdutoGiassiDTO;
import com.checkbuy.project.service.supermercado.komprao.dto.ProdutoKompraoDTO;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScrapingGiassiService {

    private final AliasUnidadeRepository aliasUnidadeRepository;
    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;

    private final WebDriver driver;

    public ScrapingGiassiService(
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
    }

    public List<ProdutoKompraoDTO> buscarTermo(String termo, int page) {
        List<ProdutoKompraoDTO> lista = new ArrayList<>();
        String url = "https://www.giassi.com.br/" + termo + "?page=" + page;

        driver.get(url);

        // 1. Seletor principal para encontrar cada item de produto na grade
        List<WebElement> produtos = driver.findElements(By.cssSelector("div.vtex-search-result-3-x-galleryItem"));

        for (WebElement produto : produtos) {
            try {
                // 2. Extração do nome e da URL da imagem
                String nome = produto.findElement(By.cssSelector("span.vtex-product-summary-2-x-productBrand")).getText();
                String urlImg = produto.findElement(By.cssSelector("img.vtex-product-summary-2-x-imageNormal")).getAttribute("src");

                double preco = 0.0;
                double precoEspecial = 0.0;

                // 3. Busca pelo preço original ("De R$ X")
                List<WebElement> oldPriceEls = produto.findElements(By.cssSelector("p.giassi-apps-custom-0-x-listprice"));

                // 4. Busca pelo preço atual/promocional
                WebElement currentPriceEl = produto.findElement(By.cssSelector("p.giassi-apps-custom-0-x-priceUnit"));

                if (!oldPriceEls.isEmpty()) {
                    // Caso com desconto: "preço" é o antigo, "precoEspecial" é o atual
                    preco = valueToDouble(oldPriceEls.get(0).getText());
                    precoEspecial = valueToDouble(currentPriceEl.getText());
                } else {
                    // Caso sem desconto: ambos os preços são iguais ao preço atual
                    preco = valueToDouble(currentPriceEl.getText());
                    precoEspecial = preco;
                }

                ProdutoKompraoDTO dto = new ProdutoKompraoDTO(nome, urlImg, preco, precoEspecial);
                lista.add(dto);

                System.out.println(dto);

            } catch (NoSuchElementException e) {
                // Pula o item se algum elemento essencial (como nome ou preço) não for encontrado
                System.out.println("Produto pulado por falta de informações: " + e.getMessage());
            }
        }
        return lista;
    }

    /**
     * Helper para converter o texto do preço (ex: "De R$ 35,90") para double.
     */
    private double valueToDouble(String priceText) {
        if (priceText == null || priceText.trim().isEmpty()) {
            return 0.0;
        }
        // Remove tudo que não for dígito ou vírgula, depois substitui vírgula por ponto
        String cleanedPrice = priceText.replaceAll("[^0-9,]", "").replace(",", ".");
        return Double.parseDouble(cleanedPrice);
    }


}

