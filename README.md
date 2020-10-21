Final Reality
=============

![http://creativecommons.org/licenses/by/4.0/](https://i.creativecommons.org/l/by/4.0/88x31.png)

This work is licensed under a 
[Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/)

Context
-------

This project's goal is to create a (simplified) clone of _Final Fantasy_'s combat, a game developed
by [_Square Enix_](https://www.square-enix.com)
Broadly speaking for the combat the player has a group of characters to control and a group of 
enemies controlled by the computer.

---

### Instrucciones de ejecucion.
Dada la etapa temprana del proyecto, en donde recien se han establecido los esquemas generales e iniciales, no existe como tal una ejecucion del programa.
### Supuestos.
* Se considera que los puntos de defensa, ataque, salud inicial y mana inicial de cada personaje (segun corresponda) seran fijos al iniciarlos, es decir, no se veran afectados por algun estado. Esto implica que son considerados como atributos diferenciables.
* En cambio, los puntos de vida o mana actuales, podran variar a lo largo de la partida (al recibir o realizar ataques por ejemplo). Esto implica que no son considerados como atributos diferenciables.
### Funcionamiento y logica.
* Un personaje, en lineas abstractas, posee un nombre, puntos de vida iniciales, puntos de vida actuales, puntos de defensa y una lista de turnos de juego. Como tal posee metodos para obtener esas variables, para establecer un nuevo punto de vida y esperar un turno.
* Un personaje jugable, es un tipo de personaje abstracto que posee ademas la posibilidad de equiparse armas. Este posee metodos para obtener esa variable, inicializada por defecto como vacia, y equiparse un arma. Implementa de manera particular el esperar un turno, de su padre.
* Los personajes jugables magicos, son tipos asbtractos de personajes jugables que poseen de manera extra un atributo de mana inicial y mana actual, junto a sus getters y el setter del mana actual.
* Los Caballeros, Ladrones e Ingenieros son tipos particulares de personajes jugables, mientras que los Magos Negros y Magos Blancos, son tipos particulares de personajes jugables magicos. Todos ellos implementan de forma particular el equipamento de un arma.
* Los enemigos, por otro lado, son tipos particulares de un personaje, estos no son jugables. Como tal poseen de manera extra un peso y puntos de ataque, junto con sus respectivos metodos para obtenerlos. Al ser un personaje NO jugable, implementa de manera particular y distinta el metodo de esperar turno.
* Los distintos tipos de personajes jugables tienen la posibilidad de equiparse de manera distinta un arma (dependiendo de su tipo). Esta fucionalidad (junto a otras mas), sera desarrollada en la proxima entrega, por el momento se definio de igual forma para todas las clases.
* Un arma en general posee un nombre, daño y peso asociado, junto a sus getters.
* Existen 5 tipos particulares de armas: Las hachas, los arcos, los cuchillos, las espadas y los bastones. Estos ultimos poseen de manera especial un daño magico usado para efectuar distintos hechizos.
* Todas las armas pueden ser equipadas de manera exclusiva por algunos personajes.
