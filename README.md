# Smart Notes

Aplicação de gerenciamento de notas que utiliza Inteligência Artificial para transformar áudios em anotações organizadas, facilitando o registro, a consulta e a organização de informações do dia a dia.

## Sobre o projeto

O **Smart Notes** foi desenvolvido com a proposta de simplificar a criação e organização de anotações. Em vez de depender exclusivamente de registros manuais, o usuário pode enviar gravações de áudio para que o sistema realize a transcrição e utilize Inteligência Artificial para gerar informações estruturadas.

A aplicação integra frontend e backend, conectando uma interface de usuário a uma API REST responsável pelo processamento de áudio, integração com serviços de IA e persistência dos dados.

## Funcionalidades

- **Transcrição de áudio:** conversão de gravações em texto.
- **Análise inteligente:** utilização de IA para gerar títulos e categorizar as anotações.
- **Gerenciamento de notas:** criação, consulta e organização de anotações.
- **Persistência de dados:** armazenamento das informações em banco de dados relacional.

## Tecnologias utilizadas

### Backend
- Java
- Spring Boot
- Spring AI
- Spring Data JPA
- Spring Web
- MySQL
- Maven

### Frontend
- React
- JavaScript
- CSS

## Arquitetura

O backend foi desenvolvido com Spring Boot, utilizando uma API REST para disponibilizar os recursos da aplicação e integrar os serviços necessários ao processamento das anotações.

O frontend foi desenvolvido em React, sendo responsável pela interface e interação do usuário com as funcionalidades do sistema.

A integração entre as duas partes permite o envio de áudios, recebimento das transcrições e visualização das notas organizadas.

## Objetivo

O projeto tem como objetivo explorar o desenvolvimento de aplicações full stack, aplicando conceitos de desenvolvimento backend com Java e Spring Boot, construção de interfaces com React, integração com APIs externas, processamento de arquivos, Inteligência Artificial e persistência de dados.
