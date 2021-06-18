package empresasnavieras.ruta

import java.util.Calendar

/**
 * Contiene el listado de puertos que forman parte de una determinada ruta
 *
 * @param puertos
 * @param puertoActual
 * @param puertoSiguiente
 */
class Ruta(puertos:List[Puerto], puertoActual:Puerto, puertoSiguiente:Puerto) {
  private val _puertos: List[Puerto] = puertos
  private var _atracos: List[Atraco] = List.empty[Atraco]
  private var _puertoActual: Puerto = puertoActual
  private var _puertoSiguiente: Puerto = puertoSiguiente

  def puertos: List[Puerto] = _puertos
  def atracos: List[Atraco] = _atracos
  def puertoActual: Puerto = _puertoActual
  def puertoSiguiente: Puerto = _puertoSiguiente

  /**
   * Simula el trayecto entre el puerto actual y el puerto siguiente en la ruta.
   * Actualiza tanto el puerto actual como el siguiente
   */
  def realizarAtraco(): Unit = {
    val atraco = new Atraco(_puertoSiguiente, Calendar.getInstance().getTime)
    _atracos = _atracos :+ atraco
    _puertoActual = _puertoSiguiente
    val indicePuertoActual = _puertos.indexOf(_puertoActual)
    _puertoSiguiente = _puertos(indicePuertoActual+1)
  }
}
