# java-training-api

Esse repositório disponibiliza uma versão zero de uma API de cadastro de usuários (Users) na pasta [src](https://github.com/GuillaumeFalourd/java-training-api/tree/main/src) *a ser melhorada*.

## DESAFIOS

Os desafio podem ser encontrado no arquivo [DESAFIOS.md](https://github.com/GuillaumeFalourd/java-training-api/tree/main/DESAFIOS.md).

## Requisitos

- Maven
- Spring
- Java 8 (11 melhor)

Baixar as dependências: na raiz do projeto: executar o comando `mvn clean install`.

Para rodar a aplicação localmente, executar o metodo `main` da classe [TrainingApiApplication.java](https://github.com/GuillaumeFalourd/java-training-api/tree/main/src/main/java/br/com/training/TrainingApiApplication.java).

## Endpoint disponíveis (v0)

### Criar usuário

**POST:** `http://localhost:8080/users` com *body*:

```json
{
    "name":"Name",
    "cpf":"cpf",
    "email":"email",
    "birthDate":"1900-01-01"
}
```

### Obter usuário com CPF

**GET:** `http://localhost:8080/users/{cpf}` vai retornar:

```json
{
    "name":"Name",
    "cpf":"cpf",
    "email":"email",
    "birthDate":"1900-01-01",
    "vehicles": ["..."]
}
```
