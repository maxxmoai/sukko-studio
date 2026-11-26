---
name: software-architecture
description: Faire évoluer les frontières de modules et fournisseurs de données Sukko.
---

# software-architecture

Lire docs/ARCHITECTURE.md et les dépendances des modules concernés. Conserver les modèles dans core/data, Room dans core/database, l’import Android dans core/importexport et le rendu dans core/unglance. Ajouter les fournisseurs avec disponibilité, permissions, cache et horodatage explicites. Une donnée inconnue ne vaut pas zéro. Éviter une réécriture du moteur sans mesure démontrant sa nécessité.

Lire AGENTS.md et docs/DEVELOPMENT_STATUS.md pour les contraintes et vérifications actuelles.
