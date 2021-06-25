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
  var ruta: Ruta = _
  var contenedores: List[Contenedor] = List.empty[Contenedor]

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

            contenedores = crearContenedores()
            contenedores.head.guardarProducto(("patata", 5))
            println(s"El contenedor ${contenedores.head._id} contiene ${contenedores.head.comprobarCantidad("patata")} " +
              s"unidades del producto 'patata'")
            println(s"El contenedor ${contenedores.head._id} contiene ${contenedores.head.comprobarCantidad("tomate")} " +
              s"unidades del producto 'tomate")
            println(s"En el contenedor ${contenedores.head._id} quedan ${contenedores.head.sacarProducto(("patata", 6))} " +
              s"unidades de patatas")
            println(s"En el contenedor ${contenedores.head._id} quedan ${contenedores.head.sacarProducto(("tomate", 1))} " +
              s"unidades de tomates")

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
  def crearEmpresa(): Future[Unit] = {
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

            val barco = crearBarco(contenedores, empresa, ruta)
            val contenedor = barco.obtenerContenedor(barco._contenedores.head._1)
            if (contenedor.isDefined) println(s"El contenedor ${contenedor.get._id} existe dentro del barco")
            barco.sacarContenedor(contenedor.get._id)
            println(s"Contenedor ${contenedor.get._id} retirado del barco")
            val contenedor2 = barco.obtenerContenedor(contenedor.get._id)
            if (contenedor2.isEmpty) println(s"El contenedor ${contenedor.get._id} no existe dentro del barco")
//            barco.navegar()
//            barco.navegar()
          }
        case Failure(e) =>
          println(e)
          Future.failed(e)
      }
  }

  /**
   * Método utilizado para crear uno o más contenedores de prueba
   *
   * @return Se devuelve como resultado una lista de contenedores para que sea más simple la posterior creación
   *         de un barco sin que sea necesario llamar a crearContenedores múltiples veces
   */
  def crearContenedores(): List[Contenedor] = List.fill(Random.between(1,3))(Contenedor())

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

    contenedores.foreach(contenedor => println(s"El contenedor ${contenedor._id} contiene " +
      s"${contenedor.hacerRecuento()} unidades de productos"))
    /**
     * Uso de función de alto nivel para almacenar en el barco de carga todos los contenedores creados.
     * La alternativa habría sido un bucle for, aunque este habría ocupado más espacio para acabar
     * realizando la misma función
     */
    contenedores.map(contenedor => barco.guardarContenedor(contenedor))
    println(s"En total, el barco contiene ${barco.contar()} productos")
    barco
  }
}
