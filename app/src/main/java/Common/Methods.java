package Common;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itcr.eecc.eecc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Methods extends AppCompatActivity {

     /*// Changes of screen      ---- ORIGINAL
    public void changeScreen(Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(this, pClass);
        intent.putExtra(pMessageKey, pJsonMessageValue.toString());
        startActivity(intent);
    }*/



    /*-----------------------------------------------------
    * Send and receive objects through Activities
    *
    --------------------------------------------------------*/
    public static void changeScreenAndSendObject(Context pContext, Class pClass, String pMessageKey, Object pObject, String pObjectType){
        Intent intent = new Intent(pContext, pClass);
        Bundle params = new Bundle();
        params.putSerializable(pObjectType, (Serializable) pObject);
        intent.putExtra(pMessageKey, params);
        pContext.startActivity(intent);
    }

    // Returns a json object sent as parameter on the previous activity
    public static Object getObjectFromPreviousScreen(Intent pIntent, String pParamKey, String pObjectType){
        //Intent intent = getIntent();
        Bundle params = null;

        if(pIntent != null) {
            params = pIntent.getBundleExtra(pParamKey);

            if(params != null) {
                return params.getSerializable(pObjectType);
            }
        }

        return params;
    }


    // Changes of screen
    public static void changeScreenAndSendJson(Context pContext, Class pClass, String pMessageKey, JSONObject pJsonMessageValue) {
        Intent intent = new Intent(pContext, pClass);
        intent.putExtra(pMessageKey, pJsonMessageValue.toString());
        pContext.startActivity(intent);
    }

    // Changes of screen
    public static void changeScreen(Context pContext, Class pClass) {
        Intent intent = new Intent(pContext, pClass);
        pContext.startActivity(intent);
    }

    // Returns a json object sent as parameter on the previous activity
    public static JSONObject getJsonParamFromPreviousScreen(Intent pIntent, String pParamKey){
        //Intent intent = getIntent();
        JSONObject json = null;
        try {
            if(pIntent != null) {

                String jsonString = pIntent.getStringExtra(pParamKey);

                if (jsonString != null) {
                    json = new JSONObject(jsonString);

                    return json;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



}
