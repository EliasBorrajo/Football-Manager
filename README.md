# Configuration de l'application Java Jakarta EE

## WildFly 23.0.2 Final

## Version de Java
- openjdk-11.0.2_windows-x64_bin.zip

## Utilisateurs / Groupes / Mots de passe

| Utilisateur | Groupe                | Mot de passe |
|-------------|-----------------------|--------------|
| Elias       | Manager               | Elias        |
| Theo        | Player                | Theo         |
| Eric        | Fan                   | Eric         |
| Admin       | Manager, Player, Fan  | Admin        |

## Optional - Configuration de la base de données HSQLDB - Lancement automatique
On a re-crée la database HSQL pour qu'elle se lance automatiquement au démarrage de l'application.
Normalement en cours nous devions lancer le scripte "startDB.bat" pour lancer la database HSQLDB, 
mais étatn donné qe c'est embêtant de devoir le faire à chaque fois, nous avons ajouoté au code un programme "createDB.java" qui doit être lancé avant le serveur wildfly.


Run configuration -> Edit Configurations -> Before launch -> Add new -> Run program -> /utils/serverHSQLDB/createDB

Run before launch order : 
1) createDB programm - To initialize the database
2) build - To build the project
3) build artifact - To build the artifact (war file) and deploy it on the server (wildFly)

## Run configuration 
1) Click on the green arrow -> RUN -> this starts the HSQLDB server
2) Click on the green arrow AGAIN -> RUN -> this starts the WildFly server and deploys the war file
