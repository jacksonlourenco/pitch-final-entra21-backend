# 🛒 CheckBuy Project

branch final: https://github.com/jacksonlourenco/pitch-final-entra21-backend/tree/apresentacao-pitch

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/language-Java%2017+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-success)
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Version](https://img.shields.io/badge/version-1.0.0-brightgreen.svg)

> Uma solução moderna e inteligente para comparação de preços entre supermercados e otimização de listas de compras.

---

## 📑 Tabela de Conteúdos

- [📌 Sobre o Projeto](#-sobre-o-projeto)
- [🚀 Funcionalidades](#-funcionalidades)
- [🧪 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [⚙️ Arquitetura](#️-arquitetura)
- [🧰 Pré-requisitos](#-pré-requisitos)
- [📦 Instalação](#-instalação)
- [▶️ Como Executar](#️-como-executar)
- [📬 Endpoints Principais](#-endpoints-principais)
- [💡 Contribuição](#-contribuição)
- [🔐 Autenticação](#-autenticação)
- [📤 Deploy](#-deploy)
- [📄 Licença](#-licença)

---

## 📌 Sobre o Projeto

**CheckBuy** é um sistema Full Stack desenvolvido em **Java com Spring Boot** no back-end. Ele permite ao usuário montar listas de compras e, com base em um scraping de preços de supermercados reais, calcula onde o total da compra sairá mais barato.

O sistema tem como objetivo ajudar consumidores a **economizar tempo e dinheiro**, oferecendo:
- Comparação de preços entre mercados.
- Montagem de listas personalizadas.
- Histórico de compras.
- Cadastro e autenticação de usuários via JWT.
- Interface amigável e moderna (via front-end acoplado).

---

## 🚀 Funcionalidades

- ✅ Autenticação com JWT (Login e Registro de usuários)
- ✅ Integração com **Selenium** para scraping dos sites dos mercados (ex: Betano - Dragon & Tiger)
- ✅ Cadastro de produtos com categorias e preços por mercado
- ✅ Montagem e gerenciamento de listas de compras
- ✅ Sugestão do supermercado mais barato com base nos itens da lista
- ✅ Envio de e-mails transacionais via SMTP Gmail
- ✅ Versionamento de banco de dados com Flyway
- ✅ Organização modular com pacotes bem definidos (`controller`, `service`, `repository`, `dto`, `config`)
- ✅ Ocultação de produtos com `id = 1`
- ✅ Paginação e ordenação de resultados
- ✅ Filtros por mercado, categoria e disponibilidade
- ✅ Layouts modernos com Bootstrap e Tailwind (no front-end)
- ✅ Front-end modularizado com placeholders de carregamento (skeleton loaders)

---

## 🧪 Tecnologias Utilizadas

### 🔧 Back-end

- Java 17
- Spring Boot 3.5.4
- Maven
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- Flyway
- Selenium WebDriver
- Lombok
- MySQL
- Gmail SMTP

### 🌐 Front-end *(separado)*

- HTML5, CSS3
- Bootstrap 5 + Tailwind CSS
- JavaScript moderno
- Axios

### 📦 Outros

- Docker (opcional)
- Render (para deploy futuro)
- Git + GitHub

---

## ⚙️ Arquitetura

```
Controller ─▶ Service ─▶ Repository ─▶ Database
              │
              └─▶ External APIs (Selenium)
              
AuthController       -> login, registro (JWT)
ProductController    -> produtos e preços
ListController       -> gerenciamento de listas
ScrapingService      -> coleta de preços online
EmailService         -> envio de e-mails (SMTP)
```

---

## 🧰 Pré-requisitos

- Java 17+
- Maven
- Git
- MySQL 8+
- Docker (opcional)
- Navegador Firefox (usado pelo Selenium)
- Conta Google (para SMTP)

---

## 📦 Instalação

```bash
git clone https://github.com/seu-usuario/checkbuy_project.git
cd checkbuy_project
```

Instale as dependências:

```bash
mvn clean install
```

Configure o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/checkbuy
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=validate

jwt.secret=segredoJWTseguro123

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_de_aplicativo
```

---

## ▶️ Como Executar

```bash
mvn spring-boot:run
```

---

## 📬 Endpoints Principais

> Todos os endpoints exigem token JWT, exceto o de login e cadastro.

### 🔐 Autenticação

```http
POST /api/auth/register
POST /api/auth/login
```

### 🛍 Produtos

```http
GET /api/products
GET /api/products?categoria=alimento&mercado=mercado1
POST /api/products
PUT /api/products/{id}
DELETE /api/products/{id}
```

### 📋 Listas

```http
GET /api/lists
POST /api/lists
PUT /api/lists/{id}
DELETE /api/lists/{id}
```

### 📉 Comparação de Preços

```http
GET /api/compare?listaId=1
```

### 🔄 Scraping (interno)

```java
scrapingService.buscarNovosPrecos(); // Automatizado via Selenium
```

---

## 💡 Contribuição

Contribuições são bem-vindas!

1. Fork o repositório.
2. Crie uma branch: `git checkout -b minha-feature`
3. Commit: `git commit -m 'feat: adiciona nova feature'`
4. Push: `git push origin minha-feature`
5. Abra um Pull Request.

---

## 🔐 Autenticação

O sistema utiliza **JWT (JSON Web Token)** para autenticação stateless. Após o login, um token é retornado e deve ser incluído no header:

```
Authorization: Bearer {seu_token}
```

---

## 📤 Deploy

Deploy futuro será realizado via:

- Plataforma: [Render](https://render.com)
- Banco: MySQL na nuvem (ou Docker local)
- Variáveis sensíveis protegidas via `application.properties` ou `.env`

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
