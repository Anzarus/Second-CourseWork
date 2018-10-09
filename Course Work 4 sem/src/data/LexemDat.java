package data;

public class LexemDat {
    private String lexem;
    private String type; //Todo: think about type of it
    private int string;
    private int place;

    public void setLexem(String lex, String sig, int strin, int plac) {
        this.lexem = lex;
        this.type = sig;
        this.string = strin;
        this.place = plac;
    }

    public String getLexem() {
        return this.lexem;
    }

    public String getType() {
        return this.type;
    }

    public int getString() {
        return this.string;
    }

    public int getPlace() {
        return this.place;
    }
}