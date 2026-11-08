<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>BonuApp - Registro de Usuario</title>
    <!-- Vinculamos tu hoja de estilos unificada -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<!-- Contenedor principal que arma la tarjeta blanca centrada -->
<div class="container">

    <img src="${pageContext.request.contextPath}/img/logoBonuApp.webp" class="logo" alt="Logo BonuApp">

    <!-- Título -->
    <h2>Crear Cuenta en BonuApp</h2>

    <!-- Formulario -->
    <form action="UsuarioServlet" method="POST">

        <div class="campo">
            <label for="nombre">Nombre Completo:</label>
            <input type="text" id="nombre" name="nombre" required placeholder="Ej. Tu Nombre">
        </div>

        <div class="campo">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required placeholder="correo@ejemplo.com">
        </div>

        <div class="campo">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required placeholder="Mínimo 6 caracteres">
        </div>

        <!-- Botón de envío -->
        <button type="submit" class="btn-primary">Registrarse</button>
        <!-- Botón de envío -->
        <button type="submit" class="btn-primary">Registrarse</button>

        <p class="login-link" style="text-align: center; margin-top: 15px;">
            ¿Ya tienes cuenta? <a href="index.jsp">Inicia sesión aquí</a>
        </p>
    </form>

    </form>

</div>

</body>
</html>