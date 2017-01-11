package com.itcr.eecc.eecc;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import DataBase.DataBaseManager;

public class ProjectInformation extends AppCompatActivity {

    TextView tv_projectname, tv_responsable, tv_structuretype, tv_location, tv_component_description, tv_generaluse, tv_creationstructruedate, tv_creationprojectdate;
    DataBaseManager manager;

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
        loadInformationProject();
    }

    public void loadInformationProject(){
        manager.openConnection();
        Cursor cursor = manager.getProjectInformation("1");
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
}
