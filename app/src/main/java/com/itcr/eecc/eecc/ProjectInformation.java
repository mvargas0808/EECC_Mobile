package com.itcr.eecc.eecc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Methods;
import DataBase.DataBaseManager;

public class ProjectInformation extends AppCompatActivity {

    TextView tv_projectname, tv_responsable, tv_structuretype, tv_location, tv_component_description, tv_generaluse, tv_creationstructruedate, tv_creationprojectdate;
    DataBaseManager manager;
    Button btn_create_evaluation, btn_edit_project;
    String projectId;
    Context appContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_information);
        manager = new DataBaseManager(this);
        tv_projectname = (TextView) findViewById(R.id.tv_projectname);
        tv_responsable = (TextView) findViewById(R.id.tv_responsable);
        tv_structuretype = (TextView) findViewById(R.id.tv_structuretype);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_component_description = (TextView) findViewById(R.id.tv_component_description);
        tv_generaluse = (TextView) findViewById(R.id.tv_generaluse);
        tv_creationstructruedate = (TextView) findViewById(R.id.tv_creationstructruedate);
        tv_creationprojectdate = (TextView) findViewById(R.id.tv_creationprojectdate);
        btn_create_evaluation = (Button) findViewById(R.id.btn_create_evaluation);
        btn_edit_project = (Button) findViewById(R.id.btn_edit_project);
        JSONObject data = Methods.getJsonParamFromPreviousScreen(getIntent(), "Data");
        try {
            projectId = data.getString("ProjectId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadInformationProject();
    }

    public void loadInformationProject(){
        manager.openConnection();
        Cursor cursor = manager.getProjectInformation(projectId);
        if (cursor != null){
            cursor.moveToFirst();
            String structureCreationDate = cursor.getString(cursor.getColumnIndex("StructureCreationDate"));
            tv_projectname.setText(cursor.getString(cursor.getColumnIndex("ProjectName")));
            tv_responsable.setText(cursor.getString(cursor.getColumnIndex("UserName")));
            tv_structuretype.setText(cursor.getString(cursor.getColumnIndex("StructureName")));
            tv_location.setText(cursor.getString(cursor.getColumnIndex("ProvinceName"))+", "+
                    cursor.getString(cursor.getColumnIndex("CantonName"))+", "+
                    cursor.getString(cursor.getColumnIndex("DistrictName")));
            tv_component_description.setText(cursor.getString(cursor.getColumnIndex("ComponentDescription")));
            tv_generaluse.setText(cursor.getString(cursor.getColumnIndex("StructureUseDescription")));
            tv_creationprojectdate.setText(cursor.getString(cursor.getColumnIndex("CreationDate")));
            tv_creationstructruedate.setText(structureCreationDate.equals("0000-00-00") ? "---" : structureCreationDate);
        }
        manager.closeConnection();
    }

    public void editProject(View v){
        try {
            Methods.changeScreenAndSendJson(appContext, EditProject.class,
                    "Data", new JSONObject("{'ProjectId':"+projectId+"}"));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void createEvaluation(View v){
        manager.openConnection();
        String evaluationId = manager.getEvaluationIdbyProject(projectId);
        if(evaluationId.equals("-1")){
            alertMessageCreateEvaluation(projectId);
        } else {
            alertMessageGoEvaluation(evaluationId);
        }
        manager.closeConnection();
    }



    public void alertMessageCreateEvaluation(String projectId){
        final String projectIdFinal = projectId;
        Toast.makeText(getApplicationContext(), "fINAL " + projectIdFinal, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Desea crear una nueva evaluación en el sistema?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Crear",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        manager.openConnection();
                        String evaluationId = manager.createEvaluationProject(projectIdFinal);
                        if(evaluationId.equals("-1")){
                            Toast.makeText(getApplicationContext(), "ERROR: Evaluación no creada ", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class,
                                        "json", new JSONObject("{'ProjectId':"+projectIdFinal+",'evaluationId':"+evaluationId+"}"));
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        manager.closeConnection();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void alertMessageGoEvaluation(String pEvaluationId){
        final String evaluationId = pEvaluationId;
        final String projectIdFinal = projectId;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("INFORMACIÓN IMPORTANTE\nLe recordamos que usted tiene una evaluación en proceso");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ir a la evaluación",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class,
                                    "json", new JSONObject("{'ProjectId':"+projectIdFinal+",'evaluationId':"+evaluationId+"}"));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
