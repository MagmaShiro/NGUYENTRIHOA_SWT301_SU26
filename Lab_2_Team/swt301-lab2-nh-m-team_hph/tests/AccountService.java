import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AccountService Tests")
class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    @DisplayName("Create account successfully with valid data")
    void testCreateAccountSuccess() {
        BankAccount account = accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountId());
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    @DisplayName("Create account fails when accountId already exists")
    void testCreateAccountDuplicateId() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount("ACC001", "Tran Thi B", new BigDecimal("50.00"));
        });
        assertTrue(exception.getMessage().contains("Account already exists"));
    }

    @Test
    @DisplayName("Find account returns Optional with data when ID exists")
    void testFindAccountExist() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        Optional<BankAccount> result = accountService.findAccount("ACC001");
        assertTrue(result.isPresent());
        assertEquals("Nguyen Van A", result.get().getOwnerName());
    }

    @Test
    @DisplayName("Find account returns empty Optional when ID does not exist")
    void testFindAccountNotExist() {
        Optional<BankAccount> result = accountService.findAccount("INVALID_ID");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deposit successfully into an existing account")
    void testDepositSuccess() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        accountService.deposit("ACC001", new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), accountService.getBalance("ACC001"));
    }

    @Test
    @DisplayName("Deposit fails when account is not found")
    void testDepositAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.deposit("INVALID_ID", new BigDecimal("50.00"));
        });
    }

    @Test
    @DisplayName("Withdraw successfully from an existing account")
    void testWithdrawSuccess() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        accountService.withdraw("ACC001", new BigDecimal("40.00"));
        assertEquals(new BigDecimal("60.00"), accountService.getBalance("ACC001"));
    }

    @Test
    @DisplayName("Withdraw fails when account balance is insufficient")
    void testWithdrawInsufficientFunds() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw("ACC001", new BigDecimal("150.00"));
        });
    }

    @Test
    @DisplayName("Transfer money successfully between two accounts")
    void testTransferSuccess() {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("500.00"));
        accountService.createAccount("ACC002", "Tran Thi B", new BigDecimal("200.00"));
        accountService.transfer("ACC001", "ACC002", new BigDecimal("150.00"));
        assertEquals(new BigDecimal("350.00"), accountService.getBalance("ACC001"));
        assertEquals(new BigDecimal("350.00"), accountService.getBalance("ACC002"));
    }

    @ParameterizedTest
    @CsvSource({
            "ACC001, ACC001, 100.00, Cannot transfer to the same account",
            "ACC001, INVALID_ID, 50.00, Account not found",
            "INVALID_ID, ACC001, 50.00, Account not found"
    })
    @DisplayName("Transfer fails under invalid scenarios")
    void testTransferInvalidScenarios(String fromId, String toId, BigDecimal amount, String expectedErrorMessage) {
        accountService.createAccount("ACC001", "Nguyen Van A", new BigDecimal("500.00"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.transfer(fromId, toId, amount);
        });
        assertTrue(exception.getMessage().contains(expectedErrorMessage));
    }
}