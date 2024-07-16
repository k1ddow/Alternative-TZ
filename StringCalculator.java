import java.util.Scanner;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение в формате \"строка\" оператор \"строка\" или число: ");
        String input = scanner.nextLine().trim();

        try {
            String output = calculate(input);
            System.out.println("Результат:");
            System.out.println(output);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static String calculate(String inputStr) throws Exception {
        Scanner scanner = new Scanner(inputStr);
        scanner.useDelimiter("\\s+");

        if (!scanner.hasNext()) {
            scanner.close();
            throw new IllegalArgumentException("Пустой ввод");
        }

        StringBuilder str1Builder = new StringBuilder();

        while (scanner.hasNext()) {
            String part = scanner.next();
            str1Builder.append(part);
            if (part.endsWith("\"")) {
                break;
            } else {
                str1Builder.append(" ");
            }
        }

        String str1 = str1Builder.toString().trim();
        if (!str1.startsWith("\"") || !str1.endsWith("\"")) {
            scanner.close();
            throw new IllegalArgumentException("Первый аргумент должен быть в кавычках");
        }
        str1 = str1.substring(1, str1.length() - 1);
        if(str1.length() > 10){
            throw new IllegalArgumentException("Длина строки не должна превышать 10 символов");
        }

        if (!scanner.hasNext()) {
            scanner.close();
            throw new IllegalArgumentException("Неверный формат ввода");
        }

        String operator = scanner.next();
        if (!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/")) {
            scanner.close();
            throw new IllegalArgumentException("Операция не поддерживается");
        }

        if (!scanner.hasNext()) {
            scanner.close();
            throw new IllegalArgumentException("Неверный формат ввода");
        }

        String strOrNum = scanner.next();
        if (strOrNum.startsWith("\"")) {
            StringBuilder str2Builder = new StringBuilder();
            str2Builder.append(strOrNum);
            while (scanner.hasNext()) {
                String part = scanner.next();
                str2Builder.append(" ").append(part);
                if (part.endsWith("\"")) {
                    break;
                }
            }
            strOrNum = str2Builder.toString().trim();
            if (!strOrNum.endsWith("\"")) {
                scanner.close();
                throw new IllegalArgumentException("Неверный формат");
            }
            strOrNum = strOrNum.substring(1, strOrNum.length() - 1);
            if (strOrNum.length() > 10){
                throw new IllegalArgumentException("Длина строки не должна превышать 10 символов");
            }
        } else {
            try {
                int num = Integer.parseInt(strOrNum);
                if (num < 1 || num > 10) {
                    scanner.close();
                    throw new IllegalArgumentException("Число должно быть от 1 до 10");
                }
            } catch (NumberFormatException e) {
                scanner.close();
                throw new IllegalArgumentException("Аргумент должен быть в кавычках или числом");
            }
        }

        scanner.close();
        String result;

        switch (operator) {
            case "+":
                result = str1 + strOrNum;
                break;
            case "-":
                if (str1.contains(strOrNum)) {
                    result = str1.replace(strOrNum, "");
                } else {
                    result = str1;
                }
                break;
            case "*":
                int n = Integer.parseInt(strOrNum);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    sb.append(str1);
                }
                result = sb.toString();
                break;
            case "/":
                int divisor = Integer.parseInt(strOrNum);
                int newLength = str1.length() / divisor;
                result = str1.substring(0, newLength);
                break;
            default:
                throw new IllegalArgumentException("Операция не поддерживается");
        }

        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }

        return "\"" + result + "\"";
    }
}
