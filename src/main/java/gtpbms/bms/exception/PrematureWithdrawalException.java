package gtpbms.bms.exception;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PrematureWithdrawalException extends UnsupportedOperationException {
  public PrematureWithdrawalException(LocalDate maturityDate) {
    super(String.format("Withdrawal not allowed before maturity date: %s", maturityDate));
  }

  public PrematureWithdrawalException(LocalDate currentDate, LocalDate maturityDate) {
    super(String.format(
        "Withdrawal blocked! %d days remaining until maturity (due: %s)",
        ChronoUnit.DAYS.between(currentDate, maturityDate),
        maturityDate));
  }

}
