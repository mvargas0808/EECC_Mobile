package com.itcr.eecc.eecc;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
                Methods.changeScreen(appContext, ProjectForm.class);
                finish();
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
                try {
                    String projectId = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();
                    Methods.changeScreenAndSendJson(appContext, ProjectInformation.class,
                            "Data", new JSONObject("{'ProjectId':"+projectId+"}"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                String projectId = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();
                alertMessageCreateEvaluation(projectId);
                return true;
            }
        });
    }

    public void alertMessageCreateEvaluation(final String projectId){
        final String projectIdFinal = projectId;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Desea eliminar el proyecto?\n(Toda la información se perderá y no será recuperada)");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Eliminar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        manager.openConnection();
                        long code = manager.deleteProyect(projectId);
                        if(code == 1){
                            Toast.makeText(getApplicationContext(), "El proyecto fue eliminado", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        } else {
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
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
                            String evaluationId = manager.createProjectToken(tokenInputDialog.getText().toString().toUpperCase());

                            JSONObject json = new JSONObject();
                            try {
                                //Cambiar de pantalla a la evaluación
                                json.put("evaluationId",evaluationId);
                                Methods.changeScreenAndSendJson(appContext, EvaluationMenu.class, "json", json);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            Methods.changeScreen(this, LoadProject.class);
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

    // Sets the screen's title
    public void changeScreenTitle(String pNewTitle){
        getSupportActionBar().setTitle(pNewTitle);
    }

}
