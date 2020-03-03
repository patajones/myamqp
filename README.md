# My AMQP

Apenas um exemplo de como consumir e publicar mensagem no RabbitMQ usando Java/Spring Boot


## Requisitos

* Java 11
* Maven
* Docker e Docker Compose (Para subir a instancia do RabbitMQ)

## Executando

Suba a instancia do RabbitMQ com o comando:

```
docker-compose up -d
```

Execute a aplicação com o comando:

```
mvn spring-boot:run
```

Abra o RabbitMQ Management em http://localhost:8099, abra a fila `myamqp.entrada` e publique uma mensagem.

Acompanhe os logs para verificar o funcionamento.

