import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            run(input, System.out);
        }
    }

    static void run(Scanner input, PrintStream output) {
        Calculator calculator = new Calculator();

        try {
            output.println("Введіть перше число:");
            double a = readNumber(input);
            output.println("Введіть друге число:");
            double b = readNumber(input);
            output.println("Введіть операцію (+, -, *, /):");
            String operation = input.hasNext() ? input.next() : "";

            double result;
            switch (operation) {
                case "+": result = calculator.add(a, b); break;
                case "-": result = calculator.subtract(a, b); break;
                case "*": result = calculator.multiply(a, b); break;
                case "/": result = calculator.divide(a, b); break;
                default: throw new InvalidInputException("Невідома операція.");
            }
            output.println("Результат: " + result);
        } catch (NumberFormatException e) {
            output.println("Помилка: потрібно ввести число.");
        } catch (ArithmeticException | InvalidInputException e) {
            output.println("Помилка: " + e.getMessage());
        } finally {
            output.println("Обробку запиту завершено.");
        }
    }

    private static double readNumber(Scanner input) {
        if (!input.hasNext()) {
            throw new NumberFormatException();
        }
        return Double.parseDouble(input.next().replace(',', '.'));
    }
}
