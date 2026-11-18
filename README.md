# BonuApp — Plataforma Web de Fidelización y Bonos Virtuales

Aplicación web full-stack desarrollada como proyecto productivo para el programa **Análisis y Desarrollo de Software (ADSO)** del **SENA**. **BonuApp** permite a los usuarios gestionar cuentas, acumular y consultar bonos virtuales mediante comercios aliados.

---

## Tabla de Contenidos
1. [Características Principales](#características-principales)
2. [Arquitectura y Tecnologías](#arquitectura-y-tecnologías)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Requisitos Previos](#requisitos-previos)
5. [Instalación y Configuración Local](#instalación-y-configuración-local)
6. [Controladores (Servlets y API)](#controladores-servlets-y-api)
7. [Autor](#autor)

---

## Características Principales
* **Gestión de Usuarios (CRUD):** Registro, autenticación (`LoginServlet`), actualización y control de perfiles de usuario.
* **Sistema de Premios y Bonos:** Consulta y gestión de bonos y puntos a través de `PremioRestController` y `PremioDAO`.
* **Diseño e Interfaz Web:** Vistas dinámicas en JSP (`login.jsp`, `registro.jsp`, `index.jsp`, `bono.jsp`) respaldadas por componentes CSS y recursos multimedia organizados.

---

## Arquitectura y Tecnologías

| Capa | Tecnología / Herramienta | Descripción |
| :--- | :--- | :--- |
| **Frontend** | JSP, HTML5, CSS3, JavaScript | Vistas de usuario y formularios responsivos. |
| **Backend** | Java Servlets, JDBC | Controladores de peticiones y lógica de negocio. |
| **Servidor Web** | Apache Tomcat | Contenedor de servlets para entorno local (`localhost`). |
| **Base de Datos** | MySQL | Persistencia relacional (`bonuapp.sql`). |
| **Entorno y Control** | IntelliJ IDEA, GitHub Desktop | IDE de desarrollo y control de versiones. |

---

## Estructura del Proyecto

```text
BonuApp/
├── database/
│   └── bonuapp.sql              # Script SQL con estructura de tablas
├── docs/
│   ├── MANUALES BONUAPP.docx    # Documentación técnica y de usuario
│   └── swagger.yaml             # Especificación de la API
├── src/
│   └── main/
│       ├── java/
│       │   └── com/bonuapp/
│       │       ├── config/      # Filtros de configuración (CORSFilter)
│       │       ├── controller/  # Servlets (LoginServlet, RegistroServlet, UsuarioServlet, PremioRestController)
│       │       ├── dao/         # Objetos de Acceso a Datos (UsuarioDAO, PremioDAO)
│       │       ├── model/       # Clases de entidad (Usuario, Premio)
│       │       └── util/        # Conexión a Base de Datos (DatabaseConnection)
│       └── webapp/
│           ├── components/      # Componentes UI (CSS / JSX)
│           ├── css/             # Hojas de estilo (estilos.css)
│           ├── img/             # Logos y recursos gráficos (logos de marcas, etc.)
│           ├── WEB-INF/         # Configuración web.xml y librerías JAR (.jar)
│           ├── index.jsp        # Página principal
│           ├── login.jsp        # Vista de inicio de sesión
│           ├── registro.jsp     # Vista de registro de usuarios
│           └── bono.jsp         # Vista de gestión de bonos
├── pruebas.http                 # Pruebas de endpoints HTTP
└── README.md