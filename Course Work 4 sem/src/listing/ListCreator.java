package listing;

import analizator.sentax.SentencesAnalise;
import data.DaTa;
import data.DataAsm;
import data.LexemDat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static listing.Shelling.*;

public class ListCreator {

    public static void fWriter(LexemDat[] lexemMatrix, int stringOfLast, DaTa daTa, String fileName,
                               int quantityOfErrors, int[] errorMas) throws IOException {
        DataAsm dat = new DataAsm();
        FileWriter writer = new FileWriter("Results/Listing.txt");
        String[] shellMassiveValue = new String[stringOfLast + 1];
        int[] dataShellMassive = shell(lexemMatrix, stringOfLast, daTa);
        int shell = 0;
        int counter = 0;
        boolean flag = false;

        shell += dataShellMassive[0];
        writer.write(calculateHex(shell) + "\t\t");

        for (int i = 0; i < dataShellMassive.length; i++) {
            int placeOfStringEnd = SentencesAnalise.findPlaceOfStringEnd(lexemMatrix, counter, stringOfLast);

            if (lexemMatrix[counter].getLexem().equalsIgnoreCase("data")
                    && lexemMatrix[counter + 1].getLexem().equals("ENDS")) {
                flag = false;
                shell = 0;
            }
            if (flag) writer.write("\t");
            if (lexemMatrix[counter].getLexem().equalsIgnoreCase("data")
                    && lexemMatrix[counter + 1].getLexem().equals("SEGMENT")) {
                flag = true;
            }

            for (int j = 0; j <= placeOfStringEnd; j++) {
                if (j == 1 && lexemMatrix[counter].getLexem().equalsIgnoreCase("data")
                        && lexemMatrix[counter + 1].getLexem().equals("ENDS")) {
                    writer.write(" " + lexemMatrix[counter + 1].getLexem() + "\n");
                    continue;
                }
                if (lexemMatrix[counter + j].getType().equals(dat.getCname())) {
                    writer.write("\t" + lexemMatrix[counter + j].getLexem() + "\t");
                    continue;
                }
                if (lexemMatrix[counter + j].getType().equals(dat.getDname())) {
                    writer.write(" " + lexemMatrix[counter + j].getLexem() + " ");
                    continue;
                }
                if (lexemMatrix[counter + j].getType().equals(dat.getSRname())) {
                    writer.write(" " + lexemMatrix[counter + j].getLexem() + " ");
                    continue;
                }
                writer.write(lexemMatrix[counter + j].getLexem());
            }
            if (errorMas[lexemMatrix[counter].getString()] == 1)
                writer.write("\n\t\t\t;Error " + lexemMatrix[counter].getString() + "\n");
            else writer.write("\n");

            shell += dataShellMassive[i];

            shellMassiveValue[i] = calculateHex(shell);
            if (i != dataShellMassive.length - 1) {
                writer.write(shellMassiveValue[i] + "\t\t");
            } else {
                writer.write("\t\t\t");
            }

            counter += placeOfStringEnd + 1;
        }
        writer.write(lexemMatrix[counter].getLexem());

        lexemTable(lexemMatrix, writer, dat, shellMassiveValue, stringOfLast, daTa, fileName);
        writer.write("\n" + quantityOfErrors + "\tErrors\n");

        writer.close();
    }

    private static String calculateHex(int shellBefore) {
        String hex = "";

        for (int i = 0; i < 4; i++) {
            switch (shellBefore % 16) {
                case 0:
                    hex = newString(hex, "0");
                    shellBefore /= 16;
                    break;
                case 1:
                    hex = newString(hex, "1");
                    shellBefore /= 16;
                    break;
                case 2:
                    hex = newString(hex, "2");
                    shellBefore /= 16;
                    break;
                case 3:
                    hex = newString(hex, "3");
                    shellBefore /= 16;
                    break;
                case 4:
                    hex = newString(hex, "4");
                    shellBefore /= 16;
                    break;
                case 5:
                    hex = newString(hex, "5");
                    shellBefore /= 16;
                    break;
                case 6:
                    hex = newString(hex, "6");
                    shellBefore /= 16;
                    break;
                case 7:
                    hex = newString(hex, "7");
                    shellBefore /= 16;
                    break;
                case 8:
                    hex = newString(hex, "8");
                    shellBefore /= 16;
                    break;
                case 9:
                    hex = newString(hex, "9");
                    shellBefore /= 16;
                    break;
                case 10:
                    hex = newString(hex, "A");
                    shellBefore /= 16;
                    break;
                case 11:
                    hex = newString(hex, "B");
                    shellBefore /= 16;
                    break;
                case 12:
                    hex = newString(hex, "C");
                    shellBefore /= 16;
                    break;
                case 13:
                    hex = newString(hex, "D");
                    shellBefore /= 16;
                    break;
                case 14:
                    hex = newString(hex, "E");
                    shellBefore /= 16;
                    break;
                case 15:
                    hex = newString(hex, "F");
                    shellBefore /= 16;
                    break;
            }
        }
        return hex;
    }

    private static String newString(String old, String newPart) {
        return newPart.concat(old);
    }

    private static void lexemTable(LexemDat[] lexemMatrix, FileWriter writer, DataAsm dat,
                                   String[] shellMassiveValue, int stringOfLast, DaTa daTa, String fileName) throws IOException {
        writer.write("\n\n");
        int count = 0;
        Lexem[] segmentsUI = new Lexem[2];
        ArrayList<Lexem> ui = new ArrayList<>();
        int i = 0;
        boolean dataFlag = false;
        boolean codeFlag = false;

        while (lexemMatrix[count].getString() != stringOfLast) {
            if (lexemMatrix[count].getLexem().equalsIgnoreCase("data")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("segment")) {
                dataFlag = true;
            }
            if (lexemMatrix[count].getLexem().equalsIgnoreCase("code")
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("segment")) {
                codeFlag = true;
            }

            if (lexemMatrix[count].getType().equals(dat.getUIname())
                    && lexemMatrix[count + 1].getLexem().equalsIgnoreCase("ends")) {
                if (lexemMatrix[count].getLexem().equalsIgnoreCase("data")) {
                    dataFlag = false;
                }
                if (lexemMatrix[count].getLexem().equalsIgnoreCase("code")) {
                    codeFlag = false;
                }

                Lexem lexemBuff = new Lexem(lexemMatrix[count].getLexem(), "32 Bit",
                        shellMassiveValue[lexemMatrix[count].getString() - 1], "PARA");
                segmentsUI[i] = lexemBuff;
                i++;
                count++;
                continue;
            }
            if (lexemMatrix[count].getType().equals(dat.getUIname())
                    && !lexemMatrix[count + 1].getLexem().equalsIgnoreCase("segment")) {
                String Attr = "";
                if (dataFlag) {
                    Attr = "DATA";
                } else if (codeFlag) {
                    Attr = "CODE";
                } else {
                }
                boolean flagFound = false;
                int j = 0;
                for (; j < daTa.getCountOfDataConstants(); j++) {
                    if (lexemMatrix[count].getLexem().equalsIgnoreCase(daTa.getDataConstantsName(j))) {
                        flagFound = true;
                        break;
                    }
                }
                if (flagFound) {
                    Lexem lexemBuff = new Lexem(lexemMatrix[count].getLexem(), "L " + daTa.getDataConstantsType(j),
                            shellMassiveValue[lexemMatrix[count].getString() - 1], Attr);
                    ui.add(lexemBuff);
                } else if (lexemMatrix[count + 1].getLexem().equals(":")) {
                    Lexem lexemBuff = new Lexem(lexemMatrix[count].getLexem(), "L NEAR",
                            shellMassiveValue[lexemMatrix[count].getString() - 1], Attr);
                    ui.add(lexemBuff);
                }
                flagFound = false;
                for (int k = 0; k < ui.size() - 1; k++) {
                    if (lexemMatrix[count].getLexem().equals(ui.get(k).getLexem())) {
                        flagFound = true;
                        break;
                    }
                }
                if (flagFound) {
                    ui.remove(ui.size() - 1);
                }

            }
            count++;
        }
        segLexemTableSorting(segmentsUI);
        lexemTableSorting(ui);
        lexemTableWriter(writer, ui, segmentsUI, fileName);
    }

    private static void segLexemTableSorting(Lexem[] segmentsUI) {
        for (int i = 0; i < segmentsUI.length - 1; i++) {
            char[] str1 = segmentsUI[i].getLexem().toCharArray();
            char[] str2 = segmentsUI[i + 1].getLexem().toCharArray();
            int length;
            if (str1.length <= str2.length) {
                length = str1.length;
            } else {
                length = str2.length;
            }
            boolean flag = false;
            for (int j = 0; j < length; j++) {
                if (str1[j] <= str2[j]) {
                } else {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                Lexem buff = segmentsUI[0];
                segmentsUI[0] = segmentsUI[1];
                segmentsUI[1] = buff;
            }
        }
    }

    private static void lexemTableSorting(ArrayList<Lexem> ui) {
        for (int i = 0; i < ui.size() - 1; i++) {
            char[] str1 = ui.get(i).getLexem().toLowerCase().toCharArray();
            for (int j = i + 1; j < ui.size(); j++) {
                char[] str2 = ui.get(j).getLexem().toLowerCase().toCharArray();
                int length;
                if (str1.length <= str2.length) {
                    length = str1.length;
                } else {
                    length = str2.length;
                }
                boolean flag = false;

                for (int k = 0; k < length; k++) {
                    if (str1[k] == str2[k]) {
                    } else if (str1[k] < str2[k]) {
                        break;
                    } else {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    Lexem buff = ui.get(i);
                    ui.set(i, ui.get(j));
                    ui.set(j, buff);
                    str1 = ui.get(i).getLexem().toLowerCase().toCharArray();
                }
            }
        }
    }

    private static void lexemTableWriter(FileWriter writer, ArrayList<Lexem> ui, Lexem[] segmentsUI, String fileName) throws IOException {
        writer.write(String.format("%27s       \t%-6s\t%s\t%s\t%s\n\n",
                "N a m e", "Size", "Length", "Align", "Combine Class"));
        for (Lexem aSegmentsUI : segmentsUI) {
            writer.write(String.format("%-34S\t%-6S\t%S\t%S\tNONE\n",
                    aSegmentsUI.getLexem(), aSegmentsUI.getType(), aSegmentsUI.getValue(), aSegmentsUI.getAttr()));
        }
        writer.write("\n\n\n");
        writer.write(String.format("%27s       \t%-6s\t%s\t%s\n\n",
                "N a m e", "Type", "Value", "Attr"));
        for (Lexem anUi : ui) {
            writer.write(String.format("%-34S\t%-6S\t%S\t%S\n",
                    anUi.getLexem(), anUi.getType(), anUi.getValue(), anUi.getAttr()));
        }
        writer.write(String.format("\n%-34S\t%-6S\t%s\n",
                "@Filename", "text", fileName));
    }
}
