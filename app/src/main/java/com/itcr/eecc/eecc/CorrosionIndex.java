package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 08/01/2017.
 */

public class CorrosionIndex extends Activity implements View.OnClickListener  {

    private Button prueba;
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
            case "CII":
                setContentView(R.layout.corrosion_index_indicators);
                prueba = (Button) findViewById(R.id.prueba);
                prueba.setOnClickListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prueba:
                Intent si = new Intent(CorrosionIndex.this, EvaluationMenu.class);
                startActivity(si);
                break;
            default:
                break;
        }
    }
}
