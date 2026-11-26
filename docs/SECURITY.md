# Sécurité — périmètre P0

Les archives et leur JSON sont non fiables. SafeArchive vérifie chemins canoniques, chemins absolus,
segments parents, séparateurs Windows, doublons et quotas sur les octets réellement lus.
Extraction uniquement dans un dossier privé vide, supprimé si elle échoue. Les références JSON
aux fichiers sont vérifiées avant le choix d’import. Les doublons ne remplacent pas les fichiers installés.

Référence: https://developer.android.com/privacy-and-security/risks/zip-path-traversal

Audit restant: validation structurelle SVG/XML (entités, références distantes), scripts et limites
CPU/mémoire, erreurs utilisateur traduites, transaction globale et sessions concurrentes.
Ne pas qualifier cette version de totalement sécurisée sur la seule base des tests ZIP.
Aucun secret, données privées ou clé de production à committer. Données de santé locales par défaut.
