package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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

    public int getIATInformationId(int pRow, int pColumn, String pElementType){
        Cursor cursor = db.rawQuery("SELECT IATInformationId FROM iatinformation WHERE Row = "+pRow+" AND Col = "+pColumn+" AND ElementType = '"+pElementType+"' AND Enabled = 1 LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("IATInformationId"));
        } else {
            return -1;
        }
    }

    public int getIALInformationId(int pRow, int pColumn, String pElementType){
        Cursor cursor = db.rawQuery("SELECT IALInformationId FROM ialinformation WHERE Row = "+pRow+" AND Col = "+pColumn+" AND ElementType = '"+pElementType+"' AND Enabled = 1 LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("IALInformationId"));
        } else {
            return -1;
        }
    }

    public int getESInformationId(int pRow, int pColumn, String pElementType){
        Cursor cursor = db.rawQuery("SELECT ESInformationId FROM esinformation WHERE Row = "+pRow+" AND Col = "+pColumn+" AND ElementType = '"+pElementType+"' AND Enabled = 1 LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("ESInformationId"));
        } else {
            return -1;
        }
    }

    public int getStructuralIndexIdCount(long pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT COUNT(StructuralIndexId) as IndexCount FROM structuralindexes WHERE EvaluationId = "+pEvaluationId+" AND Enabled = 1 LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("IndexCount"));
        } else {
            return -1;
        }
    }

    public long getStructuralIndexId(long pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT StructuralIndexId FROM structuralindexes WHERE EvaluationId = "+pEvaluationId+" AND Enabled = 1 LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(cursor.getColumnIndex("StructuralIndexId"));
        } else {
            return -1;
        }
    }

    public int saveStructuralIndex(float pStructuralIndex,
        int pTransverseIndex,
        int pTransverseRow,
        int pTransverseColumn,
        int pLongitudinalIndex,
        int pLongitudinalRow,
        int pLongitudinalColumn,
        String pElementType,
        long pEvaluationId){

        int indexCount = getStructuralIndexIdCount(pEvaluationId);
        int IATInformationId = getIATInformationId(pTransverseRow,pTransverseColumn,pElementType);
        int IALInformationId = getIALInformationId(pLongitudinalRow,pLongitudinalColumn,pElementType);
        long structuralIndexId;
        ContentValues structuralIndexValues = new ContentValues();
        ContentValues iatValues = new ContentValues();
        ContentValues ialValues = new ContentValues();
        long value;
        if(indexCount==0){
            structuralIndexValues.put("IE", pStructuralIndex);
            structuralIndexValues.put("Enabled", 1);
            structuralIndexValues.put("EvaluationType" , 1);
            structuralIndexValues.put("EvaluationId", pEvaluationId);
            value = db.insert("structuralindexes", null, structuralIndexValues);
            if(value==-1){
                return -1;
            }
            structuralIndexId = value;
        } else {
            structuralIndexId = getStructuralIndexId(pEvaluationId);
            ContentValues updateValues = new ContentValues();
            updateValues.put("IE", pStructuralIndex);
            updateValues.put("EvaluationType", 1);

            value = db.update("structuralindexes", updateValues,"EvaluationId = "+pEvaluationId+" AND StructuralIndexId = "+structuralIndexId+" AND Enabled = 1", null);
            if(value==-1){
                return -1;
            }
        }

        iatValues.put("IATInformationId",IATInformationId);
        iatValues.put("StructuralIndexId",structuralIndexId);
        iatValues.put("IAT",pTransverseIndex);
        iatValues.put("Enabled",1);
        value = db.insert("iat", null, iatValues);
        if(value==-1){
            return -1;
        }

        ialValues.put("IALInformationId",IALInformationId);
        ialValues.put("StructuralIndexId",structuralIndexId);
        ialValues.put("IAL",pLongitudinalIndex);
        ialValues.put("Enabled",1);
        value = db.insert("ial", null, ialValues);
        if(value==-1){
            return -1;
        }
        return 1;
    }

    public int saveStructuralIndexSimplified(float pStructuralIndex,
                                             int pSimplifiedIndex,
                                             int pSimplifiedRow,
                                             int pSimplifiedColumn,
                                             String pElementType,
                                             long pEvaluationId){

        int indexCount = getStructuralIndexIdCount(pEvaluationId);
        int ESInformationId = getESInformationId(pSimplifiedRow,pSimplifiedColumn,pElementType);
        long structuralIndexId;
        ContentValues structuralIndexValues = new ContentValues();
        ContentValues esValues = new ContentValues();
        long value;
        if(indexCount==0){
            structuralIndexValues.put("IE", pStructuralIndex);
            structuralIndexValues.put("Enabled", 1);
            structuralIndexValues.put("EvaluationType" , 0);
            structuralIndexValues.put("EvaluationId", pEvaluationId);
            value = db.insert("structuralindexes", null, structuralIndexValues);
            if(value==-1){
                return -1;
            }
            structuralIndexId = value;
        } else {
            structuralIndexId = getStructuralIndexId(pEvaluationId);
            ContentValues updateValues = new ContentValues();
            updateValues.put("IE", pStructuralIndex);
            updateValues.put("EvaluationType", 0);

            value = db.update("structuralindexes",updateValues, "EvaluationId = "+pEvaluationId+" AND StructuralIndexId = "+structuralIndexId+" AND Enabled = 1",null);
            if(value==-1){
                return -1;
            }
        }

        esValues.put("ESInformationId",ESInformationId);
        esValues.put("StructuralIndexId",structuralIndexId);
        esValues.put("ES",pSimplifiedIndex);
        esValues.put("Enabled",1);
        value = db.insert("es", null, esValues);
        if(value==-1){
            return -1;
        }
        return 1;
    }


    public long validatePreviousStructuralIndex(long pEvaluationId){
        long structuralIndexId = getStructuralIndexId(pEvaluationId);
        if(structuralIndexId == -1){
            return 0;
        }
        Cursor cursor = db.rawQuery("SELECT iat_Table.IATInformationId, ial_Table.IALInformationId\n" +
                "FROM structuralindexes SI\n" +
                "INNER JOIN iat iat_Table \n" +
                "ON iat_Table.StructuralIndexId = SI.StructuralIndexId\n" +
                "INNER JOIN ial ial_Table \n" +
                "ON ial_Table.StructuralIndexId = SI.StructuralIndexId\n" +
                "WHERE \n" +
                "SI.StructuralIndexId = "+structuralIndexId+" AND \n" +
                "iat_Table.Enabled = 1 AND \n" +
                "ial_Table.Enabled = 1 \n" +
                "LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return 1;
        } else {
            return 0;
        }
    }

    public long disablePreviousStructuralIndex(long pEvaluationId){
        long structuralIndexId = getStructuralIndexId(pEvaluationId);
        long value;
        if(structuralIndexId == -1){
            return -1;
        }
        ContentValues updateValuesIAT = new ContentValues();
        updateValuesIAT.put("Enabled", 0);
        value = db.update("iat",updateValuesIAT,"StructuralIndexId = "+structuralIndexId+";",null);
        if(value==-1){
            return -1;
        }
        ContentValues updateValuesIAL = new ContentValues();
        updateValuesIAL.put("Enabled", 0);
        value = db.update("ial",updateValuesIAL,"StructuralIndexId = "+structuralIndexId+";",null);
        if(value==-1){
            return -1;
        }
        return 1;
    }

    public long validatePreviousStructuralIndexSimplified(long pEvaluationId){
        long structuralIndexId = getStructuralIndexId(pEvaluationId);
        if(structuralIndexId==-1){
            return 0;
        }
        Cursor cursor = db.rawQuery("SELECT ES_Table.ESInformationId\n" +
                "FROM structuralindexes SI\n" +
                "INNER JOIN es ES_Table\n" +
                "ON ES_Table.StructuralIndexId = SI.StructuralIndexId\n" +
                "WHERE \n" +
                "SI.StructuralIndexId = "+structuralIndexId+" AND \n" +
                "ES_Table.Enabled = 1\n" +
                "LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            return 1;
        } else {
            return 0;
        }
    }

    public long disablePreviousStructuralIndexSimplified(long pEvaluationId){
        long structuralIndexId = getStructuralIndexId(pEvaluationId);
        long value;
        if(structuralIndexId == -1){
            return -1;
        }
        ContentValues updateValues = new ContentValues();
        updateValues.put("Enabled", 0);
        value = db.update("es",updateValues,"StructuralIndexId = "+structuralIndexId+";",null);
        if(value==-1){
            return -1;
        }
        return 1;
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
