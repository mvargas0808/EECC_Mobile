package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Double2;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Common.Constants;
import Common.Methods;
import DataBase.DataBaseManager;


public class CorrosionIndex extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {


    /*Indicators Table Values*/
    JSONObject carbonationDepth;
    JSONObject chlorideLevel;
    JSONObject cracking;
    JSONObject resistivity;
    JSONObject sectionLoss;
    JSONObject intensity;
    JSONObject idcIndexJson;

    JSONObject iaaIndexJson;
    ArrayList<String> iaaTable;

    Context appContext;
    DataBaseManager manager;
    JSONObject prevActivityParams;

    boolean existPrevICEvaluation;


    Button btn_calculateIDC;
    Button btn_GoToIAA;
    Button btn_calculateIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corrosion_index_indicators);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        appContext = this;
        manager = new DataBaseManager(this);
        prevActivityParams = Methods.getJsonParamFromPreviousScreen(getIntent(), "data");




        carbonationDepth = null;
        chlorideLevel = null;
        cracking = null;
        resistivity = null;
        sectionLoss = null;
        intensity = null;
        idcIndexJson = null;

        iaaIndexJson = null;
        iaaTable = null;
        iaaTable = loadIAATable();

        existPrevICEvaluation = false;

        loadPreviousCorrosionIndexEvaluation();


        btn_calculateIDC = (Button) findViewById(R.id.btn_calculateIDC);
        btn_GoToIAA = (Button) findViewById(R.id.btn_GoToIAA);
        btn_calculateIC = (Button) findViewById(R.id.btn_calculateIC);

        btn_calculateIDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIDC();
            }
        });

        btn_GoToIAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.corrosion_index_iaa);
                if (iaaIndexJson != null){
                    setIAAInformation(iaaIndexJson);
                }
            }
        });

        /*btn_calculateIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateIC();
            }
        });*/

    }

    // Loads previous Corrosion Index Evaluation
    public void loadPreviousCorrosionIndexEvaluation(){



        manager.openConnection();

        if(prevActivityParams != null) {
            try {
                String evaluationId = prevActivityParams.get("activityType").toString();

                JSONObject jsonCorrosionIndexInfo = manager.loadCorrosionIndexValues(evaluationId);

                if (jsonCorrosionIndexInfo != null){

                    int corrosionIndexId = jsonCorrosionIndexInfo.getInt("corrosionId");
                    double IDC = jsonCorrosionIndexInfo.getDouble("IDC");
                    double corrosionIndex = jsonCorrosionIndexInfo.getDouble("ISC");
                    int IAA = jsonCorrosionIndexInfo.getInt("IAA");
                    int IAA_Row = jsonCorrosionIndexInfo.getInt("IAA_Row");

                    //Loads iaaIndexJson and displays IAA information
                    //calculateIAA(IAA_Row - 1);
                    iaaIndexJson = getIAAFromTable(IAA_Row - 1);


                    //Loads idcIndexJson and displays IDC information
                    idcIndexJson = null;
                    idcIndexJson = new JSONObject("{'IDC':"+IDC+"}");

                    displayIDEValue(IDC);

                    ArrayList<String> indicators = manager.getCorrosionIndicatorsValues(corrosionIndexId);

                    if(indicators.size() > 0){
                        for(int indicatorIndex = 0; indicatorIndex < indicators.size(); indicatorIndex ++){
                            loadIndicatorsJsons(indicators.get(indicatorIndex));
                        }
                    }

                    existPrevICEvaluation = true;
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(appContext, Constants.ERROR_EVALUATIONID_FROM_EVA_MENU, Toast.LENGTH_LONG).show();
        }



        manager.closeConnection();

    }

    // Loads the indicators jsons, and display the indicator's values on the screen
    public void loadIndicatorsJsons(String pIndicatorInfo){

        TextView indicatorTextView = null;
        JSONObject indicadorInfoJson = null;

        try {
            indicadorInfoJson = new JSONObject(pIndicatorInfo);


            switch (indicadorInfoJson.getString("indicatorName")){

                case "carbonationDepth":
                    carbonationDepth = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat1value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                case "chlorideLevel":
                    chlorideLevel = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat2value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                case "cracking":
                    cracking = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat3value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                case "resistivity":
                    resistivity = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat4value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                case "sectionLoss":
                    sectionLoss = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat5value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                case "intensity":
                    intensity = indicadorInfoJson;
                    indicatorTextView = (TextView) findViewById(R.id.iat6value);
                    indicatorTextView.setText(indicadorInfoJson.getString("value"));
                    break;

                default:
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Validates if IDC has been calculated
    public boolean isIDCCalculated(){

        if(idcIndexJson != null){
            return true;
        }

        return false;
    }

    // Validates if IAA has been calculated
    public boolean isIAACalculated(){
        if(iaaIndexJson != null){
            return true;
        }

        return false;
    }

    // Calculates de Corrosion Index (IC)
    public double calculateIC(){

        try {
            double idcValue = idcIndexJson.getDouble("IDC");
            int iaaValue = iaaIndexJson.getInt("weight");

            double icValue = (idcValue + iaaValue) / 2;

            return icValue;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Evaluates de Corrosion Index (IC) and send it to the database
    public void evaluateIC(View view){
        if (isIAACalculated() && isIDCCalculated()) {
            double iscValueResult = calculateIC();

            if (iscValueResult != -1 ){
                if(prevActivityParams != null) {
                    try {
                        String evaluationId = prevActivityParams.get("activityType").toString();

                        if(!existPrevICEvaluation){
                            saveCorrosionIndex(iscValueResult, idcIndexJson, iaaIndexJson, evaluationId);
                        } else {
                            updateCorrosionIndex(iscValueResult, idcIndexJson, iaaIndexJson, evaluationId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(appContext, Constants.ERROR_EVALUATIONID_FROM_EVA_MENU, Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(appContext, Constants.IC_CALCULATE_ERROR, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(appContext, Constants.EVALUATE_IC_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    // Saves the Corrosion Index on Database
    public void saveCorrosionIndex(double pCorrosionIndex, JSONObject pIDCJson, JSONObject pIAAJson, String pEvaluationId){
        try {
            double IDC = pIDCJson.getDouble("IDC");
            int IAA_Value = pIAAJson.getInt("weight");
            int IAA_Row = pIAAJson.getInt("row");

            manager.openConnection();

            long IAAInfoId = manager.getIAAInformationId(IAA_Row);

            if (IAAInfoId != -1){

                long corrosionIndexId = manager.insertCorrosionIndex(pEvaluationId,IDC,IAA_Value,pCorrosionIndex,IAAInfoId);

                // Corrosion Damage Indicators are going to be saved
                if (corrosionIndexId != -1){


                    long sivCarbonationDepth = saveIndicatorsValues(manager, corrosionIndexId, carbonationDepth);
                    long sivChlorideLevel = saveIndicatorsValues(manager, corrosionIndexId, chlorideLevel);
                    long sivCracking = saveIndicatorsValues(manager, corrosionIndexId, cracking);
                    long sivResistivity = saveIndicatorsValues(manager, corrosionIndexId, resistivity);
                    long sivSectionLoss = saveIndicatorsValues(manager, corrosionIndexId, sectionLoss);
                    long sivIntensity = saveIndicatorsValues(manager, corrosionIndexId, intensity);

                    if (sivCarbonationDepth != -1 && sivChlorideLevel != -1 && sivCracking != -1 && sivResistivity != -1 && sivSectionLoss != -1 && sivIntensity != -1){

                        Toast.makeText(appContext, Constants.SUCCESS_SAVE_IC, Toast.LENGTH_LONG).show();

                        Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class, "json", new JSONObject("{'evaluationId':"+pEvaluationId+"}"));
                        finish();

                    } else {
                        Toast.makeText(appContext, Constants.ERROR_SAVE_IC, Toast.LENGTH_LONG).show();
                    }

                }

            } else {
                Toast.makeText(appContext, Constants.ERROR_SAVE_IC, Toast.LENGTH_LONG).show();
            }

            manager.closeConnection();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Updates the Corrosion Index on Database
    public void updateCorrosionIndex(double pCorrosionIndex, JSONObject pIDCJson, JSONObject pIAAJson, String pEvaluationId){
        try {
            double IDC = pIDCJson.getDouble("IDC");
            int IAA_Value = pIAAJson.getInt("weight");
            int IAA_Row = pIAAJson.getInt("row");

            manager.openConnection();

            long IAAInfoId = manager.getIAAInformationId(IAA_Row);

            if (IAAInfoId != -1){

                long corrosionIndexId = manager.getCorrosionIndexId(pEvaluationId);

                if (corrosionIndexId != -1){

                    long updateResult = manager.updateCorrosionIndex(corrosionIndexId, IDC, IAA_Value, pCorrosionIndex, IAAInfoId);

                    if (updateResult != -1){


                        long disableResult = manager.disableIndicators(corrosionIndexId);

                        if (disableResult != -1){


                            long sivCarbonationDepth = saveIndicatorsValues(manager, corrosionIndexId, carbonationDepth);
                            long sivChlorideLevel = saveIndicatorsValues(manager, corrosionIndexId, chlorideLevel);
                            long sivCracking = saveIndicatorsValues(manager, corrosionIndexId, cracking);
                            long sivResistivity = saveIndicatorsValues(manager, corrosionIndexId, resistivity);
                            long sivSectionLoss = saveIndicatorsValues(manager, corrosionIndexId, sectionLoss);
                            long sivIntensity = saveIndicatorsValues(manager, corrosionIndexId, intensity);

                            if (sivCarbonationDepth != -1 && sivChlorideLevel != -1 && sivCracking != -1 && sivResistivity != -1 && sivSectionLoss != -1 && sivIntensity != -1){

                                Toast.makeText(appContext, Constants.SUCCESS_UPDATE_IC, Toast.LENGTH_LONG).show();

                                Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class, "json", new JSONObject("{'evaluationId':"+pEvaluationId+"}"));
                                finish();

                            } else {
                                Toast.makeText(appContext, Constants.ERROR_UPDATE_IC, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(appContext, Constants.ERROR_DISABLE_INDICATORS, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(appContext, Constants.ERROR_UPDATE_EVALUATION, Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(appContext, Constants.IC_ID_ERROR, Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(appContext, Constants.ERROR_SAVE_IC, Toast.LENGTH_LONG).show();
            }

            manager.closeConnection();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Saves the IDC indicators values on Database
    public long saveIndicatorsValues(DataBaseManager pManager, long pCorrosionIndexId, JSONObject pIndicatorInfoJson){


        try {

            int indicatorRow = pIndicatorInfoJson.getInt("row");
            int indicatorCol = pIndicatorInfoJson.getInt("column");
            int indicatorValue = pIndicatorInfoJson.getInt("value");

            long indicatorNameId = pManager.getIndicatorNameId(indicatorRow, indicatorCol);

            if (indicatorNameId != -1){
                long indicadorResult = pManager.saveIndicatorValue(pCorrosionIndexId, indicatorValue, indicatorNameId);
                return indicadorResult;
            } else {
                Toast.makeText(appContext, Constants.ERROR_INSERT_INDICATOR, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;

    }

    // Calculates de Corrosion Damage Index (IDC)
    public void calculateIDC(){

        if(validIndicators()){

            try {

                String resultString = "";

                //Sumatoria of indicators / number of indicators

                double sum = (carbonationDepth.getDouble("value") + chlorideLevel.getDouble("value") + cracking.getDouble("value") + resistivity.getDouble("value") + sectionLoss.getDouble("value") + intensity.getDouble("value"))/6;

                resultString = Double.toString(sum);

                double idcResult = Methods.convertStringToDecimal(resultString);

                idcIndexJson = null;
                idcIndexJson = new JSONObject("{'IDC':"+idcResult+"}");

                /*EditText IDC_EditText = (EditText) findViewById(R.id.et_IDC);

                IDC_EditText.setText(Double.toString(idcResult));*/

                displayIDEValue(idcResult);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(appContext, Constants.INDICATORS_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    // Displays the IDE result on EditView
    public void displayIDEValue(double pIDC){
        EditText IDC_EditText = (EditText) findViewById(R.id.et_IDC);

        IDC_EditText.setText(Double.toString(pIDC));
    }

    // Validates if all the indicators were selected
    public boolean validIndicators(){

        if(
                carbonationDepth != null &&
                        chlorideLevel != null &&
                        cracking != null &&
                        resistivity != null &&
                        sectionLoss != null &&
                        intensity != null){
            return true;
        }

        return false;
    }

    // Calculates de Environment Agresivity Index (IAA)
    public void calculateIAA(int pRow){
        iaaIndexJson = getIAAFromTable(pRow);

        if (iaaIndexJson != null){
            setIAAInformation(iaaIndexJson);
        } else {
            Toast.makeText(appContext, Constants.ERROR_LOAD_TABLE_DATA, Toast.LENGTH_LONG).show();
        }

    }

    // Show the IAA information selected from IAA table
    public void setIAAInformation(JSONObject pIAAIndexJson){

        if (pIAAIndexJson != null){

            EditText weightEditText = (EditText) findViewById(R.id.ed_weight);
            EditText classEditText = (EditText) findViewById(R.id.ed_class);
            EditText explanationEditText = (EditText) findViewById(R.id.ed_explanation);


            try {

                weightEditText.setText(Integer.toString(pIAAIndexJson.getInt("weight")));
                classEditText.setText(pIAAIndexJson.getString("class"));
                explanationEditText.setText(pIAAIndexJson.getString("explanation"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(appContext, Constants.ERROR_LOAD_TABLE_DATA, Toast.LENGTH_LONG).show();
        }

    }

    public void setIDC(View v){

        // Value, Rox, Column

        switch (v.getId()) {
            case R.id.idc_11:
                setIDCRow1Value(1, 1, 1);
                break;
            case R.id.idc_12:
                setIDCRow1Value(2, 1, 2);
                break;
            case R.id.idc_13:
                setIDCRow1Value(3, 1, 3);
                break;
            case R.id.idc_14:
                setIDCRow1Value(4, 1, 4);
                break;


            case R.id.idc_21:
                setIDCRow2Value(1, 2, 1);
                break;
            case R.id.idc_22:
                setIDCRow2Value(2, 2, 2);
                break;
            case R.id.idc_23:
                setIDCRow2Value(3, 2, 3);
                break;
            case R.id.idc_24:
                setIDCRow2Value(4, 2, 4);
                break;



            case R.id.idc_31:
                setIDCRow3Value(1, 3, 1);
                break;
            case R.id.idc_32:
                setIDCRow3Value(2, 3, 2);
                break;
            case R.id.idc_33:
                setIDCRow3Value(3, 3, 3);
                break;
            case R.id.idc_34:
                setIDCRow3Value(4, 3, 4);
                break;



            case R.id.idc_41:
                setIDCRow4Value(1, 4, 1);
                break;
            case R.id.idc_42:
                setIDCRow4Value(2, 4, 2);
                break;
            case R.id.idc_43:
                setIDCRow4Value(3, 4, 3);
                break;
            case R.id.idc_44:
                setIDCRow4Value(4, 4, 4);
                break;


            case R.id.idc_51:
                setIDCRow5Value(1, 5, 1);
                break;
            case R.id.idc_52:
                setIDCRow5Value(2, 5, 2);
                break;
            case R.id.idc_53:
                setIDCRow5Value(3, 5, 3);
                break;
            case R.id.idc_54:
                setIDCRow5Value(4, 5, 4);
                break;


            case R.id.idc_61:
                setIDCRow6Value(1, 6, 1);
                break;
            case R.id.idc_62:
                setIDCRow6Value(2, 6, 2);
                break;
            case R.id.idc_63:
                setIDCRow6Value(3, 6, 3);
                break;
            case R.id.idc_64:
                setIDCRow6Value(4, 6, 4);
                break;


            default:
                break;
        }
    }

    public void setIDCRow1Value(int pValue, int pRow, int pColumn){

        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            carbonationDepth = null;
            carbonationDepth = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat1value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow2Value(int pValue, int pRow, int pColumn){

        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            chlorideLevel = null;
            chlorideLevel = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat2value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow3Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            cracking = null;
            cracking = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat3value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow4Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            resistivity = null;
            resistivity = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat4value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow5Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            sectionLoss = null;
            sectionLoss = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat5value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow6Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            intensity = null;
            intensity = new JSONObject(jsonString);

            TextView indicatorTextView = (TextView) findViewById(R.id.iat6value);
            indicatorTextView.setText(Integer.toString(pValue));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIAA(View v){

        // Row Index

        switch (v.getId()) {
            case R.id.iaa_11:
                calculateIAA(0);
                break;
            case R.id.iaa_12:
                calculateIAA(0);
                break;

            case R.id.iaa_21:
                calculateIAA(1);
                break;
            case R.id.iaa_22:
                calculateIAA(1);
                break;

            case R.id.iaa_31:
                calculateIAA(2);
                break;
            case R.id.iaa_32:
                calculateIAA(2);
                break;

            case R.id.iaa_41:
                calculateIAA(3);
                break;
            case R.id.iaa_42:
                calculateIAA(3);
                break;

            case R.id.iaa_51:
                calculateIAA(4);
                break;
            case R.id.iaa_52:
                calculateIAA(4);
                break;

            case R.id.iaa_61:
                calculateIAA(5);
                break;
            case R.id.iaa_62:
                calculateIAA(5);
                break;

            case R.id.iaa_71:
                calculateIAA(6);
                break;
            case R.id.iaa_72:
                calculateIAA(6);
                break;

            case R.id.iaa_81:
                calculateIAA(7);
                break;
            case R.id.iaa_82:
                calculateIAA(7);
                break;

            case R.id.iaa_91:
                calculateIAA(8);
                break;
            case R.id.iaa_92:
                calculateIAA(8);
                break;

            case R.id.iaa_101:
                calculateIAA(9);
                break;
            case R.id.iaa_102:
                calculateIAA(9);
                break;

            case R.id.iaa_111:
                calculateIAA(10);
                break;
            case R.id.iaa_112:
                calculateIAA(10);
                break;

            default:
                break;
        }
    }

    // Returns a json with information of the selected row from IAA Table
    public JSONObject getIAAFromTable(int pRowIndex){
        JSONObject resultJson = null;

        try {

            String jsonInformation = iaaTable.get(pRowIndex);

            resultJson = new JSONObject(jsonInformation);

            return resultJson;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Loads an array with the IAA Table information
    public ArrayList<String> loadIAATable(){

        ArrayList<String> iaaTable = new ArrayList<>();

        iaaTable.add("{'row':1, 'weight':0, 'class':'X0', 'explanation':'Para hormigones en masa, todos los ambientes excepto donde hay acciones de deshielo, abrasión o ataques químicos'}");
        iaaTable.add("{'row':2, 'weight':1, 'class':'XC1', 'explanation':'Seco o permanentemente húmedo'}");
        iaaTable.add("{'row':3, 'weight':1, 'class':'XC2', 'explanation':'Húmedo, raramente seco.'}");
        iaaTable.add("{'row':4, 'weight':2, 'class':'XC3', 'explanation':'Humedad moderada.'}");
        iaaTable.add("{'row':5, 'weight':3, 'class':'XC4', 'explanation':'Ciclos húmedos y secos.'}");
        iaaTable.add("{'row':6, 'weight':2, 'class':'XD1', 'explanation':'Moderadamente húmedo'}");
        iaaTable.add("{'row':7, 'weight':3, 'class':'XD2', 'explanation':'Húmedo, raramente seco.'}");
        iaaTable.add("{'row':8, 'weight':4, 'class':'XD3', 'explanation':'Ciclos húmedos y secos.'}");
        iaaTable.add("{'row':9, 'weight':2, 'class':'XS1', 'explanation':'Exposición a la acción de la sal contenida en el aire pero no en contacto con el agua de mar.'}");
        iaaTable.add("{'row':10, 'weight':3, 'class':'XS2', 'explanation':'Permanentemente sumergidas.'}");
        iaaTable.add("{'row':11, 'weight':4, 'class':'XS3', 'explanation':'Zonas expuestas a la acción de la marea o salpicaduras.'}");

        return iaaTable;
    }






    @Override
    public void onClick(View v) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_projects) {
            Methods.changeScreen(this, Projects.class);
            finish();
        } else if (id == R.id.nav_upload) {
            Methods.changeScreen(this, LoadProject.class);
            finish();
        } else if (id == R.id.nav_logout) {
            Methods.changeScreen(this,Login.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
