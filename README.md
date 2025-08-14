# Gerenciador de Postagens 📊

Aplicação backend desenvolvida como trabalho final da disciplina de Banco de Dados, simulando o núcleo de uma rede social e explorando ao máximo a lógica de negócio dentro do PostgreSQL.

## Visão Geral
O sistema gerencia usuários, postagens, comentários e curtidas. O diferencial é a arquitetura database-centric: grande parte das regras, cálculos e automações acontece direto no banco, via Stored Procedures, Functions e Triggers.

A aplicação Java (Spring Boot) funciona como camada de serviço, expondo essa lógica para um menu de console interativo.

---

###  Funcionalidades ✨
CRUD completo para usuários e postagens.

Sistema de curtidas e comentários.

Lógica no banco:

- Stored Procedures: criação de posts e comentários.

- Triggers: atualização automática de contadores.

- Functions: cálculos e relatórios, como ranking de usuários.

Análise de dados: ranking de atividade com base em posts, curtidas e comentários.

Interface de cliente: menu no console para operar todas as funcionalidades.

---

### Tecnologias
- Linguagem: Java 21

- Framework: Spring Boot

- Banco: PostgreSQL

Acesso a dados: Spring JDBC (JdbcTemplate)

Build: Maven

---

### Estrutura do Banco:
O banco foi modelado para suportar as interações de uma rede social, com tabelas relacionadas e regras de negócio encapsuladas no próprio SGBD.

### Como Rodar?
Pré-requisitos:
- Java JDK 21+
- Maven 3+
- PostgreSQL

---

### 1. Configurar o Banco:

1.1 Abra o pgAdmin (ou cliente PostgreSQL).

1.2 Crie o banco: gerenciador_db.

1.3 Abra a Query Tool.

1.4 Execute o script script_banco.sql para criar tabelas, procedures, functions e triggers.

---

### 2. Configurar a Aplicação
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

- Edite src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha_secreta

---

### 3. Executar
- Compile e rode:

#####./mvnw clean install
#####java -jar target/gerenciador-redes-sociais-0.0.1-SNAPSHOT.jar

O Spring Boot inicia o servidor e o menu de console (implementado com CommandLineRunner) aparece automaticamente para uso
