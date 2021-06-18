package empresasnavieras.transporte

import empresasnavieras.empresa.Empresa
import empresasnavieras.ruta.Ruta

/**
 * 
 */
class Buque {
  val _contenedores: Map[Int, Contenedor] = Map()
  var empresa: Empresa = null
  var ruta: Ruta = null

  def guardarContenedor(contenedor: Contenedor): Unit = _contenedores.+(contenedor._id -> contenedor)

  def sacarContenedor(id: Int) = _contenedores.-(id)

  def obtenerContenedor(id: Int) = Option(_contenedores.apply(id))

  def navegar() = ruta.realizarAtraco()
}

object Buque {
  def apply(_empresa:Empresa, _ruta:Ruta): Buque = {
    val b = new Buque
    b.empresa = _empresa
    b.ruta = _ruta
    b
  }
}
