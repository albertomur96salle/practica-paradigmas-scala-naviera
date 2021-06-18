package empresasnavieras.ruta

import java.util.Date

/**
 * Representa el momento en el que un barco llegó a un determinado puerto
 *
 * @param puerto
 * @param fecha
 */
//class Atraco(puerto:Puerto, fecha:Date) {
//  private val _puerto: Puerto = puerto
//  private val _fecha: Date = fecha
//
//  def puerto: Puerto = _puerto
//  def fecha: Date = _fecha
//}
case class Atraco(puerto:Puerto, fecha:Date) {}
