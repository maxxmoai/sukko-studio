---
name: kotlin-development
description: Modifier la logique Kotlin multiplateforme de Sukko Studio.
---

# kotlin-development

Version centralisée dans gradle/libs.versions.toml. Garder java.io, Context et ContentResolver dans androidMain; commonMain est partagé avec wasmJs. Les modèles sérialisés de core/data doivent garder leurs noms de types et valeurs par défaut. Les IO suspendues utilisent Dispatchers.IO; fermer les flux avec use et préserver l’annulation des coroutines.

Lire AGENTS.md et docs/DEVELOPMENT_STATUS.md pour les contraintes et vérifications actuelles.
