package analizator.sentax;

import data.*;

import java.io.FileWriter;
import java.io.IOException;

public class SentencesAnalise {
    public static void analiseSenFunk(LexemDat[] lexemMatrix, Labels[] labe, DaTa DATAbuff, int stringOfLast) throws Exception {
        FileWriter writer = new FileWriter("Results/SentencesAnalise.txt");
        FileWriter writer2 = new FileWriter("Results/OperandAnalise.txt");
        int i = 0;
        int j;
        int count = 0;
        int placeOfStringEnd;
        int numberOfOperands;
        int commaPlace;
        int[] tabMassive = new int[7];
        boolean consistOperand;
        boolean consistComDir;
        boolean commaFind;
        DataAsm Dat = new DataAsm();
        OperandDat OperDatFirst = new OperandDat();
        OperandDat OperDatSecond = new OperandDat();

        writer.write("\t _________________________________________________________\n");
        writer.write("\t| Label| Mnemocode field| First  operand | Second operand |\n");
        writer.write("\t| field|----------------+----------------+----------------|\n");
        writer.write("\t|(Name)| First |Quantity| First |Quantity| First |Quantity|\n");

        while (i <= stringOfLast) {
            placeOfStringEnd = findPlaceOfStringEnd(lexemMatrix, count, stringOfLast);
            numberOfOperands = numberOfOperands(lexemMatrix, count, placeOfStringEnd);
            consistOperand = false;
            consistComDir = false;
            commaFind = false;

            if (lexemMatrix[count].getType().equals(Dat.getUIname())) {
                tabMassive[0]++;
            }

            j = 0;
            while (j <= placeOfStringEnd) {
                if (lexemMatrix[count + j].getType().equals(Dat.getDname()) || lexemMatrix[count + j].getType().equals(Dat.getCname())) {
                    tabMassive[1] = j;
                    tabMassive[2]++;
                    consistComDir = true;
                }
                j++;
            }

            if (consistComDir) {
                j = tabMassive[1];
                while (j <= placeOfStringEnd) {
                    if (numberOfOperands == 1) {
                        if (lexemMatrix[count + j].getType().equals(Dat.getUIname()) || lexemMatrix[count + j].getType().equals(Dat.getBVname()) || lexemMatrix[count + j].getType().equals(Dat.getDVname()) || lexemMatrix[count + j].getType().equals(Dat.getHVname()) || lexemMatrix[count + j].getType().equals(Dat.getR8name()) || lexemMatrix[count + j].getType().equals(Dat.getR32name())) {
                            consistOperand = true;
                        }
                    }
                    j++;
                }
                if (numberOfOperands != 1) {
                    consistOperand = true;
                }
                if (consistOperand) {
                    commaPlace = placeOfStringEnd;
                    tabMassive[3] = tabMassive[1] + tabMassive[2];
                    j = tabMassive[1];
                    while (j <= placeOfStringEnd) {
                        if (lexemMatrix[count + j].getLexem().equals(",")) {
                            commaPlace = j;
                            commaFind = true;
                        }
                        j++;
                    }

                    j = tabMassive[1] + tabMassive[2];
                    OperandAnalise.analiseOperFunk(lexemMatrix, labe, OperDatFirst, count, j, commaPlace, DATAbuff.getCountOfLastLabe(), commaFind);
                    while (j <= commaPlace) {
                        if (!lexemMatrix[count + j].getLexem().equals(",") && (lexemMatrix[count + j].getType().equals(Dat.getOSLname()) || lexemMatrix[count + j].getType().equals(Dat.getUIname()) || lexemMatrix[count + j].getType().equals(Dat.getBVname()) || lexemMatrix[count + j].getType().equals(Dat.getDVname()) || lexemMatrix[count + j].getType().equals(Dat.getHVname()) || lexemMatrix[count + j].getType().equals(Dat.getR8name()) || lexemMatrix[count + j].getType().equals(Dat.getR32name()))) {
                            tabMassive[4]++;
                        }
                        j++;
                    }

                    if (commaFind) {
                        tabMassive[5] = commaPlace + 1;
                        j = tabMassive[5];
                        OperandAnalise.analiseOperFunk(lexemMatrix, labe, OperDatSecond, count, j, placeOfStringEnd, DATAbuff.getCountOfLastLabe(), commaFind);
                        while (j <= placeOfStringEnd) {
                            if (lexemMatrix[count + j].getType().equals(Dat.getOSLname()) || lexemMatrix[count + j].getType().equals(Dat.getUIname()) || lexemMatrix[count + j].getType().equals(Dat.getBVname()) || lexemMatrix[count + j].getType().equals(Dat.getDVname()) || lexemMatrix[count + j].getType().equals(Dat.getHVname()) || lexemMatrix[count + j].getType().equals(Dat.getR8name()) || lexemMatrix[count + j].getType().equals(Dat.getR32name())) {
                                tabMassive[6]++;
                            }
                            j++;
                        }
                    }
                }
            }
            writeAndPrintFunc(lexemMatrix, writer, writer2, i, count, tabMassive,
                    placeOfStringEnd, consistOperand, commaFind,
                    OperDatFirst, OperDatSecond);

            for (int k = 0; k < 7; k++) {
                tabMassive[k] = 0;
            }
            count += placeOfStringEnd + 1;
            i++;
        }
        writer.write("\t|------+-------+--------+-------+--------+-------+--------|");
        writer.close();
    }

    private static int numberOfOperands(LexemDat[] lexemMatrix, int count, int placeOfStringEnd) {
        int counter = 0;
        int i = 0;
        while (count + i <= placeOfStringEnd) {
            if (lexemMatrix[count + i].getLexem().equals(",")) {
                counter++;
            }
            i++;
        }
        if (counter == 1) {
            return 2;
        }
        if (counter == 2) {
            return 3;
        }
        return 1;
    }

    public static int findPlaceOfStringEnd(LexemDat[] lexemMatrix, int count, int stringOfLast) {
        int i = 0;
        if (lexemMatrix[count].getString() == stringOfLast) return 0;
        while (lexemMatrix[count + i + 1].getString() == lexemMatrix[count].getString()) {
            i++;
        }
        return i;
    }

    private static void writeAndPrintFunc(LexemDat[] lexemMatrix, FileWriter writer, FileWriter writer2,
                                          int i, int count, int[] tabMassive, int placeOfStringEnd,
                                          boolean consistOperand, boolean commaFind, OperandDat OperDatFirst,
                                          OperandDat OperDatSecond) throws IOException {
        writer.write("\t|------+-------+--------+-------+--------+-------+--------|\n");
//        if (i > 9) {
//            writer.write(" " + i + " |");
//        } else {
//            writer.write(" " + i + "  |");
//        }
//        writer.write("   " + tabMassive[0] + "  |");
//        writer.write("    " + tabMassive[1] + "  |");
//        writer.write("    " + tabMassive[2] + "   |");
//        writer.write("   " + tabMassive[3] + "   |");
//        writer.write("    " + tabMassive[4] + "   |");
//        writer.write("   " + tabMassive[5] + "   |");
//        writer.write("    " + tabMassive[6] + "   |  \t");

        writer.write(String.format("%3d |%4d  |%4d   |%5d   |%4d   |%5d   |%4d   |%5d   |\t\t",
                i, tabMassive[0], tabMassive[1], tabMassive[2],
                tabMassive[3], tabMassive[4], tabMassive[5], tabMassive[6]));

        for (int j = 0; j <= placeOfStringEnd; j++) {
            writer.write(lexemMatrix[count + j].getLexem() + " ");
        }
        writer.write("\n");

        int l = count;
        while (l <= count + placeOfStringEnd) {
//            System.out.print(lexemMatrix[l++].getLexem() + " ");
            writer2.write(lexemMatrix[l++].getLexem() + " ");
        }
//        System.out.println();
        writer2.write("\n");

        if (consistOperand) {
            OperandAnalise.tableFunk(writer2, OperDatFirst);
            if (commaFind) OperandAnalise.tableFunk(writer2, OperDatSecond);

        }
        if (i % 11 == 0 && i != 0) {
            writer.write("\t|------+----------------+----------------+----------------+\n");
            writer.write("\t|------+----------------+----------------+----------------+\n");
            writer.write("\t| Label| Mnemocode field| First  operand | Second operand |\n");
            writer.write("\t| field|----------------+----------------+----------------|\n");
            writer.write("\t|(Name)| First |Quantity| First |Quantity| First |Quantity|\n");
        }
    }
}