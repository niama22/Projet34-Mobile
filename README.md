# Dog Images App 🐶

**Dog Images App** est une application Android qui permet aux utilisateurs de parcourir des images de chiens provenant d'une API, de les ajouter aux favoris, de les partager, et de gérer une liste de favoris localement.

## Fonctionnalités

- Affichage d'une liste d'images de chiens via une API.
- Ajout aux favoris et gestion de la liste des images favorites.
- Partage d'images de chiens avec d'autres applications.
- Pagination des images pour un chargement progressif et fluide.
- Stockage local des favoris pour un accès hors ligne.

## Architecture

Le projet utilise l'architecture **MVVM (Model-View-ViewModel)**, ce qui permet une séparation claire des responsabilités :
- **Model** : Manipulation et stockage des données (Room, API).
- **View** : Interface utilisateur et interaction (RecyclerView).
- **ViewModel** : Gestion de la logique métier et des états.

## Bibliothèques et Technologies Utilisées

- **RecyclerView** : Pour afficher les images des chiens sous forme de liste.
- **Paging3** : Pour le chargement progressif des données via l'API.
- **Room Database** : Pour stocker les images favorites et les données de pagination en local.
- **Retrofit + Moshi** : Pour les appels à l'API et la sérialisation des données JSON.
- **Dagger Hilt** : Pour l'injection de dépendances, facilitant la gestion des composants et la modularité.
- **Coil** : Pour le chargement rapide et le cache des images.

## Auteur

- **Nom** : Votre Nom
- **Email** : votre.email@example.com

## Prérequis

- **Android Studio** Arctic Fox ou une version plus récente
- **Gradle 7.0** ou une version plus récente
- Connexion Internet pour récupérer les images depuis l'API
- Une clé API si nécessaire, à configurer dans `build.gradle` ou un fichier de propriétés sécurisé

## Démo

Une démo de l'application est disponible [ici](https://youtu.be/your-video-link).
