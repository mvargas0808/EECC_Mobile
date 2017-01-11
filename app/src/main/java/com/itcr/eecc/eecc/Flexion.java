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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Common.Methods;


public class Flexion extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<Integer> longitudinalValues;
    private ArrayList<Integer> transverseValues;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        longitudinalValues = new ArrayList<>();
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
            case "S_Flexion":
                setContentView(R.layout.si_flexion_simplified);
                break;
            case "M_Flexion":
                setContentView(R.layout.si_flexion_manual);
                buttonNext = (Button) findViewById(R.id.buttonLongitudinal);
                // register listeners
                buttonNext.setOnClickListener(this);
                break;
            case "M_Flexion_Second":
                setContentView(R.layout.si_flexion_manual_second);
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
        switch (v.getId()) {
            case R.id.buttonLongitudinal:
                startSubEvaluation(Flexion.class, "M_Flexion_Second");
                break;
            default:
                break;
        }
    }


    public void changeScreen(Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(Flexion.this, pClass);
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

    public void setLongitudinal(View v){
        TextView texto = (TextView) findViewById(v.getId());
        int indexValue = Integer.parseInt(texto.getText().toString());

        switch (v.getId()) {
            case R.id.iat11:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 1);
                break;
            case R.id.iat12:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 2);
                break;
            case R.id.iat13:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 3);
                break;
            case R.id.iat14:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 1, 4);
                break;
            case R.id.iat21:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 2, 1);
                break;
            case R.id.iat22:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
                setLongitudinalValues(indexValue, 2, 2);
                break;
            case R.id.iat23:
                Toast.makeText(Flexion.this,"soy "+texto.getText(),Toast.LENGTH_SHORT).show();
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
