document.addEventListener("click", function (event) {
    // Verifica si se hizo clic en un botón de opciones
    if (event.target.closest(".btn-option")) {
        const button = event.target.closest(".btn-option");
        const postId = button.dataset.postId; // Obtén el id del post asociado
        const dropdown = document.getElementById(`dropdown-${postId}`); // Selecciona el menú correspondiente

        // Alterna la visibilidad del menú relacionado
        const isVisible = dropdown.style.display === "block";
        closeAllDropdowns(); // Cierra todos los menús antes de abrir el actual
        dropdown.style.display = isVisible ? "none" : "block";
    } else if (event.target.closest(".btn-delete")) {
        const postId = event.target.dataset.postId;
        document.getElementById(`confirm-delete-${postId}`).style.display = "flex";
    } else if (event.target.closest(".btn-confirm-delete")) {
        const postId = event.target.dataset.postId;
        document.getElementById(`confirm-delete-form-${postId}`).submit();
    } else if (event.target.closest(".btn-cancel-delete")) {
        const postId = event.target.dataset.postId;
        document.getElementById(`confirm-delete-${postId}`).style.display = "none";
    } else {
        // Cierra todos los menús si se hace clic fuera
        closeAllDropdowns();
    }
});

function closeAllDropdowns() {
    const dropdowns = document.querySelectorAll(".dropdown-menu");
    dropdowns.forEach(menu => menu.style.display = "none");
}
