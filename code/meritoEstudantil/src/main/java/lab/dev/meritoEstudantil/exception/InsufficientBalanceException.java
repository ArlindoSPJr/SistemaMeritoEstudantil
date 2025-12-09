package lab.dev.meritoEstudantil.exception;

/**
 * Exceção lançada quando há saldo insuficiente de moedas para realizar uma operação.
 * Resulta em resposta HTTP 400 (Bad Request).
 */
public class InsufficientBalanceException extends BusinessRuleException {
    
    private final double currentBalance;
    private final double requiredAmount;

    public InsufficientBalanceException(double currentBalance, double requiredAmount) {
        super(String.format("Saldo insuficiente. Saldo atual: %.2f, valor necessário: %.2f", 
              currentBalance, requiredAmount));
        this.currentBalance = currentBalance;
        this.requiredAmount = requiredAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getRequiredAmount() {
        return requiredAmount;
    }
}
