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