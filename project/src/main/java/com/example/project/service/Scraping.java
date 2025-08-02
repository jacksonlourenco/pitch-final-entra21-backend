package com.example.project.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Scraping {

    public void cooperScraping() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try {
            // Abre a página primeiro
            driver.get("https://minhacooper.com.br/loja/i.norte-bnu/produto/listar/1?order=createdAt-desc");

            // Cria espera explícita depois de abrir a página
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Espera até o elemento da figura estar visível
            WebElement figura = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("figure.product-variation__figure")));

            // Dentro da figura, pega o link do produto
            WebElement linkProduto = figura.findElement(By.cssSelector("a.product-variation__image-container"));
            String href = linkProduto.getAttribute("href");

            // Dentro do link, pega a imagem e seu src
            WebElement img = linkProduto.findElement(By.tagName("img"));
            String imgSrc = img.getAttribute("src");
            if (imgSrc.startsWith("//")) {
                imgSrc = "https:" + imgSrc;  // Ajusta URL da imagem
            }

            // Pega o nome do produto
            WebElement nomeProduto = driver.findElement(By.cssSelector("a.product-variation__name"));
            String nome = nomeProduto.getText().trim();

            // Pega o preço final
            WebElement precoFinal = driver.findElement(By.cssSelector("span.product-variation__final-price"));
            String preco = precoFinal.getText().trim();

            // Pega o preço cooperado (se existir)
            String precoCooperado;
            try {
                WebElement precoCoop = driver.findElement(By.cssSelector("span.product-variation__cooper-price"));
                precoCooperado = precoCoop.getText().trim();
            } catch (Exception e) {
                precoCooperado = "Não informado";
            }

            // Pega o desconto (se existir)
            String desconto;
            try {
                WebElement descontoElem = driver.findElement(By.cssSelector("div.product-variation__discount"));
                desconto = descontoElem.getText().trim();
            } catch (Exception e) {
                desconto = "Sem desconto";
            }

            // Imprime os dados
            System.out.println("Nome: " + nome);
            System.out.println("Link: " + href);
            System.out.println("Imagem: " + imgSrc);
            System.out.println("Preço final: " + preco);
            System.out.println("Preço cooperado: " + precoCooperado);
            System.out.println("Desconto: " + desconto);

        } finally {
            driver.quit();
        }
    }

}
