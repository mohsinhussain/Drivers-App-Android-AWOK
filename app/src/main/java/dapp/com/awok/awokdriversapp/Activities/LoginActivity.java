package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dapp.com.awok.awokdriversapp.R;
import dapp.com.awok.awokdriversapp.Services.FetchLocationCordinatesService;
import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;
import dapp.com.awok.awokdriversapp.Utils.JSONParser;

/**
 * Created by shon on 4/11/2015.
 */
public class LoginActivity extends Activity {



    public static final String LATLON_PREFS="APP_LOC_LAT_LON";
    public static final String LATLON_PREFS_VALUE="APP_VALUE_LAT_LON";
    SharedPreferences latlon;
    SharedPreferences.Editor latlon_editor;
    CircleProgressBar p1;
    TextView p;

    private EditText log,pass;
    String txt_log,txt_pass;
    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_DID = "PREFS_APP_DID";
    public static final String APP_LOG_FNAME = "PREFS_APP_FNAME";
    public static final String APP_LOG_LNAME = "PREFS_APP_LNAME";
    public static final String APP_LOG_KEY = "PREFS_APP_KEY";
    JSONObject json;
    String text_zy;
    Button b;
    TextView ne, signInTextView;
    Button logout;
    String txt_did;
    LinearLayout l1,l2;
      Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private static String store;
    String a;
    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;
    private static String login;
    String product,did,name,lname,key;
    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
    SharedPreferences server_pref;
    String serv_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lgin_main);
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        login="http://"+serv_txt+"/d_login.php";
        store="http://"+serv_txt+"/d_login.php";
        get_id();
        log=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        signInTextView = (TextView) findViewById(R.id.textView2);
        cd = new ConnectionDetector(getApplicationContext());
        p1=(CircleProgressBar)findViewById(R.id.progress1);
        p1.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light,android.R.color.holo_blue_dark,android.R.color.holo_purple);
        p1.setShowArrow(true);
        p=(TextView)findViewById(R.id.pw);
        ne=(TextView)findViewById(R.id.tx1);
        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new up_off().execute();
            }
        });

        b=(Button)findViewById(R.id.email_sign_in_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();

            }
        });
        l1=(LinearLayout)findViewById(R.id.user_status);
        l2=(LinearLayout)findViewById(R.id.ll2);

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    check();
                }
                return false;
            }
        });

        getdata();
    }
    public void get_id() {
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);

        txt_did = login_prefs.getString(APP_LOG_DID, null);

    }
public void check()
{
    if(log.getText().length()==0 || pass.getText().length()==0) {
        if (log.getText().length() == 0) {
            log.setError("PLEASE ENTER USERNAME OR EMAIL");
        }
        if (pass.getText().length() == 0) {
            pass.setError("PLEASE ENTER PASSWORD");
        }
    }
    else
    {
        isInternetPresent = cd.isConnectingToInternet();
        if(isInternetPresent) {

            new Login_Task().execute();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Connect to Data Services to LoginActivity", Toast.LENGTH_LONG).show();
        }
    }
}
    private class Login_Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            JSONParser jParser=new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", txt_log));
            params.add(new BasicNameValuePair("password", txt_pass));
            params.add(new BasicNameValuePair("action", "login"));
            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            json = jParser.makeHttpRequest(login, "POST", params);
            System.out.println(json);
            try {
                product = json.getString("key");
                System.out.println(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(product.equals("ude"))
            {
                Toast.makeText(getApplicationContext(), "User Does not Exist", Toast.LENGTH_LONG).show();
            }
            else if(product.equals("ip"))
            {
                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
            }
            else if(product.equals("nd"))
            {
                Toast.makeText(getApplicationContext(), "Not a Driver , Please Try Again", Toast.LENGTH_LONG).show();
            }
            else
            {
                try {
                    did=json.getString("did");
                    key=json.getString("key");
                    name=json.getString("name");
                    lname=json.getString("lname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                store();
            }
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            signInTextView.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {

            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            signInTextView.setVisibility(View.GONE);
            p1.bringToFront();
            p.bringToFront();
            txt_log=log.getText().toString();
            txt_pass=pass.getText().toString();
            super.onPreExecute();
        }
    }


    public void store()
    {
        login_prefs = getSharedPreferences(APP_LOGIN, 0); //1
        login_prefs_editor = login_prefs.edit(); //2
        login_prefs_editor.putString(APP_LOG_FNAME, name);
        login_prefs_editor.putString(APP_LOG_LNAME, lname);
        login_prefs_editor.putString(APP_LOG_DID, did);
        login_prefs_editor.putString(APP_LOG_KEY, key);
        login_prefs_editor.commit();
        b.setEnabled(false);
        stopService(new Intent(LoginActivity.this,FetchLocationCordinatesService.class));
        Intent i=new Intent(LoginActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }



    public void getdata()
    {
        String b,c,d,check;
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
        a = login_prefs.getString(APP_LOG_FNAME, null);
        b = login_prefs.getString(APP_LOG_LNAME, null);
        c = login_prefs.getString(APP_LOG_DID, null);
        d = login_prefs.getString(APP_LOG_KEY, null);

        System.out.println(a + "" + b + "" + c + "" + d);
        if(a==null)
        {
            l2.setVisibility(View.VISIBLE);
        }
        else
        {
            l1.setVisibility(View.VISIBLE);
            ne.setText("Logged In as : "+a.toString());
        }
    }


    @Override
    public void onBackPressed() {
        if(a==null)
        {
        }
        else
        {
            super.onBackPressed();
        }
    }

    private class up_off extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... paramz) {
            isInternetPresent = cd.isConnectingToInternet();
            if(isInternetPresent) {
                String text;
                latlon = getSharedPreferences(LATLON_PREFS, Context.MODE_PRIVATE);
                text = latlon.getString(LATLON_PREFS_VALUE, null);
                text_zy=text;
                System.out.println("PREF_off" + text);

                if (text==null)
                {
                    System.out.println("NO DATA SENT");
                }
                else
                {
                    try{
                        JSONParser jParser = new JSONParser();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("DID", txt_did));
                        params.add(new BasicNameValuePair("OFF_LATLON", text.toString()));
                        params.add(new BasicNameValuePair("action", "locationValueTextView"));
                        params.add(new BasicNameValuePair("type", "off_data"));
                        params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
                        json = jParser.makeHttpRequest(store, "POST", params);
                        System.out.println("LAT LONghhghfhffffffffffffffffffffffff" + json.toString());
                        latlon = getSharedPreferences(LATLON_PREFS, Context.MODE_PRIVATE);
                        latlon_editor = latlon.edit(); //2
                        latlon_editor.clear();
                        latlon_editor.commit();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "PLEASE CONNECT TO DATA SERVICES TO LOGOUT", Toast.LENGTH_LONG).show();
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            signInTextView.setVisibility(View.GONE);
            p1.bringToFront();
            p.bringToFront();
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            signInTextView.setVisibility(View.VISIBLE);
            if (text_zy==null)
            {
                System.out.println("NO DATA SENT");
                login_prefs = getSharedPreferences(APP_LOGIN, 0); //1
                login_prefs_editor = login_prefs.edit(); //2
                login_prefs_editor.clear();
                login_prefs_editor.commit();
                stopService(new Intent(LoginActivity.this, FetchLocationCordinatesService.class));
                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(i);
            }
            else {
                try {
                    String ja=json.getString("key");
                    if(ja.equals("save"))
                    {
                        login_prefs = getSharedPreferences(APP_LOGIN, 0); //1
                        login_prefs_editor = login_prefs.edit(); //2
                        login_prefs_editor.clear();
                        login_prefs_editor.commit();
                        stopService(new Intent(LoginActivity.this, FetchLocationCordinatesService.class));
                        Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "PLEASE TRY AGAIN LATER", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }
}
