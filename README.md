# API de Dados Coletados por Sonda Espacial

**Vitor Garcia Correia — RM 558516 — Checkpoint 2, Modelo 2**

Artifact: `api-dado-coletado-vitor-558516-m2`.
Java 25, Spring Boot 4.0.8, Maven, JPA, Bean Validation, Flyway e Swagger/OpenAPI.

## Identificação

GitHub: `vitorgarcia03`. Group Maven: `com.github.vitorgarcia03`.
Pacote base: `com.github.vitorgarcia03.dadocoletado`.
Os DTOs usam `class`, com construtor vazio, construtor completo, getters e setters,
seguindo o padrão da aula.

## Estrutura e regras

A aplicação possui Entity, RequestDTO, ResponseDTO, Repository, Service,
Controller, configuração OpenAPI e tratamento de erros. Não usa Lombok.
O identificador `dadoId` é Long gerado pelo banco.

| Campo | Tipo | Validação |
|---|---|---|
| sondaNome | String | Obrigatório, não vazio, 3 a 100 caracteres |
| agenciaEspacial | String | Obrigatório, não vazio, 3 a 100 caracteres |
| dataColeta | LocalDate | Obrigatório, hoje ou anterior |
| tipoDado | TipoDado | Obrigatório, valor do enum |
| valorMedido | BigDecimal | Obrigatório; valores negativos são permitidos |
| unidadeMedida | String | Obrigatório, não vazio, 3 a 100 caracteres |

Enum: `TEMPERATURA`, `PRESSAO`, `RADIACAO`, `COMPOSICAO_ATMOSFERICA`,
`VELOCIDADE_VENTO`. Valor medido é persistido com quatro casas decimais.
Use `pascal` em vez de `Pa`, por exemplo: o enunciado exige ao menos 3 caracteres
para a unidade. A data usa o relógio da JVM; no container, o fuso é America/Sao_Paulo.

## Properties no padrão das fotos

- `application.properties`: nome `vitor-rm558516-m2`, profile `dev` ativo,
  Flyway desativado por padrão e `open-in-view=false`.
- `application-dev.properties`: MySQL `produtos`, usuário/senha `root/root`,
  Flyway habilitado, baseline e validação de migrations habilitados,
  Hibernate `ddl-auto=none` e logs SQL habilitados.
- `application-test.properties`: H2 `jdbc:h2:mem:testdb`, usuário `sa`, senha
  vazia, console H2 habilitado e Flyway desativado, conforme as fotos.

No `test`, Hibernate usa seu comportamento padrão de criação de tabelas em banco
embarcado; não há carga das migrations. No `dev`, Flyway cria a tabela e insere
os três exemplos fictícios; Hibernate não cria nem altera as tabelas.

## Docker com H2

Pré-requisito: Docker instalado e iniciado. Na pasta do `pom.xml`:

```bash
docker build -t api-dado-vitor:1.0 .
docker run --rm --name sonda-h2 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=test api-dado-vitor:1.0
```

O Dockerfile multi-stage compila com Maven/JDK 25 e executa com JRE 25, usando
usuário sem privilégios. Não exige Java nem Maven instalados no computador.
H2 é temporário: os dados são perdidos quando a aplicação encerra.

## Docker com MySQL

```bash
docker compose up --build -d
docker compose logs -f api
```

O Compose inicia MySQL 8.4, aguarda seu healthcheck e inicia a API em `dev`.
No container, a URL do banco é sobrescrita por `SPRING_DATASOURCE_URL` para
usar `mysql` em vez de `localhost`; os arquivos properties seguem as fotos.
Usuário/senha: `root/root`. Banco: `produtos`. Credenciais para uso local.

Para parar sem apagar os dados:

```bash
docker compose down
```

O volume `produtos-data` mantém os dados. Use um banco novo para esta aplicação:
não compartilhe um banco já migrado por outro projeto, pois ambos teriam V1/V2
na tabela `flyway_schema_history`. Não execute duas APIs na porta 8080 ao mesmo tempo.

## Execução local

Requisitos: JDK 25 e Maven 3.9.11 ou superior no PATH.
Confira com `java -version` e `mvn -version`. Importe `pom.xml` na IDE como projeto
Maven e selecione JDK 25 no projeto e no executor Maven.

```bash
mvn clean package
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

Para `dev`, inicie apenas o MySQL via Compose (ou use seu MySQL local com
`root/root` na porta 3306):

```bash
docker compose up -d mysql
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Também é possível executar o JAR:

```bash
java -jar target/app.jar --spring.profiles.active=test
java -jar target/app.jar --spring.profiles.active=dev
```

Sem informar um profile, a aplicação inicia em `dev` e exige MySQL.

## Swagger e H2

- Swagger: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- H2 Console (somente test): http://localhost:8080/h2-console/

No H2 Console: URL `jdbc:h2:mem:testdb`, usuário `sa`, senha vazia.
Foi incluído `spring-boot-h2console` para disponibilizar o console no Boot 4.
No Swagger, abra a operação, clique em **Try it out**, preencha o corpo e execute.

## CRUD

| Método | Caminho | Resultado |
|---|---|---|
| GET | `/api/dados-coletados` | 200, lista ordenada por ID |
| GET | `/api/dados-coletados/{id}` | 200 ou 404 |
| POST | `/api/dados-coletados` | 201 e cabeçalho Location |
| PUT | `/api/dados-coletados/{id}` | 200 ou 404; todos os campos são obrigatórios |
| DELETE | `/api/dados-coletados/{id}` | 204 ou 404 |

Erros de validação ou formato retornam 400. Exemplo para POST/PUT:

```json
{
  "sondaNome": "Sonda Aurora",
  "agenciaEspacial": "Agencia Espacial Brasileira",
  "dataColeta": "2025-10-14",
  "tipoDado": "TEMPERATURA",
  "valorMedido": -62.5,
  "unidadeMedida": "graus Celsius"
}
```

Não envie `dadoId` no corpo; ele é gerado pelo banco.

## Migrations

Em `src/main/resources/db/migration`:

- `V1__criar_tabela_dado_coletado.sql`: cria tabela, chave e restrições.
- `V2__inserir_dados_iniciais.sql`: três registros distintos e coerentes.

As medições da carga são fictícias, para demonstração. Flyway aplica cada versão
uma vez no profile `dev`. Para mudanças futuras, adicione uma nova migration;
não edite uma já aplicada.

## Verificação

Com a aplicação iniciada e Python 3 instalado:

```bash
python3 scripts/verificar_api.py
```

No Windows, pode usar `python scripts/verificar_api.py`.
O script verifica CRUD, limites de tamanho, campos ausentes/nulos, enum, datas,
404 e OpenAPI. Remove o registro criado pelo teste. Execute com cada profile.

**Limitação:** foi feita revisão estática dos arquivos. Compilação, containers e
verificação HTTP não foram executados no ambiente de geração, que não dispõe
de JDK 25, Maven ou Docker. Execute os comandos acima antes de entregar.

Referências: https://docs.spring.io/spring-boot/4.0/ e https://springdoc.org/
