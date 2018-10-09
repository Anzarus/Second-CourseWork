package analizator.lexical;

import data.*;

import java.io.*;
import java.util.Scanner;

public class LexAnalise {
    public static int reader(String fileName, LexemDat[] lexemMatrix, Labels[] labe, DaTa DATAbuff) throws Exception {
        FileReader fileReader = new FileReader(fileName);
        Scanner scan = new Scanner(fileReader);
        FileWriter writer = new FileWriter("Results/LexAnalise.txt");
        String str, string;
        boolean[] flags = {false, false};
        int minusFlag;
        int countOfLabe = 0;
        int[] countMass = {-1, 0, 0};
        DataAsm Dat = new DataAsm();

        //System.out.println(" ____________________________________________________");
        //System.out.println("| St | Pl |  Value   | Ln |        Definition        |");
        writer.write(" ____________________________________________________\n");
        writer.write("| St | Pl |  Value   | Ln |        Definition        |\n");

        while (scan.hasNextLine()) {
            str = scan.nextLine();
            countMass[1] = 0;                       //counter = 0;
            string = str.replace("\t", " ");
            minusFlag = 0;
            if (!str.equals("")) {
                countMass[0]++;/*counter_str++;*/
            }

            for (String parts : string.split(" ")) {
                int length = parts.length();
                if (length == 0) {
                    continue;
                }
                if (parts.equals(";")) {
                    break;
                }
//                System.out.println("|----+----+----------+----+--------------------------|");
                writer.write("|----+----+----------+----+--------------------------|\n");

                dataAnalise(flags, lexemMatrix, countMass, parts, Dat, DATAbuff);

                if ((parts.equals(":")) && (lexemMatrix[countMass[2] - 1].getType().equals(Dat.getUIname()))) {
                    writeLabeClass(lexemMatrix, labe, countMass[0], countMass[1], countMass[2], countOfLabe);
                    countOfLabe++;
                }

                if (OLSAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }
                if (commandAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }
                if (directiveAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }
                if (segmentRegAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }
                if (register32Analise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }
                if (register8Analise(lexemMatrix, Dat, writer, flags, countMass, parts, length)) {
                    continue;
                }

                if (parts.startsWith("-")) {
                    minusFlag = 1;
                }

                if (binnaryAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length, minusFlag)) {
                    continue;
                }
                if (hexAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length, minusFlag)) {
                    continue;
                }
                if (decAnalise(lexemMatrix, Dat, writer, flags, countMass, parts, length, minusFlag)) {
                    continue;
                }
                UIAnalise(lexemMatrix, Dat, writer, countMass, parts, length);
            }
        }
//        System.out.println("|----+----+----------+----+--------------------------|\n\n\n\n");
        writer.write("|----+----+----------+----+--------------------------|\n");
        DATAbuff.setCountOfLastLabe(countOfLabe);

        fileReader.close();
        writer.close();
        return countMass[0];
    }

    private static boolean checkStringDes(String string, int minusFlag) {
        char c;
        for (int i = minusFlag; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkStringBinary(String string, int minusFlag) {
        char c;
        for (int i = minusFlag; i < string.length() - 1; i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '1')) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkStringHex(String string, int minusFlag) {
        char c;
        for (int i = minusFlag; i < string.length() - 1; i++) {
            c = string.charAt(i);
            if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'))) {
                return false;
            }
        }
        return true;
    }

    private static void writeClass(LexemDat[] lexemMatrix, String value, String name, int string, int place, int iCounter) {
        LexemDat lexemMatrixBuff = new LexemDat();
        lexemMatrixBuff.setLexem(value, name, string, place);
        lexemMatrix[iCounter] = lexemMatrixBuff;
    }

    private static void writeLabeClass(LexemDat[] lexemMatrix, Labels[] labe, int string, int place, int iCounter, int countOfLabe) {
        Labels labeBuff = new Labels();
        labeBuff.setLabel(lexemMatrix[iCounter - 1].getLexem(), string, place - 1);
        labe[countOfLabe] = labeBuff;
    }

    private static void dataAnalise(boolean[] flags, LexemDat[] lexemMatrix, int[] countMass, String parts, DataAsm Dat, DaTa DATAbuff) {
        if (flags[1] && ((parts.equalsIgnoreCase("ends"))
                && (lexemMatrix[countMass[2] - 1].getLexem().equalsIgnoreCase("data")))) {
            flags[1] = false;
        }
        if (!flags[1] && ((parts.equalsIgnoreCase("segment"))
                && (lexemMatrix[countMass[2] - 1].getLexem().equalsIgnoreCase("data")))) {
            flags[1] = true;
        }
        if (flags[1]
                && (lexemMatrix[countMass[2] - 1].getType().equals(Dat.getBVname())
                || lexemMatrix[countMass[2] - 1].getType().equals(Dat.getHVname())
                || lexemMatrix[countMass[2] - 1].getType().equals(Dat.getDVname()))) {
            DATAbuff.setDataConstantsName(lexemMatrix[countMass[2] - 3].getLexem(), DATAbuff.getCountOfDataConstants());
            DATAbuff.setDataConstantsType(lexemMatrix[countMass[2] - 2].getLexem(), DATAbuff.getCountOfDataConstants());
            DATAbuff.setDataConstantsValue(lexemMatrix[countMass[2] - 1].getLexem(), DATAbuff.getCountOfDataConstants());
            DATAbuff.incCountOfDataConstants();
        }
    }

    private static boolean OLSAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                      boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getOneSymbolLexems().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getOSLname());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getOSLname()));
                writeClass(lexemMatrix, parts, Dat.getOSLname(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean commandAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                          boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getCommand().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getCname());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getCname()));
                writeClass(lexemMatrix, parts, Dat.getCname(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean directiveAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                            boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getDirective().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getDname());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getDname()));
                writeClass(lexemMatrix, parts, Dat.getDname(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean segmentRegAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                             boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getSegmentReg().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getSRname());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getSRname()));
                writeClass(lexemMatrix, parts, Dat.getSRname(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean register32Analise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                             boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getRegister32().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getR32name());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getR32name()));
                writeClass(lexemMatrix, parts, Dat.getR32name(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean register8Analise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                            boolean[] flags, int[] countMass, String parts, int length) throws IOException {
        for (String statements : Dat.getRegister8().split(" ")) {
            flags[0] = parts.equalsIgnoreCase(statements);
            if (flags[0]) {
//                System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                        countMass[0], countMass[1], parts, length, Dat.getR8name());
                writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                        countMass[0], countMass[1], parts, length, Dat.getR8name()));
                writeClass(lexemMatrix, parts, Dat.getR8name(), countMass[0], countMass[1]++, countMass[2]++);
                break;
            }
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean binnaryAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                          boolean[] flags, int[] countMass, String parts, int length, int minusFlag) throws IOException {

        if ((parts.endsWith("b")) && checkStringBinary(parts, minusFlag)) {
            flags[0] = true;
//            System.out.printf("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
//                    countMass[0], countMass[1], parts, length, Dat.getBVname());
            writer.write(String .format("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
            countMass[0], countMass[1], parts, length, Dat.getBVname()));
            writeClass(lexemMatrix, parts, Dat.getBVname(), countMass[0], countMass[1]++, countMass[2]++);
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean hexAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                      boolean[] flags, int[] countMass, String parts, int length, int minusFlag) throws IOException {
        if ((parts.endsWith("h")) && checkStringHex(parts, minusFlag)) {
            flags[0] = true;
//            System.out.printf("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
//                    countMass[0], countMass[1], parts, length, Dat.getHVname());
            writer.write(String.format("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
                    countMass[0], countMass[1], parts, length, Dat.getHVname()));
            writeClass(lexemMatrix, parts, Dat.getHVname(), countMass[0], countMass[1]++, countMass[2]++);
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static boolean decAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                      boolean[] flags, int[] countMass, String parts, int length, int minusFlag) throws IOException {
        if (checkStringDes(parts, minusFlag)) {
            flags[0] = true;
//            System.out.printf("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
//                    countMass[0], countMass[1], parts, length, Dat.getDVname());
            writer.write(String.format("| %2d | %2d |  %-8s| %2d |\t%-25s|\n",
                    countMass[0], countMass[1], parts, length, Dat.getDVname()));
            writeClass(lexemMatrix, parts, Dat.getDVname(), countMass[0], countMass[1]++, countMass[2]++);
        }
        if (flags[0]) {
            flags[0] = false;
            return true;
        }
        return false;
    }

    private static void UIAnalise(LexemDat[] lexemMatrix, DataAsm Dat, FileWriter writer,
                                  int[] countMass, String parts, int length) throws IOException {
//        System.out.printf("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
//                countMass[0], countMass[1], parts, length, Dat.getUIname());
        writer.write(String.format("| %2d | %2d |  %-8S| %2d |\t%-25s|\n",
                countMass[0], countMass[1], parts, length, Dat.getUIname()));
        writeClass(lexemMatrix, parts, Dat.getUIname(), countMass[0], countMass[1]++, countMass[2]++);
    }
}