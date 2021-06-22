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
  def sacarProducto(producto: (String, Int)): Option[Int] = {
    if (_productos.contains(producto._1)) {
      _productos = _productos.+(producto._1 -> (_productos.apply(producto._1) - producto._2))

      if (_productos(producto._1) <= 0) {
        _productos = _productos.-(producto._1)
      }
    }
    Option(_productos.apply(producto._1))
  }

  /**
   * Devuelve la cantidad de unidades restantes de un determinado producto
   *
   * @param producto Producto cuya cantidad en el contenedor se quiere comprobar
   * @return Devuelve un opcional, ya que el contenedor puede no tener guardado el producto solicitado
   */
  def comprobarCantidad(producto: String): Option[Int] = {
    Option(_productos.apply(producto))
  }

  /**
   * Devuelve la cantidad total de productos que hay en el contenedor
   *
   * @return
   */
  def hacerRecuento(): Int = {
    var recuento = 0
    for ((_,cantidad) <- _productos) recuento+=cantidad
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
   * @return
   */
  def apply(): Contenedor = {
    new Contenedor(Random.between(1, 999999))
  }
}
