<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BonuApp - Generar Bono</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<div class="container">
    <img src="${pageContext.request.contextPath}/img/logoBonuApp.webp" class="logo" alt="Logo BonuApp">

    <h2>Generar Bono</h2>

    <!-- Formulario con ID para el script -->
    <form id="formBono" action="BonoServlet" method="POST">
        <div class="campo" style="text-align: left; margin-bottom: 15px;">
            <label for="selectTienda">Selecciona la tienda:</label>
            <select id="selectTienda" name="tienda" required style="width: 100%; padding: 10px; margin-top: 5px; border-radius: 6px; border: 1px solid #ccc;">
                <option value="">-- Seleccionar --</option>
                <option value="Supermercado Éxito">Supermercado Éxito</option>
                <option value="Falabella">Falabella</option>
                <option value="D1">D1</option>
                <option value="Alkosto">Alkosto</option>
            </select>
        </div>

        <div class="campo" style="text-align: left; margin-bottom: 20px;">
            <label for="selectValor">Valor del Bono:</label>
            <select id="selectValor" name="valor" required style="width: 100%; padding: 10px; margin-top: 5px; border-radius: 6px; border: 1px solid #ccc;">
                <option value="">-- Seleccionar --</option>
                <option value="10000">$10.000</option>
                <option value="20000">$20.000</option>
                <option value="50000">$50.000</option>
                <option value="100000">$100.000</option>
            </select>
        </div>

        <button type="submit" class="btn">Generar Bono</button>
    </form>

    <p style="margin-top: 20px;">
        <a href="index.jsp" style="color: #007bff; text-decoration: none;">Volver al Inicio</a>
    </p>
</div>

<!-- Script de comunicación asíncrona con el Servlet -->
<script>
    document.getElementById('formBono').addEventListener('submit', async function(e) {
        e.preventDefault();

        const datosBono = {
            tienda: document.getElementById('selectTienda').value,
            valor: parseInt(document.getElementById('selectValor').value)
        };

        try {
            const response = await fetch('${pageContext.request.contextPath}/api/generar-bono', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datosBono)
            });

            const resultado = await response.json();

            if (response.ok && resultado.status === 'exito') {
                alert('¡Bono generado correctamente!');
                document.getElementById('formBono').reset();
            } else {
                alert('Error al generar el bono: ' + (resultado.mensaje || 'Intenta de nuevo'));
            }
        } catch (error) {
            console.error('Error de red:', error);
            alert('No se pudo conectar con el servidor.');
        }
    });
</script>

</body>
</html>