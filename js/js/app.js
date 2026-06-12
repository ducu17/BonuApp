// REGISTRO
function registrarUsuario() {
    let nombre = document.getElementById("nombre").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    if (nombre === "" || email === "" || password === "") {
        alert("Por favor complete todos los campos");
        return;
    }

    let usuario = {
        nombre: nombre,
        email: email,
        password: password,
        puntos: 0
    };

    localStorage.setItem("usuarioBonuApp", JSON.stringify(usuario));

    alert("Usuario registrado correctamente");
    window.location.href = "login.html";
}
function iniciarSesion() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let usuarioGuardado = JSON.parse(localStorage.getItem("usuarioBonuApp"));

    if (!usuarioGuardado) {
        alert("No hay usuario registrado");
        return;
    }

    if (email === usuarioGuardado.email && password === usuarioGuardado.password) {
        alert("Bienvenido a BonuApp");
        window.location.href = "dashboard.html";
    } else {
        alert("Correo o contraseña incorrectos");
    }
}
function generarBono() {
    let usuario = JSON.parse(localStorage.getItem("usuarioBonuApp"));

    if (!usuario) {
        alert("Debe iniciar sesión");
        return;
    }

    let bono = Math.floor(Math.random() * 50) + 10; // Bono entre 10 y 50 puntos
    usuario.puntos += bono;

    localStorage.setItem("usuarioBonuApp", JSON.stringify(usuario));

    document.getElementById("mensajeBono").innerHTML =
        "¡Felicidades! Ganaste " + bono + " puntos 🎉";
}
function mostrarPuntos() {
    let usuario = JSON.parse(localStorage.getItem("usuarioBonuApp"));

    if (usuario) {
        document.getElementById("puntosUsuario").innerHTML =
            "Tus puntos acumulados: " + usuario.puntos;
    }
}

window.onload = mostrarPuntos;