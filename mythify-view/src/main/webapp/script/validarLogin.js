document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("form-iniciar-sesion");
    formulario.addEventListener("submit", async (event) => {
        event.preventDefault(); // Evita el envío estándar del formulario

        let esValido = true; // Bandera para saber si todos los campos son válidos

        // Limpiar errores previos
        document.querySelectorAll(".error-formulario").forEach((error) => {
            error.textContent = "";
        });

        // Obtener referencias de campos
        const correo = document.getElementById("correo").value.trim();
        const contrasena = document.getElementById("contrasena").value;


        if (correo === "") {
            document.getElementById("correoError").textContent = "El correo es obligatorio.";
            esValido = false;
        }

        if (contrasena === "") {
            document.getElementById("contrasenaError").textContent = "La contraseña es obligatoria.";
            esValido = false;
        }

        if (esValido) {
            try {
                const response = await fetch("SVUsuario", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "action=iniciarSesion&correo=" + encodeURIComponent(correo) + "&contrasenia=" + encodeURIComponent(contrasena)
                });
                const data = await response.json();

                // Si el usuario NO existe, mostrar mensaje de error sin detalles adicionales
                if (!data.existe) {
                    document.getElementById("contrasenaError").textContent = "Usuario no encontrado.";
                    esValido = false;
                }

                // Si todo es válido, enviar el formulario
                if (esValido) {
                    formulario.submit(); // Enviar el formulario
                }
            } catch (error) {
                console.error("Error en la solicitud AJAX", error);
            }
        }


    });
});
