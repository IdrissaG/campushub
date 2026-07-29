package gestion.campushub.common;

import java.time.LocalDateTime;
import java.util.List;

public record ErreurResponse(
    LocalDateTime timestamp,
    int status,
    List<String> erreurs
) {}