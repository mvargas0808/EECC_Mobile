package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Methods;
import DataBase.DataBaseManager;


public class EvaluationMenu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Button buttonCDI, buttonSI, buttonSDI, buttonReport;
    private String evaluationId;
    private String projectId;
    Context appContext = this;
    DataBaseManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new DataBaseManager(this);

        Intent intent = getIntent();
        JSONObject json = null;

        try {
            json = new JSONObject(intent.getStringExtra("json"));
            evaluationId =  json.get("evaluationId").toString();
            projectId = json.get("ProjectId").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.evaluation_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
                startSubEvaluation(CorrosionIndex.class, evaluationId);
                break;
            case R.id.buttonSI:
                startSubEvaluation(StructuralIndex.class, "EvaluationType");
                break;
            case R.id.buttonSDI:
                //startSubEvaluation(StructuralDamage.class, "SDI");
                startSubEvaluation(StructuralDamage.class, evaluationId);
                break;
            case R.id.buttonReport:
                try {
                    Methods.changeScreenAndSendJson(appContext, Report.class,
                            "json", new JSONObject("{'ProjectId':"+projectId+",'evaluationId':"+evaluationId+"}"));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            obj.put("evaluationId", evaluationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changeScreen(pClass, "data", obj);
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
            manager.openConnection();
            manager.disableUsers();
            manager.closeConnection();
            Methods.changeScreen(this,Login.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
