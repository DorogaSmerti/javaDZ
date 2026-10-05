package dut.pd3.practics.practic_2.FinanceAnalyzer;

import dut.pd3.practics.practic_2.FinanceAnalyzer.analyzer.TransactionAnalyzer;
import dut.pd3.practics.practic_2.FinanceAnalyzer.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result);
    }

    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb);
        Assertions.assertEquals(1, countMar);
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-12-2023", -100.0, "Кава"),
                new Transaction("02-12-2023", -5000.0, "Оренда"),
                new Transaction("03-12-2023", 10000.0, "Зарплата"),
                new Transaction("04-12-2023", -1200.0, "Продукти"),
                new Transaction("05-12-2023", -300.0, "Кіно"),
                new Transaction("06-12-2023", -2500.0, "Комунальні"),
                new Transaction("07-12-2023", -800.0, "Бензин"),
                new Transaction("08-12-2023", -450.0, "Аптека"),
                new Transaction("09-12-2023", -900.0, "Одяг"),
                new Transaction("10-12-2023", -150.0, "Таксі"),
                new Transaction("11-12-2023", -3500.0, "Техніка"),
                new Transaction("12-12-2023", -50.0, "Морозиво")
        );

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, topExpenses.size());
        Assertions.assertEquals("Оренда", topExpenses.get(0).getDescription());
        Assertions.assertEquals(-5000.0, topExpenses.get(0).getAmount());
        Assertions.assertEquals("Техніка", topExpenses.get(1).getDescription());
        Assertions.assertEquals(-3500.0, topExpenses.get(1).getAmount());
    }

    @Test
    public void testFindLargestAndSmallestExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -100.0, "Кава"),
                new Transaction("15-01-2024", -3000.0, "Ремонт"),
                new Transaction("20-01-2024", -500.0, "Супермаркет"),
                new Transaction("10-02-2024", -10000.0, "Відпустка")
        );

        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        Transaction largestInJan = TransactionAnalyzer.findLargestExpense(transactions, start, end);
        Transaction smallestInJan = TransactionAnalyzer.findSmallestExpense(transactions, start, end);

        Assertions.assertNotNull(largestInJan);
        Assertions.assertEquals("Ремонт", largestInJan.getDescription());
        Assertions.assertEquals(-3000.0, largestInJan.getAmount());

        Assertions.assertNotNull(smallestInJan);
        Assertions.assertEquals("Кава", smallestInJan.getDescription());
        Assertions.assertEquals(-100.0, smallestInJan.getAmount());
    }
}
