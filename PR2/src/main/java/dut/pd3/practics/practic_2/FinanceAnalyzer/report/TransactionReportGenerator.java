package dut.pd3.practics.practic_2.FinanceAnalyzer.report;

import dut.pd3.practics.practic_2.FinanceAnalyzer.model.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionReportGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printPeriodExpenseReport(String label, Transaction transaction) {
        if (transaction != null) {
            System.out.println(label + ": " + transaction.getDescription() + " (" + transaction.getDate() + ") - " + transaction.getAmount() + " грн");
        }
    }

    public static void printCategoryAndMonthlyReport(List<Transaction> transactions) {
        printCategoryAndMonthlyReport(transactions, 1000.0);
    }

    public static void printCategoryAndMonthlyReport(List<Transaction> transactions, double scalePerStar) {
        System.out.println("\n==================================================");
        System.out.println("      ЗВІТ ПО ВИТРАТАХ (ВІЗУАЛІЗАЦІЯ '***')");
        System.out.println("      (1 символ '*' = " + (int) scalePerStar + " грн)");
        System.out.println("==================================================");

        Map<String, Double> categoryExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        LinkedHashMap::new,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))
                ));

        System.out.println("\n--- ВИТРАТИ ПО КАТЕГОРІЯХ ---");
        categoryExpenses.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    String category = entry.getKey();
                    double total = entry.getValue();
                    int stars = (int) Math.round(total / scalePerStar);
                    String bar = "*".repeat(Math.max(1, stars));
                    System.out.printf("%-22s | %10.2f грн | %s%n", category, total, bar);
                });

        Map<String, Double> monthlyExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.getDate(), DATE_FORMATTER).format(MONTH_YEAR_FORMATTER),
                        LinkedHashMap::new,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))
                ));

        System.out.println("\n--- ВИТРАТИ ПО МІСЯЦЯХ ---");
        monthlyExpenses.entrySet().stream()
                .sorted(Comparator.comparing(e -> YearMonth.parse(e.getKey(), MONTH_YEAR_FORMATTER)))
                .forEach(entry -> {
                    String monthYear = entry.getKey();
                    double total = entry.getValue();
                    int stars = (int) Math.round(total / scalePerStar);
                    String bar = "*".repeat(Math.max(1, stars));
                    System.out.printf("%-22s | %10.2f грн | %s%n", monthYear, total, bar);
                });
        System.out.println("==================================================\n");
    }
}
