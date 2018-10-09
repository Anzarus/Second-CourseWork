package data;

public class Labels {
    private String lexem;
    private int string;
    private int place;

    public void setLabel(String lex, int strin, int plac) {
        this.lexem = lex;
        this.string = strin;
        this.place = plac;
    }

    public String getLexem() {
        return this.lexem;
    }

    public int getString() {
        return this.string;
    }

    public int getPlace() {
        return this.place;
    }
}
