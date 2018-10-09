package data;

public class DaTa {
    private int countOfLastLabe;

    private String[] dataConstantsName = new String[10];
    private String[] dataConstantsValue = new String[10];
    private String[] dataConstantsType = new String[10];
    private int countOfDataConstants = 0;

    public void setDataConstantsName(String dataConstant, int numbInArray) {
        this.dataConstantsName[numbInArray] = dataConstant;
    }
    public String getDataConstantsName(int numbInArray) {
        return this.dataConstantsName[numbInArray];
    }

    public void setDataConstantsValue(String dataConstant, int numbInArray) {
        this.dataConstantsValue[numbInArray] = dataConstant;
    }
    public String getDataConstantsValue(int numbInArray) {
        return this.dataConstantsValue[numbInArray];
    }

    public void setDataConstantsType(String dataConstant, int numbInArray) {
        this.dataConstantsType[numbInArray] = dataConstant;
    }
    public String getDataConstantsType(int numbInArray) {
        return this.dataConstantsType[numbInArray];
    }

    public void incCountOfDataConstants() {
        this.countOfDataConstants++;
    }
    public int getCountOfDataConstants() {
        return this.countOfDataConstants;
    }

    public void setCountOfLastLabe(int countOfLastLabe) {
        this.countOfLastLabe = countOfLastLabe;
    }
    public int getCountOfLastLabe() {
        return this.countOfLastLabe;
    }

}
