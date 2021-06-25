package empresasnavieras.transporte.interfaces

/**
 * Interfaz para todas las embarcaciones de carga
 */
trait Embarcacion {
  /**
   * La declaración de esta cabecera obliga a que todas las embarcaciones de carga implementen
   * el método navegar.
   * Se ha creido conveniente la creación de esta interfaz para obligar a que haya un comportamiento
   * común entre todas las embarcaciones que vayan a ser dedicadas a la carga de mercancías
   */
  def navegar(): Unit
}
