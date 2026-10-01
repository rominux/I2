# Groupe I-2

## Membres

- Romain LEFEBVRE — IHM (interface JavaFX, contrôleur)
- Mylan GEORGE — Modèle (DataPoint, Dataset, patron Observateur, I/O)
- Enzo POUGHEON — Stratégie (interface Classifier et algorithmes de classification)

## Evaluation UE1

### Fonctionnalités

Fonctionnalités prévues pour le Jalon 1 :

- **Chargement de données CSV** : importation générique d'un jeu de données à partir
  d'un fichier CSV (dernière colonne = catégorie). *(Mylan)*
- **Visualisation en nuage de points** : projection des données sur deux attributs
  choisis interactivement, plusieurs vues simultanées possibles, une couleur par
  catégorie. *(Romain)*
- **Ajout d'un point inconnu** : saisie manuelle de ses coordonnées puis classification
  aléatoire et affichage distinct du jeu de données initial. *(Romain / Enzo)*
- **Classification aléatoire** : `RandomClassifier` tirant une catégorie parmi celles
  présentes dans le jeu d'entraînement. *(Enzo)*
- **Rechargement d'un jeu de données** : les points ajoutés précédemment ne sont pas
  conservés. *(Mylan / Romain)*

### UML Jalon 1

Architecture MVC : le modèle (`Dataset`, `DataPoint`) notifie les vues
(`ScatterPlotView`) via le patron Observateur (`ModelListener`). Les algorithmes de
classification suivent le patron Stratégie (`Classifier`).

### UML Jalon 2

## Evaluation UE2

### Analyse des données

### Implémentation de k-NN

### Robustesse de vos modèles
