package com.itcr.eecc.eecc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Common.Methods;
import DataBase.DataBaseManager;


public class Flexion extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<Integer> longitudinalValues;
    private ArrayList<Integer> transverseValues;
    private ArrayList<Integer> simplifiedValues;
    private int structuralIndex;
    private Button buttonNext, buttonCancel;
    private String evaluationId;
    DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        JSONObject json;
        String activityType = null;
        try {
            json = new JSONObject(intent.getStringExtra("data"));
            activityType =  json.get("activityType").toString();
            evaluationId =  json.get("evaluationId").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // switch for choose wich layout should start
        switch (activityType) {
            case "S_Flexion":
                setContentView(R.layout.si_flexion_simplified);
                simplifiedValues = new ArrayList<>();
                break;
            case "M_Flexion":
                setContentView(R.layout.si_flexion_manual);
                longitudinalValues = new ArrayList<>();
                buttonNext = (Button) findViewById(R.id.buttonLongitudinal);
                buttonNext.setOnClickListener(this);
                break;
            case "M_Flexion_Second":
                setContentView(R.layout.si_flexion_manual_second);
                transverseValues = new ArrayList<>();
                break;
            default:
                break;
        }

        manager = new DataBaseManager(this);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLongitudinal:
                if(!longitudinalValues.isEmpty()){
                    nextSubEvaluation(Flexion.class, "M_Flexion_Second");
                    finish();
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Armado Longitudinal",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonCancel:
                finishCalculation();
                finish();
                break;
            default:
                break;
        }
    }


    public void changeScreen(Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(Flexion.this, pClass);
        intent.putExtra(pMessageKey, pJsonMessageValue.toString());
        intent.putExtra("longitudinalValues", getLongitudinalValues());
        startActivity(intent);
    }

    // next to the corresponding SubEvaluation
    public void nextSubEvaluation(Class pClass, String pActivityType){
        JSONObject obj = new JSONObject();
        try {
            obj.put("activityType",pActivityType);
            obj.put("evaluationId", evaluationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changeScreen(pClass, "data", obj);
    }

    // set longitudinal values as follow: longitudinalValues[indexValue, row, column]
    public void setLongitudinal(View v){
        TextView texto = (TextView) findViewById(v.getId());
        int indexValue = Integer.parseInt(texto.getText().toString());

        switch (v.getId()) {
            case R.id.ial11:
                setLongitudinalValues(indexValue, 1, 1);
                break;
            case R.id.ial12:
                setLongitudinalValues(indexValue, 1, 2);
                break;
            case R.id.ial13:
                setLongitudinalValues(indexValue, 1, 3);
                break;
            case R.id.ial14:
                setLongitudinalValues(indexValue, 1, 4);
                break;
            case R.id.ial21:
                setLongitudinalValues(indexValue, 2, 1);
                break;
            case R.id.ial22:
                setLongitudinalValues(indexValue, 2, 2);
                break;
            case R.id.ial23:
                setLongitudinalValues(indexValue, 2, 3);
                break;
            default:
                break;
        }
    }

    public void setLongitudinalValues(int pIndex, int pRow, int pColumn) {
        this.longitudinalValues.clear();
        this.longitudinalValues.add(pIndex);
        this.longitudinalValues.add(pRow);
        this.longitudinalValues.add(pColumn);
        TextView longitudinalIndex = (TextView) findViewById(R.id.longitudinalIndex);
        longitudinalIndex.setText(Integer.toString(pIndex));
    }

    // set transverse values as follow: transverseValues[indexValue, row, column]
    public void setTransverse(View v){
        TextView texto = (TextView) findViewById(v.getId());

        switch (v.getId()) {
            case R.id.iat11:
                setTransverseValues(1, 1, 1);
                break;
            case R.id.iat12:
                setTransverseValues(2, 1, 2);
                break;
            case R.id.iat13:
                setTransverseValues(2, 1, 3);
                break;
            case R.id.iat14:
                setTransverseValues(3, 1, 4);
                break;
            case R.id.iat21:
                setTransverseValues(2, 2, 1);
                break;
            case R.id.iat22:
                setTransverseValues(3, 2, 2);
                break;
            case R.id.iat23:
                setTransverseValues(3, 2, 3);
                break;
            case R.id.iat24:
                setTransverseValues(4, 2, 4);
                break;
            case R.id.iat31:
                setTransverseValues(3, 3, 1);
                break;
            case R.id.iat32:
                setTransverseValues(4, 3, 2);
                break;
            case R.id.iat33:
                setTransverseValues(4, 3, 3);
                break;
            case R.id.iat34:
                setTransverseValues(4, 3, 4);
                break;
            default:
                break;
        }
    }

    public void setTransverseValues(int pIndex, int pRow, int pColumn) {
        this.transverseValues.clear();
        this.transverseValues.add(pIndex);
        this.transverseValues.add(pRow);
        this.transverseValues.add(pColumn);
        TextView transverseIndex = (TextView) findViewById(R.id.transverseIndex);
        transverseIndex.setText(Integer.toString(pIndex));
    }

    // set simplified values as follow:: simplifiedValues[indexValue, row, column]
    public void setSimplified(View v){
        TextView texto = (TextView) findViewById(v.getId());

        switch (v.getId()) {
            case R.id.si11:
                setSimplifiedValues(0, 1, 1);
                break;
            case R.id.si12:
                setSimplifiedValues(0, 1, 2);
                break;
            case R.id.si13:
                setSimplifiedValues(1, 1, 3);
                break;
            case R.id.si14:
                setSimplifiedValues(2, 1, 4);
                break;
            case R.id.si21:
                setSimplifiedValues(2, 2, 1);
                break;
            case R.id.si22:
                setSimplifiedValues(3, 2, 2);
                break;
            case R.id.si23:
                setSimplifiedValues(3, 2, 3);
                break;
            case R.id.si24:
                setSimplifiedValues(4, 2, 4);
                break;
            case R.id.si31:
                setSimplifiedValues(3, 3, 1);
                break;
            case R.id.si32:
                setSimplifiedValues(4, 3, 2);
                break;
            case R.id.si33:
                setSimplifiedValues(4, 3, 3);
                break;
            case R.id.si34:
                setSimplifiedValues(4, 3, 4);
                break;
            default:
                break;
        }
    }

    public void setSimplifiedValues(int pIndex, int pRow, int pColumn) {
        this.simplifiedValues.clear();
        this.simplifiedValues.add(pIndex);
        this.simplifiedValues.add(pRow);
        this.simplifiedValues.add(pColumn);
        TextView simplifiedIndex = (TextView) findViewById(R.id.simplifiedIndex);
        simplifiedIndex.setText(Integer.toString(pIndex));
    }

    public void setStructuralIndex(View v){
        switch (v.getId()) {
            case R.id.buttonTransverse:
                if(!transverseValues.isEmpty()){
                    longitudinalValues = getIntent().getIntegerArrayListExtra("longitudinalValues");
                    structuralIndex = Math.max(getLongitudinalValues().get(0), getTransverseValues().get(0));

                    Toast.makeText(Flexion.this,"El Índice Estructural es: "+structuralIndex,Toast.LENGTH_LONG).show();
                    saveValidations();
                    finish();
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Armado Transversal",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonSimplifiedCalc:
                if(!simplifiedValues.isEmpty()){
                    structuralIndex = simplifiedValues.get(0);

                    Toast.makeText(Flexion.this,"El Índice Estructural es: "+structuralIndex,Toast.LENGTH_LONG).show();
                    saveValidationsSimplified();
                    finish();
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Evaluación Simplificada",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    // save the structural index by manual into the database
    public void saveStructuralIndex(){
        manager.openConnection();
        long value = manager.saveStructuralIndex(
                structuralIndex,
                longitudinalValues.get(0), longitudinalValues.get(1), longitudinalValues.get(2),
                transverseValues.get(0), transverseValues.get(1), transverseValues.get(2),
                "Flexión", Long.parseLong(evaluationId));
        if(value == -1){
            Toast.makeText(getApplicationContext(),"Ha ocurrido un error", Toast.LENGTH_LONG).show();
        } else {
            finishCalculation();
        }
        manager.closeConnection();
    }

    // save the structural index by simplified into the database
    public void saveStructuralIndexSimplified(){
        manager.openConnection();
        long value = manager.saveStructuralIndexSimplified(
                structuralIndex, simplifiedValues.get(0), simplifiedValues.get(1), simplifiedValues.get(2),
                "Flexión", Long.parseLong(evaluationId));
        if(value == -1){
            Toast.makeText(getApplicationContext(),"Ha ocurrido un error", Toast.LENGTH_LONG).show();
        } else {
            finishCalculation();
        }
        manager.closeConnection();
    }

    // validates if there is a previous structural index
    public void saveValidations(){
        manager.openConnection();
        long value = manager.validatePreviousStructuralIndex(Long.parseLong(evaluationId));
        if(value == 0){
            saveStructuralIndex();
        } else {
            value = manager.disablePreviousStructuralIndex(Long.parseLong(evaluationId));
            if(value == 1){
                saveStructuralIndex();
            } else {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        }
        manager.closeConnection();
    }

    // validates if there is a previous structural index
    public void saveValidationsSimplified(){
        manager.openConnection();
        long value = manager.validatePreviousStructuralIndexSimplified(Long.parseLong(evaluationId));
        if(value == 0){
            saveStructuralIndexSimplified();
        } else {
            value = manager.disablePreviousStructuralIndexSimplified(Long.parseLong(evaluationId));
            if(value == 1){
                saveStructuralIndexSimplified();
            } else {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        }
        manager.closeConnection();
    }


    // finish the calculation and send the evaluationId to the others activities
    public void finishCalculation(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("evaluationId", evaluationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent returnToMenu = new Intent(Flexion.this, EvaluationMenu.class);
        returnToMenu.putExtra("json", obj.toString());
        startActivity(returnToMenu);
    }

    public ArrayList<Integer> getLongitudinalValues() {
        return longitudinalValues;
    }

    public ArrayList<Integer> getTransverseValues() {
        return transverseValues;
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
