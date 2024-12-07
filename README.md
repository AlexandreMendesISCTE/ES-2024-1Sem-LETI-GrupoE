# Aplicação de Apoio à Decisão para Gestão do Território

## Sobre o Projeto:

Este projeto faz parte da unidade curricular de Engenharia de Software (2024/2025 — 1.º Semestre) da Licenciatura em Engenharia de Telecomunicações e Informática do ISCTE. O objetivo é desenvolver uma aplicação de apoio à decisão para promover uma gestão mais equilibrada e sustentável do território em Portugal, abordando o problema do desequilíbrio populacional entre o interior e o litoral do país. A aplicação visa facilitar o processo de "desfragmentação" das propriedades rústicas e apoiar a gestão dessas áreas.

Enunciado: [Link para o Enunciado do Projeto](https://moodle24.iscte-iul.pt/pluginfile.php/41094/mod_folder/content/0/Enunciado%20do%20projeto%20pratico%20ES%201o%20Sem%202024-2025.pdf?forcedownload=1)

## Funcionalidades

### A aplicação inclui as seguintes funcionalidades principais:

- **Carregamento de Dados:** Permite carregar os dados do cadastro de propriedades rústicas em Portugal, utilizando fontes de dados públicas. Um ficheiro CSV (“Madeira-Moodle.csv”) é fornecido como fonte de dados da região autónoma da Madeira.
- **Representação em Grafo:** Representa o cadastro das propriedades sob a forma de um grafo, onde os nós representam as propriedades e as arestas representam as relações de adjacência entre elas.
- **Cálculo da Área Média:** Calcula a área média das propriedades de uma região administrativa (freguesia, concelho, distrito) indicada pelo utilizador.
- **Agrupamento de Propriedades Adjacentes:** Calcula a área média assumindo que propriedades adjacentes pertencentes ao mesmo proprietário são consideradas como uma única propriedade.
- **Relação de Vizinhança entre Proprietários:** Representa a relação de vizinhança entre proprietários num grafo, onde os nós são proprietários e as arestas indicam propriedades contíguas pertencentes a diferentes proprietários.
- **Sugestões de Troca de Propriedades:** Gera sugestões de trocas de propriedades entre proprietários que maximizem a área média das propriedades de cada um.
- **Representação Gráfica:** Permite visualizar as propriedades, incluindo o seu formato e adjacências com outras propriedades.

## Tecnologias Utilizadas

- **Java:** Linguagem de programação principal utilizada para o desenvolvimento.
- **Maven:** Para gestão de dependências.
- **JUnit:** Para criação e execução dos testes do software.
- **Git/GitHub:** Controlo de versão local e repositório remoto para colaboração no projeto.
- **Trello:** Para a gestão ágil do projeto segundo a abordagem Scrum, rastreando as user stories e integrando com o GitHub.
- **Visual Studio Code/IntelliJ IDEA:** Para programação e desenvolvimento.
- **Qodana/Metrics Tree:** Para verificação e manutenção da qualidade do código.

### Bibliotecas Maven Utilizadas

- **JUnit Jupiter API:** Versão 5.11.3 - Para testes unitários.
- **Apache Commons CSV:** Versão 1.12.0 - Para manipulação de ficheiros CSV.
- **JTS Core:** Versão 1.20.0 - Para operações geométricas avançadas dos Multipolignos.
- **JGraphT Core e IO:** Versão 1.5.2 - Para criação e manipulação de grafos.
- **JGraphX:** Versão 1.10.4.2 - Para visualização de grafos.
- **JFreeChart:** Versão 1.5.5 - Para criação de gráficos.

## Membros do Grupo

- **Nome:** Alexandre Mendes  
  **Número de Estudante:** 111026  
  **GitHub:** [AlexandreMendesISCTE](https://github.com/AlexandreMendesISCTE)

- **Nome:** Edgar Cardoso  
  **Número de Estudante:** 111340  
  **GitHub:** [EdgarCardoso04](https://github.com/EdgarCardoso04)

## Problemas Conhecidos

Inicialmente, tivemos algumas dificuldades em fazer o projeto com as bibliotecas, especialmente para representar as propriedades com os multipolígonos. Na primeira fase do projeto, mostramos os pontos iniciais com apenas duas bibliotecas. Tentámos adicionar o JavaFX, mas enfrentámos erros no computador do Edgar. Optámos então por outra abordagem.

Após a primeira entrega, continuámos a desenvolver o projeto, tornando-o totalmente funcional e disponível [neste repositório](https://github.com/AlexandreMendesISCTE/ES-2024-1Sem-LETI-GrupoE_v1). Posteriormente, decidimos refazer o projeto, aplicando os métodos aprendidos ao longo das aulas e organizando o código em diversas bibliotecas.

## Conclusão

Este projeto foi uma experiência bastante interessante e desafiante. A combinação de conceitos teóricos e práticos proporcionou uma aprendizagem significativa, especialmente ao lidar com problemas reais e encontrar soluções criativas. A implementação de funcionalidades complexas, como a representação gráfica e o uso de bibliotecas avançadas, foi uma oportunidade para aprimorar competências técnicas e de trabalho em equipa. Estamos satisfeitos com o resultado final e acreditamos que esta aplicação tem potencial para contribuir para uma gestão territorial mais eficiente e sustentável.

