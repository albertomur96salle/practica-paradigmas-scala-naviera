package empresasnavieras.empresa

/**
 * Representa a una empresa que potencialmente puede fletar uno o más barcos
 *
 * @param _nombre Nombre de la empresa
 * @param _cif Código de identificación de la empresa
 */
case class Empresa(_nombre:String, _cif:Int) {}

/**
 * Se establece Empresa como una case class debido a que se entiende que nunca va a ser
 * necesario cambiar el nombre o su identificador fiscal.
 * Se podría haber optado por una clase normal, pero se ha supuesto que una case class
 * encajaba mejor con el uso que se le da a esta clase, que es, almacenar información
 * que será usada en otros fragmentos de código.
 * Además, la clase Empresa no contiene nada de código, solo dos propiedades
 */
