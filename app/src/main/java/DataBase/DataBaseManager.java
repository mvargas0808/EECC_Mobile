package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itcr.eecc.eecc.JSONParser;
import com.itcr.eecc.eecc.LoadProject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bryan on 1/7/2017.
 */

public class DataBaseManager {

    private DBHelper helper;
    private SQLiteDatabase db;
    JSONParser jsonParser = new JSONParser();
    Context appContext;

    private static final String GET_EMAIL_BY_TOKEN = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/user/get-user-email-by-token.php";
    private static final String SAVE_PROJECT = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/project/create-project.php";
    private static final String CREATE_EVALUATION = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/evaluation/create-evaluation-mobile.php";
    private static final String GET_PROJECT_BY_TOKEN = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/project/get-project-by-token.php";
    private static final String SAVE_STRUCTURAL_DAMAGE_INDEX = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/structural-damage-index/save-structural-damage-index.php";
    private static final String SAVE_CORROSION_INDEX = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/corrosion-index/save-ci.php";
    private static final String SAVE_STRUCTURAL_INDEX_MANUAL_AUX = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/structural-index/save-si-manual.php";
    private static final String SAVE_STRUCTURAL_INDEX_SIMPLIFIED_AUX = "http://192.168.0.13/bryan/ProyectoVerano/EECC_Web/php/controllers/structural-index/save-si-simplified.php";
    String SAVE_STRUCTURAL_INDEX_MODE = "";

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
    public static final String CREATE_TABLE_USERS = "CREATE TABLE Users (UserId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name VARCHAR (50) NOT NULL, Lastname VARCHAR (50) NOT NULL, Email VARCHAR (60) NOT NULL, Token VARCHAR(40) NOT NULL, LoginDate DATE NOT NULL, Enabled BIT NOT NULL);";


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

    public long editProject(
            String projectName,
            String buildingDate,
            String componentDescription,
            String generalDescription,
            String districtId,
            String structureTypeId,
            float latitude,
            float longitude,
            String projectId){

        ContentValues values = new ContentValues();
        values.put("Name", projectName);
        values.put("ComponentDescription", componentDescription);
        values.put("StructureUseDescription", generalDescription);
        values.put("Latitude", 0);
        values.put("Longitude", 0);
        values.put("StructureCreationDate", buildingDate);
        values.put("StructureTypeId", structureTypeId);
        values.put("DistrictId", districtId);
        long value = db.update("Projects", values,"ProjectId="+projectId ,null);
        return value;
    }

    public Cursor getUserLogin(){
        openConnection();
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

    private String getUserToken(){
        Cursor cursor = getUserLogin();
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(cursor.getColumnIndex("Token"));
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

    public String getEvaluationId(String tokenId, String projectId){
        Cursor cursor = db.rawQuery("SELECT EvaluationId FROM Evaluations WHERE (TokenId = '"+tokenId+"' OR ProjectId = '"+projectId+"') AND Enabled = 1", null);
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

    public Cursor getProjectInformation(String projectId){
        Cursor cursor =  db.rawQuery("SELECT proj.*, proj.Name AS ProjectName, us.Name AS UserName, us.Lastname, stru.Name AS StructureName, dis.DistrictName, can.CantonName, can.CantonId, prov.ProvinceName, prov.ProvinceId  FROM projects proj"
        + " INNER JOIN structuretypes stru ON proj.StructureTypeId = stru.StructureTypeId AND stru.Enabled = 1"
        + " INNER JOIN districts dis ON proj.DistrictId = dis.DistrictId"
        + " INNER JOIN cantons can ON dis.CantonId = can.CantonId"
        + " INNER JOIN provinces prov ON can.ProvinceId = prov.ProvinceId"
        + " INNER JOIN users us ON proj.UserEmail = us.Email AND us.Enabled = 1"
        + " WHERE proj.ProjectId = '"+projectId+"'", null);
        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            return null;
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


     /*
    *           Corrosion Index
    *
    */


    public long getIAAInformationId(int pIAA_Row){
        //SELECT IAAInformationId FROM iaainformation WHERE Row = P_IAA_Row AND Enabled = 1 LIMIT 1
        Cursor cursor = db.rawQuery("SELECT IAAInformationId FROM iaainformation WHERE Row = '"+pIAA_Row+"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long IAAInformationId = Long.parseLong(cursor.getString(cursor.getColumnIndex("IAAInformationId")));
            return IAAInformationId;
        }
        return -1;

    }

    public long insertCorrosionIndex(String pEvaluationId, double pIDC, double pIAA, double pISC, long pIAAInformationId){

        //INSERT INTO corrosiondamageindexes
        // (IDC, IAA, ISC, Enabled, IAAIndicatorId,EvaluationId)
        // VALUES (
        // P_IDC, P_IAA, P_ISC, 1, V_IAAInformationId, P_EvaluationId);

        ContentValues values = new ContentValues();
        values.put("IDC", pIDC);
        values.put("IAA", pIAA);
        values.put("ISC", pISC);
        values.put("Enabled", 1);
        values.put("IAAIndicatorId", pIAAInformationId);
        values.put("EvaluationId", pEvaluationId);

        long value = db.insert("corrosiondamageindexes", null, values);
        return value;
    }

    public long getIndicatorNameId(int pIndicatorRow, int pIndicatorCol){
        // SELECT IndicatorNameId FROM indicatornames WHERE Row = P_IndicatorRow AND Col = P_IndicatorColumn AND Enabled = 1 LIMIT 1

        Cursor cursor = db.rawQuery("SELECT IndicatorNameId FROM indicatornames WHERE Row = '"+pIndicatorRow+"' AND Col = '"+pIndicatorCol+"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long IndicatorNameId = Long.parseLong(cursor.getString(cursor.getColumnIndex("IndicatorNameId")));
            return IndicatorNameId;
        }
        return -1;

    }

    public long saveIndicatorValue(long pCorrosionIndexId, int pIndicatorValue, long pIndicatorNameId){

        //INSERT INTO idcindicators
        // (IndicatorNameId, CorrosionDamageIndexId, Value, Enabled)
        // VALUES
        // (V_IndicatorNameId, P_CorrosionIndexId, P_IndicatorValue,1);

        ContentValues values = new ContentValues();
        values.put("IndicatorNameId", pIndicatorNameId);
        values.put("CorrosionDamageIndexId", pCorrosionIndexId);
        values.put("Value", pIndicatorValue);
        values.put("Enabled", 1);

        long value = db.insert("idcindicators", null, values);
        return value;
    }

    public JSONObject loadCorrosionIndexValues(String pEvaluationId){

        //SELECT  CDI.CorrosionDamageIndexId AS "corrosionId", CDI.IDC, CDI.IAA, CDI.ISC, IAAInfo.Row AS "IAA_Row" FROM corrosiondamageindexes CDI INNER JOIN iaainformation IAAInfo ON IAAInfo.IAAInformationId = CDI.IAAIndicatorId WHERE CDI.EvaluationId = '"+pEvaluationId+"' AND CDI.Enabled = 1 LIMIT 1

        JSONObject jsonResult = null;

        Cursor cursor = db.rawQuery("SELECT  CDI.CorrosionDamageIndexId AS \"corrosionId\", CDI.IDC, CDI.IAA, CDI.ISC, IAAInfo.Row AS \"IAA_Row\" FROM corrosiondamageindexes CDI INNER JOIN iaainformation IAAInfo ON IAAInfo.IAAInformationId = CDI.IAAIndicatorId WHERE CDI.EvaluationId = '"+pEvaluationId+"' AND CDI.Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {

            try {

                long corrosionId = Long.parseLong(cursor.getString(cursor.getColumnIndex("corrosionId")));
                double IDC = Double.parseDouble(cursor.getString(cursor.getColumnIndex("IDC")));
                double ISC = Double.parseDouble(cursor.getString(cursor.getColumnIndex("ISC")));
                double IAA = Double.parseDouble(cursor.getString(cursor.getColumnIndex("ISC")));
                int IAA_Row = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IAA_Row")));

                String jsonString = "{'corrosionId':"+corrosionId+", 'IDC':"+IDC+", 'ISC':"+ISC+", 'IAA':"+IAA+", 'IAA_Row':"+IAA_Row+"}";
                jsonResult = new JSONObject(jsonString);

                return jsonResult;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getCorrosionIndicatorsValues(long pCorrosionIndexId){

        // SELECT IDC.Value, IndNames.Row, IndNames.Col FROM idcindicators IDC INNER JOIN indicatornames IndNames ON IndNames.IndicatorNameId = IDC.IndicatorNameId WHERE IDC.CorrosionDamageIndexId = P_CorrosionIndexId AND  IDC.Enabled = 1;

        ArrayList<String> indicators = new ArrayList<>();


        Cursor cursor = db.rawQuery("SELECT IDC.Value, IndNames.Row, IndNames.Col FROM idcindicators IDC INNER JOIN indicatornames IndNames ON IndNames.IndicatorNameId = IDC.IndicatorNameId WHERE IDC.CorrosionDamageIndexId = '"+pCorrosionIndexId+"' AND  IDC.Enabled = 1", null);
        if (cursor.moveToFirst()) {

            do {
                int value = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Value")));
                int column = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Col")));
                int row = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Row")));

                String indicatorName = getIndicatorName(row);

                String jsonString = "{'value':"+value+", 'column':"+column+", 'row':"+row+", 'indicatorName':'"+indicatorName+"'}";

                indicators.add(jsonString);
            } while(cursor.moveToNext());

        }
        return indicators;

    }

    public String getIndicatorName(int pRow){

        String indicatorName = "";

        switch (pRow){
            case 1:
                indicatorName = "carbonationDepth";
                break;
            case 2:
                indicatorName = "chlorideLevel";
                break;
            case 3:
                indicatorName = "cracking";
                break;
            case 4:
                indicatorName = "resistivity";
                break;
            case 5:
                indicatorName = "sectionLoss";
                break;
            case 6:
                indicatorName = "intensity";
                break;

            default:
                indicatorName = "";
                break;
        }

        return indicatorName;
    }

    public long getCorrosionIndexId(String pEvaluationId){
        //SELECT CorrosionDamageIndexId FROM corrosiondamageindexes WHERE EvaluationId = 1 AND Enabled = 1 LIMIT 1

        Cursor cursor = db.rawQuery("SELECT CorrosionDamageIndexId FROM corrosiondamageindexes WHERE EvaluationId = '"+pEvaluationId+"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            long CorrosionDamageIndexId = Long.parseLong(cursor.getString(cursor.getColumnIndex("CorrosionDamageIndexId")));
            return CorrosionDamageIndexId;
        }
        return -1;
    }

    public long updateCorrosionIndex(long pCorrosionIndexId, double pIDC, double pIAA, double pISC, long pIAAInformationId){

        //UPDATE corrosiondamageindexes
        //SET
        //        IDC = P_IDC,
        //        IAA = P_IAA,
        //        ISC = P_ISC,
        //        IAAIndicatorId = V_IAAInformationId
        //WHERE
        //        CorrosionDamageIndexId = P_CorrosionIndexId;

        ContentValues values = new ContentValues();
        values.put("IDC", pIDC);
        values.put("IAA", pIAA);
        values.put("ISC", pISC);
        values.put("IAAIndicatorId", pIAAInformationId);

        String whereCondition = "CorrosionDamageIndexId = '"+pCorrosionIndexId+"'";
        String tableName = "corrosiondamageindexes";

        long updateResult = db.update(tableName, values, whereCondition, null);

        return updateResult;
    }

    public long disableIndicators(long pCorrosionIndexId){
        //UPDATE idcindicators SET Enabled = 0 WHERE CorrosionDamageIndexId = P_CorrosionIndexId;

        ContentValues values = new ContentValues();
        values.put("Enabled", 0);

        String whereCondition = "CorrosionDamageIndexId = '"+pCorrosionIndexId+"'";
        String tableName = "idcindicators";

        long updateResult = db.update(tableName, values, whereCondition, null);

        return updateResult;

    }

    /*
    *           Structural Damage Index
    *
    */

    public int loadStructuralIndexToIDE(String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT IE FROM structuralindexes WHERE EvaluationId = '"+ pEvaluationId +"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            int ideInfoId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IE")));
            return ideInfoId;
        }
        return -1;
    }

    public double loadCorrosionIndexToIDE (String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT ISC FROM corrosiondamageindexes WHERE EvaluationId = '"+ pEvaluationId +"' AND Enabled = 1 LIMIT 1", null);
        if (cursor.moveToFirst()) {
            double ideInfoId = Double.parseDouble(cursor.getString(cursor.getColumnIndex("ISC")));
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

    public long updateStructuralDamageIndex(String pIDE, double pStructuralIndex, double pCorrosionIndex, String pEvaluationId, int pIDE_Row, int pIDE_Col, String pFailureConsequence) {

        long ideInfoId = getIDEInformationId(pIDE_Row, pIDE_Col, pFailureConsequence);
        long IDE_Id = getIDEId(pEvaluationId);

        if (ideInfoId != -1 && IDE_Id != -1) {

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


    /*
    *           Structural  Index
    *
    */

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

    public int createUser(String pName, String pLastName, String pEmail, String pToken){
        ContentValues insertValues = new ContentValues();
        long result;

        if(disableUsers() == -1){
            return -1;
        }

        insertValues.put("Name",pName);
        insertValues.put("LastName",pLastName);
        insertValues.put("Email",pEmail);
        insertValues.put("Token",pToken);
        insertValues.put("LoginDate",getCurrentDate());
        insertValues.put("Enabled",1);
        result = db.insert("users", null, insertValues);
        if(result == -1){
            return -1;
        }
        return 1;
    }

    //{"Name":"William","Lastname":"Borges","Email":"wb@gmail.com","Result":"1"}

    public int disableUsers(){
        long result;
        ContentValues updateValues = new ContentValues();
        updateValues.put("Enabled", 0);
        result = db.update("users",updateValues,"",null);
        if(result == -1){
            return -1;
        }
        return 1;
    }

    public Cursor getReportInformation(String evaluationId){
        Cursor cursor = db.rawQuery(" SELECT str.EvaluationType, indcnam.Description AS IndicatorDescription, iaainf.Description AS IaaDescription, iatinf.Description AS IatDescription, ialinf.Description AS IalDescription, esinf.Description AS EsDescription, ideinf.Description AS IdeDescription, ialinf.ElementType AS IalElementType, esinf.ElementType AS EsElementType, cdi.IDC,"
                +" eva.*, ifnull(cdi.ISC, -1) AS ISC, ifnull(str.IE, -1) AS IE, ifnull(strdamage.IDE, -1) AS IDE FROM evaluations eva"
                +" LEFT JOIN corrosiondamageindexes cdi ON cdi.EvaluationId = eva.EvaluationId AND cdi.Enabled = 1"
                +" LEFT JOIN idcindicators idcin ON cdi.CorrosionDamageIndexId = idcin.CorrosionDamageIndexId AND idcin.Enabled = 1"
                +" LEFT JOIN indicatornames indcnam ON indcnam.IndicatorNameId = idcin.IndicatorNameId AND  indcnam.Enabled = 1"
                +" LEFT JOIN iaainformation iaainf ON iaainf.IAAInformationId = cdi.IAAIndicatorId AND iaainf.Enabled = 1"
                +" LEFT JOIN structuralindexes str ON str.EvaluationId = eva.EvaluationId AND str.Enabled = 1"
                +" LEFT JOIN iat inat ON inat.StructuralIndexId = str.StructuralIndexId AND inat.Enabled = 1"
                +" LEFT JOIN iatinformation iatinf ON iatinf.IATInformationId = inat.IATInformationId AND iatinf.Enabled = 1"
                +" LEFT JOIN ial inal ON inal.StructuralIndexId = str.StructuralIndexId AND inal.Enabled = 1"
                +" LEFT JOIN ialinformation ialinf ON ialinf.IALInformationId = inal.IALInformationId AND ialinf.Enabled = 1"
                +" LEFT JOIN es ens ON ens.StructuralIndexId = str.StructuralIndexId AND ens.Enabled = 1"
                +" LEFT JOIN esinformation esinf ON esinf.ESInformationId = ens.ESInformationId AND esinf.Enabled = 1"
                +" LEFT JOIN structuraldamageindexes strdamage ON strdamage.EvaluationId = eva.EvaluationId AND strdamage.Enabled = 1"
                +" LEFT JOIN ide inde ON inde.StructuralDamageIndexId = strdamage.StructuralDamageIndexId AND inde.Enabled = 1"
                +" LEFT JOIN ideinformation ideinf ON ideinf.IDEInformationId = inde.IDEInformationId AND ideinf.Enabled = 1"
                +" WHERE eva.EvaluationId = '"+evaluationId+"'"
                +" GROUP BY (indcnam.Description)"
                +" LIMIT 6;", null);
        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            return null;
        }
    }


    public void updateToken(String newToken, String oldTokenId){
        ContentValues values = new ContentValues();
        values.put("Token", newToken);
        db.update("Tokens",values,"TokenId = "+oldTokenId+";",null);
    }



    //--------------------------------------------------------------SET PROJECT SERVER INTERNET-----
    public boolean existIDEProject(String pProjectId){
        openConnection();
        Cursor cursor = db.rawQuery("SELECT SDI.IDE\n" +
                "FROM evaluations eva \n" +
                "INNER JOIN structuraldamageindexes SDI ON SDI.EvaluationId = eva.EvaluationId\n" +
                "INNER JOIN\n" +
                "ide ideTable ON ideTable.StructuralDamageIndexId = SDI.StructuralDamageIndexId\n" +
                "INNER JOIN \n" +
                "ideinformation IDE_Info ON IDE_Info.IDEInformationId = ideTable.IDEInformationId\n" +
                "WHERE\n" +
                "eva.ProjectId = '"+pProjectId+"' AND\n" +
                "ideTable.Enabled = 1 AND\n" +
                "IDE_Info.Enabled = 1 AND\n" +
                "eva.Enabled = 1;", null);
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }


    public boolean existIDEToken(String pTokenId){
        Cursor cursor = db.rawQuery("SELECT SDI.IDE\n" +
                "FROM evaluations eva \n" +
                "INNER JOIN structuraldamageindexes SDI ON SDI.EvaluationId = eva.EvaluationId\n" +
                "INNER JOIN\n" +
                "ide ideTable ON ideTable.StructuralDamageIndexId = SDI.StructuralDamageIndexId\n" +
                "INNER JOIN \n" +
                "ideinformation IDE_Info ON IDE_Info.IDEInformationId = ideTable.IDEInformationId\n" +
                "WHERE\n" +
                "eva.TokenId = '"+pTokenId+"' AND\n" +
                "ideTable.Enabled = 1 AND\n" +
                "IDE_Info.Enabled = 1 AND\n" +
                "eva.Enabled = 1;", null);
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existCorrosionIndex(String evaluationId){
        Cursor getCorrosionIndex = db.rawQuery("SELECT CDI.IDC, CDI.IAA, CDI.ISC, IAAInfo.Row AS IAA_Row\n" +
                "FROM corrosiondamageindexes CDI\n" +
                "INNER JOIN iaainformation IAAInfo\n" +
                "ON IAAInfo.IAAInformationId = CDI.IAAIndicatorId\n" +
                "WHERE\n" +
                "CDI.EvaluationId = '"+evaluationId+"' AND\n" +
                "CDI.Enabled = 1;" , null);
        if (getCorrosionIndex.getCount() == 1){
            return true;
        } else {
            return false;
        }
    }

    public boolean existStructuralIndex(String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT EvaluationType FROM structuralindexes WHERE EvaluationId = '"+pEvaluationId+"' AND Enabled = 1;", null);
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }


    public Cursor countEvaluationProject(String projectId){
        return db.rawQuery("SELECT pro.* FROM projects pro " +
                "INNER JOIN evaluations eva ON pro.ProjectId = eva.ProjectId " +
                "WHERE pro.ProjectId = '"+projectId+"';", null);
    }

    public void saveProjectMySQL(String projectId, Context pAppContext, LoadProject loadproject) throws JSONException {
        int countEvaluation = countEvaluationProject(projectId).getCount();
        if(countEvaluation == 1){
            System.out.println("-------------HAY EVALUACIÓN-------------------------------------");
            if (existIDEProject(projectId)){
                System.out.println("-------------HAY IDE-------------------------------------");
                saveProjectMySQL_GetEmail(projectId, pAppContext, countEvaluation, loadproject);
            }
        } else {
            saveProjectMySQL_GetEmail(projectId, pAppContext, countEvaluation, loadproject);
        }

        System.out.println("-------------project NO SE PUEDE-------------------------------------");

    }

    public void saveTokenProjectMySQL(String tokenId, String tokenName, Context pAppContext, LoadProject loadproject){
        if (existIDEToken(tokenId)){
            System.out.println("-------------token exist evaluation IDE-------------------------------------");
            saveTokenProjectMySQL_Aux(tokenId, tokenName, pAppContext, loadproject);
        }
    }

    public void saveTokenProjectMySQL_Aux(final String tokenId, final String tokenName, final Context pAppContext, final LoadProject loadproject){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, GET_PROJECT_BY_TOKEN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String code = obj.get("code").toString();
                            if (code.equals("1")){
                                JSONArray result = (JSONArray)obj.get("result");
                                String projectId = ((JSONObject)result.get(0)).get("ProjectId").toString();
                                createEvaluation_MySQL(projectId, "0", tokenId, pAppContext);

                                openConnection();
                                /*
                                ContentValues values = new ContentValues();
                                values.put("Enabled", 0);
                                db.update("Tokens",values,"TokenId = "+tokenId+";",null);
*/
                                loadproject.loadTokenList();
                            } else {
                                loadproject.insertToken(tokenId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", tokenName);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void saveProjectMySQL_GetEmail(final String pProjectId, final Context pAppContext, final int pEvaluationCount, final  LoadProject loadproject ) throws JSONException {
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, GET_EMAIL_BY_TOKEN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray result = (JSONArray)obj.get("result");
                            String Email = ((JSONObject)result.get(0)).get("Email").toString();
                            saveProjectMySQL_Aux(pProjectId, Email, pAppContext, pEvaluationCount, loadproject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", getUserToken().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void saveProjectMySQL_Aux(final String pProjectId, final String pEmail, final Context pAppContext, final int pEvaluationCount, final  LoadProject loadproject){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SAVE_PROJECT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String projectId_sql = jsonObject.getString("ProjectId").toString();
                            String code = jsonObject.getString("Code").toString();
                            if(code.equals("1")){
                                /*
                                ContentValues values = new ContentValues();
                                values.put("Enabled", 0);
                                long value = db.update("Projects",values,"ProjectId = "+pProjectId+";",null);
                                */
                                loadproject.loadProjectList();

                                if(pEvaluationCount > 0){
                                    System.out.println("------------------------VA A CREAR LA EVALUACIÓN-------------------------");
                                   createEvaluation_MySQL(projectId_sql, pProjectId,  "0", pAppContext);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                Cursor getProject = db.rawQuery("SELECT pro.*, strty.Name AS StructureType FROM projects pro " +
                        "INNER JOIN structuretypes strty ON pro.StructureTypeId = strty.StructureTypeId " +
                        "WHERE pro.ProjectId = '"+pProjectId+"' AND pro.Enabled = 1 LIMIT 1;", null);
                getProject.moveToFirst();
                params.put("projectName", getProject.getString(getProject.getColumnIndex("Name")));
                params.put("buildingDate", getProject.getString(getProject.getColumnIndex("StructureCreationDate")));
                params.put("componentDescription", getProject.getString(getProject.getColumnIndex("ComponentDescription")));
                params.put("generalDescription", getProject.getString(getProject.getColumnIndex("StructureUseDescription")));
                params.put("districtId", getProject.getString(getProject.getColumnIndex("DistrictId")));
                params.put("structureType", getProject.getString(getProject.getColumnIndex("StructureType")));
                params.put("latitude", getProject.getString(getProject.getColumnIndex("Latitude")));
                params.put("longitude", getProject.getString(getProject.getColumnIndex("Longitude")));
                params.put("email", pEmail);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void createEvaluation_MySQL(final String projectId_sql, final String projectId, final String tokenId, final Context pAppContext){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, CREATE_EVALUATION,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("--------------ENTRO A CREAR LA EVALUACIÓN---------------------"+response);
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String evaluationId_sql = jsonObject.getString("EvaluationId").toString();
                            System.out.println("--------------existIDEProject---------------------"+existIDEProject(projectId));
                            if (existIDEProject(projectId) || existIDEToken(tokenId)){
                                System.out.println("--------------1---------saveStructuralDamageIndex_MySQL---------------------");
                                saveStructuralDamageIndex_MySQL(getEvaluationId(tokenId, projectId), evaluationId_sql, pAppContext);
                            } if (existCorrosionIndex(getEvaluationId(tokenId, projectId))){
                                System.out.println("------------2-----------saveCorrosionIndex_MySQL---------------------");
                                //saveCorrosionIndex_MySQL(getEvaluationId(tokenId, projectId),evaluationId_sql, pAppContext);
                            } if (existStructuralIndex(getEvaluationId(tokenId, projectId))){
                                System.out.println("--------------3---------saveStructuralIndex_MySQL---------------------");
                                if(getEvaluationType(getEvaluationId(tokenId, projectId)) == 1){
                                    System.out.println("--------------4---------manual---------------------");
                                    SAVE_STRUCTURAL_INDEX_MODE = SAVE_STRUCTURAL_INDEX_MANUAL_AUX;
                                    saveStructuralIndex_MySQL(getEvaluationId(tokenId, projectId), evaluationId_sql, pAppContext);
                                } else {
                                    System.out.println("--------------5---------simplificada---------------------");
                                    SAVE_STRUCTURAL_INDEX_MODE = SAVE_STRUCTURAL_INDEX_SIMPLIFIED_AUX;
                                    saveStructuralIndex_MySQL(getEvaluationId(tokenId, projectId), evaluationId_sql, pAppContext);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("projectId", projectId_sql);
                return params;
            }
        };
        queue.add(postRequest);
    }


    public void saveStructuralDamageIndex_MySQL(final String evaluationId, final String evaluationId_sql, final Context pAppContext){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SAVE_STRUCTURAL_DAMAGE_INDEX,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("--------------------response---------"+response);
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                Cursor getSDI = db.rawQuery("SELECT SDI.IDE, SDI.StructuralIndex AS structuralIndex, SDI.CorrosionIndex AS corrosionIndex, IDE_Info.Row AS IDE_Row, IDE_Info.Col AS IDE_Col, IDE_Info.failureConsequence AS failureConsequence\n" +
                        "FROM structuraldamageindexes SDI\n" +
                        "INNER JOIN\n" +
                        "ide ideTable ON ideTable.StructuralDamageIndexId = SDI.StructuralDamageIndexId\n" +
                        "INNER JOIN \n" +
                        "ideinformation IDE_Info ON IDE_Info.IDEInformationId = ideTable.IDEInformationId\n" +
                        "WHERE\n" +
                        "SDI.EvaluationId = '"+evaluationId+"' AND\n" +
                        "SDI.Enabled = 1 AND\n" +
                        "ideTable.Enabled = 1 AND\n" +
                        "IDE_Info.Enabled = 1;" , null);
                if(getSDI.getCount() == 1) {
                    getSDI.moveToFirst();
                    params.put("IDE", getSDI.getString(getSDI.getColumnIndex("IDE")));
                    params.put("structuralIndex", getSDI.getString(getSDI.getColumnIndex("structuralIndex")));
                    params.put("corrosionIndex", getSDI.getString(getSDI.getColumnIndex("corrosionIndex")));
                    params.put("IDE_Row", getSDI.getString(getSDI.getColumnIndex("IDE_Row")));
                    params.put("IDE_Col", getSDI.getString(getSDI.getColumnIndex("IDE_Col")));
                    params.put("failureConsequence", getSDI.getString(getSDI.getColumnIndex("failureConsequence")));
                    params.put("evaluationId", evaluationId_sql);
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void saveCorrosionIndex_MySQL(final String evaluationId, final String evaluationId_sql, final Context pAppContext){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SAVE_CORROSION_INDEX,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                Cursor getCorrosionIndex = db.rawQuery("SELECT CDI.IDC, CDI.IAA, CDI.ISC, IAAInfo.Row AS IAA_Row\n" +
                        "FROM corrosiondamageindexes CDI\n" +
                        "INNER JOIN iaainformation IAAInfo\n" +
                        "ON IAAInfo.IAAInformationId = CDI.IAAIndicatorId\n" +
                        "WHERE\n" +
                        "CDI.EvaluationId = '"+evaluationId+"' AND\n" +
                        "CDI.Enabled = 1;" , null);
                if(getCorrosionIndex.getCount() == 1) {
                    getCorrosionIndex.moveToFirst();
                    params.put("IDC", getCorrosionIndex.getString(getCorrosionIndex.getColumnIndex("IDC")));
                    params.put("IAA", getCorrosionIndex.getString(getCorrosionIndex.getColumnIndex("IAA")));
                    params.put("IAA_Row", getCorrosionIndex.getString(getCorrosionIndex.getColumnIndex("IAA_Row")));
                    params.put("evaluationId", evaluationId_sql);
                    //$ISC = $_POST['ISC'];
                    //$indicators = $_POST['indicators'];
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public int getEvaluationType(String pEvaluationId){
        Cursor cursor = db.rawQuery("SELECT EvaluationType FROM structuralindexes WHERE EvaluationId = '"+pEvaluationId+"' AND Enabled = 1;", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("EvaluationType"));
        } else {
            return -1;
        }
    }

    public void saveStructuralIndex_MySQL(final String evaluationId, final String evaluationId_sql, final Context pAppContext){
        final RequestQueue queue = Volley.newRequestQueue(pAppContext);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SAVE_STRUCTURAL_INDEX_MODE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("---------------------Response 2------------"+response);
                        queue.stop();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("---------------error------------------"+error);
                        queue.stop();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                int evaluationMode = getEvaluationType(evaluationId);
                if(evaluationMode == 1){
                    Cursor getStructuralIndex = db.rawQuery("SELECT trans.IE AS structuralIndex, trans.IAT AS transverseIndex, trans.Row AS transverseRow, trans.Col AS transverseColumn, \n" +
                            "longi.IAL AS longitudinalIndex, longi.Row AS longitudinalRow, longi.Col AS longitudinalColumn, longi.ElementType AS ElementType\n" +
                            "FROM" +
                            "(SELECT stru.StructuralIndexId, IE, IAT, iatinf.Row, iatinf.Col, ElementType FROM structuralindexes stru \n" +
                            "INNER JOIN iat ON stru.StructuralIndexId = iat.StructuralIndexId\n" +
                            "INNER JOIN iatinformation iatinf ON iat.IATInformationId = iatinf.IATInformationId\n" +
                            "WHERE stru.EvaluationId = "+evaluationId+" AND stru.Enabled = 1 AND iat.Enabled = 1 AND iatinf.Enabled = 1) trans\n" +
                            "INNER JOIN" +
                            "(SELECT stru.StructuralIndexId, IE, IAL, ialinf.Row, ialinf.Col, ElementType FROM structuralindexes stru\n" +
                            "INNER JOIN ial ON stru.StructuralIndexId = ial.StructuralIndexId\n" +
                            "INNER JOIN ialinformation ialinf ON ial.IALInformationId = ialinf.IALInformationId\n" +
                            "WHERE stru.EvaluationId = "+evaluationId+" AND stru.Enabled = 1 AND ial.Enabled = 1 AND ialinf.Enabled = 1) longi WHERE  trans.StructuralIndexId = longi.StructuralIndexId;", null);
                    if(getStructuralIndex.getCount() == 1) {
                        getStructuralIndex.moveToFirst();
                        params.put("structuralIndex", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("structuralIndex")));
                        params.put("transverseIndex", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("transverseIndex")));
                        params.put("transverseRow", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("transverseRow")));
                        params.put("transverseColumn", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("transverseColumn")));
                        params.put("longitudinalIndex", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("longitudinalIndex")));
                        params.put("longitudinalRow", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("longitudinalRow")));
                        params.put("longitudinalColumn", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("longitudinalColumn")));
                        params.put("elementType", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("ElementType")));
                        params.put("evaluationId", evaluationId_sql);
                    }

                }
                else if(evaluationMode == 0){
                    Cursor getStructuralIndex = db.rawQuery("SELECT IE AS structuralIndex, ES AS simplifiedIndex, esinf.Row AS simplifiedRow, esinf.Col AS simplifiedColumn, ElementType FROM structuralindexes stru \n" +
                            "INNER JOIN es ON stru.StructuralIndexId = es.StructuralIndexId\n" +
                            "INNER JOIN esinformation esinf ON es.ESInformationId = esinf.ESInformationId\n" +
                            "WHERE stru.EvaluationId = "+evaluationId+" AND stru.Enabled = 1 AND es.Enabled = 1 AND esinf.Enabled = 1;" , null);
                    if(getStructuralIndex.getCount() == 1) {
                        getStructuralIndex.moveToFirst();
                        params.put("structuralIndex", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("structuralIndex")));
                        params.put("simplifiedIndex", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("simplifiedIndex")));
                        params.put("simplifiedRow", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("simplifiedRow")));
                        params.put("simplifiedColumn", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("simplifiedColumn")));
                        params.put("elementType", getStructuralIndex.getString(getStructuralIndex.getColumnIndex("ElementType")));
                        params.put("evaluationId", evaluationId_sql);
                    }
                }
                return params;
            }
        };
        queue.add(postRequest);
    }



















}
