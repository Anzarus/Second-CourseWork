package data;

public class DataAsm {
    private String oneSymbolLexems = ", [ ] : + - * ( ) ;";
    public String getOneSymbolLexems() {
        return oneSymbolLexems;
    }

    private String OSLname = "oneSymbolsLexeme";
    public String getOSLname() {
        return OSLname;
    }

    private String command = "cli inc mov dec add cmp jnb or";
    public String getCommand() {
        return command;
    }

    private String Cname = "Command";
    public String getCname() {
        return Cname;
    }

    private String directive = "end segment ends db dw dd";
    public String getDirective() {
        return directive;
    }

    private String Dname = "Directive";
    public String getDname() {
        return Dname;
    }

    private String register32 = "eax ecx edx ebx esp ebp esi edi";
    public String getRegister32() {
        return register32;
    }

    private String R32name = "Register 32bit";
    public String getR32name() {
        return R32name;
    }

    private String register8 = "al cl dl bl ah ch dh bh";
    public String getRegister8() {
        return register8;
    }

    private String R8name = "Register 8bit";
    public String getR8name() {
        return R8name;
    }

    private String BVname = "Binary value";
    public String getBVname() {
        return BVname;
    }

    private String HVname = "Hex value";
    public String getHVname() {
        return HVname;
    }

    private String DVname = "Dec value";
    public String getDVname() {
        return DVname;
    }

    private String UIname = "Undefined identifier";
    public String getUIname() {
        return UIname;
    }

    private String SegmentReg = "es cs ss ds fs gs";
    public String getSegmentReg() {
        return SegmentReg;
    }

    private String SRname = "Segment exchange reg";
    public String getSRname() {
        return SRname;
    }

    private String SegmentRegNumb = "26h 2Eh 36h 3Eh 64h 65h";
    public String getSegmentRegNumb() {
        return SegmentRegNumb;
    }
}