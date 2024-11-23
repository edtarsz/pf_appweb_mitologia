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