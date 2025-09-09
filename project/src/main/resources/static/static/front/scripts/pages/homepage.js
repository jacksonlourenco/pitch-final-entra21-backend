import { loadHeader } from '../components/header.js';
import { loadFooter } from '../components/footer.js';
import { initAuth } from '../loginAndRegister.js';
import '../components/headerLinkActive.js';


document.addEventListener("DOMContentLoaded", async () => {
  await loadHeader();
  await loadFooter();
  await initAuth();
});
