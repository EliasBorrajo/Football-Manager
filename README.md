# Football Manager – Java EE 7 (legacy stack)

> *Mini information system to explore football leagues, clubs and players, built with ********Java EE 7******** and WildFly.*

---

## 📚 Project Description

Football Manager is a three‑tier web application developed as a mini‑project for the HES‑SO course “Java/Jakarta EE”.
The app lets visitors consult football **leagues**, **clubs** and **players** stored in an in‑memory HSQLDB database, while authenticated users with higher roles can perform richer actions such as editing club data or following their favourite squad.

The primary goal is to demonstrate an end‑to‑end **Java EE 7** stack — **JSF 2.2 / Facelets**, **EJB 3.2** and **JPA 2.1** — deployed on **WildFly 23.0.2** and running on **JDK 11**.

> *Note : the project has not yet been migrated to the   ********`jakarta.*`******** namespace (Jakarta EE 9+).*

---

## 🧪 Technologies Used

| Type               | Name / Tool                               | Version      |
| ------------------ | ----------------------------------------- | ------------ |
| Language           | Java (OpenJDK)                            | 11.0.2       |
| Build Tool         | Maven                                     | 3.8.x        |
| Frameworks         | **Java EE 7** (JSF 2.2, EJB 3.2, JPA 2.1) | 7            |
| Template Engine    | Facelets                                  | 2.2          |
| Application Server | WildFly JBoss                             | 23.0.2.Final |
| Database           | HSQLDB (in‑memory)                        | 1.8.0.10     |

\--- 

## 🎯 Learning Objectives

* **Understand** the end‑to‑end architecture of a multi‑tier **Java EE 7** application.
* **Learn** core Java EE concepts (JSF 2.2, CDI/EJB beans, JPA 2.1) and best practices.
* **Implement** CRUD operations with JPA entities and relations.
* **Apply** Stateless / Stateful EJBs and manage transactions with JTA.
* **Secure** resources via role‑based access in WildFly.
* **Automate** deployment & hot‑reload with Maven and IntelliJ IDEA.

## 🔧 Features

* Consult **league** details and list associated clubs.
* View **club** information and stadium data.
* Browse **player** roster for a given club.
* Fans: set a **favourite club** and see its current squad.
* Managers: *add* / *update* clubs and players *(Manager role required).*
* Players: update their personal profile *(Player role required).*

---

## 🧠 Design & Architecture

* **Presentation layer** : JSF / Facelets pages, managed beans.
* **Business layer** : EJB services encapsulating business logic.
* **Persistence layer** : JPA entities, repository classes, HSQLDB.
* Deployed as `football-manager.war` on WildFly.

---

## 🛠️ Setup & Deployment

### IDE import (IntelliJ IDEA)

1. `git clone` this repository.
2. *IDEA* → **Get from Version Control** → select the clone.
3. Ensure **Project SDK 11** and let IDEA index the project.

### Run configuration (IntelliJ IDEA)

| Step | Action                                                                                                                                 |
| ---- | -------------------------------------------------------------------------------------------------------------------------------------- |
| 1    | Configure a **WildFly 23.0.2** server (local).                                                                                         |
| 2    | Add **Deployment** → *artifact*: `football-manager.war` — choose **Exploded** for hot‑reload.                                          |
| 3    | URL pattern: `http://localhost:8080/football-manager/faces/index.xhtml`                                                                |
| 4    | *(Optional)* In **Before launch** add **Run program** → `src/main/java/ch/hevs/utils/serverHSQLDB/CreateDB.java` to auto‑start HSQLDB. |

> **Quick start** : click the green **Run** arrow twice. The first click boots HSQLDB; the second starts WildFly and deploys the WAR.

---

#### Migrating from Eclipse (legacy path)

1. In **Eclipse**, create a `.gitignore` at the project root to exclude workspace files.
2. Push the project to an empty GitHub or GitLab repository.
3. Follow the IntelliJ import steps above.

---

## 🔐 Default Users

Role mapping is handled by WildFly’s *Application Realm*; create the following accounts with `add-user.sh`:

| Username | Roles                | Password |
| -------- | -------------------- | -------- |
| Elias    | Manager              | Elias    |
| Theo     | Player               | Theo     |
| Eric     | Fan                  | Eric     |
| Admin    | Manager, Player, Fan | Admin    |

---

---

## 📘 Documentation & Diagrams

* UML *Class diagram* → [Class diagram.pdf](https://github.com/EliasBorrajo/Football-Manager/blob/master/annexes/Class%20diagram.pdf)
* *Use‑Case diagram* → [UseCase.pdf](https://github.com/EliasBorrajo/Football-Manager/blob/master/annexes/UseCase.pdf)
* Mini‑project brief → [MiniProjectDescriptionSpring2023.pdf](https://github.com/EliasBorrajo/Football-Manager/blob/master/annexes/MiniProjectDescriptionSpring2023.pdf)

---

## ✅ Tests & Validation

* Manual tests

---

## 📌 Success Criteria

| Criterion               | Status | Notes                                                            |
| ----------------------- | ------ | ---------------------------------------------------------------- |
| Java EE mandatory techs | ✅      | JSF 2.2, EJB 3.2, JPA 2.1 implemented (legacy namespace)         |
| CRUD operations         | ✅      | All entities                                                     |
| Role‑based security     | ✅     | Basic users; should need hashing, but not asked for this project |
| Documentation           | ✅     | Diagrams present                                                 |

---

## 👤 Authors

* **Elias Borrajo** [@EliasBorrajo](https://github.com/EliasBorrajo)
* **Théo Clerc** — theo.clerc\@hes‑so.ch

---

Project realised for the course **635‑1 Java/Jakarta EE** — Instructor: **Prof. Adrien Depeursinge**, HES‑SO Valais‑Wallis.

*The original README has been preserved here for reference.*

<details>
 <summary>
  <h2>
   Original Readme (FR)
  </h2>
 </summary>


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
* Optional - Edit Configurations -> Before launch -> Add new -> Run program -> /utils/serverHSQLDB/createDB.java

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

 
</details>


