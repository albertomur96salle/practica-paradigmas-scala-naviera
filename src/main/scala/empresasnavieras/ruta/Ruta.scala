package empresasnavieras.ruta

import java.util.Calendar

/**
 * Contiene el listado de puertos que forman parte de una determinada ruta
 *
 * @param puertos
 * @param puertoActual
 * @param puertoSiguiente
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

object Ruta {
  def apply(_puertos:List[Puerto], _puertoActual:Puerto, _puertoSiguiente:Puerto): Ruta = {
    new Ruta(_puertos, _puertoActual, _puertoSiguiente)
  }
}
