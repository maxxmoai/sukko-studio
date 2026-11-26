# Architecture observée

Base upstream: 8c359be906e55d2af78d8bd93c052fb45bb7b060 (experimental 4).
26 modules déclarés dans settings.gradle.kts; build-logic fournit les plugins de convention.

| Modules | Responsabilité |
| --- | --- |
| androidApp | Application Android, manifest, APK, identifiant installable |
| app | Assemblage Compose, scènes et injection |
| core/common | Utilitaires, fichiers, constantes dont schéma des presets |
| core/data | Modèles sérialisés, repositories, évaluateurs et moteur de scripts |
| core/database | Room, DAO, schémas Android |
| core/fontfiles, core/iconfiles | Ressources typographiques et icônes |
| core/importexport | Export ZIP, import des presets, migration du JSON |
| core/remote | Accès distants existants |
| core/designsystem, core/ui | Ressources et composants partagés |
| core/routes, core/routes-ui | Navigation |
| core/unglance | Infrastructure de rendu des widgets |
| feature/editor | Éditeur et propriétés des calques |
| feature/widget | Host Android, rendu et abonnements aux actualisations |
| feature/home, feature/widgetinfo | Accueil et informations |
| feature/importpreset, feature/saveaspreset, feature/presetselector | Parcours des presets |
| feature/iconpackeditor, feature/fontseditor | Gestion des ressources |
| feature/settings | Paramètres |
| material-symbols, themmo | Bibliothèque d’icônes et thème |

Kotlin 2.3.21, Compose Multiplatform 1.11.0, Material 3 1.11.0-alpha07,
Room 2.8.4, Metro 1.1.1, AGP 9.2.1, Gradle 9.4.1. Source de vérité: catalogue Gradle.
Android est la cible livrable; wasmJs existe dans les conventions mais n’est pas validé ici.
Ne pas renommer les packages sérialisés: Json utilise leurs noms dans les discriminants de types.

Import observé: décompression cache → lecture schéma/JSON → choix des packs → copie ressources → insertion Room.
Limite: Room et le système de fichiers ne forment pas actuellement une transaction commune.
