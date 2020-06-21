# Tortoise


Proyecto Búsqueda de Vuelos

Augusto Lombino

                                                                                                                                        



La aplicación es
desarrollada en Android Studio, utilizando el lenguaje, Kotlin.



Repositorio: https://github.com/AugustoLL/Tortoise




La aplicación se decidió realizar
en Kotlin ya que no solo es completamente interoperable con Java, sino que
provee una sintaxis mucho más simple que elimina código innecesario y es fácil
de entender y entre otras funciones me permite el uso de DataClasses, que en la
aplicación usé para alojar los datos obtenidos como respuesta en formato JSON
de la API. También me brinda seguridad contra los típicos errores de Java como
la seguridad de nulls.



La función de la aplicación
desarrollada consiste en conectarse con una API y obtener un JSON como
respuesta para mostrar vuelos de Mendoza a cualquier parte del mundo. Una vez
obtenida una respuesta de la API, se guardará la información del JSON y se
mostrará la aerolínea del vuelo, el origen, la fecha del vuelo, el destino y el
precio en euros.



El proyecto se ha desarrollado
utilizando los íconos de la página Material Design de google (https://material.io/resources/icons/?style=baseline).
Se decidió utilizar la misma ya que no solo proporciona una gran cantidad de
íconos en buena calidad, sino que los proporciona en formato SVG (Gráficos Vectoriales Escalables en formato
XML), que es el que se debe utilizar en Android Studio. Además, la API se
obtuvo utilizando la página Rapid Api, que permite testear, y buscar más de mil
APIs. Usando ésta página, se decidió utilizar la API de Skyscanner, que permite
buscar vuelos, y la misma devuelve los resultados basados en los parámetros que
se ingresan, como el país de origen, provincia de origen, destino y fechas.



 




 
 
  
  
  
  
  
  
  
  
  
  
  
  
 
 
 

 




 



 

A continuación, se desarrolla la arquitectura y el código de la aplicación:

 



La aplicación se ha desarrollado
con el objetivo de utilizar y aprender cómo usar los componentes de Navegación
y las Corrutinas de Android, incluidas en la librería de JetPack, y para
demostrar cómo Kotlin, el lenguaje utilizado para desarrollar la app,
simplifica la creación de Singletons y de Factory Methods con el uso de Objects
y Companion Objects respectivamente.



 



Singletons vs Objects

 



Como sabemos, un Singleton es un
patrón de diseño que me garantiza que una clase solo tenga una sola instancia,
y a su vez que la clase proporcione un acceso global para dicho Singleton. Sin
embargo, en Java no es tan simple de implementar, ya que se debe garantizar que
si se está trabajando con hilos, o en el caso de mi aplicación con corrutinas,
si dos hilos o corrutinas acceden a la vez a un singleton, se podrían generar
dos instancias del mismo. A continuación se provee un ejemplo genérico de cómo
crear un Singleton en Java:




 




Como se puede observar, se
necesitan de varias líneas sólo para evitar errores. En cambio, en Kotlin se
utilizan Objects, que es la representación de los Singleton en Kotlin, se crean
si necesidad de hacer uso de una clase, y el mismo puede contener propiedades y
métodos o funciones pero no se permite un constructor, y a su vez, no pueden
ser instanciados como se instancian a las clases, sino que los Objects se
instancian la primera vez que son usados. A continuación se provee un ejemplo
genérico de cómo crear un Object en Kotlin, y luego se muestra cómo se utilizó
en el proyecto:




 




En la aplicación, usé el Object
para poder hacer la conexión con la API, ya que no necesitaba crear varias
instancias de una clase que cumplieran esa función, pero si necesitaba hacer
uso de atributos y funciones. A su vez, no me tuve que preocupar de cuidar que
no sea instanciado más de una vez, como en Java, ya que el objeto es
instanciado cuando se usa por primera vez.




 




Factory Methods vs Companion Objects

 



El patron creacional Factory Method, consiste en utilizar métodos
de “fábrica” para poder evitar el problema de tener que especificar la clase
exacta cuando se crea un objeto. En Kotlin se puede hacer uso de los Companion
Objects, que permiten una fácil implementación de los Factory methods. A
continuación se muestra un ejemplo genérico de Factory Methods en Kotlin:




 




Y a continuación se muestra cómo
se implementó en la aplicación. Cómo se puede observar se usó el método
setFlightsList() para poder crear un objeto del tipo Flights. Si en un futuro
yo quisiera agregarle a la aplicación una conexión con una API de hoteles,
podría cambiar la clase Flight para que se adapte a ambos tipos de objetos y
según las variables que recibe por parámetro, utilizar el método
setFlightsList() si es un vuelo, o setHotelsList() si es un Hotel. De esta
manera logro implementar de manera correcta el patrón de Factory Method.



 




 




 

 



 



 



 



Corrutinas y Navigation Components

 



Las corrutinas son patrones de
diseño de simultaneidad que se pueden usar en Android para simpificar el código
que se ejecuta de manera asíncrona. Las corrutinas ayudan a solucionar dos problemas
principales:



·        
Administran tareas prolongadas que podrían
bloquear el subproceso principal y provocar que la aplicación quede trabada



·        
Proporcionan seguridad del subproceso principal



Para administrar tareas
prolongadas, como las solicitudes de red, análisis e JSON, leer o escribir
desde una bse de datos o iterar listas de gran tamaño, se deben ejecutar fuera
del subproceso principal.Para ello se hace uso de SUSPEND.



Por el otro lado, para asegurar
la seguridad del subproceso principal, las corrutinas deben usar despachadores
para determinar qué subprocesos se utilizan para la ejecución de corrutinas.
Existen tres despachadores:



·        
Dispatchers.Main:
que se utiliza para ejecutar corrutinas en el subproceso de Android principal.
Sólo se debe usar para interctuar con la IU y realizar trabajos rápidos.



·        
Dispatchers.IO:
Está optimizado para realizar E/S de disco o red fuera del subproceso
principal. Se debe usar para leer desde archivos o ejecutar operaciones de red



·        
Dispatchers.Default:
Está optimizado para realizar trabajo que usa la CPU de manera intensiva.
Se debe usar para clasificar una lista muy grande o para analizar JSON de gran
tamaño.



En mi aplicación utilicé tanto Dispatchers.Main como Dispatchers.IO, ambos son usados en el
método apiRequest que se muestra a continuación. Primero creo una corrutina
para el IO, en la cual obtengo los datos de la api y los guardo en el companion
object de Flight, y la segunda corrutina es en el Main, se utiliza para
modificar la Interfaz de la aplicación con los datos obtenidos de la API.



 




 




Por otro lado también utilicé
Navigation Component, que me permite modificar las interacciones que les
permiten a los usuarios navegar a través de las distintas Actividades y
Fragmentos de mi aplicación. Para ello tuve que crear un gráfico de navegación,
que es un archivo XML que contiene toda la información relacionada a la
navegación dentro mi aplicación. Tuve que crear también un NavHost, que es un
contenedor vacío que muestra los destinos del gráfico de navegación. En mi
caso, el NavHost es la única actividad que posee la aplicación, y el mismo se
encarga de alojar a los distintos fragmentos (como el FragmentMain, que muestra
los botones y el FragmentFlights, que muestra los vuelos obtenidos). Y
finalmente creé un NavController, que es un objeto usado en la actividad
principal para administrar la navegación de la app dentreo de un NavHost. Es
decir a través de él se determina que ruta debe tomar a medida que los usuarios
se mueven en la aplicación.



 



Producto Final

 




 
                  
 




 



 



 



 



La aplicación es capaz de mostrar
los principales vuelos de Mendoza a cualquier lugar del mundo, utilizando
patrones como Corrutinas, Singleton, FactoryMethods y la librería de NavigationComponent.
En un futuro se buscará mejorar la aplicación para también poder implementar el
patrón de arquitectura de Android que recomienda Google, que es el Modelo Vista Presentador. También se
buscará utilizar DataBinding, que es una biblioteca de vinculación de datos que
permite vincular los componentes de la IU a las fuentes de datos de la
aplicación. Así como también lograr que la aplicación guarde la información en
paquetes, o Bundles, para que si por ejemplo se rota la pantalla, lo que provoca
que la actividad sea destruida y creada nuevamente, no tenga que volver a pedir
los datos de la API, sino que pueda usar los datos guardados en el paquete. Finalmente
se buscaba implementar una API de hoteles, pero por falta de tiempo no se
logró, ya que las APIs que se encontraron no cumplían la función que se
deseaba.



