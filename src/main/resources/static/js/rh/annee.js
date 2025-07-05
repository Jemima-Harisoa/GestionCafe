function getCurrentYear() {
    return new Date().getFullYear();
}

// Mettre à jour le contenu du <h1 id="annee">
document.addEventListener("DOMContentLoaded", function () {
    const h1 = document.getElementById("annee");
    if (h1) {
        h1.textContent = "Année : " + getCurrentYear();
    }
});
