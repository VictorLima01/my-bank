My Bank Challenge - Contas Correntes

A aplicação foi pensada como um aplicativo de banco, no qual teria um sistema de cadastro de clientes e de suas contas das quais esses cliente poderiam fazer o login e realizar transações e fazer operações de reverter a transação.

Pensando nesse desafio criei essa aplicação que utiliza de frameworks do Spring Security, Spring Data JPA e Spring Boot. A aplicação também utiliza sistema de cache para o login do cliente e também para sempre saber o remetente da transação bancaria.

Mas para exemplificar melhor a aplicação estarei deixando a collection do PostMan para seguir os testes que deverão ser feitos para concluir os testes da aplicação.

Link PostMan: https://drive.google.com/drive/folders/10gWoDMZIXws8OZ8HeRQiP-kml26BJo02?usp=sharing. Estou deixando as requisições numeradas e já com os payloads montados. Lembrando que para seguir com os testes dessa aplicação é necessário fazer a ceiacao dos clientes, e depois seguri com o login do cliente. Após o login será gerado um token que terá que ser usado como Bearer token para realizar transações. A ideia do app seria mais para simular um login de uma app do banco.

Sobre o banco de dados foi usado o H2, do qual configurei na aplicação para subir localmente junto a aplicação. http://localhost:8080/h2-console, acessando esse seguinte link e nos campos 
 * JDBC URL: jdbc:h2:mem:testdb
 * User Name: sa

Com essas configurações será possível ver as teblas criadas a partir da aplicação Java.

Payload de criação de clientes e contas:
        {
    "cpfCnpj": "47403550862",
    "endereco": "Avenida Santo Antonio",
    "nome": "Victor",
    "password": "123456789",
    "contas" : [
        {
            "agencia": "1272",
            "saldo": 10.0,
            "status": "ATIVA"
        }
    ],
    "role": "USER"
}

{
    "cpfCnpj": "44400055566",
    "endereco": "Avenida Santo Antonio",
    "nome": "João",
    "password": "123456789",
    "contas" : [
        {
            "agencia": "1273",
            "saldo": 10.0,
            "status": "ATIVA"
        }
    ],
    "role": "USER"
}

Observação: Tentei consumir o serviço de envio de notificações porém parece estar fora, mas utilizei o Rest Template para consumo de API's. Deixei o print na mesma pasta do drive que foi disponibilizada.
