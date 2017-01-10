package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Michael on 09/01/2017.
 */

public class Flexion extends Activity implements View.OnClickListener{
    private ArrayList<Integer> longitudinalValues;
    private ArrayList<Integer> transverseValues;
    private ArrayList<Integer> simplifiedValues;
    private float structuralIndex;
    private Button buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        JSONObject json;
        String activityType = null;
        try {
            json = new JSONObject(intent.getStringExtra("data"));
            activityType =  json.get("activityType").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON:", activityType);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLongitudinal:
                if(!longitudinalValues.isEmpty()){
                    nextSubEvaluation(Flexion.class, "M_Flexion_Second");
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Armado Longitudinal",Toast.LENGTH_LONG).show();
                }
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
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 1);
                break;
            case R.id.ial12:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 2);
                break;
            case R.id.ial13:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 3);
                break;
            case R.id.ial14:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 4);
                break;
            case R.id.ial21:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 2, 1);
                break;
            case R.id.ial22:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 2, 2);
                break;
            case R.id.ial23:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 2, 3);
                break;
            default:
                break;
        }
        Log.d("***********************", "setLongitudinal: "+ longitudinalValues.toString());
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
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(1, 1, 1);
                break;
            case R.id.iat12:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(2, 1, 2);
                break;
            case R.id.iat13:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(2, 1, 3);
                break;
            case R.id.iat14:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(3, 1, 4);
                break;
            case R.id.iat21:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(2, 2, 1);
                break;
            case R.id.iat22:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(3, 2, 2);
                break;
            case R.id.iat23:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(3, 2, 3);
                break;
            case R.id.iat24:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(4, 2, 4);
                break;
            case R.id.iat31:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(3, 3, 1);
                break;
            case R.id.iat32:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(4, 3, 2);
                break;
            case R.id.iat33:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setTransverseValues(4, 3, 3);
                break;
            case R.id.iat34:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(0, 1, 1);
                break;
            case R.id.si12:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(0, 1, 2);
                break;
            case R.id.si13:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(1, 1, 3);
                break;
            case R.id.si14:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(2, 1, 4);
                break;
            case R.id.si21:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(2, 2, 1);
                break;
            case R.id.si22:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(3, 2, 2);
                break;
            case R.id.si23:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(3, 2, 3);
                break;
            case R.id.si24:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(4, 2, 4);
                break;
            case R.id.si31:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(3, 3, 1);
                break;
            case R.id.si32:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(4, 3, 2);
                break;
            case R.id.si33:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setSimplifiedValues(4, 3, 3);
                break;
            case R.id.si34:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
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
                    longitudinalValues = getIntent().getIntegerArrayListExtra("longitudinalValues"); /*Cambiar a float*/
                    structuralIndex = (getLongitudinalValues().get(0) + getTransverseValues().get(0))/2;

                    Toast.makeText(Flexion.this,"El Índice Estructural es: "+structuralIndex,Toast.LENGTH_SHORT).show();
                    Intent finishCalculation = new Intent(Flexion.this, EvaluationMenu.class);
                    startActivity(finishCalculation);
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Armado Transversal",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonSimplifiedCalc:
                if(!simplifiedValues.isEmpty()){
                    structuralIndex = simplifiedValues.get(0);

                    Toast.makeText(Flexion.this,"El Índice Estructural es: "+structuralIndex,Toast.LENGTH_SHORT).show();
                    Intent finishCalculation = new Intent(Flexion.this, EvaluationMenu.class);
                    startActivity(finishCalculation);
                } else {
                    Toast.makeText(Flexion.this,"Debe seleccionar el Índice de Evaluacion Simplificada",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public ArrayList<Integer> getLongitudinalValues() {
        return longitudinalValues;
    }

    public ArrayList<Integer> getTransverseValues() {
        return transverseValues;
    }
}
