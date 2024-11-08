const mythologySelect = document.getElementById('mythologySelect');
const contenedorMenuDesplegable = document.querySelector(".clicking");
const menuDesplegable = document.querySelector(".items-menu");

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