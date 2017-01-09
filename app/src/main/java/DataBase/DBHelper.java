package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bryan on 1/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "eecc";
    private static final int DB_SCHEME_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE_CANTONS);
        db.execSQL(DataBaseManager.CREATE_TABLE_CORROSIONDAMAGEINDEXES);
        db.execSQL(DataBaseManager.CREATE_TABLE_COUNTRIES);
        db.execSQL(DataBaseManager.CREATE_TABLE_DISTRICTS);
        db.execSQL(DataBaseManager.CREATE_TABLE_ES);
        db.execSQL(DataBaseManager.CREATE_TABLE_ESINFORMATION);
        db.execSQL(DataBaseManager.CREATE_TABLE_EVALUATIONS);
        db.execSQL(DataBaseManager.CREATE_TABLE_IAAINFORMATION);
        db.execSQL(DataBaseManager.CREATE_TABLE_IAL);
        db.execSQL(DataBaseManager.CREATE_TABLE_IALINFORMATION);
        db.execSQL(DataBaseManager.CREATE_TABLE_IAT);
        db.execSQL(DataBaseManager.CREATE_TABLE_IATINFORMATION);
        db.execSQL(DataBaseManager.CREATE_TABLE_IDCINDICATORS);
        db.execSQL(DataBaseManager.CREATE_TABLE_IDE);
        db.execSQL(DataBaseManager.CREATE_TABLE_IDEINFORMATION);
        db.execSQL(DataBaseManager.CREATE_TABLE_INDICATORNAMES);
        db.execSQL(DataBaseManager.CREATE_TABLE_PROJECTS);
        db.execSQL(DataBaseManager.CREATE_TABLE_PROVINCES);
        db.execSQL(DataBaseManager.CREATE_TABLE_STRUCTURALDAMAGEINDEXES);
        db.execSQL(DataBaseManager.CREATE_TABLE_STRUCTURALINDEXES);
        db.execSQL(DataBaseManager.CREATE_TABLE_STRUCTURETYPES);
        db.execSQL(DataBaseManager.CREATE_TABLE_TOKENS);
        db.execSQL(DataBaseManager.CREATE_TABLE_USERS);
        db.execSQL(DataBaseManager.CREATE_TABLE_CONTACTO);
        db.execSQL(DataBaseManager.CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
