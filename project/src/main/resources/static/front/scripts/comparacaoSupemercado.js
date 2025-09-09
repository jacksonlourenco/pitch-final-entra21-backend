import { API_BASE_URL } from './config.js';


// A palavra 'export' no início torna esta função disponível para ser importada por outros arquivos.
export function initComparisonPage() {
    // --- Constantes, Variáveis e Funções de Dados ---
    const marketCardsContainer = document.getElementById('market-cards-container');
    const showAllContainer = document.getElementById('show-all-container');
    const loadingSpinner = document.getElementById('loading-spinner');
    const resultsContent = document.getElementById('results-content');
    const viewAllOptionsBtn = document.getElementById('viewAllOptionsBtn');
    const TOP_N_RESULTS = 3;
    let listItemsData = [], productRefsData = [];
    let allChainsData = [];
    let allBranchesData = [];
    let isShowingAll = false;
    let comparisonModalInstance = null;
    let mobileDetailsModalInstance = null;
    
    function getToken() { return localStorage.getItem('jwtToken'); }
    
    function formatPrice(price) { if (price === null || typeof price === 'undefined') return '-'; return `R$${price.toFixed(2).replace('.', ',')}`; }
    
    async function fetchData(endpoint) { const token = getToken(); if (!token) { console.error('Token JWT não encontrado.'); return null; } try { const response = await fetch(`${API_BASE_URL}${endpoint}`, { headers: { 'Authorization': `Bearer ${token}` } }); if (!response.ok) throw new Error(`Erro: ${response.statusText}`); return await response.json(); } catch (error) { console.error(`Falha ao buscar ${endpoint}:`, error); return null; } }
    
    async function fetchComparisonData(listId) { const listItems = await fetchData(`/listas/${listId}`); if (!listItems || listItems.length === 0) { throw new Error("A lista está vazia ou não foi encontrada."); } const offerPromises = listItems.map(item => fetchData(`/produtos/scraping/melhores-ofertas/${item.produtoId}`)); const productRefPromises = listItems.map(item => fetchData(`/produtos/referencias/${item.produtoId}`)); const [allOffersByProduct, productRefs] = await Promise.all([Promise.all(offerPromises), Promise.all(productRefPromises)]); return { listItems, allOffersByProduct, productRefs }; }
    
    function aggregateDataForAllBranches(listItems, allOffersByProduct, productRefs) { const marketData = {}; const createMarketKey = (offer) => offer.unidadeNome ? `${offer.estabelecimentoNome} - ${offer.unidadeNome}` : offer.estabelecimentoNome; allOffersByProduct.forEach(offers => { if (!offers) return; offers.forEach(offer => { const marketKey = createMarketKey(offer); if (!marketData[marketKey]) { marketData[marketKey] = { key: marketKey, total: 0, products: {}, missingProductsCount: 0, missingProductsList: [], name: offer.estabelecimentoNome, unit: offer.unidadeNome || 'Unidade Principal' }; } }); }); listItems.forEach((item, index) => { const productOffers = allOffersByProduct[index] || []; const productRef = productRefs[index]; if (!productRef) return; for (const marketKey in marketData) { const offerInThisMarket = productOffers.find(o => createMarketKey(o) === marketKey); if (offerInThisMarket) { const price = offerInThisMarket.precoEspecial < offerInThisMarket.preco ? offerInThisMarket.precoEspecial : offerInThisMarket.preco; marketData[marketKey].total += price * item.quantidade; marketData[marketKey].products[item.produtoId] = { price, name: productRef.nome, quantity: item.quantidade }; } else { marketData[marketKey].missingProductsCount++; marketData[marketKey].missingProductsList.push(productRef.nome); marketData[marketKey].products[item.produtoId] = { price: null, name: productRef.nome, quantity: item.quantidade }; } } }); return Object.values(marketData); }
    
    function aggregateDataForTopChains(branchesData) { const bestOfEachChain = {}; branchesData.forEach(branch => { const chainName = branch.name; const existingBest = bestOfEachChain[chainName]; if (!existingBest || branch.missingProductsCount < existingBest.missingProductsCount || (branch.missingProductsCount === existingBest.missingProductsCount && branch.total < existingBest.total)) { bestOfEachChain[chainName] = branch; } }); const topChains = Object.values(bestOfEachChain); topChains.sort((a, b) => { if (a.missingProductsCount !== b.missingProductsCount) return a.missingProductsCount - b.missingProductsCount; return a.total - b.total; }); return topChains; }
    
    function renderSummaryCards() { const dataToShow = isShowingAll ? allChainsData : allChainsData.slice(0, TOP_N_RESULTS); if (dataToShow.length === 0) { marketCardsContainer.innerHTML = `<div class="alert alert-info">Nenhuma oferta encontrada para os itens da sua lista.</div>`; showAllContainer.innerHTML = ''; return; } const cardsHtml = dataToShow.map((data, index) => { const isBest = index === 0; const unitDisplay = data.unit ? `<small class="text-muted">Melhor unidade: ${data.unit}</small>` : ''; const marketDisplayName = `${data.name}`; let missingItemsAlert = ''; if (data.missingProductsCount > 0) { const tooltipTitle = `Produtos Faltantes:\n- ${data.missingProductsList.join('\n- ')}`; missingItemsAlert = `<div class="missing-items-alert" data-bs-toggle="tooltip" data-bs-placement="bottom" title="${tooltipTitle}"><i class="bi bi-exclamation-triangle-fill"></i> Faltam ${data.missingProductsCount} ${data.missingProductsCount === 1 ? 'item' : 'itens'}</div>`; } let columnClass = ''; if (dataToShow.length === 2) { columnClass = 'col-6 col-md-4 col-lg-4'; } else { if (index === 0) { columnClass = 'col-12 col-md-4 col-lg-4'; } else if (index === 1 || index === 2) { columnClass = 'col-6 col-md-4 col-lg-4'; } else { columnClass = 'col-12 col-md-4 col-lg-4'; } } return `<div class="${columnClass} mb-4"><div class="market-card clickable ${isBest ? 'highlight' : ''}" data-market-key="${data.key}"><h5>${marketDisplayName} <span class="badge ${isBest ? 'bg-success' : 'bg-secondary'}">${index + 1}º</span></h5><div class="price">${formatPrice(data.total)}</div><p class="mt-2 mb-0">${unitDisplay}</p>${missingItemsAlert}</div></div>`; }).join(''); marketCardsContainer.innerHTML = `<div class="row justify-content-center">${cardsHtml}</div>`; const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');[...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl)); if (allChainsData.length > TOP_N_RESULTS) { const buttonText = isShowingAll ? 'Mostrar apenas o Top 3' : `Mostrar ranking completo (${allChainsData.length} redes)`; showAllContainer.innerHTML = `<button id="showAllBtn" class="btn btn-outline-primary">${buttonText}</button>`; } else { showAllContainer.innerHTML = ''; } }

    function getMarketDetailsHTML(marketKey) {
        const market = allBranchesData.find(b => b.key === marketKey);
        if (!market) return '<p class="text-danger">Erro: Supermercado não encontrado.</p>';
        let html = `<h5>Supermercado: <strong>${market.key}</strong></h5><div class="alert alert-success text-center p-2 mt-3 mb-4"><strong>Total da Compra: ${formatPrice(market.total)}</strong></div>`;
        const availableProducts = [], missingProducts = [];
        listItemsData.forEach(item => { const productData = market.products[item.produtoId]; if (productData && productData.price !== null) { availableProducts.push({ ...item, name: productData.name, unitPrice: productData.price, totalPrice: productData.price * item.quantidade }); } else { missingProducts.push({ ...item, name: (productRefsData.find(p => p && p.id === item.produtoId))?.nome || 'Produto' }); } });
        if (availableProducts.length > 0) { availableProducts.sort((a, b) => b.totalPrice - a.totalPrice); html += `<h6 class="details-section-title">Produtos Encontrados (${availableProducts.length})</h6>`; html += availableProducts.map(p => `<div class="product-detail-item item-encontrado"><div class="product-info"><i class="bi bi-check-circle-fill"></i><span>${p.quantidade}x ${p.name}</span></div><div class="product-pricing">${formatPrice(p.unitPrice)}/un. | <b>${formatPrice(p.totalPrice)}</b></div></div>`).join(''); }
        if (missingProducts.length > 0) { html += `<h6 class="details-section-title">Produtos Faltantes (${missingProducts.length})</h6>`; html += missingProducts.map(p => `<div class="product-detail-item item-faltante"><div class="product-info"><i class="bi bi-x-circle-fill"></i><span class="product-name">${p.quantidade}x ${p.name}</span></div></div>`).join(''); }
        return html;
    }

    function renderComparisonTable() {
        const tableBody = document.getElementById('comparison-table-body');
        allBranchesData.sort((a, b) => { if (a.missingProductsCount !== b.missingProductsCount) return a.missingProductsCount - b.missingProductsCount; return a.total - b.total; });
        tableBody.innerHTML = allBranchesData.map((branch, index) => { const missingBadge = branch.missingProductsCount > 0 ? `<span class="badge bg-danger rounded-pill">${branch.missingProductsCount}</span>` : `<span class="badge bg-success rounded-pill">0</span>`; return `<tr class="main-row" data-market-key="${branch.key}"><th scope="row">${index + 1}º</th><td>${branch.key}</td><td><strong>${formatPrice(branch.total)}</strong></td><td>${missingBadge}</td><td><button class="btn btn-outline-primary btn-sm view-details-btn"><span class="d-none d-lg-inline">Detalhes</span><span class="d-lg-none">Detalhes</span></button></td></tr>`; }).join('');
    }

    // --- Lógica Principal e Event Listeners ---
    async function main() {
        try {
            const urlParams = new URLSearchParams(window.location.search);
            const listId = urlParams.get('listId');
            if (!listId) throw new Error("ID da lista não fornecido na URL.");

            const { listItems, allOffersByProduct, productRefs } = await fetchComparisonData(listId);
            listItemsData = listItems;
            productRefsData = productRefs;
            allBranchesData = aggregateDataForAllBranches(listItems, allOffersByProduct, productRefs);
            allChainsData = aggregateDataForTopChains(allBranchesData);

            loadingSpinner.classList.add('d-none');
            resultsContent.classList.remove('d-none');

            renderSummaryCards();
            comparisonModalInstance = new bootstrap.Modal(document.getElementById('comparisonModal'));
            mobileDetailsModalInstance = new bootstrap.Modal(document.getElementById('mobileDetailsModal'));
        } catch (error) {
            loadingSpinner.innerHTML = `<div class="alert alert-danger">${error.message}</div>`;
        }
    }

    showAllContainer.addEventListener('click', (e) => { if (e.target.id === 'showAllBtn') { isShowingAll = !isShowingAll; renderSummaryCards(); } });
    
    marketCardsContainer.addEventListener('click', (e) => {
        const card = e.target.closest('.clickable');
        if (!card) return;
        const marketKey = card.dataset.marketKey;
        renderComparisonTable();
        document.getElementById('comparison-details-content').innerHTML = getMarketDetailsHTML(marketKey);
        comparisonModalInstance.show();
        setTimeout(() => {
            const row = document.querySelector(`.main-row[data-market-key="${marketKey}"]`);
            if (row) row.classList.add('table-active');
        }, 200);
    });

    viewAllOptionsBtn.addEventListener('click', () => {
        renderComparisonTable();
        document.getElementById('comparison-details-content').innerHTML = `<div class="text-center text-muted mt-5"><i class="bi bi-card-list fs-1"></i><p class="mt-2">Selecione um mercado na tabela para ver a lista de produtos.</p></div>`;
        comparisonModalInstance.show();
    });

    document.getElementById('comparison-table-body').addEventListener('click', (event) => {
        const targetButton = event.target.closest('.view-details-btn');
        if (!targetButton) return;
        const mainRow = targetButton.closest('.main-row');
        const marketKey = mainRow.dataset.marketKey;
        if (window.innerWidth >= 992) {
            document.querySelectorAll('.comparison-table tbody .main-row').forEach(r => r.classList.remove('table-active'));
            mainRow.classList.add('table-active');
        }
        if (window.innerWidth < 992) {
            const detailsHTML = getMarketDetailsHTML(marketKey);
            document.getElementById('mobileDetailsModalBody').innerHTML = detailsHTML;
            mobileDetailsModalInstance.show();
        } else {
            document.getElementById('comparison-details-content').innerHTML = getMarketDetailsHTML(marketKey);
        }
    });

    // Inicia a execução de toda a lógica da página
    main();
}