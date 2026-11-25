
# Sistema de Gestión de Farmacia

## Descripción

Aplicación de escritorio desarrollada en Java para apoyar la gestión y consulta del inventario de medicamentos e insumos de una farmacia.

El proyecto corresponde a un prototipo académico orientado a facilitar el registro de productos recibidos y la consulta de existencias disponibles.

## Funcionalidades implementadas

### HU-01: Registro de medicamentos e ingresos
- Registrar medicamentos e insumos.
- Editar información de medicamentos.
- Eliminar medicamentos.
- Registrar ingresos de productos mediante lotes.
- Almacenar cantidades, números de lote y fechas correspondientes.

### HU-02: Consulta de stock disponible
- Consultar los medicamentos registrados.
- Buscar productos por nombre o código.
- Visualizar las cantidades disponibles.
- Consultar los lotes asociados a cada medicamento.

## Tecnologías utilizadas

- Java 21
- JavaFX 21
- Maven
- Gson
- IntelliJ IDEA

## Arquitectura

El proyecto utiliza una estructura basada en MVC, complementada con las capas DAO y Repository para organizar la interfaz, la lógica de gestión y la persistencia de datos.

## Requisitos

- JDK 21.
- Maven.
- IntelliJ IDEA (recomendado).

## Ejecución

Clonar o descargar el repositorio y abrir el proyecto desde IntelliJ IDEA como proyecto Maven.

También es posible ejecutar la aplicación desde una terminal ubicada en la carpeta raíz del proyecto mediante:

```bash
mvn clean javafx:run
```

## Persistencia

Los datos se almacenan localmente en archivos JSON mediante Gson. La carpeta `data` se genera durante la ejecución de la aplicación y no forma parte del repositorio.

## Estado del proyecto

Prototipo funcional académico. Las funcionalidades implementadas corresponden a las historias de usuario HU-01 y HU-02.

