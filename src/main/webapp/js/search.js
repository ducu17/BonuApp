/**
 * Módulo: Buscador de Productos y Acumulación de Puntos - BonuApp
 * Descripción: Filtra el catálogo de productos de tiendas aliadas en tiempo real
 * y acredita puntos virtuales al usuario por cada consulta válida.
 */

// Catálogo simulado conectado a los comercios aliados de BonuApp
const catalogoProductos = [
    { id: 1, nombre: "Tenis Deportivos Runner", tienda: "SportCenter", puntos: 50, precio: "$180.000" },
    { id: 2, nombre: "Audífonos Bluetooth Pro", tienda: "TecnoStore", puntos: 80, precio: "$120.000" },
    { id: 3, nombre: "Cafetera Express Hogar", tienda: "HogarPlus", puntos: 60, precio: "$210.000" },
    { id: 4, nombre: "Mochila Ejecutiva Impermeable", tienda: "ModaUrbana", puntos: 40, precio: "$95.000" }
];

let puntosUsuario = 120; // Saldo inicial vinculado a PointsCard

/**
 * Ejecuta la consulta de productos, renderiza las tarjetas y actualiza los puntos.
 * @param {Event} event - Evento de envío del formulario de búsqueda.
 */
function buscarProductos(event) {
    event.preventDefault();

    const inputBusqueda = document.getElementById("inputSearch").value.trim().toLowerCase();
    const contenedorResultados = document.getElementById("resultadosProductos");
    const contadorPuntos = document.getElementById("puntosTotales");

    if (inputBusqueda === "") {
        contenedorResultados.innerHTML = "<p class='alerta'>Por favor, ingresa el nombre de un producto o tienda.</p>";
        return;
    }

    // 1. Consulta efectiva: filtra productos por nombre o tienda aliada
    const filtrados = catalogoProductos.filter(item =>
        item.nombre.toLowerCase().includes(inputBusqueda) ||
        item.tienda.toLowerCase().includes(inputBusqueda)
    );

    // 2. Renderizado dinámico de resultados en la vista
    if (filtrados.length > 0) {
        contenedorResultados.innerHTML = filtrados.map(prod => `
      <div class="producto-card">
        <h4>${prod.nombre}</h4>
        <p>Tienda aliada: <strong>${prod.tienda}</strong></p>
        <p>Precio: ${prod.precio} | Recompensa: +${prod.puntos} pts</p>
      </div>
    `).join("");

        // 3. Incremento de puntos por búsqueda efectiva
        puntosUsuario += 10;
        contadorPuntos.textContent = puntosUsuario;
    } else {
        contenedorResultados.innerHTML = `<p class="sin-resultados">No se encontraron productos para "${inputBusqueda}".</p>`;
    }
}