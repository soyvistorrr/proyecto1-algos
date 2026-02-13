# Proyecto I: Grafo - Implementación con Listas de Adyacencia

**Universidad Simón Bolívar**  
**Departamento de Computación y Tecnología de la Información**  
**Asignatura:** Algoritmos y Estructuras de Datos III (CI-2693)  
**Trimestre:** Enero-Marzo 2026  

## Integrantes
 
### Nombre Completo y Carnet
Victor Hernandez 20-10349  
Daniela Gragirena 19-10543

## Instrucciones de Ejecución

El proyecto ha sido desarrollado utilizando el lenguaje **Kotlin**. A continuación se detallan los pasos para compilar y ejecutar el código fuente.

### 1. Requisitos Previos
* Tener instalado el JDK (Java Development Kit) versión 8 o superior.

* Tener instalado el compilador de Kotlin (kotlinc) configurado en el PATH del sistema.

### 2. Ejecución desde la Terminal
Para compilar y ejecutar el proyecto correctamente, siga estos pasos:

* **Ubicación:** Abra una terminal (CMD o PowerShell) y navegue hasta la carpeta raíz del proyecto donde se encuentran los archivos .kt.

cd ruta/a/tu/proyecto  

* **Compilación:** Ejecute el siguiente comando para compilar todos los archivos fuente (Grafo.kt, ListaAdyacenciaGrafo.kt, Main.kt) simultáneamente y generar un ejecutable .jar:

kotlinc *.kt -include-runtime -d ProyectoGrafo.jar  

(**Nota:** Es importante usar *.kt para que el compilador reconozca todas las clases e interfaces al mismo tiempo).

* **Ejecución:** Una vez generado el archivo sin errores, ejecute el programa con:

java -jar ProyectoGrafo.jar

## Ejemplo de Uso y Prueba

Para verificar el correcto funcionamiento de todas las funcionalidades implementadas, se puede utilizar el siguiente código en el archivo `Main.kt`. Este ejemplo cubre la creación del grafo, conexión de vértices y generación de un subgrafo.



## Complejidad Computacional (Big O)

A continuación se presenta el análisis de complejidad temporal asintótica para cada método implementado en la clase `ListaAdyacenciaGrafo`.

**Leyenda:**
* V: Número total de vértices en el grafo.
* E: Número total de arcos (lados) en el grafo.
* D_{out}: Grado de salida del vértice (cantidad de sucesores).

**agregarVertice**  
**Complejidad:** O(1)  
La inserción en un `MutableMap` (HashMap) tiene un costo amortizado constante. Solo se verifica si la clave existe y se agrega la nueva entrada.  

**conectar**  
**Complejidad:** O(D_{out})  
Aunque el acceso al vértice origen es O(1), se debe recorrer su lista de adyacencia (de tamaño D_{out}) para verificar que el arco no exista previamente y evitar duplicados.  

**contiene**  
**Complejidad:** O(1)  
La búsqueda de una clave en un `HashMap` (operación `containsKey`) es una operación de tiempo constante promedio.  

**eliminarVertice**  
**Complejidad:** O(V + E)  
Eliminar el vértice del mapa es O(1), pero es necesario recorrer las listas de adyacencia de todos los demás vértices (V) y sus aristas (E) para encontrar y eliminar cualquier arco entrante hacia el vértice borrado.  

**obtenerArcosSalida**  
**Complejidad:** O(1)  
Se retorna directamente la referencia a la lista de sucesores almacenada en el mapa. No requiere iterar ni copiar elementos.  

**obtenerArcosEntrada**  
**Complejidad:** O(V + E)  
Dado que la implementación es por listas de adyacencia (solo conocemos los sucesores), debemos recorrer todos los vértices (V) y sus respectivas listas de arcos (E) para filtrar quiénes apuntan al vértice objetivo.  

**tamano**  
**Complejidad:** O(1)  
Se utiliza la propiedad `.size` del mapa, la cual mantiene un contador interno actualizado automáticamente, evitando un conteo lineal.  

**subgrafo**  
**Complejidad:** O(V' + E')  
Se iteran únicamente los vértices de la colección solicitada y sus vecinos directos. Gracias a las verificaciones O(1) del mapa, la complejidad es lineal respecto al tamaño del subgrafo resultante (V' vértices y E' arcos).  



## Explicación de Decisiones de Implementación

Para el desarrollo de la solución, se tomaron decisiones de diseño fundamentadas tanto en la eficiencia algorítmica como en las características modernas del lenguaje Kotlin. A continuación se detallan los puntos clave:

### 1. Representación del Grafo: Listas de Adyacencia
Se optó por implementar el grafo utilizando el modelo de **Listas de Adyacencia** en lugar de una Matriz de Adyacencia.
* **Justificación:** La complejidad espacial de una matriz es siempre O(V^2), lo cual es ineficiente para **grafos dispersos** (aquellos con pocos arcos en relación a los vértices), que son el caso más común en aplicaciones reales. Las listas de adyacencia reducen el consumo de memoria a O(V + E).

### 2. Estructuras de Datos Internas (`HashMap` + `ArrayList`)
Siguiendo las recomendaciones del enunciado, se utilizó la colección `MutableMap<T, MutableList<T>>` como núcleo de la clase:
* **Elección del Mapa (HashMap):** Se utilizó un mapa hash para almacenar los vértices como claves. Esto permite verificar la existencia de un vértice (`contiene`) y acceder a su lista de vecinos en tiempo constante promedio O(1). Si hubiéramos usado una lista simple para guardar los vértices, estas operaciones costarían O(V).
* **Elección de la Lista (MutableList):** Se utilizó una lista dinámica para almacenar los sucesores de cada vértice, permitiendo la inserción de arcos en tiempo amortizado constante y preservando el orden de inserción.

### 3. Enfoque Declarativo (Programación Funcional)
Para métodos que requieren procesamiento de colecciones, como `obtenerArcosEntrada`, se decidió evitar los bucles imperativos anidados (`for` dentro de `for`) en favor de funciones de orden superior (`filter` y `map`).
* **Justificación:** Este enfoque hace que el código sea más legible y menos propenso a errores de índices. Además, delega la iteración a las optimizaciones internas de la biblioteca estándar de Kotlin.

### 4. Algoritmo de Subgrafo
Para el método `subgrafo`, se implementó una estrategia constructiva de dos pasos:
1.  **Filtrado de Vértices:** Primero se agregan todos los nodos válidos al nuevo grafo.
2.  **Reconstrucción de Arcos:** Luego se iteran los vértices originales y se copian las conexiones solo si el destino también existe en el subgrafo.
* **Justificación:** Esta separación garantiza que el método `conectar` nunca falle por falta de un vértice destino, y aprovecha la búsqueda rápida en el mapa (O(1)) para verificar si un vecino debe ser incluido, manteniendo la eficiencia del algoritmo.

### 5. Manejo de Seguridad de Tipos (Null Safety)
Se aprovechó el sistema de tipos de Kotlin para garantizar la robustez:
* Uso del operador Elvis (`?:`) para retornar listas vacías cuando se consultan los vecinos de un vértice inexistente.
* Uso de llamadas seguras para evitar `NullPointerException`, un error común en implementaciones en Java.