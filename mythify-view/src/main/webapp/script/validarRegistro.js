document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("form-register-validation");
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
        const apellidoMaterno = document.getElementById("apellidoMaterno").value.trim();
        const estado = document.getElementById("estado").value.trim();
        const municipio = document.getElementById("municipio").value.trim();
        const ciudad = document.getElementById("ciudad").value.trim();
        const fechaNacimiento = document.getElementById("fechaNacimiento").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const contrasena = document.getElementById("contrasena").value;
        const confirmarContrasena = document.getElementById("confirmarConstrasena").value;
        const avatar = document.getElementById("file-upload").files[0];
        const telefono = document.getElementById("telefono").value.trim();
        const genero = document.getElementById("genero").value;

        // Validar nombre
        if (nombre === "") {
            document.getElementById("nombreError").textContent = "El nombre es obligatorio.";
            esValido = false;
        } else if (nombre.length > 50) {
            document.getElementById("nombreError").textContent = "Debe tener máximo 50 caracteres.";
            esValido = false;
        }

        // Validar apellido
        if (apellidoPaterno === "") {
            document.getElementById("apellidoPaternoError").textContent = "El apellido paterno es obligatorio.";
            esValido = false;
        } else if (apellidoPaterno.length > 30) {
            document.getElementById("apellidoPaternoError").textContent = "Debe tener máximo 30 caracteres.";
            esValido = false;
        }

        if (apellidoMaterno === "") {
            document.getElementById("apellidoMaternoError").textContent = "El apellido materno es obligatorio.";
            esValido = false;
        } else if (apellidoMaterno.length > 30) {
            document.getElementById("apellidoMaternoError").textContent = "Debe tener máximo 30 caracteres.";
            esValido = false;
        }

        if (estado === "") {
            document.getElementById("estadoError").textContent = "El estado es obligatorio.";
            esValido = false;
        } else if (estado.length > 30) {
            document.getElementById("estadoError").textContent = "Debe tener máximo 30 caracteres.";
            esValido = false;
        }

        if (municipio === "") {
            document.getElementById("municipioError").textContent = "El municipio es obligatorio.";
            esValido = false;
        } else if (municipio.length > 30) {
            document.getElementById("municipioError").textContent = "Debe tener máximo 30 caracteres.";
            esValido = false;
        }

        if (ciudad === "") {
            document.getElementById("ciudadError").textContent = "La ciudad es obligatoria.";
            esValido = false;
        } else if (ciudad.length > 30) {
            document.getElementById("ciudadError").textContent = "Debe tener máximo 30 caracteres.";
            esValido = false;
        }

        if (fechaNacimiento === "") {
            document.getElementById("fechaNacimientoError").textContent = "La fecha de nacimiento es obligatoria.";
            esValido = false;
        } else {
            const fechaNacimientoDate = new Date(fechaNacimiento);
            // Validar si la fecha es válida
            if (isNaN(fechaNacimientoDate.getTime())) {
                document.getElementById("fechaNacimientoError").textContent = "Fecha de nacimiento inválida.";
                esValido = false;
            } else {
                const fechaActual = new Date();
                let edad = fechaActual.getFullYear() - fechaNacimientoDate.getFullYear();
                const mes = fechaActual.getMonth() - fechaNacimientoDate.getMonth();
                // Ajuste si no ha cumplido años este año
                if (mes < 0 || (mes === 0 && fechaActual.getDate() < fechaNacimientoDate.getDate())) {
                    edad--;
                }
                // Verificar si la edad es menor a 18 años
                if (edad < 18) {
                    document.getElementById("fechaNacimientoError").textContent = "Debes tener al menos 18 años para registrarte.";
                    esValido = false;
                }
            }
        }

        // Validar correo
        const regexEmail = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
        if (correo === "") {
            document.getElementById("correoError").textContent = "El correo es obligatorio.";
            esValido = false;
        } else if (correo.length > 100) {
            document.getElementById("correoError").textContent = "Debe tener máximo 100 caracteres.";
            esValido = false;
        } else if (!regexEmail.test(correo)) {
            document.getElementById("correoError").textContent = "El correo no es válido.";
            esValido = false;
        }

        const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/;
        // Validar contraseñas
        if (contrasena === "") {
            document.getElementById("contrasenaError").textContent = "La contraseña es obligatoria.";
            esValido = false;
        } else if (contrasena.length < 8) {
            document.getElementById("contrasenaError").textContent = "Debe tener mínimo 8 caracteres.";
            esValido = false;
        } else if (contrasena.length > 20) {
            document.getElementById("contrasenaError").textContent = "Debe tener máximo 20 caracteres.";
            esValido = false;
        } else if (!regexPassword.test(contrasena)) {
            document.getElementById("contrasenaError").textContent = "La contraseña debe contener al menos una mayúscula, una minúscula y un número.";
            esValido = false;
        }

        if (contrasena !== confirmarContrasena) {
            document.getElementById("confirmarConstrasenaError").textContent = "Las contraseñas no coinciden.";
            esValido = false;
        }

        // Validar avatar
        if (avatar && avatar.size > 5 * 1024 * 1024) {
            document.getElementById("avatarError").textContent = "El avatar debe ser menor a 5 MB.";
            esValido = false;
        }

        if (telefono === "") {
            document.getElementById("telefonoError").textContent = "El teléfono es obligatorio.";
            esValido = false;
        } else if (telefono.length < 10 || telefono.length > 10) {
            document.getElementById("telefonoError").textContent = "El teléfono debe tener 10 dígitos.";
            esValido = false;
        } else if (isNaN(telefono)) {
            document.getElementById("telefonoError").textContent = "El teléfono debe ser numérico.";
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
