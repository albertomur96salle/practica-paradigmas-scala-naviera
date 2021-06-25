import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.HttpRequest
import empresasnavieras.empresa.Empresa
import empresasnavieras.ruta.{Puerto, Ruta}
import empresasnavieras.transporte.{Buque, Contenedor}
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.language.postfixOps
import scala.util.{Failure, Random, Success}

object Main {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  var ruta: Ruta = null
  var contenedor: List[Contenedor] = List.empty[Contenedor]

  def main(args: Array[String]): Unit = empezarPlan()

  /**
   * Método que da comienzo a la ejecución de prueba.
   * 1º: Empieza solicitando información a la API jsonplaceholder para la creación de puertos
   * 2º: Se crea la ruta que seguirá el barco
   * 3º: Se crean los contenedores (1 o más) que se cargarán en el barco
   * 4º: Se crea la empresa y se inicia la navegación
   */
  def empezarPlan(): Unit = {
    /**
     * Uso de akka para recuperar información de la página jsonplaceholder.
     * La información recuperada es de usuarios debido a que en el json devuelto hay nombres de ciudades
     * que son útiles a la hora de realizar la creación de puertos
     */
    Http().singleRequest(HttpRequest(uri = "https://jsonplaceholder.typicode.com/users"))
      .transformWith {
        case Success(res) =>
          val data = Unmarshal(res).to[String]

          data.map { d =>
            val puertos = crearPuertos(Json.parse(d))
            ruta = crearRuta(puertos)
            contenedor = crearContenedores()
            crearEmpresa()
          }
        case Failure(e) =>
          println(e)
          Future.failed(e)
      }
  }

  /**
   * Método encargado de crear puertos para la posterior creación de una ruta
   *
   * @param data Información empleada para la creación de los puertos
   * @return Listado de puertos que formarán parte de una ruta
   */
  def crearPuertos(data: JsValue): List[Puerto] = {
    val puerto1 = Puerto("P1", data(0).apply("address").apply("city").toString())
    val puerto2 = Puerto("P2", data(1).apply("address").apply("city").toString())
    val puerto3 = Puerto("P3", data(2).apply("address").apply("city").toString())
    List(puerto1, puerto2, puerto3)
  }

  /**
   * Devuelve una ruta que contiene toda una serie de puertos
   *
   * @param puertos Puertos que se añadirán a la ruta
   * @return Ruta con los puertos solicitados
   */
  def crearRuta(puertos: List[Puerto]): Ruta = Ruta(puertos, puertos.head, puertos(1))

  /**
   * Crea una empresa e inicia la navegación del barco
   *
   * @return
   */
  def crearEmpresa() = {
    /**
     * Uso de akka para recuperar información de la página jsonplaceholder.
     * La información recuperada es de utilidad para la creación de empresas con nombres cualesquiera
     */
    Http().singleRequest(HttpRequest(uri = "https://jsonplaceholder.typicode.com/todos"))
      .transformWith {
        case Success(res) =>
          val data = Unmarshal(res).to[String]

          data.map { d =>
            val numberString = faker.PhoneNumber.phone_number.replaceAll("[^0-9]", "")
            val number = numberString.substring(0, Math.min(numberString.length(), 9)).toInt
            val empresa = Empresa(Json.parse(d)(0).apply("title").toString(), number)
            val barco = crearBarco(contenedor, empresa, ruta)
            barco.navegar()
            barco.navegar()
            barco.ruta.obtenerSituacion()
          }
        case Failure(e) =>
          println(e)
          Future.failed(e)
      }
  }

  /**
   * Método utilizado para crear uno o más contenedores de prueba
   *
   * @return Lista que contiene uno o más contenedores
   */
  def crearContenedores(): List[Contenedor] = {
    val contenedor = Contenedor()
    val nombreProducto = faker.Lorem.words(2).mkString("")
    val cantidadProducto = Random.between(1, 20)

    contenedor.guardarProducto((nombreProducto, cantidadProducto))

    /**
     * Se devuelve como resultado una lista de contenedores para que sea más simple la posterior creación
     * de un barco sin que sea necesario llamar a crearContenedores múltiples veces.
     * De esta manera, solo haría falta modificar el código de crearContenedores para que se añadan más
     * contenedores a la lista devuelta
     */
    List(contenedor)
  }

  /**
   * Método encargado de crear un barco de pruebas dada una lista de contenedores, una empresa y una ruta
   *
   * @param contenedores Contenedores que serán añadidos al barco
   * @param empresa Entidad dueña del barco
   * @param ruta Ruta seguida por el barco
   * @return Barco creado según los parámetros anteriores
   */
  def crearBarco(contenedores: List[Contenedor], empresa: Empresa, ruta: Ruta): Buque = {
    val barco: Buque = Buque(empresa, ruta)

    /**
     * Uso de función de alto nivel para almacenar en el barco de carga todos los contenedores creados.
     * La alternativa habría sido un bucle for, aunque este habría ocupado más espacio para acabar
     * realizando la misma función
     */
    contenedores.map(contenedor => barco.guardarContenedor(contenedor))
    barco
  }
}
