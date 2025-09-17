export async function loadFooter() {
  const footer = document.getElementById("footer");
  const response = await fetch("../components/footerLogado.html");
  const html = await response.text();
  footer.innerHTML = html;
}
