/**
 * Função para buscar e exibir o nome do utilizador logado no header.
 * Requer que o elemento HTML com o ID 'userNameDisplay' exista na página.
 */

import { API_BASE_URL } from '../config.js';

export async function updateUserHeader() {
  const userNameDisplay = document.getElementById('userNameDisplay');

  // 1. Obter o token de autenticação do localStorage
  const token = localStorage.getItem('jwtToken');

  // Se não houver token, o utilizador não está logado. Paramos a execução.
  if (!token) {
    console.error("Token de autenticação não encontrado. O utilizador não está logado.");
    if (userNameDisplay) {
        userNameDisplay.textContent = 'Olá, Visitante';
    }
    return;
  }

  try {
    // 2. Fazer a requisição GET para a API
    const response = await fetch(API_BASE_URL + '/user', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}` // Incluímos o token no cabeçalho
      }
    });

    // 3. Tratar a resposta da API
    if (response.ok) { // Se a resposta for um sucesso (código 200-299)
      const userData = await response.json();
      
      // Verificamos se o elemento existe na página antes de tentar atualizar.
      if (userNameDisplay) {
        // 4. Atualizar o texto do elemento com o nome do utilizador
        userNameDisplay.textContent = `Olá, ${userData.nome}`;
      }
    } else {
      // Caso a resposta não seja um sucesso (ex: 401 Unauthorized)
      console.error(`Falha ao buscar dados do utilizador. Status: ${response.status}`);
      if (userNameDisplay) {
        userNameDisplay.textContent = 'Erro ao carregar';
      }
    }

  } catch (error) {
    // 5. Tratar erros de rede ou outros problemas
    console.error('Ocorreu um erro na requisição:', error);
    if (userNameDisplay) {
        userNameDisplay.textContent = 'Erro de conexão';
    }
  }
}

// Nova função para preencher o modal com os dados do utilizador
async function populateProfileModal() {
  const userNameElement = document.getElementById('profileUserName');
  const userEmailElement = document.getElementById('profileUserEmail');

  const token = localStorage.getItem('jwtToken');
  if (!token) {
    userNameElement.textContent = 'Erro';
    userEmailElement.textContent = 'Não autenticado';
    return;
  }

  try {
    const response = await fetch(API_BASE_URL + '/user', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (response.ok) {
      const userData = await response.json();
      if (userNameElement) {
        userNameElement.textContent = userData.nome;
      }
      if (userEmailElement) {
        userEmailElement.textContent = userData.email;
      }
    } else {
      userNameElement.textContent = 'Erro ao carregar';
      userEmailElement.textContent = 'Verifique a conexão';
      console.error(`Erro ao buscar dados do perfil: ${response.status}`);
    }
  } catch (error) {
    userNameElement.textContent = 'Erro de rede';
    userEmailElement.textContent = '';
    console.error('Ocorreu um erro na requisição:', error);
  }
}

/**
 * Funçao principal para carregar e exibir o modal de perfil.
 */
export async function loadAndShowProfileModal() {
    const profileModalContent = document.getElementById('profileModalContent');
    const profileModal = new bootstrap.Modal(document.getElementById('profileModal'));

    try {
        const response = await fetch('../components/profile.html');
        if (!response.ok) {
            throw new Error('Falha ao carregar o ficheiro de perfil.');
        }
        const profileHTML = await response.text();

        // 1. Injeta o HTML no modal
        profileModalContent.innerHTML = profileHTML;
        
        // 2. Chama a nova função para preencher os dados
        await populateProfileModal();

        // 3. Abre o modal
        profileModal.show();

    } catch (error) {
        console.error('Ocorreu um erro ao carregar o perfil:', error);
    }
}