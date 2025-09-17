import { API_BASE_URL } from './config.js';

export function initCatalogPage() {
    let searchInput;
    const productGrid = document.getElementById('product-grid');
    const filterSidebar = document.getElementById('filter-sidebar');
    const offcanvasFiltersBody = document.getElementById('offcanvas-filters-body');
    const filtersContentWrapper = document.createElement('div');
    filtersContentWrapper.innerHTML = `
        <h4>Filtro</h4>
        <hr>
        <div id="filters-container"></div>
    `;
    const filtersContainer = filtersContentWrapper.querySelector('#filters-container');
    const selectedFiltersContainer = document.getElementById('selected-filters-container');
    const sortByPriceButton = document.getElementById('sort-by-price');
    const paginationContainer = document.getElementById('pagination-container');

    let allReferences = [];
    let isSortedByPrice = false;
    let currentPage = 1;
    const productsPerPage = 12;

    const moveFilters = () => {
        if (window.innerWidth >= 768) {
            if (filterSidebar && !filterSidebar.contains(filtersContentWrapper)) {
                filterSidebar.appendChild(filtersContentWrapper);
            }
        } else {
            if (offcanvasFiltersBody && !offcanvasFiltersBody.contains(filtersContentWrapper)) {
                offcanvasFiltersBody.appendChild(filtersContentWrapper);
            }
        }
    };

    function getToken() {
        return localStorage.getItem('jwtToken');
    }

    async function fetchData(endpoint, token) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (!response.ok) throw new Error(`Erro na requisição: ${response.statusText}`);
            return await response.json();
        } catch (error) {
            console.error(`Falha ao buscar dados de ${endpoint}:`, error);
            return null;
        }
    }

    function createProductCard(product) {
        const prices = product.prices || [];
        const formatPrice = (price) => price ? `R$${price.toFixed(2).replace('.', ',')}` : '-';
        const bestPrice = formatPrice(prices[0]);
        const otherPrice1 = formatPrice(prices[1]);
        const otherPrice2 = formatPrice(prices[2]);
        const imageElement = product.urlImg ? `<img src="${product.urlImg}" class="card-img-top" alt="${product.nome}">` : `<div class="placeholder-box card-img-top"></div>`;
        let savingsBadgeHTML = '';
        if (prices && prices.length >= 2) {
            const lowestPrice = prices[0];
            const highestPrice = Math.max(...prices);
            if (highestPrice > lowestPrice) {
                const savings = ((highestPrice - lowestPrice) / highestPrice) * 100;
                savingsBadgeHTML = `<div class="card-savings-badge">${savings.toFixed(0)}% OFF</div>`;
            }
        }
        return `
            <div class="col">
                <a href="detalhes-produto.html?id=${product.id}" class="text-decoration-none text-dark">
                    <div class="card h-100 border-0 rounded-0">
                        ${savingsBadgeHTML} ${imageElement}
                        <div class="card-body p-3 d-flex flex-column">
                            <h6 class="card-title">${product.nome}</h6>
                            <div class="mt-auto">
                                <small class="text-muted">Melhor preço</small>
                                <div class="d-flex gap-2 mt-2">
                                    <span class="btn btn-sm btn-success rounded-1">${bestPrice}</span>
                                    <span class="btn btn-sm btn-custom-danger rounded-1">${otherPrice1}</span>
                                    <span class="btn btn-sm btn-custom-danger rounded-1">${otherPrice2}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        `;
    }

    function renderFilterGroup(title, name, options) {
        let html = `<div class="mb-3"><p class="fw-bold mb-1">${title}</p>`;
        options.forEach(option => {
            const id = `filter-${name}-${option.replace(/\s/g, '-')}-${Math.random().toString(36).substring(2, 8)}`;
            html += `<div class="form-check"><input class="form-check-input filter-checkbox" type="checkbox" value="${option}" data-filter-name="${name}" id="${id}"><label class="form-check-label" for="${id}">${option}</label></div>`;
        });
        html += `</div>`;
        return html;
    }

    function generateAndRenderFilters(references) {
        const previouslyActiveFilters = getActiveFilters();
        filtersContainer.innerHTML = '';
        const filters = { marca: new Set(), tamanho: new Set() };
        references.forEach(item => {
            if (item.marca) filters.marca.add(item.marca);
            if (item.valorMedida && item.unidadeMedida) filters.tamanho.add(`${item.valorMedida} ${item.unidadeMedida}`);
        });
        let allFiltersHTML = '';
        allFiltersHTML += renderFilterGroup('Marca', 'marca', [...filters.marca].sort());
        allFiltersHTML += renderFilterGroup('Tamanho', 'tamanho', [...filters.tamanho].sort());
        filtersContainer.innerHTML = allFiltersHTML;
        for (const category in previouslyActiveFilters) {
            previouslyActiveFilters[category].forEach(value => {
                const checkbox = filtersContainer.querySelector(`input[data-filter-name="${category}"][value="${value}"]`);
                if (checkbox) checkbox.checked = true;
            });
        }
        addFilterListeners();
    }

    function addFilterListeners() {
        document.querySelectorAll('.filter-checkbox').forEach(checkbox => {
            checkbox.addEventListener('change', () => {
                currentPage = 1;
                updateDisplay();
                const offcanvasEl = document.getElementById('offcanvasFilters');
                if (offcanvasEl) {
                    const offcanvas = bootstrap.Offcanvas.getInstance(offcanvasEl);
                    if (offcanvas && window.innerWidth < 768) {
                        offcanvas.hide();
                    }
                }
            });
        });
    }

    function getActiveFilters() {
        const activeFilters = { marca: [], tamanho: [] };
        document.querySelectorAll('.filter-checkbox:checked').forEach(checkbox => {
            const filterName = checkbox.getAttribute('data-filter-name');
            if (activeFilters[filterName]) activeFilters[filterName].push(checkbox.value);
        });
        return activeFilters;
    }

    function renderSelectedFilters(filters) {
        selectedFiltersContainer.innerHTML = '';
        if (!filters || (!filters.marca.length && !filters.tamanho.length)) return;
        [...(filters.marca || []), ...(filters.tamanho || [])].forEach(filter => {
            selectedFiltersContainer.innerHTML += `<span class="btn btn-sm btn-light border-secondary-subtle">${filter}</span>`;
        });
    }

    function renderProductCards(productsToRender) {
        productGrid.innerHTML = '';
        if (productsToRender.length === 0) {
            productGrid.innerHTML = `<div class="col-12"><div class="alert alert-info text-center">Nenhum produto encontrado.</div></div>`;
            return;
        }
        productsToRender.forEach(product => productGrid.insertAdjacentHTML('beforeend', createProductCard(product)));
    }

    function renderPagination(totalProducts) {
        paginationContainer.innerHTML = '';
        const totalPages = Math.ceil(totalProducts / productsPerPage);
        if (totalPages <= 1) return;
        let paginationHTML = `<nav aria-label="Navegação de página"><ul class="pagination">`;
        paginationHTML += `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage - 1}">Anterior</a></li>`;
        for (let i = 1; i <= totalPages; i++) {
            paginationHTML += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
        }
        paginationHTML += `<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage + 1}">Próximo</a></li>`;
        paginationHTML += `</ul></nav>`;
        paginationContainer.innerHTML = paginationHTML;
        document.querySelectorAll('.page-link').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const page = parseInt(e.target.dataset.page, 10);
                if (page && page !== currentPage && page > 0 && page <= totalPages) {
                    currentPage = page;
                    updateDisplay();
                    window.scrollTo(0, 0);
                }
            });
        });
    }

    sortByPriceButton.addEventListener('click', () => {
        isSortedByPrice = !isSortedByPrice;
        sortByPriceButton.textContent = isSortedByPrice ? 'Remover Ordenação' : 'Ordenar por Menor Preço';
        sortByPriceButton.classList.toggle('btn-primary', !isSortedByPrice);
        sortByPriceButton.classList.toggle('btn-outline-primary', isSortedByPrice);
        currentPage = 1;
        updateDisplay();
    });

    async function updateDisplay() {
        productGrid.innerHTML = `<div class="col-12 text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Carregando...</span></div></div>`;
        paginationContainer.innerHTML = '';
        const token = getToken();

        const searchTerm = searchInput ? searchInput.value.trim().toLowerCase() : '';
        const referencesAfterSearch = allReferences.filter(ref => {
            if (ref.id === 1) return false;
            if (searchTerm && !ref.nome.toLowerCase().includes(searchTerm)) return false;
            return true;
        });

        generateAndRenderFilters(referencesAfterSearch);

        const activeFilters = getActiveFilters();
        renderSelectedFilters(activeFilters);
        const referencesAfterAllFilters = referencesAfterSearch.filter(ref => {
            if (activeFilters.marca.length > 0 && !activeFilters.marca.includes(ref.marca)) return false;
            if (activeFilters.tamanho.length > 0 && !activeFilters.tamanho.includes(`${ref.valorMedida} ${ref.unidadeMedida}`)) return false;
            return true;
        });

        const startIndex = (currentPage - 1) * productsPerPage;
        const endIndex = startIndex + productsPerPage;
        const referencesForCurrentPage = referencesAfterAllFilters.slice(startIndex, endIndex);

        const scrapingPromises = referencesForCurrentPage.map(async (reference) => {
            const scrapingData = await fetchData(`/produtos/scraping/${reference.id}?page=0&size=10`, token);
            let prices = [];
            if (scrapingData && scrapingData.content.length > 0) {
                prices = scrapingData.content
                    .map(item => (item.precoEspecial && item.precoEspecial > 0 && item.precoEspecial < item.preco) ? item.precoEspecial : item.preco)
                    .filter(price => price !== null && !isNaN(price));
                prices.sort((a, b) => a - b);
            }
            return { ...reference, prices };
        });

        let productsForCurrentPage = await Promise.all(scrapingPromises);
        if (isSortedByPrice) {
            productsForCurrentPage.sort((a, b) => (a.prices[0] || Infinity) - (b.prices[0] || Infinity));
        }

        renderProductCards(productsForCurrentPage);
        renderPagination(referencesAfterAllFilters.length);
    }

    async function initializePage() {
        const token = getToken();
        if (!token) {
            productGrid.innerHTML = `<div class="col-12"><div class="alert alert-danger">Erro: Token de autenticação não encontrado.</div></div>`;
            return;
        }
        productGrid.innerHTML = `<div class="col-12 text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Carregando...</span></div></div>`;
        moveFilters();
        window.addEventListener('resize', moveFilters);

        const allReferencesData = await fetchData('/produtos/referencias/listar?page=0&size=1000', token);
        if (!allReferencesData || !allReferencesData.content) {
            productGrid.innerHTML = `<div class="col-12"><div class="alert alert-warning">Nenhum produto encontrado.</div></div>`;
            return;
        }
        allReferences = allReferencesData.content.filter(p => p.id !== 1);

        searchInput = document.getElementById('searchInput');
        const urlParams = new URLSearchParams(window.location.search);
        const searchTermFromURL = urlParams.get('search');
        if (searchTermFromURL && searchInput) {
            searchInput.value = decodeURIComponent(searchTermFromURL);
        }
        
        await updateDisplay();
    }

    initializePage();
}