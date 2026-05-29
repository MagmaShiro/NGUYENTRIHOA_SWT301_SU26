import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BankAccount Tests")
class BankAccountTest {

    private BankAccount defaultAccount;

    @BeforeEach
    void setUp() {
        defaultAccount = new BankAccount("ACC001", "Nguyen Van A", new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Create BankAccount successfully with valid data")
    void testConstructorSuccess() {
        assertNotNull(defaultAccount);
        assertEquals("ACC001", defaultAccount.getAccountId());
        assertEquals("Nguyen Van A", defaultAccount.getOwnerName());
        assertEquals(new BigDecimal("100.00"), defaultAccount.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            ", Nguyen Van A, 100.00, account Id must not be null or blank",
            "'', Nguyen Van A, 100.00, account Id must not be null or blank",
            "'   ', Nguyen Van A, 100.00, account Id must not be null or blank",
            "ACC001,, 100.00, ownerName must not be null or blank",
            "ACC001, '', 100.00, ownerName must not be null or blank",
            "ACC001, 'Nguyen Van A', -0.01, initialBalance must be >= 0"
    })
    @DisplayName("Create BankAccount fails with invalid input data")
    void testConstructorInvalidInputs(String accountId, String ownerName, BigDecimal initialBalance, String expectedErrorMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount(accountId, ownerName, initialBalance);
        });
        assertTrue(exception.getMessage().contains(expectedErrorMessage));
    }

    @ParameterizedTest
    @CsvSource({
            "0.01, 100.01",
            "50.00, 150.00",
            "999.99, 1099.99"
    })
    @DisplayName("Deposit successfully with positive amounts")
    void testDepositSuccess(String depositAmount, String expectedBalance) {
        defaultAccount.deposit(new BigDecimal(depositAmount));
        assertEquals(new BigDecimal(expectedBalance), defaultAccount.getBalance());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.00", "-0.01", "-50.00"})
    @DisplayName("Deposit fails with zero or negative amounts")
    void testDepositInvalidAmount(String depositAmount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultAccount.deposit(new BigDecimal(depositAmount));
        });
        assertTrue(exception.getMessage().contains("Deposit amount must be positive"));
    }

    @Test
    @DisplayName("Deposit fails when amount is null")
    void testDepositNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultAccount.deposit(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "0.01, 99.99",
            "50.00, 50.00",
            "100.00, 0.00"
    })
    @DisplayName("Withdraw successfully with valid amounts")
    void testWithdrawSuccess(String withdrawAmount, String expectedBalance) {
        defaultAccount.withdraw(new BigDecimal(withdrawAmount));
        assertEquals(new BigDecimal(expectedBalance), defaultAccount.getBalance());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.00", "-0.01", "-20.00"})
    @DisplayName("Withdraw fails with zero or negative amounts")
    void testWithdrawInvalidAmount(String withdrawAmount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultAccount.withdraw(new BigDecimal(withdrawAmount));
        });
        assertTrue(exception.getMessage().contains("Withdrawal amount must be positive"));
    }

    @Test
    @DisplayName("Withdraw fails when amount is null")
    void testWithdrawNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            defaultAccount.withdraw(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "100.01",
            "150.00"
    })
    @DisplayName("Withdraw throws InsufficientFundsException when amount exceeds balance")
    void testWithdrawInsufficientFunds(String withdrawAmount) {
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            defaultAccount.withdraw(new BigDecimal(withdrawAmount));
        });
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }
}