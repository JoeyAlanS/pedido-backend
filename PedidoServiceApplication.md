# pedidoservice

Projeto de exemplo utilizando Spring Boot para gerenciamento de pedidos, com integração ao MongoDB.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.0**
  - Spring Web
  - Spring Security
  - Spring Data MongoDB
- **MongoDB**

## Pré-requisitos

- Java 21 ou superior
- Maven 3.x
- Uma instância do MongoDB (local ou na nuvem)

## Configuração

### 1. Clonando o repositório

```markdown
bash
git clone https://github.com/seu-usuario/pedidoservice.git
cd pedidoservice
```
### 2. Configurando as propriedades sensíveis

Crie o arquivo `src/main/resources/application-secret.properties` com:
```
properties
spring.data.mongodb.uri=mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=servicepedido
```
Adicione o arquivo `application-secret.properties` ao seu `.gitignore` para evitar expor informações sensíveis.

### 3. Configurando a aplicação

No arquivo `src/main/resources/application.properties`, adicione:
```
properties
spring.config.import=optional:application-secret.properties
server.port=8081
```
Assim, as credenciais do banco ficam separadas, e o projeto busca automaticamente pelo arquivo secreto durante a inicialização.

### 4. Compilando o projeto
```
bash
mvn clean install
```
### 5. Executando a aplicação
```
bash
mvn spring-boot:run
```
A aplicação estará disponível em:  
[http://localhost:8081](http://localhost:8081)

### 6. Estrutura do Projeto

- `src/main/java`: Código-fonte principal (controllers, services, repositories, etc.)
- `src/main/resources`: Arquivos de configuração

### 7. Executando os testes
```
bash
mvn test
```
### 8. Comandos Maven úteis

- Build: `mvn clean install`
- Run: `mvn spring-boot:run`
- Testes: `mvn test`

## Observações Importantes

- **Nunca suba arquivos com credenciais ou informações sensíveis para o repositório.**
- Em ambientes de produção, utilize ferramentas de gerenciamento de segredos (exemplo: Spring Cloud Config, Hashicorp Vault).
- O arquivo `application-secret.properties` é apenas um exemplo para ambiente local.

---
```
