document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("form-register-validation");
    console.log("validarRegistro.js loaded");
    formulario.addEventListener("submit", (event) => {
        event.preventDefault(); // Evita el envío estándar del formulario

        let esValido = true; // Bandera para saber si todos los campos son válidos

        // Limpiar errores previos
        document.querySelectorAll(".error-formulario").forEach((error) => {
            error.textContent = "";
        });

        // Obtener referencias de campos
        const nombre = document.getElementById("nombre").value.trim();
        const apellidoPaterno = document.getElementById("apellidoPaterno").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const contrasena = document.getElementById("contrasena").value;
        const confirmarContrasena = document.getElementById("confirmarConstrasena").value;
        const avatar = document.getElementById("file-upload").files[0];
        const genero = document.getElementById("genero").value;

        // Validar nombre
        if (nombre === "") {
            document.getElementById("nombreError").textContent = "El nombre es obligatorio.";
            esValido = false;
        } else if (nombre.length > 50) {
            document.getElementById("nombreError").textContent = "Debe tener al menos 3 caracteres.";
            esValido = false;
        }

        // Validar apellido
        if (apellidoPaterno === "") {
            document.getElementById("apellidoPaternoError").textContent = "El primer apellido es obligatorio.";
            esValido = false;
        }

        // Validar correo
        const regexEmail = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
        if (correo === "") {
            document.getElementById("correoError").textContent = "El correo es obligatorio.";
            esValido = false;
        } else if (!regexEmail.test(correo)) {
            document.getElementById("correoError").textContent = "El correo no es válido.";
            esValido = false;
        }

        // Validar contraseñas
        if (contrasena === "") {
            document.getElementById("contrasenaError").textContent = "La contraseña es obligatoria.";
            esValido = false;
        } else if (contrasena !== confirmarContrasena) {
            document.getElementById("confirmarConstrasenaError").textContent = "Las contraseñas no coinciden.";
            esValido = false;
        }

        // Validar avatar
        if (avatar && avatar.size > 2 * 1024 * 1024) {
            alert("El avatar debe ser menor a 2 MB.");
            esValido = false;
        }

        // Validar género
        if (genero === "seleccionar") {
            document.getElementById("generoError").textContent = "Debes seleccionar un género.";
            esValido = false;
        }

        // Si todo es válido, enviar el formulario
        if (esValido) {
            formulario.submit(); // Enviar el formulario
        }
    });

    // Previsualización del avatar
    document.getElementById("file-upload").addEventListener("change", (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                document.getElementById("imgAvatar").src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});
