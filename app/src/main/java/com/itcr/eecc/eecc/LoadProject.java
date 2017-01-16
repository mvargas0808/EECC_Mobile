package com.itcr.eecc.eecc;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class LoadProject extends AppCompatActivity {


    DataBaseManager manager;
    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";

    Button btnUseProject;

    Context appContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_project);

        manager = new DataBaseManager(this);

        btnUseProject = (Button) findViewById(R.id.btn_use_project);
        loadProjectList();
        loadTokenList();

    }

    public void loadTokenList(){
        productsList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = manager.getTokenList();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("TokenId"));
                String name = cursor.getString(cursor.getColumnIndex("Token"));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_PID, id);
                map.put(TAG_NAME, name);
                productsList.add(map);
            } while (cursor.moveToNext());
        }
        manager.closeConnection();

        ListView listViewToken = (ListView) findViewById(R.id.listViewToken);
        listViewToken.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListAdapter adapter = new SimpleAdapter(
                LoadProject.this, productsList,
                R.layout.token_list_items, new String[] { TAG_PID,
                TAG_NAME},
                new int[] { R.id.pidToken, R.id.nameToken });
        listViewToken.setAdapter(adapter);
        listViewToken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String TokenId = ((TextView) view.findViewById(R.id.pidToken)).getText()
                        .toString();
                alertLoadTokens(TokenId);
            }
        });
    }

    public void alertLoadTokens(String projectId){
        final String projectIdFinal = projectId;
        Toast.makeText(getApplicationContext(), "fINAL " + projectIdFinal, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Desea subir la evaluación a internet?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Subir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Todo fue subido", Toast.LENGTH_SHORT).show();
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

    public void loadProjectList() {
        productsList = new ArrayList<HashMap<String, String>>();
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
                LoadProject.this, productsList,
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
                alertLoadProjects(projectId);
            }
        });
    }

    public void alertLoadProjects(String projectId){
        final String projectIdFinal = projectId;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Desea subir los datos a internet?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Subir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            setProyectMySQL(projectIdFinal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


    public void setProyectMySQL(String projectId) throws JSONException {
        manager.openConnection();
        manager.saveProjectMySQL(projectId, appContext);
        manager.closeConnection();
        Toast.makeText(getApplicationContext(), "fINAL " + projectId, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Todo fue subido", Toast.LENGTH_SHORT).show();
    }
}
