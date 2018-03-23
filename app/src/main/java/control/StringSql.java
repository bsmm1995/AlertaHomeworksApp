package control;

public class StringSql {
    static final String DB_NAME = "dbHomeworks";
    static final String NAME_TABLE_SUBJET = "subject";
    static final String NAME_TABLE_CLASS = "homework";
    static final String NAME_TABLE_FOTO = "foto";
    static final int DB_VERSION = 1;

    static final String SUBJET_ID = "id_subjet";
    static final String SUBJET_NAME = "name_subjet";

    public static final String CLASS_ID = "id_class";
    static final String CLASS_SUBJET = "subjet_class";
    static final String CLASS_DESC = "desc_class";
    static final String CLASS_DATE = "date_class";
    static final String CLASS_TIME = "time_class";
    static final String CLASS_STATE = "state_class";

    public static final String FOTO_ID = "id_foto";
    static final String FOTO_ID_CLASS = "id_class";
    static final String FOTO_FOTO = "foto";

    static final String DELETE_TABLE = "DROP TABLE IF EXISTS";
}
