package model;

import android.graphics.Bitmap;

public class Imagen {

    public Bitmap getImg() {
        return img;
    }
    public void setImg(Bitmap img) {
        this.img = img;
    }
    Bitmap  img;

    public int getId_homework() {
        return id_homework;
    }

    public void setId_homework(int id_homework) {
        this.id_homework = id_homework;
    }

    public int getId_Img() {
        return id_Img;
    }

    public void setId_Img(int id_Img) {
        this.id_Img = id_Img;
    }

    int id_homework;
    int id_Img;

}
