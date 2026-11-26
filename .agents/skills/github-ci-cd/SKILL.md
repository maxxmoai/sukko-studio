---
name: github-ci-cd
description: Maintenir la CI Android et les APK de développement Sukko Studio.
---

# github-ci-cd

Compiler androidApp avec Java 21 et SDK 37; exécuter tests hôte et analyse statique avant upload d’APK debug. Permissions contents: read par défaut, pas de secrets sur pull_request, jamais pull_request_target pour exécuter du code proposé. Distinguer workflow écrit, run déclenché et run réussi. Les releases signées exigent une autorisation explicite et des clés hors dépôt. Utiliser gh-fix-ci pour diagnostiquer les checks GitHub.

Lire AGENTS.md et docs/DEVELOPMENT_STATUS.md pour les contraintes et vérifications actuelles.
