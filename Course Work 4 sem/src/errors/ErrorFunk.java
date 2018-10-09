package errors;

import analizator.sentax.*;
import data.*;
import listing.Lexem;

public class ErrorFunk {

    public static int lexemErrors(LexemDat[] lexemMatrix, int[] errorMas, int stringOfLast, DataAsm dat, boolean out) {
        int result = 0;
        int j = 0;
        while (lexemMatrix[j].getString() != stringOfLast) {
            if (lexemMatrix[j].getType().equals(dat.getUIname())) {
                char c = lexemMatrix[j].getLexem().charAt(0);
                if ((c >= '0' && c <= '9')) {
                    result++;
                    if (out) System.out.println("Error int string " + lexemMatrix[j].getString());
                    errorMas[lexemMatrix[j].getString()] = 1;
                }
                if (c == '@') {
                    result++;
                    if (out) System.out.println("Error int string " + lexemMatrix[j].getString());
                    errorMas[lexemMatrix[j].getString()] = 1;
                }
                if (c == '_') {
                    result++;
                    if (out) System.out.println("Error int string " + lexemMatrix[j].getString());
                    errorMas[lexemMatrix[j].getString()] = 1;
                }
                if (lexemMatrix[j].getLexem().length() > 8) {
                    result++;
                    if (out) System.out.println("Error int string " + lexemMatrix[j].getString());
                    errorMas[lexemMatrix[j].getString()] = 1;
                }
            }
            j++;
        }
        return result;
    }

    public static int segmentsErrors(LexemDat[] lexemMatrix, int stringOfLast, boolean out) {
        int countOfErrors = 0;
        boolean programEnd = false;
        boolean dataBeg = false;
        boolean dataEnds = false;
        boolean codeBeg = false;
        boolean codeEnds = false;

        int i = 0;
        int count = 0;

        while (lexemMatrix[count + i].getString() != stringOfLast && lexemMatrix[count + i + 1] != null) {
            if (lexemMatrix[count + i + 1].getLexem().equalsIgnoreCase("end")
                    && lexemMatrix[count + i + 1].getString() == stringOfLast
                    && lexemMatrix[count + i + 2] == null) {
                programEnd = true;
            }
            i++;
        }
        if (!programEnd) {
            if (out) System.out.println("Error: no program END");
            countOfErrors++;
            return countOfErrors;
        }

        while (!lexemMatrix[count].getLexem().equalsIgnoreCase("end")
                || !(lexemMatrix[count + 1] == null)) {
            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, count, stringOfLast);

            if (lexemMatrix[count].getLexem().equalsIgnoreCase("DATA")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("SEGMENT")) {
                dataBeg = true;
            }
            if (lexemMatrix[count].getLexem().equalsIgnoreCase("DATA")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("ENDS")) {
                dataEnds = true;
            }
            if (lexemMatrix[count].getLexem().equalsIgnoreCase("CODE")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("SEGMENT")) {
                codeBeg = true;
            }
            if (lexemMatrix[count].getLexem().equalsIgnoreCase("CODE")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("ENDS")) {
                codeEnds = true;
            }
            count += placeOfStringEnd + 1;

        }
        if (!dataBeg) System.out.println("ERROR: No data begins");
        if (!dataEnds) System.out.println("ERROR: No data ends");
        if (!codeBeg) System.out.println("ERROR: No code begins");
        if (!codeEnds) System.out.println("ERROR: No code ends");

        if (!dataBeg || !dataEnds || !codeBeg || !codeEnds){
            return -1;
        }
//            if (lexemDats[count].getLexem().equalsIgnoreCase("DATA")
//                    && lexemDats[count + 1].getLexem().equalsIgnoreCase("ENDS")) {
//                if (flags[0] == 1) {
//                    flags[0] = 2;
//                } else {
//                    if (out) System.out.println("Error: no Data segment begins");
//                    flags[3]++;
//                }
//            }
//            if (lexemDats[count].getLexem().equalsIgnoreCase("CODE")
//                    && lexemDats[count + 1].getLexem().equalsIgnoreCase("SEGMENT")) {
//                if (flags[0] == 2) {
//                    flags[1] = 1;
//                } else {
//                    if (out) System.out.println("Error: no Data segment ends");
//                    flags[3]++;
//                }
//            }
//            if (lexemDats[count].getLexem().equalsIgnoreCase("CODE")
//                    && lexemDats[count + 1].getLexem().equalsIgnoreCase("ENDS")) {
//                if (flags[1] == 1) {
//                    flags[1] = 2;
//                } else {
//                    System.out.println("Error: no Code segment begins");
//                    flags[3]++;
//                }
//            }
//            count += placeOfStringEnd + 1;
//        }
//        if (flags[1] != 2) {
//            if (out) System.out.println("Error: no Code segment ends");
//            flags[3]++;
//        }
            return countOfErrors;
    }

    public static int dataSentaxErrors(LexemDat[] lexemMatrix, int[] errorMas, int stringOfLast, boolean out) {
        boolean flag = true;
        int count = 0;
        int value = 0;
        while (!lexemMatrix[count].getLexem().equalsIgnoreCase("data") ||
                !lexemMatrix[count + 1].getLexem().equalsIgnoreCase("ends")) {
            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, count, stringOfLast);
            if (flag && (lexemMatrix[count].getLexem().equalsIgnoreCase("data") &&
                    lexemMatrix[count + 1].getLexem().equalsIgnoreCase("segment"))) {
                flag = false;
                count += placeOfStringEnd + 1;
                continue;
            }
            if (!lexemMatrix[count].getType().equals("Undefined identifier")) {
                if (out) System.out.println("Error in string " + lexemMatrix[count].getString());
                errorMas[lexemMatrix[count].getString()] = 1;
                value++;
            } else {
                if (!(lexemMatrix[count + 1].getLexem().equalsIgnoreCase("dd") ||
                        lexemMatrix[count + 1].getLexem().equalsIgnoreCase("dw") ||
                        lexemMatrix[count + 1].getLexem().equalsIgnoreCase("db"))) {
                    if (out) System.out.println("Error in string " + lexemMatrix[count].getString());
                    errorMas[lexemMatrix[count].getString()] = 1;
                    value++;
                } else {
                    if (!(lexemMatrix[count + 2].getType().equals("Binary value") ||
                            lexemMatrix[count + 2].getType().equals("Hex value") ||
                            lexemMatrix[count + 2].getType().equals("Dec value"))) {
                        if (out) System.out.println("Error in string " + lexemMatrix[count].getString());
                        errorMas[lexemMatrix[count].getString()] = 1;
                        value++;
                    } else {
                        if (!(lexemMatrix[count].getString() == lexemMatrix[count + 2].getString())) {
                            if (out) System.out.println("Error in string " + lexemMatrix[count].getString());
                            errorMas[lexemMatrix[count].getString()] = 1;
                            value++;
                        }
                    }
                }
            }
            count += placeOfStringEnd + 1;
        }
        return value;
    }

    public static int commandsSentaxErrors(LexemDat[] lexemMatrix, Labels[] labels, DaTa daTa,
                                           int[] errorMas, int stringOfLast, boolean out) {
        int count = 0;
        int value = 0;
        boolean globalFlag = true;
        DataAsm dat = new DataAsm();
        while (!lexemMatrix[count].getLexem().equalsIgnoreCase("code") ||
                !lexemMatrix[count + 1].getLexem().equalsIgnoreCase("ends")) {

            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, count, stringOfLast);

            if (globalFlag && (lexemMatrix[count].getLexem().equalsIgnoreCase("code") &&
                    lexemMatrix[count + 1].getLexem().equalsIgnoreCase("segment"))) {
                globalFlag = false;
                count += placeOfStringEnd + 1;
                continue;
            }
            if (globalFlag) {
                count += placeOfStringEnd + 1;
                continue;
            }
            int flag = labeERROR(lexemMatrix, count, dat, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = cliERROR(lexemMatrix, count, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = incERROR(lexemMatrix, count, dat, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = decERROR(lexemMatrix, count, dat, daTa, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = addERROR(lexemMatrix, count, dat, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = cmpERROR(lexemMatrix, count, dat, daTa, placeOfStringEnd, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = addERROR(lexemMatrix, count, dat, daTa, placeOfStringEnd, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = movERROR(lexemMatrix, count, dat, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = orERROR(lexemMatrix, count, dat, daTa, placeOfStringEnd, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;

            flag = jnbERROR(lexemMatrix, count, labels, daTa, out);
            if (flag == 0) count += placeOfStringEnd + 1;
            if (flag == 1) {
                errorMas[lexemMatrix[count].getString()] = 1;
                count += placeOfStringEnd + 1;
                value++;
            }
            if (flag != -1) continue;
            else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                errorMas[lexemMatrix[count].getString()] = 1;
                value++;
            }
            count += placeOfStringEnd + 1;
        }
        return value;
    }

    private static int labeERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, boolean out) {
        if (lexemMatrix[count].getType().equals(dat.getUIname()) &&
                lexemMatrix[count + 1].getLexem().equals(":")) {
            if (lexemMatrix[count].getString() == lexemMatrix[count + 1].getString()) {
                return 0;
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int cliERROR(LexemDat[] lexemMatrix, int count, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("cli")) {
            if (lexemMatrix[count].getString() != lexemMatrix[count + 1].getString()) {
                return 0;
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int incERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("inc") &&
                (lexemMatrix[count + 1].getType().equals(dat.getR8name()) ||
                        lexemMatrix[count + 1].getType().equals(dat.getR32name()))) {
            if (lexemMatrix[count].getString() == lexemMatrix[count + 1].getString()) {
                return 0;
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int decERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, DaTa daTa, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("dec")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname()) && lexemMatrix[count + 2].getLexem().equals(":")) {
                shell += 2;
                if (lexemMatrix[count + 1].getType().equalsIgnoreCase("cs")) {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            }
            if (lexemMatrix[count + 2 + shell].getLexem().equals("[") &&
                    lexemMatrix[count + 3 + shell].getType().equals(dat.getR32name()) &&
                    lexemMatrix[count + 4 + shell].getLexem().equals("]")) {

                boolean foundFlag = false;
                for (int i = 0; i < daTa.getCountOfDataConstants(); i++) {
                    if (lexemMatrix[count + 1 + shell].getLexem().equalsIgnoreCase(daTa.getDataConstantsName(i))) {
                        foundFlag = true;
                        break;
                    }
                }
                if (foundFlag &&
                        lexemMatrix[count].getString() == lexemMatrix[count + 4 + shell].getString()) {
                    return 0;
                } else {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int addERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("add") &&
                (lexemMatrix[count + 1].getType().equals(dat.getR8name()) ||
                        lexemMatrix[count + 1].getType().equals(dat.getR32name())) &&
                lexemMatrix[count + 2].getLexem().equals(",") &&
                (lexemMatrix[count + 3].getType().equals(dat.getR8name()) ||
                        lexemMatrix[count + 3].getType().equals(dat.getR32name()))) {
            if (lexemMatrix[count].getString() == lexemMatrix[count + 3].getString()) {
                return 0;
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int cmpERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, DaTa daTa, int placeOfStringEnd,
                                boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("cmp")) {
            if ((lexemMatrix[count + 1].getType().equals(dat.getR8name()) ||
                    lexemMatrix[count + 1].getType().equals(dat.getR32name())) &&
                    lexemMatrix[count + 2].getLexem().equals(",")) {
                boolean foundFlag = false;
                int shell = 0;
                if (lexemMatrix[count + 3].getType().equals(dat.getSRname()) && lexemMatrix[count + 4].getLexem().equals(":")) {
                    shell += 2;
                    if (lexemMatrix[count + 3].getType().equalsIgnoreCase("cs")) {
                        if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        return 1;
                    }
                }
//                if (outOfRange(lexemDats, shell, count, placeOfStringEnd, daTa, 3, out)) return 1;

                for (int i = 0; i < daTa.getCountOfDataConstants(); i++) {
                    if (lexemMatrix[count + 3 + shell].getLexem().equalsIgnoreCase(daTa.getDataConstantsName(i))) {
                        foundFlag = true;
                        break;
                    }
                }
                if (foundFlag && lexemMatrix[count + 4 + shell].getLexem().equals("[") &&
                        lexemMatrix[count + 5 + shell].getType().equals(dat.getR32name()) &&
                        lexemMatrix[count + 6 + shell].getLexem().equals("]")) {
                    if (lexemMatrix[count].getString() == lexemMatrix[count + 6 + shell].getString()) {
                        return 0;
                    } else {
                        if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        return 1;
                    }
                } else {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int addERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, DaTa daTa, int placeOfStringEnd,
                                boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("add")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname())
                    && lexemMatrix[count + 2].getLexem().equals(":")) {
                shell += 2;
                if (lexemMatrix[count + 1].getType().equalsIgnoreCase("cs")) {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            }
//            if (outOfRange(lexemDats, shell, count, placeOfStringEnd, daTa, 1, out)) return 1;

            boolean foundFlag = false;
            for (int i = 0; i < daTa.getCountOfDataConstants(); i++) {
                if (lexemMatrix[count + 1 + shell].getLexem().equalsIgnoreCase(daTa.getDataConstantsName(i))) {
                    foundFlag = true;
                    break;
                }
            }
            if (foundFlag && lexemMatrix[count + 2 + shell].getLexem().equals("[") &&
                    lexemMatrix[count + 3 + shell].getType().equals(dat.getR32name()) &&
                    lexemMatrix[count + 4 + shell].getLexem().equals("]") &&
                    lexemMatrix[count + 5 + shell].getLexem().equals(",")) {
                if (lexemMatrix[count + 6 + shell].getType().equals(dat.getR8name()) ||
                        lexemMatrix[count + 6 + shell].getType().equals(dat.getR32name())) {
                    if (lexemMatrix[count].getString() == lexemMatrix[count + 6 + shell].getString()) {
                        return 0;
                    } else {
                        if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        return 1;
                    }
                } else {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
        }
        return -1;
    }

    private static int movERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("mov")) {//Mov
            if ((lexemMatrix[count + 1].getType().equals(dat.getR32name())
                    || lexemMatrix[count + 1].getType().equals(dat.getR8name()))
                    && lexemMatrix[count + 2].getLexem().equals(",")) {
                int i = 0;
                while (lexemMatrix[count + 3].getString() == lexemMatrix[count + 3 + i].getString()) {
                    if (lexemMatrix[count + 3 + i].getType().equals(dat.getDVname())
                            || lexemMatrix[count + 3 + i].getType().equals(dat.getBVname())
                            || lexemMatrix[count + 3 + i].getType().equals(dat.getHVname())
                            || lexemMatrix[count + 3 + i].getType().equals(dat.getOSLname())) {
                    } else {
                        if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        return 1;
                    }
                    i++;
                }
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
            return 0;
        }
        return -1;
    }

    private static int orERROR(LexemDat[] lexemMatrix, int count, DataAsm dat, DaTa daTa, int placeOfStringEnd,
                               boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("or")) {
            int shell = 0;
            if (lexemMatrix[count + 1].getType().equals(dat.getSRname())
                    && lexemMatrix[count + 2].getLexem().equals(":")) {
                shell += 2;
                if (lexemMatrix[count + 1].getType().equalsIgnoreCase("cs")) {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            }
            if (outOfRange(lexemMatrix, shell, count, placeOfStringEnd, daTa, out)) return 1;

            boolean foundFlag = false;
            for (int i = 0; i < daTa.getCountOfDataConstants(); i++) {
                if (lexemMatrix[count + 1 + shell].getLexem().equalsIgnoreCase(daTa.getDataConstantsName(i))) {
                    foundFlag = true;
                    break;
                }
            }
            if (foundFlag && lexemMatrix[count + 2 + shell].getLexem().equals("[") &&
                    lexemMatrix[count + 3 + shell].getType().equals(dat.getR32name()) &&
                    lexemMatrix[count + 4 + shell].getLexem().equals("]") &&
                    lexemMatrix[count + 5 + shell].getLexem().equals(",")) {
                int i = 0;
                while (lexemMatrix[count + 6 + shell].getString() == lexemMatrix[count + 6 + shell + i].getString()) {
                    if (lexemMatrix[count + 6 + shell + i].getType().equals(dat.getDVname())
                            || lexemMatrix[count + 6 + shell + i].getType().equals(dat.getBVname())
                            || lexemMatrix[count + 6 + shell + i].getType().equals(dat.getHVname())
                            || lexemMatrix[count + 6 + shell + i].getType().equals(dat.getOSLname())) {

                    } else {
                        if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        return 1;
                    }
                    i++;
                }
            } else {
                if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                return 1;
            }
            return 0;
        }
        return -1;
    }

    private static int jnbERROR(LexemDat[] lexemMatrix, int count, Labels[] labels, DaTa daTa, boolean out) {
        if (lexemMatrix[count].getLexem().equalsIgnoreCase("jnb")) {//Jnb
            boolean flag = true;
            for (int i = 0; i < daTa.getCountOfLastLabe(); i++) {
                if (lexemMatrix[count + 1].getLexem().equals(labels[i].getLexem())) {
                    flag = false;
                }
            }
            if (flag) {
                if (lexemMatrix[count].getString() == lexemMatrix[count + 1].getString()) {
                    if (out) System.out.println("ERROR in string " + lexemMatrix[count].getString());
                    return 1;
                }
            }
            return 0;
        }
        return -1;
    }

    private static boolean outOfRange(LexemDat[] lexemMatrix, int shell, int count, int placeOfStringEnd,
                                      DaTa daTa, boolean out) {
        boolean flag = true;
        int toShell = 0;
        for (int i = count; i <= count + placeOfStringEnd; i++) {
            if (lexemMatrix[i].getLexem().equals(",")) {
                toShell = i - count + 1;
                flag = false;
                break;
            }
        }

        if (flag) {
            return true;
        }
        int j = 0;
        while (j < daTa.getCountOfDataConstants()) {
            if (daTa.getDataConstantsType(j).equalsIgnoreCase("DB")) {
                if (daTa.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
                    double value = Calculate_Class.calculationFunc(lexemMatrix, count, toShell, placeOfStringEnd);
                    if (Math.abs(value) > 255) {
                        if (out) {
                            System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        }
                        return true;
                    }
                }
            }
            if (daTa.getDataConstantsType(j).equalsIgnoreCase("DW")) {
                if (daTa.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
                    double value = Calculate_Class.calculationFunc(lexemMatrix, count, toShell, placeOfStringEnd);
                    if (Math.abs(value) > 65535) {
                        if (out) {
                            System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        }
                        return true;
                    }
                }
            }
            if (daTa.getDataConstantsType(j).equalsIgnoreCase("DD")) {
                if (daTa.getDataConstantsName(j).equals(lexemMatrix[count + shell + 1].getLexem())) {
                    double value = Calculate_Class.calculationFunc(lexemMatrix, count, toShell, placeOfStringEnd);
                    if (Math.abs(value) > 4_294_967_295L) {
                        if (out) {
                            System.out.println("ERROR in string " + lexemMatrix[count].getString());
                        }
                        return true;
                    }
                }
            }
            j++;
        }
        return false;
    }
}