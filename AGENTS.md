# Sukko Studio — règles du dépôt

## Périmètre et architecture
Fork de sadellie/sukko; origin = maxxmoai/sukko-studio. Ne jamais pousser vers upstream.
Priorité P0: compilation, imports, sécurité, anciens presets. Les P1/P2/P3 restent incrémentaux.
Lire docs/DEVELOPMENT_STATUS.md et docs/ARCHITECTURE.md avant modification.
Conserver Kotlin, Compose Multiplatform, Metro, Room et le moteur existant.
Les skills spécialisés sont dans .agents/skills; charger ceux pertinents au travail.

## Avant chaque modification
Inspecter git status, les instructions applicables et les appelants/tests du code concerné.
Vérifier les versions dans gradle/libs.versions.toml et build-logic. Ne pas ajouter de dépendance
sans besoin démontré. Établir reproduction et résultat attendu pour les bugs.
Ne pas écraser les modifications utilisateur ni réécrire l’historique.

## Conventions
Suivre les sources voisines (indentation Kotlin de deux espaces). Ne pas renommer les packages
sérialisés historiques. commonMain doit rester multiplateforme, APIs Android dans androidMain.
Centraliser les chaînes UI dans les ressources Compose, français et anglais.
Fermer les flux, utiliser Dispatchers.IO, conserver l’annulation des coroutines.
Les valeurs indisponibles sont explicites, jamais silencieusement remplacées par zéro.

## Compilation et tests
Java 21; wrapper Gradle 9.4.1; SDK compile/target 37, min 31 au démarrage du fork.
Configurer SDK via ANDROID_HOME ou local.properties ignoré; ne pas committer de chemin local.
- bash gradlew :androidApp:assembleDebug
- bash gradlew :core:importexport:testAndroidHostTest
- bash gradlew tasks --all (pour confirmer les tâches disponibles)
- bash gradlew detektProjectReport
Tests communs/hôte/device séparés. Les tests instrumentés nécessitent un appareil/émulateur.
Vérifier les tests ciblés puis la compilation. Rapporter commandes et résultats exacts;
ne jamais présenter un test non exécuté comme réussi.

## Sécurité et compatibilité
Valider chemins ZIP et références JSON; borner extraction; préserver les ressources installées.
Pas de compte, télémétrie ni Google Play Services obligatoires. Permissions facultatives et graduelles.
Pas de root, contournement Android, API cachée ni secret dans Git. Pas de release signée sans accord.
Conserver Android 12+, anciens presets et noms sérialisés. Priorité Pixel/GrapheneOS, grille 5 × 6.
Tester les dimensions en dp, pas seulement le nombre de cellules; ne pas inventer une compatibilité.

## Design et livraison
Style minimaliste Studio Glass, lisible en sombre; réutiliser Material 3 et les thèmes existants.
Respecter TalkBack, contraste et agrandissement. Ne pas promettre le flou du fond d’écran
ou une actualisation permanente à la seconde. Pas de ressources Apple propriétaires.
Conserver licence GPL et crédits, documenter les changements et limitations.
Mettre à jour CHANGELOG et DEVELOPMENT_STATUS avec ce qui est implémenté/testé/restant.
