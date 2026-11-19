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
                <option value="Supermercados Éxito">Supermercados Éxito</option>
                <option value="Falabella">Falabella</option>
                <option value="D1">D1</option>
                <option value="Alkosto">Alkosto</option>
                <option value="ARA">ARA</option>
                <option value="Tiendas Olimpica">Tiendas Olimpica</option>
                <option value="Tiendas STOP">Tiendas STOP</option>
                <option value="Tiendas adidas">Tiendas adidas</option>
                <option value="Tiendas skechers">Tiendas skechers</option>
                <option value="Jumbo">Jumbo</option>
                <option value="Droguerias Cruz Verde">Droguerias Cruz Verde</option>
                <option value="Drogas La Rebaja">Drogas La Rebaja</option>
                <option value="Supermercados Mercacentro">Supermercados Mercacentro</option>
                <option value="Homecenter">Homecenter</option>
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
    <button type="button" class="btn" onclick="abrirModalBono('BN-01')" style="background-color: #16a34a; color: #ffffff; padding: 10px 18px; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; margin-top: 8px;">Ver bono</button>
</div>
<section id="gridBonos" class="grid-cards"></section>

<div id="modalBono" class="modal-overlay">
    <div class="modal-content">
        <h2 id="modalBonoTitulo"></h2>
        <p id="modalBonoTienda"></p>
        <div class="codigo-box" id="modalBonoCodigo"></div>
        <p id="modalBonoVigencia"></p>
        <p id="modalBonoCondiciones"></p>
        <p id="modalEstadoCanje"></p>
        <button type="button" class="btn-primary" onclick="cerrarModalBono()">Cerrar</button>
    </div>
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
                const selTienda = document.getElementById('selectTienda');
                const selValor = document.getElementById('selectValor');
                ultimoBonoGenerado = {
                    codigo: resultado.codigo || 'BN-01',
                    tienda: selTienda && selTienda.selectedIndex >= 0 ? selTienda.options[selTienda.selectedIndex].text.trim() : 'Tienda aliada',
                    valorTexto: selValor && selValor.selectedIndex >= 0 ? selValor.options[selValor.selectedIndex].text.trim() : '$20.000'
                };
                document.getElementById('formBono').reset();
            } else {
                alert('Error al generar el bono: ' + (resultado.mensaje || 'Intenta de nuevo'));
            }
        } catch (error) {
            console.error('Error de red:', error);
            alert('No se pudo conectar con el servidor.');
        }
    });
    let ultimoBonoGenerado = {
        codigo: "BN-01",
        tienda: "D1",
        valorTexto: "$20.000"
    };

    function obtenerLogoTienda(nombreTienda) {
        const limpio = (nombreTienda || "").toLowerCase().trim();
        if (limpio.includes("d1")) return "img/d1.png";
        if (limpio.includes("alkosto")) return "img/alkosto.png";
        if (limpio.includes("exito") || limpio.includes("éxito")) return "img/exito.png";
        if (limpio.includes("olimpica") || limpio.includes("olímpica")) return "img/olimpica.png";
        return "img/logoBonuApp.webp";
    }

    function abrirModalBono(codigoOpcional) {
        const modal = document.getElementById("modalBono");
        if (!modal) return;

        const selTienda = document.getElementById("selectTienda");
        const selValor = document.getElementById("selectValor");

        const tiendaActual = (selTienda && selTienda.value)
            ? selTienda.options[selTienda.selectedIndex].text.trim()
            : ultimoBonoGenerado.tienda;

        const valorActual = (selValor && selValor.value)
            ? selValor.options[selValor.selectedIndex].text.trim()
            : ultimoBonoGenerado.valorTexto;

        const codigoFinal = codigoOpcional || ultimoBonoGenerado.codigo || "BN-01";
        const rutaLogo = obtenerLogoTienda(tiendaActual);

        // Centrar y estilizar el modal y su tarjeta interna
        modal.style.cssText = [
            "display:flex",
            "position:fixed",
            "inset:0",
            "background:rgba(15,23,42,0.65)",
            "align-items:center",
            "justify-content:center",
            "z-index:9999",
            "padding:16px"
        ].join(";");

        const contenido = modal.querySelector(".modal-content");
        if (contenido) {
            contenido.style.cssText = [
                "background:#ffffff",
                "color:#1e293b",
                "width:100%",
                "max-width:400px",
                "border-radius:16px",
                "padding:24px",
                "box-shadow:0 20px 40px rgba(0,0,0,0.25)",
                "text-align:center",
                "font-family:inherit"
            ].join(";");
        }

        const titulo = document.getElementById("modalBonoTitulo");
        const tienda = document.getElementById("modalBonoTienda");
        const codigo = document.getElementById("modalBonoCodigo");
        const vigencia = document.getElementById("modalBonoVigencia");
        const condiciones = document.getElementById("modalBonoCondiciones");
        const estado = document.getElementById("modalEstadoCanje");

        if (titulo) {
            titulo.textContent = "Detalle de tu Bono";
            titulo.style.cssText = "margin:0 0 14px 0;font-size:1.35rem;color:#0f172a;";
        }

        if (tienda) {
            tienda.style.cssText = "margin:0 0 14px 0;display:flex;flex-direction:column;align-items:center;gap:8px;";
            tienda.innerHTML =
                '<img src="' + rutaLogo + '" alt="' + tiendaActual + '" ' +
                'onerror="this.src=\'img/logoBonuApp.webp\'" ' +
                'style="width:64px;height:64px;object-fit:contain;border-radius:12px;background:#f8fafc;padding:6px;border:1px solid #e2e8f0;">' +
                '<div style="font-size:1.05rem;font-weight:700;color:#0f172a;">' + tiendaActual + '</div>' +
                '<div style="font-size:1.25rem;font-weight:800;color:#15803d;background:#dcfce7;padding:6px 14px;border-radius:999px;">Monto: ' + valorActual + '</div>';
        }

        if (codigo) {
            codigo.textContent = "Código: " + codigoFinal;
            codigo.style.cssText = "margin:10px auto 14px auto;padding:10px 14px;background:#f1f5f9;border:1px dashed #64748b;border-radius:10px;font-weight:700;letter-spacing:1px;color:#0f172a;";
        }

        if (vigencia) {
            vigencia.textContent = "Vigencia: 30 días calendario";
            vigencia.style.cssText = "margin:4px 0;font-size:0.9rem;color:#475569;";
        }

        if (condiciones) {
            condiciones.textContent = "Válido para redimir en compras participantes de BonuApp.";
            condiciones.style.cssText = "margin:4px 0;font-size:0.88rem;color:#475569;";
        }

        if (estado) {
            estado.textContent = "Estado: Disponible para canje";
            estado.style.cssText = "margin:8px 0 16px 0;font-size:0.9rem;font-weight:600;color:#d97706;";
        }
    }

    function cerrarModalBono() {
        const modal = document.getElementById("modalBono");
        if (!modal) return;
        modal.style.display = "none";
        modal.classList.remove("active");
    }
</script>
<script src="js/bonuses.js"></script>

</body>
</html>