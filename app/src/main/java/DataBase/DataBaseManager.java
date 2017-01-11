package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static final String CREATE_TABLE_PROJECTS = "CREATE TABLE Projects (ProjectId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR (60) NOT NULL, ComponentDescription VARCHAR (1000) NOT NULL, StructureUseDescription VARCHAR (1000) NOT NULL, Latitude DECIMAL (13, 10) NOT NULL, Longitude DECIMAL (13, 10) NOT NULL, CreationDate DATE NOT NULL, StructureCreationDate DATE, Token VARCHAR (50), Enabled BIT NOT NULL, UserEmail VARCHAR (60) NOT NULL, UserId INTEGER NOT NULL, StructureTypeId INTEGER NOT NULL, DistrictId INTEGER NOT NULL, FOREIGN KEY (UserId) REFERENCES Users (UserId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (StructureTypeId) REFERENCES StructureTypes (StructureTypeId) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (DistrictId) REFERENCES Districts (DistrictId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: provinces
    public static final String CREATE_TABLE_PROVINCES = "CREATE TABLE Provinces (ProvinceId INTEGER NOT NULL PRIMARY KEY , ProvinceName VARCHAR(30) NOT NULL, CountryId INTEGER NOT NULL, FOREIGN KEY (CountryId) REFERENCES Countries (CountryId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuraldamageindexes
    public static final String CREATE_TABLE_STRUCTURALDAMAGEINDEXES = "CREATE TABLE StructuralDamageIndexes (StructuralDamageIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDE VARCHAR(10) NOT NULL, StructuralIndex DECIMAL(5,3) NOT NULL, CorrosionIndex DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, EvaluationId INTEGER NOT NULL, FOREIGN KEY (EvaluationId) REFERENCES Evaluations (EvaluationId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuralindexes
    public static final String CREATE_TABLE_STRUCTURALINDEXES = "CREATE TABLE StructuralIndexes (StructuralIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IE DECIMAL(5,3) NOT NULL, Enabled BIT NOT NULL, EvaluationType BIT NOT NULL, EvaluationId INTEGER NOT NULL, FOREIGN KEY (EvaluationId) REFERENCES Evaluations (EvaluationId) ON DELETE NO ACTION ON UPDATE NO ACTION);";

    // Table: structuretypes
    public static final String CREATE_TABLE_STRUCTURETYPES = "CREATE TABLE StructureTypes (StructureTypeId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR(40) NOT NULL, Enabled BIT NOT NULL);";

    // Table: tokens
    public static final String CREATE_TABLE_TOKENS = "CREATE TABLE Tokens (TokenId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Token VARCHAR(50) NOT NULL, UserEmail VARCHAR (60) NOT NULL, Enabled BIT NOT NULL);";

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

    public Cursor getProjectList(){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM Projects WHERE UserEmail = '"+getUserEmail().toString()+"' AND Enabled = '1'", null);
        return cursor;
    }

    public Cursor getTokenList(){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT * FROM Tokens WHERE UserEmail = '"+getUserEmail().toString()+"' AND Enabled = '1'", null);
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
        values.put("Enabled", 1);
        values.put("UserEmail", getUserEmail());
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

    private String getUserId(){
        Cursor cursor = getUserLogin();
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(cursor.getColumnIndex("UserId"));
            } while(cursor.moveToNext());
        } else {
            return "-1";
        }
    }

    private String getUserEmail(){
        Cursor cursor = getUserLogin();
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(cursor.getColumnIndex("Email"));
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



    public String createEvaluationProject(String projectId){
        ContentValues values = new ContentValues();
        values.put("CreationDate", getCurrentDate());
        values.put("Enabled", 1);
        values.put("ProjectId", projectId);
        long value = db.insert("Evaluations", null, values);
        return String.valueOf(value);
    }

    public long createEvaluationToken(String tokenId){
        ContentValues values = new ContentValues();
        values.put("CreationDate", getCurrentDate());
        values.put("Enabled", 1);
        values.put("TokenId", tokenId);
        long value = db.insert("Evaluations", null, values);
        return value;
    }

    public String createProjectToken(String token){
        String tokenId = getIdToken(token);
        if(tokenId.equals("-1")){
            ContentValues values = new ContentValues();
            values.put("Token", token);
            values.put("UserEmail", getUserEmail());
            values.put("Enabled", 1);
            long tokenResultId = db.insert("Tokens", null, values);
            long evaluationId = createEvaluationToken(String.valueOf(tokenResultId));
            return String.valueOf(evaluationId);
        } else {
            return getEvaluationIdbyToken(tokenId);
        }
    }

    public String getEvaluationIdbyProject(String projectId){
        Cursor cursor = db.rawQuery("SELECT EvaluationId FROM Evaluations WHERE ProjectId = '"+projectId+"' AND Enabled = 1", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("EvaluationId"));
        } else {
            return "-1";
        }
    }

    public String getEvaluationIdbyToken(String tokenId){
        Cursor cursor = db.rawQuery("SELECT EvaluationId FROM Evaluations WHERE TokenId = '"+tokenId+"' AND Enabled = 1", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("EvaluationId"));
        } else {
            return "-1";
        }
    }

    public String getIdToken(String token){
        Cursor cursor = db.rawQuery("SELECT TokenId FROM Tokens WHERE Token = '"+token+"' AND Enabled = 1", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("TokenId"));
        } else {
            return "-1";
        }
    }




    /*
    *           Structural Damage Index
    *
    */

    public long loadStructuralIndexToIDE(String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT IE FROM structuralindexes WHERE EvaluationId = '"+ pEvaluationId +"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long ideInfoId = Long.parseLong(cursor.getString(cursor.getColumnIndex("IE")));
            return ideInfoId;
        }
        return -1;
    }

    public long loadCorrosionIndexToIDE (String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT ISC FROM corrosiondamageindexes WHERE EvaluationId = '"+ pEvaluationId +"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long ideInfoId = Long.parseLong(cursor.getString(cursor.getColumnIndex("ISC")));
            return ideInfoId;
        }
        return -1;
    }

    public JSONObject loadPreviousIDEEvaluation(String pEvaluationId){
        JSONObject json = null;

        Cursor cursor = db.rawQuery("SELECT SDI.IDE AS \"IDE\", SDI.StructuralIndex AS \"structuralIndex\", SDI.CorrosionIndex AS \"corrosionIndex\", IDE_Info.Row AS \"IDE_Row\", IDE_Info.Col AS \"IDE_Col\", IDE_Info.failureConsequence AS \"failureConsequence\" FROM structuraldamageindexes SDI INNER JOIN ide ideTable ON ideTable.StructuralDamageIndexId = SDI.StructuralDamageIndexId INNER JOIN ideinformation IDE_Info ON IDE_Info.IDEInformationId = ideTable.IDEInformationId WHERE SDI.EvaluationId = '"+pEvaluationId+"' AND SDI.Enabled = 1 AND ideTable.Enabled = 1 AND IDE_Info.Enabled = 1 LIMIT 1", null);

        if (cursor.moveToFirst()) {

            try {
                String IDE  = cursor.getString(cursor.getColumnIndex("IDE"));

                double structuralIndex =  Double.parseDouble(cursor.getString(cursor.getColumnIndex("structuralIndex")));
                double corrosionIndex =  Double.parseDouble(cursor.getString(cursor.getColumnIndex("corrosionIndex")));

                int IDE_Row = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDE_Row")));
                int IDE_Col = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDE_Col")));

                String failureConsequence = cursor.getString(cursor.getColumnIndex("failureConsequence"));

                String jsonString = "{'FailConsequence': "+failureConsequence+",'IDE':"+IDE+", 'IDE_Row': "+IDE_Row+", 'IDE_Column': "+IDE_Col+", 'corrosionIndex': "+corrosionIndex+", 'structuralIndex': "+structuralIndex+"}";

                json = new JSONObject(jsonString);

                return json;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public long getIDEId(String pEvaluationId){

        Cursor cursor = db.rawQuery("SELECT StructuralDamageIndexId FROM structuraldamageindexes WHERE EvaluationId = '"+pEvaluationId+"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long structuralDamageIndexId = Long.parseLong(cursor.getString(cursor.getColumnIndex("StructuralDamageIndexId")));
            return structuralDamageIndexId;
        }
        return -1;
    }

    public long disableRelatedIDEInformation(String pEvaluationId){
        long structuralDamageIndexId = getIDEId(pEvaluationId);

        if(structuralDamageIndexId != -1){


            //"UPDATE ide SET Enabled = 0 WHERE StructuralDamageIndexId = '"+structuralDamageIndexId+"'";

            ContentValues values = new ContentValues();
            values.put("Enabled", 0);

            String whereCondition = "StructuralDamageIndexId = '"+structuralDamageIndexId+"'";
            String tableName = "ide";

            long value = db.update(tableName, values, whereCondition, null);

            return value;
        }

        return -1;
    }

    public long getIDEInformationId(int pIDE_Row, int pIDE_Column, String pFailureConsequence){
        Cursor cursor = db.rawQuery("SELECT IDEInformationId FROM ideinformation WHERE Row = '" + pIDE_Row + "' AND Col = '" + pIDE_Column + "' AND failureConsequence = '" + pFailureConsequence+ "' AND Enabled = 1", null);
        if (cursor.moveToFirst()) {
            long ideInfoId = Long.parseLong(cursor.getString(cursor.getColumnIndex("IDEInformationId")));
            return ideInfoId;
        }
        return -1;

    }

    public long insertStructuralDamageIndex(String pIDE, double pStructuralIndex, double pCorrosionIndex, String pEvaluationId){

        ContentValues values = new ContentValues();
        values.put("IDE", pIDE);
        values.put("StructuralIndex", pStructuralIndex);
        values.put("CorrosionIndex", pCorrosionIndex);
        values.put("Enabled", 1);
        values.put("EvaluationId", pEvaluationId);

        long value = db.insert("structuraldamageindexes", null, values);
        return value;
    }

    public long insertRalatedIDEInformation(long pIDE_Id, long pIDEInformationId ){

        ContentValues values = new ContentValues();
        values.put("IDEInformationId", pIDEInformationId);
        values.put("StructuralDamageIndexId", pIDE_Id);
        values.put("Enabled", 1);

        long value = db.insert("ide", null, values);
        return value;
    }

    public long updateStructuralDamageIndex(String pIDE, double pStructuralIndex, double pCorrosionIndex, String pEvaluationId, int pIDE_Row, int pIDE_Col, String pFailureConsequence){

        long ideInfoId = getIDEInformationId(pIDE_Row, pIDE_Col, pFailureConsequence);
        long IDE_Id = getIDEId(pEvaluationId);

        if(ideInfoId != -1 && IDE_Id != -1) {

            //UPDATE structuraldamageindexes
            // SET IDE = P_IDE, StructuralIndex = P_StructuralIndex, CorrosionIndex = P_CorrosionIndex
            // WHERE
            // EvaluationId = P_EvaluationId AND
            // Enabled = 1;

            ContentValues values = new ContentValues();
            values.put("IDE", pIDE);
            values.put("StructuralIndex", pStructuralIndex);
            values.put("CorrosionIndex", pCorrosionIndex);

            String whereCondition = "EvaluationId = '" + pEvaluationId + "' AND Enabled = 1";
            String tableName = "structuraldamageindexes";

            long updateResult = db.update(tableName, values, whereCondition, null);

            if (updateResult != -1) {
                return insertRalatedIDEInformation(IDE_Id, ideInfoId);
            }
        }

        return -1;
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
