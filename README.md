# Bookstore API

A RESTFul API for an independent bookstore which allows customers to browse available books by genre and author.

---
 
## 1. Requisitos
- Java 21
- Docker
- Redis
- Postgres 16

## 2. Antes de Executar

- Verifique se possue um banco de dados postgresql ativo.
- Em application.properties e application-test.properties insira os dados do banco, username e password corretos na url.
- Faça o build da aplicação
- Após iniciar, faça uma requisição POST no endpoint "/load/books" sem body, para que insira os livros no database. 
- Dica: Teste os endpoints com o postman

---

#### Importante! - Documentação Swagger
A documentação dos endpoints está diponível. Para acessa-lá, execute a aplicação e abra no navegador o seguinte endereço:

http://localhost:8080/swagger-ui/index.html

---
## Relatório de Arquitetura
### I. Arquitetura de Solução e Arquitetura Técnica

#### 1. Estrutura de Pacotes

A API foi organizada com uma separação clara de responsabilidades, dividida em camadas conforme as boas práticas de desenvolvimento. A estrutura de pacotes é a seguinte:

- **Config**: Responsável por classes de configuração do Swagger para documentação dos endpoints e do Redis para cache.
- **Controller**: Contém os controladores da API. O BookController expõe os principais endpoints REST relacionados ao CRUD de livros e busca por autor ou gênero. O DataImportController gerencia a importação de dados de livros a partir de um arquivo CSV.
- **Domain**: Abriga a entidade Book, que representa o domínio do negócio (informações dos livros).
- **DTO**: Contém o BookDTO, utilizado para transportar dados entre a API e o cliente, mantendo uma separação clara entre a camada de persistência e a camada de exposição de dados.
- **Repository**: Gerencia a interação com o banco de dados, utilizando o JPA para persistir e consultar dados.
  Response: Uma classe padronizada para formatação de respostas da API, facilitando o controle das respostas enviadas ao cliente.
- **Service**: Contém a lógica de negócio da aplicação, incluindo operações de persistência e manipulação de dados.

#### 2. Tecnologias Utilizadas
As principais tecnologias e dependências do projeto foram:

- **Spring Boot 3.3.3**: Framework principal para construção da API, que fornece uma base sólida para o desenvolvimento rápido de aplicações em Java.
- **Postgres**: Banco de dados relacional utilizado para persistir os dados dos livros. O JPA foi utilizado para gerenciar o mapeamento objeto-relacional, simplificando a persistência e consultas ao banco.
- **Redis**: Utilizado para caching, com foco na otimização de respostas em endpoints que envolvem operações mais custosas, como a listagem de todos os livros (findAll()).
- **Commons CSV**: Usado para importar dados de livros a partir de arquivos CSV, permitindo uma forma prática de popular a base de dados.
- **JUnit**: Utilizado no desenvolvimento com metodologia TDD, garantindo a qualidade do código por meio de testes unitários. Foram implementados testes para validação de operações de CRUD e buscas por autor e gênero.
- **Springdoc OpenAPI**: Gerador automático da documentação da API, facilitando o acesso e entendimento dos endpoints para o cliente.

#### 3. Decisões de Design

A API foi construída com foco em modularidade e separação de responsabilidades, seguindo a arquitetura MVC (Model-View-Controller). As seguintes decisões de design merecem destaque:

- **Uso do Redis para Caching**: Implementado com a anotação @Cacheable para otimizar a performance no método que lista todos os livros (findAll), reduzindo a carga no banco de dados em operações repetitivas.
- **Gerenciamento de Transações**: Foi implementado gerenciamento transacional no método de salvar um livro e na importação dos livros do CSV, assegurando consistência nos dados e controle de rollback em caso de falhas.
- **Mapeamento Objeto-Relacional com JPA**: A aplicação foi estruturada para utilizar JPA para o mapeamento da entidade Book, facilitando a persistência de dados e operações de CRUD com o banco de dados Postgres.

#### 4. Integração e Comunicação
Os endpoints da API foram construídos com base em princípios RESTful, proporcionando uma interface de comunicação clara entre a API e os clientes. A documentação gerada automaticamente pelo Swagger facilita o entendimento dos endpoints e seus parâmetros.

### II. Explicação sobre o Case Desenvolvido (Plano de Implementação)

#### 1. Descrição Geral
A API foi desenvolvida com foco no gerenciamento de informações de livros, utilizando dados importados de um arquivo CSV público obtido no Kaggle. O objetivo principal é oferecer uma interface para busca e cadastro de livros, assim como otimizar o desempenho com cache em operações de leitura intensiva. O projeto conta com endpoints para listar todos os livros, buscar por ID, autor e gênero, além de um endpoint para importar os dados diretamente do CSV.

#### 2. Estrutura dos Dados
O arquivo CSV utilizado para popular a base de dados contém as seguintes colunas:

- **Title**: Título do livro.
- **Author**: Nome do autor ou da editora.
- **Main Genre**: Gênero principal ao qual o livro pertence.
- **Sub Genre**: Subgênero específico do livro.
- **Type**: Formato do livro, como paperback, Kindle, audiobook ou hardcover.
- **Price**: Preço do livro.
- **Rating**: Média de avaliações dadas pelos usuários.
- **No. of People Rated**: Número de usuários que avaliaram o livro.
- **URLs**: Links para a página do livro no site da Amazon.

#### 3. Importação e Tratamento de Dados
Durante o processo de importação, a API precisou lidar com inconsistências nos dados do CSV. Como o dataset original contém algumas informações fora do padrão, foram aplicados tratamentos específicos, como:

- **Valores de Preço**: O preço dos livros continha o identificador da moeda local (ex: "$"), que foi removido para garantir a consistência nos dados numéricos.
- **Número de Avaliadores**: Alguns valores na coluna "No. of People Rated" estavam representados como números flutuantes, o que foi corrigido para valores inteiros.
- **Validação de Campos**: A importação também aplica validações nos dados, assegurando que os campos como Title, Author, e Price seguem os requisitos de não serem nulos ou vazios, bem como garantindo que valores como Price e Rating estejam dentro dos intervalos definidos.

#### 4. Validações e Tratamento de Exceções
Durante as operações de cadastro e consulta, foram implementadas diversas validações para garantir a integridade dos dados:

- **Busca por ID Inexistente**: Quando o cliente tenta buscar um livro com um ID que não existe no banco de dados, a API retorna um erro 404 com a mensagem "Livro não encontrado".
- **Validações no Cadastro de Livros**: Ao cadastrar novos livros, tanto manualmente quanto pela importação de CSV, foram aplicadas validações como:
    - Verificação de tamanho mínimo de strings para Title e Author.
    - Validação de campos numéricos como Price e Rating para garantir que estejam dentro de limites aceitáveis.
    - Validações de campos obrigatórios (NotBlank, NotNull) para garantir que todos os dados essenciais sejam fornecidos.

- **Feedback ao Cliente**: Em caso de falha nas validações, a API retorna mensagens de erro específicas, informando o cliente sobre quais campos estão incorretos ou faltantes, permitindo uma rápida correção.

#### 5. Testes e Garantia de Qualidade
A API foi construída seguindo a metodologia TDD (Test-Driven Development), utilizando JUnit para criar testes unitários robustos. Os testes desenvolvidos cobrem cenários como:

- **Cadastro e Salvamento de Livros**: Testes que verificam a criação e persistência correta de um livro no banco de dados.
- **Consultas por Gênero e Autor**: Testes para garantir que os filtros por Main Genre e Author funcionam corretamente, retornando apenas os livros que atendem aos critérios de busca.

Esses testes garantem a estabilidade da aplicação, assegurando que as funcionalidades cruciais estão funcionando conforme esperado.

### III. Melhorias e Considerações Finais

#### 1. Melhorias Futuras

Embora a API esteja funcional e tenha sido desenvolvida com boas práticas de modularidade e separação de responsabilidades, há algumas melhorias que podem ser implementadas para aprimorar a segurança, a escalabilidade e a experiência do usuário:

- **Implementação de Segurança com Spring Security**: Uma importante melhoria seria adicionar camadas de segurança utilizando o Spring Security, permitindo a criação de diferentes tipos de usuários com níveis de permissão distintos. Isso incluiria a definição de "papéis" de usuários, como administradores e usuários regulares, controlando o acesso aos endpoints e as operações que podem ser realizadas por cada perfil.

- **Normalização de Gênero e Subgênero**: Atualmente, os gêneros e subgêneros dos livros são armazenados diretamente na tabela Book. Uma melhoria significativa seria a criação de tabelas separadas para os gêneros e subgêneros, com relacionamento entre as tabelas para garantir maior normalização dos dados e facilitar futuras expansões, como filtros mais eficientes.

- **Desenvolvimento de um Front-end Web Responsivo**: Para melhorar a experiência dos usuários, seria interessante a construção de uma interface web (front-end) que permita a visualização e interação com a API em diferentes dispositivos, como computadores e smartphones. A aplicação de design responsivo melhoraria a acessibilidade e a usabilidade.

- **Funcionalidade de "Visualizados Recentemente"**: Outra funcionalidade interessante seria a adição de uma feature que armazena e exibe os livros visualizados recentemente pelos usuários, oferecendo uma navegação mais intuitiva e uma melhor experiência de uso.

#### 2. Dificuldades

- Não consegui configurar a aplicação para que fosse executada através do docker-compose. Tive problemas para configurar o banco de dados automaticamente e conversar entre as applicações.