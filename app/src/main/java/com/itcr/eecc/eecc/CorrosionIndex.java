package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Constants;
import Common.Methods;


public class CorrosionIndex extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    private Button prueba;

    /*Indicators Table Values*/
    JSONObject carbonationDepth;
    JSONObject chlorideLevel;
    JSONObject cracking;
    JSONObject resistivity;
    JSONObject sectionLoss;
    JSONObject intensity;

    Context appContext;


    Button btn_calculateIDC;

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


        carbonationDepth = null;
        chlorideLevel = null;
        cracking = null;
        resistivity = null;
        sectionLoss = null;
        intensity = null;


        btn_calculateIDC = (Button) findViewById(R.id.btn_calculateIDC);

        btn_calculateIDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIDC();
            }
        });


    }



    // Validates if IDC has been calculated
    public boolean isIDCCalculated(){return false;}

    // Validates if IAA has been calculated
    public boolean isIAACalculated(){return false;}

    // Calculates de Corrosion Damage Index (IDC)
    public void calculateIDC(){

        if(validIndicators()){

            try {

                String resultString = "";

                //Sumatoria of indicators / number of indicators

                double sum = (carbonationDepth.getDouble("value") + chlorideLevel.getDouble("value") + cracking.getDouble("value") + resistivity.getDouble("value") + sectionLoss.getDouble("value") + intensity.getDouble("value"))/6;

                resultString = Double.toString(sum);

                double idcResult = Methods.convertStringToDecimal(resultString);

                EditText IDC_EditText = (EditText) findViewById(R.id.et_IDC);

                IDC_EditText.setText(Double.toString(idcResult));
                //IDC_EditText.setText(resultString);

                Toast.makeText(appContext, resultString, Toast.LENGTH_LONG).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(appContext, Constants.INDICATORS_ERROR, Toast.LENGTH_LONG).show();
        }
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
    public void calculateIAA(){}

    // Calculates de Corrosion Index (IC)
    public void calculateIC(){}

    // Evaluates de Corrosion Index (IC) and send it to the database
    public void evaluateIC(){}




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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow2Value(int pValue, int pRow, int pColumn){

        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            chlorideLevel = null;
            chlorideLevel = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow3Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            cracking = null;
            cracking = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow4Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            resistivity = null;
            resistivity = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow5Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            sectionLoss = null;
            sectionLoss = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIDCRow6Value(int pValue, int pRow, int pColumn){
        try {
            String jsonString = "{'value':"+pValue+", 'row':"+pRow+", 'column':"+pColumn+"}";

            intensity = null;
            intensity = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        } else if (id == R.id.nav_logout) {
            Methods.changeScreen(this,Login.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
