<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <title>BonuApp - Iniciar sesión</title>
  ...
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <title>BonuApp - Iniciar sesión</title>
  <link rel="stylesheet" href="css/estilos.css">
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<main class="page">
  <section class="card" role="main" aria-labelledby="login-title">

    <!-- Logo de BonuApp integrado -->
    <img src="${pageContext.request.contextPath}/img/logoBonuApp.webp" class="logo" alt="Logo BonuApp">

    <h1 id="login-title" class="title">Bienvenido a BonuApp</h1>
    <p class="subtitle">Gana bonos por buscar y comprar en tus tiendas aliadas</p>

    <form id="loginForm" class="form" novalidate>

      <div class="campo">
        <label for="email" class="label">Correo electrónico</label>
        <input id="email" type="email" name="email" class="input" placeholder="tucorreo@ejemplo.com" required aria-required="true">
      </div>

      <div class="campo">
        <label for="password" class="label">Contraseña</label>
        <input id="password" type="password" name="password" class="input" placeholder="••••••••" required aria-required="true">
      </div>

      <button type="submit" class="btn-primary">Iniciar sesión</button>

      <div class="links">
        <a href="#" class="link">¿Olvidaste tu contraseña?</a>
        <a href="registro.jsp" class="link">¿No tienes cuenta? Regístrate</a>
      </div>

      <div id="msg" class="msg" aria-live="polite"></div>
    </form>
  </section>
</main>

<script>
  // Validación simple demo
  const form = document.getElementById('loginForm');
  const msg = document.getElementById('msg');
  form.addEventListener('submit', (e)=>{
    e.preventDefault();
    const email = form.email.value.trim();
    const pwd = form.password.value.trim();
    if(!email || !pwd){
      msg.textContent = 'Por favor completa todos los campos.';
      msg.className = 'msg error';
      form.email.focus();
      return;
    }
    // demo success
    msg.textContent = '¡Acceso correcto! (demo)';
    msg.className = 'msg success';
  });
</script>
</body>
</html>