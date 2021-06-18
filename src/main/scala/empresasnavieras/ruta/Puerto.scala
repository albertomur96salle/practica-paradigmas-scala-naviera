package empresasnavieras.ruta

/**
 * Representa a un posible punto de atraco para las rutas de los barcos
 *
 * @param nombre
 * @param ciudad
 */
class Puerto(nombre:String, ciudad:String) {
  private val _nombre: String = nombre
  private val _ciudad: String = ciudad

  def nombre: String = _nombre
  def ciudad: String = _ciudad
}
