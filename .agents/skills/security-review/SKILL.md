---
name: security-review
description: Auditer les archives, scripts, ressources ou permissions de Sukko.
---

# security-review

Tracer entrée non fiable jusqu’au stockage/rendu. Contrôler chemins ZIP ET noms issus du JSON; borner octets réellement lus et nombre d’entrées. Ne jamais écraser une ressource installée implicitement. Examiner séparément XML/SVG, chargements réseau, scripts et budgets CPU/mémoire: une extraction sûre ne constitue pas une sandbox SVG ou script. Ne pas journaliser les données de santé, calendrier ou secrets.

Lire AGENTS.md et docs/DEVELOPMENT_STATUS.md pour les contraintes et vérifications actuelles.
