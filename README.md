![Star Wars Characteres](background.jpg)

By: https://i.pinimg.com/736x/55/9b/eb/559beb42c00c7240e80f58e59595f12b.jpg

---

### README — Desafio Final Hackathon Orbitall 2025 (Channels)

Este projeto é um serviço REST em Spring Boot para gestão de clientes e transações, usando banco de dados em memória H2.

---

### Requisitos
- Java 21 (JDK 21)
- Maven 3.9+

Opcional para inspeção do banco:
- Console H2 habilitado em `/h2-console`

---

### Como executar a aplicação (Passo a passo)
1) Clonar o repositório e entrar no módulo do serviço
- O serviço está no diretório `channels/`.

2) Compilar e rodar com Maven
- Dentro do diretório `channels/` execute:
```
mvn spring-boot:run
```

3) Acessar a API
- Base URL padrão: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:channels`
    - Usuário e senha podem ficar em branco (padrão do H2 em memória, a não ser que você configure diferente).

4) Parar a aplicação
- Pressione `Ctrl + C` no terminal que está executando a aplicação.

Observação: Os dados são voláteis (H2 em memória). A cada reinício, a base é recriada.

---

### Configurações principais
Arquivo: `channels/src/main/resources/application.properties`
- `spring.h2.console.enabled=true`
- `spring.h2.console.path=/h2-console`
- `spring.datasource.url=jdbc:h2:mem:channels;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`

---

### Endpoints e exemplos (Postman/cURL)
A seguir, os recursos disponíveis com exemplos de requisições e respostas. Você pode usar o Postman criando uma requisição com os mesmos métodos, URLs, headers e bodies abaixo. Também incluímos comandos cURL equivalentes.

#### 1) Clientes (`/customers`)

- Criar cliente
    - Método/URL: `POST /customers`
    - Headers: `Content-Type: application/json`
    - Body (JSON):
      ```json
      {
        "fullName": "Maria da Silva",
        "email": "maria.silva@example.com",
        "phone": "+55 11 99999-0000"
      }
      ```
    - Resposta 201 (exemplo):
      ```json
      {
        "id": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
        "fullName": "Maria da Silva",
        "email": "maria.silva@example.com",
        "phone": "+55 11 99999-0000",
        "createdAt": "2025-09-20T12:00:00",
        "updatedAt": "2025-09-20T12:00:00",
        "active": true
      }
      ```
    - cURL:
      ```bash
      curl -X POST http://localhost:8080/customers \
        -H "Content-Type: application/json" \
        -d '{
          "fullName": "Maria da Silva",
          "email": "maria.silva@example.com",
          "phone": "+55 11 99999-0000"
        }'
      ```

- Buscar cliente por ID
    - Método/URL: `GET /customers/{id}`
    - Resposta 200 (exemplo):
      ```json
      {
        "id": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
        "fullName": "Maria da Silva",
        "email": "maria.silva@example.com",
        "phone": "+55 11 99999-0000",
        "createdAt": "2025-09-20T12:00:00",
        "updatedAt": "2025-09-20T12:10:00",
        "active": true
      }
      ```
    - cURL:
      ```bash
      curl http://localhost:8080/customers/8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1
      ```

- Listar todos os clientes
    - Método/URL: `GET /customers`
    - Resposta 200 (exemplo):
      ```json
      [
        {
          "id": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
          "fullName": "Maria da Silva",
          "email": "maria.silva@example.com",
          "phone": "+55 11 99999-0000",
          "createdAt": "2025-09-20T12:00:00",
          "updatedAt": "2025-09-20T12:10:00",
          "active": true
        }
      ]
      ```
    - cURL:
      ```bash
      curl http://localhost:8080/customers
      ```

- Atualizar cliente
    - Método/URL: `PUT /customers/{id}`
    - Headers: `Content-Type: application/json`
    - Body (JSON):
      ```json
      {
        "fullName": "Maria Souza",
        "email": "maria.souza@example.com",
        "phone": "+55 11 90000-1111"
      }
      ```
    - Resposta 200 (exemplo): estrutura igual ao `GET /customers/{id}` com campos atualizados.
    - cURL:
      ```bash
      curl -X PUT http://localhost:8080/customers/8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1 \
        -H "Content-Type: application/json" \
        -d '{
          "fullName": "Maria Souza",
          "email": "maria.souza@example.com",
          "phone": "+55 11 90000-1111"
        }'
      ```

- Deletar (soft delete) cliente
    - Método/URL: `DELETE /customers/{id}`
    - Resposta 200 (exemplo):
      ```json
      {
        "id": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
        "fullName": "Maria Souza",
        "email": "maria.souza@example.com",
        "phone": "+55 11 90000-1111",
        "createdAt": "2025-09-20T12:00:00",
        "updatedAt": "2025-09-20T12:15:00",
        "active": false
      }
      ```
    - cURL:
      ```bash
      curl -X DELETE http://localhost:8080/customers/8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1
      ```

Validações do payload de cliente (`CustomerRequest`):
- `fullName`: obrigatório, até 255 caracteres
- `email`: obrigatório, formato válido, até 100 caracteres
- `phone`: obrigatório

Erros de validação retornam 400 com um objeto contendo `campo -> mensagem`, por exemplo:
```json
{
  "fullName": "This field is mandatory",
  "email": "must be a well-formed email address"
}
```

Erros de recurso não encontrado retornam 404:
```json
{
  "timestamp": "2025-09-20T12:34:56.789",
  "status": 404,
  "error": "Resource not found",
  "message": "No Customer found with id {UUID}"
}
```

#### 2) Transações (`/transactions`)

- Criar transação
    - Método/URL: `POST /transactions`
    - Headers: `Content-Type: application/json`
    - Body (JSON):
      ```json
      {
        "customerId": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
        "amount": 150.75,
        "cardType": "CRÉDITO"
      }
      ```
      Observações:
        - `customerId` deve referenciar um cliente existente e ativo.
        - `amount` deve ser maior que zero.
        - `cardType` deve ser exatamente `DÉBITO` ou `CRÉDITO` (com acento).
    - Resposta 201 (exemplo):
      ```json
      {
        "id": "b1d2aa1b-2bcb-4a27-8c42-8ce8c9f8af7c",
        "customerId": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
        "amount": 150.75,
        "cardType": "CRÉDITO",
        "createdAt": "2025-09-20T12:20:00",
        "active": true
      }
      ```
    - cURL:
      ```bash
      curl -X POST http://localhost:8080/transactions \
        -H "Content-Type: application/json" \
        -d '{
          "customerId": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
          "amount": 150.75,
          "cardType": "CRÉDITO"
        }'
      ```

- Listar transações por cliente
    - Método/URL: `GET /transactions?customerId={UUID}`
    - Resposta 200 (exemplo):
      ```json
      [
        {
          "id": "b1d2aa1b-2bcb-4a27-8c42-8ce8c9f8af7c",
          "customerId": "8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1",
          "amount": 150.75,
          "cardType": "CRÉDITO",
          "createdAt": "2025-09-20T12:20:00",
          "active": true
        }
      ]
      ```
    - cURL:
      ```bash
      curl "http://localhost:8080/transactions?customerId=8a7c4c5e-4d3b-4d8e-9c8e-4a6bd5d9f3a1"
      ```

Erros comuns em transações:
- Cliente inexistente/inativo: 404 `{"message": "Customer not found"}`
- Validação de payload: 400 com `campo -> mensagem` (por exemplo, `cardType` fora do padrão)

---

### Coleção Postman (como usar)
- No Postman, crie uma nova Collection "Channels API".
- Adicione as requisições conforme descrito:
    - `POST http://localhost:8080/customers`
    - `GET http://localhost:8080/customers/{id}`
    - `GET http://localhost:8080/customers`
    - `PUT http://localhost:8080/customers/{id}`
    - `DELETE http://localhost:8080/customers/{id}`
    - `POST http://localhost:8080/transactions`
    - `GET http://localhost:8080/transactions?customerId={UUID}`
- Configure o header `Content-Type: application/json` nos métodos `POST` e `PUT`.
- Use os bodies de exemplo acima.

Se preferir importar via cURL, use os comandos informados para testar rapidamente pelo terminal.

---

### Dependências utilizadas (pom.xml)
- `spring-boot-starter-web` — Criação de APIs REST
- `spring-boot-starter-data-jpa` — Persistência JPA/Hibernate
- `spring-boot-starter-validation` — Validações com Jakarta Validation
- `spring-boot-starter-actuator` — Endpoints de monitoramento/saúde
- `com.h2database:h2` — Banco de dados em memória H2 (runtime)
- `org.projectlombok:lombok` — Redução de boilerplate (getters/setters, builders, etc.)
- `spring-boot-starter-test` — Testes (escopo de teste)

Java: `21` (configurado em `pom.xml`)

---

### Estrutura básica do domínio
- `Customer` (cliente): `id`, `fullName`, `email`, `phone`, `createdAt`, `updatedAt`, `active`
- `Transaction` (transação): `id`, `customerId`, `amount`, `cardType`, `createdAt`, `active`

Regra de negócio principal:
- Transações só podem ser criadas para clientes ativos; do contrário, retorna 404.

---

### Tratamento de erros
- Validação (`400 BAD REQUEST`): retorna mapa de `campo -> mensagem`
- Recurso não encontrado (`404 NOT FOUND`): payload com `timestamp`, `status`, `error`, `message`
- Erro genérico (`500 INTERNAL SERVER ERROR`): payload com `timestamp`, `status`, `error`, `message`

---

### Dicas
- Ao testar `cardType` use exatamente `DÉBITO` ou `CRÉDITO` (incluindo acento).
- Lembre-se de criar um cliente antes de criar uma transação e usar o `id` retornado no `customerId`.
- Para inspecionar tabelas no H2 Console, use JDBC URL `jdbc:h2:mem:channels` e clique em Connect.

---

### BÔNUS :D
- Olá, sou eu vitor ! Se você está lendo isso eu deixei um "easter egg" no código. Se você achou, PARABÉNS :DDDD !!