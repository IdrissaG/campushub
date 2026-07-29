# Variables d'environnement prévues pour la prod

- DB_URL - url de connexion Postgres managée
- DB_USERNAME
- DB_PASSWORD
- JWT_SECRET - clé de signature des tokens
- SPRING_PROFILES_ACTIVE=prod

# Choix de la plateforme Cloud

Nous avons choisi d'utiliser RENDER pour notre futur déploiement. C'est une plateforme Cloud très accessible, avec un niveau gratuit, qui permet de se connecter directement à GitHub et de lier facilement une base de données PostgreSQL managée.