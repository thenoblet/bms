package gtpbms.bms.exception;

public class InvalidInitialBalanceException extends IllegalArgumentException {
  public InvalidInitialBalanceException(String message) {
    super(message);
  }

  public InvalidInitialBalanceException(String message, Throwable status) {
    super(message, status);
  }
}
