package listing;

import analizator.sentax.Calculate_Class;
import analizator.sentax.SentencesAnalise;
import data.DaTa;
import data.DataAsm;
import data.LexemDat;

import java.util.Arrays;

class Shelling {

    static int[] shell(LexemDat[] lexemMatrix, int stringOfLast, DaTa data) {
        int[] shellMassive = new int[stringOfLast];
        int[] counters = {0, 0};
        counters[0] = lexemMatrix[calculateDATAShelling(lexemMatrix, shellMassive, stringOfLast, counters)].getString();
        calculateCODEShelling(lexemMatrix, shellMassive, stringOfLast, counters, data);
//        System.out.println(Arrays.toString(shellMassive));
        return shellMassive;
    }

    private static int calculateDATAShelling(LexemDat[] lexemMatrix, int[] shellMassive, int stringOfLast, int[] count) {

        while (!lexemMatrix[count[1]].getLexem().equalsIgnoreCase("data") ||
                !lexemMatrix[count[1] + 1].getLexem().equalsIgnoreCase("ends")) {
            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, count[1], stringOfLast);
//            if (lexemMatrix[count + 1].getLexem().equalsIgnoreCase("db")) {
//                shellMassive[i] += 1;
//            }
            dataShellCondition(1, "db", lexemMatrix, count[1], count[0], shellMassive, placeOfStringEnd);
//            if (lexemMatrix[count + 1].getLexem().equalsIgnoreCase("dw")) {
//                shellMassive[i] += 2;
//            }
            dataShellCondition(2, "dw", lexemMatrix, count[1], count[0], shellMassive, placeOfStringEnd);
//            if (lexemMatrix[count + 1].getLexem().equalsIgnoreCase("dd")) {
//                shellMassive[i] += 1;
//            }
            dataShellCondition(4, "dd", lexemMatrix, count[1], count[0], shellMassive, placeOfStringEnd);
            count[0]++;
            count[1] += placeOfStringEnd + 1;
        }
        return count[1];
    }

    private static void dataShellCondition(int shell, String str, LexemDat[] lexemMatrix, int count,
                                           int i, int[] shellMassive, int placeOfStringEnd) {
        DataAsm dataAsm = new DataAsm();

        for (int j = count; j <= count + placeOfStringEnd; j++) {
            if (lexemMatrix[j].getLexem().equalsIgnoreCase(str)
                    && (lexemMatrix[j + 1].getType().equals(dataAsm.getDVname())
                    || lexemMatrix[j + 1].getType().equals(dataAsm.getHVname())
                    || lexemMatrix[j + 1].getType().equals(dataAsm.getBVname()))) {
                shellMassive[i] += shell;
            }
        }
//        if (lexemMatrix[count + 1].getLexem().equalsIgnoreCase(str)
//                && (lexemMatrix[count + 2].getType().equals(dataAsm.getDVname())
//                    || lexemMatrix[count + 2].getType().equals(dataAsm.getHVname())
//                    || lexemMatrix[count + 2].getType().equals(dataAsm.getBVname()))) {
//            shellMassive[i] += shell;
//        }
    }

    private static void calculateCODEShelling(LexemDat[] lexemMatrix, int[] shellMassive, int stringOfLast, int[] count, DaTa data) {
        DataAsm dat = new DataAsm();
        while (!lexemMatrix[count[1]].getLexem().equalsIgnoreCase("code") ||
                !lexemMatrix[count[1] + 1].getLexem().equalsIgnoreCase("ends")) {
            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, count[1], stringOfLast);

            if (cliShell(lexemMatrix, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (incShell(lexemMatrix, dat, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (movShell(lexemMatrix, dat, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (addRShell(lexemMatrix, dat, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (cmpShell(lexemMatrix, dat, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (jmpShell(lexemMatrix, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (addShell(lexemMatrix, dat, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (decShell(lexemMatrix, dat, data, shellMassive, count[1], count[0])) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            if (orShell(lexemMatrix, dat, data, shellMassive, count[1], count[0], placeOfStringEnd)) {
                count[0]++;
                count[1] += placeOfStringEnd + 1;
                continue;
            }
            count[0]++;
            count[1] += placeOfStringEnd + 1;
        }
    }

    private static boolean cliShell(LexemDat[] lexemMatrix, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("cli")) {
            shellMassive[i] += 1;
            return true;
        }
        return false;
    }

    private static boolean incShell(LexemDat[] lexemMatrix, DataAsm dat, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("inc")) {
            if (lexemMatrix[count + 1].getType().equals(dat.getR8name())) {
                shellMassive[i] += 2;
                return true;
            }
            if (lexemMatrix[count + 1].getType().equals(dat.getR32name())) {
                shellMassive[i] += 1;
                return true;
            }
        }
        return false;
    }

    private static boolean movShell(LexemDat[] lexemMatrix, DataAsm dat, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("mov")) {
            if (lexemMatrix[count + 1].getType().equals(dat.getR8name())) {
                shellMassive[i] += 2;
                return true;
            }
            if (lexemMatrix[count + 1].getType().equals(dat.getR32name())) {
                shellMassive[i] += 5;
                return true;
            }
        }
        return false;
    }

    private static boolean addRShell(LexemDat[] lexemMatrix, DataAsm dat, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("add")) {
            if (lexemMatrix[count + 1].getType().equals(dat.getR8name())
                    || lexemMatrix[count + 1].getType().equals(dat.getR32name())) {
                shellMassive[i] += 2;
                return true;
            }
        }
        return false;
    }

    private static boolean cmpShell(LexemDat[] lexemMatrix, DataAsm dat, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("cmp")) {
            int shell = 0;
            if (lexemMatrix[count + 3].getType().equals(dat.getSRname())) {
                shell = 2;
            }
            switch (lexemMatrix[count + shell + 5].getLexem()) {
                case "esp":
                    shellMassive[i] += 8;
                    if (shell != 0) {
                        if (lexemMatrix[count + 3].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                case "ebp":
                    shellMassive[i] += 7;
                    if (shell != 0) {
                        if (lexemMatrix[count + 3].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                default:
                    shellMassive[i] += 6;
                    if (shell != 0) {
                        if (lexemMatrix[count + 3].getLexem().equals("ds")) {
                            shellMassive[i] += 0;
                        } else {
                            shellMassive[i] += 1;
                        }
                    }
                    return true;
            }
        }
        return false;
    }

    private static boolean jmpShell(LexemDat[] lexemMatrix, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("jnb")) {
            shellMassive[i] += 6;
            return true;
        }
        return false;
    }

    private static boolean addShell(LexemDat[] lexemMatrix, DataAsm dat, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("add")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname())) {
                shell = +2;
            }
            switch (lexemMatrix[count + shell + 3].getLexem()) {
                case "esp":
                    shellMassive[i] += 8;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                case "ebp":
                    shellMassive[i] += 7;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                default:
                    shellMassive[i] += 6;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ds")) {
                            shellMassive[i] += 0;
                        } else {
                            shellMassive[i] += 1;
                        }
                    }
                    return true;
            }
        }
        return false;
    }

    private static boolean decShell(LexemDat[] lexemMatrix, DataAsm dat, DaTa data, int[] shellMassive, int count, int i) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("dec")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname())) {
                shell = +2;
            }

            int j = 0;
            boolean flag = true;
            while (j < data.getCountOfDataConstants()) {
                if (data.getDataConstantsValue(j).endsWith("h")) {
                    if (data.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
                        shellMassive[i] += 7;
                        flag = false;
                        break;
                    }
                }
                j++;
            }
            if (flag) shellMassive[i] += 6;

            switch (lexemMatrix[count + shell + 3].getLexem()) {
                case "esp":
                    shellMassive[i] += 2;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                case "ebp":
                    shellMassive[i] += 1;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                default:
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getType().equals(dat.getSRname())) {
                            if (lexemMatrix[count + 1].getLexem().equals("ds")) {
                                shellMassive[i] += 0;
                            } else {
                                shellMassive[i] += 1;
                            }
                        }
                    }
                    return true;
            }
        }
        return false;
    }

    private static boolean orShell(LexemDat[] lexemMatrix, DataAsm dat, DaTa data, int[] shellMassive, int count, int i, int placeOfStringEnd) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("or")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname())) {
                shell = +2;
            }
            shellMassive[i] += 6;

            boolean flag = true;
            int toShell = 0;
            for (int j = count; j <= count + placeOfStringEnd; j++) {
                if (lexemMatrix[j].getLexem().equals(",")) {
                    toShell = j - count + 1;
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return true;
            }

            int j = 0;
            while (j < data.getCountOfDataConstants()) {
                if (data.getDataConstantsType(j).equalsIgnoreCase("DB")) {
                    if (data.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
                        shellMassive[i] += 1;
                        if (data.getDataConstantsValue(j).endsWith("h")) {
                            shellMassive[i] += 1;
                            break;
                        }
                    }
                }
                if (data.getDataConstantsType(j).equalsIgnoreCase("DW")) {
                    if (data.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
//                        shellMassive[i] += 9;
                        double value = Calculate_Class.calculationFunc(lexemMatrix, count, toShell, placeOfStringEnd);
                        if (value >= 65_408) {
                            shellMassive[i] += 1;
                        } else {
                            shellMassive[i] += 2;
                        }
                        if (data.getDataConstantsValue(j).endsWith("h")) {
                            shellMassive[i] += 1;
                            break;
                        }
                        break;
                    }
                }
                if (data.getDataConstantsType(j).equalsIgnoreCase("DD")) {
                    if (data.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
//                        shellMassive[i] += 10;
                        double value = Calculate_Class.calculationFunc(lexemMatrix, count, toShell, placeOfStringEnd);
                        if (Math.abs(value) >= 4_294_967_168L) {
                            shellMassive[i] += 1;
                        } else if (Math.abs(value) < 128 || value == -128) {
                            shellMassive[i] += 1;
                        } else {
                            shellMassive[i] += 4;
                        }
                        if (data.getDataConstantsValue(j).endsWith("h")) {
                            shellMassive[i] += 1;
                            break;
                        }
                        break;
                    }
                }
                j++;
            }
            switch (lexemMatrix[count + shell + 3].getLexem()) {
                case "esp":
                    shellMassive[i] += 2;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                case "ebp":
                    shellMassive[i] += 1;
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getLexem().equals("ss")) {
                            shellMassive[i] -= 1;
                        }
                    }
                    return true;
                default:
                    if (shell != 0) {
                        if (lexemMatrix[count + 1].getType().equals(dat.getSRname())) {
                            if (lexemMatrix[count + 1].getLexem().equals("ds")) {
                                shellMassive[i] += 0;
                            } else {
                                shellMassive[i] += 1;
                            }
                        }
                    }
                    return true;
            }
        }
        return false;
    }
}
