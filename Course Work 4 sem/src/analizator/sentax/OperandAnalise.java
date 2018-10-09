package analizator.sentax;

import data.*;

import java.io.FileWriter;
import java.io.IOException;

public class OperandAnalise {
    public static void analiseOperFunk(LexemDat[] lexemMatrix, Labels[] labe, OperandDat operandMatrix, int count, int begin, int last, int countOfLabe, boolean commaFind) {
        int i = begin;
        boolean scopeOpen = false;
        boolean firstRegister = false;
        DataAsm Dat = new DataAsm();
        operandMatrix.setOperandDatZero();

        while (i <= last) {
            if (lexemMatrix[count + i].getLexem().equals("[")) {
                scopeOpen = true;
            }
            firstAttribute(lexemMatrix, operandMatrix, count, i, scopeOpen, Dat);
            secondAttribute(lexemMatrix, operandMatrix, count, i);
            thirdAttribute(lexemMatrix, operandMatrix, count, i, Dat);
            fourthAttribute(lexemMatrix, labe, operandMatrix, count, i, countOfLabe);
            fivesAttribute(lexemMatrix, operandMatrix, count, i, Dat);
            seventhAttribute(lexemMatrix, operandMatrix, count, i, Dat, firstRegister);
            firstRegister = sixthAttribute(lexemMatrix, operandMatrix, scopeOpen, count, i, Dat);
            eighthAttribute(lexemMatrix, operandMatrix, count, i, scopeOpen, Dat);
            ninthAttribute(lexemMatrix, operandMatrix, count, i, scopeOpen);
            tenthAttribute(lexemMatrix, operandMatrix, count, i, Dat, begin, last, commaFind);

            i++;
        }
    }

    private static void firstAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, boolean scopeOpen, DataAsm Dat) {
        int regNumb;
        if (lexemMatrix[count + i].getType().equals(Dat.getR8name()) && !scopeOpen) {
            operandMatrix.possibleOperands[0] = true;
            operandMatrix.mainProperties[0] = "8";
            regNumb = 0;
            for (String reg8 : Dat.getRegister8().split(" ")) {
                if (reg8.equals(lexemMatrix[count + i].getLexem())) {
                    int first = regNumb % 2;
                    regNumb = regNumb / 2;
                    int second = regNumb % 2;
                    regNumb = regNumb / 2;
                    int third = regNumb % 2;
                    operandMatrix.secondProperties[0] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                    break;
                }
                regNumb++;
            }
        }
        if (lexemMatrix[count + i].getType().equals(Dat.getR32name()) && !scopeOpen) {
            operandMatrix.possibleOperands[0] = true;
            operandMatrix.mainProperties[0] = "32";
            regNumb = 0;
            for (String reg32 : Dat.getRegister32().split(" ")) {
                if (reg32.equals(lexemMatrix[count + i].getLexem())) {
                    int first = regNumb % 2;
                    regNumb = regNumb / 2;
                    int second = regNumb % 2;
                    regNumb = regNumb / 2;
                    int third = regNumb % 2;
                    operandMatrix.secondProperties[0] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                    break;
                }
                regNumb++;
            }
        }
    }

    private static void secondAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i) {
        if (lexemMatrix[count + i].getLexem().equalsIgnoreCase("ptr")) {
            operandMatrix.possibleOperands[1] = true;
            if (lexemMatrix[count + i - 1].getLexem().equalsIgnoreCase("byte")) {
                operandMatrix.mainProperties[1] = "1";
            }
            if (lexemMatrix[count + i - 1].getLexem().equalsIgnoreCase("word")) {
                operandMatrix.mainProperties[1] = "2";
            }
            if (lexemMatrix[count + i - 1].getLexem().equalsIgnoreCase("dword")) {
                operandMatrix.mainProperties[1] = "4";
            }
        }
    }

    private static void thirdAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, DataAsm Dat) {
        if (lexemMatrix[count + i].getType().equals("Segment exchange reg")) {
            operandMatrix.possibleOperands[2] = true;
            boolean found = false;
            int number = 0;
            for (String SegReg : Dat.getSegmentReg().split(" ")) {
                if (SegReg.equals(lexemMatrix[count + i].getLexem()) && lexemMatrix[count + i + 1].getLexem().equals(":")) {
                    found = true;
                    break;
                }
                number++;
            }
            if (found) {
                int integer = 0;
                for (String SegRegNumb : Dat.getSegmentRegNumb().split(" ")) {
                    if (number == integer) {
                        operandMatrix.mainProperties[2] = SegRegNumb;
                    }
                    integer++;
                }
            }
        }
    }

    private static void fourthAttribute(LexemDat[] lexemMatrix, Labels[] labe, OperandDat operandMatrix, int count, int i, int countOfLabe) {
        int j = 0;
        while (j < countOfLabe) {
            if (lexemMatrix[count + i].getLexem().equals(labe[j].getLexem())) {
                operandMatrix.possibleOperands[3] = true;
                operandMatrix.mainProperties[3] = String.valueOf(labe[j].getString());
            }
            j++;
        }
    }

    private static void fivesAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, DataAsm Dat) {
        if (lexemMatrix[count + i].getType().equals(Dat.getUIname())) {
            operandMatrix.possibleOperands[4] = true;
        }
    }

    private static void seventhAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, DataAsm Dat, boolean firstRegister) {
        if (firstRegister) {
            int regNumb;
            if (lexemMatrix[count + i].getType().equals(Dat.getR8name())) {
                operandMatrix.possibleOperands[6] = true;
                operandMatrix.secondProperties[6] = "8";
                regNumb = 0;
                for (String reg8 : Dat.getRegister8().split(" ")) {
                    if (reg8.equals(lexemMatrix[count + i].getLexem())) {
                        int first = regNumb % 2;
                        regNumb = regNumb / 2;
                        int second = regNumb % 2;
                        regNumb = regNumb / 2;
                        int third = regNumb % 2;
                        operandMatrix.mainProperties[6] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                        break;
                    }
                    regNumb++;
                }
            }
            if (lexemMatrix[count + i].getType().equals(Dat.getR32name())) {
                operandMatrix.possibleOperands[6] = true;
                operandMatrix.secondProperties[6] = "32";
                regNumb = 0;
                for (String reg32 : Dat.getRegister32().split(" ")) {
                    if (reg32.equals(lexemMatrix[count + i].getLexem())) {
                        int first = regNumb % 2;
                        regNumb = regNumb / 2;
                        int second = regNumb % 2;
                        regNumb = regNumb / 2;
                        int third = regNumb % 2;
                        operandMatrix.mainProperties[6] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                        break;
                    }
                    regNumb++;
                }
            }
        }
    }

    private static boolean sixthAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, boolean scopeOpen, int count, int i, DataAsm Dat) {
        int regNumb;
        if (lexemMatrix[count + i].getType().equals(Dat.getR8name()) && scopeOpen) {
            operandMatrix.possibleOperands[5] = true;
            operandMatrix.secondProperties[5] = "8";
            regNumb = 0;
            for (String reg8 : Dat.getRegister8().split(" ")) {
                if (reg8.equals(lexemMatrix[count + i].getLexem())) {
                    int first = regNumb % 2;
                    regNumb = regNumb / 2;
                    int second = regNumb % 2;
                    regNumb = regNumb / 2;
                    int third = regNumb % 2;
                    operandMatrix.mainProperties[5] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                    break;
                }
                regNumb++;
            }
            return true;
        }
        if (lexemMatrix[count + i].getType().equals(Dat.getR32name()) && scopeOpen) {
            operandMatrix.possibleOperands[5] = true;
            operandMatrix.secondProperties[5] = "32";
            regNumb = 0;
            for (String reg32 : Dat.getRegister32().split(" ")) {
                if (reg32.equals(lexemMatrix[count + i].getLexem())) {
                    int first = regNumb % 2;
                    regNumb = regNumb / 2;
                    int second = regNumb % 2;
                    regNumb = regNumb / 2;
                    int third = regNumb % 2;
                    operandMatrix.mainProperties[5] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                    break;
                }
                regNumb++;
            }
            return true;
        }
        return false;
    }

    private static void eighthAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, boolean scopeOpen, DataAsm Dat) {
        if ((lexemMatrix[count + i].getType().equals(Dat.getHVname()) || lexemMatrix[count + i].getType().equals(Dat.getBVname()) || lexemMatrix[count + i].getType().equals(Dat.getDVname())) && scopeOpen) {
            int regNumb = 0;
            if ((lexemMatrix[count + i + 2].getLexem().equals(Dat.getR8name()) && lexemMatrix[count + i + 1].getLexem().equals("*")) || (lexemMatrix[count + i - 2].getLexem().equals(Dat.getR8name()) && lexemMatrix[count + i - 1].getLexem().equals("*"))) {
                operandMatrix.possibleOperands[7] = true;
                operandMatrix.secondProperties[7] = lexemMatrix[count + i].getLexem();
                for (String reg8 : Dat.getRegister8().split(" ")) {
                    if (reg8.equals(lexemMatrix[count + i + 2].getLexem()) || reg8.equals(lexemMatrix[count + i - 2].getLexem())) {
                        int first = regNumb % 2;
                        regNumb = regNumb / 2;
                        int second = regNumb % 2;
                        regNumb = regNumb / 2;
                        int third = regNumb % 2;
                        operandMatrix.mainProperties[7] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                        break;
                    }
                    regNumb++;
                }
            }

            regNumb = 0;
            if ((lexemMatrix[count + i + 2].getLexem().equals(Dat.getR32name()) && lexemMatrix[count + i + 1].getLexem().equals("*")) || (lexemMatrix[count + i - 2].getLexem().equals(Dat.getR32name()) && lexemMatrix[count + i - 1].getLexem().equals("*"))) {
                operandMatrix.possibleOperands[7] = true;
                operandMatrix.secondProperties[7] = lexemMatrix[count + i].getLexem();
                for (String reg32 : Dat.getRegister32().split(" ")) {
                    if (reg32.equals(lexemMatrix[count + i + 2].getLexem()) || reg32.equals(lexemMatrix[count + i - 2].getLexem())) {
                        int first = regNumb % 2;
                        regNumb = regNumb / 2;
                        int second = regNumb % 2;
                        regNumb = regNumb / 2;
                        int third = regNumb % 2;
                        operandMatrix.mainProperties[7] = String.valueOf(third) + String.valueOf(second) + String.valueOf(first);
                        break;
                    }
                    regNumb++;
                }
            }
        }
    }

    private static void ninthAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, boolean scopeOpen) {
        if ((lexemMatrix[count + i].getLexem().equals("offset") || lexemMatrix[count + i].getLexem().equals("seg")) && scopeOpen) {
            operandMatrix.possibleOperands[8] = true;
        }
    }

    private static void tenthAttribute(LexemDat[] lexemMatrix, OperandDat operandMatrix, int count, int i, DataAsm Dat, int begin, int last, boolean commaFind) {
        if (lexemMatrix[count + i].getType().equals(Dat.getHVname()) || lexemMatrix[count + i].getType().equals(Dat.getBVname()) || lexemMatrix[count + i].getType().equals(Dat.getDVname())) {
            operandMatrix.possibleOperands[9] = true;
            if (commaFind) {
                operandMatrix.mainProperties[9] = String.valueOf((int) Calculate_Class.calculationFunc(lexemMatrix, count, begin, last));
            }
        }
    }

    public static void tableFunk(FileWriter writer2, OperandDat operandMatrix) throws IOException {
//        System.out.println("\n\t\t __________________________________________________________");
//        System.out.println("\t\t|    |    |   List    of   possible   |   Main   |  Second  |");
//        System.out.println("\t\t| № |flag| tokens  of   the  operand |Properties|Properties|");
        writer2.write("\n\t\t ___________________________________________________________\n" +
                "\t\t|    |    |   List    of   possible   |   Main   |  Second  |\n" +
                "\t\t| № |flag| tokens  of   the  operand |Properties|Properties|\n");
        for (int i = 0; i < 10; i++) {
            if (operandMatrix.possibleOperands[i]) {
//                System.out.println("\t\t|----+----+---------------------------+----------+----------|");
//                System.out.printf("\t\t|%3d |%-1b|%15s|%-6s    |%-6s    |\n",
//                        i + 1, operandMatrix.possibleOperands[i], operandMatrix.possibleOperandLexems[i],
//                        operandMatrix.mainProperties[i], operandMatrix.secondProperties[i]);
                writer2.write("\t\t|----+----+---------------------------+----------+----------|\n" +
                        String.format("\t\t|%3d |%-1b|%15s|%6s    |%6s    |\n",
                                i + 1, operandMatrix.possibleOperands[i], operandMatrix.possibleOperandLexems[i],
                                operandMatrix.mainProperties[i], operandMatrix.secondProperties[i]));
            }
        }
//        System.out.println("\t\t|-----------------------------------------------------------|\n");
        writer2.write("\t\t|-----------------------------------------------------------|\n\n");
    }
}