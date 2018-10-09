package listing;

public class Lexem {
    private String lexem;
    private String type;
    private String value;
    private String Attr;

    Lexem(String lexem, String type, String value, String Attr) {
        this.lexem = lexem;
        this.type = type;
        this.value = value;
        this.Attr = Attr;
    }

    public void setAttr(String attr) {
        this.Attr = attr;
    }

    public String getAttr() {
        return Attr;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }

    public String getLexem() {
        return lexem;
    }
}
