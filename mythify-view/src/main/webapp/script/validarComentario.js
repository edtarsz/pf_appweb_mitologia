document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("form-comment");
    formulario.addEventListener("submit", async (event) => {
        event.preventDefault(); // Evita el envío estándar del formulario

        let esValido = true; // Bandera para saber si todos los campos son válidos

        // Limpiar errores previos
        document.querySelectorAll(".error-formulario").forEach((error) => {
            error.textContent = "";
        });
        document.getElementById("content").textContent = "";

        // Obtener referencias de campos
        const comentario = document.getElementById("content").value.trim();

        if (comentario === "") {
            document.getElementById("comentarioError").textContent = "El comentario es obligatorio.";
            esValido = false;
        } else if (comentario.length > 1000) {
            document.getElementById("comentarioError").textContent = "El comentario no puede exceder los 1000 caracteres.";
            esValido = false;
        }

        if (esValido) {
            formulario.submit();
        }

    });
});