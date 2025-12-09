package lab.dev.meritoEstudantil.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 * Resulta em resposta HTTP 400 (Bad Request).
 */
public class BusinessRuleException extends RuntimeException {
    
    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
