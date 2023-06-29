## :recycle: Eco Corporation

Solução API Rest que permite fixar uma cota trimestral para cada vendedor e monitorar o progresso de cada um na consecução da cota. 
Para ajudar a empresa a monitorar o desempenho com mais eficácia, a solução manipula os dados de desempenho de vendas e cotas. 

Tabela abaixo mostra uma amostragem dos dados:

![image](https://user-images.githubusercontent.com/61856025/128608732-2ae392b9-7c4b-45ac-9237-615d9dedc5fa.png)

### Funcionalidades do sistema
- uso dos quatro métodos (GET, POST PUT, DELETE) para o recurso funcionário com persistência no banco de dados
- monitoramento de desempenho por funcionário, por região e por trimestre
- geração de relatório geral de todas as informações armazenadas

### Recursos utilizados
- IDE Eclipse 2021-03
- Linguagem JAVA
- Banco de dados MYSQL
- Postman
- Xampp ou DBeaver
- Spring Boot
- JPA

### Endpoints
- GET: http://localhost:8080/amostra/{codigo} *Retorna o registro da tabela Amostra que possui o codigo informado no request*
- POST: http://localhost:8080/amostra/criar *Cria um novo registro na tabela Amostra*
- PUT: http://localhost:8080/amostra/atualizar/{codigo} *Atualiza as informações do registro na tabela Amostra que possui o codigo informado no request*
- DELETE: http://localhost:8080/amostra/remover/{codigo} *Remove o registro da tabela Amostra que possui o codigo informado no request*
- GET: http://localhost:8080/amostra/desempenho?funcionario=Sandra *Retorna o desempenho pessoal da funcionaria Sandra*
- GET: http://localhost:8080/amostra/desempenho?regiao=Oeste *Retorna o desempenho total da região Oeste*
- GET: http://localhost:8080/amostra/desempenho?trimestre=2020-3 *Retorna o desempenho total do trimestre 2020-3*
- GET: http://localhost:8080/amostra/geraRelatorio *Gera um relatório excel com todas as informações da tabela Amostra, que é a mesma da primeira imagem deste readme*
- No arquivo ```Eco Corporation.postman_collection.json``` que está na raiz do projeto, contém o arquivo que pode ser importado dentro do Postman.

### Executar Eco Corporation usando arquivo jar
- Criar o banco de dados localmente de nome ```eco-corporation```,
- Fazer o download deste projeto em algum diretório local,
- Abrir algum terminal de comando da sua máquina (Prompt de Comando, Windows PowerShell),
- Executar o comando ```mvn clean package```,
- Executar o comando ```cd target```,
- Executar o comando ```java -jar ecocorporation-0.0.1-SNAPSHOT.jar```,
- Aguardar pela mensagem ```Started EcocorporationApplication in n seconds```
