export async function loadHeader() {
  const header = document.getElementById("header");
  const response = await fetch("components/header.html");
  const html = await response.text();
  header.innerHTML = html;
}
