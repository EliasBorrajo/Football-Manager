# FOOTBALL MANAGER - JAVA EE
Dans ce mini projet, nous avons traité le thème "Football". Il est ainsi possible d'obtenir des informations sur des ligues, clubs et joueurs de foot.
De plus, il est possible, en tant que fan, d'obtenir des informations sur son club favoris et retrouvé tous les joueurs y faisant partie.


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
Ces utilisateurs sont à rentrer manuellement dans le registre du serveur WildFly car ils n'existent pas dans la DB.

## Optional - Configuration de la base de données HSQLDB - Lancement automatique
On a re-crée la database HSQL pour qu'elle se lance automatiquement au démarrage de l'application.
Normalement en cours nous devions lancer le scripte "startDB.bat" pour lancer la database HSQLDB, 
mais étatn donné qe c'est embêtant de devoir le faire à chaque fois, nous avons ajouoté au code un programme "createDB.java" qui doit être lancé avant le serveur wildfly.


Run configuration -> Edit Configurations -> Before launch -> Add new -> Run program -> /utils/serverHSQLDB/createDB

Ordre d'exécution avant le lancement :
1) createDB programm - Pour initialiser la base de données
2) build - Pour construire le projet
3) build artifact - Pour construire l'artefact (fichier war) et le déployer sur le serveur (wildFly)

### Exécuter la configuration
1) Cliquez sur la flèche verte -> RUN -> ceci démarre le serveur HSQLDB
2) Cliquez à nouveau sur la flèche verte -> RUN -> ceci démarre le serveur WildFly et déploie le fichier war.


# Auteurs : 
- Elias Borrajo
- Theo Clerc

GitLab repository : [Jakarta Footall Manager](https://gitlab.com/hes-so-elias/semestre6/jakarta_football)
