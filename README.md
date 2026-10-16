# 100 exemplos de CRUD com Java + Spring Boot + SQL Server

Coleção de 100 sistemas pequenos e didáticos, todos com a mesma receita:

- Java 17+ e Spring Boot 3.5.16 (Spring Web + Spring Data JPA);
- SQL Server como banco, com as tabelas criadas automaticamente pelo Hibernate;
- 2 ou 3 entidades relacionadas por `@ManyToOne`;
- CRUD REST completo por entidade (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`);
- 52 projetos usam Lombok e 32 têm uma camada `Service` entre o controller e o repositório (os demais chamam o repositório direto no controller).

Cada projeto é independente: tem o próprio `pom.xml`, `run.ps1`/`run.bat` e `README.md` com as entidades, endpoints e exemplos de `curl`.

## Pré-requisitos

Somente dois programas precisam estar instalados:

1. **JDK 17 ou superior** (precisa ser o JDK, com `javac`; ex.: [Temurin](https://adoptium.net/temurin/releases/)).
2. **SQL Server** (Express serve; o script de setup foi testado com `SQLEXPRESS`).

O Maven e todas as dependências são baixados automaticamente pelo `run.ps1` na primeira execução (cache em `%LOCALAPPDATA%\spring-crud-exemplos` e `%USERPROFILE%\.m2`).

## Passo a passo

1. **Prepare o SQL Server uma única vez**, em um PowerShell **como Administrador**:

   ```powershell
   .\setup-sqlserver.ps1
   ```

   O script habilita o TCP/IP na porta 1433, liga a autenticação mista, cria o login `projeto` (senha `Projeto@123`), recria o banco `projeto` vazio e, no final, imprime o passo a passo para apontar o projeto Java para esse banco.

   Parâmetros opcionais: `-Instancia SQLEXPRESS`, `-Banco projeto`, `-Usuario projeto`, `-Senha 'Projeto@123'`, `-Porta 1433`.

2. **Rode um projeto** (duplo clique em `run.bat` ou, no terminal, `.\run.ps1` dentro da pasta do projeto):

   ```powershell
   cd projetos\001-biblioteca
   .\run.ps1
   ```

   A API sobe em http://localhost:8080. Todos os projetos usam o banco `projeto` e a porta 8080, então rode um de cada vez. **Ao trocar de projeto, rode o `setup-sqlserver.ps1` de novo** para zerar o banco: vários projetos usam tabelas com o mesmo nome (`produtos`, `clientes`...) com colunas diferentes, e o `ddl-auto=update` não remove nem altera colunas que já existem.

3. **Teste os endpoints** com `curl`, navegador ou qualquer cliente HTTP. Cada `README.md` de projeto traz os comandos prontos.

## Estrutura de um projeto

```
NNN-slug/
  pom.xml
  run.ps1 / run.bat
  README.md
  src/main/resources/application.properties
  src/main/java/com/exemplo/<slug>/
    Application.java
    model/        entidades JPA (@Entity)
    repository/   interfaces JpaRepository
    service/      (opcional) regras entre controller e repositório
    controller/   endpoints REST (@RestController)
```

## Documentação de apoio

- [docs/01-orientacao-a-objetos-java.md](docs/01-orientacao-a-objetos-java.md) — classes, interfaces, herança, tipos, generics, Lombok.
- [docs/02-arquitetura-mvc-spring.md](docs/02-arquitetura-mvc-spring.md) — separação em pacotes, entidades, repositórios, controllers, fluxo de uma requisição.
- [docs/03-sql-server-guia-rapido.md](docs/03-sql-server-guia-rapido.md) — conexão, sintaxe T-SQL, `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `JOIN`, erros comuns.

## Gerador

Os projetos são gerados a partir de `gerador/spec.json` por `gerador\gerar.ps1` (PowerShell 5.1+, sem dependências extras). Para regenerar tudo: `.\gerador\gerar.ps1`; para regenerar só alguns: `.\gerador\gerar.ps1 12 35`. O contrato da especificação está em `gerador/CONTRATO-SPEC.md`.

## Catálogo dos projetos

### Comércio (9)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 003 | [Loja de Roupas](projetos/003-loja-roupas/) | Cadastro de categorias e peças de roupa com preço e estoque. | Categoria, Roupa | ✔ | ✔ |
| 006 | [Petshop](projetos/006-petshop/) | Cadastro de tutores, animais e serviços de banho e tosa. | Tutor, Animal, Atendimento |  | ✔ |
| 008 | [Livraria](projetos/008-livraria/) | Controle de editoras e livros à venda em uma livraria. | Editora, Livro |  |  |
| 012 | [Ótica](projetos/012-otica/) | Cadastro de marcas e armações de óculos disponíveis na loja. | Marca, Armacao |  | ✔ |
| 013 | [Loja de Games](projetos/013-loja-games/) | Catálogo de plataformas e jogos novos e usados. | Plataforma, Jogo | ✔ |  |
| 014 | [Sebo](projetos/014-sebo/) | Controle de gêneros e exemplares de livros usados. | Genero, Exemplar |  |  |
| 019 | [Mercearia](projetos/019-mercearia/) | Controle de fornecedores e produtos de uma mercearia de bairro. | Fornecedor, Produto | ✔ | ✔ |
| 020 | [Papelaria](projetos/020-papelaria/) | Controle de categorias, produtos e vendas de uma papelaria. | Categoria, Produto, Venda |  |  |
| 025 | [Floricultura](projetos/025-floricultura/) | Registro de clientes e encomendas de arranjos de flores. | Cliente, Encomenda | ✔ |  |

### Educação (7)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 001 | [Biblioteca](projetos/001-biblioteca/) | Controle de autores e livros de uma biblioteca. | Autor, Livro | ✔ |  |
| 004 | [Escola](projetos/004-escola/) | Gerenciamento de turmas e alunos matriculados em uma escola. | Turma, Aluno |  |  |
| 007 | [Cursos Online](projetos/007-cursos-online/) | Plataforma simples de instrutores, cursos e seus módulos. | Instrutor, Curso, Modulo | ✔ |  |
| 010 | [Autoescola](projetos/010-autoescola/) | Agendamento de aulas práticas com instrutores e veículos. | Instrutor, Veiculo, Aula |  |  |
| 015 | [Creche](projetos/015-creche/) | Cadastro de responsáveis e crianças atendidas pela creche. | Responsavel, Crianca | ✔ | ✔ |
| 021 | [Escola de Idiomas](projetos/021-escola-idiomas/) | Organização de idiomas, professores e turmas de uma escola de línguas. | Idioma, Professor, Turma | ✔ |  |
| 022 | [Faculdade](projetos/022-faculdade/) | Cadastro de cursos superiores e suas disciplinas. | Curso, Disciplina |  |  |

### Saúde (9)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 002 | [Clínica Médica](projetos/002-clinica-medica/) | Agendamento de consultas entre médicos e pacientes de uma clínica. | Medico, Paciente, Consulta |  | ✔ |
| 005 | [Farmácia](projetos/005-farmacia/) | Controle de fabricantes e medicamentos de uma farmácia. | Fabricante, Medicamento | ✔ |  |
| 009 | [Academia](projetos/009-academia/) | Gestão de planos e alunos de uma academia de ginástica. | Plano, Aluno | ✔ | ✔ |
| 011 | [Laboratório de Exames](projetos/011-laboratorio-exames/) | Registro de tipos de exame, pacientes e coletas realizadas. | TipoExame, Paciente, Exame | ✔ |  |
| 016 | [Hospital](projetos/016-hospital/) | Controle de alas, leitos e internações de um hospital. | Ala, Leito, Internacao |  |  |
| 017 | [Nutricionista](projetos/017-nutricionista/) | Acompanhamento de pacientes e avaliações nutricionais. | Paciente, Avaliacao | ✔ |  |
| 018 | [Consultório Odontológico](projetos/018-dentista/) | Registro de dentistas e procedimentos realizados no consultório. | Dentista, Procedimento |  |  |
| 023 | [Clínica Veterinária](projetos/023-clinica-veterinaria/) | Controle de animais atendidos e suas vacinas aplicadas. | Animal, Vacina | ✔ |  |
| 024 | [Clínica de Fisioterapia](projetos/024-fisioterapia/) | Agendamento de sessões de fisioterapia com fisioterapeutas. | Fisioterapeuta, Sessao |  | ✔ |

### Serviços (14)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 026 | [Oficina Mecânica](projetos/026-oficina-mecanica/) | Controle de veículos e ordens de serviço de uma oficina mecânica. | Veiculo, OrdemServico | ✔ |  |
| 027 | [Salão de Beleza](projetos/027-salao-beleza/) | Agendamento de atendimentos, clientes e procedimentos de um salão de beleza. | Cliente, Procedimento, Atendimento |  | ✔ |
| 030 | [Lavanderia](projetos/030-lavanderia/) | Registro de clientes e pedidos de lavagem de roupas de uma lavanderia. | Cliente, PedidoLavagem | ✔ | ✔ |
| 033 | [Chaveiro](projetos/033-chaveiro/) | Controle de clientes e chamados de atendimento de um chaveiro. | Cliente, ChamadoChaveiro |  | ✔ |
| 036 | [Assistência Técnica](projetos/036-assistencia-tecnica/) | Controle de clientes, aparelhos e reparos de uma assistência técnica. | Cliente, Aparelho, Reparo | ✔ | ✔ |
| 039 | [Lava-Rápido](projetos/039-lava-rapido/) | Registro de veículos e lavagens realizadas em um lava-rápido. | Veiculo, Lavagem |  | ✔ |
| 042 | [Encanador](projetos/042-encanador/) | Agenda de clientes e visitas técnicas de um serviço de encanamento. | Cliente, VisitaTecnica | ✔ | ✔ |
| 046 | [Dedetizadora](projetos/046-dedetizadora/) | Registro de clientes e aplicações de controle de pragas. | Cliente, Aplicacao | ✔ |  |
| 050 | [Jardinagem](projetos/050-jardinagem/) | Controle de clientes e serviços de jardinagem prestados. | Cliente, ServicoJardim | ✔ |  |
| 096 | [Hotel](projetos/096-hotel/) | Gestão de quartos, hóspedes e reservas de um hotel. | Quarto, Hospede, Reserva | ✔ | ✔ |
| 097 | [Pousada](projetos/097-pousada/) | Controle de chalés e hospedagens de uma pousada. | Chale, Hospedagem |  |  |
| 098 | [Estacionamento](projetos/098-estacionamento/) | Controle de vagas e tickets de entrada e saída de um estacionamento. | Vaga, Ticket | ✔ |  |
| 099 | [Agência de Viagens](projetos/099-agencia-viagens/) | Venda de pacotes turísticos para clientes de uma agência de viagens. | Pacote, Cliente, Contratacao |  | ✔ |
| 100 | [Locadora de Veículos](projetos/100-locadora/) | Controle da frota e das locações de uma locadora de veículos. | Veiculo, Locacao | ✔ |  |

### Gestão (8)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 028 | [Controle de Estoque](projetos/028-controle-estoque/) | Cadastro de produtos por categoria e registro das movimentações de estoque. | CategoriaProduto, Produto, Movimentacao | ✔ |  |
| 031 | [Recursos Humanos](projetos/031-recursos-humanos/) | Cadastro de departamentos e funcionários de uma empresa. | Departamento, Funcionario |  |  |
| 034 | [Gestão de Projetos](projetos/034-gestao-projetos/) | Acompanhamento de projetos, responsáveis e tarefas de uma equipe. | Projeto, Responsavel, Tarefa | ✔ |  |
| 037 | [Reserva de Salas](projetos/037-reserva-salas/) | Cadastro de salas de reunião e das reservas feitas em cada uma. | Sala, Reserva |  |  |
| 040 | [Frota de Veículos](projetos/040-frota-veiculos/) | Gerenciamento da frota de uma empresa com manutenções e abastecimentos. | Veiculo, Manutencao, Abastecimento | ✔ |  |
| 043 | [Controle de Patrimônio](projetos/043-patrimonio/) | Cadastro de setores e dos bens patrimoniais de cada setor. | Setor, Bem |  |  |
| 045 | [Controle de Contratos](projetos/045-controle-contratos/) | Gestão de empresas contratadas, contratos vigentes e seus aditivos. | Empresa, Contrato, Aditivo |  | ✔ |
| 047 | [Fornecedores](projetos/047-fornecedores/) | Cadastro de fornecedores por categoria e das cotações de preço recebidas. | CategoriaFornecedor, Fornecedor, Cotacao |  |  |

### Finanças (8)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 029 | [Contas a Pagar](projetos/029-contas-a-pagar/) | Controle de fornecedores e das contas a pagar com seus vencimentos. | Fornecedor, ContaPagar |  |  |
| 032 | [Orçamento Doméstico](projetos/032-orcamento-domestico/) | Lançamento de receitas e despesas da casa organizados por categoria de gasto. | CategoriaGasto, Lancamento | ✔ |  |
| 035 | [Cofrinho de Metas](projetos/035-cofrinho-metas/) | Definição de metas de economia e registro dos depósitos feitos em cada uma. | Meta, Deposito |  |  |
| 038 | [Cartões de Crédito](projetos/038-cartoes-credito/) | Controle de cartões de crédito, faturas e compras realizadas. | Cartao, Fatura, Compra | ✔ |  |
| 041 | [Investimentos](projetos/041-investimentos/) | Organização de carteiras de investimento e dos ativos que as compõem. | Carteira, Ativo |  |  |
| 044 | [Empréstimos](projetos/044-emprestimos/) | Controle de empréstimos e das parcelas a receber de cada um. | Emprestimo, Parcela | ✔ |  |
| 048 | [Caixa Diário](projetos/048-caixa-diario/) | Abertura de caixas e registro das entradas e saídas do dia. | Caixa, MovimentoCaixa | ✔ | ✔ |
| 049 | [Contas Bancárias](projetos/049-contas-bancarias/) | Controle de bancos, contas bancárias e suas transações. | Banco, ContaBancaria, Transacao |  |  |

### Lazer e Cultura (8)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 051 | [Cinema](projetos/051-cinema/) | Controle de filmes em cartaz e das sessões de um cinema. | Filme, Sessao | ✔ |  |
| 052 | [Teatro](projetos/052-teatro/) | Cadastro de peças teatrais e de suas apresentações. | Peca, Apresentacao |  |  |
| 053 | [Museu](projetos/053-museu/) | Gerenciamento de exposições e das visitas recebidas por um museu. | Exposicao, Visita | ✔ | ✔ |
| 054 | [Clube de Leitura](projetos/054-clube-leitura/) | Acompanhamento dos livros lidos pelos membros de um clube de leitura. | Livro, Membro, Leitura |  |  |
| 055 | [Locadora de Jogos](projetos/055-locadora-jogos/) | Locação de jogos de videogame para clientes cadastrados. | Jogo, Cliente, Locacao | ✔ |  |
| 056 | [Campeonato de Futebol](projetos/056-campeonato/) | Cadastro de times e jogadores de um campeonato de futebol amador. | Time, Jogador |  | ✔ |
| 057 | [Playlist de Músicas](projetos/057-playlist/) | Organização de artistas e músicas favoritas em uma playlist. | Artista, Musica | ✔ |  |
| 058 | [Coleção de Filmes](projetos/058-colecao-filmes/) | Catálogo pessoal de filmes organizados por diretor. | Diretor, Filme |  |  |

### Alimentação (8)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 059 | [Restaurante](projetos/059-restaurante/) | Controle de mesas e dos pedidos feitos em um restaurante. | Mesa, Pedido | ✔ |  |
| 060 | [Pizzaria](projetos/060-pizzaria/) | Registro de clientes, sabores de pizza e pedidos de uma pizzaria. | Cliente, Pizza, Pedido |  | ✔ |
| 061 | [Lanchonete](projetos/061-lanchonete/) | Cardápio de lanches organizado por categorias. | Categoria, Lanche | ✔ |  |
| 062 | [Cafeteria](projetos/062-cafeteria/) | Controle de fornecedores e lotes de grãos de café de uma cafeteria. | Fornecedor, GraoCafe |  |  |
| 063 | [Padaria](projetos/063-padaria/) | Registro dos padeiros e das fornadas produzidas por dia. | Padeiro, Fornada | ✔ | ✔ |
| 064 | [Delivery de Comida](projetos/064-delivery/) | Entregas de restaurantes parceiros realizadas por entregadores. | Restaurante, Entregador, Entrega |  |  |
| 065 | [Livro de Receitas](projetos/065-receitas/) | Cadastro de receitas culinárias e de seus ingredientes. | Receita, Ingrediente | ✔ |  |
| 066 | [Adega de Vinhos](projetos/066-adega/) | Controle de vinícolas e dos vinhos guardados em uma adega. | Vinicola, Vinho |  | ✔ |

### Tecnologia (9)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 067 | [Helpdesk](projetos/067-helpdesk/) | Abertura e acompanhamento de chamados de suporte técnico. | Solicitante, Chamado | ✔ |  |
| 068 | [Inventário de Equipamentos de TI](projetos/068-inventario-ti/) | Controle dos equipamentos de informática distribuídos entre os setores da empresa. | Setor, Equipamento |  |  |
| 069 | [Controle de Bugs](projetos/069-bugs/) | Registro de projetos de software e dos bugs encontrados em cada um. | Projeto, Bug | ✔ | ✔ |
| 070 | [Blog](projetos/070-blog/) | Publicação de posts por autores, com comentários dos leitores. | Autor, Post, Comentario |  |  |
| 071 | [Encurtador de Links](projetos/071-encurtador-links/) | Criação de links curtos por usuário, com contagem de cliques. | Usuario, LinkCurto | ✔ |  |
| 072 | [Licenças de Software](projetos/072-licencas-software/) | Controle das licenças adquiridas para cada software da empresa. | Software, Licenca |  | ✔ |
| 073 | [Servidores e Aplicações](projetos/073-servidores/) | Cadastro de servidores e das aplicações hospedadas em cada um. | Servidor, Aplicacao | ✔ |  |
| 074 | [Chaves de API](projetos/074-api-keys/) | Emissão de chaves de API por cliente e registro do consumo de cada chave. | Cliente, ApiKey, Consumo |  |  |
| 075 | [Fórum de Discussão](projetos/075-forum/) | Fórum com membros, tópicos de discussão e respostas. | Membro, Topico, Resposta | ✔ | ✔ |

### Comunidade (10)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 076 | [Igreja](projetos/076-igreja/) | Cadastro de células e membros de uma igreja. | Celula, Membro | ✔ |  |
| 077 | [Condomínio](projetos/077-condominio/) | Controle de apartamentos e moradores de um condomínio residencial. | Apartamento, Morador |  |  |
| 078 | [Eventos](projetos/078-eventos/) | Organização de eventos comunitários e das inscrições dos participantes. | Evento, Inscricao | ✔ | ✔ |
| 079 | [Agenda de Contatos](projetos/079-agenda-contatos/) | Agenda pessoal com contatos organizados por grupos. | Grupo, Contato |  |  |
| 080 | [ONG](projetos/080-ong/) | Gestão das ações sociais de uma ONG e dos voluntários participantes. | Acao, Voluntario | ✔ |  |
| 081 | [Cooperativa](projetos/081-cooperativa/) | Controle de cooperados e das contribuições mensais da cooperativa. | Cooperado, Contribuicao |  | ✔ |
| 082 | [Associação de Bairro](projetos/082-associacao-bairro/) | Cadastro de ruas e associados de uma associação de moradores. | Rua, Associado | ✔ |  |
| 083 | [Clube Social](projetos/083-clube-social/) | Gerenciamento de modalidades esportivas e sócios de um clube. | Modalidade, Socio |  | ✔ |
| 084 | [Brechó Solidário](projetos/084-brecho-solidario/) | Controle de peças doadas e vendidas em um brechó beneficente. | Categoria, Peca | ✔ |  |
| 085 | [Campanha de Doações](projetos/085-campanha-doacoes/) | Acompanhamento de campanhas de arrecadação, doadores e doações recebidas. | Campanha, Doador, Doacao |  |  |

### Agro e Logística (10)

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
| 086 | [Fazenda](projetos/086-fazenda/) | Controle de talhões e plantios de uma propriedade rural. | Talhao, Plantio | ✔ |  |
| 087 | [Rebanho](projetos/087-rebanho/) | Gestão de lotes e animais de um rebanho bovino. | Lote, Animal |  | ✔ |
| 088 | [Hortifruti](projetos/088-hortifruti/) | Cadastro de produtores e dos hortifrútis que eles fornecem. | Produtor, Produto | ✔ |  |
| 089 | [Silo de Grãos](projetos/089-silo/) | Controle de silos e das cargas de grãos armazenadas. | Silo, Carga |  |  |
| 090 | [Transportadora](projetos/090-transportadora/) | Gerenciamento de motoristas, veículos e entregas de uma transportadora. | Motorista, Veiculo, Entrega | ✔ | ✔ |
| 091 | [Frota Agrícola](projetos/091-frota-agricola/) | Controle de máquinas agrícolas e de suas manutenções. | Maquina, Manutencao |  |  |
| 092 | [Apicultura](projetos/092-apicultura/) | Acompanhamento de apiários, colmeias e colheitas de mel. | Apiario, Colmeia, Colheita | ✔ |  |
| 093 | [Viveiro de Mudas](projetos/093-viveiro/) | Cadastro de espécies e lotes de mudas de um viveiro. | Especie, Muda |  | ✔ |
| 094 | [Pesqueiro](projetos/094-pesqueiro/) | Controle de tanques de peixes e das despescas realizadas. | Tanque, Despesca | ✔ |  |
| 095 | [Cooperativa Agrícola](projetos/095-coop-agricola/) | Registro de agricultores e dos recebimentos de produção da cooperativa. | Agricultor, Recebimento |  |  |
