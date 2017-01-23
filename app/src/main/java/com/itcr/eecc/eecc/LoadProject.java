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

import java.lang.reflect.Method;
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
                String TokenName = ((TextView) view.findViewById(R.id.nameToken)).getText()
                        .toString();
                alertLoadTokens(TokenId, TokenName);
            }
        });

        listViewToken.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                String tokenId = ((TextView) view.findViewById(R.id.pidToken)).getText()
                        .toString();
                insertToken(tokenId);
                return true;
            }
        });
    }

    public void alertLoadTokens(final String tokenId, final String tokenName){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("¿Desea subir la evaluación a internet?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Subir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            setTokenProyectMySQL(tokenId, tokenName);
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
        if(Methods.isOnlineNet()){
            manager.openConnection();
            manager.saveProjectMySQL(projectId, appContext, this);
            manager.closeConnection();
        } else {
            Toast.makeText(getApplicationContext(), "No cuenta con una conexión a internet", Toast.LENGTH_LONG).show();
        }

    }

    public void setTokenProyectMySQL(String tokenId, String tokenName) throws JSONException {
        if(Methods.isOnlineNet()){
            manager.openConnection();
            manager.saveTokenProjectMySQL(tokenId, tokenName, appContext, this);
            manager.closeConnection();
        } else {
            Toast.makeText(getApplicationContext(), "No cuenta con una conexión a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void insertToken(final String tokenId){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.token_input_dialog_change, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText tokenInputDialog = (EditText) mView.findViewById(R.id.tokenInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        //This is the evaluatioin value
                        manager.openConnection();
                        if(!tokenInputDialog.getText().toString().trim().equals("")){
                            manager.updateToken(tokenInputDialog.getText().toString().toUpperCase(), tokenId);
                            loadTokenList();
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
}
