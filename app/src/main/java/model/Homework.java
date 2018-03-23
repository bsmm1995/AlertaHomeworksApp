package model;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Homework {
    private int inId;
    private String stDesc;
    private String stSubjet;
    private String stDate;
    private String stTime;

    public Homework() {

    }

    private ArrayList<Imagen> imgs = new ArrayList<>();

    public String getStSubjet() {
        return stSubjet;
    }

    public void setStSubjet(String stSubjet) {
        this.stSubjet = stSubjet;
    }

    public String getStDesc() {
        return stDesc;
    }

    public void setStDesc(String stDesc) {
        this.stDesc = stDesc;
    }

    public String getStDate() {
        return stDate;
    }

    public void setStDate(String stDate) {
        this.stDate = stDate;
    }

    public int getInId() {
        return inId;
    }

    public void setInId(int inId) {
        this.inId = inId;
    }

    public ArrayList<Imagen> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<Imagen> imgs) {
        this.imgs = imgs;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }
}
