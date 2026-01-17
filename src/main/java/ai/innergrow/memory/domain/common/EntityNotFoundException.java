package ai.innergrow.memory.domain.common;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message, 404);
    }
}
