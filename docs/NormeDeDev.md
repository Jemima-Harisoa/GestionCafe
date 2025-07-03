Langue : FRANCAIS

kebab-case : tirer-par-mots
snake_case : tirer_bas_par_mots
camelCase : debutMinusculePuisMajusculeParMots
PascalCase : DebutMajusculeParMots

Database
    nom de base : 1 mot
    nom de table : snake_case
    nom de champ : snake_case
    cle primaire : id
    cle etrangere : id_nomdetable
    nom de view : action_metier_view

Classe
    - nom de classe : PascalCase
        - Model : 
            - nom de table version PascalCase
            - suit la regle de Entity
            - getters et setters
        - le reste terminer par : Repository, Controller, Service
    - nom de variable/attribut : camelCase 
        - array / list : nom de classe / table avec s
        - object : nom de classe / table
    - nom de fonction : camelCase
        - dans {Repository, Service} :  
            - return result 
            - throws Exception 
        - dans {Controller} :
            - si dans try : envoyer les reponses vers la page desiree
            - si catch exception : envoyer message d erreur dans page precedente
            
/src/main/java/com/gestioncafe/database
    - cheatsheet.sql : cheatsheet sur postgresql
    - methods.md : liste des methodes fournis par JpaRepository
    - data.sql : les donnees de tests
    - query.sql : les requetes non fournis par JpaRepository
    - reset : listes de commandes pour drop database
    - table.sql :  liste des tables 
    - view.sql : liste des views crees

View 
    - nom de page : kebab-case 
    - tout lien commence par @{} (effet thymeleaf)
    - pour integrer les fichiers dans static : passer direct au nom du sous dossier
    - si utiliser /templates: toujours integrer html.html(pour check les elements voir index.html)
    - tous les images dans les bases sont dans /uploads
    - gerer les erreurs avec if et les afficher
