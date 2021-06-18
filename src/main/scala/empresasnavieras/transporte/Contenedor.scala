package empresasnavieras.transporte

/**
 * Representa a un contenedor con un identificador numérico específico
 *
 * @param _id
 */
class Contenedor(val _id:Int) {
  var _productos: Map[String, Int] = Map()

  /**
   * Almacena una cantidad x de producto en el contenedor.
   * Si el producto ya existía, a la cantidad actual de producto se le suma la cantidad a añadir
   *
   * @param producto
   * @return
   */
  def guardarProducto(producto :(String, Int)): Map[String, Int] = {
    _productos.contains(producto._1) match {
      case false => _productos.+(producto._1 -> producto._2)
      case true => _productos.+(producto._1 -> (_productos.apply(producto._1) + producto._2))
    }
  }

  /**
   * Retira x unidades de producto del contenedor
   *
   * @param producto
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
   * @param producto
   * @return
   */
  def comprobarCantidad(producto: String): Option[Int] = {
    Option(_productos.apply(producto))
  }
}
