// Script pour rendre la table triable
document.addEventListener('DOMContentLoaded', function() {
    const table = document.querySelector('.data-table');
    if (!table) return;

    // Ajouter les classes et icônes aux en-têtes triables
    const headers = table.querySelectorAll('th');
    headers.forEach((header, index) => {
        // Rendre les colonnes Date et Montant triables (indices 0 et 1)
        if (index === 0 || index === 1) {
            header.classList.add('sortable');
            
            // Créer le conteneur pour le header avec icône
            const headerText = header.innerHTML;
            const sortIcon = index === 0 ? 'fas fa-sort-numeric-up' : 'fas fa-sort-numeric-up';
            
            header.innerHTML = `
                <div class="sort-header">
                    <span>${headerText}</span>
                    <i class="sort-icon ${sortIcon}"></i>
                </div>
            `;
            
            // Variables pour suivre l'état du tri
            header.dataset.sortDirection = 'none'; // none, asc, desc
            header.dataset.columnIndex = index;
            
            // Ajouter l'événement de clic
            header.addEventListener('click', function() {
                sortTable(table, index, header);
            });
        }
    });

    function sortTable(table, columnIndex, headerElement) {
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));
        
        // Déterminer la direction du tri
        let sortDirection = headerElement.dataset.sortDirection;
        if (sortDirection === 'none' || sortDirection === 'desc') {
            sortDirection = 'asc';
        } else {
            sortDirection = 'desc';
        }
        
        // Réinitialiser tous les autres headers
        table.querySelectorAll('th.sortable').forEach(th => {
            if (th !== headerElement) {
                th.dataset.sortDirection = 'none';
                th.classList.remove('sort-asc', 'sort-desc');
                const icon = th.querySelector('.sort-icon');
                if (icon) {
                    icon.className = columnIndex === 0 ? 'sort-icon fas fa-sort-numeric-up' : 'sort-icon fas fa-sort-numeric-up';
                }
            }
        });
        
        // Mettre à jour l'header actuel
        headerElement.dataset.sortDirection = sortDirection;
        headerElement.classList.remove('sort-asc', 'sort-desc');
        headerElement.classList.add(sortDirection === 'asc' ? 'sort-asc' : 'sort-desc');
        
        // Mettre à jour l'icône
        const icon = headerElement.querySelector('.sort-icon');
        if (icon) {
            if (columnIndex === 0) { // Date
                icon.className = sortDirection === 'asc' ? 
                    'sort-icon fas fa-sort-numeric-up' : 
                    'sort-icon fas fa-sort-numeric-down';
            } else { // Montant
                icon.className = sortDirection === 'asc' ? 
                    'sort-icon fas fa-sort-numeric-up' : 
                    'sort-icon fas fa-sort-numeric-down';
            }
        }
        
        // Trier les lignes
        rows.sort((a, b) => {
            const cellA = a.children[columnIndex];
            const cellB = b.children[columnIndex];
            
            if (!cellA || !cellB) return 0;
            
            let valueA = cellA.textContent.trim();
            let valueB = cellB.textContent.trim();
            
            // Gérer les valeurs vides
            if (valueA === '-' || valueA === '') valueA = columnIndex === 0 ? '1900-01-01' : '0';
            if (valueB === '-' || valueB === '') valueB = columnIndex === 0 ? '1900-01-01' : '0';
            
            let comparison = 0;
            
            if (columnIndex === 0) {
                // Tri par date
                const dateA = parseDate(valueA);
                const dateB = parseDate(valueB);
                comparison = dateA - dateB;
            } else if (columnIndex === 1) {
                // Tri par montant
                const numA = parseFloat(valueA.replace(/[^\d.,-]/g, '').replace(',', '.')) || 0;
                const numB = parseFloat(valueB.replace(/[^\d.,-]/g, '').replace(',', '.')) || 0;
                comparison = numA - numB;
            }
            
            return sortDirection === 'asc' ? comparison : -comparison;
        });
        
        // Réorganiser les lignes dans le DOM
        rows.forEach(row => tbody.appendChild(row));
    }
    
    function parseDate(dateString) {
        // Essayer différents formats de date
        if (dateString === '-' || dateString === '') {
            return new Date('1900-01-01');
        }
        
        // Format ISO (YYYY-MM-DD)
        if (dateString.match(/^\d{4}-\d{2}-\d{2}$/)) {
            return new Date(dateString);
        }
        
        // Format français (DD/MM/YYYY)
        if (dateString.match(/^\d{2}\/\d{2}\/\d{4}$/)) {
            const parts = dateString.split('/');
            return new Date(parts[2], parts[1] - 1, parts[0]);
        }
        
        // Format américain (MM/DD/YYYY)
        if (dateString.match(/^\d{2}\/\d{2}\/\d{4}$/)) {
            return new Date(dateString);
        }
        
        // Autres formats
        const date = new Date(dateString);
        return isNaN(date.getTime()) ? new Date('1900-01-01') : date;
    }
});