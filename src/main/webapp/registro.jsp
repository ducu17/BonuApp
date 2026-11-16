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
    <form id="registroForm">
        <div class="campo">
            <label for="nombre">Nombre Completo:</label>
            <input type="text" id="nombre" name="nombre" required placeholder="Ej. Tu Nombre">
        </div>

        <div class="campo">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required placeholder="correo@ejemplo.com">
        </div>

        <!-- Asegúrate de incluir el campo usuario que exige la base de datos -->
        <div class="campo">
            <label for="usuario">Nombre de Usuario:</label>
            <input type="text" id="usuario" name="usuario" required placeholder="Ej. ducu17">
        </div>

        <div class="campo">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required placeholder="Mínimo 6 caracteres">
        </div>

        <button type="submit" class="btn">Registrarse</button>
    </form> <!-- Aquí cierra correctamente el formulario -->

    <p class="login-link">
        ¿Ya tienes cuenta? <a href="index.jsp">Inicia sesión aquí</a>
    </p>

</div> <!-- Cierra el <div class="container"> -->

<!-- ========================================== -->
<!-- SCRIPT DE ENVÍO ASÍNCRONO CON FETCH (JSON) -->
<!-- ========================================== -->
<script>
    document.getElementById('registroForm').addEventListener('submit', async function(e) {
        e.preventDefault(); // Evita el envío tradicional

        const datosUsuario = {
            nombre: document.getElementById('nombre').value,
            email: document.getElementById('email').value,
            usuario: document.getElementById('usuario').value,
            password: document.getElementById('password').value
        };

        try {
            const response = await fetch('${pageContext.request.contextPath}/api/registro', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datosUsuario)
            });

            const resultado = await response.json();

            if (response.ok && resultado.status === 'exito') {
                alert('¡Usuario registrado correctamente!');
                document.getElementById('registroForm').reset();
            } else {
                alert('Error al registrar: ' + (resultado.mensaje || 'Intenta de nuevo'));
            }
        } catch (error) {
            console.error('Error de red:', error);
            alert('No se pudo conectar con el servidor.');
        }
    });
</script>

</body>
</html>

