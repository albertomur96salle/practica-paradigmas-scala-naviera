package empresasnavieras

import empresasnavieras.empresa.Empresa
import empresasnavieras.ruta.{Puerto, Ruta}
import empresasnavieras.transporte.{Buque, Contenedor}

object Main {

  def main(args: Array[String]): Unit = {
    val empresa = Empresa("Transportes Paco", 645892973)

    val puerto1 = new Puerto("P1", "Barcelona")
    val puerto2 = new Puerto("P2", "Valencia")
    val puerto3 = new Puerto("P3", "Cadiz")

    val puertosRuta : List[Puerto] = List(puerto1, puerto2, puerto3)

    val ruta = new Ruta(puertosRuta, puerto1, puerto2)

    val buque = Buque(empresa, ruta)

    val contenedor1 = new Contenedor(1)
    contenedor1.guardarProducto("Salchichas", 100)

    buque.guardarContenedor(contenedor1)

    buque.navegar()
  }

}
