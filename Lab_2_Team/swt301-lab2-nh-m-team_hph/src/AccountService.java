import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * Dịch vụ quản lý tài khoản ngân hàng.
 * Hỗ trợ: tạo tài khoản, nạp/rút tiền, chuyển khoản, tra cứu.
 */
public class AccountService {
    private final Map<String, BankAccount> accounts = new HashMap<>();
    /** Tạo tài khoản mới. Throws IllegalArgumentException nếu accountId đã tồn tại.
     */
    public BankAccount createAccount(String accountId, String ownerName,
                                     BigDecimal initialBalance) {
        if (accounts.containsKey(accountId)) {
            throw new IllegalArgumentException("Account already exists: " +
                    accountId);
        }
        BankAccount account = new BankAccount(accountId, ownerName, initialBalance);
        accounts.put(accountId, account);
        return account;
    }
    /** Tìm tài khoản theo ID. Trả về Optional. */
    public Optional<BankAccount> findAccount(String accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }
    /** Nạp tiền. Throws AccountNotFoundException nếu không tồn tại. */
    public void deposit(String accountId, BigDecimal amount) {
        BankAccount account = getAccountOrThrow(accountId);
        account.deposit(amount);
    }
    /** Rút tiền. Throws AccountNotFoundException / InsufficientFundsException. */
    public void withdraw(String accountId, BigDecimal amount) {
        BankAccount account = getAccountOrThrow(accountId);
        account.withdraw(amount);
    }
    /**
     * Chuyển tiền giữa 2 tài khoản.
     * Throws IllegalArgumentException nếu chuyển cho chính mình.
     */
    public void transfer(String fromId, String toId, BigDecimal amount) {
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        BankAccount from = getAccountOrThrow(fromId);
        BankAccount to = getAccountOrThrow(toId);
        from.withdraw(amount);
        to.deposit(amount);
    }
    /** Trả về số dư tài khoản. */
    public BigDecimal getBalance(String accountId) {
        return getAccountOrThrow(accountId).getBalance();
    }
    private BankAccount getAccountOrThrow(String accountId) {
        return findAccount(accountId).orElseThrow(() ->
                new AccountNotFoundException("Account not found: " + accountId));
    }
}