import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * Đại diện cho một tài khoản ngân hàng.
 * Mọi giá trị tiền dùng BigDecimal để tránh lỗi floating-point.
 */
public class BankAccount {
    private final String accountId;
    private final String ownerName;
    private BigDecimal balance;
/**
 * Tạo tài khoản mới.
 * @param accountId  mã tài khoản, không được null/rỗng
 * @param ownerName  tên chủ tài khoản, không được null/rỗng
 * @param initialBalance số dư ban đầu, phải >= 0
 */
    public BankAccount(String accountId, String ownerName, BigDecimal
            initialBalance) {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("accountId must not be null or blank");
        }
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("ownerName must not be null or blank");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalArgumentException("initialBalance must be >= 0");
        }
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialBalance.setScale(2, RoundingMode.HALF_UP);
    }

    /** Nạp tiền vào tài khoản. amount phải > 0 */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    /** Rút tiền khỏi tài khoản. amount phải > 0 và <= balance */
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new InsufficientFundsException (
                    "Insufficient funds: balance=" + this.balance + ", requested=" +
                            amount
            );
        }
        this.balance = this.balance.subtract(amount).setScale(2,
                RoundingMode.HALF_UP);
    }

    public String getAccountId()   { return accountId; }
    public String getOwnerName()   { return ownerName; }
    public BigDecimal getBalance() { return balance; }
}