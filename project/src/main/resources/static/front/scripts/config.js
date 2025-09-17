// src/scripts/config.js

// Pega protocolo + host + porta da URL atual
const { protocol, hostname, port } = window.location;

// Monta a URL base da API
export const API_BASE_URL = `${protocol}//${hostname}${port ? `:${port}` : ''}`;
//export const API_BASE_URL = `http://localhost:8080`;
