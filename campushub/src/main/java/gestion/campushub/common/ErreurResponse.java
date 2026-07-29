package gestion.campushub.common;

import java.time.LocalDateTime;
import java.util.List;

public record ErreurResponse(
    LocalDateTime timestamp,
    int status,
    List<String> erreurs
) {}

// Le rôle de record ErreurResponse est de représenter les informations d'une réponse d'erreur qui seront renvoyées au client. Il contient un horodatage indiquant le moment de l'erreur, un code de statut HTTP et une liste de messages d'erreur détaillant les problèmes rencontrés.

// en termes simples, il s'agit d'une structure de données qui permet de communiquer efficacement les erreurs survenues lors du traitement des requêtes, en fournissant des informations claires et utiles pour le débogage et la compréhension des problèmes par le client.


// c'est utile pour fournir des informations claires et structurées sur les erreurs qui se produisent lors du traitement des requêtes, facilitant ainsi le débogage et la compréhension des problèmes par le client.