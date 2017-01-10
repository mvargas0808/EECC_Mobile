package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 08/01/2017.
 */

public class StructuralIndex extends Activity implements View.OnClickListener {

    private Button buttonManual, buttonSimplified;
    private RadioGroup radioType;
    private ImageView iaa1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        JSONObject json = null;
        String value = null;
        try {
            json = new JSONObject(intent.getStringExtra("data"));
            value =  json.get("activityType").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON:", value);

        switch (value) {
            case "EvaluationType":
                setContentView(R.layout.structural_index_type);
                buttonManual = (Button) findViewById(R.id.buttonManual);
                buttonSimplified = (Button) findViewById(R.id.buttonSimplified);
                radioType = (RadioGroup)findViewById(R.id.radioType);
                buttonManual.setOnClickListener(this);
                buttonSimplified.setOnClickListener(this);
                //radioType.setOnCheckedChangeListener(this);
                break;
            case "S_Flexion":
                setContentView(R.layout.si_flexion_simplified);
                break;
            case "S_Flexocompresion":
                setContentView(R.layout.si_flexocompresion_simplified);
                break;
            case "M_Flexion":
                setContentView(R.layout.si_flexion_manual);
                iaa1 = (ImageView) findViewById(R.id.iaa1);
                //iaa1.setOnClickListener(this);
                break;
            case "M_Flexocompresion":
                setContentView(R.layout.si_flexocompresion_manual);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        radioType = (RadioGroup)findViewById(R.id.radioType);
        int checkedRadio = radioType.getCheckedRadioButtonId();
        switch (v.getId()) {
            case R.id.buttonSimplified:
                switch (checkedRadio){
                    case R.id.radioFlexion:
                        startSubEvaluation(StructuralIndex.class, "S_Flexion");
                        break;
                    case R.id.radioFlexocompresion:
                        startSubEvaluation(StructuralIndex.class, "S_Flexocompresion");
                        break;
                }
                break;
            case R.id.buttonManual:
                switch (checkedRadio){
                    case R.id.radioFlexion:
                        startSubEvaluation(StructuralIndex.class, "M_Flexion");
                        break;
                    case R.id.radioFlexocompresion:
                        startSubEvaluation(StructuralIndex.class, "M_Flexocompresion");
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void changeScreen(Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(StructuralIndex.this, pClass);
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

    public void showToast(View v){
        switch (v.getId()) {

            case R.id.iaa1:
                Toast.makeText(StructuralIndex.this,"soy "+v.getId(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
