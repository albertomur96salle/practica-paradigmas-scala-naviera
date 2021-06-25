package empresasnavieras.ruta

import java.util.Date

/**
 * Representa el momento en el que un barco llegó a un determinado puerto
 *
 * @param puerto Puerto en el que se realiza el atraco
 * @param fecha Fecha en la que se realiza el atraco
 */
case class Atraco(puerto:Puerto, fecha:Date) {}

/**
 * Se establece Atraco como una case class debido a que se entiende que nunca va a ser
 * necesario cambiar el ni el puerto ni la fecha (al fin y al cabo, un atraco es algo que
 * sucede puntualmente en el tiempo, un evento que se registra y que nunca cambia).
 * Se podría haber optado por una clase normal, pero se ha supuesto que una case class
 * encajaba mejor con el uso que se le da a esta clase, que es, almacenar información
 * que será usada en otros fragmentos de código.
 * Además, la clase Atraco no contiene nada de código, solo dos propiedades
 */
