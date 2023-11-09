# Utilisez une image de base Java 11 Alpine d'AdoptOpenJDK
FROM adoptopenjdk/openjdk11:alpine-jre

# Exposez le port sur lequel votre application Spring Boot s'exécute (par défaut, c'est le port 8080)
EXPOSE 8082

# Ajouter le fichier JAR de l'application dans le conteneur
ADD target/devops_project-2.1.jar devops_project-2.1.jar

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/devops_project-2.1.jar"]
