# 150 exemplos de CRUD com Java + Spring Boot + SQL Server ou H2

Coleção de 150 sistemas pequenos e didáticos, todos com a mesma receita:

- Java 17+ e Spring Boot 3.5.16 (Spring Web + Spring Data JPA);
- 100 projetos usam **SQL Server** (001–100) e 50 usam **H2** em arquivo (101 em diante), sempre com as tabelas criadas automaticamente pelo Hibernate;
- 2 ou 3 entidades relacionadas por `@ManyToOne`;
- CRUD REST completo por entidade (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`);
- 77 projetos usam Lombok e 49 têm uma camada `Service` entre o controller e o repositório (os demais chamam o repositório direto no controller).

Cada projeto é independente: tem o próprio `pom.xml`, `run.ps1`/`run.bat` e `README.md` com as entidades, endpoints e exemplos de `curl`.

## Pré-requisitos

Somente dois programas precisam estar instalados:

1. **JDK 17 ou superior** (precisa ser o JDK, com `javac`; ex.: [Temurin](https://adoptium.net/temurin/releases/)).
2. **SQL Server** (Express serve; o script de setup foi testado com `SQLEXPRESS`), apenas para os projetos 001–100.

Os projetos H2 não precisam de banco instalado: o H2 é uma biblioteca Java baixada como dependência, e cada projeto grava os dados na própria pasta `data\`.

O Maven, o Tomcat 10.1 e todas as dependências são baixados automaticamente pelo `run.ps1` na primeira execução (cache em `%LOCALAPPDATA%\spring-crud-exemplos` e `%USERPROFILE%\.m2`). Cada projeto é empacotado como WAR (`ROOT.war`) e implantado em um Tomcat externo; a porta é configurável por `-Porta`.

## Passo a passo

1. **Prepare o SQL Server uma única vez** (só para os projetos SQL Server), em um PowerShell **como Administrador**:

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

   Nos projetos H2 isso não é necessário: cada um tem o próprio arquivo de banco. Para zerar, pare o projeto e apague a pasta `data`. O console do H2 fica em http://localhost:8080/h2-console.

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

- [docs/01-orientacao-a-objetos-java.md](docs/01-orientacao-a-objetos-java.md) — classes, interfaces, herança, tipos, generics, lambdas, `Optional`, Lombok.
- [docs/02-arquitetura-mvc-spring.md](docs/02-arquitetura-mvc-spring.md) — separação em pacotes, entidades, repositórios, controllers, fluxo de uma requisição.
- [docs/03-sql-server-guia-rapido.md](docs/03-sql-server-guia-rapido.md) — conexão, sintaxe T-SQL, `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `JOIN`, erros comuns.
- [docs/04-requisicoes-respostas-e-injecao.md](docs/04-requisicoes-respostas-e-injecao.md) — parâmetros e corpo das requisições, status e JSON das respostas, tratamento de erros, service ↔ banco, transações, injeção de dependência.
- [docs/05-configuracao-properties-sqlserver-tomcat.md](docs/05-configuracao-properties-sqlserver-tomcat.md) — `application.properties`, variáveis de ambiente e perfis, configuração do SQL Server e do H2, WAR e Tomcat externo.

## Gerador

Os projetos são gerados a partir de `gerador/spec.json` por `gerador\gerar.ps1` (PowerShell 5.1+, sem dependências extras). Para regenerar tudo: `.\gerador\gerar.ps1`; para regenerar só alguns: `.\gerador\gerar.ps1 12 35`. O contrato da especificação está em `gerador/CONTRATO-SPEC.md`.

## Catálogo dos projetos

### Comércio (14)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 003 | [Loja de Roupas](projetos/003-loja-roupas/) | Cadastro de categorias e peças de roupa com preço e estoque. | Categoria, Roupa | SQL Server | ✔ | ✔ |
| 006 | [Petshop](projetos/006-petshop/) | Cadastro de tutores, animais e serviços de banho e tosa. | Tutor, Animal, Atendimento | SQL Server |  | ✔ |
| 008 | [Livraria](projetos/008-livraria/) | Controle de editoras e livros à venda em uma livraria. | Editora, Livro | SQL Server |  |  |
| 012 | [Ótica](projetos/012-otica/) | Cadastro de marcas e armações de óculos disponíveis na loja. | Marca, Armacao | SQL Server |  | ✔ |
| 013 | [Loja de Games](projetos/013-loja-games/) | Catálogo de plataformas e jogos novos e usados. | Plataforma, Jogo | SQL Server | ✔ |  |
| 014 | [Sebo](projetos/014-sebo/) | Controle de gêneros e exemplares de livros usados. | Genero, Exemplar | SQL Server |  |  |
| 019 | [Mercearia](projetos/019-mercearia/) | Controle de fornecedores e produtos de uma mercearia de bairro. | Fornecedor, Produto | SQL Server | ✔ | ✔ |
| 020 | [Papelaria](projetos/020-papelaria/) | Controle de categorias, produtos e vendas de uma papelaria. | Categoria, Produto, Venda | SQL Server |  |  |
| 025 | [Floricultura](projetos/025-floricultura/) | Registro de clientes e encomendas de arranjos de flores. | Cliente, Encomenda | SQL Server | ✔ |  |
| 101 | [Joalheria](projetos/101-joalheria/) | Controle de categorias, joias e vendas de uma joalheria. | Categoria, Joia, Venda | H2 | ✔ | ✔ |
| 102 | [Loja de Eletrônicos](projetos/102-loja-eletronicos/) | Cadastro de marcas e aparelhos eletrônicos à venda. | Marca, Aparelho | H2 |  |  |
| 103 | [Loja de Móveis](projetos/103-loja-moveis/) | Catálogo de ambientes e móveis de uma loja. | Ambiente, Movel | H2 | ✔ |  |
| 104 | [Perfumaria](projetos/104-perfumaria/) | Controle de fabricantes e perfumes de uma perfumaria. | Fabricante, Perfume | H2 |  | ✔ |
| 105 | [Loja de Esportes](projetos/105-loja-esportes/) | Estoque de artigos esportivos organizados por modalidade. | Modalidade, Artigo | H2 | ✔ |  |

### Educação (11)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 001 | [Biblioteca](projetos/001-biblioteca/) | Controle de autores e livros de uma biblioteca. | Autor, Livro | SQL Server | ✔ |  |
| 004 | [Escola](projetos/004-escola/) | Gerenciamento de turmas e alunos matriculados em uma escola. | Turma, Aluno | SQL Server |  |  |
| 007 | [Cursos Online](projetos/007-cursos-online/) | Plataforma simples de instrutores, cursos e seus módulos. | Instrutor, Curso, Modulo | SQL Server | ✔ |  |
| 010 | [Autoescola](projetos/010-autoescola/) | Agendamento de aulas práticas com instrutores e veículos. | Instrutor, Veiculo, Aula | SQL Server |  |  |
| 015 | [Creche](projetos/015-creche/) | Cadastro de responsáveis e crianças atendidas pela creche. | Responsavel, Crianca | SQL Server | ✔ | ✔ |
| 021 | [Escola de Idiomas](projetos/021-escola-idiomas/) | Organização de idiomas, professores e turmas de uma escola de línguas. | Idioma, Professor, Turma | SQL Server | ✔ |  |
| 022 | [Faculdade](projetos/022-faculdade/) | Cadastro de cursos superiores e suas disciplinas. | Curso, Disciplina | SQL Server |  |  |
| 106 | [Escola de Música](projetos/106-escola-musica/) | Agendamento de aulas de instrumentos entre professores e alunos. | Instrumento, Professor, Aula | H2 |  |  |
| 107 | [Reforço Escolar](projetos/107-reforco-escolar/) | Registro de alunos e sessões de reforço escolar. | Aluno, Sessao | H2 | ✔ | ✔ |
| 108 | [Transporte Escolar](projetos/108-transporte-escolar/) | Gestão de veículos, rotas e passageiros do transporte escolar. | Veiculo, Rota, Passageiro | H2 |  |  |
| 109 | [Simulados para Concursos](projetos/109-simulados-concursos/) | Cadastro de concursos e dos simulados aplicados para cada um. | Concurso, Simulado | H2 | ✔ |  |

### Saúde (13)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 002 | [Clínica Médica](projetos/002-clinica-medica/) | Agendamento de consultas entre médicos e pacientes de uma clínica. | Medico, Paciente, Consulta | SQL Server |  | ✔ |
| 005 | [Farmácia](projetos/005-farmacia/) | Controle de fabricantes e medicamentos de uma farmácia. | Fabricante, Medicamento | SQL Server | ✔ |  |
| 009 | [Academia](projetos/009-academia/) | Gestão de planos e alunos de uma academia de ginástica. | Plano, Aluno | SQL Server | ✔ | ✔ |
| 011 | [Laboratório de Exames](projetos/011-laboratorio-exames/) | Registro de tipos de exame, pacientes e coletas realizadas. | TipoExame, Paciente, Exame | SQL Server | ✔ |  |
| 016 | [Hospital](projetos/016-hospital/) | Controle de alas, leitos e internações de um hospital. | Ala, Leito, Internacao | SQL Server |  |  |
| 017 | [Nutricionista](projetos/017-nutricionista/) | Acompanhamento de pacientes e avaliações nutricionais. | Paciente, Avaliacao | SQL Server | ✔ |  |
| 018 | [Consultório Odontológico](projetos/018-dentista/) | Registro de dentistas e procedimentos realizados no consultório. | Dentista, Procedimento | SQL Server |  |  |
| 023 | [Clínica Veterinária](projetos/023-clinica-veterinaria/) | Controle de animais atendidos e suas vacinas aplicadas. | Animal, Vacina | SQL Server | ✔ |  |
| 024 | [Clínica de Fisioterapia](projetos/024-fisioterapia/) | Agendamento de sessões de fisioterapia com fisioterapeutas. | Fisioterapeuta, Sessao | SQL Server |  | ✔ |
| 110 | [Clínica de Psicologia](projetos/110-clinica-psicologia/) | Agenda de sessões entre psicólogos e pacientes. | Psicologo, Paciente, Sessao | H2 |  | ✔ |
| 111 | [Clínica de Vacinação](projetos/111-clinica-vacinacao/) | Controle de vacinas disponíveis e das aplicações realizadas. | Vacina, Aplicacao | H2 | ✔ |  |
| 112 | [Banco de Sangue](projetos/112-banco-sangue/) | Registro de doadores e das doações de sangue realizadas. | Doador, Doacao | H2 |  |  |
| 113 | [Clínica de Estética](projetos/113-clinica-estetica/) | Cadastro de procedimentos estéticos e dos atendimentos realizados. | Procedimento, Atendimento | H2 | ✔ | ✔ |

### Serviços (19)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 026 | [Oficina Mecânica](projetos/026-oficina-mecanica/) | Controle de veículos e ordens de serviço de uma oficina mecânica. | Veiculo, OrdemServico | SQL Server | ✔ |  |
| 027 | [Salão de Beleza](projetos/027-salao-beleza/) | Agendamento de atendimentos, clientes e procedimentos de um salão de beleza. | Cliente, Procedimento, Atendimento | SQL Server |  | ✔ |
| 030 | [Lavanderia](projetos/030-lavanderia/) | Registro de clientes e pedidos de lavagem de roupas de uma lavanderia. | Cliente, PedidoLavagem | SQL Server | ✔ | ✔ |
| 033 | [Chaveiro](projetos/033-chaveiro/) | Controle de clientes e chamados de atendimento de um chaveiro. | Cliente, ChamadoChaveiro | SQL Server |  | ✔ |
| 036 | [Assistência Técnica](projetos/036-assistencia-tecnica/) | Controle de clientes, aparelhos e reparos de uma assistência técnica. | Cliente, Aparelho, Reparo | SQL Server | ✔ | ✔ |
| 039 | [Lava-Rápido](projetos/039-lava-rapido/) | Registro de veículos e lavagens realizadas em um lava-rápido. | Veiculo, Lavagem | SQL Server |  | ✔ |
| 042 | [Encanador](projetos/042-encanador/) | Agenda de clientes e visitas técnicas de um serviço de encanamento. | Cliente, VisitaTecnica | SQL Server | ✔ | ✔ |
| 046 | [Dedetizadora](projetos/046-dedetizadora/) | Registro de clientes e aplicações de controle de pragas. | Cliente, Aplicacao | SQL Server | ✔ |  |
| 050 | [Jardinagem](projetos/050-jardinagem/) | Controle de clientes e serviços de jardinagem prestados. | Cliente, ServicoJardim | SQL Server | ✔ |  |
| 096 | [Hotel](projetos/096-hotel/) | Gestão de quartos, hóspedes e reservas de um hotel. | Quarto, Hospede, Reserva | SQL Server | ✔ | ✔ |
| 097 | [Pousada](projetos/097-pousada/) | Controle de chalés e hospedagens de uma pousada. | Chale, Hospedagem | SQL Server |  |  |
| 098 | [Estacionamento](projetos/098-estacionamento/) | Controle de vagas e tickets de entrada e saída de um estacionamento. | Vaga, Ticket | SQL Server | ✔ |  |
| 099 | [Agência de Viagens](projetos/099-agencia-viagens/) | Venda de pacotes turísticos para clientes de uma agência de viagens. | Pacote, Cliente, Contratacao | SQL Server |  | ✔ |
| 100 | [Locadora de Veículos](projetos/100-locadora/) | Controle da frota e das locações de uma locadora de veículos. | Veiculo, Locacao | SQL Server | ✔ |  |
| 114 | [Escritório de Advocacia](projetos/114-escritorio-advocacia/) | Acompanhamento de clientes, processos e audiências de um escritório. | Cliente, Processo, Audiencia | H2 |  |  |
| 115 | [Ateliê de Costura](projetos/115-atelie-costura/) | Controle de clientes e encomendas de peças sob medida. | Cliente, Encomenda | H2 | ✔ |  |
| 116 | [Estúdio de Fotografia](projetos/116-estudio-fotografia/) | Agendamento de ensaios fotográficos para clientes. | Cliente, Ensaio | H2 |  | ✔ |
| 117 | [Empresa de Mudanças](projetos/117-empresa-mudancas/) | Organização de mudanças residenciais, clientes e caminhões da empresa. | Cliente, Caminhao, Mudanca | H2 | ✔ |  |
| 118 | [Gráfica Rápida](projetos/118-grafica-rapida/) | Registro de clientes e dos serviços de impressão solicitados. | Cliente, Impressao | H2 |  |  |

### Gestão (13)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 028 | [Controle de Estoque](projetos/028-controle-estoque/) | Cadastro de produtos por categoria e registro das movimentações de estoque. | CategoriaProduto, Produto, Movimentacao | SQL Server | ✔ |  |
| 031 | [Recursos Humanos](projetos/031-recursos-humanos/) | Cadastro de departamentos e funcionários de uma empresa. | Departamento, Funcionario | SQL Server |  |  |
| 034 | [Gestão de Projetos](projetos/034-gestao-projetos/) | Acompanhamento de projetos, responsáveis e tarefas de uma equipe. | Projeto, Responsavel, Tarefa | SQL Server | ✔ |  |
| 037 | [Reserva de Salas](projetos/037-reserva-salas/) | Cadastro de salas de reunião e das reservas feitas em cada uma. | Sala, Reserva | SQL Server |  |  |
| 040 | [Frota de Veículos](projetos/040-frota-veiculos/) | Gerenciamento da frota de uma empresa com manutenções e abastecimentos. | Veiculo, Manutencao, Abastecimento | SQL Server | ✔ |  |
| 043 | [Controle de Patrimônio](projetos/043-patrimonio/) | Cadastro de setores e dos bens patrimoniais de cada setor. | Setor, Bem | SQL Server |  |  |
| 045 | [Controle de Contratos](projetos/045-controle-contratos/) | Gestão de empresas contratadas, contratos vigentes e seus aditivos. | Empresa, Contrato, Aditivo | SQL Server |  | ✔ |
| 047 | [Fornecedores](projetos/047-fornecedores/) | Cadastro de fornecedores por categoria e das cotações de preço recebidas. | CategoriaFornecedor, Fornecedor, Cotacao | SQL Server |  |  |
| 119 | [Gestão de Obras](projetos/119-gestao-obras/) | Acompanhamento de obras, etapas e materiais utilizados. | Obra, Etapa, Material | H2 | ✔ | ✔ |
| 120 | [Controle de Visitantes](projetos/120-controle-visitantes/) | Registro de entrada e saída de visitantes por anfitrião. | Anfitriao, Visita | H2 |  |  |
| 121 | [Ouvidoria](projetos/121-ouvidoria/) | Recebimento de manifestações de clientes encaminhadas aos departamentos. | Departamento, Manifestacao | H2 | ✔ |  |
| 122 | [Protocolo de Documentos](projetos/122-protocolo-documentos/) | Protocolo e tramitação de documentos entre os setores. | Setor, Documento, Tramitacao | H2 |  | ✔ |
| 123 | [Pesquisa de Satisfação](projetos/123-pesquisa-satisfacao/) | Criação de pesquisas de satisfação e registro das respostas recebidas. | Pesquisa, Resposta | H2 | ✔ |  |

### Finanças (12)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 029 | [Contas a Pagar](projetos/029-contas-a-pagar/) | Controle de fornecedores e das contas a pagar com seus vencimentos. | Fornecedor, ContaPagar | SQL Server |  |  |
| 032 | [Orçamento Doméstico](projetos/032-orcamento-domestico/) | Lançamento de receitas e despesas da casa organizados por categoria de gasto. | CategoriaGasto, Lancamento | SQL Server | ✔ |  |
| 035 | [Cofrinho de Metas](projetos/035-cofrinho-metas/) | Definição de metas de economia e registro dos depósitos feitos em cada uma. | Meta, Deposito | SQL Server |  |  |
| 038 | [Cartões de Crédito](projetos/038-cartoes-credito/) | Controle de cartões de crédito, faturas e compras realizadas. | Cartao, Fatura, Compra | SQL Server | ✔ |  |
| 041 | [Investimentos](projetos/041-investimentos/) | Organização de carteiras de investimento e dos ativos que as compõem. | Carteira, Ativo | SQL Server |  |  |
| 044 | [Empréstimos](projetos/044-emprestimos/) | Controle de empréstimos e das parcelas a receber de cada um. | Emprestimo, Parcela | SQL Server | ✔ |  |
| 048 | [Caixa Diário](projetos/048-caixa-diario/) | Abertura de caixas e registro das entradas e saídas do dia. | Caixa, MovimentoCaixa | SQL Server | ✔ | ✔ |
| 049 | [Contas Bancárias](projetos/049-contas-bancarias/) | Controle de bancos, contas bancárias e suas transações. | Banco, ContaBancaria, Transacao | SQL Server |  |  |
| 124 | [Corretora de Seguros](projetos/124-corretora-seguros/) | Controle de segurados, apólices e sinistros de uma corretora. | Segurado, Apolice, Sinistro | H2 |  |  |
| 125 | [Consórcio](projetos/125-consorcio/) | Gestão de grupos de consórcio e das cotas dos participantes. | Grupo, Cota | H2 | ✔ | ✔ |
| 126 | [Casa de Câmbio](projetos/126-casa-cambio/) | Registro de moedas, cotações e operações de câmbio. | Moeda, Operacao | H2 |  |  |
| 127 | [Controle de Assinaturas](projetos/127-controle-assinaturas/) | Acompanhamento de assinaturas recorrentes e seus pagamentos. | Categoria, Assinatura, Pagamento | H2 | ✔ |  |

### Lazer e Cultura (13)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 051 | [Cinema](projetos/051-cinema/) | Controle de filmes em cartaz e das sessões de um cinema. | Filme, Sessao | SQL Server | ✔ |  |
| 052 | [Teatro](projetos/052-teatro/) | Cadastro de peças teatrais e de suas apresentações. | Peca, Apresentacao | SQL Server |  |  |
| 053 | [Museu](projetos/053-museu/) | Gerenciamento de exposições e das visitas recebidas por um museu. | Exposicao, Visita | SQL Server | ✔ | ✔ |
| 054 | [Clube de Leitura](projetos/054-clube-leitura/) | Acompanhamento dos livros lidos pelos membros de um clube de leitura. | Livro, Membro, Leitura | SQL Server |  |  |
| 055 | [Locadora de Jogos](projetos/055-locadora-jogos/) | Locação de jogos de videogame para clientes cadastrados. | Jogo, Cliente, Locacao | SQL Server | ✔ |  |
| 056 | [Campeonato de Futebol](projetos/056-campeonato/) | Cadastro de times e jogadores de um campeonato de futebol amador. | Time, Jogador | SQL Server |  | ✔ |
| 057 | [Playlist de Músicas](projetos/057-playlist/) | Organização de artistas e músicas favoritas em uma playlist. | Artista, Musica | SQL Server | ✔ |  |
| 058 | [Coleção de Filmes](projetos/058-colecao-filmes/) | Catálogo pessoal de filmes organizados por diretor. | Diretor, Filme | SQL Server |  |  |
| 128 | [Casa de Shows](projetos/128-casa-shows/) | Programação de shows, artistas e venda de ingressos. | Artista, Show, Ingresso | H2 |  | ✔ |
| 129 | [Galeria de Arte](projetos/129-galeria-arte/) | Catálogo de artistas e obras expostas em uma galeria. | Artista, Obra | H2 | ✔ |  |
| 130 | [Boliche](projetos/130-boliche/) | Reservas de pistas de boliche feitas pelos clientes. | Pista, Reserva | H2 |  |  |
| 131 | [Gibiteca](projetos/131-gibiteca/) | Controle de coleções de quadrinhos e revistas emprestadas. | Colecao, Revista | H2 | ✔ | ✔ |
| 132 | [Clube de Xadrez](projetos/132-clube-xadrez/) | Organização de jogadores, torneios e partidas de xadrez. | Jogador, Torneio, Partida | H2 |  |  |

### Alimentação (12)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 059 | [Restaurante](projetos/059-restaurante/) | Controle de mesas e dos pedidos feitos em um restaurante. | Mesa, Pedido | SQL Server | ✔ |  |
| 060 | [Pizzaria](projetos/060-pizzaria/) | Registro de clientes, sabores de pizza e pedidos de uma pizzaria. | Cliente, Pizza, Pedido | SQL Server |  | ✔ |
| 061 | [Lanchonete](projetos/061-lanchonete/) | Cardápio de lanches organizado por categorias. | Categoria, Lanche | SQL Server | ✔ |  |
| 062 | [Cafeteria](projetos/062-cafeteria/) | Controle de fornecedores e lotes de grãos de café de uma cafeteria. | Fornecedor, GraoCafe | SQL Server |  |  |
| 063 | [Padaria](projetos/063-padaria/) | Registro dos padeiros e das fornadas produzidas por dia. | Padeiro, Fornada | SQL Server | ✔ | ✔ |
| 064 | [Delivery de Comida](projetos/064-delivery/) | Entregas de restaurantes parceiros realizadas por entregadores. | Restaurante, Entregador, Entrega | SQL Server |  |  |
| 065 | [Livro de Receitas](projetos/065-receitas/) | Cadastro de receitas culinárias e de seus ingredientes. | Receita, Ingrediente | SQL Server | ✔ |  |
| 066 | [Adega de Vinhos](projetos/066-adega/) | Controle de vinícolas e dos vinhos guardados em uma adega. | Vinicola, Vinho | SQL Server |  | ✔ |
| 133 | [Sorveteria](projetos/133-sorveteria/) | Controle de sabores, sorvetes e vendas de uma sorveteria. | Sabor, Sorvete, Venda | H2 | ✔ |  |
| 134 | [Açougue](projetos/134-acougue/) | Cadastro de cortes de carne e registro das vendas por peso. | Corte, Venda | H2 |  | ✔ |
| 135 | [Marmitaria](projetos/135-marmitaria/) | Cardápio de marmitas e encomendas feitas pelos clientes. | Marmita, Encomenda | H2 | ✔ |  |
| 136 | [Food Truck](projetos/136-food-truck/) | Pontos de venda de um food truck e seu faturamento diário. | PontoVenda, Faturamento | H2 |  |  |

### Tecnologia (14)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 067 | [Helpdesk](projetos/067-helpdesk/) | Abertura e acompanhamento de chamados de suporte técnico. | Solicitante, Chamado | SQL Server | ✔ |  |
| 068 | [Inventário de Equipamentos de TI](projetos/068-inventario-ti/) | Controle dos equipamentos de informática distribuídos entre os setores da empresa. | Setor, Equipamento | SQL Server |  |  |
| 069 | [Controle de Bugs](projetos/069-bugs/) | Registro de projetos de software e dos bugs encontrados em cada um. | Projeto, Bug | SQL Server | ✔ | ✔ |
| 070 | [Blog](projetos/070-blog/) | Publicação de posts por autores, com comentários dos leitores. | Autor, Post, Comentario | SQL Server |  |  |
| 071 | [Encurtador de Links](projetos/071-encurtador-links/) | Criação de links curtos por usuário, com contagem de cliques. | Usuario, LinkCurto | SQL Server | ✔ |  |
| 072 | [Licenças de Software](projetos/072-licencas-software/) | Controle das licenças adquiridas para cada software da empresa. | Software, Licenca | SQL Server |  | ✔ |
| 073 | [Servidores e Aplicações](projetos/073-servidores/) | Cadastro de servidores e das aplicações hospedadas em cada um. | Servidor, Aplicacao | SQL Server | ✔ |  |
| 074 | [Chaves de API](projetos/074-api-keys/) | Emissão de chaves de API por cliente e registro do consumo de cada chave. | Cliente, ApiKey, Consumo | SQL Server |  |  |
| 075 | [Fórum de Discussão](projetos/075-forum/) | Fórum com membros, tópicos de discussão e respostas. | Membro, Topico, Resposta | SQL Server | ✔ | ✔ |
| 137 | [Registro de Domínios](projetos/137-registro-dominios/) | Controle de registradores e dos domínios com data de expiração. | Registrador, Dominio | H2 | ✔ | ✔ |
| 138 | [Repositório de Snippets](projetos/138-repositorio-snippets/) | Biblioteca de trechos de código organizados por linguagem, com comentários. | Linguagem, Snippet, Comentario | H2 |  |  |
| 139 | [Feature Flags](projetos/139-feature-flags/) | Gerenciamento de flags de funcionalidades por aplicação. | Aplicacao, Flag | H2 | ✔ |  |
| 140 | [Catálogo de APIs](projetos/140-catalogo-apis/) | Catálogo interno de APIs, equipes responsáveis e seus endpoints. | Equipe, Api, Endpoint | H2 |  | ✔ |
| 141 | [Controle de Backups](projetos/141-controle-backups/) | Acompanhamento de rotinas de backup e de suas execuções. | Rotina, Execucao | H2 | ✔ |  |

### Comunidade (14)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 076 | [Igreja](projetos/076-igreja/) | Cadastro de células e membros de uma igreja. | Celula, Membro | SQL Server | ✔ |  |
| 077 | [Condomínio](projetos/077-condominio/) | Controle de apartamentos e moradores de um condomínio residencial. | Apartamento, Morador | SQL Server |  |  |
| 078 | [Eventos](projetos/078-eventos/) | Organização de eventos comunitários e das inscrições dos participantes. | Evento, Inscricao | SQL Server | ✔ | ✔ |
| 079 | [Agenda de Contatos](projetos/079-agenda-contatos/) | Agenda pessoal com contatos organizados por grupos. | Grupo, Contato | SQL Server |  |  |
| 080 | [ONG](projetos/080-ong/) | Gestão das ações sociais de uma ONG e dos voluntários participantes. | Acao, Voluntario | SQL Server | ✔ |  |
| 081 | [Cooperativa](projetos/081-cooperativa/) | Controle de cooperados e das contribuições mensais da cooperativa. | Cooperado, Contribuicao | SQL Server |  | ✔ |
| 082 | [Associação de Bairro](projetos/082-associacao-bairro/) | Cadastro de ruas e associados de uma associação de moradores. | Rua, Associado | SQL Server | ✔ |  |
| 083 | [Clube Social](projetos/083-clube-social/) | Gerenciamento de modalidades esportivas e sócios de um clube. | Modalidade, Socio | SQL Server |  | ✔ |
| 084 | [Brechó Solidário](projetos/084-brecho-solidario/) | Controle de peças doadas e vendidas em um brechó beneficente. | Categoria, Peca | SQL Server | ✔ |  |
| 085 | [Campanha de Doações](projetos/085-campanha-doacoes/) | Acompanhamento de campanhas de arrecadação, doadores e doações recebidas. | Campanha, Doador, Doacao | SQL Server |  |  |
| 142 | [Horta Comunitária](projetos/142-horta-comunitaria/) | Controle de moradores, canteiros e plantios de uma horta comunitária. | Morador, Canteiro, Plantio | H2 |  |  |
| 143 | [Grupo de Escoteiros](projetos/143-escoteiros/) | Controle de patrulhas e de seus escoteiros. | Patrulha, Escoteiro | H2 | ✔ | ✔ |
| 144 | [Banco de Alimentos](projetos/144-banco-alimentos/) | Registro de doadores, alimentos recebidos e distribuições às famílias. | Doador, Alimento, Distribuicao | H2 |  |  |
| 145 | [Coral Comunitário](projetos/145-coral-comunitario/) | Cadastro de naipes e coristas de um coral comunitário. | Naipe, Corista | H2 | ✔ |  |

### Agro e Logística (15)

| # | Projeto | Descrição | Entidades | Banco | Lombok | Service |
|---|---|---|---|---|---|---|
| 086 | [Fazenda](projetos/086-fazenda/) | Controle de talhões e plantios de uma propriedade rural. | Talhao, Plantio | SQL Server | ✔ |  |
| 087 | [Rebanho](projetos/087-rebanho/) | Gestão de lotes e animais de um rebanho bovino. | Lote, Animal | SQL Server |  | ✔ |
| 088 | [Hortifruti](projetos/088-hortifruti/) | Cadastro de produtores e dos hortifrútis que eles fornecem. | Produtor, Produto | SQL Server | ✔ |  |
| 089 | [Silo de Grãos](projetos/089-silo/) | Controle de silos e das cargas de grãos armazenadas. | Silo, Carga | SQL Server |  |  |
| 090 | [Transportadora](projetos/090-transportadora/) | Gerenciamento de motoristas, veículos e entregas de uma transportadora. | Motorista, Veiculo, Entrega | SQL Server | ✔ | ✔ |
| 091 | [Frota Agrícola](projetos/091-frota-agricola/) | Controle de máquinas agrícolas e de suas manutenções. | Maquina, Manutencao | SQL Server |  |  |
| 092 | [Apicultura](projetos/092-apicultura/) | Acompanhamento de apiários, colmeias e colheitas de mel. | Apiario, Colmeia, Colheita | SQL Server | ✔ |  |
| 093 | [Viveiro de Mudas](projetos/093-viveiro/) | Cadastro de espécies e lotes de mudas de um viveiro. | Especie, Muda | SQL Server |  | ✔ |
| 094 | [Pesqueiro](projetos/094-pesqueiro/) | Controle de tanques de peixes e das despescas realizadas. | Tanque, Despesca | SQL Server | ✔ |  |
| 095 | [Cooperativa Agrícola](projetos/095-coop-agricola/) | Registro de agricultores e dos recebimentos de produção da cooperativa. | Agricultor, Recebimento | SQL Server |  |  |
| 146 | [Granja Avícola](projetos/146-granja-avicola/) | Controle de galpões, lotes de aves e coleta de ovos. | Galpao, Lote, Coleta | H2 |  | ✔ |
| 147 | [Cafeicultura](projetos/147-cafeicultura/) | Registro de talhões de café e das colheitas realizadas. | Talhao, Colheita | H2 | ✔ |  |
| 148 | [Laticínio](projetos/148-laticinio/) | Acompanhamento da coleta de leite dos produtores e dos lotes fabricados. | Produtor, Coleta, Lote | H2 |  |  |
| 149 | [Centro de Distribuição](projetos/149-centro-distribuicao/) | Controle de armazéns, mercadorias estocadas e expedições. | Armazem, Mercadoria, Expedicao | H2 | ✔ | ✔ |
| 150 | [Irrigação](projetos/150-irrigacao/) | Registro de áreas irrigadas e dos acionamentos do sistema. | Area, Acionamento | H2 |  |  |
