import { API_BASE_URL } from './config.js';

export function initProductDetailPage() {
  const urlParams = new URLSearchParams(window.location.search);
  const MAIN_PRODUCT_ID = urlParams.get('id');

  if (!MAIN_PRODUCT_ID) {
    document.querySelector('.container').innerHTML = `<div class="alert alert-danger text-center">ID do produto não especificado na URL.</div>`;
    return;
  }

  const storeLogos = {
    'Koch': '../images/koch-logo.png',
    'Bistek': '../images/bistek-logo.png',
    'Cooper': '../images/cooper-logo.png',
    'Unidade Mafisa': '../images/cooper-logo.png',
    'Unidade Vila Nova': '../images/cooper-logo.png',
    'Unidade Garcia': '../images/cooper-logo.png',
    'Unidade Itoupava Norte': '../images/cooper-logo.png',
    'Unidade Água Verde':'../images/cooper-logo.png',
    'Outros': '../images/default-logo.jpg'
  };

  // --- FUNÇÕES UTILITÁRIAS ---
  function getToken() {
    return localStorage.getItem('jwtToken');
  }

  async function fetchData(endpoint, method = 'GET', body = null) {
    const token = getToken();
    if (!token) {
      console.error('Token JWT não encontrado. O login é necessário.');
      return null;
    }
    const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
    const config = { method, headers };
    if (body) {
      config.body = JSON.stringify(body);
    }
    try {
      const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || response.statusText);
      }
      const contentType = response.headers.get('content-type');
      if (contentType && contentType.includes('application/json')) {
        return await response.json();
      }
      return response;
    } catch (error) {
      console.error(`Falha ao buscar dados de ${endpoint}:`, error);
      return null;
    }
  }

  function formatPrice(price) {
    if (price === null || isNaN(price)) return 'N/A';
    return `R$${price.toFixed(2).replace('.', ',')}`;
  }

  // --- FUNÇÕES DE RENDERIZAÇÃO DA PÁGINA ---
  function createPriceItemHtml(item, isBestPrice = false) {
    const price = (item.precoEspecial !== null && item.precoEspecial < item.preco) ? item.precoEspecial : item.preco;
    const name = item.unidade.nome;
    const logo = storeLogos[name] || storeLogos['Outros'];
    const priceHtml = isBestPrice
      ? `<h5 class="price-text text-success">${formatPrice(price)}</h5>`
      : `<h5><del class="strikethrough-price">${formatPrice(price)}</del></h5>`;

    return `
      <div class="price-item">
        <img src="${logo}" alt="${name} Logo">
        <div class="store-price">
          <p class="mb-0">${isBestPrice ? 'Melhor Preço' : name}</p>
          ${priceHtml}
        </div>
      </div>
    `;
  }

  function renderMainProduct(productData, scrapingData) {
    const mainProductName = document.getElementById('main-product-name');
    const mainProductDescription = document.getElementById('main-product-description');
    const mainProductImageContainer = document.getElementById('main-product-image-container');
    const priceListContainer = document.getElementById('price-list-container');
    const breadcrumbProductName = document.getElementById('breadcrumb-product-name');
    const savingsBadge = document.getElementById('savings-badge');

    mainProductImageContainer.innerHTML = '';
    priceListContainer.innerHTML = '';
    breadcrumbProductName.textContent = productData.nome;

    if (productData.urlImg) {
      mainProductImageContainer.innerHTML = `<div class="card-image-container"><img src="${productData.urlImg}" class="card-img-top" alt="${productData.nome}"></div>`;
    } else {
      mainProductImageContainer.innerHTML = `<div class="placeholder-box"></div>`;
    }

    mainProductName.textContent = productData.nome;
    mainProductDescription.textContent = productData.descricao || 'Produto sem descrição.';

    if (scrapingData && scrapingData.length > 0) {
      const allPrices = scrapingData.map(item =>
        (item.precoEspecial !== null && item.precoEspecial > 0 && item.precoEspecial < item.preco) ? item.precoEspecial : item.preco
      ).filter(price => price !== null && !isNaN(price));

      if (allPrices.length >= 2) {
        const lowestPrice = Math.min(...allPrices);
        const highestPrice = Math.max(...allPrices);
        const savings = ((highestPrice - lowestPrice) / highestPrice) * 100;
        savingsBadge.textContent = `${savings.toFixed(0)}% de economia`;
        savingsBadge.style.display = 'inline-block';
      } else {
        savingsBadge.style.display = 'none';
      }

      scrapingData.sort((a, b) => {
        const priceA = (a.precoEspecial !== null && a.precoEspecial < a.preco) ? a.precoEspecial : a.preco;
        const priceB = (b.precoEspecial !== null && b.precoEspecial < b.preco) ? b.precoEspecial : b.preco;
        return priceA - priceB;
      });

      const pricesToShow = scrapingData.slice(0, 3);
      const pricesToHide = scrapingData.slice(3);

      if (pricesToShow.length > 0) {
        priceListContainer.innerHTML += createPriceItemHtml(pricesToShow[0], true);
      }

      pricesToShow.slice(1).forEach(item => {
        priceListContainer.innerHTML += createPriceItemHtml(item, false);
      });

      if (pricesToHide.length > 0) {
        const hiddenPricesHtml = pricesToHide.map(item => createPriceItemHtml(item, false)).join('');
        priceListContainer.innerHTML += `
          <div id="hidden-prices" class="hidden-prices">
            ${hiddenPricesHtml}
          </div>
          <button id="toggle-prices-btn" class="btn btn-link p-0">Ver mais preços</button>
        `;
        document.getElementById('toggle-prices-btn').addEventListener('click', (event) => {
          const hiddenPricesDiv = document.getElementById('hidden-prices');
          const isHidden = hiddenPricesDiv.classList.toggle('hidden-prices');
          event.target.textContent = isHidden ? 'Ver mais preços' : 'Ver menos preços';
        });
      }
    } else {
      priceListContainer.innerHTML = `<p class="text-danger">Nenhum preço disponível.</p>`;
    }
  }

  async function renderSimilarProducts(products) {
    const similarProductsGrid = document.getElementById('similar-products-grid');
    similarProductsGrid.innerHTML = '';

    await Promise.all(products.map(async (product) => {
      const scrapingData = await fetchData(`/produtos/scraping/${product.id}?page=0&size=5`);

      let prices = [];
      if (scrapingData && scrapingData.content.length > 0) {
        prices = scrapingData.content.map(item =>
          (item.precoEspecial !== null && item.precoEspecial < item.preco) ? item.precoEspecial : item.preco
        );
        prices.sort((a, b) => a - b);
      }

      const cardHTML = `
        <div class="col-md-4 mb-4">
          <a href="detalhes-produto.html?id=${product.id}" class="text-decoration-none text-dark">
            <div class="card similar-product-card h-100 green-border rounded-0">
              <div class="card-image-container">
                <img src="${product.urlImg || 'https://placehold.co/150x150'}" class="card-img-top" alt="${product.nome}">
              </div>
              <div class="card-body p-3">
                <h6 class="card-title mb-1">${product.nome}</h6>
                <small class="text-muted">Melhor preço</small>
                <div class="d-flex gap-2 mt-2">
                  <span class="btn btn-sm btn-success rounded-1">${formatPrice(prices[0])}</span>
                  <span class="btn btn-sm btn-custom-danger rounded-1"><del>${formatPrice(prices[1])}</del></span>
                  <span class="btn btn-sm btn-custom-danger rounded-1"><del>${formatPrice(prices[2])}</del></span>
                </div>
              </div>
            </div>
          </a>
        </div>
      `;
      similarProductsGrid.insertAdjacentHTML('beforeend', cardHTML);
    }));
  }

  // --- LÓGICA DOS MODAIS E LISTAS ---
  
  // IDs com prefixo 'pd' para evitar conflitos
  const listsModalEl = document.getElementById('pdListsModal');
  const createListModalEl = document.getElementById('pdCreateListModal');
  const confirmModalEl = document.getElementById('pdConfirmModal');
  const notificationModalEl = document.getElementById('pdNotificationModal');

  if (!listsModalEl || !createListModalEl || !confirmModalEl || !notificationModalEl) {
    console.error("Um ou mais elementos de modal específicos da página de produto não foram encontrados. Verifique os IDs no HTML se eles têm o prefixo 'pd'.");
    return;
  }

  const listsModal = new bootstrap.Modal(listsModalEl);
  const createListModal = new bootstrap.Modal(createListModalEl);
  const confirmModal = new bootstrap.Modal(confirmModalEl);
  const notificationModal = new bootstrap.Modal(notificationModalEl);

  // Elementos internos dos modais, também com prefixo
  const modalContentContainer = document.getElementById('pdModalContentContainer');
  const listsModalLabel = document.getElementById('pdListsModalLabel');
  const backToListsBtn = document.getElementById('pdBackToListsBtn');
  const compareBtn = document.getElementById('pdCompareBtn');
  
  const productToAdd = { id: MAIN_PRODUCT_ID };
  let currentListViewState = 'lists';
  let currentListId = null;
  let currentListName = '';
  let confirmAction = null;

  document.getElementById('add-to-list-btn').addEventListener('click', async () => {
    const productRef = await fetchData(`/produtos/referencias/${MAIN_PRODUCT_ID}`);
    if (!productRef) {
      showNotification('Não foi possível obter os dados do produto.', 'danger');
      return;
    }
    productToAdd.nome = productRef.nome;
    productToAdd.urlImg = productRef.urlImg;

    await renderListsView();
    listsModal.show();
  });

  backToListsBtn.addEventListener('click', () => {
    renderListsView();
  });

  compareBtn.addEventListener('click', () => {
    if (currentListViewState === 'products' && currentListId) {
      window.location.href = `comparacao.html?listId=${currentListId}`;
    }
  });

  document.getElementById('pdSaveNewListBtn').addEventListener('click', async () => {
    const listNameInput = document.getElementById('pdNewListNameInput');
    const listName = listNameInput.value.trim();
    if (listName) {
      const payload = { nome: listName };
      const result = await fetchData('/listas', 'POST', payload);
      if (result) {
        showNotification('Nova lista criada com sucesso!');
        createListModal.hide();
        listNameInput.value = '';
        await renderListsView();
      } else {
        showNotification('Ocorreu um erro ao criar a nova lista.', 'danger');
      }
    } else {
      showNotification('O nome da lista não pode ser vazio.', 'warning');
    }
  });

  document.getElementById('pdConfirmActionBtn').addEventListener('click', () => {
    if (typeof confirmAction === 'function') {
      confirmAction();
    }
    confirmAction = null;
    confirmModal.hide();
  });

  async function addProductToList(listId, productId) {
    const payload = { produtoReferenciaId: parseInt(productId), quantidade: 1 };
    try {
      const result = await fetchData(`/listas/${listId}/produtos`, 'POST', payload);
      if (result) {
        showNotification(`Produto "${productToAdd.nome}" adicionado à lista com sucesso!`);
        return true;
      }
    } catch (error) {
      showNotification('Produto já existe na lista.', 'warning');
      return false;
    }
    return false;
  }

  async function renderListsView() {
    currentListViewState = 'lists';
    listsModalLabel.textContent = 'Minhas Listas';
    backToListsBtn.classList.add('d-none');
    compareBtn.classList.add('d-none');
    modalContentContainer.innerHTML = `<div class="text-center p-5"><div class="spinner-border text-primary" role="status"></div></div>`;

    const lists = await fetchData('/listas');
    let listsHtml = `<div class="row row-cols-1 row-cols-md-2 g-3">
                        <div class="col">
                          <div class="card h-100 text-center justify-content-center create-list-card" id="create-new-list-card" style="cursor: pointer;">
                            <div class="card-body">
                              <h5 class="card-title"><i class="bi bi-plus-circle-fill"></i> Criar Nova Lista</h5>
                            </div>
                          </div>
                        </div>`;

    if (lists && lists.length > 0) {
      const listContentsPromises = lists.map(list => fetchData(`/listas/${list.id}`));
      const listContents = await Promise.all(listContentsPromises);

      lists.forEach((list, index) => {
        const content = listContents[index];
        const isProductInList = content && content.some(item => item.produtoId == MAIN_PRODUCT_ID);
        const addButtonHtml = isProductInList
          ? `<button class="btn btn-sm btn-secondary mt-2" disabled><i class="bi bi-check-lg"></i> Adicionado</button>`
          : `<button class="btn btn-sm btn-success add-to-existing-list-btn mt-2" data-list-id="${list.id}">Adicionar a esta lista</button>`;

        listsHtml += `
          <div class="col">
            <div class="card h-100 list-card" data-list-id="${list.id}">
              <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                  <div>
                    <h5 class="card-title mb-1">${list.nome}</h5>
                    <small class="text-muted">Criada em ${new Date(list.dataCriacao).toLocaleDateString('pt-BR')}</small>
                  </div>
                  <button class="btn btn-sm btn-outline-danger delete-list-btn" data-list-id="${list.id}"><i class="bi bi-trash"></i></button>
                </div>
                <div class="mt-3">
                  ${addButtonHtml}
                  <button class="btn btn-sm btn-outline-primary view-list-btn mt-2" data-list-id="${list.id}">Ver produtos</button>
                </div>
              </div>
            </div>
          </div>`;
      });
    }

    listsHtml += `</div>`;
    modalContentContainer.innerHTML = listsHtml;
    addListHandlers();
  }

  async function renderProductsInListView(listId, listName) {
    currentListViewState = 'products';
    currentListId = listId;
    currentListName = listName;
    listsModalLabel.textContent = `Itens em: ${listName}`;
    backToListsBtn.classList.remove('d-none');
    compareBtn.classList.remove('d-none');

    modalContentContainer.innerHTML = `<div class="text-center p-5"><div class="spinner-border text-primary" role="status"></div></div>`;

    const listProducts = await fetchData(`/listas/${listId}`);

    if (!listProducts || listProducts.length === 0) {
      modalContentContainer.innerHTML = `<div class="p-5 text-center text-muted">Esta lista está vazia.</div>`;
      return;
    }

    const productRefsPromises = listProducts.map(item => fetchData(`/produtos/referencias/${item.produtoId}`));
    const productRefs = await Promise.all(productRefsPromises);

    let productsHtml = `<div class="row row-cols-1 row-cols-md-2 g-3">`;
    productRefs.forEach((product, index) => {
      const listProduct = listProducts[index];
      if (!product) return;
      productsHtml += `
          <div class="col">
            <div class="card h-100 list-item-card">
              <div class="delete-btn" data-list-id="${listId}" data-product-id="${product.id}" title="Remover da lista"><i class="bi bi-trash"></i></div>
              <div class="row g-0">
                <div class="col-4 d-flex align-items-center justify-content-center p-2">
                  <img src="${product.urlImg || 'https://placehold.co/150x150'}" class="img-fluid rounded-start" alt="${product.nome}" style="max-height: 100px; object-fit: contain;">
                </div>
                <div class="col-8">
                  <div class="card-body">
                    <h6 class="card-title">${product.nome}</h6>
                    <div class="input-group input-group-sm mt-2">
                      <button class="btn btn-outline-secondary btn-decrease" type="button" data-product-id="${product.id}" data-list-id="${listId}">-</button>
                      <span class="form-control text-center" id="quantity-${product.id}">${listProduct.quantidade}</span>
                      <button class="btn btn-outline-secondary btn-increase" type="button" data-product-id="${product.id}" data-list-id="${listId}">+</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>`;
    });
    productsHtml += `</div>`;
    modalContentContainer.innerHTML = productsHtml;
    addProductsInListHandlers();
  }

  function addListHandlers() {
    const createCard = document.getElementById('create-new-list-card');
    if (createCard) {
      createCard.addEventListener('click', () => {
        document.getElementById('pdNewListNameInput').value = '';
        createListModal.show();
      });
    }

    document.querySelectorAll('.add-to-existing-list-btn').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        e.stopPropagation();
        const listId = e.currentTarget.getAttribute('data-list-id');
        const success = await addProductToList(listId, productToAdd.id);
        if (success) {
          listsModal.hide();
        }
      });
    });

    document.querySelectorAll('.view-list-btn').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        e.stopPropagation();
        const listId = e.currentTarget.getAttribute('data-list-id');
        const listName = e.currentTarget.closest('.list-card').querySelector('.card-title').textContent;
        await renderProductsInListView(listId, listName);
      });
    });

    document.querySelectorAll('.delete-list-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        e.stopPropagation();
        const listId = e.currentTarget.getAttribute('data-list-id');
        const listCard = e.currentTarget.closest('.list-card');
        const listName = listCard.querySelector('.card-title').textContent;
        document.getElementById('pdConfirmMessage').textContent = `Tem certeza que deseja apagar a lista "${listName}"? Todos os produtos serão removidos.`;
        confirmAction = async () => {
          const result = await fetchData(`/listas/${listId}`, 'DELETE');
          if (result) {
            showNotification('Lista apagada com sucesso!');
            await renderListsView();
          } else {
            showNotification('Erro ao apagar a lista.', 'danger');
          }
        };
        confirmModal.show();
      });
    });
  }

  function addProductsInListHandlers() {
    document.querySelectorAll('.btn-decrease').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const { productId, listId } = e.currentTarget.dataset;
        const quantitySpan = document.getElementById(`quantity-${productId}`);
        let quantity = parseInt(quantitySpan.textContent);

        if (quantity > 1) {
          quantity--;
          const payload = { produtoReferenciaId: parseInt(productId), quantidade: quantity };
          const result = await fetchData(`/listas/${listId}/produtos`, 'PUT', payload);
          if (result) {
            await renderProductsInListView(listId, currentListName);
          } else {
            showNotification('Erro ao atualizar a quantidade.', 'danger');
          }
        } else {
          document.getElementById('pdConfirmMessage').textContent = 'A quantidade é 1. Deseja remover este produto da lista?';
          confirmAction = async () => {
            const result = await fetchData(`/listas/${listId}/produtos/${productId}`, 'DELETE');
            if (result) {
              showNotification('Produto removido da lista.');
              await renderProductsInListView(listId, currentListName);
            } else {
              showNotification('Erro ao remover o produto.', 'danger');
            }
          };
          confirmModal.show();
        }
      });
    });

    document.querySelectorAll('.btn-increase').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const { productId, listId } = e.currentTarget.dataset;
        const quantitySpan = document.getElementById(`quantity-${productId}`);
        let quantity = parseInt(quantitySpan.textContent);

        quantity++;
        const payload = { produtoReferenciaId: parseInt(productId), quantidade: quantity };
        const result = await fetchData(`/listas/${listId}/produtos`, 'PUT', payload);
        if (result) {
          await renderProductsInListView(listId, currentListName);
        } else {
          showNotification('Erro ao atualizar a quantidade.', 'danger');
        }
      });
    });

    document.querySelectorAll('.list-item-card .delete-btn').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        e.stopPropagation();
        const { listId, productId } = e.currentTarget.dataset;

        document.getElementById('pdConfirmMessage').textContent = 'Tem certeza que deseja remover este produto da lista?';
        confirmAction = async () => {
          const result = await fetchData(`/listas/${listId}/produtos/${productId}`, 'DELETE');
          if (result) {
            showNotification('Produto removido com sucesso.');
            await renderProductsInListView(listId, currentListName);
          } else {
            showNotification('Erro ao remover o produto.', 'danger');
          }
        };
        confirmModal.show();
      });
    });
  }

  function showNotification(message, type = 'success') {
    const modalTitle = document.getElementById('pdNotificationModalLabel');
    const modalMessage = document.getElementById('pdNotificationMessage');
    const modalHeader = notificationModalEl.querySelector('.modal-header');

    modalMessage.textContent = message;
    modalHeader.className = 'modal-header';

    switch (type) {
      case 'danger':
        modalTitle.textContent = 'Erro';
        modalHeader.classList.add('bg-danger', 'text-white');
        break;
      case 'warning':
        modalTitle.textContent = 'Atenção';
        modalHeader.classList.add('bg-warning', 'text-dark');
        break;
      default:
        modalTitle.textContent = 'Sucesso';
        modalHeader.classList.add('bg-success', 'text-white');
    }
    notificationModal.show();
  }

  // --- INICIALIZAÇÃO DA PÁGINA ---
  async function loadProductPage() {
    const mainProductPromise = fetchData(`/produtos/referencias/${MAIN_PRODUCT_ID}`);
    const mainScrapingPromise = fetchData(`/produtos/scraping/${MAIN_PRODUCT_ID}?page=0`);
    const allReferencesPromise = fetchData('/produtos/referencias/listar?page=0&size=5');

    const [mainProduct, mainScraping, allReferences] = await Promise.all([
      mainProductPromise,
      mainScrapingPromise,
      allReferencesPromise
    ]);

    if (mainProduct) {
      renderMainProduct(mainProduct, mainScraping.content);
    } else {
      document.querySelector('.container').innerHTML = `<div class="alert alert-danger text-center">Não foi possível carregar os detalhes do produto.</div>`;
    }

    if (allReferences && allReferences.content) {
      const similarProducts = allReferences.content.filter(p => p.id !== parseInt(MAIN_PRODUCT_ID) &&  p.id !== 1).slice(0, 3) ;
      await renderSimilarProducts(similarProducts);
    } else {
      document.getElementById('similar-products-grid').innerHTML = `<p class="text-center text-muted">Não foi possível carregar produtos similares.</p>`;
    }
  }

  loadProductPage();
}