package com.example.project.service;

import com.example.project.service.dto.supemercado.cooper.ApiResponse;
import com.example.project.service.dto.supemercado.cooper.Variant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class Scraping {

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

        System.out.println(apiResponse.getVariants());


    }

}
