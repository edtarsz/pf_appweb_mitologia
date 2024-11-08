const btnMythology = document.querySelector("#btn-mesoamerica");
const deployMesoamerica = document.querySelector(".container-list-mythologies");

btnMythology.addEventListener("click", () => {
    deployMesoamerica.classList.toggle("visible");

    const isVisible = deployMesoamerica.classList.contains("visible");
    localStorage.setItem("mesoamericaVisible", isVisible);
});


document.addEventListener("DOMContentLoaded", () => {
    const isVisible = localStorage.getItem("mesoamericaVisible") === "true";

    if (isVisible) {
        deployMesoamerica.classList.add("visible");
    }
});