package dut.pd3.practics.practic_2.FinanceAnalyzer.analyzer;

import dut.pd3.practics.practic_2.FinanceAnalyzer.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
            String transactionMonthYear = date.format(MONTH_YEAR_FORMATTER);
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .toList();
    }

    public static Transaction findLargestExpense(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                           (date.isEqual(endDate) || date.isBefore(endDate));
                })
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findSmallestExpense(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                           (date.isEqual(endDate) || date.isBefore(endDate));
                })
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }
}
