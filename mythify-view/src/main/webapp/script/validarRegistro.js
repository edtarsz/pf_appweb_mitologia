document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM loaded');
    const form = document.getElementById('form-register-validation');
    const submitButton = document.getElementById('btn-registrar');

    // Definir límites de caracteres
    const maxLengths = {
        nombre: 50,
        apellidoPaterno: 30,
        apellidoMaterno: 30,
        estado: 30,
        ciudad: 50,
        municipio: 30,
        correo: 100,
        contrasena: 20,
        confirmarConstrasena: 20,
        telefono: 10,
    };

    // Función para mostrar mensajes de error
    function showError(input, message) {
        const errorDiv = document.getElementById(input.id + 'Error');
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    }

    // Función para limpiar los mensajes de error
    function clearError(input) {
        const errorDiv = document.getElementById(input.id + 'Error');
        errorDiv.textContent = '';
        errorDiv.style.display = 'none';
    }

    // Función de validación del formulario
    function validateForm(event) {
        let valid = true;

        // Validar cada campo del formulario
        const inputs = form.querySelectorAll('.register-input, select');

        inputs.forEach(input => {
            clearError(input);  // Limpiar mensajes de error previos

            if (input.value.trim() === '') {
                showError(input, 'Este campo es obligatorio');
                valid = false;
            } else if (input.value.length > maxLengths[input.name]) {
                showError(input, `El campo no debe exceder los ${maxLengths[input.name]} caracteres`);
                valid = false;
            }
        });

        // Validar que las contraseñas coincidan
        const contrasena = form.querySelector('#contrasena').value;
        const confirmarContrasena = form.querySelector('#confirmarConstrasena').value;
        if (contrasena !== confirmarContrasena) {
            showError(form.querySelector('#confirmarConstrasena'), 'Las contraseñas no coinciden');
            valid = false;
        }

        // Si todo es válido, el formulario puede enviarse
        if (!valid) {
            event.preventDefault();  // Prevenir el envío del formulario
        }
    }

    // Evento de validación al hacer clic en "Registrar"
    submitButton.addEventListener('click', validateForm);
});
