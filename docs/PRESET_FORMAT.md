# Format des presets

Un .sukko est un ZIP; le schéma courant est 1 (core/common/Const.kt).
- widget_data_preset.json: modèle WidgetDataPreset.Custom sérialisé par kotlinx.serialization.
- schema_version.txt: version entière du schéma.
- preview.png: facultatif; import sans copie d’aperçu s’il est absent.
- iconPacks/<id>/<nom.svg>: ressources des calques image.
- fonts/<nom>: ressources typographiques (voir FontFile.Custom.DIR_PATH).

Les discriminants JSON restent ceux de io.github.sadellie.sukko malgré l’identifiant Android distinct.
L’import rejette chemins dangereux, noms de ressources traversants, ressources référencées absentes
et doublons d’identifiants de calques. Les versions futures sont refusées par le mécanisme existant.

Fusion de packs: SHA-256 des fichiers. Même nom/contenu identique: réutilisation; même nom/contenu
différent: suffixe _1, _2… et remappage des calques. Un second import réutilise aussi la copie suffixée
identique. Même principe pour les polices locales et les styles globaux. Aucun remplacement silencieux.
Les polices communes aux calques/globaux ne sont exportées qu’une fois.
