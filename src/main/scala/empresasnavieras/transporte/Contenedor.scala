package empresasnavieras.transporte

import scala.util.Random

/**
 * Representa a un contenedor con un identificador numérico específico
 *
 * @param _id Identificador numérico del contenedor
 */
class Contenedor(val _id:Int) {
  var _productos: Map[String, Int] = Map()

  /**
   * Almacena una cantidad x de producto en el contenedor.
   * Si el producto ya existía, a la cantidad actual de producto se le suma la cantidad a añadir
   *
   * @param producto Tupla que representa el producto (nombre) que se quiere almacenar y la cantidad
   * @return
   */
  def guardarProducto(producto :(String, Int)): Unit = {
    /**
     * Uso de pattern matching para diferenciar si un contenedor lleva en su interior el producto
     * solicitado o no.
     * Cumple la misma función que un if y además requiere menos líneas de código
     */
    _productos.contains(producto._1) match {
      case false => _productos = _productos.+(producto._1 -> producto._2)
      case true => _productos = _productos.+(producto._1 -> (_productos.apply(producto._1) + producto._2))
    }
  }

  /**
   * Retira x unidades de producto del contenedor
   *
   * @param producto Tupla que representa el producto (nombre) que se quiere retirar y la cantidad
   * @return Devuelve la cantidad de unidades restantes del producto retirado
   */
  def sacarProducto(producto: (String, Int)): Int = {
    if (_productos.contains(producto._1)) {
      _productos = _productos.+(producto._1 -> (_productos.apply(producto._1) - producto._2))

      if (_productos(producto._1) <= 0) {
        _productos = _productos.-(producto._1)
      }
    }

    /**
     * Uso de opcionales debido a que un producto puede no existir más después de haber intentado
     * sacar una determinada cantidad.
     * La alternativa a este uso habría sido comprobar con un if si el producto todavía existe antes
     * de intentar devolver la cantidad, y devolver la cantidad restante (o no) en función del resultado de
     * dicha comprobación
     */
    _productos.get(producto._1) match {
      case Some(cantidad) => cantidad
      case None => 0
    }
  }

  /**
   * Devuelve la cantidad de unidades restantes de un determinado producto
   *
   * @param producto Producto cuya cantidad en el contenedor se quiere comprobar
   * @return Devuelve un opcional, ya que el contenedor puede no tener guardado el producto solicitado
   */
  def comprobarCantidad(producto: String): Int = {
    /**
     * Uso de opcionales para recuperar la cantidad de un producto.
     * Como no se sabe a priori si el producto existe (o no) en el contenedor, es necesario tener en cuenta
     * dicha condición.
     * La alternativa a este uso habría sido comprobar con un if si el producto todavía existe antes
     * de intentar devolver la cantidad, y devolver la cantidad restante (o no) en función del resultado de
     * dicha comprobación
     */
      _productos.get(producto) match {
        case Some(cantidad) => cantidad
        case None => 0
      }
  }

  /**
   * Devuelve la cantidad total de productos que hay en el contenedor
   *
   * @return
   */
  def hacerRecuento(): Int = {
    var recuento = 0
    _productos.foreach(producto => recuento+=producto._2)
    recuento
  }
}

/**
 * Companion object utilizado para la generación de contenedores
 */
object Contenedor {
  /**
   * Crea un contenedor con un identificador numérico aleatorio
   *
   * @return Devuelve un contenedor creado de forma aleatoria
   *         En este caso, se ha hecho uso del companion object para seguir el patrón object mother, patrón
   *         usado para la generación aleatoria de objetos.
   *         En caso de querer crear un objeto más personalizado, el constructor de la clase está disponible
   */
  def apply(): Contenedor = {
    val contenedor = new Contenedor(Random.between(1, 999999))
    for(_ <- 1 to Random.between(2, 5)) contenedor.guardarProducto(faker.Lorem.words(2).mkString(""), Random.between(1, 20))
    contenedor
  }
}
