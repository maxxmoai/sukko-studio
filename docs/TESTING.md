# Validation

Java 21, SDK 37; configuration locale dans local.properties ignoré.
Commandes cibles:

```sh
bash gradlew :androidApp:assembleDebug testAndroidHostTest -Pkotlin.incremental=false --max-workers=2
bash gradlew :core:importexport:testAndroidHostTest
bash gradlew detektProjectReport
bash gradlew tasks --all
```

Nouveaux tests: SafeArchiveTest, ResourceMergeTest, IconReferenceMergeTest.
Ils utilisent des archives/fichiers temporaires synthétiques. Les tests historiques de migrations,
suppression des URI et tests instrumentés restent dans leurs source sets.
La présence de ces tests n’est pas une preuve de succès: résultats dans DEVELOPMENT_STATUS.md.

Avant validation 0.1: obtenir les anciens presets batterie et Studio Glass réels; importer deux fois,
fusionner fichiers identiques/différents, vérifier les styles globaux, supprimer preview.png,
refuser ZIP dangereux, puis tester sur appareil les dimensions, permissions et redémarrage.

Validation du 26 novembre 2026: 300 tests hôte réussis, Detekt réussi, APK debug généré.
Le test Android de couleurs se trouve désormais dans androidDeviceTest; aucun appareil connecté,
donc aucune réussite instrumentée revendiquée. Le fournisseur de dates est injectable en fuseau
et les fixtures de scripts utilisent UTC.
