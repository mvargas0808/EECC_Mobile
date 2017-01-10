package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 08/01/2017.
 */

public class EvaluationMenu extends Activity implements View.OnClickListener {

    private Button buttonCDI, buttonSI, buttonSDI, buttonReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_menu);

        // setup buttons
        buttonCDI = (Button) findViewById(R.id.buttonCDI);
        buttonSI = (Button) findViewById(R.id.buttonSI);
        buttonSDI = (Button) findViewById(R.id.buttonSDI);
        buttonReport = (Button) findViewById(R.id.buttonReport);

        // register listeners
        buttonCDI.setOnClickListener(this);
        buttonSI.setOnClickListener(this);
        buttonSDI.setOnClickListener(this);
        buttonReport.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCDI:
                startSubEvaluation(CorrosionIndex.class, "CII");
                break;
            case R.id.buttonSI:
                startSubEvaluation(StructuralIndex.class, "EvaluationType");
                break;
            case R.id.buttonSDI:
                Intent sdi = new Intent(EvaluationMenu.this, Login.class);
                startActivity(sdi);
                break;
            case R.id.buttonReport:
                Intent report = new Intent(EvaluationMenu.this, Login.class);
                startActivity(report);
                break;
            default:
                break;
        }
    }

    public void changeScreen(Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(EvaluationMenu.this, pClass);
        intent.putExtra(pMessageKey, pJsonMessageValue.toString());
        startActivity(intent);
    }

    // start the corresponding SubEvaluation
    public void startSubEvaluation(Class pClass, String pActivityType){
        JSONObject obj = new JSONObject();
        try {
            obj.put("activityType",pActivityType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changeScreen(pClass, "data", obj);
    }




}
