package empresasnavieras.ruta

import java.util.Calendar

/**
 * Contiene el listado de puertos que forman parte de una determinada ruta
 *
 * @param puertos Listado de puertos que forman parte de la ruta
 * @param puertoActual Puerto actual en el que un barco se encuentra
 * @param puertoSiguiente Puerto siguiente en la ruta del barco
 */
class Ruta(val _puertos:List[Puerto], var _puertoActual:Puerto, var _puertoSiguiente:Puerto) {
  private var _atracos: List[Atraco] = List.empty[Atraco]

  /**
   * Simula el trayecto entre el puerto actual y el puerto siguiente en la ruta.
   * Actualiza tanto el puerto actual como el siguiente
   */
  def realizarAtraco(): Unit = {
    val atraco = Atraco(_puertoSiguiente, Calendar.getInstance().getTime)
    _atracos = _atracos :+ atraco
    _puertoActual = _puertoSiguiente
    val indicePuertoActual = _puertos.indexOf(_puertoActual)
    _puertoSiguiente = _puertos(indicePuertoActual+1)
    println(_puertoActual)
  }
}

/**
 * Companion object utilizado para la generación de rutas
 */
object Ruta {
  /**
   * Crea una ruta
   *
   * @param _puertos Listado de puertos que forman parte de una determinada ruta
   * @param _puertoActual Puerto actual en el que un barco se encuentra
   * @param _puertoSiguiente Puerto siguiente en la ruta del barco
   * @return
   */
  def apply(_puertos:List[Puerto], _puertoActual:Puerto, _puertoSiguiente:Puerto): Ruta = {
    new Ruta(_puertos, _puertoActual, _puertoSiguiente)
  }
}
