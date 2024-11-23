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
console.log("register.js waching");
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
;