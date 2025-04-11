package gtpbms.bms.exception;

public class InvalidDepositAmountException extends IllegalArgumentException {
  public InvalidDepositAmountException(String message) {
    super(message);
  }

  public InvalidDepositAmountException(String message, Throwable cause) {
    super(message, cause);
  }
}
