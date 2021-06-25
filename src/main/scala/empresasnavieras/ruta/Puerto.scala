package empresasnavieras.ruta

/**
 * Representa a un posible punto de atraco para las rutas de los barcos
 *
 * @param nombre Nombre del puerto
 * @param ciudad Ciudad en la que se encuentra el puerto
 */
case class Puerto(nombre:String, ciudad:String) {}

/**
 * Se establece Puerto como una case class debido a que se entiende que nunca va a ser
 * necesario cambiar su nombre o la ciudad (se entiende que un puerto determinado siempre
 * va a estar en la misma localización, por lo tanto, no se permite su modificación)
 * Se podría haber optado por una clase normal, pero se ha supuesto que una case class
 * encajaba mejor con el uso que se le da a esta clase, que es, almacenar información
 * que será usada en otros fragmentos de código.
 * Además, la clase Puerto no contiene nada de código, solo dos propiedades
 */
