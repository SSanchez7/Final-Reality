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
* Tras iniciar el juego se muestra en pantalla el nombre del juego junto con un boton "Start" para dar inicio.
* Presionar "Start" llevara a la seleccion de jugadores, de donde se debe escoger uno clickeando los circulos del costado izquierdo junto a los indices, e insertarle un nombre, en la caja de texto bajo la lista, para el personaje escogido. Para confirmar presionar "Choose Player".
* Entonces se mostrara una lista de armas para equipar el personaje anteriormente elegido, de la cual se escogera una de la misma forma que la eleccion de personaje: clickeando los circulos del costado izquierdo junto a los indices. Para confirmar presionar el boton "Choose Weapon".
* En la eleccion del arma puede darse el caso que esta no sea compatible con el tipo de personaje escogido, instancia en la cual se mostrara un mensaje de error (en consola), permitiendo de igual forma escoger un arma correcta. 
* Repetir estos pasos 4 veces mas hasta completar el equipo de 5 jugadores, momento en el cual se mostrara en pantalla automaticamente el campo de batlla.
* El campo de batalla consiste en la representacion del equipo del jugador (a la izquierda de la pantalla) y del equipo enemigo (a la derecha de la pantalla) con sus stats respectivos , ademas de informacion del arma equipada por el personaje dueño del turno y la identificacion por nombre de este (Playing... [nombre]). Contiene tambien dos botones que se dehabilitaran (o no) dependiendo si el personaje en cuestion es jugador o enemigo: "Change Weapon" que llevara a la ventana de seleccion de armas y "Attack" que llevara a la ventana de seleccion del enemigo objetivo.
* Tras presionar "Change Weapon" se mostrara la ventana de seleccion de armas, la cual permite seleccionar exactamente un arma para el cambio (clickeando en algun circulo del costado izquierdo junto a los indices). Para confirmar el cambio presionar "Choose Weapon".
* Presionar el boton "Attack" llevara a la ventan de seleccion de enemigos, la cual de igual forma que las ventanas de seleccion anteriores, permite escoger un enemigo objetivo del ataque clickeando los circulos del costado izquierdo. Para confirmar el objetivo presionar "Choose Enemy", se realizara el ataque y se reducira la vida de ese enemigo segun corresponda con los stats de ambos. En este punto termina el turno del personaje actual y se da paso al siguiente de la lista.
* A medida que se van realizando ataques y van siendo derrotados personajes (tanto jugadores como enemigos), estos se identificaran en el campo de batalla con un color gris claro.
* Esto se repite hasta que alguno de los dos equipos termine con todos sus integrantes derrotados, momento en el cual automaticamente se deshabilitaran los botones, se terminara el juego y se mostrara un mensaje en pantalla indicando el resultado: 
  - Si ganaste: Winner Winner Chicken Dinner.
  - Si perdiste: You Died.
* Finalmente para salir del juego basta con cerrar su ventana clickeando en la "x" (Esto puede depender del sistema operativo)
### Supuestos.
* Se considera que los puntos de defensa, ataque, salud inicial y mana inicial de cada personaje (segun corresponda) seran fijos al iniciarlos, es decir, no se veran afectados por algun estado. Esto implica que son considerados como atributos diferenciables.
* En cambio, los puntos de vida o mana actuales, podran variar a lo largo de la partida (al recibir o realizar ataques por ejemplo). Esto implica que no son considerados como atributos diferenciables.
* Un player puede atacar tanto a un player como a un enemy. Lo mismo ocurra con un enemy.
* Si un personaje muere (hp=0) aun mantiene su arma equipada, con la posibilidad de ser revivido en algun momento.
* Un personaje derrotado no puede recibir ni realizar daño.
* Existe la posibilidad de que un personaje se ataque a si mismo (Esta tan confuso que se hirio a si mismo!).
* Por el momento si es posible instanciar un personaje muerto (hpMax=0) o con hpMax y defensePoints negativos. Posteriormente se planea mediante el uso excepciones corregirlo.
* Un jugador dispone de exactamente 5 personajes jugables, entre los cuales puede haber Caballeros, Ladrones, Ingenieros, Magos Negros y Magos Blancos. Estos 5 personajes controlados por el jugador se llamara "party".
* La cantidad de enemigos no es fija pero por combate solo pueden participar 8.
* Un personaje jugable comienza el combate con arma equipada, siendo posible cambiarla durante este (no puede esperar un turno si no tiene una).
* El juego permite escoger 5 personajes jugables, de una lista de predefinidos y equiparles armas a cada uno previo al combate
### Funcionamiento y logica.
#### Definicion de clases
* Un personaje, en lineas abstractas, posee un nombre, puntos de vida iniciales, puntos de vida actuales, puntos de defensa y una lista de turnos de juego. Como tal posee metodos para obtener esas variables, para establecer un nuevo punto de vida y esperar un turno.
* Un personaje jugable, es un tipo de personaje abstracto que posee ademas la posibilidad de equiparse armas. Este posee metodos para obtener esa variable, inicializada por defecto como vacia, y equiparse un arma. Implementa de manera particular el esperar un turno, de su padre.
* Los personajes jugables magicos, son tipos asbtractos de personajes jugables que poseen de manera extra un atributo de mana inicial y mana actual, junto a sus getters y el setter del mana actual.
* Los Caballeros, Ladrones e Ingenieros son tipos particulares de personajes jugables, mientras que los Magos Negros y Magos Blancos, son tipos particulares de personajes jugables magicos. Todos ellos implementan de forma particular el equipamento de un arma.
* Los enemigos, por otro lado, son tipos particulares de un personaje, estos no son jugables. Como tal poseen de manera extra un peso y puntos de ataque, junto con sus respectivos metodos para obtenerlos. Al ser un personaje NO jugable, implementa de manera particular y distinta el metodo de esperar turno.
* Los distintos tipos de personajes jugables tienen la posibilidad de equiparse de manera distinta un arma (dependiendo de su tipo). Esta fucionalidad (junto a otras mas), sera desarrollada en la proxima entrega, por el momento se definio de igual forma para todas las clases.
* Un arma en general posee un nombre, daño y peso asociado, junto a sus getters.
* Existen 5 tipos particulares de armas: Las hachas, los arcos, los cuchillos, las espadas y los bastones. Estos ultimos poseen de manera especial un daño magico usado para efectuar distintos hechizos.
* Todas las armas pueden ser equipadas de manera exclusiva por algunos personajes.
#### Equipar
* Al equipar un arma de manera especializada, como no se sabe en principio que tipo de arma se refiere (equip(IWeapon) puede recibir todo tipo) y dado que el equiparse es distinto de acuerdo al personaje utilizado, se utiliza DOUBLE DISPATCH.
  - Cada personaje si bien no sabe qué se le equipara, si sabe que tipo de personaje es él, por lo que mandara su "tipo" en un mensaje hacia el arma "generica", mensaje que, gracias al polimorfismo, se recibira en la instancia correcta que respondera si esta equipacion corresponde o no (true o false). Este booleano determinara en la funcion equip(IWeapon), el equipamento, o no, del arma en cada personaje.
* Si un arma no puede ser equipada por un tipo de personaje, entonces este mantendra la que tenia hasta el momento (generar excepcion en futuras entregas).
* Un personaje derrotado (hp=0) no puede equparse armas (generar excepcion en futuras entregas).
#### Ataque
* El atacar a otro personaje no dependera del atacado (a todos de igual forma se le descontara puntos de vida), sino del atacante. Si el personaje que ataca: 
  - Es un jugador con arma equipada => hara un daño base igual al daño del arama equipada.
  - Es un enemigo => hara un daño base igual a sus puntos de ataque.
* Si un personaje realiza un daño base menor a los puntos de defensa del personaje atacado, el ataque fallara (no realiza daño, no puede realizar un ataque de daño negativo).
* Si un personaje realiza un daño efectivo (dañoBaseAtacante-defensaAtacado) mayor que los puntos de vida del contrincante, este solo se descontara esa cantidad (los puntos de vida no pueden ser negativos)
* Para mantener el principio de Single Resposability, el atacante no puede acceder a los elementos del atacado, sino que es el mismo atacado quien se preocupa de manejar sus puntos de vida de acuerdo al daño base recibido mediante el metodo beAttacked().
* Un personaje queda fuera de combate al llegar a 0 puntos.
#### Controlador
* El controlador sirve de conexion logica entre la vista y el modelo, ejecuta todas las operaciones que un jugador podria querer efectuar, entrega los mensajes necesarios a cada objeto del modelo guarda la informacion mas importante del estado del juego en cada momento.
* Al inicializarse el controlador, entre otros atributos, se genera de manera aleatoria la cantidad de enemigos (un vaor entre 1 y 8).
* El usuario dispone de un inventario para las armas, inicialmente vacio, al cual se le pueden añadir con los metodos create por cada tipo (con limite de 30). De esta lista pueden salir y entrar armas, tal que si un personaje jugable cambia de arma, el arma desequipada vuelve al inventario y la nueva equipada sale de el.
* Existe una lista de stats de personajes inicialmente vacia, a la cual se le pueden añadir distintos valores con los metodos create para cada tipo de personaje jugable (con un limite de 50). De esta lista, el jugador escoge 5 para su party (cada uno mediante el metodo selectionPLayer()). 
  - El metodo selectionPlayer(int, String) crea un personaje acorde al elegido por el usuario (mediante el indice de la lista de playerStat), con un nombre asignado por él, lo equipa con un arma del inventario y lo agrega a la lista de la party. El stat escogido sale de la lista, tal que no pueda escogerse nuevamente.
* De manera similiar, existen 2 listas independientes, iniciamente vacias, con nombres y stats de enemigos, a las cuales se le pueden añadir valores mediante los metodos createEnemyName() y createEnemyStat respectivamente (cada una con limite de 50). De estas listas, con el metodo selectionEnemy(), se escogen aleatoriamente dos elementos (uno dae cada una) y se crea un enemigo con los valores seleccionados
#### Turnos
* Los turnos son los espacios en que el jugador esté utilizando a uno de sus personajes (seleccionando o realizando una acción) además de los momentos en los que los enemigos "decidan" qué hacer.
* Todos los personajes comparten la misma lista de turnos, por lo que cada uno tiene la capacidad de modificarla (Al cambiar de arma un player, por ejemplo, si cambia el arma, puede cambiar el peso del arma, por lo que al terminar su turno cambiara el orden que pudo hbaer sido)
* Durante el combate se escoge al primer personaje en la lista de turnos y dependiendo si es jugable o no (player o enemy), se realiza un determinado cambio de fase de juego.
  - En el turno de cada personaje el jugador puede cambiar el arma de éste tantas veces como quiera, pero para terminar el turno debe atacar.
  - En el turno de cada enemigo, este realiza un ataque a un player aleatorio de la party.
* Para implementar el sistema de turnos se hizo uso de Observer Pattern, para notificar al controlador (mediante handlers) cuando un personaje muere, para llevar la cuenta de caidos y determinar cuando el juego termina y quien gana, y cuando la lista de turnos no esta vacia, y no se tenga que esperar ineficientemente a que esta se llene al estarlo.
  - Hay 2 handlers que reciben la notificacion de Enemy y AbstractPLayerCharacter cuando un personaje muere, uno por cada tipo (Enemy, IPlayer).
  - Hay un handler que recibe la notificacion de TurnList () cuando la lista de turnos no es vacia, caso en que efectivamente puede aplicarse el metodo turnCharacter() que determina el inicio y fin de turno de un personaje.
* Si todos los enemigos mueren se gana y si toda la party muere se pierde. Esto se implemento mediante el uso de los metodos isInGame(), isWin() e isLose() que verifican la cantidad de personajes y enemigos muertos y determinan el estado del juego.
#### Fases.
* El juego comienza en la fase de "Titulo" y termina en la del "Fin"
  - De "DrawTitlePhase" puede pasar a:
    + "SelectionPlayerPhase" para empezar el juego seleccionando personajes.
  - De "SelectionPlayerPhase" puede pasar a:
    + "SelectionWeaponPhase" para seleccionar un arma para equipar al personaje en cuestion.
    + "TurnsPhase" cuando se haya alcanzado la cantidad maxima de jugadores permitidos en la party (5), y asi dar inicio a la "batalla".
  - De "SelectionWeaponPhase" puede pasar a:
    + "SelectionPlayerPhase" para seleccionar un nuevo integrante de la party.
    + "PlayerTurnPhase" para que ya estando en batalla, luego de cambiar el arma vuelva a su turno.
  - De "TurnsPhase" puede pasar a:
    + "PlayerTurnPhase" si el primer personaje de la lista de turnos es un jugador, entonces se inicia el turno del jugador.
    + "EnemyTurnPhase" si el primer personaje de la lista de turnos es un enemigo, entonces se inicia el turno del enemigo.
    + "EndPhase" cuando el juego termine, es decir, cuando todos los miembros de la party o todos los miembros del equipo enemigo esten derrotados.
  - De "PlayerTurnPhase" puede pasar a:
    + "SelectionWeaponPhase" para cambiar de arma.
    + "SelectionAttackPhase" para seleccionar el enemigo a atacar y terminar el turno del jugdaor.
  - De "EnemyTurnPhase" puede pasar a:
    + "SelectionAttackPhase" comparte la misma implementacion que para un player, pero quien recibe el ataque es un personaje de la party escogido al azar.
  - De "SelectionAttackPhase" puede pasar a:
    + "TurnsPhase" para "reiniciar" el ciclo con un nuevo personaje en la lista de turnos.
  
* ![alt text](https://github.com/CC3002-Metodologias/final-reality-SSanchez7/blob/parcial/diagram.jpg?raw=true)

#### Interfaz.
* Lo mostrado en la interfaz dependera de la fase en que se encuentre el juego.
* El juego comienza con la pantalla de titulo (fase "DrawTitle").
* Al presionar el boton la pantalla cambia a seleccion de jugadores (Cambio a "SelectionPlayerPhase"). Por defecto se mostrara una lista con los 5 tipos de personajes disponibles en el juego, para tener variedad en la eleccion y generar combinaciones de equipo interesantes.
* Despues para seleccionar el arma y equiparla al jugador escogido se procede de manera similar, se selecciona de una lista de disponibles (cambio a "SelectionWeaponPhase"). Las armas que no puedan ser equipadas en el personaje, mostraran un mensaje de error al intentar quiparlas (Por tiempo el error solo se muestra en la consola)
* Estos dos ultimos pasos se repiten hasta completar todo el equipo (maximo 5m personajes), a pratir de donde se da inicio a la batalla (cambio a "TurnsPhase") mostrandose en pantalla los listados de jugadores y enemigos, con sus respectivos stats y armas equipadas (para el caso de jugadores), identificando tambien en una pestaña el nombre del personaje "dueño" del turno.
* Dependiendo del tipo de este personaje (IPlayer o Enemy) se deshabilitan (o no) los botones para atacar o cambiar armas (solo disponibles para jugadores)
* Al presionar el boton "Change Weapon" se mostrara en pantalla el inventario con la lista de armas disponibles para cambiar, que funcionara de igual manera que en la primera equipacion de armamento y que se ira actualizando a medida que se hagan los cambios (cambio a "SelectionWeaponPhase" y luego vuelve a "PlayerTurnPhase").
* Al presionar el boton "Attack" se mostrara en pantalla la lista de enemigos, de los cuales solo pueden escogerse aquellos que continuan con vida (Hp>0), para seleccionar el objetivo del ataque, dando por finalizado el turno del personaje (Cambio a "SelectionAttackPhase" y luego a "TurnsPhase").
* El juego continua hasta que uno de los equipos no pueda continuar (todos sus integrantes derrotados), instante en donde se da por finalizado el juego y se muestra en pantalla el resultado (cambio a "EndPhase").
