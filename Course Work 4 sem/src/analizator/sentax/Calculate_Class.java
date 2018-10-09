package analizator.sentax;

import data.LexemDat;

public class Calculate_Class {
    public static double calculationFunc(LexemDat[] lexemMatrix, int count, int begin, int last) {
        StringBuilder str = new StringBuilder();

        boolean flag = false;
        int i = begin;
        while (i <= last) {
            if (lexemMatrix[count + i].getType().equals("Hex value")) {
                str.append(calculateHexValue(lexemMatrix, count + i)).append(" ");
                i++;
                continue;
            }
            if (lexemMatrix[count + i].getType().equals("Binary value")) {
                str.append(calculateBinValue(lexemMatrix, count + i)).append(" ");
                i++;
                continue;
            }
            if (lexemMatrix[count + i].getType().equals("Dec value")) {
                str.append(calculateDecValue(lexemMatrix, count + i)).append(" ");
                flag = true;
                i++;
                continue;
            }
            str.append(lexemMatrix[count + i].getLexem()).append(" ");
            i++;
        }
        if ((begin == last) && (lexemMatrix[count + begin].getLexem().startsWith("-"))
                && (flag)) {
            return calculate(str.toString());
        } else {
            return calculate(str.toString());
        }
    }

    private static double calculate(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }

    private static int calculateHexValue(LexemDat[] lexemMatrix, int place) {
        int operand = 0;
        if (lexemMatrix[place].getType().equals("Hex value")) {
            char[] hexOperand = lexemMatrix[place].getLexem().toCharArray();

            int j = 0;
            while (j < lexemMatrix[place].getLexem().length() - 1) {
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '0') {
                    operand += Math.pow(16, j) * 0;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '1') {
                    operand += Math.pow(16, j) * 1;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '2') {
                    operand += Math.pow(16, j) * 2;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '3') {
                    operand += Math.pow(16, j) * 3;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '4') {
                    operand += Math.pow(16, j) * 4;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '5') {
                    operand += Math.pow(16, j) * 5;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '6') {
                    operand += Math.pow(16, j) * 6;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '7') {
                    operand += Math.pow(16, j) * 7;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '8') {
                    operand += Math.pow(16, j) * 8;
                }
                if (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == '9') {
                    operand += Math.pow(16, j) * 9;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'A') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'a')) {
                    operand += Math.pow(16, j) * 10;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'B') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'b')) {
                    operand += Math.pow(16, j) * 11;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'C') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'c')) {
                    operand += Math.pow(16, j) * 12;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'D') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'd')) {
                    operand += Math.pow(16, j) * 13;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'E') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'e')) {
                    operand += Math.pow(16, j) * 14;
                }
                if ((hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'F') || (hexOperand[lexemMatrix[place].getLexem().length() - 2 - j] == 'f')) {
                    operand += Math.pow(16, j) * 15;
                }
                j++;
            }
        }
        return operand;
    }

    private static int calculateBinValue(LexemDat[] lexemMatrix, int place) throws NumberFormatException {
        int operand = 0;
        if (lexemMatrix[place].getType().equals("Binary value")) {
            String value = lexemMatrix[place].getLexem().substring(0, lexemMatrix[place].getLexem().length() - 1);

            int j = value.length() - 1;
            int pow = 0;
            while (0 <= j) {
                if (value.charAt(j) == '1') {
                    operand += Math.pow(2, pow);
                }
                j--;
                pow++;
            }

        }
        return operand;
    }
//    private static int calculateBinValue(LexemDat[] lexemMatrix, int place) throws NumberFormatException {
//        int operand = 0;
//        if (lexemMatrix[place].getType().equals("Binary value")) {
//            String value = lexemMatrix[place].getLexem().substring(0, lexemMatrix[place].getLexem().length() - 1);
//            int begin = 0;
//            if (value.length() >= 9) {
//                if (value.length() >= 17) {
//                    if (value.length() >= 25) {
//                        begin += 24;
//                        operand += calculationBigBinValue(value, 24);
//                    } else {
//                        begin += 16;
//                    }
//                    operand += calculationBigBinValue(value, 16);
//                } else {
//                    begin += 8;
//                }
//                operand += calculationBigBinValue(value, 8);
//            }
//            int byteOperand = Integer.parseInt(value.substring(begin));
//            int j = value.length() - begin;
//            int pow = 0;
//            while (0 < j) {
//                if (Math.abs(byteOperand % 10) == 1) {
//                    operand += Math.pow(2, pow);
//                }
//                byteOperand = byteOperand / 10;
//                j--;
//                pow++;
//            }
//
//        }
//        return operand;
//    }
//
//    private static int calculationBigBinValue(String value, int half) {
//        int operand = 0;
//        String highPartStr = value.substring(0, value.length() - half);
//
//        int byteOperand = Integer.parseInt(highPartStr);
//        int j = highPartStr.length();
//        int pow = half;
//        while (0 < j) {
//            if (Math.abs(byteOperand % 10) == 1) {
//                operand += Math.pow(2, pow);
//            }
//            byteOperand = byteOperand / 10;
//            j--;
//            pow++;
//        }
//        operand += byteOperand;
//        return operand;
//    }

    private static int calculateDecValue(LexemDat[] lexemMatrix, int place) {
        int operand = 0;
        if (lexemMatrix[place].getType().equals("Dec value")) {
            if (lexemMatrix[place].getType().endsWith("d")) {
                operand = Integer.parseInt(lexemMatrix[place].getLexem().substring(0, lexemMatrix[place].getLexem().length() - 2));
            } else {
                operand = Integer.parseInt(lexemMatrix[place].getLexem());
            }
        }
        return operand;
    }
}
