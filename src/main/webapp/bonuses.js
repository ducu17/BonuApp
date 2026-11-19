/**
 * Abre el detalle del bono seleccionado mostrando su código de redención y vigencia.
 * @param {string} tituloBono - Nombre del bono en BonusCard.
 * @param {string} codigoRedencion - Código único generado para la tienda aliada.
 * @param {string} tienda - Comercio donde aplica el bono.
 */
function verDetalleBono(tituloBono, codigoRedencion, tienda) {
    const modal = document.getElementById("modalBono");
    document.getElementById("modalTitulo").textContent = tituloBono;
    document.getElementById("modalTienda").textContent = `Válido en: ${tienda}`;
    document.getElementById("modalCodigo").textContent = codigoRedencion;

    // Muestra el modal en pantalla
    modal.style.display = "flex";
}

function cerrarModalBono() {
    document.getElementById("modalBono").style.display = "none";
}