// Application JavaScript pour CafeManager Pro

class CafeManager {
    constructor() {
        this.currentPage = 'dashboard';
        this.sidebarCollapsed = false;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.initializeCharts();
        this.loadDashboardData();
        this.setupNotifications();
    }

    setupEventListeners() {
        // Navigation
        /*
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const page = link.dataset.page;
                this.navigateTo(page);
            });
        });
        */

        // Sidebar toggle

        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebarShowBtn = document.getElementById('sidebarShowBtn');
        const mobileToggle = document.getElementById('mobileToggle');

        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', () => {
                this.toggleSidebar();
            });
        }

        if (sidebarShowBtn) {
            sidebarShowBtn.addEventListener('click', () => {
                this.toggleSidebar(true);
            });
        }

        if (mobileToggle) {
            mobileToggle.addEventListener('click', () => {
                this.toggleMobileSidebar();
            });
        }

        // Search functionality
        const searchInput = document.querySelector('.search-box input');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.handleSearch(e.target.value);
            });
        }

        // Notification click
        const notifications = document.querySelector('.notifications');
        if (notifications) {
            notifications.addEventListener('click', () => {
                this.showNotifications();
            });
        }

        // Form submissions
        this.setupFormHandlers();

        // Button interactions
        this.setupButtonHandlers();

        // Window resize
        window.addEventListener('resize', () => {
            this.handleResize();
        });
    }

    navigateTo(page) {
        // Update active navigation
        document.querySelectorAll('.nav-item').forEach(item => {
            item.classList.remove('active');
        });
        
        const activeNavItem = document.querySelector(`[data-page="${page}"]`).closest('.nav-item');
        if (activeNavItem) {
            activeNavItem.classList.add('active');
        }

        // Show/hide pages
        document.querySelectorAll('.page').forEach(pageEl => {
            pageEl.classList.remove('active');
        });

        const targetPage = document.getElementById(`${page}-page`);
        if (targetPage) {
            targetPage.classList.add('active');
            targetPage.classList.add('fade-in');
        }

        // Update page title
        const pageTitles = {
            dashboard: 'Tableau de Bord',
            stock: 'Gestion des Stocks',
            production: 'Gestion de Production',
            finances: 'Gestion Financière',
            elements: 'Éléments UI',
            orders: 'Gestion des Commandes',
            customers: 'Gestion des Clients',
            reports: 'Rapports'
        };

        const pageTitle = document.getElementById('pageTitle');
        if (pageTitle && pageTitles[page]) {
            pageTitle.textContent = pageTitles[page];
        }

        this.currentPage = page;

        // Load page-specific data
        this.loadPageData(page);
    }

    toggleSidebar(forceShow = false) {
        const sidebar = document.getElementById('sidebar');
        const sidebarShowBtn = document.getElementById('sidebarShowBtn');
        const mainContent = document.getElementById('mainContent');

        if (sidebar && mainContent) {
            if (forceShow) {
                this.sidebarCollapsed = false;
            } else {
                this.sidebarCollapsed = !this.sidebarCollapsed;
            }

            if (this.sidebarCollapsed) {
                sidebar.classList.add('collapsed');
                mainContent.classList.add('expanded');
                if (sidebarShowBtn) sidebarShowBtn.style.display = 'flex';
            } else {
                sidebar.classList.remove('collapsed');
                mainContent.classList.remove('expanded');
                if (sidebarShowBtn) sidebarShowBtn.style.display = 'none';
            }
        }
    }

    toggleMobileSidebar() {
        const sidebar = document.getElementById('sidebar');
        if (sidebar) {
            sidebar.classList.toggle('mobile-open');
        }
    }

    handleSearch(query) {
        console.log('Recherche:', query);
        // Implémenter la logique de recherche
        if (query.length > 2) {
            this.performSearch(query);
        }
    }

    performSearch(query) {
        // Simulation de recherche
        const searchResults = this.mockSearchData.filter(item => 
            item.name.toLowerCase().includes(query.toLowerCase()) ||
            item.category.toLowerCase().includes(query.toLowerCase())
        );
        
        console.log('Résultats de recherche:', searchResults);
        // Afficher les résultats
    }

    showNotifications() {
        const notifications = [
            { id: 1, message: 'Stock de café faible', type: 'warning', time: '2 min' },
            { id: 2, message: 'Nouvelle commande reçue', type: 'info', time: '5 min' },
            { id: 3, message: 'Rapport mensuel disponible', type: 'success', time: '1h' }
        ];

        // Créer et afficher le popup de notifications
        this.showNotificationPopup(notifications);
    }

    showNotificationPopup(notifications) {
        // Supprimer popup existant
        const existingPopup = document.querySelector('.notification-popup');
        if (existingPopup) {
            existingPopup.remove();
        }

        // Créer nouveau popup
        const popup = document.createElement('div');
        popup.className = 'notification-popup';
        popup.innerHTML = `
            <div class="notification-header">
                <h3>Notifications</h3>
                <button class="close-popup">&times;</button>
            </div>
            <div class="notification-list">
                ${notifications.map(notif => `
                    <div class="notification-item ${notif.type}">
                        <div class="notification-content">
                            <p>${notif.message}</p>
                            <span class="notification-time">il y a ${notif.time}</span>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;

        // Ajouter styles
        popup.style.cssText = `
            position: fixed;
            top: 80px;
            right: 20px;
            width: 300px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
            z-index: 1001;
            animation: slideIn 0.3s ease-out;
        `;

        document.body.appendChild(popup);

        // Gestionnaire de fermeture
        popup.querySelector('.close-popup').addEventListener('click', () => {
            popup.remove();
        });

        // Fermeture automatique
        setTimeout(() => {
            if (popup.parentNode) {
                popup.remove();
            }
        }, 5000);
    }

    initializeCharts() {
        // Simuler l'initialisation des graphiques
        const salesChart = document.getElementById('salesChart');
        if (salesChart) {
            // Ici, vous pourriez intégrer Chart.js ou une autre bibliothèque
            this.createMockChart(salesChart);
        }
    }

    createMockChart(canvas) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width = canvas.offsetWidth;
        const height = canvas.height = 200;

        // Dessiner un graphique simple
        ctx.fillStyle = '#8B4513';
        ctx.fillRect(50, 150, 30, 40);
        ctx.fillRect(100, 120, 30, 70);
        ctx.fillRect(150, 100, 30, 90);
        ctx.fillRect(200, 80, 30, 110);
        ctx.fillRect(250, 60, 30, 130);

        // Ajouter du texte
        ctx.fillStyle = '#666';
        ctx.font = '12px Arial';
        ctx.fillText('Lun', 55, 180);
        ctx.fillText('Mar', 105, 180);
        ctx.fillText('Mer', 155, 180);
        ctx.fillText('Jeu', 205, 180);
        ctx.fillText('Ven', 255, 180);
    }

    loadDashboardData() {
        // Simuler le chargement des données du dashboard
        this.updateStatsCards();
        this.updateProgressBars();
    }

    updateStatsCards() {
        // Animation des valeurs statistiques
        const statValues = document.querySelectorAll('.stat-value');
        statValues.forEach(stat => {
            const finalValue = stat.textContent;
            const numericValue = parseFloat(finalValue.replace(/[^\d.]/g, ''));
            
            if (!isNaN(numericValue)) {
                this.animateValue(stat, 0, numericValue, finalValue);
            }
        });
    }

    animateValue(element, start, end, suffix) {
        const duration = 1000;
        const startTime = performance.now();
        
        const updateValue = (currentTime) => {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);
            const current = start + (end - start) * this.easeOutCubic(progress);
            
            element.textContent = Math.floor(current) + suffix.replace(/[\d.]/g, '');
            
            if (progress < 1) {
                requestAnimationFrame(updateValue);
            }
        };
        
        requestAnimationFrame(updateValue);
    }

    easeOutCubic(t) {
        return 1 - Math.pow(1 - t, 3);
    }

    updateProgressBars() {
        const progressBars = document.querySelectorAll('.progress');
        progressBars.forEach((bar, index) => {
            setTimeout(() => {
                bar.style.transform = 'scaleX(1)';
                bar.style.transformOrigin = 'left';
            }, index * 200);
        });
    }

    loadPageData(page) {
        switch(page) {
            case 'stock':
                this.loadStockData();
                break;
            case 'production':
                this.loadProductionData();
                break;
            case 'finances':
                this.loadFinanceData();
                break;
            case 'orders':
                this.loadOrdersData();
                break;
            case 'customers':
                this.loadCustomersData();
                break;
            case 'reports':
                this.loadReportsData();
                break;
        }
    }

    loadStockData() {
        console.log('Chargement des données de stock...');
        // Simuler le chargement des données
    }

    loadProductionData() {
        console.log('Chargement des données de production...');
        // Animer les compteurs de production
        const productionItems = document.querySelectorAll('.item-quantity');
        productionItems.forEach((item, index) => {
            setTimeout(() => {
                item.classList.add('fade-in');
            }, index * 100);
        });
    }

    loadFinanceData() {
        console.log('Chargement des données financières...');
        // Animer les montants
        const amounts = document.querySelectorAll('.finance-card .amount');
        amounts.forEach(amount => {
            amount.classList.add('slide-in');
        });
    }

    loadOrdersData() {
        console.log('Chargement des commandes...');
    }

    loadCustomersData() {
        console.log('Chargement des données clients...');
    }

    loadReportsData() {
        console.log('Chargement des rapports...');
    }

    setupFormHandlers() {

        // Gestionnaires de formulaires
        const forms = document.querySelectorAll('form');
        forms.forEach(form => {
            // Ne pas intercepter les formulaires de stats et stats-filtre
            const action = form.getAttribute('action') || '';
            // Désactiver l'interception pour les formulaires d'unité (CRUD)
            if (action.includes('/administratif/production/stats') || action.includes('/administratif/production/unite')) {
                return;
            }
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleFormSubmit(form);
            });
        });

        // Validation en temps réel
        const inputs = document.querySelectorAll('.form-input');
        inputs.forEach(input => {
            input.addEventListener('blur', () => {
                this.validateField(input);
            });
        });
    }

    handleFormSubmit(form) {
        const formData = new FormData(form);
        const data = Object.fromEntries(formData);
        
        console.log('Données du formulaire:', data);
        this.showSuccessMessage('Formulaire soumis avec succès!');
    }

    validateField(field) {
        const value = field.value.trim();
        const fieldName = field.name || field.id;
        
        // Validation basique
        if (field.required && !value) {
            this.showFieldError(field, `Le champ ${fieldName} est requis`);
            return false;
        }
        
        this.clearFieldError(field);
        return true;
    }

    showFieldError(field, message) {
        field.classList.add('error');
        
        // Supprimer message d'erreur existant
        const existingError = field.parentNode.querySelector('.field-error');
        if (existingError) {
            existingError.remove();
        }
        
        // Ajouter nouveau message
        const errorEl = document.createElement('span');
        errorEl.className = 'field-error';
        errorEl.textContent = message;
        errorEl.style.cssText = 'color: var(--danger-color); font-size: 0.8rem; margin-top: 0.25rem;';
        
        field.parentNode.appendChild(errorEl);
    }

    clearFieldError(field) {
        field.classList.remove('error');
        const errorEl = field.parentNode.querySelector('.field-error');
        if (errorEl) {
            errorEl.remove();
        }
    }

    setupButtonHandlers() {
        // Boutons d'action
        const actionButtons = document.querySelectorAll('.btn');
        actionButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                if (!button.form) { // Éviter les boutons de formulaire
                    this.handleButtonClick(button, e);
                }
            });
        });

        // Boutons icône
        const iconButtons = document.querySelectorAll('.btn-icon');
        iconButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                this.handleIconButtonClick(button, e);
            });
        });
    }

    handleButtonClick(button, event) {
        const buttonText = button.textContent.trim();
        
        // Ajouter effet de clic
        button.classList.add('clicked');
        setTimeout(() => {
            button.classList.remove('clicked');
        }, 200);

        console.log('Bouton cliqué:', buttonText);
        
        // Actions spécifiques selon le bouton
        if (buttonText.includes('Ajouter') || buttonText.includes('Nouveau')) {
            this.showAddModal();
        } else if (buttonText.includes('Exporter')) {
            this.exportData();
        } else if (buttonText.includes('Générer')) {
            this.generateReport();
        }
    }

    handleIconButtonClick(button, event) {
        const icon = button.querySelector('i');
        const actionType = icon.classList.contains('fa-edit') ? 'edit' : 
                          icon.classList.contains('fa-trash') ? 'delete' : 'action';
        
        console.log('Action:', actionType);
        
        if (actionType === 'delete') {
            this.confirmDelete(() => {
                this.showSuccessMessage('Élément supprimé avec succès');
            });
        } else if (actionType === 'edit') {
            this.showEditModal();
        }
    }

    showAddModal() {
        this.showModal('Ajouter un nouvel élément', `
            <form id="addForm">
                <div class="form-group">
                    <label class="form-label">Nom</label>
                    <input type="text" class="form-input" name="name" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Description</label>
                    <textarea class="form-input" name="description" rows="3"></textarea>
                </div>
                <div style="display: flex; gap: 1rem; justify-content: flex-end; margin-top: 1.5rem;">
                    <button type="button" class="btn outline modal-close">Annuler</button>
                    <button type="submit" class="btn primary">Ajouter</button>
                </div>
            </form>
        `);
    }

    showEditModal() {
        this.showModal('Modifier l\'élément', `
            <form id="editForm">
                <div class="form-group">
                    <label class="form-label">Nom</label>
                    <input type="text" class="form-input" name="name" value="Exemple" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Description</label>
                    <textarea class="form-input" name="description" rows="3">Description exemple</textarea>
                </div>
                <div style="display: flex; gap: 1rem; justify-content: flex-end; margin-top: 1.5rem;">
                    <button type="button" class="btn outline modal-close">Annuler</button>
                    <button type="submit" class="btn primary">Modifier</button>
                </div>
            </form>
        `);
    }

    showModal(title, content) {
        // Suppr
        const existingModal = document.querySelector('.modal-overlay');
        if (existingModal) {
            existingModal.remove();
        }

        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.innerHTML = `
            <div class="modal">
                <div class="modal-header">
                    <h3>${title}</h3>
                    <button class="modal-close">&times;</button>
                </div>
                <div class="modal-content">
                    ${content}
                </div>
            </div>
        `;

        // Styles du modal
        modal.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 2000;
            animation: fadeIn 0.3s ease;
        `;

        const modalContent = modal.querySelector('.modal');
        modalContent.style.cssText = `
            background: white;
            border-radius: 8px;
            padding: 0;
            max-width: 500px;
            width: 90%;
            max-height: 80vh;
            overflow-y: auto;
            animation: slideIn 0.3s ease-out;
        `;

        const modalHeader = modal.querySelector('.modal-header');
        modalHeader.style.cssText = `
            padding: 1.5rem;
            border-bottom: 1px solid var(--gray-200);
            display: flex;
            justify-content: space-between;
            align-items: center;
        `;

        const modalBody = modal.querySelector('.modal-content');
        modalBody.style.cssText = `
            padding: 1.5rem;
        `;

        document.body.appendChild(modal);

        // Gestionnaires d'événements
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.remove();
            }
        });

        modal.querySelectorAll('.modal-close').forEach(btn => {
            btn.addEventListener('click', () => {
                modal.remove();
            });
        });

        // Gestionnaire de formulaire dans le modal
        const form = modal.querySelector('form');
        if (form) {
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                modal.remove();
                this.showSuccessMessage('Opération réussie!');
            });
        }
    }

    confirmDelete(callback) {
        this.showModal('Confirmer la suppression', `
            <p style="margin-bottom: 1.5rem;">Êtes-vous sûr de vouloir supprimer cet élément ? Cette action est irréversible.</p>
            <div style="display: flex; gap: 1rem; justify-content: flex-end;">
                <button class="btn outline modal-close">Annuler</button>
                <button class="btn danger" id="confirmDelete">Supprimer</button>
            </div>
        `);

        document.getElementById('confirmDelete').addEventListener('click', () => {
            document.querySelector('.modal-overlay').remove();
            callback();
        });
    }

    exportData() {
        this.showSuccessMessage('Export en cours...');
        
        // Simuler l'export
        setTimeout(() => {
            this.showSuccessMessage('Données exportées avec succès!');
        }, 2000);
    }

    generateReport() {
        this.showSuccessMessage('Génération du rapport en cours...');
        
        setTimeout(() => {
            this.showSuccessMessage('Rapport généré avec succès!');
        }, 3000);
    }

    showSuccessMessage(message) {
        const toast = document.createElement('div');
        toast.className = 'toast success';
        toast.textContent = message;
        toast.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: var(--success-color);
            color: white;
            padding: 1rem 1.5rem;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
            z-index: 3000;
            animation: slideIn 0.3s ease-out;
        `;

        document.body.appendChild(toast);

        setTimeout(() => {
            toast.style.animation = 'slideOut 0.3s ease-in forwards';
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.remove();
                }
            }, 300);
        }, 3000);
    }

    handleResize() {
        // Gérer le redimensionnement
        if (window.innerWidth <= 768) {
            document.getElementById('sidebar').classList.remove('mobile-open');
        }
    }

    // Données mockées pour la recherche
    get mockSearchData() {
        return [
            { name: 'Espresso', category: 'Café', price: 2.50 },
            { name: 'Cappuccino', category: 'Café', price: 3.50 },
            { name: 'Latte', category: 'Café', price: 4.00 },
            { name: 'Croissant', category: 'Pâtisserie', price: 1.80 },
            { name: 'Muffin', category: 'Pâtisserie', price: 2.20 },
        ];
    }
}

// Styles additionnels pour les modaux et toasts
const additionalStyles = `
    <style>
        .modal-header h3 {
            margin: 0;
            color: var(--dark-color);
        }
        
        .modal-close {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: var(--gray-600);
            padding: 0.25rem;
        }
        
        .modal-close:hover {
            color: var(--dark-color);
        }
        
        .notification-popup {
            animation: slideIn 0.3s ease-out;
        }
        
        .notification-header {
            padding: 1rem;
            border-bottom: 1px solid var(--gray-200);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .notification-header h3 {
            margin: 0;
            font-size: 1.1rem;
            color: var(--dark-color);
        }
        
        .close-popup {
            background: none;
            border: none;
            font-size: 1.25rem;
            cursor: pointer;
            padding: 0.25rem;
            color: var(--gray-600);
        }
        
        .notification-list {
            max-height: 300px;
            overflow-y: auto;
        }
        
        .notification-item {
            padding: 1rem;
            border-bottom: 1px solid var(--gray-200);
            display: flex;
            align-items: flex-start;
            gap: 0.75rem;
        }
        
        .notification-item:last-child {
            border-bottom: none;
        }
        
        .notification-item.warning::before {
            content: '⚠️';
        }
        
        .notification-item.info::before {
            content: 'ℹ️';
        }
        
        .notification-item.success::before {
            content: '✅';
        }
        
        .notification-content p {
            margin: 0 0 0.25rem 0;
            font-size: 0.9rem;
            color: var(--dark-color);
        }
        
        .notification-time {
            font-size: 0.8rem;
            color: var(--gray-500);
        }
        
        .btn.clicked {
            transform: scale(0.95);
            transition: transform 0.1s ease;
        }
        
        .form-input.error {
            border-color: var(--danger-color);
        }
        
        @keyframes slideOut {
            from { transform: translateX(0); opacity: 1; }
            to { transform: translateX(100%); opacity: 0; }
        }
    </style>
`;

// Ajouter les styles additionnels
document.head.insertAdjacentHTML('beforeend', additionalStyles);

// Initialiser l'application
document.addEventListener('DOMContentLoaded', () => {
    const app = new CafeManager();
    
    // Exposer l'instance globalement pour le débogage
    window.cafeManager = app;
    
    console.log('🚀 CafeManager Pro initialisé avec succès!');
});

// Fonctions utilitaires
const utils = {
    formatCurrency: (amount) => {
        return new Intl.NumberFormat('fr-FR', {
            style: 'currency',
            currency: 'EUR'
        }).format(amount);
    },
    
    formatDate: (date) => {
        return new Intl.DateTimeFormat('fr-FR', {
            day: '2-digit',
            month: '2-digit',  
            year: 'numeric'
        }).format(date);
    },
    
    formatTime: (date) => {
        return new Intl.DateTimeFormat('fr-FR', {
            hour: '2-digit',
            minute: '2-digit'
        }).format(date);
    },
    
    generateId: () => {
        return 'id_' + Math.random().toString(36).substr(2, 9);
    },
    
    debounce: (func, wait) => {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }
};

// Exposer les utilitaires globalement
window.utils = utils;

// Gestion de l'activation du menu sidebar
const sidebarNav = document.querySelector('.sidebar-nav');
if (sidebarNav) {
    sidebarNav.addEventListener('click', function(e) {
        const link = e.target.closest('a.nav-link');
        if (link) {
            // Retirer l'active de tous les li
            sidebarNav.querySelectorAll('.nav-item').forEach(item => item.classList.remove('active'));
            // Ajouter active au parent li du lien cliqué
            const li = link.closest('.nav-item');
            if (li) {
                li.classList.add('active');
                // Persister l'URL active
                localStorage.setItem('sidebarActive', link.getAttribute('th:href') || link.getAttribute('href'));
            }
        }
    });
    // Au chargement, restaurer l'élément actif
    const activeHref = localStorage.getItem('sidebarActive');
    if (activeHref) {
        sidebarNav.querySelectorAll('.nav-link').forEach(link => {
            if ((link.getAttribute('th:href') || link.getAttribute('href')) === activeHref) {
                const li = link.closest('.nav-item');
                if (li) li.classList.add('active');
            }
        });
    }
}
