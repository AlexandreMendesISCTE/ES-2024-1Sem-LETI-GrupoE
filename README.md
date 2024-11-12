# Aplicação de Apoio à Decisão para Gestão do Território

## Sobre o Projeto:

Este projeto faz parte da unidade curricular de Engenharia de Software (2024/2025 — 1.º Semestre) da Licenciatura em Engenharia de Telecomunicações e Informática do ISCTE. O objetivo é desenvolver uma aplicação de apoio à decisão para a gestão mais equilibrada e sustentável do território em Portugal, abordando o problema do desequilíbrio populacional entre o interior e o litoral do país. A aplicação visa facilitar o processo de "desfragmentação" das propriedades rústicas e ajudar na gestão dessas áreas.

## Funcionalidades:

### A aplicação inclui as seguintes funcionalidades principais:

- Carregamento de Dados: Permite carregar os dados do cadastro de propriedades rústicas em Portugal, utilizando fontes de dados públicas. Um arquivo CSV (“Madeira-Moodle.csv”) é fornecido como fonte de dados da região autónoma da Madeira.
- Representação em Grafo: Representa o cadastro das propriedades sob a forma de um grafo, onde os nós representam as propriedades e as arestas representam as relações de adjacência entre elas.3-

### No Futuro:

- Cálculo da Área Média: Calcula a área média das propriedades de uma região administrativa (freguesia, concelho, distrito) indicada pelo utilizador.
- Agrupamento de Propriedades Adjacentes: Calcula a área média assumindo que propriedades adjacentes do mesmo proprietário são consideradas como uma única propriedade.
- Relação de Vizinhança entre Proprietários: Representa a relação de vizinhança entre proprietários num grafo, onde os nós são proprietários e as arestas indicam propriedades contíguas entre diferentes proprietários.
- Sugestões de Troca de Propriedades: Gera sugestões de trocas de propriedades entre proprietários que maximizem a área média das propriedades de cada um.

## Tecnologias Utilizadas

- Java: Linguagem de programação principal utilizada para o desenvolvimento.
- Maven: Para gestão de dependências.
- JUnit: Para criação e execução dos testes do software.
- Git/GitHub: Controle de versão local e repositório remoto para colaboração do projeto.
- Trello: Para a gestão ágil do projeto segundo a abordagem Scrum, rastreando as user stories e integração com GitHub.

## Membros do Grupo

Nome: Alexandre Mendes - Número de Estudante: 111026 - GitHub: AlexandreMendesISCTE

Nome: Edgar Cardoso - Número de Estudante: 111340 - GitHub: EdgarCardoso04

## Problemas Conhecidos e Funcionalidades Incompletas

Ainda falta acabar de desenvolver as restantes funcionalidades para a entrega 2.
