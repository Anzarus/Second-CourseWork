package data;

public class OperandDat {
    public String[] possibleOperandLexems = {   "   Any   data   Register   ",
                                                "     Operator     ptr      ",
                                                "Any segment exchange prefix",
                                                "  Any  labe  identificator ",
                                                "Any undefined identificator",
                                                "  First  address  register ",
                                                " Second  address  register ",
                                                "      Any   multiplier     ",
                                                " offset,   seg   operators ",
                                                "     Any     constants     "};
    public boolean[] possibleOperands = new boolean[10];
    public String[] mainProperties = new String[10];
    public String[] secondProperties = new String[10];

    public void setOperandDatZero() {
        int j = 0;
        while (j < 10) {
            this.possibleOperands[j] = false;
            this.mainProperties[j] = "";
            this.secondProperties[j] = "";
            j++;
        }
    }
}
