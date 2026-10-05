package dut.pd3.practics.practic_2.FinanceAnalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private String date;
    private double amount;
    private String description;
}
