# 🏢 Projet de Gestion 
Theme : Cafeteria
Application : GestionCafe
Description : Application Web de Gestion quotidienne et adminsitratif d'un cafeteria 
DEADLINE : Jeudi 10 juillet 2025 a 6h
Mini-soutennace : Jeudi 10 juillet a partie de 8h

---

## 🚀 Fonctionnalités

Voir /docs/Fonctionnalites.md

---

## 🛠️ Technologies utilisées

Voir /docs/Technologies.md

---

## Architecture
- model : les classes sont liees aux tables des bases via Hibernate
- repository : gere l interaction avec la base, fournit les methodes
- service : pont entre repository et controller
- controller : gere les requetes http, appelle service, prepare les donnees des 

+ Voir /docs/Architecture.md

---


## recuperer le projet

- Github : https://github.com/loicDYlanrak/GestionCafe?fbclid=IwY2xjawLDZAFleHRuA2FlbQIxMABicmlkETFVSjJuNXFGTW1oNDJhREp2AR6EUfBdhpTz-OiKtkTqPRC9RXys4GXb83X58qa_FsnFeBxptoARAYZg3zoOuw_aem_og1QtdVg6OnnPkITBIzFVw

- code -> copier URL
- GitHub desktop : menu -> file -> clone repository -> url :
    - url : coller le lien 
    - local path : choisir le dossier pour mettre le dossier du projet
- changer de branche a votre nom
- ouvrir le code dans vscode
- aller dans /src/main/resources/application.properties :  changer les parametres de la base
- aller dans deploy.bat et changer TOMCAT_WEBAPPS_DIR

---

## controle de version

- un developpeur = une branche en son nom + nbTache accompli (ex au depart : toky0)
- commit tous les 20-30 min (titre avec minimum de sens)
- push apres test sur UNE TACHE
- prevenir l'integrateur du sous groupe a chaque TACHE deja  tester et pusher
- prendre la nouvelle branche creer par l integrateur : votre nom + nbTache accompli(toky1 puis toky2 etc) et ne plus toucher aux anciennes branches
- Pour les integrateurs : Tony, Loic, Tsiky
    - branche main pour 03/07/2025
    - branche dev pour 
    - un module = une branche pour clore la branche
    - quand un developpeur fini une tache, integrer dans le code de l integrateur et creer une nouvelle branche au nom du developpeur avec le prochain numero de sa tache 

## Norme de Developpement

Voir /docs/NormeDeDev.md

---

## executer le projet 
executer a la racine du projet :
./mvnw clean package
verifier : /target/GestionCafe.war

--- 

## deployement du projet 
./deploy.bat

---

## lancement du projet

- Demarrer tomcat dans l'invite des commmandes avec : 
startup.bat
- Ouvrir le navigateur et aller sur l'URL
http://localhost:8080/GestionCafe/

- si erreur : 
    - demarrer ou recharger dans http://localhost:8080/manager/html
---