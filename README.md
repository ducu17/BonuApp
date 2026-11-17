# BonuApp — Plataforma Web de Fidelización y Bonos Virtuales

Aplicación web full-stack responsiva desarrollada como proyecto productivo para el programa **Análisis y Desarrollo de Software (ADSO)** del **SENA**. **BonuApp** permite a los usuarios acumular y gestionar bonos virtuales por realizar búsquedas y compras en comercios aliados, impulsando la fidelización digital.

---

## Tabla de Contenidos
1. [Características Principales](#características-principales)
2. [Arquitectura y Tecnologías](#arquitectura-y-tecnologías)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Requisitos Previos](#requisitos-previos)
5. [Instalación y Configuración Local](#instalación-y-configuración-local)
6. [Endpoints Principales (API REST)](#endpoints-principales-api-rest)
7. [Pruebas de Software](#pruebas-de-software)
8. [Autor](#autor)

---

## Características Principales
* **Gestión de Usuarios (CRUD):** Registro de nuevos clientes, inicio de sesión con validación de credenciales, actualización de perfil y eliminación de cuenta.
* **Sistema de Acumulación de Bonos:** Asignación automática de bonos virtuales tras registrar búsquedas o compras en comercios aliados.
* **Consulta de Saldo e Historial:** Visualización en tiempo real de bonos disponibles, comercios participantes y movimientos realizados.
* **Diseño Responsivo (Mobile-First):** Interfaz adaptable a computadores de escritorio y dispositivos móviles.

---

## Arquitectura y Tecnologías

| Capa | Tecnología | Descripción |
| :--- | :--- | :--- |
| **Frontend** | HTML5, CSS3, JavaScript, JSP | Vistas dinámicas, formularios validados y consumo asíncrono de servicios. |
| **Backend** | Java (Jakarta/Java EE Servlets) | Controladores, lógica de negocio, patrón DAO y gestión de sesiones. |
| **Servidor Web** | Apache Tomcat | Contenedor de Servlets y servidor de aplicaciones local. |
| **Base de Datos** | MySQL (phpMyAdmin / Workbench) | Persistencia relacional de usuarios, comercios aliados y bonos. |
| **Herramientas** | IntelliJ IDEA, Postman, GitHub Desktop | Desarrollo, pruebas de endpoints REST y control de versiones. |

---

## Estructura del Proyecto

```text
BonuApp/
├── database/
│   └── bonuapp_db.sql           # Script SQL con estructura de tablas y datos de prueba
├── src/
│   └── main/
│       ├── java/
│       │   └── com/bonuapp/
│       │       ├── config/      # Clase de conexión JDBC a MySQL
│       │       ├── controller/  # Java Servlets (Auth, Usuarios, Bonos, Comercios)
│       │       ├── dao/         # Operaciones CRUD contra la base de datos
│       │       └── model/       # Entidades (Usuario, Bono, Comercio, Transaccion)
│       └── webapp/
│           ├── css/             # Hojas de estilo responsivas
│           ├── js/              # Validaciones y peticiones Fetch/AJAX
│           ├── WEB-INF/         # Configuración web.xml
│           └── index.jsp        # Página principal de la aplicación
└── README.md