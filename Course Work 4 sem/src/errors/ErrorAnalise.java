package errors;

import data.*;
import errors.ErrorFunk.*;

public class ErrorAnalise {
    public static int errAnaliser(LexemDat[] lexemMatrix, Labels[] labe, DaTa DaTa,int[] errorMas, int stringOfLast) {
        int quantityOfErrors = 0;
        DataAsm dat = new DataAsm();
        quantityOfErrors += ErrorFunk.segmentsErrors(lexemMatrix, stringOfLast, true);
        if (quantityOfErrors == 0) {
            quantityOfErrors += ErrorFunk.dataSentaxErrors(lexemMatrix,errorMas, stringOfLast, true);
            quantityOfErrors += ErrorFunk.commandsSentaxErrors(lexemMatrix, labe, DaTa,errorMas, stringOfLast, true);
            quantityOfErrors += ErrorFunk.lexemErrors(lexemMatrix,errorMas, stringOfLast, dat, true);
        }else{
            return -1;
        }
//        ErrorFunk.outOfValue(lexemMatrix, 1,1,1,"dd");
        return quantityOfErrors;
    }
}
