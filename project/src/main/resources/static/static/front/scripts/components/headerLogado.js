/**
 * Carrega o conteúdo HTML do header, insere na página e ativa a funcionalidade de busca.
 */
export async function loadHeader() {
    const headerContainer = document.getElementById('header');
    if (!headerContainer) {
        console.error("Elemento com id 'header' não foi encontrado no DOM.");
        return;
    }

    try {
        // Busca o conteúdo do arquivo header.html
        const response = await fetch('../components/headerLogado.html'); // AJUSTE ESTE CAMINHO se necessário
        if (!response.ok) {
            throw new Error(`Não foi possível carregar header.html: ${response.statusText}`);
        }
        headerContainer.innerHTML = await response.text();

        // --- LÓGICA DE BUSCA GLOBAL ---
        // Este código é executado em todas as páginas que carregam o header.
        const searchForm = document.querySelector('#header form[role="search"]');
        const searchInput = document.getElementById('searchInput');

        if (searchForm && searchInput) {
            searchForm.addEventListener('submit', (event) => {
                // 1. Impede que a página recarregue
                event.preventDefault(); 
                
                // 2. Pega o texto da busca e remove espaços em branco
                const searchTerm = searchInput.value.trim();

                // 3. Se houver texto, redireciona para a página de catálogo
                if (searchTerm) {
                    // encodeURIComponent prepara o texto para ser usado em uma URL (trata espaços, acentos, etc.)
                    const catalogURL = `../pages/catalogo-produtos.html?search=${encodeURIComponent(searchTerm)}`;
                    
                    // 4. Efetivamente redireciona o usuário
                    window.location.href = catalogURL;
                }
            });
        }
    } catch (error) {
        console.error("Erro ao carregar o header:", error);
        headerContainer.innerHTML = `<p class="text-center text-danger">Erro ao carregar o cabeçalho.</p>`;
    }
}



