Golden Raspberry Awards API

Uma aplicação Spring Boot para gerenciar e recuperar informações sobre os Golden Raspberry Awards, com foco em identificar os produtores com os menores e maiores intervalos entre suas vitórias.

Tecnologias Utilizadas

    Java 21
    Spring Boot: Framework principal para construção da aplicação.
    H2 Database: Banco de dados em memória para desenvolvimento e testes.
    Spring Data JPA: Facilita o acesso e gerenciamento do banco de dados.
    Springdoc OpenAPI: Geração automática da documentação da API e interface Swagger UI.
    Lombok: Reduz a quantidade de código repetitivo com anotações.
    MapStruct: Simplifica o mapeamento e conversão de objetos.
    JaCoCo: Análise de cobertura de código nos testes.
    JUnit & Mockito: Framework de testes e biblioteca de mocking.

### Como Rodar a Aplicação

Clonar o Repositório

    git clone https://github.com/ViniciusVonAhn/golden-raspberry-awards.git

Acessar o diretório

    cd golden-raspberry-awards

Construir a Aplicação

    mvn clean install

Rodar a Aplicação 

Observação: dentro de resources já existe o csv com os filmes a serem persistidos no banco ao rodar a aplicação.

    mvn spring-boot:run

Acessar a Aplicação:

    Swagger UI: http://localhost:8080/swagger-ui.html
    Acesse a documentação da API e teste os endpoints diretamente.

    Console H2: http://localhost:8080/h2-console
        URL JDBC: jdbc:h2:mem:testdb
        Usuário: sa
        Senha: (deixe em branco)

Rodando os Testes

Execute os testes utilizando o Maven:

    mvn test

Acessando o Relatório de Cobertura de Código do JaCoCo

Gere o relatório:

    mvn jacoco:report

Abra o relatório:

    Navegue até a pasta target/site/jacoco/index.html no seu navegador para visualizar os resultados da cobertura de código.

Endpoints:
GET /v1/api/producers/min-max-winners

    Descrição: Recupera os produtores com os menores e maiores intervalos entre suas vitórias.
    Exemplo de Resposta:

    {
      "min": [
        {
          "name": "Producer A",
          "previousWin": 1980,
          "followingWin": 1981,
          "interval": 1
        }
      ],
      "max": [
        {
          "name": "Producer B",
          "previousWin": 1990,
          "followingWin": 2000,
          "interval": 10
        }
      ]
    }

Curl para o endpoint:
    
    curl --location 'http://localhost:8080/v1/api/producers/min-max-winners'