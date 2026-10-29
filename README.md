# E-Washing — Plateforme de pressing à domicile

Application web de pressing à domicile : les clients commandent un service (lavage, repassage…), un livreur collecte puis livre le linge, et un administrateur pilote l'activité depuis un tableau de bord.

## Architecture

```
┌──────────────────────┐      ┌──────────────────────────┐      ┌───────────┐
│ Front-end            │ ───▶ │ API REST Spring Boot     │ ───▶ │ MySQL 8   │  données métier
│ JavaScript + Vite    │ HTTP │ Spring Security + JWT    │      └───────────┘
│ (axios, Sass)        │      │ Java 17 · Spring Boot 3  │ ───▶ ┌───────────┐
└──────────────────────┘      └──────────────────────────┘      │ MongoDB   │  journal des commandes
                                                                └───────────┘
```

| Dossier | Contenu |
|---|---|
| `E_Washing/` | API REST Spring Boot (Java 17), Dockerfile multi-étapes, `docker-compose.yml` |
| `TEXTE VISUELLE/` | Front-end JavaScript (Vite) : accueil, services, commande, profil, tableau de bord, gestion des utilisateurs |

## Fonctionnalités

- **Authentification JWT** et rôles `CLIENT`, `LIVREUR` et `ADMINISTRATEUR` (Spring Security)
- **Commandes** : création, suivi, association à un ou plusieurs services, affectation d'un livreur
- **Catalogue de services** et **avis clients**
- **Journal des commandes** stocké dans MongoDB (`LogCommande`)
- **Validations métier personnalisées** : email, robustesse du mot de passe, type d'utilisateur, cohérence des dates de collecte et de livraison

## Stack

Java 17 · Spring Boot 3.4 (Web, Data JPA, Data MongoDB, Security, Validation) · JWT · MySQL 8 · MongoDB · JavaScript · Vite · Sass · axios · Docker · Docker Compose · phpMyAdmin

## Lancer le projet avec Docker

```bash
cd E_Washing
cp .env.example .env      # puis renseignez vos propres valeurs
docker compose up --build
```

- API : http://localhost:8080
- phpMyAdmin : http://localhost:8081

MySQL et MongoDB sont lancés dans des conteneurs : aucune base à installer. Le conteneur de l'API attend que MySQL soit disponible (`wait-for-it.sh`) avant de démarrer.

## Lancer le front-end

```bash
cd "TEXTE VISUELLE"
npm install
npm run dev
```

## Sécurité

Aucun secret n'est versionné : mots de passe, chaîne de connexion MongoDB et autres valeurs sensibles sont lus depuis les variables d'environnement (`.env`, ignoré par Git). Le fichier `.env.example` liste les variables attendues.

---
Auteur : [Yvan Seukou](https://yvanseukou.netlify.app) · [GitHub](https://github.com/YvanSeukou)
