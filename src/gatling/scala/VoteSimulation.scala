import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class VoteSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .header("Accept", "application/json")

  // Etapa 1: Criar a pauta e capturar o ID da pauta
  val createPauta = exec(
    http("Create Pauta")
      .post("/pauta")
      .body(StringBody("""{ "tema": "teste", "descricao": "teste" }""")).asJson
      .check(jsonPath("$.id").saveAs("idPauta"))
  ).pause(100 milliseconds)

  // Etapa 2: Criar a sessão de votação usando o ID da pauta e depois capturar o ID da sessão
  val createSession = exec(
    http("Open Voting Session")
      .post("/sessao_votacao")
      .body(StringBody("""{ "idPauta": "${idPauta}", "duracao": "10" }""")).asJson
      .check(jsonPath("$.id").saveAs("idSessaoVotacao"))
  ).pause(100 milliseconds)

  // Etapa 3: Criar associados e votar com cada associado
  val createAssociadosAndVote = repeat(5000) {
    exec(
      http("Create Associado")
        .post("/associado")
        .body(StringBody("""{ "nome": "Nome do Associado", "cpf": "12345678901" }""")).asJson
        .check(jsonPath("$.id").saveAs("idAssociado"))
    )
      .exec(
        http("Register and Vote")
          .post("/voto")
          .body(StringBody("""{ "idSessaoVotacao": "${idSessaoVotacao}", "idAssociado": "${idAssociado}", "valor": "Sim" }""")).asJson
      ).pause(0.001 milliseconds)
  }

  val scn = scenario("Vote cenario")
    .exec(createPauta, createSession) // Primeira etapa: criar pauta e sessão de votação
    .exec(createAssociadosAndVote) // Segunda etapa: criar associados e votar com cada associado

  setUp(
    scn.inject(
      atOnceUsers(1) // Criação de pauta, sessão de votação, associados e votos
    )
  ).protocols(httpProtocol)
}
