const page1 = document.querySelector(".register-page1");
const page2 = document.querySelector(".register-page2");
const leftRadio = document.querySelector("#leftRadio");
const rightRadio = document.querySelector("#rightRadio");
const arrowRight = document.querySelector(".arrow-register-right");
const arrowLeft = document.querySelector(".arrow-register-left");
const formRegister = document.querySelector(".form-register");
const containerBtn = document.querySelector(".transition-btn");
const avatar = document.querySelector("#file-upload");
const img = document.querySelector("#imgAvatar");
const titleRegister = document.querySelector(".title-register");
const mythologySelect = document.getElementById('mythologySelect');
const contenedorMenuDesplegable = document.querySelector(".clicking");
const menuDesplegable = document.querySelector(".items-menu");

const groupImg = document.querySelector("#drop-area");
const groupTexto = document.querySelector(".input-area-post");
const groupLink = document.querySelector("#link-url");

const liTexto = document.querySelector("#li-texto");
const liImg = document.querySelector("#li-img");
const liLink = document.querySelector("#li-link");

const previewTitulo = document.querySelector("#preview-titulo");
const previewComentario = document.querySelector("#preview-texto");

const tituloCrearPost = document.querySelector("#titleGet");
const comentarioCrearPost = document.querySelector("#comentGet");

if (rightRadio !== null) {
    rightRadio.addEventListener("click", () => cambiarPantalla(false));
    leftRadio.addEventListener("click", () => cambiarPantalla(true));
    arrowRight.addEventListener("click", () => cambiarPantalla(false));
    arrowLeft.addEventListener("click", () => cambiarPantalla(true));
}

if (avatar !== null) {
    avatar.addEventListener("change", (e) => {
        if (e.target.files[0]) {
            const reader = new FileReader();
            reader.onload = (e) => {
                img.src = e.target.result;
            };
            reader.readAsDataURL(e.target.files[0]);
        } else {
            img.src = defaultFile;
        }
    });
}

function cambiarPantalla(isPageOneVisible) {
    if (isPageOneVisible) {
        arrowLeft.style.opacity = "0";
        arrowRight.style.opacity = "1";
        page2.style.display = "none";
        page1.style.display = "block";
        leftRadio.checked = true;
        page2.style.left = "100px";
        page2.style.opacity = "0";
        containerBtn.style.display = "none";
        containerBtn.style.left = "100px";
        titleRegister.style.display = "block";
        setTimeout(() => {
            titleRegister.style.right = "0";
            page1.style.right = "0";
            page1.style.opacity = "1";
        }, 10);
    } else {
        arrowLeft.style.opacity = "1";
        arrowRight.style.opacity = "0";
        page1.style.display = "none";
        page2.style.display = "block";
        rightRadio.checked = true;
        page1.style.right = "100px";
        page1.style.opacity = "0";
        containerBtn.style.display = "block";
        containerBtn.style.right = "100px";
        titleRegister.style.display = "none";
        titleRegister.style.right = "100px";
        setTimeout(() => {
            containerBtn.style.left = "0";
            page2.style.left = "0";
            page2.style.opacity = "1";
        }, 10);
    }
}

mythologySelect.addEventListener("change", () => {
    window.location.href = mythologySelect.value;
});

contenedorMenuDesplegable.addEventListener("mouseenter", () => {
    menuDesplegable.style.visibility = "visible";
    setTimeout(() => {
        menuDesplegable.style.opacity = "1";
    }, 10);
});

contenedorMenuDesplegable.addEventListener("mouseleave", () => {
    menuDesplegable.style.opacity = "0";
    setTimeout(() => {
        menuDesplegable.style.visibility = "hidden";
    }, 10);
});

liTexto.addEventListener("click", () => columna(true));
liImg.addEventListener("click", () => columna(false, true));
liLink.addEventListener("click", () => columna(false, false, true));

tituloCrearPost.addEventListener("input", () => {
    previewTitulo.innerHTML = tituloCrearPost.value;
})

comentarioCrearPost.addEventListener("input", () => {
    previewComentario.innerHTML = comentarioCrearPost.value;
})

function columna(texto = false, img = false, link = false) {
    if (texto) {
        groupTexto.style.display = "block";
        liTexto.style.borderWidth = "0 0 2px 0";
    } else {
        groupTexto.style.display = "none";
        liTexto.style.borderWidth = "0";
    }

    if (img) {
        groupImg.style.display = "flex";
        liImg.style.borderWidth = "0 0 2px 0";
    } else {
        groupImg.style.display = "none";
        liImg.style.borderWidth = "0";
    }

    if (link) {
        groupLink.style.display = "block";
        liLink.style.borderWidth = "0 0 2px 0";
    } else {
        groupLink.style.display = "none";
        liLink.style.borderWidth = "0";
    }
}