 function toggleDropdown() {
        var dropdown = document.getElementById("dropdownMenu");
        dropdown.style.display = (dropdown.style.display === "none" || dropdown.style.display === "") ? "block" : "none";
    }
    
    document.addEventListener('click', function(event) {
        var dropdown = document.getElementById("dropdownMenu");
        var button = document.querySelector(".btn-option");
        
        if (!dropdown.contains(event.target) && !button.contains(event.target)) {
            dropdown.style.display = "none";
        }
    });