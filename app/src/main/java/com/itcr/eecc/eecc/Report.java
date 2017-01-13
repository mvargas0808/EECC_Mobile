package com.itcr.eecc.eecc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Methods;
import DataBase.DataBaseManager;

public class Report extends AppCompatActivity {

    DataBaseManager manager;
    private String evaluationId;
    private String projectId;
    Context appContext = this;
    TextView tvIndicator1, tvIndicator2, tvIndicator3, tvIndicator4, tvIndicator5, tvIndicator6;
    TextView tv_IDC, tv_AA, tv_IC, tvStructuralIndex, tvIEDescription, tvIALDescription, tvIATDescription, tvIEvalue, tvIDEDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

            //Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class,
            //"json", new JSONObject("{'ProjectId':"+projectId+",'evaluationId':"+evaluationId+"}"));

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
        System.out.println("---------------------eeeee-------------"+evaluationId);
        Cursor cursor = manager.getReportInformation(evaluationId);
        Cursor cursorIndicator = manager.getReportInformation(evaluationId);
        if (cursor != null){
            cursor.moveToFirst();
            if(!cursor.getString(cursor.getColumnIndex("ISC")).equals("-1")){
                if(cursorIndicator != null){
                    cursorIndicator.moveToFirst();
                    tvIndicator1.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator1.setVisibility(View.VISIBLE);
                    cursorIndicator.moveToNext();
                    tvIndicator2.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator2.setVisibility(View.VISIBLE);
                    cursorIndicator.moveToNext();
                    tvIndicator3.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator3.setVisibility(View.VISIBLE);
                    cursorIndicator.moveToNext();
                    tvIndicator4.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator4.setVisibility(View.VISIBLE);
                    cursorIndicator.moveToNext();
                    tvIndicator5.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator5.setVisibility(View.VISIBLE);
                    cursorIndicator.moveToNext();
                    tvIndicator6.setText(cursorIndicator.getString(cursor.getColumnIndex("IndicatorDescription")));
                    tvIndicator6.setVisibility(View.VISIBLE);
                }
                tv_IDC.setText("IDC = "+cursor.getString(cursor.getColumnIndex("IDC")));
                tv_IDC.setVisibility(View.VISIBLE);
                tv_AA.setText(cursor.getString(cursor.getColumnIndex("IaaDescription")));
                tv_AA.setVisibility(View.VISIBLE);
                tv_IC.setText("IC = "+cursor.getString(cursor.getColumnIndex("ISC")));
                tv_IC.setVisibility(View.VISIBLE);
            } if(!cursor.getString(cursor.getColumnIndex("IE")).equals("-1")){
                if (cursor.getString(cursor.getColumnIndex("EvaluationType")).equals("0")){
                    tvStructuralIndex.setText("ÍNDICE ESTRUCTURAL - Elemento en "+cursor.getString(cursor.getColumnIndex("EsElementType")));
                    tvStructuralIndex.setVisibility(View.VISIBLE);
                    tvIEDescription.setText(cursor.getString(cursor.getColumnIndex("EsDescription")));
                    tvIEDescription.setVisibility(View.VISIBLE);
                } else if (cursor.getString(cursor.getColumnIndex("EvaluationType")).equals("1")){
                    tvStructuralIndex.setText("ÍNDICE ESTRUCTURAL - Elemento en "+cursor.getString(cursor.getColumnIndex("IalElementType")));
                    tvStructuralIndex.setVisibility(View.VISIBLE);
                    tvIALDescription.setText(cursor.getString(cursor.getColumnIndex("IalDescription")));
                    tvIALDescription.setVisibility(View.VISIBLE);
                    tvIATDescription.setText(cursor.getString(cursor.getColumnIndex("IatDescription")));
                    tvIATDescription.setVisibility(View.VISIBLE);
                    tvIEvalue.setText("IE = "+cursor.getString(cursor.getColumnIndex("IE")));
                    tvIEvalue.setVisibility(View.VISIBLE);
                }
            } if(!cursor.getString(cursor.getColumnIndex("IDE")).equals("-1")){
                tvIDEDescription.setText(cursor.getString(cursor.getColumnIndex("IdeDescription")));
                tvIDEDescription.setVisibility(View.VISIBLE);
            }
        }
        manager.closeConnection();
    }
}
