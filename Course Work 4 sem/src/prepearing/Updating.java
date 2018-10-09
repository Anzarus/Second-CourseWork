package prepearing;

import data.DataAsm;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

public class Updating {
    public static String addSpace(Path path) throws Exception {
        FileReader read = new FileReader(String.valueOf(path));
        Scanner scan = new Scanner(read);
        DataAsm dat = new DataAsm();
        String str, fileName2 = "Work_files/WorkAsm.asm";
        boolean dataEnds = false;
        char[] lexem = new char[10];
        FileWriter writer = new FileWriter(fileName2);

        int i = 0;
        for (String oneSymbol : dat.getOneSymbolLexems().split(" ")) {
            lexem[i] = oneSymbol.charAt(0);
            i++;
        }

        while (scan.hasNextLine()) {
            str = scan.nextLine();
            if (str.equals("DATA ENDS")) {
                dataEnds = true;
            }
            if (!dataEnds) {
                dataSpace(writer, str, str.length(), lexem);
//                writer.write(str);
                writer.write("\n");
                continue;
            }
            space(writer, str, str.length(), lexem);
            writer.write("\n");
        }
        read.close();
        writer.close();
        return fileName2;
    }

    private static void space(FileWriter writer, String str, int length, char[] lexem) throws Exception {
        char[] strCh;
        strCh = str.toCharArray();
        boolean found;

        for (int i = 0; i < length; i++) {
            found = false;
            for (int j = 0; j < 10; j++) {
                if (strCh[i] == lexem[j]) {
                    writer.write(" " + strCh[i] + " ");
                    found = true;
                    break;
                }
            }
            if (!found) {
                writer.write(strCh[i]);
            }
        }
    }

    private static void dataSpace(FileWriter writer, String str, int length, char[] lexem) throws Exception {
        char[] strCh;
        strCh = str.toCharArray();
        boolean found;

        for (int i = 0; i < length; i++) {
            found = false;
            if (strCh[i] == lexem[9]) {
                writer.write(" " + strCh[i] + " ");
                found = true;
            }
            if (!found) {
                writer.write(strCh[i]);
            }
        }
    }
}