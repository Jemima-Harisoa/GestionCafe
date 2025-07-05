#!/bin/bash

# === Configuration ===
PROJECT_NAME="GestionCafe"
TOMCAT_HOME="/opt/tomcat"
WAR_FILE="target/${PROJECT_NAME}.war"

# Script de déploiement pour Tomcat 10.1.28
# Construction du projet
echo "📦 Construction du projet avec Maven..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "❌ Erreur lors de la construction du projet"
    exit 1
fi

# Vérification que le fichier WAR existe
if [ ! -f "$WAR_FILE" ]; then
    echo "❌ Fichier WAR non trouvé: $WAR_FILE"
    find target/ -name "*.war"
    exit 1
fi

# Arrêt de Tomcat
echo "🛑 Arrêt de Tomcat..."
sudo $TOMCAT_HOME/bin/shutdown.sh

# Attendre que Tomcat s'arrête
sleep 5

# Suppression de l'ancienne version
echo "🧹 Nettoyage de l'ancien déploiement..."
sudo rm -f "$TOMCAT_HOME/webapps/${PROJECT_NAME}.war"
sudo rm -rf "$TOMCAT_HOME/webapps/${PROJECT_NAME}"

# Copie du nouveau WAR
echo "📤 Copie du nouveau fichier WAR..."
sudo cp "$WAR_FILE" "$TOMCAT_HOME/webapps/${PROJECT_NAME}.war"

if [ $? -ne 0 ]; then
    echo "❌ Erreur lors de la copie du fichier WAR"
    exit 1
fi

# Démarrage de Tomcat
echo "🚀 Démarrage de Tomcat..."
sudo $TOMCAT_HOME/bin/startup.sh

echo "✅ Déploiement terminé avec succès!"
echo "🌐 L'application sera disponible à: http://localhost:8080/${PROJECT_NAME}/"
