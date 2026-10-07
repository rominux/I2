# SAÉ 3.02 — Outil de classification supervisée

## Présentation du projet
* **Groupe** : I-2
* **Formation** : BUT Informatique - Semestre 3 (IUT de Lille)

### Membres de l'équipe et rôles
* **Romain LEFEBVRE** : Développement de l'IHM JavaFX
* **Enzo POUGHEON** :
* **Titouan RASSET** :
* **Mylan GEORGE** :

---

## But de la SAÉ
Cette SAÉ a pour objectif de concevoir et développer une application complète de manipulation, visualisation et classification de données :
* **Chargement de données** : importation générique de jeux de données depuis des fichiers CSV.
* **Visualisation 2D** : affichage des données sous forme de nuage de points avec projection personnalisée sur deux axes numériques choisis dynamiquement par l'utilisateur, gestion simultanée de plusieurs vues indépendantes et différenciation visuelle des catégories.
* **Classification** : saisie manuelle de nouveaux points à classifier, intégration d'une classification aléatoire pour le **Jalon 1**, puis implémentation de l'algorithme des $k$ plus proches voisins (**k-NN**) pour le **Jalon 2**.

---

## Conception

### Diagramme de cas d'utilisation
Ce diagramme détaille les fonctionnalités offertes à l'utilisateur.

![Diagramme de cas d'utilisation](res/diagrammes%20de%20cas%20d'utilisation.webp)

---

### Diagramme de classes
Le diagramme de classes illustre l'architecture logicielle retenue.

![Diagramme de classes](res/diagramme%20de%20classe.webp)