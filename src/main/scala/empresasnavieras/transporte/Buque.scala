package empresasnavieras.transporte

import empresasnavieras.empresa.Empresa
import empresasnavieras.ruta.Ruta

/**
 * Representa a un barco junto con contenedores de productos
 * Los buques pertenecen a una determinada empresa y siguen solo 1 ruta
 */
class Buque {
  var _contenedores: Map[Int, Contenedor] = Map()
  var empresa: Empresa = null
  var ruta: Ruta = null

  /**
   * Añade un contenedor a la bodega de carga del buque
   *
   * @param contenedor
   */
  def guardarContenedor(contenedor: Contenedor): Unit = _contenedores += (contenedor._id -> contenedor)

  /**
   * Retira un contenedor concreto del buque
   *
   * @param id
   */
  def sacarContenedor(id: Int) = _contenedores -= (id)

  /**
   * Obtiene la información de un determinado contenedor
   *
   * @param id
   * @return Devuelve un opcional, ya que el contenedor puede no existir para el id introducido
   */
  def obtenerContenedor(id: Int) = Option(_contenedores.apply(id))

  /**
   * Hace que el barco avance 1 puerto en su ruta actual
   */
  def navegar() = ruta.realizarAtraco()

  /**
   * Hace un recuento de la cantidad de productos que hay en todos los contenedores
   *
   * @return
   */
  def contar() = {
    val sumar = (x:Int, y:Int) => x + y

    val recuentoContenedores = _contenedores.map(contenedor => contenedor._2.hacerRecuento())
    recuentoContenedores.reduce((acc,x) => sumar(acc,x))
  }
}

/**
 * Companion object utilizado para la generación de barcos sin contenedores
 */
object Buque {
  /**
   * Crea un barco
   *
   * @param _empresa
   * @param _ruta
   * @return Devuelve un barco perteneciente a la empresa "_empresa" y que sigue la ruta "_ruta"
   */
  def apply(_empresa:Empresa, _ruta:Ruta): Buque = {
    val b = new Buque
    b.empresa = _empresa
    b.ruta = _ruta
    b
  }
}
