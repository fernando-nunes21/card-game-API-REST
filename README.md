# card-game-API-REST
Olá e seja bem vindo a CARD GAME API. 

Resumidamente, este projeto busca disponibilizar uma API REST para criação de card games contando com a parte de repositório de cartas e baralhos para jogos baseados no card game YU-GI-OH. 

Como não visa ser direcionada a um game específico, os atributos para cartas e baralhos estão bem reduzidos, possibilitando mais facilmente a adaptação para o seu projeto. 

Basicamente a API conta com 2 endpoints "/v1/cards" e "/v1/decks" nos quais você vai poder utilizar e modificar os cruds a vontade. 

Vamos para os passos básicos, como para rodar a API local para testar é preciso do que? 

	- PostgreSQL 14 instalado. 
	- Java 16
	- Postman (Opcional, para realizar as requests)

Tendo esses requisitos, clone o repositório e abra o em sua IDE (Preferência pessoal, INTELIJ)

Antes de começar a rodar, vai precisar ajustar as configurações do Postgres da sua máquina, que podem ser alteradas acessando o arquivo "application.properties", modificando o que for necessário. Também é preciso que seja criado o banco no qual a API foi construida para funcionar corretamente (pode ser modificado se desejar), então abra a pasta "database" do projeto e rode o SQL que encontrar lá para criar o banco e as respectivas tabelas. 

Com tudo configurado, você poderá subir a aplicação e usar a vontade. 

Segue abaixo as rotas e suas respectivas funções para facilitar seus testes e modificações:

# LEMBRANDO: 
Para realizar alterações nas cards (criar, modificar e deletar, é necessário, como definição do projeto, ser um administrador, então foi criado um processo simplificao de validação, onde no Header Authorization é "cardAdminAPI")

Nem todas as chamadas precisam dessa validação do header, somente as que na própria controller estão usando a annotation @AdminRouteAuth

O código está bem organizado de forma a conseguir se orientar e navegar por ele com mais facilidade. 

As rotas para request de cards estão todas bem expostas na CardController, assim como as de decks estão na DeckController.

Mais detalhes sobre os CRUDS é só navegar pelas controllers. 
