const mainCommentForm = document.querySelector("#commentForm");
const mainCancelComment = document.querySelector("#cancel-comment");
const addCommentButton = document.querySelector(".add-comment");

let mainFormVisible = false;

addCommentButton?.addEventListener("click", () => {
    mainCommentForm.style.display = mainFormVisible ? "none" : "block";
    mainFormVisible = !mainFormVisible;
});

mainCancelComment?.addEventListener("click", () => {
    mainCommentForm.style.display = "none";
    mainFormVisible = false;
});

document.addEventListener('DOMContentLoaded', () => {
    const replyButtons = document.querySelectorAll('.reply-button');

    replyButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            e.preventDefault();

            const commentId = button.getAttribute('data-comment-id');
            if (!commentId)
                return;

            const replyForm = document.getElementById(`replyForm-${commentId}`);
            if (!replyForm)
                return;

            document.querySelectorAll('.reply-form').forEach(form => {
                if (form !== replyForm) {
                    form.style.display = 'none';
                }
            });

            const isVisible = replyForm.style.display === "block";
            replyForm.style.display = isVisible ? "none" : "block";
        });
    });

    document.querySelectorAll('.reply-form .btn-cancel').forEach(cancelBtn => {
        cancelBtn.addEventListener("click", () => {
            const commentId = cancelBtn.getAttribute('data-comment-id');
            if (!commentId)
                return;

            const replyForm = document.getElementById(`replyForm-${commentId}`);
            if (replyForm) {
                replyForm.style.display = "none";
            }
        });
    });
});

document.addEventListener("click", function (event) {
    if (event.target.closest(".btn-option")) {
        const button = event.target.closest(".btn-option");
        const comentarioId = button.dataset.comentarioId; // Obtiene el ID del comentario
        const dropdown = document.getElementById(`dropdown-comentario-${comentarioId}`);
        const isVisible = dropdown.style.display === "block";

        closeAllCommentDropdowns(); // Cierra todos los menús antes de abrir el actual
        dropdown.style.display = isVisible ? "none" : "block";

        event.stopPropagation();
    } else if (!event.target.closest(".dropdown-menu")) {
        closeAllCommentDropdowns();
    }
});

// Prevenir que los clicks dentro del menú desplegable lo cierren
document.querySelectorAll(".dropdown-menu").forEach(dropdown => {
    dropdown.addEventListener("click", function (event) {
        event.stopPropagation();
    });
});

// Prevenir que los clicks dentro del menú desplegable lo cierren
function closeAllCommentDropdowns() {
    const dropdowns = document.querySelectorAll(".dropdown-menu");
    dropdowns.forEach(menu => menu.style.display = "none");
}