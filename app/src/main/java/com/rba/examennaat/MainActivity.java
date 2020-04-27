package com.rba.examennaat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rba.examennaat.pojos.LoginPojo;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Activity mActivity;
    ProgressDialog progressDialog;

    private Button mbtnLogin;
    private EditText meTPass;
    private EditText meTUser;
    private ImageView miVShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity=MainActivity.this;

        mbtnLogin=findViewById(R.id.btnLogin);
        miVShow=findViewById(R.id.iVShow);
        meTPass=findViewById(R.id.eTPass);
        meTUser=findViewById(R.id.eTUser);

        progressDialog= new ProgressDialog(mActivity);

        mbtnLogin.setOnClickListener(this);
        miVShow.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        meTPass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        meTPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view==null)
            return;
        switch (view.getId()){
            case R.id.btnLogin:
                progressDialog.setMessage("Autenticando");
                progressDialog.setTitle("Examen Na-at");
                progressDialog.setIcon(R.drawable.ic_logo);
                progressDialog.setCancelable(false);
                progressDialog.show();

                try {
                    new PeticionToken().execute(meTUser.getText().toString(),sha256(meTPass.getText().toString()));
                } catch (Exception e) {
                    Log.d("ExamenNaat ###","Error En la petición de Login."+e);
                }
                break;
        }
    }

    public static void cambiarActivity(Activity actual,LoginPojo mpojo){
        Intent intent=new Intent();
        intent.setClass(actual, MenuActivity.class);
        intent.putExtra("loginPojo",mpojo);
        actual.startActivity(intent);
        actual.finish();
    }

    private static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

/*
    Petición de Login
*/
    @SuppressLint("StaticFieldLeak")
    private class PeticionToken extends AsyncTask<String,String,String> {
        private LoginPojo loginPojo;

        private String usuario;
        private String password;

        protected void onPreExecute() {
            loginPojo=new LoginPojo();
            loginPojo.setSuccess(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected String doInBackground(String... params) {
            usuario = params[0];
            password = params[1];

            try {

                URL httpbinEndpoint = new URL("https://uat.firmaautografa.com/authorization-server/oauth/token");
                HttpsURLConnection myConnection = (HttpsURLConnection) httpbinEndpoint.openConnection();

                myConnection.setRequestMethod("POST");
                myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                myConnection.setRequestProperty("Authorization", "Basic Wm1Ga0xXTXlZeTF3YjNKMFlXdz06TWpoa04yUTNNbUppWVRWbVpHTTBObVl4Wmpka1lXSmpZbVEyTmpBMVpEVXpaVFZoT1dNMVpHVTROakF4TldVeE9EWmtaV0ZpTnpNd1lUUm1ZelV5WWc9PQ==");

                HttpResponseCache myCache = HttpResponseCache.install(mActivity.getCacheDir(),100000L);
                String data = "grant_type="+ URLEncoder.encode("password","UTF-8")
                            +"&username="+ URLEncoder.encode(usuario,"UTF-8")
                            +"&password="+ URLEncoder.encode(password,"UTF-8");
                myConnection.setDoOutput(true);
                myConnection.setFixedLengthStreamingMode(data.getBytes().length);
                byte[] msg=data.getBytes();
                OutputStream out = new BufferedOutputStream(myConnection.getOutputStream());
                out.write(msg);
                out.flush();
                out.close();


                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        switch (jsonReader.nextName()){
                            case"access_token":
                                loginPojo.setAccessToken(jsonReader.nextString());
                                loginPojo.setSuccess(true);
                                break;
                            case"token_type":
                                loginPojo.setTokenType(jsonReader.nextString());
                                break;
                            case"refresh_token":
                                loginPojo.setRefreshToken(jsonReader.nextString());
                                break;
                            case"expires_in":
                                loginPojo.setExpiresIn(jsonReader.nextInt());
                                break;
                            case"scope":
                                loginPojo.setScope(jsonReader.nextString());
                                break;
                            case"jti":
                                loginPojo.setJti(jsonReader.nextString());
                                break;
                            case"success":
                                loginPojo.setSuccess(jsonReader.nextBoolean());
                                break;
                            case"error":
                                loginPojo.setError(jsonReader.nextString());
                                break;
                            default:
                                jsonReader.skipValue();
                                break;
                        }
                    }
                    jsonReader.close();
                    myConnection.disconnect();
                } else {
                    loginPojo.setError("Error de conexión:"+myConnection.getResponseCode());
                }
            }catch (Exception e){
                loginPojo.setError("Error de conexión. Intente más tarde.");
                Log.d("ExamenNaat ####", " Exception: "+e);
            }
            return loginPojo.isSuccess()+"";
        }

        protected void onPostExecute(String result) {
            if(loginPojo.isSuccess()){
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                cambiarActivity(mActivity,loginPojo);
            }else{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
                AlertDialog AD = builder.create();
                AD.setButton(AlertDialog.BUTTON_POSITIVE, "ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Login Exitoso
                                dialog.dismiss();
                            }
                        });
                AD.setIcon(R.drawable.ic_logo);
                AD.setTitle("Examen Na-at");
                AD.setMessage( (loginPojo.getError()!=null)?loginPojo.getError():"Error al inicar sesión.Intente más tarde.");
                AD.show();
            }
        }
    }
}
