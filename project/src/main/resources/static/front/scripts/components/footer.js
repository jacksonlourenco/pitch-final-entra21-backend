export async function loadFooter() {
  const footer = document.getElementById("footer");
  const response = await fetch("components/footer.html");
  const html = await response.text();
  footer.innerHTML = html;
}
