package control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Conexion extends SQLiteOpenHelper {
    private String strCreate_table_subject;
    private String strCreate_table_homewok;
    private String strCreate_table_foto;

    Conexion(Context context) {
        super(context, StringSql.DB_NAME, null, StringSql.DB_VERSION);
        strCreate_table_subject = "CREATE TABLE " + StringSql.NAME_TABLE_SUBJET + " (" +
                StringSql.SUBJET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StringSql.SUBJET_NAME + " TEXT NOT NULL)";

        strCreate_table_homewok = "CREATE TABLE " + StringSql.NAME_TABLE_CLASS + "(" +
                StringSql.CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StringSql.CLASS_DESC + " TEXT NOT NULL, " +
                StringSql.CLASS_SUBJET + " TEXT NOT NULL, " +
                StringSql.CLASS_DATE + " TEXT NOT NULL, " +
                StringSql.CLASS_TIME + " TEXT NOT NULL, " +
                StringSql.CLASS_STATE + " CHAR(1) DEFAULT '1')";

        strCreate_table_foto = "CREATE TABLE " + StringSql.NAME_TABLE_FOTO + " (" +
                StringSql.FOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StringSql.FOTO_ID_CLASS + " INTEGER, " +
                StringSql.FOTO_FOTO + " BLOB, FOREIGN KEY (" + StringSql.FOTO_ID_CLASS + ") REFERENCES " +
                StringSql.NAME_TABLE_CLASS + "(" + StringSql.CLASS_ID + "))";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strCreate_table_subject);
        db.execSQL(strCreate_table_homewok);
        db.execSQL(strCreate_table_foto);
        Log.i("VAR", strCreate_table_homewok);
        Log.i("VAR", strCreate_table_subject);
        Log.i("VAR", strCreate_table_foto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(StringSql.DELETE_TABLE + " " + StringSql.NAME_TABLE_SUBJET);
        db.execSQL(StringSql.DELETE_TABLE + " " + StringSql.NAME_TABLE_CLASS);
        onCreate(db);
    }
}
