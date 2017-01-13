package com.itcr.eecc.eecc;


        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.nfc.tech.NfcA;
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

        import Common.Constants;
        import Common.Methods;
        import DataBase.DataBaseManager;

public class Login extends Activity implements OnClickListener {

    // AGREGAR ESTO PARA TRABAJAR CON LA BASE DE DATOS

    private DataBaseManager manager;

    private EditText user, pass;
    private Button mSubmit;
    TextView mTextView;

    Context appContext = this;



    JSONParser jsonParser = new JSONParser();

    //Michael
    private static final String LOGIN_URL = "http://192.168.0.216:81/admin/Proyecto/eecc/EECC_Web/php/controllers/user/login.php";

    //William
    //private static final String LOGIN_URL = "http://192.168.0.105:8081/EECC_Web/php/controllers/user/login.php";


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
                    if(!isInputEmpty("inputUsername") && !isInputEmpty("inputPassword")){

                        if(validateEmail(user.getText().toString())){
                            new AttemptLogin().execute();

                        }
                        else {
                            Toast.makeText(Login.this,"Formato de correo inválido",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Login.this,"Ingrese los credenciales",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            Toast.makeText(Login.this,"Error conexión a Internet",Toast.LENGTH_SHORT).show();
        }


    }
    // returns if the device has a connection to internet
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
                if(json != null){
                    JSONArray result = (JSONArray)json.get("result");
                    Log.d("Lenght", ""+result.length());
                    if(result.length()!=0){
                        Log.d("JSON:", result.get(0).toString());
                        String Name, LastName, Email;
                        Name = ((JSONObject)result.get(0)).get("Name").toString();
                        LastName = ((JSONObject)result.get(0)).get("Lastname").toString();
                        Email = ((JSONObject)result.get(0)).get("Email").toString();
                        mTextView.setText("Email: - " + Email);
                        int response = createUser(Name, LastName, Email);

                        if(response == 1){
                            Methods.changeScreen(appContext,Projects.class);
                            Toast.makeText(getApplicationContext(),"Todo fue un éxito ", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Ha ocurrido un error", Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(Login.this,"Credenciales inválidas",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, Constants.ERROR_LOGGING_CONNECTION,Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


    public int createUser(String pName, String pLastName, String pEmail){
        manager.openConnection();

        long value = manager.createUser(pName, pLastName, pEmail);

        manager.closeConnection();
        if(value == -1){
            return -1;
        }
        return 1;

    }

    // Returns if a text input is empty
    public  boolean isInputEmpty(String pInputId){
        EditText editInput = null;

        switch (pInputId){
            case "inputUsername":
                editInput = (EditText) findViewById(R.id.inputUsername);
                break;
            case "inputPassword":
                editInput = (EditText) findViewById(R.id.inputPassword);
                break;
        }

        if(editInput == null){
            return true;
        } else {
            return editInput.getText().toString().equals("");
        }

    }
}