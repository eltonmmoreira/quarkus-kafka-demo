# quarkus-kafka-demo
# Getting Started
Clone o repositório:
<pre><code> https://github.com/eltonmmoreira/quarkus-kafka-demo.git</code></pre>

# Description
Serviços construídos para demo das tecnologias abaixo. A aplicação simula ordens de compra e venda de ativos e um balanço com o total dos ativos por conta.
O serviço assets-order é responsável por criar as ordens, gravar em um banco de dados e enviar a um tópico do kafka, onde o serviço 
assets-balance recebe a mensagem e então cria ou atualiza um consolidado por ativo e conta, gravando em outro banco. 
O serviços tem a responsabilidade dividida, um que somente gravar as ordens e outro que realiza a leitura. 

Tecnologias:
- Quarkus,
- JAVA, 
- REST, 
- Docker,
- Docker compose,
- kafka

# Building and running the application
## Pre-requisites
<pre><code>JAVA 15
Maven
Docker
Docker compose</code></pre>

## Package the Spring Boot jar and create the docker image
Para compilar e empacotar o arquivo jar e criar uma imagem docker, execute os comandos abaixo:
<pre><code>mvn clean package -Dquarkus.container-image.build=true</code></pre>

## Docker compose:
Para os serviços utilizados no projeto, no diretório raiz execute o comando:
<pre><code>docker-compose up</code></pre>

# Documentation
<pre><code>http://localhost:9090/q/swagger-ui
http://localhost:9091/q/swagger-ui
</code></pre>

