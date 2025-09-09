import { loadHeader } from '../components/headerLogado.js';
import { loadFooter } from '../components/footerLogado.js';
import {initCatalogPage} from '../populeProdutos.js';
import { updateUserHeader, loadAndShowProfileModal } from '../components/headerUser.js'; 
import { showMyListsModal } from '../components/lists-modal.js';    


document.addEventListener("DOMContentLoaded", async () => {
  await loadHeader();
  await loadFooter();
  updateUserHeader();
  initCatalogPage();

   // Perfil
  const profileLink = document.getElementById('profile-link');
  if (profileLink) {
    profileLink.addEventListener('click', (e) => {
      e.preventDefault();       // previne o comportamento padrão do link
      loadAndShowProfileModal(); // abre o modal de perfil
    });
  }

  // Minhas listas
  const myListsLink = document.getElementById('my-lists-link');
  if (myListsLink) {
    myListsLink.addEventListener('click', (e) => {
      e.preventDefault();
      showMyListsModal();       // abre o modal de listas
    });
  }
  
});
