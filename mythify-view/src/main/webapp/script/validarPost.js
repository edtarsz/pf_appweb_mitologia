document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("form-post");
    formulario.addEventListener("submit", async (event) => {
        event.preventDefault(); // Evita el envío estándar del formulario
        console.log("Hola");
        let esValido = true; // Bandera para saber si todos los campos son válidos

        document.getElementById("tituloError").textContent = "";
        document.getElementById("comentError").textContent = "";
        document.getElementById("categoryError").textContent = "";


        const titulo = document.getElementById("titleGet").value.trim();
        const comentario = document.getElementById("comentGet").value.trim();
        const category = document.querySelector("select[name='category']").value;

        if (titulo === "") {
            document.getElementById("tituloError").textContent = "El titulo es obligatorio.";
            esValido = false;
        } else if (titulo.length > 50) {
            document.getElementById("tituloError").textContent = "Debe tener máximo 50 caracteres.";
            esValido = false;
        }

        if (comentario === "") {
            document.getElementById("comentError").textContent = "El comentario es obligatorio.";
            esValido = false;
        } else if (comentario.length > 5000) {
            document.getElementById("comentError").textContent = "Debe tener máximo 500 caracteres.";
            esValido = false;
        }

        if (category === "") {
            document.getElementById("categoryError").textContent = "La categoria es obligatoria.";
            esValido = false;
        }

        if (esValido){
            formulario.submit();
        }
    });
});
