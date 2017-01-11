package com.itcr.eecc.eecc;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Common.Constants;
import Common.Methods;

public class StructuralDamage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private JSONObject jsonIDEValues = new JSONObject();
    ArrayList<ArrayList<ArrayList<JSONObject>>> tableValues = loadTableValues();

    Switch onOffSwitch;
    HorizontalScrollView tableHorizontalScrollView;
    Button btnEvaluateIDE;
    Button btnCalculateIDE;

    Context appContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structural_damage);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        tableHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.tableHorizontalScrollView);
        onOffSwitch = (Switch) findViewById(R.id.on_off_switch);
        btnEvaluateIDE = (Button) findViewById(R.id.evaluateIDE);
        btnCalculateIDE = (Button) findViewById(R.id.calculateIDE);

        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tableHorizontalScrollView.setVisibility(View.VISIBLE);
                } else {
                    tableHorizontalScrollView.setVisibility(View.GONE);
                }
            }

        });


        btnEvaluateIDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateIDE();
            }
        });


        btnCalculateIDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIDE();
            }
        });




    }

    public ArrayList<ArrayList<ArrayList<JSONObject>>> loadTableValues () {

        ArrayList<ArrayList<ArrayList<JSONObject>>> tableValues = new ArrayList<>();

        ArrayList<ArrayList<JSONObject>> rowArray1 = new ArrayList<>();
        ArrayList<ArrayList<JSONObject>> rowArray2 = new ArrayList<>();
        ArrayList<ArrayList<JSONObject>> rowArray3 = new ArrayList<>();
        ArrayList<ArrayList<JSONObject>> rowArray4 = new ArrayList<>();

        ArrayList<JSONObject> columnArray11 = new ArrayList<>();
        ArrayList<JSONObject> columnArray12 = new ArrayList<>();
        ArrayList<JSONObject> columnArray13 = new ArrayList<>();
        ArrayList<JSONObject> columnArray14 = new ArrayList<>();

        ArrayList<JSONObject> columnArray21 = new ArrayList<>();
        ArrayList<JSONObject> columnArray22 = new ArrayList<>();
        ArrayList<JSONObject> columnArray23 = new ArrayList<>();
        ArrayList<JSONObject> columnArray24 = new ArrayList<>();

        ArrayList<JSONObject> columnArray31 = new ArrayList<>();
        ArrayList<JSONObject> columnArray32 = new ArrayList<>();
        ArrayList<JSONObject> columnArray33 = new ArrayList<>();
        ArrayList<JSONObject> columnArray34 = new ArrayList<>();

        ArrayList<JSONObject> columnArray41 = new ArrayList<>();
        ArrayList<JSONObject> columnArray42 = new ArrayList<>();
        ArrayList<JSONObject> columnArray43 = new ArrayList<>();
        ArrayList<JSONObject> columnArray44 = new ArrayList<>();

        JSONObject json = new JSONObject();


        try {

            /*
            * -------------------- ROW 1
            */
            // ROW 1  - COLUMN 1
            /*json.put("Row",1);
            json.put("Column",1);
            json.put("FailConsequence","Leve");
            json.put("IDE","D");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':1,'FailConsequence':'Leve','IDE':'D'}");

            columnArray11.add(json);

            /*json.put("Row",1);
            json.put("Column",1);
            json.put("FailConsequence","Importante");
            json.put("IDE","D");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':1,'FailConsequence':'Importante','IDE':'D'}");

            columnArray11.add(json);
            rowArray1.add(columnArray11);
            //columnArray.clear();



            // ROW 1  - COLUMN 2
            /*json.put("Row",1);
            json.put("Column",2);
            json.put("FailConsequence","Leve");
            json.put("IDE","D");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':2,'FailConsequence':'Leve','IDE':'D'}");

            columnArray12.add(json);

            /*json.put("Row",1);
            json.put("Column",2);
            json.put("FailConsequence","Importante");
            json.put("IDE","D");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':2,'FailConsequence':'Importante','IDE':'D'}");

            columnArray12.add(json);


            rowArray1.add(columnArray12);
            //columnArray.clear();



            // ROW 1  - COLUMN 3
            /*json.put("Row",1);
            json.put("Column",3);
            json.put("FailConsequence","Leve");
            json.put("IDE","D");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':3,'FailConsequence':'Leve','IDE':'D'}");

            columnArray13.add(json);

            /*json.put("Row",1);
            json.put("Column",3);
            json.put("FailConsequence","Importante");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':3,'FailConsequence':'Importante','IDE':'M'}");

            columnArray13.add(json);



            rowArray1.add(columnArray13);
            //columnArray.clear();




            // ROW 1  - COLUMN 4
            /*json.put("Row",1);
            json.put("Column",4);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':4,'FailConsequence':'Leve','IDE':'M'}");

            columnArray14.add(json);

            /*json.put("Row",1);
            json.put("Column",4);
            json.put("FailConsequence","Importante");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':1,'Column':4,'FailConsequence':'Importante','IDE':'M'}");

            columnArray14.add(json);

            rowArray1.add(columnArray14);



            // ------------ Adds the row 1 to table array
            tableValues.add(rowArray1);



            //columnArray.clear();
            //rowArray.clear();

            /*
            * -------------------- ROW 2
            */

            // ROW 2  - COLUMN 1
            /*json.put("Row",2);
            json.put("Column",1);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':1,'FailConsequence':'Leve','IDE':'M'}");

            columnArray21.add(json);

            /*json.put("Row",2);
            json.put("Column",1);
            json.put("FailConsequence","Importante");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':1,'FailConsequence':'Importante','IDE':'M'}");

            columnArray21.add(json);

            rowArray2.add(columnArray21);
            //columnArray.clear();

            // ROW 2  - COLUMN 2
            /*json.put("Row",2);
            json.put("Column",2);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':2,'FailConsequence':'Leve','IDE':'M'}");

            columnArray22.add(json);

            /*json.put("Row",2);
            json.put("Column",2);
            json.put("FailConsequence","Importante");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':2,'FailConsequence':'Importante','IDE':'M'}");

            columnArray22.add(json);

            rowArray2.add(columnArray22);
            //columnArray.clear();


            // ROW 2  - COLUMN 3
            /*json.put("Row",2);
            json.put("Column",3);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':3,'FailConsequence':'Leve','IDE':'M'}");

            columnArray23.add(json);

            /*json.put("Row",2);
            json.put("Column",3);
            json.put("FailConsequence","Importante");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':3,'FailConsequence':'Importante','IDE':'S'}");

            columnArray23.add(json);

            rowArray2.add(columnArray23);
            //columnArray.clear();


            // ROW 2  - COLUMN 4
            /*json.put("Row",2);
            json.put("Column",4);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':4,'FailConsequence':'Leve','IDE':'M'}");

            columnArray24.add(json);

            /*json.put("Row",2);
            json.put("Column",4);
            json.put("FailConsequence","Importante");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':2,'Column':4,'FailConsequence':'Importante','IDE':'S'}");

            columnArray24.add(json);

            rowArray2.add(columnArray24);


            // ------------ Adds the row 2 to table array
            tableValues.add(rowArray2);
            //columnArray.clear();
            //rowArray.clear();




            /*
            * -------------------- ROW 3
            */

            // ROW 3  - COLUMN 1
            /*json.put("Row",3);
            json.put("Column",1);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':1,'FailConsequence':'Leve','IDE':'M'}");

            columnArray31.add(json);

            /*json.put("Row",3);
            json.put("Column",1);
            json.put("FailConsequence","Importante");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':1,'FailConsequence':'Importante','IDE':'S'}");

            columnArray31.add(json);

            rowArray3.add(columnArray31);
            //columnArray.clear();

            // ROW 3  - COLUMN 2
            /*json.put("Row",3);
            json.put("Column",2);
            json.put("FailConsequence","Leve");
            json.put("IDE","M");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':2,'FailConsequence':'Leve','IDE':'M'}");

            columnArray32.add(json);

            /*json.put("Row",3);
            json.put("Column",2);
            json.put("FailConsequence","Importante");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':2,'FailConsequence':'Importante','IDE':'S'}");

            columnArray32.add(json);

            rowArray3.add(columnArray32);
            //columnArray.clear();


            // ROW 3  - COLUMN 3
            /*json.put("Row",3);
            json.put("Column",3);
            json.put("FailConsequence","Leve");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':3,'FailConsequence':'Leve','IDE':'S'}");

            columnArray33.add(json);

            /*json.put("Row",3);
            json.put("Column",3);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':3,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray33.add(json);

            rowArray3.add(columnArray33);
            //columnArray.clear();


            // ROW 3  - COLUMN 4
            /*json.put("Row",3);
            json.put("Column",4);
            json.put("FailConsequence","Leve");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':4,'FailConsequence':'Leve','IDE':'S'}");

            columnArray34.add(json);

            /*json.put("Row",3);
            json.put("Column",4);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':3,'Column':4,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray34.add(json);

            rowArray3.add(columnArray34);


            // ------------ Adds the row 3 to table array
            tableValues.add(rowArray3);
            //columnArray.clear();
            //rowArray.clear();



            /*
            * -------------------- ROW 4
            */

            // ROW 4  - COLUMN 1
            /*json.put("Row",4);
            json.put("Column",1);
            json.put("FailConsequence","Leve");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':1,'FailConsequence':'Leve','IDE':'S'}");

            columnArray41.add(json);

            /*json.put("Row",4);
            json.put("Column",1);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':1,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray41.add(json);

            rowArray4.add(columnArray41);
            //columnArray.clear();

            // ROW 4  - COLUMN 2
            /*json.put("Row",4);
            json.put("Column",2);
            json.put("FailConsequence","Leve");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':2,'FailConsequence':'Leve','IDE':'S'}");

            columnArray42.add(json);

            /*json.put("Row",4);
            json.put("Column",2);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':2,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray42.add(json);

            rowArray4.add(columnArray42);
            //columnArray.clear();


            // ROW 4  - COLUMN 3
            /*json.put("Row",4);
            json.put("Column",3);
            json.put("FailConsequence","Leve");
            json.put("IDE","S");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':3,'FailConsequence':'Leve','IDE':'S'}");

            columnArray43.add(json);

            /*json.put("Row",4);
            json.put("Column",3);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':3,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray43.add(json);

            rowArray4.add(columnArray43);
            //columnArray.clear();


            // ROW 4  - COLUMN 4
            /*json.put("Row",4);
            json.put("Column",4);
            json.put("FailConsequence","Leve");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':4,'FailConsequence':'Leve','IDE':'MS'}");

            columnArray44.add(json);

            /*json.put("Row",4);
            json.put("Column",4);
            json.put("FailConsequence","Importante");
            json.put("IDE","MS");*/

            json = null;
            json = new JSONObject("{'Row':4,'Column':4,'FailConsequence':'Importante','IDE':'MS'}");

            columnArray44.add(json);



            rowArray4.add(columnArray44);


            // ------------ Adds the row 1 to table array
            tableValues.add(rowArray4);






            // ------------ Adds the row 4 to table array

            //columnArray.clear();
            //rowArray.clear();




            return tableValues;


        } catch (JSONException e) {
            e.printStackTrace();

        }

        return null;
    }

    public int getRowIndexValue(double pValue){

        int indexValue = 0;

        if(pValue >= 0 && pValue < 1){
            indexValue = 1;
        } else if(pValue >= 1 && pValue < 2){
            indexValue = 2;
        } else if(pValue >= 2 && pValue < 3){
            indexValue = 3;
        } else if(pValue >= 3 && pValue <= 4){
            indexValue = 4;
        }

        return indexValue;
    }

    public int getColumnIndexValue(double pValue){

        int indexValue = 0;

        if(pValue >= 0 && pValue <= 1){
            indexValue = 1;
        } else if(pValue > 1 && pValue <= 2){
            indexValue = 2;
        } else if(pValue > 2 && pValue <= 3){
            indexValue = 3;
        } else if(pValue > 3 && pValue <= 4){
            indexValue = 4;
        }

        return indexValue;
    }

    public JSONObject getTableValueJSON(ArrayList<ArrayList<ArrayList<JSONObject>>> pTableValues, double pRow, double pColumn, String pFailureConsequence){
        JSONObject json = null;
        ArrayList<ArrayList<JSONObject>> row = null;
        ArrayList<JSONObject> column = null;

        int rowIndexValue = getRowIndexValue(pRow);
        int columnIndexValue = getColumnIndexValue(pColumn);

        /*if (pRow != 0){
            row = tableValues.get(rowValue - 1);
        } else {
            row = tableValues.get(rowValue);
        }*/

        row = tableValues.get(rowIndexValue - 1);


        /*if (pColumn != 0){
            column = row.get(pColumn - 1);
        } else {
            column = row.get(pColumn);
        }*/

        column = row.get(columnIndexValue - 1);


        for (int jsonObjectIndex = 0; jsonObjectIndex < column.size(); jsonObjectIndex++){

            json = column.get(jsonObjectIndex);

            try {
                /*if(json.getString("FailConsequence").equals(pFailureConsequence) && json.getInt("Column") == pColumn && json.getInt("Row") == pRow){
                    return json;
                }*/

                if(json.getString("FailConsequence").equals(pFailureConsequence) && json.getInt("Column") == columnIndexValue && json.getInt("Row") == rowIndexValue){
                    return json;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }


        return json;
    }

    public void setIDE(View v){
        TextView tableLetterTV = (TextView) findViewById(v.getId());
        String IDE = tableLetterTV.getText().toString();

        switch (v.getId()) {
            case R.id.ids11L:
                setIDEValues(0, 1, "Leve");
                break;
            case R.id.ids11I:
                setIDEValues(0, 1, "Importante");
                break;
            case R.id.ids12L:
                setIDEValues(0, 2, "Leve");
                break;
            case R.id.ids12I:
                setIDEValues(0, 2, "Importante");
                break;
            case R.id.ids13L:
                setIDEValues(0, 3, "Leve");
                break;
            case R.id.ids13I:
                setIDEValues(0, 3, "Importante");
                break;
            case R.id.ids14L:
                setIDEValues(0, 4, "Leve");
                break;
            case R.id.ids14I:
                setIDEValues(0, 4, "Importante");
                break;


            case R.id.ids21L:
                setIDEValues(1, 1, "Leve");
                break;
            case R.id.ids21I:
                setIDEValues(1, 1, "Importante");
                break;
            case R.id.ids22L:
                setIDEValues(1, 2, "Leve");
                break;
            case R.id.ids22I:
                setIDEValues(1, 2, "Importante");
                break;
            case R.id.ids23L:
                setIDEValues(1, 3, "Leve");
                break;
            case R.id.ids23I:
                setIDEValues(1, 3, "Importante");
                break;
            case R.id.ids24L:
                setIDEValues(1, 4, "Leve");
                break;
            case R.id.ids24I:
                setIDEValues(1, 4, "Importante");
                break;



            case R.id.ids31L:
                setIDEValues(2, 1, "Leve");
                break;
            case R.id.ids31I:
                setIDEValues(2, 1, "Importante");
                break;
            case R.id.ids32L:
                setIDEValues(2, 2, "Leve");
                break;
            case R.id.ids32I:
                setIDEValues(2, 2, "Importante");
                break;
            case R.id.ids33L:
                setIDEValues(2, 3, "Leve");
                break;
            case R.id.ids33I:
                setIDEValues(2, 3, "Importante");
                break;
            case R.id.ids34L:
                setIDEValues(2, 4, "Leve");
                break;
            case R.id.ids34I:
                setIDEValues(2, 4, "Importante");
                break;




            case R.id.ids41L:
                setIDEValues(3, 1, "Leve");
                break;
            case R.id.ids41I:
                setIDEValues(3, 1, "Importante");
                break;
            case R.id.ids42L:
                setIDEValues(3, 2, "Leve");
                break;
            case R.id.ids42I:
                setIDEValues(3, 2, "Importante");
                break;
            case R.id.ids43L:
                setIDEValues(3, 3, "Leve");
                break;
            case R.id.ids43I:
                setIDEValues(3, 3, "Importante");
                break;
            case R.id.ids44L:
                setIDEValues(3, 4, "Leve");
                break;
            case R.id.ids44I:
                setIDEValues(3, 4, "Importante");
                break;

            default:
                break;
        }
    }

    //public void setIDEValues(String pIDE, int pRow, int pColumn, String pFailureConsequence) {
    public void setIDEValues(double pRow, double pColumn, String pFailureConsequence) {
        //try {
            /*jsonIDEValues.put("IDE",pIDE);
            jsonIDEValues.put("structuralIndex",pColumn);
            jsonIDEValues.put("corrosionIndex",pRow);
            jsonIDEValues.put("failureConsequence",pFailureConsequence);*/


        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/

        //jsonIDEValues = getTableValueJSON(tableValues, (int)Math.round(pRow), (int)Math.round(pColumn), pFailureConsequence);
        jsonIDEValues = getTableValueJSON(tableValues, pRow, pColumn, pFailureConsequence);

        if(jsonIDEValues != null){


            try {
                jsonIDEValues.put("structuralIndex",pColumn);
                jsonIDEValues.put("corrosionIndex",pRow);

                jsonIDEValues.put("IDE_Row",jsonIDEValues.getInt("Row"));
                jsonIDEValues.put("IDE_Column",jsonIDEValues.getInt("Column"));



                EditText IDE_EditText = (EditText) findViewById(R.id.et_structuralDamageIndex);
                EditText IC_EditText = (EditText) findViewById(R.id.et_corrosionIndex);
                EditText IE_EditText = (EditText) findViewById(R.id.et_structuralIndex);

                IDE_EditText.setText(jsonIDEValues.getString("IDE"));

                /*IC_EditText.setText(Double.toString(pRow));
                IE_EditText.setText(Double.toString(pColumn));*/

                IC_EditText.setText(Double.toString(jsonIDEValues.getDouble("corrosionIndex")));
                IE_EditText.setText(Double.toString(jsonIDEValues.getDouble("structuralIndex")));
                setRadioChecked(jsonIDEValues.getString("FailConsequence"));

                showIDEInformation(jsonIDEValues.getString("IDE"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    public void evaluateIDE(){

        if(isIDECalculated()){

            try {
                String IC_String = Methods.convertDoubleToString(jsonIDEValues.getDouble("corrosionIndex"));
                String IE_String = Methods.convertDoubleToString(jsonIDEValues.getDouble("structuralIndex"));

                double corrosionIndex = Methods.convertStringToDecimal(IC_String);
                double structuralIndex = Methods.convertStringToDecimal(IE_String);
                String IDE = jsonIDEValues.getString("IDE");
                String failureConsequence = jsonIDEValues.getString("FailConsequence");
                int IDE_Row = jsonIDEValues.getInt("IDE_Row");
                int IDE_Column = jsonIDEValues.getInt("IDE_Column");
                int evaluationId = 0;

                Toast.makeText(appContext, "Guardar IDE en DB", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }



        } else {
            Toast.makeText(appContext, Constants.IDE_EVALUATION_ERROR, Toast.LENGTH_LONG).show();
        }

    }

    public boolean isIDECalculated(){
        TextView IDE_TextView = (TextView) findViewById(R.id.et_structuralDamageIndex);
        return !IDE_TextView.getText().toString().equals("");
    }

    public void calculateIDE(){
        String failureConsequence = "";
        EditText IC_EditText = (EditText) findViewById(R.id.et_corrosionIndex);
        EditText IE_EditText = (EditText) findViewById(R.id.et_structuralIndex);

        String IC_String = IC_EditText.getText().toString();
        String IE_String = IE_EditText.getText().toString();

        if (validateIDERequiredValues(IC_String, IE_String)) {

            double corrosionIndex = Methods.convertStringToDecimal(IC_String);
            double structuralIndex = Methods.convertStringToDecimal(IE_String);

            if ((corrosionIndex >= 0 &&  corrosionIndex <= 4) && (structuralIndex >= 0 && structuralIndex <= 4)){

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupFailureConsequence);

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);

                failureConsequence = selectedRadioButton.getText().toString();

                setIDEValues(corrosionIndex,structuralIndex,failureConsequence);
            } else {
                Toast.makeText(appContext, Constants.IDE_NUMBERS_ERROR, Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(appContext, Constants.IDE_ERROR, Toast.LENGTH_LONG).show();
        }


    }

    public boolean validateIDERequiredValues(String pCorrosionEditTextValue, String pStructuralEditTextValue) {
        if ((!pCorrosionEditTextValue.equals("")) && (!pStructuralEditTextValue.equals(""))) {
            return true;
        }

        return false;
    }

    public void setRadioChecked(String pRadioText){

        RadioButton radio = null;

        if (pRadioText.equals("Leve")){
            radio = (RadioButton) findViewById(R.id.slightFailureConsequence);
        } else if (pRadioText.equals("Importante")){
            radio = (RadioButton) findViewById(R.id.importantFailureConsequence);
        }

        radio.setChecked(true);
    }

    public String getInterventionUrgency(String pIDE){

        String urgency = "";

        switch (pIDE){
            case "D":
                urgency = " > 10 años";
                break;
            case "M":
                urgency = " 5 - 10 años ";
                break;
            case "S":
                urgency = " 2 - 5 años";
                break;
            case "MS":
                urgency = " 0 - 2 años";
                break;
        }

        return urgency;
    }

    public String getAcronymMeaning(String pIDE){

        String meaning = "";

        switch (pIDE){
            case "D":
                meaning = "Despreciable";
                break;
            case "M":
                meaning = "Medio";
                break;
            case "S":
                meaning = "Severo";
                break;
            case "MS":
                meaning = "Muy Severo";
                break;
        }

        return meaning;
    }

    public void showIDEInformation(String pIDE){

        LinearLayout informationLayout = (LinearLayout) findViewById(R.id.IDE_information);

        TextView acrMeaningText = (TextView) findViewById(R.id.IDE_AcrMeaningText);
        TextView urgencyText = (TextView) findViewById(R.id.IDE_AcrUrgencyText);

        acrMeaningText.setText(pIDE + " = " + getAcronymMeaning(pIDE));
        urgencyText.setText(Constants.INTERVENTION_URGENCY + " " +getInterventionUrgency(pIDE));

        informationLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View view) {

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

        } else if (id == R.id.nav_logout) {
            Methods.changeScreen(this,Login.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
