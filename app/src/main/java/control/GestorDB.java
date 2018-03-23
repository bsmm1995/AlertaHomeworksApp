package control;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bsmm.bsmm.homeworks.ServicioNotificacion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import model.Homework;
import model.Imagen;
import model.Subjet;

public class GestorDB {
    private Conexion conn;

    public GestorDB(Context context) {
        conn = new Conexion(context);
    }

    public void insertSubject(String name) {
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(StringSql.SUBJET_NAME, name);
            db.insert(StringSql.NAME_TABLE_SUBJET, null, values);
            db.close();
        }
    }

    @SuppressLint("Recycle")
    public ArrayList<Subjet> getAllSubject() {
        ArrayList<Subjet> lista = new ArrayList<>();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(StringSql.NAME_TABLE_SUBJET, new String[]{StringSql.SUBJET_ID, StringSql.SUBJET_NAME}, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                lista.add(new Subjet(id, name));
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    public void deleteSubject(Subjet subjet) {
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            db.delete(StringSql.NAME_TABLE_SUBJET, StringSql.SUBJET_ID + "=?", new String[]{"" + subjet.getInId()});
        }
        assert db != null;
        db.close();
    }

    public boolean existsSubjet(String stName) {
        SQLiteDatabase db = conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_SUBJET, new String[]{StringSql.SUBJET_ID},
                StringSql.SUBJET_NAME + "=?", new String[]{stName}, null, null, null);
        return cursor.getCount() > 0;
    }

    ////////////////////////////////////////////////////7
    public ArrayList<Homework> getAllHomeworks() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Homework> lista = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_CLASS, new String[]{StringSql.CLASS_ID, StringSql.CLASS_DESC, StringSql.CLASS_SUBJET, StringSql.CLASS_DATE, StringSql.CLASS_TIME}, StringSql.CLASS_STATE + "=?", new String[]{"1"}, null, null, StringSql.CLASS_ID + " DESC");
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            do {
                Homework obj = new Homework();
                int id = cursor.getInt(0);
                obj.setInId(id);
                obj.setStDesc(cursor.getString(1));
                obj.setStSubjet(cursor.getString(2));
                obj.setStDate(cursor.getString(3));
                obj.setStTime(cursor.getString(4));
                lista.add(obj);
            } while (cursor.moveToNext());
        return lista;
    }

    public ArrayList<Homework> getAllHomeworksTotal() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Homework> lista = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_CLASS, new String[]{StringSql.CLASS_ID, StringSql.CLASS_DESC, StringSql.CLASS_SUBJET, StringSql.CLASS_DATE, StringSql.CLASS_TIME}, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            do {
                Homework obj = new Homework();
                int id = cursor.getInt(0);
                obj.setInId(id);
                obj.setStDesc(cursor.getString(1));
                obj.setStSubjet(cursor.getString(2));
                obj.setStDate(cursor.getString(3));
                obj.setStTime(cursor.getString(4));
                lista.add(obj);
            } while (cursor.moveToNext());
        return lista;
    }


    @SuppressLint("Recycle")
    public Homework getHomework(int id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(StringSql.NAME_TABLE_CLASS, new String[]{StringSql.CLASS_ID, StringSql.CLASS_DESC, StringSql.CLASS_SUBJET, StringSql.CLASS_DATE, StringSql.CLASS_TIME}, StringSql.CLASS_ID + "=?", new String[]{id + ""}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            Homework obj = new Homework();
            obj.setInId(cursor.getInt(0));
            obj.setStDesc(cursor.getString(1));
            obj.setStSubjet(cursor.getString(2));
            obj.setStDate(cursor.getString(3));
            obj.setStTime(cursor.getString(4));

            getImgOfHomework(String.valueOf(obj.getInId()));
            return obj;
        }
        db.close();
        return null;
    }


    public void archivarHomewok(Homework homework) {
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(StringSql.CLASS_STATE, "0");
            db.update(StringSql.NAME_TABLE_CLASS, values, StringSql.CLASS_ID + "=?", new String[]{homework.getInId() + ""});
        }
        assert db != null;
        db.close();
    }

    public void insertHomework(Homework homework) {
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {

            ContentValues values = new ContentValues();
            values.put(StringSql.CLASS_DESC, homework.getStDesc());
            values.put(StringSql.CLASS_SUBJET, homework.getStSubjet());
            values.put(StringSql.CLASS_DATE, homework.getStDate());
            values.put(StringSql.CLASS_TIME, homework.getStTime());
            db.insert(StringSql.NAME_TABLE_CLASS, null, values);

            if (homework.getImgs().size() > 0) {
                int inIdClass = getIdClass();
                insertFotos(homework.getImgs(), inIdClass);
            }

            db.close();
        }
    }

    private int getIdClass() {
        SQLiteDatabase db = conn.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_CLASS, new String[]{StringSql.CLASS_ID}, null, null, null, null, StringSql.CLASS_ID + "  DESC", "1");
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    private void insertFotos(ArrayList<Imagen> list, int id_class) {
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            for (Imagen img : list) {
                ContentValues values = new ContentValues();
                values.put(StringSql.FOTO_ID_CLASS, id_class);
                //////////////
                ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                img.getImg().compress(Bitmap.CompressFormat.JPEG, 0, baos);
                byte[] blob = baos.toByteArray();
                /////////////777
                values.put(StringSql.FOTO_FOTO, blob);
                db.insert(StringSql.NAME_TABLE_FOTO, null, values);
            }
            db.close();
        }
    }

    public ArrayList<Imagen> getImgOfHomework(String stId_class) {
        SQLiteDatabase db = conn.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_FOTO, new String[]{StringSql.FOTO_ID, StringSql.FOTO_ID_CLASS, StringSql.FOTO_FOTO}, StringSql.FOTO_ID_CLASS + "=?", new String[]{stId_class}, null, null, null);
        cursor.moveToFirst();
        ArrayList<Imagen> lista = new ArrayList<>();
        if (cursor.getCount() > 0)
            do {
                Imagen img = new Imagen();
                img.setId_Img(cursor.getInt(0));
                img.setId_homework(cursor.getInt(1));

                byte[] blob = cursor.getBlob(2);
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                img.setImg(BitmapFactory.decodeStream(bais));

                lista.add(img);
            } while (cursor.moveToNext());

        return lista;
    }

    public Imagen getImagenById(int id) {
        Imagen img = new Imagen();
        SQLiteDatabase db = conn.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_FOTO, new String[]{StringSql.FOTO_FOTO}, StringSql.FOTO_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            img.setImg(BitmapFactory.decodeStream(bais));
        }
        return img;
    }


    ////////////////7
    public ArrayList<String> getAllTimesHomeworks() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> lista = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query(StringSql.NAME_TABLE_CLASS, new String[]{StringSql.CLASS_TIME}, StringSql.CLASS_STATE + "=?", new String[]{"1"}, null, null, StringSql.CLASS_ID + " DESC");
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        return lista;
    }
}
