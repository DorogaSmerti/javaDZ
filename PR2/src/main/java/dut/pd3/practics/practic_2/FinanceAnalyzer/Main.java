package dut.pd3.practics.practic_2.FinanceAnalyzer;

import dut.pd3.practics.practic_2.FinanceAnalyzer.analyzer.TransactionAnalyzer;
import dut.pd3.practics.practic_2.FinanceAnalyzer.model.Transaction;
import dut.pd3.practics.practic_2.FinanceAnalyzer.reader.TransactionCSVReader;
import dut.pd3.practics.practic_2.FinanceAnalyzer.report.TransactionReportGenerator;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        if (transactions.isEmpty()) {
            transactions = getSampleTransactions();
        }

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        Transaction largestExp = TransactionAnalyzer.findLargestExpense(transactions, startDate, endDate);
        Transaction smallestExp = TransactionAnalyzer.findSmallestExpense(transactions, startDate, endDate);

        TransactionReportGenerator.printPeriodExpenseReport("Найбільша витрата за грудень 2023", largestExp);
        TransactionReportGenerator.printPeriodExpenseReport("Найменша витрата за грудень 2023", smallestExp);

        TransactionReportGenerator.printCategoryAndMonthlyReport(transactions);
    }

    private static List<Transaction> getSampleTransactions() {
        return List.of(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("07-12-2023", -120.0, "Аптека"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"),
                new Transaction("12-12-2023", 1500.0, "Авторські винагороди"),
                new Transaction("14-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("16-12-2023", 2500.0, "Фріланс"),
                new Transaction("18-12-2023", -200.0, "Кав'ярня"),
                new Transaction("20-12-2023", -750.0, "Подарунки"),
                new Transaction("22-12-2023", -600.0, "Бензин"),
                new Transaction("24-12-2023", -950.0, "Ресторан"),
                new Transaction("26-12-2023", 3000.0, "Премія"),
                new Transaction("28-12-2023", -400.0, "Кінотеатр"),
                new Transaction("30-12-2023", -1200.0, "Новорічні прикраси"),
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("10-01-2024", 9500.0, "Зарплата"),
                new Transaction("15-01-2024", -4500.0, "Оренда квартири")
        );
    }
}
