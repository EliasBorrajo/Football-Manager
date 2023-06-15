# FOOTBALL MANAGER - JAVA JAKARTA
Dans ce mini projet, nous avons traité le thème "Football". Il est ainsi possible d'obtenir des informations sur des ligues, clubs et joueurs de foot.
De plus, il est possible, en tant que fan, d'obtenir des informations sur son club favoris et retrouvé tous les joueurs y faisant partie.

# Portage du projet Eclipse vers IntelliJ IDEA
Pour exporter le projet Eclipse vers IntelliJ IDEA, il faut suivre les étapes suivantes :

Si vous importez directement le projet dans intelliJ IDEA, suivre à partir de l'étape 3.

1) Dans eclipse, créer le ficher .gitignore à la racine du projet
* Ainsi les fichiers systèmes d'Eclipse ne sont pas portés sur le repository GitLab ou GitHub et ne sont pas importés dans IntelliJ IDEA
* [gitIgnore](/.gitignore)
2) Push le projet sur un repository GitLab ou GitHub vide
3) Dans IntelliJ IDEA, faire un "Get from Version Control" et choisir le repository GitLab ou GitHub
* Laisser IntelliJ IDEA importer le projet
4) Dans intelliJ IDEA :
* File -> Project Structure -> Project -> Project SDK -> 11
* Edit Configurations -> Application Server -> WildFly 23.0.2.Final
* Edit Configurations -> URL -> http://localhost:8080/[ARTIFACT_NAME]/faces/[HOME_PAGE_NAME].xhtml
* Edit Configurations -> Deployment -> + -> Artifact -> Exploded
* Optional - Edit Configurations -> Before launch -> Add new -> Run program -> /utils/serverHSQLDB/createDB

Si vous importez le projet dans un autre IDE, il faudra configurer le serveur WildFly et la base de données HSQLDB manuellement.


# Configuration de l'application Java Jakarta EE

### Serveur Wildfly JBoss 
- [WildFly 23.0.2 Final](https://www.wildfly.org/downloads/#23.0.2.Final)

### Version de Java
- [openjdk-11.0.2_windows-x64_bin.zip](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip)

## Utilisateurs / Groupes / Mots de passe

| Utilisateur | Groupe                | Mot de passe |
|-------------|-----------------------|--------------|
| Elias       | Manager               | Elias        |
| Theo        | Player                | Theo         |
| Eric        | Fan                   | Eric         |
| Admin       | Manager, Player, Fan  | Admin        |
Ces utilisateurs sont à rentrer manuellement dans le registre du serveur WildFly car ils n'existent pas dans la DB.

## Configuration in IntelliJ IDEA
Voici un screenshot de la configuration de l'application dans IntelliJ IDEA.
![Config 1](/src/main/resources/images/Configuration01.png)
* Il faut configurer l'emplacemnt du serveur WildFly dans IntelliJ IDEA.
* Il faut donner l'URL à ouvrir dans le navigateur pour accéder à l'application.
  * Structure : http://localhost:8080/[ARTIFACT_NAME]/faces/[HOME_PAGE_NAME].xhtml 
* Tout en bas, on peut voir la section BEFORE LAUNCH, laisser par défaut, ou optionellement ajouter en tête de liste le programme "createDB.java" pour lancer la base de données HSQLDB avant de lancer le serveur wildfly.

Voici les screenshots des autres onglets

![Config 2](/src/main/resources/images/Configuration02.png)
* Il faut décider l'artefact à déployer sur le serveur WildFly. On peut prendre le Exploded ou non, cela n'a pas d'importance.
  * Exploded : Permet le debug en live plus facilement, car il n'y a pas besoin de build l'artefact à chaque fois.

![Config 3](/src/main/resources/images/Configuration03.png)


## Optional - BEFORE LAUNCH - Configuration de la base de données HSQLDB - Lancement automatique
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
- [Elias Borrajo](mailto:borrajo.elias@gmail.com)
- [Theo Clerc](mailto:theo.clerc@hes-so.ch)

GitLab repository privé : [Jakarta Footall Manager](https://gitlab.com/hes-so-elias/semestre6/jakarta_football)
Pour avoir accès, contacter les auteurs.
