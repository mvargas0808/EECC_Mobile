package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Bryan on 1/7/2017.
 */

public class DataBaseManager {

    private DBHelper helper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void openConnection(){
        db = helper.getWritableDatabase();
    }

    public void closeConnection(){
        helper.close();
    }

    // Table: cantons
    public static final String CREATE_TABLE_CANTONS = "CREATE TABLE Cantons (CantonId INTEGER NOT NULL PRIMARY KEY , CantonName VARCHAR(60) NOT NULL, ProvinceId INTEGER NOT NULL, FOREIGN KEY (ProvinceId) REFERENCES Provinces (ProvinceId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: corrosiondamageindexes
    public static final String CREATE_TABLE_CORROSIONDAMAGEINDEXES = "CREATE TABLE CorrosionDamageIndexes (CorrosionDamageIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDC DECIMAL(5,3) NOT NULL, IAA DECIMAL(5,3) NOT NULL, ISC DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, IAAIndicatorId INTEGER NOT NULL, EvaluationId INTEGER NOT NULL, FOREIGN KEY (IAAIndicatorId) REFERENCES IAAInformation (IAAInformationId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (EvaluationId) REFERENCES Evaluations (EvaluationId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: countries
    public static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE Countries (CountryId INTEGER NOT NULL PRIMARY KEY , CountryName VARCHAR(60) NOT NULL);";

    // Table: districts
    public static final String CREATE_TABLE_DISTRICTS = "CREATE TABLE Districts (DistrictId INTEGER NOT NULL PRIMARY KEY , DistrictName VARCHAR(60) NOT NULL, CantonId INTEGER NOT NULL, FOREIGN KEY (CantonId) REFERENCES Cantons (CantonId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: es
    public static final String CREATE_TABLE_ES = "CREATE TABLE ES (ESInformationId INTEGER NOT NULL, StructuralIndexId INTEGER NOT NULL, ES DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, FOREIGN KEY (ESInformationId) REFERENCES ESInformation (ESInformationId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructuralIndexId) REFERENCES StructuralIndexes (StructuralIndexId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: esinformation
    public static final String CREATE_TABLE_ESINFORMATION = "CREATE TABLE ESInformation (ESInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Col INTEGER NOT NULL, ElementType VARCHAR(20) NOT NULL, Description VARCHAR(100) NOT NULL, Enabled VARCHAR(45) NOT NULL);";

    // Table: evaluations
    public static final String CREATE_TABLE_EVALUATIONS = "CREATE TABLE Evaluations (EvaluationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, CreationDate DATE NOT NULL, Enabled BIT NOT NULL, ProjectId INTEGER, TokenId INTEGER, FOREIGN KEY (ProjectId) REFERENCES Projects (ProjectId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (TokenId) REFERENCES tokens (TokenId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: iaainformation
    public static final String CREATE_TABLE_IAAINFORMATION = "CREATE TABLE IAAInformation (IAAInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Description VARCHAR(150) NOT NULL, Enabled BIT NOT NULL);";

    // Table: ial
    public static final String CREATE_TABLE_IAL = "CREATE TABLE IAL (IALInformationId INTEGER NOT NULL, StructuralIndexId INTEGER NOT NULL, IAL DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, FOREIGN KEY (IALInformationId) REFERENCES IALInformation (IALInformationId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructuralIndexId) REFERENCES StructuralIndexes (StructuralIndexId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: ialinformation
    public static final String CREATE_TABLE_IALINFORMATION = "CREATE TABLE IALInformation (IALInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Col INTEGER NOT NULL, ElementType VARCHAR(20) NOT NULL, Description VARCHAR(100) NOT NULL, Enabled VARCHAR(45) NOT NULL);";

    // Table: iat
    public static final String CREATE_TABLE_IAT = "CREATE TABLE IAT (IATInformationId INTEGER NOT NULL, StructuralIndexId INTEGER NOT NULL, IAT DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, FOREIGN KEY (IATInformationId) REFERENCES IATInformation (IATInformationId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructuralIndexId) REFERENCES StructuralIndexes (StructuralIndexId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: iatinformation
    public static final String CREATE_TABLE_IATINFORMATION = "CREATE TABLE IATInformation (IATInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Col INTEGER NOT NULL, ElementType VARCHAR(20) NOT NULL, Description VARCHAR(100) NOT NULL, Enabled VARCHAR(45) NOT NULL);";

    // Table: idcindicators
    public static final String CREATE_TABLE_IDCINDICATORS = "CREATE TABLE IDCIndicators (IndicatorNameId INTEGER NOT NULL, CorrosionDamageIndexId INTEGER NOT NULL, Value INTEGER NOT NULL, Enabled BIT NOT NULL, FOREIGN KEY (CorrosionDamageIndexId) REFERENCES CorrosionDamageIndexes (CorrosionDamageIndexId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (IndicatorNameId) REFERENCES IndicatorNames (IndicatorNameId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: ide
    public static final String CREATE_TABLE_IDE = "CREATE TABLE IDE (IDEInformationId INTEGER NOT NULL, StructuralDamageIndexId INTEGER NOT NULL, Enabled BIT NOT NULL, FOREIGN KEY (IDEInformationId) REFERENCES IDEInformation (IDEInformationId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructuralDamageIndexId) REFERENCES StructuralDamageIndexes (StructuralDamageIndexId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: ideinformation
    public static final String CREATE_TABLE_IDEINFORMATION = "CREATE TABLE IDEInformation (IDEInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Col INTEGER NOT NULL, failureConsequence VARCHAR(20) NOT NULL, Description VARCHAR(100) NOT NULL, Enabled BIT NOT NULL);";

    // Table: indicatornames
    public static final String CREATE_TABLE_INDICATORNAMES = "CREATE TABLE IndicatorNames (IndicatorNameId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Row INTEGER NOT NULL, Col INTEGER NOT NULL, Description VARCHAR(150) NOT NULL, Enabled BIT NOT NULL);";

    // Table: projects
    public static final String CREATE_TABLE_PROJECTS = "CREATE TABLE Projects (ProjectId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR(60) NOT NULL, ComponentDescription VARCHAR(1000) NOT NULL, StructureUseDescription VARCHAR(1000) NOT NULL, Latitude DECIMAL(13,10) NOT NULL, Longitude DECIMAL(13,10) NOT NULL, CreationDate DATE NOT NULL, StructureCreationDate DATE NULL, Token VARCHAR(50) NULL, Enabled BIT NOT NULL, UserId INTEGER NOT NULL, StructureTypeId INTEGER NOT NULL, DistrictId INTEGER NOT NULL, FOREIGN KEY (UserId) REFERENCES Users (UserId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructureTypeId) REFERENCES StructureTypes (StructureTypeId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (DistrictId) REFERENCES Districts (DistrictId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: provinces
    public static final String CREATE_TABLE_PROVINCES = "CREATE TABLE Provinces (ProvinceId INTEGER NOT NULL PRIMARY KEY , ProvinceName VARCHAR(30) NOT NULL, CountryId INTEGER NOT NULL, FOREIGN KEY (CountryId) REFERENCES Countries (CountryId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuraldamageindexes
    public static final String CREATE_TABLE_STRUCTURALDAMAGEINDEXES = "CREATE TABLE StructuralDamageIndexes (StructuralDamageIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDE VARCHAR(10) NOT NULL, StructuralIndex DECIMAL(5,3) NOT NULL, CorrosionIndex DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, EvaluationId INTEGER NOT NULL, FOREIGN KEY (EvaluationId) REFERENCES Evaluations (EvaluationId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuralindexes
    public static final String CREATE_TABLE_STRUCTURALINDEXES = "CREATE TABLE StructuralIndexes (StructuralIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IE DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, EvaluationType BIT NOT NULL, EvaluationId INTEGER NOT NULL, FOREIGN KEY (EvaluationId) REFERENCES Evaluations (EvaluationId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuretypes
    public static final String CREATE_TABLE_STRUCTURETYPES = "CREATE TABLE StructureTypes (StructureTypeId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR(40) NOT NULL, Enabled BIT NOT NULL);";

    // Table: tokens
    public static final String CREATE_TABLE_TOKENS = "CREATE TABLE Tokens (TokenId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Token VARCHAR(50) NOT NULL, Enabled BIT NOT NULL);";

    // Table: users
    public static final String CREATE_TABLE_USERS = "CREATE TABLE Users (UserId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR (50) NOT NULL, Lastname VARCHAR (50) NOT NULL, Email VARCHAR (60) NOT NULL, LoginDate DATE NOT NULL, Enabled BIT NOT NULL);";


    public Cursor getStructureTypeNames(){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM StructureTypes WHERE Enabled = '1' ", null);
        return cursor;
    }

    public Cursor getProvinces(){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM Provinces", null);
        return cursor;
    }

    public Cursor getCantons(String provinceId){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM Cantons WHERE ProvinceId = "+provinceId, null);
        return cursor;
    }

    public Cursor getDistricts(String cantonId){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM Districts WHERE CantonId = "+cantonId, null);
        return cursor;
    }

    public long createProject(
            String projectName,
            String buildingDate,
            String componentDescription,
            String generalDescription,
            String districtId,
            String structureTypeId,
            float latitude,
            float longitude){

        ContentValues values = new ContentValues();
        values.put("Name", projectName);
        values.put("ComponentDescription", componentDescription);
        values.put("StructureUseDescription", generalDescription);
        values.put("Latitude", 0);
        values.put("Longitude", 0);
        values.put("CreationDate", getCurrentDate());
        values.put("StructureCreationDate", buildingDate);
        values.put("Token", "null");
        values.put("Enabled", 1);
        values.put("UserId", getUserId());
        values.put("StructureTypeId", structureTypeId);
        values.put("DistrictId", districtId);
        long value = db.insert("Projects", null, values);
        return value;
    }

    public Cursor getUserLogin(){
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE Enabled = 1", null);
        return cursor;
    }

    public String getUserId(){
        Cursor cursor = getUserLogin();
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(cursor.getColumnIndex("UserId"));
            } while(cursor.moveToNext());
        } else {
            return "-1";
        }

    }

    private String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }






















    //SECCIÓN DE PRUEBAS
    // Table: Contacto
    public static final String CREATE_TABLE_CONTACTO = "CREATE TABLE Contacto (ContactoId INTEGER NOT NULL PRIMARY KEY, Contacto VARCHAR(20) NOT NULL, Enabled BIT NOT NULL);";

    // Table: Usuario
    public static final String CREATE_TABLE_USUARIO = "CREATE TABLE Usuario (UsuarioId INTEGER NOT NULL PRIMARY KEY, Nombre VARCHAR (50) NOT NULL, ContactoId INTEGER NOT NULL REFERENCES Contacto (ContactoId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    public void crearContacto(Context context){
        openConnection();
        ContentValues values = new ContentValues();
        values.put("Contacto", "49494949");
        values.put("Enabled", 1);
        long value = db.insert("Contacto", null, values);
        Toast.makeText(context,"Resultado contacto "+value, Toast.LENGTH_LONG).show();

        values = new ContentValues();
        values.put("Nombre", "Bryan");
        values.put("ContactoId", 1);
        long value2 = db.insert("Usuario", null, values);
        closeConnection();
        //db.execSQL("SELECT last_insert_rowid();");
        //Toast.makeText(context,"Resultado Usuario "+value2, Toast.LENGTH_LONG).show();



    }

    public void  consulta(Context context){

        String[] campos = new String[] {"*"};
        String[] args = new String[] {"1"};
        openConnection();
        //Cursor c = db.query("Contacto", campos, "Enabled=?", args, null, null, null);
        Cursor c = db.rawQuery(" SELECT * FROM Contacto WHERE Enabled = '1' ", null);
        System.out.println("--------------------------------------------------------------");



        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                System.out.println(c.getString(c.getColumnIndex("Contacto")));
            } while(c.moveToNext());
        }
        closeConnection();

        //String newvonombre = "William";

        //db.execSQL("UPDATE Usuario SET Nombre = "+newvonombre+" Where ContactoId = 1; SELECT 1;");
    }

}
