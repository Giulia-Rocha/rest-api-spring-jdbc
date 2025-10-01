
# API de Gestão Acadêmica - Spring Boot

API RESTful desenvolvida com Spring Boot e JDBC para gerenciar entidades de um sistema acadêmico, como Alunos e Professores. O projeto utiliza uma conexão direta com um banco de dados Oracle via JDBC, demonstrando uma arquitetura em camadas (Controller -> Service -> Repository).

## ✨ Funcionalidades

* **CRUD de Alunos**: Operações completas de Criar, Ler, Atualizar e Deletar para a entidade Aluno.
* **CRUD de Professores**: Operações completas de Criar, Ler, Atualizar e Deletar para a entidade Professor.
* **Conexão Direta com Banco Oracle**: Utiliza JDBC puro para manipulação dos dados, sem o uso de um ORM como o Hibernate.
* **Validações de Dados**: A camada de serviço contém validações para garantir a integridade dos dados antes da persistência.
* **Arquitetura em Camadas**: O código é organizado seguindo as melhores práticas, com separação clara de responsabilidades entre Controllers, Services e Repositories.

## 🛠️ Tecnologias Utilizadas

* **Java 21**: Versão da linguagem Java utilizada no projeto.
* **Spring Boot 3.5.6**: Framework principal para a criação da API REST.
* **Apache Maven**: Gerenciador de dependências e build do projeto.
* **JDBC (Oracle Driver ojdbc11)**: Para a conexão e manipulação do banco de dados Oracle.
* **Project Lombok**: Para reduzir código boilerplate nos modelos (getters, setters, construtores).

## 📋 Pré-requisitos

* **JDK 21** ou superior instalado.
* **Apache Maven** instalado e configurado no PATH do sistema.
* Acesso a um banco de dados **Oracle**.
* Uma ferramenta para testar APIs, como **Postman** ou **Insomnia**.

## ⚙️ Configuração

1.  **Clone o repositório:**

    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd rest-api-spring-jdbc
    ```

2.  **Configure o Banco de Dados:**

    * Execute o script `script/script.sql` no seu banco de dados Oracle para criar as tabelas `ALUNO` e `PROFESSOR` e povoá-las com dados iniciais.

3.  **Configure a Conexão:**

    * Abra o arquivo `src/main/resources/application.properties`.
    * Altere as propriedades `db.url`, `db.user` e `db.password` com as credenciais do seu banco de dados Oracle.

    <!-- end list -->

    ```properties
    spring.application.name=restapi
    db.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
    db.user=SEU_USUARIO
    db.password=SUA_SENHA
    ```

    * **Nota**: O projeto também está configurado para ler estas credenciais de variáveis de ambiente (`DB_URL`, `DB_USER`, `DB_PASSWORD`), o que é uma prática recomendada para ambientes de produção.

## ▶️ Executando a Aplicação

Para executar a aplicação, utilize o Maven Wrapper incluído no projeto:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints da API

### Alunos (`/api/alunos`)

* **`GET /api/alunos`**: Lista todos os alunos.
* **`GET /api/alunos/{id}`**: Busca um aluno pelo ID.
* **`POST /api/alunos`**: Cria um novo aluno.
    * **Body (JSON):**
      ```json
      {
          "nome": "Nome do Aluno",
          "curso": "Nome do Curso"
      }
      ```
* **`PUT /api/alunos/{id}`**: Atualiza um aluno existente.
    * **Body (JSON):**
      ```json
      {
          "nome": "Novo Nome do Aluno",
          "curso": "Novo Nome do Curso"
      }
      ```
* **`DELETE /api/alunos/{id}`**: Deleta um aluno pelo ID.

### Professores (`/api/professores`)

* **`GET /api/professores`**: Lista todos os professores.
* **`GET /api/professores/{id}`**: Busca um professor pelo ID.
* **`POST /api/professores`**: Cria um novo professor.
    * **Body (JSON):**
      ```json
      {
          "nome": "Nome do Professor",
          "departamento": "Departamento",
          "email": "email@dominio.com",
          "titulacao": "Mestre"
      }
      ```
* **`PUT /api/professores/{id}`**: Atualiza um professor existente.
    * **Body (JSON):**
      ```json
      {
          "nome": "Novo Nome",
          "departamento": "Novo Departamento",
          "email": "novoemail@dominio.com",
          "titulacao": "Doutor"
      }
      ```
* **`DELETE /api/professores/{id}`**: Deleta um professor pelo ID.