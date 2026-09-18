# Fractionation-and-traceability-of-blood-components-in-a-regional-blood-center
This repo will serve as an example of how to organize information about collection journals.

## Especification-of-patterns-used
In this project the Singleton pattern was used to specify unique instances. Additionally, the Builder and Prototype patterns were employed to create the necessary objects within the system and reduce it's load; furthermore, the entire system was developed using the Abstract Factory pattern, creating distinct classes for each mode.

### Requisitos
Java 17 o superior (probado con Java 21).
Sin librerías externas, sin frameworks, sin Lombok. Solo java.time y java.util.
Cómo compilar y ejecutar

### bash
javac -d out $(find src -name "*.java")
 
java -cp out app.Main

## Estructura del proyecto

src /

 ├── modality/    Abstract Factory + Factory Method (familias por modalidad)
 
 ├── collection/  Builder (registro de colecta inmutable)
 
 ├── campaign/    Prototype (plantillas de jornada móvil)
 
 ├── service/     Lógica pura de inventario (sin patrones)
 
 └── app/         Main.java — programa de demostración
