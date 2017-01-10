package com.itcr.eecc.eecc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Common.ElementKey;
import Common.Methods;
import DataBase.DataBaseManager;

public class ProjectForm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DataBaseManager manager;
    Spinner spProvince, spCanton, spDistrict;
    Button btnGetDate;
    TextView tvDate;
    DialogFragment newFragment;
    ElementKey ekProvincias;
    ElementKey ekCantons;
    ElementKey ekDistricts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manager = new DataBaseManager(this);
        btnGetDate = (Button) findViewById(R.id.btn_getDate);
        tvDate = (TextView) findViewById(R.id.tv_date);
        spProvince = (Spinner) findViewById(R.id.sp_province);
        spCanton = (Spinner) findViewById(R.id.sp_canton);
        spDistrict = (Spinner) findViewById(R.id.sp_district);
        newFragment = new DatePickerFragment();

        loadStructureTypeSpinner();
        loadProvinceSpinner();

    }

    public void showPickerDate(View v){
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void loadProvinceSpinner(){
        Cursor cursor = manager.getProvinces();
        List<String> provincesArray = new ArrayList<String>();
        ekProvincias = new ElementKey();

        if (cursor.moveToFirst()) {
            do {
                ekProvincias.addElement(cursor.getString(cursor.getColumnIndex("ProvinceId")),
                        cursor.getString(cursor.getColumnIndex("ProvinceName")));
                provincesArray.add(cursor.getString(cursor.getColumnIndex("ProvinceName")));
            } while(cursor.moveToNext());
        }
        manager.closeConnection();
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, provincesArray);
        spProvince.setAdapter(provinceAdapter);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadCantonSpinner(ekProvincias.getKey(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadCantonSpinner(ekProvincias.getKey(spProvince.getSelectedItem().toString()));

    }

    private void loadCantonSpinner(String provinceId){
        Cursor cursor = manager.getCantons(provinceId);
        List<String> cantonsArray = new ArrayList<String>();
        ekCantons = new ElementKey();

        if (cursor.moveToFirst()) {
            do {
                ekCantons.addElement(cursor.getString(cursor.getColumnIndex("CantonId")),
                        cursor.getString(cursor.getColumnIndex("CantonName")));
                cantonsArray.add(cursor.getString(cursor.getColumnIndex("CantonName")));
            } while(cursor.moveToNext());
        }
        manager.closeConnection();
        ArrayAdapter<String> cantonAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, cantonsArray);
        spCanton.setAdapter(cantonAdapter);
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDistrictSpiner(ekCantons.getKey(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

                loadDistrictSpiner(ekCantons.getKey(spCanton.getSelectedItem().toString()));
    }

    private void loadDistrictSpiner(String cantonId){
        Cursor cursor = manager.getDistricts(cantonId);
        List<String> districtsArray = new ArrayList<String>();
        ekDistricts = new ElementKey();

        if (cursor.moveToFirst()) {
            do {
                ekDistricts.addElement(cursor.getString(cursor.getColumnIndex("DistrictId")),
                        cursor.getString(cursor.getColumnIndex("DistrictName")));
                districtsArray.add(cursor.getString(cursor.getColumnIndex("DistrictName")));
            } while(cursor.moveToNext());
        }
        manager.closeConnection();
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, districtsArray);
        spDistrict.setAdapter(districtAdapter);
    }

    private void loadStructureTypeSpinner(){
        Cursor cursor = manager.getStructureTypeNames();
        List<String> structureNameArray = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                structureNameArray.add(cursor.getString(cursor.getColumnIndex("Name")));
            } while(cursor.moveToNext());
        }
        manager.closeConnection();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, structureNameArray);

        Spinner mySpiner = (Spinner) findViewById(R.id.sp_structuretype);
        mySpiner.setAdapter(myAdapter);

        /*
        Spinner mySpiner = (Spinner) findViewById(R.id.sp_structuretype);
        mySpiner.setAdapter(myAdapter);
        mySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            tvDate.setText(year+"-"+((month+1) < 10 ? "0"+(month+1) : (month+1)) +"-"+(day < 10 ? "0"+day : day));
        }
    }
}
