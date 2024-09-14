# Proyecto Java con MySQL - Primera Prueba

Este proyecto es una primera prueba de integración entre Java y MySQL. Se ha diseñado una pequeña aplicación que realiza consultas básicas a una base de datos utilizando JDBC (Java Database Connectivity).

## Estructura del Proyecto

El proyecto está organizado en varios paquetes, cada uno con una responsabilidad específica:

1. **app**
   - Contiene la clase `Principal`, que es el punto de entrada de la aplicación.
   - Aquí es donde se inicia la conexión con las bases de datos y se ejecutan las consultas.

2. **basesdedatos**
   - Contiene la clase `BasesDeDatos`, que gestiona las dos bases de datos utilizadas en la prueba.
   - Son 2 bases de datos que contienen 1 tabla con 1 o 3 columnas.

3. **conexion**
   - Contiene la clase `Conexion`, que se encarga de establecer la conexión con las bases de datos utilizando JDBC.
   - Gestiona la configuración de la conexión (URL, usuario, contraseña) y el manejo de errores durante la conexión.

4. **consultas**
   - Contiene cuatro clases, cada una de las cuales realiza una operación SQL típica:
     - `ConsultaSelect`: Realiza una consulta de selección (`SELECT`) sobre la base de datos.
     - `ConsultaInsert`: Inserta nuevos registros en la base de datos (`INSERT INTO`).
     - `ConsultaUpdate`: Actualiza registros existentes en la base de datos (`UPDATE`).
     - `ConsultaDelete`: Elimina registros de la base de datos (`DELETE`).

## Configuración del Proyecto

### Requisitos Previos

- Java Development Kit (JDK) 8 o superior.
- MySQL Server instalado y configurado.
- Driver JDBC para MySQL (`mysql-connector-java`) incluido en el classpath.
