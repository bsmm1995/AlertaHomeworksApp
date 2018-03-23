package model;

public class Subjet {
    private int inId;
    private String stName;

    public void Subjet() {
    }

    public  Subjet(int inId, String stName) {
        this.inId = inId;
        this.stName = stName;
    }

    public int getInId() {
        return inId;
    }

    public void setInId(int inId) {
        this.inId = inId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }
}
