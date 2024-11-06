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
const imagenCrearPost = document.querySelector("#imgGet");

const imgView = document.querySelector("#img-view");

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
};

if (mythologySelect !== null) {
    mythologySelect.addEventListener("change", () => {
        window.location.href = mythologySelect.value;
    });
}

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

if (liTexto !== null || liImg !== null || liLink !== null) {
    liTexto.addEventListener("click", () => columna(true));
    liImg.addEventListener("click", () => columna(false, true));
    liLink.addEventListener("click", () => columna(false, false, true));
}

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
};

if (tituloCrearPost !== null) {
    tituloCrearPost.addEventListener("input", () => {
        previewTitulo.innerHTML = tituloCrearPost.value;
    });
}

if (comentarioCrearPost !== null) {
    comentarioCrearPost.addEventListener("input", () => {
        previewComentario.innerHTML = comentarioCrearPost.value;
    });
}

function actualizarVistaPrevia(file) {
    if (file) {
        const imgLink = URL.createObjectURL(file);
        const img = new Image();
        img.onload = function () {
            imgView.innerHTML = '';
            const imgElement = document.createElement('img');
            imgElement.src = imgLink;
            imgElement.style.maxWidth = '100%';
            imgElement.style.height = 'auto';
            imgElement.style.display = 'block';
            imgElement.style.margin = '0 auto';
            imgView.appendChild(imgElement);
            // imgView.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
        };
        img.src = imgLink;
    }
}

// Event listener para el input file tradicional
if (imagenCrearPost !== null) {
    imagenCrearPost.addEventListener("change", (e) => {
        if (e.target.files && e.target.files[0]) {
            actualizarVistaPrevia(e.target.files[0]);
        }
    });
}

// Event listeners para drag and drop
if (groupImg !== null) {
    groupImg.addEventListener("dragover", (e) => {
        e.preventDefault();
        groupImg.style.border = "2px dashed #0088ff";
    });

    groupImg.addEventListener("dragleave", (e) => {
        e.preventDefault();
        groupImg.style.border = "2px dashed #ccc";
    });

    groupImg.addEventListener("drop", (e) => {
        e.preventDefault();
        groupImg.style.border = "2px dashed #ccc";

        const file = e.dataTransfer.files[0];
        if (file && file.type.startsWith('image/')) {
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            imagenCrearPost.files = dataTransfer.files;
            actualizarVistaPrevia(file);
        }
    });
}