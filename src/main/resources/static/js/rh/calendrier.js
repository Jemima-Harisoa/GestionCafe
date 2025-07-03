const months = [
  "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
  "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
];

const days = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"];
const calendarContainer = document.getElementById('calendar');
const selectedDates = new Set();
const year = 2025;

// === Prétraitement des jours reçus ===
const couleurEmployes = {};
const employeLegende = new Map();

function randomColor() {
  const letters = '456789ABCD';
  return '#' + Array.from({ length: 6 }, () => letters[Math.floor(Math.random() * letters.length)]).join('');
}

const joursMap = {}; // yyyy-mm-dd -> {type: 'ferie' | 'employe', info: ...}
jours.forEach(j => {
  const date = new Date(j.jour); // <-- CORRECTION : utiliser j.jour
  const key = date.toISOString().split("T")[0];

  if (j.jourFerie) {
    joursMap[key] = { type: "ferie", nom: j.jourFerie.name || "Jour férié" };
  } else if (j.employe) {
    const nom = j.employe.nom || `Employé ${j.employe.id}`;
    if (!couleurEmployes[j.employe.id]) {
      const color = randomColor();
      couleurEmployes[j.employe.id] = color;
      employeLegende.set(j.employe.id, { nom, couleur: color });
    }
    joursMap[key] = { type: "employe", employeId: j.employe.id, nom };
  }
});

function generateCalendar(year) {
  calendarContainer.innerHTML = '';

  for (let m = 0; m < 12; m++) {
    const monthDiv = document.createElement('div');
    monthDiv.className = 'month';

    const title = document.createElement('h3');
    title.textContent = `${months[m]} ${year}`;
    monthDiv.appendChild(title);

    const header = document.createElement('div');
    header.className = 'days-header';
    for (const d of days) {
      const dayDiv = document.createElement('div');
      dayDiv.textContent = d;
      header.appendChild(dayDiv);
    }
    monthDiv.appendChild(header);

    const grid = document.createElement('div');
    grid.className = 'days-grid';

    const firstDay = new Date(year, m, 1).getDay();
    let startDay = (firstDay === 0) ? 6 : firstDay - 1;
    const daysInMonth = new Date(year, m + 1, 0).getDate();

    for (let i = 0; i < startDay; i++) {
      const empty = document.createElement('div');
      empty.className = 'empty';
      grid.appendChild(empty);
    }

    for (let d = 1; d <= daysInMonth; d++) {
      const btn = document.createElement('button');
      btn.textContent = d;
      btn.className = 'day-button';

      const fullDate = new Date(year, m, d);
      const iso = fullDate.toISOString().split("T")[0];
      btn.dataset.date = iso;

      if (joursMap[iso]) {
        if (joursMap[iso].type === 'ferie') {
          btn.style.backgroundColor = 'black';
          btn.style.color = 'white';
          btn.title = joursMap[iso].nom;
        } else if (joursMap[iso].type === 'employe') {
          const color = couleurEmployes[joursMap[iso].employeId];
          btn.style.backgroundColor = color;
          btn.style.color = 'white';
          btn.title = joursMap[iso].nom;
        }
      }

      btn.addEventListener('click', () => {
        if (selectedDates.has(iso)) {
          selectedDates.delete(iso);
          btn.classList.remove('selected');
        } else {
          selectedDates.add(iso);
          btn.classList.add('selected');
        }
        console.log("Dates sélectionnées :", Array.from(selectedDates));
      });

      grid.appendChild(btn);
    }

    monthDiv.appendChild(grid);
    calendarContainer.appendChild(monthDiv);
  }

  generateLegend();
}

// === Légende dynamique ===
function generateLegend() {
  let legend = document.getElementById('calendar-legend');
  if (!legend) {
    legend = document.createElement('div');
    legend.id = 'calendar-legend';
    legend.style.marginLeft = '20px';
    legend.style.padding = '10px';
    legend.style.border = '1px solid #ccc';
    legend.style.backgroundColor = '#f9f9f9';
    legend.style.maxWidth = '250px';
    document.querySelector('main').appendChild(legend);
  }

  legend.innerHTML = "<h4>Légende</h4>";

  const ferie = document.createElement('div');
  ferie.innerHTML = `<span style="display:inline-block;width:15px;height:15px;background:black;margin-right:10px;"></span> Jour férié`;
  legend.appendChild(ferie);

  for (const [id, info] of employeLegende.entries()) {
    const line = document.createElement('div');
    line.innerHTML = `<span style="display:inline-block;width:15px;height:15px;background:${info.couleur};margin-right:10px;"></span> ${info.nom}`;
    legend.appendChild(line);
  }
}

generateCalendar(year);
