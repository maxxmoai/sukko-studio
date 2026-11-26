# Limites connues

La version upstream reste expérimentale. Aucune validation appareil ni anciens presets utilisateur
n’est implicite dans la compilation ou les tests synthétiques.

- Fusion: politique automatique conserver les deux/réutiliser l’identique; écran de choix
  réutiliser une icône différente ou remplacer après consentement encore à développer.
- Pas de transaction atomique globale entre Room, packs, polices et presets; des ressources
  peuvent rester après un échec tardif. L’insertion du preset annule sa ligne si sa copie d’aperçu échoue.
- Le cache d’import partagé historique nécessite encore une gestion de sessions concurrentes.
- Limites ZIP: 2048 entrées, 16 Mio par fichier, 64 Mio extraits au total; les gros presets sont refusés.
- Extraction bornée ne signifie pas validation complète des SVG ou isolation des scripts.
- Les migrations historiques ne garantissent pas tous les anciens presets; fixtures utilisateur requises.
- Accessibilité complète, français complet, nouveaux fournisseurs, thèmes et éditeur V2 restent planifiés.
- Le vrai flou du fond derrière un widget et les mises à jour à la seconde ne sont pas garantis.
