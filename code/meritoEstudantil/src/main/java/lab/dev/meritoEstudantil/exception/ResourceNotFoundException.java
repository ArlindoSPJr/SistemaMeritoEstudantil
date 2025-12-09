package lab.dev.meritoEstudantil.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no banco de dados.
 * Resulta em resposta HTTP 404 (Not Found).
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado(a) com ID: %d", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(String.format("%s não encontrado(a) com %s: %s", resourceName, field, value));
    }
}
