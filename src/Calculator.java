import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Input: ");
        String str = sc.nextLine();
        String response = Main.calc(str);
        System.out.println("Output:\n" + response);
    }
}

class Main {
    static String exception = "throws Exception";

    public static String calc(String input) {
//        String exception = "throws Exception";
        String numeralSet;
        String[] splitedExpr = input.split(" ");
        String result = "";
        Main rules = new Main();

        try {
            numeralSet = rules.checkConditions(splitedExpr);
            switch (numeralSet) {
                case "arabic":
                    result = rules.arabicCalc(splitedExpr);
                    break;
                case "roman":
                    splitedExpr[0] = rules.romanToArab(splitedExpr[0]);
                    splitedExpr[2] = rules.romanToArab(splitedExpr[2]);
                    result = rules.arabicCalc((splitedExpr));
                    result = rules.checkConditions(result);
                    if (!Objects.equals(result, "null")) {
                        result = rules.arabToRoman(Integer.parseInt(result));
                    }
                    break;
            }
        } catch (MyException my) {
//            System.out.println(exception);
            return result = exception;
        }
        return result;
    }


    private String checkConditions(String result) throws MyException {
        if (Integer.parseInt(result) < 0) {    //т.к. в римской записи нет отрицательных чисел
            throw new MyException(exception);
        } else if (Integer.parseInt(result) == 0) {    //и нет числа ноль
            return "null";
        }
        return result;
    }

    private String checkConditions(String[] expressionArr) throws MyException {
        String isArabic = "^([1-9]|10?)$";
        String isRoman = "^(I{1,3}|I[VX]?|[VX]|VI{1,3})$";
        String isOperator = "^[-+*/]$";
        if (expressionArr.length != 3)
            throw new MyException(exception);
        else if (!Pattern.matches(isOperator, expressionArr[1]))
            throw new MyException(exception);
        else if (
                (Pattern.matches(isArabic, expressionArr[0])) &
                        (Pattern.matches(isArabic, expressionArr[2]))
        )
            return "arabic";
        else if (
                (Pattern.matches(isRoman, expressionArr[0])) &
                        (Pattern.matches(isRoman, expressionArr[2]))
        )
            return "roman";
        throw new MyException(exception);
    }

    private String arabToRoman(int arabNumber) {                                  //арабский в римский
        String result = "";
        int value;
        int[] arab = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arab.length; i++) {
            value = arabNumber / arab[i];
            for (int j = 0; j < value; j++) {
                result = result.concat(roman[i]);
            }
            arabNumber = arabNumber % arab[i];
        }
        return result;
    }

    private String romanToArab(String romanNumber) {                            //римский в арабский

        int result = 0;
        int[] arab = {10, 9, 5, 4, 1};
        String[] roman = {"X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arab.length; i++) {
            while (romanNumber.indexOf(roman[i]) == 0) {
                result += arab[i];
                romanNumber = romanNumber.substring(roman[i].length());
            }
        }
        return Integer.toString(result);
    }

    private String arabicCalc(String[] splitedExpr) {
        int result = 0;
        switch (splitedExpr[1]) {
            case "+":
                result = Integer.parseInt(splitedExpr[0]) + Integer.parseInt(splitedExpr[2]);
                break;
            case "-":
                result = Integer.parseInt(splitedExpr[0]) - Integer.parseInt(splitedExpr[2]);
                break;
            case "*":
                result = Integer.parseInt(splitedExpr[0]) * Integer.parseInt(splitedExpr[2]);
                break;
            case "/":
                result = Integer.parseInt(splitedExpr[0]) / Integer.parseInt(splitedExpr[2]);
                break;
        }
        return Integer.toString(result);
    }


}

class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}


