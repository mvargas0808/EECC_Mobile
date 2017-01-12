package com.itcr.eecc.eecc;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import DataBase.DataBaseManager;

public class Report extends AppCompatActivity {

    DataBaseManager manager;
    private String evaluationId;
    private String projectId;
    TextView tvIndicator1, tvIndicator2, tvIndicator3, tvIndicator4, tvIndicator5, tvIndicator6;
    TextView tv_IDC, tv_AA, tv_IC, tvStructuralIndex, tvIEDescription, tvIALDescription, tvIATDescription, tvIEvalue, tvIDEDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class,
        //"json", new JSONObject("{'ProjectId':"+projectIdFinal+",'evaluationId':"+evaluationId+"}"));
        Intent intent = getIntent();
        JSONObject json = null;
        try {
            json = new JSONObject(intent.getStringExtra("json"));
            evaluationId =  json.get("evaluationId").toString();
            projectId = json.get("ProjectId").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        manager = new DataBaseManager(this);
        tvIndicator1 = (TextView) findViewById(R.id.tvIndicator1);
        tvIndicator2 = (TextView) findViewById(R.id.tvIndicator2);
        tvIndicator3 = (TextView) findViewById(R.id.tvIndicator3);
        tvIndicator4 = (TextView) findViewById(R.id.tvIndicator4);
        tvIndicator5 = (TextView) findViewById(R.id.tvIndicator5);
        tvIndicator6 = (TextView) findViewById(R.id.tvIndicator6);
        tv_IDC = (TextView) findViewById(R.id.tv_IDC);
        tv_AA = (TextView) findViewById(R.id.tv_AA);
        tv_IC = (TextView) findViewById(R.id.tv_IC);
        tvStructuralIndex = (TextView) findViewById(R.id.tvStructuralIndex);
        tvIEDescription = (TextView) findViewById(R.id.tvIEDescription);
        tvIALDescription = (TextView) findViewById(R.id.tvIALDescription);
        tvIATDescription = (TextView) findViewById(R.id.tvIATDescription);
        tvIEvalue = (TextView) findViewById(R.id.tvIEvalue);
        tvIDEDescription = (TextView) findViewById(R.id.tvIDEDescription);
        showReport();
    }

    public void showReport(){
        manager.openConnection();
        Cursor cursor = manager.getReportInformation(evaluationId);
        Cursor cursorIndicator = manager.getReportInformation(evaluationId);
        if (cursor != null){
            cursor.moveToFirst();
            if(!cursor.getString(cursor.getColumnIndex("ISC")).equals("-1")){
                if(cursorIndicator != null){
                    cursorIndicator.moveToNext();
                    tvIndicator1.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                    cursorIndicator.moveToNext();
                    tvIndicator2.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                    cursorIndicator.moveToNext();
                    tvIndicator3.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                    cursorIndicator.moveToNext();
                    tvIndicator4.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                    cursorIndicator.moveToNext();
                    tvIndicator5.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                    cursorIndicator.moveToNext();
                    tvIndicator6.setText(cursor.getString(cursor.getColumnIndex("IndicatorDescription")));
                }
                tv_IDC.setText("IDC = "+cursor.getString(cursor.getColumnIndex("IDC")));
                tv_AA.setText(cursor.getString(cursor.getColumnIndex("IaaDescription")));
                tv_IC.setText("IC = "+cursor.getString(cursor.getColumnIndex("ISC")));
            } if(!cursor.getString(cursor.getColumnIndex("IE")).equals("-1")){
                if (cursor.getString(cursor.getColumnIndex("EvaluationType")).equals("0")){
                    tvStructuralIndex.setText("ÍNDICE ESTRUCTURAL - Elemento en "+cursor.getString(cursor.getColumnIndex("EsElementType")));
                    tvIEDescription.setText(cursor.getString(cursor.getColumnIndex("EsDescription")));
                } else if (cursor.getString(cursor.getColumnIndex("EvaluationType")).equals("1")){
                    tvStructuralIndex.setText("ÍNDICE ESTRUCTURAL - Elemento en "+cursor.getString(cursor.getColumnIndex("IalElementType")));
                    tvIALDescription.setText(cursor.getString(cursor.getColumnIndex("IalDescription")));
                    tvIATDescription.setText(cursor.getString(cursor.getColumnIndex("IatDescription")));
                    tvIEvalue.setText(cursor.getString(cursor.getColumnIndex("IE")));
                }
            } if(!cursor.getString(cursor.getColumnIndex("IDE")).equals("-1")){
                tvIDEDescription.setText(cursor.getString(cursor.getColumnIndex("IdeDescription")));
            }
        }
        manager.closeConnection();
    }
}
