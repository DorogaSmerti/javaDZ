package dut.pd3.practics.practic_2.FinanceAnalyzer;

import dut.pd3.practics.practic_2.FinanceAnalyzer.model.Transaction;
import dut.pd3.practics.practic_2.FinanceAnalyzer.reader.TransactionCSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionCSVReaderTest {

    @Test
    public void testReadTransactionsFromCSV(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("test.csv");
        Files.write(tempFile, List.of(
                "05-12-2023,-450,Сільпо",
                "07-12-2023,-120,Аптека",
                "10-12-2023,8000,Зарплата"
        ));

        List<Transaction> transactions = TransactionCSVReader.readTransactions(tempFile.toUri().toURL().toString());

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(3, transactions.size());

        Transaction first = transactions.get(0);
        Assertions.assertEquals("05-12-2023", first.getDate());
        Assertions.assertEquals(-450.0, first.getAmount());
        Assertions.assertEquals("Сільпо", first.getDescription());
    }
}
