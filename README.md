# 🚀 API REST de Gestión de Tareas (To-Do List)

Este repositorio contiene la implementación práctica de una API REST realizada como parte del curso de **"Desarrollo de una API REST con Spring Boot"** de **OpenWebinars**.

El objetivo del proyecto es gestionar tareas de forma eficiente, aplicando principios de seguridad avanzada y persistencia de datos.

---

## 🛠️ Funcionalidades Principales

La API permite realizar un CRUD completo sobre el recurso de tareas, incluyendo:

* **Gestión de Tareas:**
    * ➕ **Creación:** Registro de nuevas tareas con título, descripción y fecha límite.
    * 📝 **Edición:** Modificación de tareas existentes.
    * 🗑️ **Borrado:** Eliminación de tareas del sistema.
    * 🔍 **Consulta:** Visualización de todas las tareas o búsqueda específica por su **ID** (generado automáticamente mediante secuencias).
* **Gestión de Usuarios:**
    * 👤 Registro de nuevos usuarios para interactuar con la plataforma.

---

## 🔐 Seguridad y Control de Acceso

Un pilar fundamental de este proyecto es la seguridad implementada con **Spring Security**:

1.  **Autenticación Obligatoria:** Solo los usuarios registrados en la base de datos pueden acceder a los recursos de las tareas.
2.  **Propiedad de los Datos (Owner Check):** Las tareas están vinculadas directamente al usuario que las crea.
3.  **Restricciones de Usuario:** Para garantizar la privacidad, un usuario solo puede:
    * Visualizar sus propias tareas.
    * Editar únicamente las tareas que él mismo creó.
    * Eliminar solo sus tareas personales.
4.  **Manejo de Excepciones:** Si un usuario intenta acceder, editar o borrar una tarea que pertenece a otro autor, el sistema bloquea la acción y lanza una excepción de seguridad (403 Forbidden).

---

## 🚀 Tecnologías Utilizadas

* **Java 17+**
* **Spring Boot** (Starter Web, Data JPA, Security)
* **H2 Database** (Base de datos en memoria para desarrollo)
* **Hibernate/JPA** (Mapeo objeto-relacional)
* **Postman** (Para pruebas de los Endpoints)

---

## 📋 Requisitos Previos

Para ejecutar este proyecto localmente, asegúrate de tener:
* JDK 17 o superior.
* Maven instalado.
* Tu IDE favorito (IntelliJ IDEA, VS Code, Eclipse).

---

## 📚 Créditos
Este proyecto ha sido desarrollado siguiendo las pautas de **OpenWebinars** bajo la instrucción de sus expertos en Java y Spring Boot.

---
