// dashboard.js
document.addEventListener('DOMContentLoaded', function() {
   

    // Initialisation des graphiques
    initializeCharts();
    animateCounters();
    setupInteractions();

    function initializeCharts() {
        // Graphique des lieux
        createProgressChart('lieuxChart', sampleData.lieuxCounts, 'lieu');
        
        // Graphique des produits
        createProgressChart('produitsChart', sampleData.produitsCounts, 'produit');
        
        // Graphique de genre (pie chart)
        updateGenderChart(sampleData.genderData.hommes, sampleData.genderData.femmes);
    }

    function createProgressChart(containerId, data, type) {
        const container = document.getElementById(containerId);
        if (!container) return;

        // Trouver la valeur maximale pour normaliser
        const maxValue = Math.max(...Object.values(data));
        
        // Vider le conteneur
        container.innerHTML = '';

        // Créer les barres de progression
        Object.entries(data).forEach(([label, value], index) => {
            const percentage = (value / maxValue) * 100;
            
            const progressItem = document.createElement('div');
            progressItem.className = 'progress-item';
            
            progressItem.innerHTML = `
                <span class="progress-label">${label}</span>
                <div class="progress-bar">
                    <div class="progress-fill ${type}" style="width: 0%;" data-width="${percentage}%"></div>
                </div>
                <span class="progress-value">${value}</span>
            `;
            
            container.appendChild(progressItem);
            
            // Animation de la barre avec délai
            setTimeout(() => {
                const fill = progressItem.querySelector('.progress-fill');
                fill.style.width = fill.dataset.width;
            }, 200 + (index * 100));
        });
    }

    function updateGenderChart(hommes, femmes) {
        const pieChart = document.getElementById('genderPie');
        if (!pieChart) return;

        const total = hommes + femmes;
        const hommesPercent = (hommes / total) * 100;
        const femmesPercent = (femmes / total) * 100;

        // Mise à jour du graphique circulaire CSS
        const hommesAngle = (hommesPercent / 100) * 360;
        pieChart.style.background = `conic-gradient(
            #667eea 0deg ${hommesAngle}deg,
            #f093fb ${hommesAngle}deg 360deg
        )`;

        // Mise à jour des pourcentages dans la légende
        const menPercentElement = document.getElementById('menPercent');
        const womenPercentElement = document.getElementById('womenPercent');
        
        if (menPercentElement) menPercentElement.textContent = Math.round(hommesPercent);
        if (womenPercentElement) womenPercentElement.textContent = Math.round(femmesPercent);
    }

    function animateCounters() {
        const counters = document.querySelectorAll('.stat-value');
        
        counters.forEach(counter => {
            const target = parseInt(counter.textContent) || parseFloat(counter.textContent) || 0;
            const isFloat = counter.textContent.includes('.');
            let current = 0;
            const increment = target / 50;
            
            const updateCounter = () => {
                if (current < target) {
                    current += increment;
                    if (isFloat) {
                        counter.textContent = current.toFixed(2);
                    } else {
                        counter.textContent = Math.floor(current);
                    }
                    requestAnimationFrame(updateCounter);
                } else {
                    if (isFloat) {
                        counter.textContent = target.toFixed(2);
                    } else {
                        counter.textContent = target;
                    }
                }
            };
            
            // Démarrer l'animation après un délai
            setTimeout(updateCounter, 500);
        });
    }

    function setupInteractions() {
        // Toggle mobile menu
        const mobileToggle = document.getElementById('mobileToggle');
        if (mobileToggle) {
            mobileToggle.addEventListener('click', function() {
                // Logique pour le menu mobile si nécessaire
                console.log('Menu mobile cliqué');
            });
        }

        // Export functionality
        const exportBtn = document.querySelector('.btn-export');
        if (exportBtn) {
            exportBtn.addEventListener('click', function() {
                exportTableData();
            });
        }

        // Hover effects pour les cartes
        const cards = document.querySelectorAll('.stat-card, .chart-card');
        cards.forEach(card => {
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-8px) scale(1.02)';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });

        // Animation des barres de progression au scroll
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const progressBars = entry.target.querySelectorAll('.progress-fill');
                    progressBars.forEach((bar, index) => {
                        setTimeout(() => {
                            bar.style.width = bar.dataset.width || '0%';
                        }, index * 100);
                    });
                }
            });
        }, observerOptions);

        // Observer les graphiques
        const chartCards = document.querySelectorAll('.chart-card');
        chartCards.forEach(card => observer.observe(card));
    }

    function exportTableData() {
        const table = document.querySelector('.data-table');
        if (!table) return;

        // Créer le contenu CSV
        let csvContent = '';
        const rows = table.querySelectorAll('tr');
        
        rows.forEach(row => {
            const cells = row.querySelectorAll('th, td');
            const rowData = Array.from(cells).map(cell => {
                return '"' + cell.textContent.trim().replace(/"/g, '""') + '"';
            });
            csvContent += rowData.join(',') + '\n';
        });

        // Créer et télécharger le fichier
        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', 'dashboard_data.csv');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    // Fonction pour mettre à jour les données en temps réel
    function updateDashboardData(newData) {
        // Mettre à jour les statistiques
        if (newData.averageAge) {
            const ageElement = document.querySelector('.stat-card .stat-value');
            if (ageElement) {
                animateValue(ageElement, parseInt(ageElement.textContent), newData.averageAge);
            }
        }

        // Mettre à jour les graphiques
        if (newData.lieuxCounts) {
            createProgressChart('lieuxChart', newData.lieuxCounts, 'lieu');
        }

        if (newData.produitsCounts) {
            createProgressChart('produitsChart', newData.produitsCounts, 'produit');
        }

        if (newData.genderData) {
            updateGenderChart(newData.genderData.hommes, newData.genderData.femmes);
        }
    }

    function animateValue(element, start, end) {
        const duration = 1000;
        const startTime = performance.now();
        
        function update(currentTime) {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);
            
            const currentValue = start + (end - start) * progress;
            element.textContent = Math.round(currentValue);
            
            if (progress < 1) {
                requestAnimationFrame(update);
            }
        }
        
        requestAnimationFrame(update);
    }

    // Fonction pour simuler des données en temps réel (pour les tests)
    function simulateRealtimeData() {
        setInterval(() => {
            const randomData = {
                lieuxCounts: {
                    'Paris': Math.floor(Math.random() * 50) + 30,
                    'Lyon': Math.floor(Math.random() * 40) + 25,
                    'Marseille': Math.floor(Math.random() * 35) + 20,
                    'Toulouse': Math.floor(Math.random() * 30) + 15,
                    'Nice': Math.floor(Math.random() * 25) + 10
                }
            };
            // updateDashboardData(randomData);
        }, 10000); // Mise à jour toutes les 10 secondes
    }

    // Gestion du redimensionnement de la fenêtre
    window.addEventListener('resize', function() {
        // Recalculer les graphiques si nécessaire
        setTimeout(() => {
            initializeCharts();
        }, 300);
    });

    // Démarrer la simulation des données (décommentez pour tester)
    // simulateRealtimeData();

    // Fonction utilitaire pour formater les nombres
    function formatNumber(num) {
        if (num >= 1000000) {
            return (num / 1000000).toFixed(1) + 'M';
        } else if (num >= 1000) {
            return (num / 1000).toFixed(1) + 'K';
        }
        return num.toString();
    }

    // Fonction pour créer des tooltips
    function createTooltip(element, text) {
        element.addEventListener('mouseenter', function(e) {
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = text;
            tooltip.style.cssText = `
                position: absolute;
                background: rgba(0, 0, 0, 0.8);
                color: white;
                padding: 8px 12px;
                border-radius: 6px;
                font-size: 12px;
                pointer-events: none;
                z-index: 1000;
                opacity: 0;
                transition: opacity 0.3s ease;
            `;
            
            document.body.appendChild(tooltip);
            
            const updatePosition = (e) => {
                tooltip.style.left = e.pageX + 10 + 'px';
                tooltip.style.top = e.pageY - 10 + 'px';
            };
            
            updatePosition(e);
            element.addEventListener('mousemove', updatePosition);
            
            setTimeout(() => tooltip.style.opacity = '1', 10);
        });
        
        element.addEventListener('mouseleave', function() {
            const tooltip = document.querySelector('.tooltip');
            if (tooltip) {
                tooltip.remove();
            }
        });
    }

    // Ajouter des tooltips aux éléments interactifs
    const progressBars = document.querySelectorAll('.progress-bar');
    progressBars.forEach(bar => {
        const label = bar.closest('.progress-item')?.querySelector('.progress-label')?.textContent;
        const value = bar.closest('.progress-item')?.querySelector('.progress-value')?.textContent;
        if (label && value) {
            createTooltip(bar, `${label}: ${value} occurrences`);
        }
    });

    // Console log pour le debugging
    console.log('Dashboard initialisé avec succès!');
});