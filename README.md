![Projeto Customers](Hackaton-Logo-dark.png)

By [Stefanini](https://stefanini.com/).

# Desafio Final - Hackaton Orbitall 2025 - API de Gestão de Clientes e Transações

### 📜 Contexto:
Você está participando do Hackaton do setor financeiro e sua missão é desenvolver uma API REST em Java + Spring Boot para gerenciamento de clientes e registro de transações.
A API será utilizada por sistemas internos para cadastrar, consultar, atualizar e remover dados, além de processar transações simuladas.

### 🛠 Requisitos Técnicos
- Java 21
- Spring Boot
- Banco de dados em memória H2
- Retornar status codes corretos (HTTP)
- Uso correto dos verbos HTTP (GET, POST, PUT, DELETE)
- Boas práticas de nomenclatura REST (ex: /customers, /transactions)
- Tratamento de erros e exceções

### 📌 Funcionalidades Obrigatórias

1. Clientes (/customers)
- POST /customers → Cadastrar um novo cliente.
- GET /customers/{id} → Buscar cliente por ID.
- PUT /customers/{id} → Atualizar dados do cliente.
- DELETE /customers/{id} → Excluir cliente.
- GET /customers → Listar todos os clientes ativos no sistema.

```sh
+--------------------------------------+
| Customer                             | -> nome da classe
+--------------------------------------+
| + id: java.util.UUID                 |
| + fullName: String                   |
| + email: String                      |
| + phone: String                      | -> atributos da classe
| + createdAt: java.time.LocalDateTime |
| + updatedAt: java.time.LocalDateTime |
| + active: boolean                    |
+--------------------------------------+
| + getters                            |
| - setters                            | -> getters / setters da classe
+--------------------------------------+
```

2. Transações (/transactions)
- POST /transactions → Criar uma nova transação vinculada a um cliente. A transação é composta por id, id do cliente (Customer), valor (amount), tipo de cartão como DÉBITO/CRÉDITO (cardType) e data da transação (createdAt).
- GET /transactions?customerId=... → Listar todas as transações de um cliente.

```sh
+--------------------------------------+
| Transaction                          | -> nome da classe
+--------------------------------------+
| + id: java.util.UUID                 |
| + customerId: java.util.UUID         |
| + amount: java.math.BigDecimal       |
| + cardType: String                   | -> atributos da classe
| + createdAt: java.time.LocalDateTime |
| + active: boolean                    |
+--------------------------------------+
| + getters                            |
| - setters                            | -> getters / setters da classe
+--------------------------------------+
```

### 📈 Regras de Negócio
- Necessário validar o atributo nome completo (fullName) do Cliente (Customer) como obrigatório e tem que preencher até no máximo 255 caracteres.
- Necessário validar o atributo e-mail (email) do Cliente (Customer) como obrigatório e tem que preencher até no máximo 100 caracteres.
- Necessário validar o atributo telefone (phone) do Cliente (Customer) como obrigatório.
- Não permitir transações para clientes inexistentes (retornar 404 Not Found).
- Necessário validar o atributo cliente (customerId) da Transação (Transaction) como obrigatório e cliente válido.
- Necessário validar o atributo valor (amount) da transação (Transaction) como obrigatório e maior que zero.
- Necessário validar o atributo tipo de cartão (cardType) da Transação (Transaction) como obrigatório e valor como DÉBITO OU CRÉDITO.

### 💡 Diferenciais (Extra Points)
- Implementar tratamento global de erros com @ControllerAdvice.

### 📂 Entrega
- Código fonte em repositório git público (GitHub).
- README.md explicando:
  - Passo a passo para rodar a aplicação. 
  - Endpoints e exemplos de requisições/respostas (com Postman).
  - Dependências utilizadas.

Desejamos uma boa prova, sucesso e BOA SORTE!!!

Orbitall Payments Teams - 20/Setembro/2025
- - Amanda Queiroz
- - Gabriela De Oliveira
- - Leandro Capuano
- - Luis Forcinnetti
- - Maysa Hoffmann
- - Rodrigo Bibiano 
- - Sérgio Sampaio

<br>

![Projeto Customers](codigo-de-barras.png)

<br>

### *** DICAS ****

#### ** GIT **
```sh
$ git clone <branch>
$ git add .
$ git commit -m 'Seu comentário'
$ git push origin <branch>
```
###### PS: Cuidado com o artefato oculto chamado .git, você tem que basear na sua repositório e não no que foi clonado!

#### ** MAVEN **
```sh
$ mvn clean
$ mvn install
$ mvn spring-boot:run
```

#### ** Lombok **
- Não esqueça de habilitar o Lombok como Plugin dentro do IntelliJ.
- Apele para o Lombok gerar os setters/getters através da anotação @Data.

#### ** Spring Boot **
- Use o módulo Validation do Spring Boot para fazer a validação dos campos e não esqueça de implementar o GlobalExceptionHandler que aprendemos durante o hackathon.
- Use a camada Service para aplicar a regra de negócio.
- Não esqueça de setar os valores padrão como id (UUID randômico), datas (createdAt/updatedAt) com a data/hora corrente e ativar o registro (active) como verdadeiro (true).

#### ** IA/LLM **
- Não esquecer de instalar e habilitar o Junie dentro do IntelliJ, fica dentro dos Plugins.
- Apele ao Junie do IntelliJ para gerar o arquivo README.md do pedido do enunciado.
- Não perca tempo criando teste unitário, não é requisito deste desafio final.
- Com o Junie é possível criar o CRUD, mas cuidado com a adrenalina e o tempo, principalmente para não cair no labirinto.
