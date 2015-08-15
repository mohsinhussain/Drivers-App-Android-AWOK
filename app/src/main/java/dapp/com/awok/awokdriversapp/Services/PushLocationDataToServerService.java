package dapp.com.awok.awokdriversapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;
import dapp.com.awok.awokdriversapp.Utils.JSONParser;

public class PushLocationDataToServerService extends Service {

    public static final String APP_LOGIN = "PREFS_APP_LOGIN";


    public static final String APP_LOG_DID = "PREFS_APP_DID";


    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;


    private String filename = "SHON_LOG_UPDATE_INTERVAL.txt";
    private String filenamefd = "SHON_LOG_DON_MESS_WITH_ME_online.txt";
    private String filepath = "SHON_LOG_OFFLINE";
    File myInternalFile;
    File myExternalFile;
    //Boolean b=false;
    String txt_did;

    TimerTask doAsynchronousTask;
    public static final String LATLON_PREFS="APP_LOC_LAT_LON";
    public static final String LATLON_PREFS_VALUE="APP_VALUE_LAT_LON";
    SharedPreferences latlon;
    SharedPreferences.Editor latlon_editor;
    Boolean b=false;
    Timer timer;
    Handler handler=new Handler();
    Boolean isInternetPresent = false;
    String off_data;
    ConnectionDetector cd;
    JSONObject json;
    //private static final String store = "http://dev.alifca.com/d_login.php";
    private static String store;
            //= "http://dev.alifca.com/d_login.php";

    String new_freq,value_freq;

    public static final String PREFS_UPDATE_FREQUENCY = "APP_UPDATE_FREQ";
    public static final String PREFS_UPDATE_TIME = "APP_UPDATE_TIME";
    SharedPreferences update_frequency;
    SharedPreferences.Editor update_freq_editor;
int vz;


    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
    SharedPreferences server_pref;
    SharedPreferences.Editor serv_editor;
    String serv_txt;


    public PushLocationDataToServerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!b) {
            timer = new Timer();
             doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    //  handler.post(new Runnable() {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            try {

new up_off().execute();


                            } catch (Exception e) {
                            }
                        }
                    };
                    handler.post(runnable);
                }
            };
            timer.schedule(doAsynchronousTask, 10000, vz); //execute in every 10 ms

        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        //validate="http://"+serv_txt+"/d_login.php";
        //url="http://"+serv_txt+"/d_login.php";
        store="http://"+serv_txt+"/d_login.php";
        cd = new ConnectionDetector(getApplicationContext());
        get_id();
        get_up_freq();
    }
    @Override
    public void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
        try {
            timer.cancel();
            doAsynchronousTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



    public void get_id() {
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);

        txt_did = login_prefs.getString(APP_LOG_DID, null);

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
                        params.add(new BasicNameValuePair("action", "location"));
                        params.add(new BasicNameValuePair("type", "off_data"));
                        params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
                        System.out.println("LAT LONghhghfhffffffffffffffffffffffff" + txt_did.toString());
                        off_data=text;
                        sea();

                        json = jParser.makeHttpRequest(store, "POST", params);
                        //String cc=json.toString();

                        System.out.println("LAT LONghhghfhffffffffffffffffffffffff" + json.toString());
                        latlon = getSharedPreferences(LATLON_PREFS, Context.MODE_PRIVATE);
                        latlon_editor = latlon.edit(); //2

                        latlon_editor.clear();
                        latlon_editor.commit();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
              /*      try {
                        String jsonString = getJsonString();
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String nameTextView = jsonObject.getString("nameTextView");
                        String age = jsonObject.getString("age");
                        String address = jsonObject.getString("address");
                        String phone = jsonObject.getString("phone");
                        String jsonText=nameTextView + "\n" + age + "\n" + address + "\n" + phone;
                        json= (TextView)findViewById(R.id.json);
                        json.setText(jsonText);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }



            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



    private void get_up_freq()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";


        update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, Context.MODE_PRIVATE);
        text = update_frequency.getString(PREFS_UPDATE_TIME, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            new_freq="";
            new_freq="600000";

            update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0); //1
            update_freq_editor = update_frequency.edit(); //2

            update_freq_editor.putString(PREFS_UPDATE_TIME, new_freq); //3
            value_freq=new_freq;
            update_freq_editor.commit();
            vz= Integer.parseInt(value_freq);
        }
        else
        {
            //set_log();
            value_freq=text;
            System.out.println("PREFtime" + text);
            vz= Integer.parseInt(value_freq);
        }
    }



    public void sea()
    {



        try {
           /*FileInputStream fis = new FileInputStream(myExternalFile);
           DataInputStream in = new DataInputStream(fis);
           BufferedReader br =
                   new BufferedReader(new InputStreamReader(in));
           String strLine;
           while ((strLine = br.readLine()) != null) {
               myData = strLine;
               System.out.println("myData"+myData.toString());
           }
           in.close();*/



            myExternalFile = new File(getExternalFilesDir(filepath), filename);

            FileOutputStream fos = new FileOutputStream(myExternalFile,true);
            String guess=off_data.toString();
            System.out.println("guess"+guess.toString());
            fos.write(guess.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void dea()
    {



        try {
           /*FileInputStream fis = new FileInputStream(myExternalFile);
           DataInputStream in = new DataInputStream(fis);
           BufferedReader br =
                   new BufferedReader(new InputStreamReader(in));
           String strLine;
           while ((strLine = br.readLine()) != null) {
               myData = strLine;
               System.out.println("myData"+myData.toString());
           }
           in.close();*/



            myExternalFile = new File(getExternalFilesDir(filepath), filenamefd);

            FileOutputStream fos = new FileOutputStream(myExternalFile,true);
            String guess=off_data.toString();
            System.out.println("guess"+guess.toString());
            fos.write(guess.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
