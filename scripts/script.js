const page1 = document.querySelector(".register-page1");
const page2 = document.querySelector(".register-page2");
const leftRadio = document.querySelector("#leftRadio")
const rightRadio = document.querySelector("#rightRadio")
const arrow = document.querySelector(".arrow-register");
const formRegister = document.querySelector(".form-register");


rightRadio.addEventListener("click", () => {
	arrow.style.display = "none";
	page1.style.display = "none";
	page2.style.display = "block";
	page1.style.right = "100px";
	setTimeout(() => {
		page2.style.left = "0";
	}, 10)
})

leftRadio.addEventListener("click", () => {
	arrow.style.display = "block";
	page2.style.display = "none";
	page1.style.display = "block";
	rightRadio.checked = false;
	page2.style.left = "100px";
	setTimeout(() => {
		page1.style.right = "0";
	}, 10)
})

arrow.addEventListener("click", () => {
	arrow.style.display = "none";
	page1.style.display = "none";
	page2.style.display = "block";
	rightRadio.checked = true;
	page1.style.right = "100px";
	setTimeout(() => {
		page2.style.left = "0";
	}, 10)
})

