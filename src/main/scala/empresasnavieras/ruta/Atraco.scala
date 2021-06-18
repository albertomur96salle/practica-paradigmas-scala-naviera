package empresasnavieras.ruta

import java.util.Date

/**
 * Representa el momento en el que un barco llegó a un determinado puerto
 *
 * @param puerto
 * @param fecha
 */

case class Atraco(puerto:Puerto, fecha:Date) {}
