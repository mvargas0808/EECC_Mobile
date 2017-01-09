package com.itcr.eecc.eecc;

/**
 * Created by Michael on 07/01/2017.
 */

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.DatabaseMetaData;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import DataBase.DataBaseManager;

public class Login extends Activity implements OnClickListener {

    // AGREGAR ESTO PARA TRABAJAR CON LA BASE DE DATOS

    private DataBaseManager manager;

    private EditText user, pass;
    private Button mSubmit;
    TextView mTextView;

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://192.168.0.216:81/admin/Proyecto/eecc/EECC_Web/php/controllers/user/login.php";

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        manager = new DataBaseManager(this);

        // setup input fields
        user = (EditText) findViewById(R.id.inputUsername);
        pass = (EditText) findViewById(R.id.inputPassword);

        // setup buttons
        mSubmit = (Button) findViewById(R.id.buttonLogin);

        // register listeners
        mSubmit.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.textView2);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if(getConectionState()){
            switch (v.getId()) {
                case R.id.buttonLogin:
                    if(validateEmail(user.getText().toString())){
                        new AttemptLogin().execute();
                    }
                    else {
                        //Toast.makeText(Login.this,"Formato de correo inválido",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ProjectForm.class);
                        startActivity(i);
                    }
                    break;
                default:
                    break;
            }
        }
        else
            Toast.makeText(Login.this,"Error conexión a Internet",Toast.LENGTH_SHORT).show();

    }

    protected boolean getConectionState(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }

    protected static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


    class AttemptLogin extends AsyncTask<JSONObject, String, JSONObject> {

        protected JSONObject doInBackground(JSONObject... args) {
            // Building Parameters
            String username = user.getText().toString();
            String password = pass.getText().toString();

            JSONObject dataForPost = new JSONObject();

            try {
                dataForPost.put("email", username);
                dataForPost.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsonResult = jsonParser.makeHttpRequest(LOGIN_URL, "POST", dataForPost);

            return jsonResult;

        }

        protected void onPostExecute(JSONObject json) {
            // dismiss the dialog after getting all products
            try {
                JSONArray result = (JSONArray)json.get("result");
                Log.d("Lenght", ""+result.length());
                if(result.length()!=0){
                    Log.d("JSON:", result.get(0).toString());
                    mTextView.setText("Email: - " + ((JSONObject)result.get(0)).get("Email").toString());
                }
                else{
                    Toast.makeText(Login.this,"Credenciales inválidas",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}