package empresasnavieras.ruta

import java.util.Date

/**
 * Representa el momento en el que un barco llegó a un determinado puerto
 *
 * @param puerto Puerto en el que se realiza el atraco
 * @param fecha Fecha en la que se realiza el atraco
 */
case class Atraco(puerto:Puerto, fecha:Date) {}
