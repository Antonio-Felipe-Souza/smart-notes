package projeto.smartnotes.domain;

import java.util.UUID;

public record ResourceId(UUID uuid) {
    public ResourceId() {
        this(UUID.randomUUID());
    }
}
