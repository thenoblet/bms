package gtpbms.bms.exception;

public class InsufficientFundsException extends IllegalArgumentException {
  public InsufficientFundsException(String message) {
    super(message);
  }

  public InsufficientFundsException(String message, Throwable cause) {
    super(message, cause);
  }
}
