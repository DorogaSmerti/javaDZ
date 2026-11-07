import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CalculatorTest {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        check(calculator.add(2, 3) == 5, "Додавання");
        check(calculator.subtract(2, 3) == -1, "Віднімання");
        check(calculator.multiply(-2, 3) == -6, "Множення");
        check(calculator.divide(5, 2) == 2.5, "Ділення");

        checkException(ArithmeticException.class, () -> calculator.divide(5, 0));
        checkException(InvalidInputException.class, () -> calculator.add(Double.NaN, 1));
        checkInput("abc", "потрібно ввести число");
        checkInput("5 0 /", "На нуль ділити не можна.");
        checkInput("NaN 1 +", "Введіть скінченні числа.");
        checkInput("1,5 2 +", "Результат: 3.5");

        System.out.println("Усі тести пройдено.");
    }

    private static void checkInput(String input, String expected) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner(input); PrintStream output = new PrintStream(buffer)) {
            Main.run(scanner, output);
        }
        String result = buffer.toString();
        check(result.contains(expected), input);
        check(result.contains("Обробку запиту завершено."), "Блок finally");
    }

    private static void checkException(Class<? extends RuntimeException> expected, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            check(expected.isInstance(e), expected.getSimpleName());
            return;
        }
        throw new AssertionError("Очікувалося виключення " + expected.getSimpleName());
    }

    private static void check(boolean condition, String test) {
        if (!condition) {
            throw new AssertionError("Тест не пройдено: " + test);
        }
    }
}
