package Main;

import analizator.lexical.LexAnalise;
import data.*;
import errors.ErrorAnalise;
import listing.ListCreator;
import prepearing.Updating;
import analizator.sentax.SentencesAnalise;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = "asm1";
        Path path = Paths.get("Resources/" + fileName + ".asm");
        LexemDat[] lexemMatrix = new LexemDat[500];
        Labels[] labe = new Labels[20];
        DaTa DaTa = new DaTa();

        String workFile = Updating.addSpace(path);

        int stringOfLast = LexAnalise.reader(workFile, lexemMatrix, labe, DaTa);
        int[] errorMas = new int[stringOfLast];
        int quantityOfErrors = ErrorAnalise.errAnaliser(lexemMatrix, labe, DaTa, errorMas, stringOfLast);
        if (quantityOfErrors == -1) {
            System.out.println("=========================================================");
            System.out.println("FOUND SEGMENTS ERRORS");
            return;
        }
        SentencesAnalise.analiseSenFunk(lexemMatrix, labe, DaTa, stringOfLast);
        ListCreator.fWriter(lexemMatrix, stringOfLast, DaTa, fileName, quantityOfErrors, errorMas);
    }
}