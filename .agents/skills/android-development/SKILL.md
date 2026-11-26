---
name: android-development
description: Modifier les fonctionnalités Android natives de Sukko Studio et leurs permissions.
---

# android-development

Le point d’entrée est androidApp; app compose les scènes partagées. Les implémentations Android restent dans androidMain. Lire androidApp/build.gradle.kts et le plugin de build-logic avant de toucher aux SDK (min 31, compile/target 37 à la création du fork). Ne pas rendre Play Services obligatoire; traiter les permissions refusées et révoquées. Conserver namespace et discriminants sérialisés historiques même si applicationId change.

Lire AGENTS.md et docs/DEVELOPMENT_STATUS.md pour les contraintes et vérifications actuelles.
