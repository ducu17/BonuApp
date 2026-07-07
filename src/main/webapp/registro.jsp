<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>BonuApp - Registro de Usuario</title>
    <!-- Vinculamos tu hoja de estilos en la carpeta css -->
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<div class="contenedor-registro">
    <h2>Crear Cuenta en BonuApp</h2>

    <!-- El formulario apunta a la URL del Servlet usando POST -->
    <form action="UsuarioServlet" method="POST">
        <div class="campo">
            <label for="nombre">Nombre Completo:</label>
            <input type="text" id="nombre" name="nombre" required placeholder="Ej. Donaldo">
        </div>

        <div class="campo">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required placeholder="correo@sena.edu.co">
        </div>

        <div class="campo">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required placeholder="Mínimo 6 caracteres">
        </div>

        <button type="submit" class="btn-registrar">Registrarme</button>
    </form>

    <!-- Si el Servlet nos devuelve un mensaje de error o éxito, lo mostramos aquí -->
    <% if (request.getAttribute("mensaje") != null) { %>
    <div class="mensaje-alerta">
        <p><%= request.getAttribute("mensaje") %></p>
    </div>
    <% } %>
</div>
</body>
</html>