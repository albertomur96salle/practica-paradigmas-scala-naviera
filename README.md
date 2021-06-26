# Práctica Empresa Naviera - Paradigmas de programacion

La aplicación ha sido desarrollada por los siguientes integrantes:
* Miquel Jordà Lopez
* Marc Grau Riesco
* Alberto Mur Rodrigo

Los pertinentes comentarios acerca del código están en cada una de las clases creadas.

## Requisitos

* Tener instalado el IDE IntelliJ
* Tener instalado el plugin de Scala

## Comentarios

Al abrir el proyecto con IntelliJ, si el IDE muestra un cuadro diciendo que ha encontrado 2 configuraciones, hacer click
en la casilla "proyecto sbt" y después en OK (en función del idioma del ordenador, el texto del cuadro puede variar).

IntelliJ le asigna Java 14 o incluso 15 al proyecto automáticamente cuando termina de importarlo. Sin embargo, con la
versión 1.8 funciona sin problemas. En caso de no tener ninguna versión de Java instalada en el equipo, IntelliJ se
puede configurar para que descargue una versión determinada dentro de los ajustes del proyecto.

## Ejecución

1) Abrir la clase `src/main/scala/Main.scala`
2) Hacer click en el símbolo de play verde que aparecerá a la izquierda del código o bien en la línea 15 o bien en la
línea 21
   
## Solución de problemas

En caso de no poder poner en marcha la clase Main con las llamadas a los métodos desarrollados, el problema puede ser:
* IntelliJ todavía no ha terminado de descargar las dependencias de Scala
* IntelliJ todavía no ha terminado de realizar el análisis de los distintos ficheros de código de Scala

Si sbt no descarga las dependencias ni realiza ninguna otra acción, probablemente falte o el plugin de scala o el
"framework support", el cual se puede añadir haciendo click derecho en el nombre del proyecto como se ha visto en las
clases de la asignatura.
