package com.itcr.eecc.eecc;

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
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DataBaseManager;

public class ProjectList extends AppCompatActivity {

    DataBaseManager manager;
    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    Button btnUseProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        manager = new DataBaseManager(this);
        productsList = new ArrayList<HashMap<String, String>>();
        btnUseProject = (Button) findViewById(R.id.btn_use_project);
        loadProjectList();
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
                ProjectList.this, productsList,
                R.layout.project_list_items, new String[] { TAG_PID,
                TAG_NAME},
                new int[] { R.id.pid, R.id.name });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();
                Toast.makeText(getApplicationContext(), "Este fue" + pid, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertToken(View v){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.token_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText tokenInputDialog = (EditText) mView.findViewById(R.id.tokenInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        Toast.makeText(getApplicationContext(), "Este fue " + tokenInputDialog.getText().toString(), Toast.LENGTH_SHORT).show();
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
