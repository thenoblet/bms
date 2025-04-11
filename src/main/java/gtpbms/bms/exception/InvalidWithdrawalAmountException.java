package gtpbms.bms.exception;

public class InvalidWithdrawalAmountException extends IllegalArgumentException {
  public InvalidWithdrawalAmountException(String message) {
    super(message);
  }

  public InvalidWithdrawalAmountException(String message, Throwable cause) {
    super(message, cause);
  }
}
