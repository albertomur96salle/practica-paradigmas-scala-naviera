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
    try {
      obtenerSituacion()
      if (_puertoSiguiente != null) {
        _atracos = _atracos :+ Atraco(_puertoSiguiente, Calendar.getInstance().getTime)
        _puertoActual = _puertoSiguiente
        _puertoSiguiente = _puertos(_puertos.indexOf(_puertoSiguiente) + 1)
      }
    } catch {
      /**
       * Uso de pattern matching, en este caso con excepciones, para evitar accesos no controlados
       * a la estructura de almacenamiento de los puertos.
       * Esto se hace para evitar que un barco pueda atracar en puertos que "no existen"
       */
      case _: IndexOutOfBoundsException => _puertoSiguiente = null
    }
  }

  /**
   * Permite dar a conocer la situación del barco en cuanto a qué puertos ha recorrido,
   * el puerto actual y los puertos que faltan por recorrer
   */
  def obtenerSituacion(): Unit = {
    /**
     * Se divide la lista para presentar la información de la situación actual de la ruta.
     * La alternativa habría sido almacenar los puertos recorridos y los pendientes por recorrer
     * en estructuras distintas, pero se creyó más conveniente dejarlo en una sola lista
     */
    val (recorridos, pendientes) = _puertos.splitAt(_puertos.indexOf(_puertoActual) + 1)
    println(s"Se han recorrido los siguientes puertos: ${_atracos.mkString(", ")}")
    println(s"El puerto actual es: ${_puertoActual}")
    println(s"El puerto siguiente es: ${_puertoSiguiente}")
    println(s"Faltan por recorrer los siguientes puertos: ${pendientes.mkString(", ")}")
    println("\n")
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
    val ruta = new Ruta(_puertos, _puertoActual, _puertoSiguiente)
    ruta._atracos = ruta._atracos :+ Atraco(_puertoActual, Calendar.getInstance().getTime)
    ruta
  }
}
