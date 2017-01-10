package com.itcr.eecc.eecc;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import Common.Methods;
import DataBase.DataBaseManager;

public class Projects extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DataBaseManager manager;
    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";

    Button btnUseProject;
    Button btnCreateNewProject;

    Context appContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        manager = new DataBaseManager(this);
        productsList = new ArrayList<HashMap<String, String>>();
        btnUseProject = (Button) findViewById(R.id.btn_use_project);
        loadProjectList();

        btnCreateNewProject = (Button) findViewById(R.id.btn_create_new);

        btnCreateNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Methods.changeScreen(appContext, ProjectForm.class);

                Methods.changeScreen(appContext, EvaluationMenu.class);

            }
        });

    }

    public void loadProjectList() {
        Cursor cursor = manager.getProjectList();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("ProjectId"));
                String name = cursor.getString(cursor.getColumnIndex("Name"));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_PID, id);
                map.put(TAG_NAME, name);
                productsList.add(map);
            } while (cursor.moveToNext());
        }
        manager.closeConnection();

        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListAdapter adapter = new SimpleAdapter(
                Projects.this, productsList,
                R.layout.project_list_items, new String[] { TAG_PID,
                TAG_NAME},
                new int[] { R.id.pid, R.id.name });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String projectId = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();
                manager.openConnection();
                String evaluationId = manager.getEvaluationIdbyProject(projectId);
                if(evaluationId.equals("-1")){
                    alertMessageCreateEvaluation(projectId);
                } else {
                    alertMessageGoEvaluation(evaluationId);
                }
                manager.closeConnection();
            }
        });
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
                            //Cambiar de pantalla a la evaluación
                            Toast.makeText(getApplicationContext(), "SE CAMBIO DE PANTALLA ", Toast.LENGTH_LONG).show();
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

    public void alertMessageGoEvaluation(String evaluationId){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("INFORMACIÓN IMPORTANTE\nLe recordamos que usted tiene una evaluación en proceso");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ir a la evaluación",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "SE CAMBIO DE PANTALLA ", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void insertToken(View v){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.token_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText tokenInputDialog = (EditText) mView.findViewById(R.id.tokenInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Evaluar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        //This is the evaluatioin value
                        manager.openConnection();
                        if(!tokenInputDialog.getText().toString().trim().equals("")){
                            String result = manager.createProjectToken(tokenInputDialog.getText().toString());
                            Toast.makeText(getApplicationContext(), "Este fue el id de la evaluacion " + result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR Token invalido", Toast.LENGTH_LONG).show();
                        }
                        manager.closeConnection();
                    }
                })

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
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
        getMenuInflater().inflate(R.menu.projects, menu);
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

    // Sets the screen's title
    public void changeScreenTitle(String pNewTitle){
        getSupportActionBar().setTitle(pNewTitle);
    }


}
