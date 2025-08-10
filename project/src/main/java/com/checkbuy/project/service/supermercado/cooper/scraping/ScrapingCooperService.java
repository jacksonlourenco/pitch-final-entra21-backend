package com.checkbuy.project.service.supermercado.cooper.scraping;

import com.checkbuy.project.domain.model.ProdutoScraping;
import com.checkbuy.project.domain.model.alias.AliasProdutoReferencia;
import com.checkbuy.project.domain.repository.AliasProdutoReferenciaRepository;
import com.checkbuy.project.domain.repository.AliasUnidadeRepository;
import com.checkbuy.project.domain.repository.ProdutoScrapingRepository;
import com.checkbuy.project.service.supermercado.cooper.dto.ListVariantsDTO;
import com.checkbuy.project.service.supermercado.cooper.dto.PriceDTO;
import com.checkbuy.project.service.supermercado.cooper.dto.VariantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ScrapingCooperService {

    private final AliasUnidadeRepository aliasUnidadeRepository;
    private final ProdutoScrapingRepository produtoScrapingRepository;
    private final AliasProdutoReferenciaRepository aliasProdutoReferenciaRepository;

    private final WebDriver driver;

    public ScrapingCooperService(
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

    private Object buscarPorNome(String produtoName, int page) {

        // Abre a página primeiro
        driver.get("https://minhacooper.com.br/loja/i.norte-bnu");

        // Executar uma requisição GET via fetch dentro da página
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return js.executeAsyncScript(
                "const callback = arguments[arguments.length - 1];" +
                        "fetch('https://minhacooper.com.br/store/api/v1/product-list/i.norte-bnu?page=" + page + "&itemsPerPage=100&searchTerm=" + produtoName + "&showOnlyAvailable=true&order=&returnFormat=json&useSearchParameters=true', {" +
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
    }

    @Async
    public CompletableFuture<Void> cooperScrapingTermo(String produtoName) {
        int page = 1;
        boolean continuar = true;

        while (continuar) {
            var json = buscarPorNome(produtoName, page);
            var apiResponse = parseJsonCooper(json);

            if (apiResponse.variants().isEmpty()) {
                continuar = false;
                System.out.println("Log: Scraping Cooper - para termo de busca: " + produtoName + " Foi Finalizado!!");
            } else {
                salvarProdutosCooper(apiResponse);
                System.out.println("Log: Scraping Cooper - Page: " + page + " para termo de busca: " + produtoName);
                page++;
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    /**
     * Utiliza Selenium com JavaScript para realizar uma requisição assíncrona à API da Cooper
     * e retorna os produtos conforme os parâmetros informados.
     *
     * @param tipoRetorno  o tipo de formato de retorno esperado (html ou json).
     * @param page         a página de resultados a ser carregada (paginação).
     * @param itemsPerPage o número de itens por página a serem retornados (min 1, max 100).
     * @param categoria    o código da categoria de produtos a ser filtrada (1 - alimentos)
     * @return um objeto contendo os dados da resposta conforme "tipoRetorno" da API.
     */
    private Object obterProdutosCooper(String tipoRetorno, int page, int itemsPerPage, int categoria) {

        // Abre a página primeiro
        driver.get("https://minhacooper.com.br/loja/i.norte-bnu");

        // Executar uma requisição GET via fetch dentro da página
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return js.executeAsyncScript(
                "const callback = arguments[arguments.length - 1];" +
                        "fetch('https://minhacooper.com.br/store/api/v1/product-list/i.norte-bnu?page=" + page + "&itemsPerPage=" + itemsPerPage + "&category%5BreferenceCode%5D=" + categoria + "&category%5BisShopping%5D=true&showOnlyAvailable=true&order=createdAt-desc&returnFormat=" + tipoRetorno + "&sortOrder%5Bfield%5D=createdAt&sortOrder%5Border%5D=desc', {" +
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
    }


    /**
     * Converte um objeto genérico (como o retornado por JavascriptExecutor) em uma instância de {@link ListVariantsDTO}.
     * Primeiro serializa o objeto em uma string JSON, depois desserializa essa string para um objeto Java.
     *
     * @param json o objeto genérico (geralmente um {@code Map} ou {@code LinkedHashMap}) retornado pelo Selenium via JavaScript.
     * @return uma instância de {@link ListVariantsDTO} contendo os dados convertidos.
     * @throws RuntimeException se ocorrer falha durante a conversão do objeto para JSON ou na desserialização.
     */
    private ListVariantsDTO parseJsonCooper(Object json) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(json);
            return mapper.readValue(jsonString, ListVariantsDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter Json em Classe" + e);
        }
    }

    /**
     * Processa a lista de variantes contida no {@link ListVariantsDTO} e salva os produtos no banco de dados.
     * Para cada variante, agrupa os preços por loja e cria uma instância de {@link ProdutoScraping}
     * com os dados correspondentes, incluindo preços padrão e especiais.
     * <p>
     * Caso a unidade correspondente à loja não seja encontrada, uma mensagem é exibida no console.
     *
     * @param apiResponse objeto contendo as variantes dos produtos obtidos da API Cooper.
     */
    private void salvarProdutosCooper(ListVariantsDTO apiResponse) {

        for (VariantDTO variant : apiResponse.variants()) {

            var variantGrouping = variant.prices().stream()
                    .collect(Collectors.groupingBy(PriceDTO::shoppingStoreReferenceCode));

            for (Map.Entry<String, List<PriceDTO>> entry : variantGrouping.entrySet()) {


                String loja = entry.getKey();
                var unidade = aliasUnidadeRepository.findByAlias(loja);

                if (unidade.isEmpty()) {
                    continue; //SE NÃO ENCONTRAR LOJA NO BANCO - PULA LOOP
                }

                ProdutoScraping produto = new ProdutoScraping();
                produto.setNome(variant.presentation());
                produto.setUrlImg(variant.product().images().getFirst().urlOriginal());
                produto.setDataScraping(LocalDateTime.now());
                List<PriceDTO> precos = entry.getValue();

                precos.stream()
                        .filter(p -> "lista-de-preco-padrao".equalsIgnoreCase(p.criteriaReferenceCode()))
                        .findFirst()
                        .ifPresent(p -> produto.setPreco(p.price()));

                precos.stream()
                        .filter(p -> "ListaPrecoCooperado".equalsIgnoreCase(p.criteriaReferenceCode()))
                        .findFirst()
                        .ifPresent(p -> produto.setPrecoEspecial(p.price()));

                produto.setUnidade(unidade.get().getUnidade());

                var produtoReferencia = aliasProdutoReferenciaRepository.findByAlias(variant.presentation());

                if (produtoReferencia.isPresent()) {
                    produto.setProdutoReferencia(produtoReferencia.get().getProdutoReferencia());
                } else {
                    var notIndex = aliasProdutoReferenciaRepository.findByAlias("NOT INDEX");
                    produto.setProdutoReferencia(notIndex.orElseThrow().getProdutoReferencia());

                    //CADASTRA Alias COM REFERENCIA PARA NOT-INDEX
                    AliasProdutoReferencia aliasProduto = new AliasProdutoReferencia(produto.getNome(), notIndex.get());
                    aliasProdutoReferenciaRepository.save(aliasProduto);
                }

                produtoScrapingRepository.save(produto);
                System.out.println("Cooper -  Produto Cadastrado! " + produto.getNome() + " -> "+ produto.getUnidade().getNome());
            }
        }
    }

    /**
     * Realiza o processo completo de scraping dos produtos da Cooper.
     * <p>
     * O método obtém os dados da API da Cooper no formato JSON, realiza a desserialização
     * para o objeto {@link ListVariantsDTO} e salva os produtos extraídos no banco de dados.
     * <p>
     * Esta é a função principal que orquestra as etapas do scraping.
     */
    public void cooperScraping(int page, int item, int categoria) {
        var json = obterProdutosCooper("json", page, item, categoria);
        var apiResponse = parseJsonCooper(json);
        salvarProdutosCooper(apiResponse);
    }
}