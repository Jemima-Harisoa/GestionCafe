// Script AJAX pour filtrer les produits vendus sans recharger la page
const filtreForm = document.getElementById('statsFiltreForm');
const tableBody = document.querySelector('table.data-table tbody');

if (filtreForm && tableBody) {
    filtreForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const params = new URLSearchParams(new FormData(filtreForm)).toString();
        fetch(filtreForm.action + '?' + params, {
            headers: {'X-Requested-With': 'XMLHttpRequest'}
        })
            .then(response => response.text())
            .then(html => {
                // Extraire le tbody du HTML retourné
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const newTbody = doc.querySelector('table.data-table tbody');
                if (newTbody) {
                    tableBody.innerHTML = newTbody.innerHTML;
                }
            });
    });
}

// Script AJAX pour mettre à jour la courbe et les moyennes sans recharger la page
const courbeForm = document.getElementById('courbeForm');
const chartCanvas = document.getElementById('courbeTotalChart');
const spanMoyenneProduit = document.getElementById('moyenneProduit')
const spanBeneficeMoyen = document.getElementById('beneficeMoyen')
let chartInstance = Chart.getChart(chartCanvas);

if (courbeForm && chartCanvas && spanMoyenneProduit && spanBeneficeMoyen) {
    courbeForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const params = new URLSearchParams(new FormData(courbeForm)).toString();
        fetch('/administratif/production/stats-json?' + params, {
            headers: {'X-Requested-With': 'XMLHttpRequest'}
        })
            .then(response => response.json())
            .then(json => {
                spanMoyenneProduit.innerHTML = json.moyenneProduit;
                spanBeneficeMoyen.innerHTML = json.beneficeMoyen;

                // Réinitialise le graphique si besoin
                if (!chartInstance) chartInstance = Chart.getChart(chartCanvas);
                if (chartInstance) {
                    chartInstance.data.labels = json.labels;
                    chartInstance.data.datasets[0].data = json.data;
                    chartInstance.update();
                }
            });
    });
}