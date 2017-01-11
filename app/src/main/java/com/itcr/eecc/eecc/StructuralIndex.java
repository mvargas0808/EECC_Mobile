package com.itcr.eecc.eecc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Methods;


public class StructuralIndex extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Button buttonManual, buttonSimplified;
    private RadioGroup radioType;
    private ImageView iaa1;
    private String evaluationId;

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
        Log.d("JSON:", activityType);

        switch (activityType) {
            case "EvaluationType":
                setContentView(R.layout.structural_index_type);
                buttonManual = (Button) findViewById(R.id.buttonManual);
                buttonSimplified = (Button) findViewById(R.id.buttonSimplified);
                radioType = (RadioGroup)findViewById(R.id.radioType);
                buttonManual.setOnClickListener(this);
                buttonSimplified.setOnClickListener(this);
                //radioType.setOnCheckedChangeListener(this);
                break;
            default:
                break;
        }

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
        radioType = (RadioGroup)findViewById(R.id.radioType);
        int checkedRadio = radioType.getCheckedRadioButtonId();
        switch (v.getId()) {
            case R.id.buttonSimplified:
                switch (checkedRadio){
                    case R.id.radioFlexion:
                        startSubEvaluation(Flexion.class, "S_Flexion");
                        break;
                    case R.id.radioFlexocompresion:
                        startSubEvaluation(Flexocompresion.class, "S_Flexocompresion");
                        break;
                }
                break;
            case R.id.buttonManual:
                switch (checkedRadio){
                    case R.id.radioFlexion:
                        startSubEvaluation(Flexion.class, "M_Flexion");
                        break;
                    case R.id.radioFlexocompresion:
                        startSubEvaluation(Flexocompresion.class, "M_Flexocompresion");
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

        } else if (id == R.id.nav_logout) {
            Methods.changeScreen(this,Login.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
