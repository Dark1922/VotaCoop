import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random
import scala.util.Random

class VoteSimulation extends Simulation {

  // Function to generate a random CPF
  def generateCPF(): String = {
    val rnd = (n: Int) => Random.nextInt(n)
    val mod = (base: Int, div: Int) => base - (base / div) * div
    val n = Array.fill(9)(rnd(9))

    var d1 = n.zipWithIndex.map { case (number, index) => number * (10 - index) }.sum
    d1 = 11 - mod(d1, 11)
    if (d1 >= 10) d1 = 0

    var d2 = (d1 * 2) + n.zipWithIndex.map { case (number, index) => number * (11 - index) }.sum
    d2 = 11 - mod(d2, 11)
    if (d2 >= 10) d2 = 0

    s"${n.mkString}${d1}${d2}"
  }

  // HTTP protocol configuration
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .header("Accept", "application/json")

  // Step 1: Create Pauta
  val createPauta = exec(
    http("Create Pauta")
      .post("/pauta")
      .body(StringBody("""{ "tema": "teste", "descricao": "teste" }""")).asJson
      .check(jsonPath("$.id").saveAs("idPauta"))
  ).pause(100 milliseconds)

  // Step 2: Open Voting Session
  val createSession = exec(
    http("Open Voting Session")
      .post("/sessao_votacao")
      .body(StringBody("""{ "idPauta": "${idPauta}", "duracao": "10" }""")).asJson
      .check(jsonPath("$.id").saveAs("idSessaoVotacao"))
  ).pause(100 milliseconds)

  // Step 3: Create Associados and Vote
  val createAssociadosAndVote = repeat(5000) {
    exec(session => session.set("cpf", generateCPF()))
      .exec(
        http("Create Associado")
          .post("/associado")
          .body(StringBody("""{ "nome": "Nome do Associado", "cpf": "${cpf}" }""")).asJson
          .check(status.is(201))
          .check(jsonPath("$.id").saveAs("idAssociado"))
          .check(bodyString.saveAs("responseBody"))
      )
      .exec(session => {
        println(s"Response from Create Associado: ${session("responseBody").as[String]}")
        session
      })
      .exec(
        // Step 3.1: Register and Vote
        http("Register and Vote")
          .post("/voto")
          .body(StringBody(session => {
            val randomVote = if (Random.nextBoolean()) "Sim" else "Não" // Randomize vote value
            s"""{ "idSessaoVotacao": "${session("idSessaoVotacao").as[String]}", "idAssociado": "${session("idAssociado").as[String]}", "valor": "$randomVote" }"""
          })).asJson
          .check(status.is(201))
          .check(bodyString.saveAs("responseBody"))
      )
      .exec(session => {
        println(s"Response from Create Voto: ${session("responseBody").as[String]}")
        session
      })
      .pause(0.001 milliseconds)
  }

  // Define scenario
  val scn = scenario("Vote cenario")
    .exec(createPauta, createSession)
    .exec(createAssociadosAndVote)

  // Set up simulation
  setUp(
    scn.inject(
      atOnceUsers(1)
    )
  ).protocols(httpProtocol)
}