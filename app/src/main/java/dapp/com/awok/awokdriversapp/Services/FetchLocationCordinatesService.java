package dapp.com.awok.awokdriversapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dapp.com.awok.awokdriversapp.Utils.JSONParser;
import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;

public class FetchLocationCordinatesService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
JSONObject json;




    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
    SharedPreferences server_pref;
    SharedPreferences.Editor serv_editor;
    String serv_txt;



    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 1;
    //private static final String store = "http://dev.alifca.com/d_login.php";
    private static String store;
            //= "http://dev.alifca.com/d_login.php";
    double zlatitude, zlongitude;
    Location locationz;
    //boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;





    public static final String PREFS_SWITCH = "APP_SWITCH";
    public static final String PREFS_SWITCH_VALUE = "APP_SWITCH_VALUE";
    SharedPreferences switch_prefs;
    SharedPreferences.Editor switch_editor;




    boolean isGPSEnabled = false;

    LocationManager locationManager;

String txt_did;

    // flag for Internet connection status
    Boolean isInternetPresent = false;
double latitude,longitude;
    Date date;
    String temp_date="";
    long time;
    public static final String PREFS_HISTORY_LAT_LON = "APP_PREFS_HISTORY_LAT_LON";
    public static final String PREFS_SET_HISTORY_LAT_LON = "APP_PREFS_SET_HISTORY_LAT_LON";
    SharedPreferences history_lat_lon;
    SharedPreferences.Editor editor_history_lat_lon;
    // Connection detector class
    ConnectionDetector cd;
    public static final String LATLON_PREFS="APP_LOC_LAT_LON";
    public static final String LATLON_PREFS_VALUE="APP_VALUE_LAT_LON";
    SharedPreferences latlon;
    SharedPreferences.Editor latlon_editor;
    //JSONObject lat_lon_object;

    private static final String TAG = FetchLocationCordinatesService.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
Handler handler=new Handler();
    Runnable r;
    private LocationRequest mLocationRequest;
    TimerTask doAsynchronousTask;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 30000; // 10 sec
    private static int FATEST_INTERVAL = 10000; // 5 sec
    private static int DISPLACEMENT = 5; // 10 meters

    Boolean b=false;
    public static final String PREFS_FREQUENCY = "APP_FREQ";
    public static final String PREFS_TIME = "APP_TIME";
    SharedPreferences frequency;
    SharedPreferences.Editor freq_editor;
    String new_freq,value_freq,his_lat_lon,val;
    int vz;
    Timer timer;


    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_NAME = "PREFS_APP_NAME";

    public static final String APP_LOG_DID = "PREFS_APP_DID";
    public static final String APP_LOG_KEY = "PREFS_APP_KEY";

    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;





    private String filename = "SHON_LOG_DON_MESS_WITH_ME.txt";
    private String filenamefd = "SHON_LOG_DON_MESS_WITH_ME_online.txt";
    private String filepath = "SHON_LOG_OFFLINE";
    File myInternalFile;
    File myExternalFile;
    String myData;
    String off_data;




    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service Created!");
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        //validate="http://"+serv_txt+"/d_login.php";
        //url="http://"+serv_txt+"/d_login.php";
        store="http://"+serv_txt+"/d_login.php";
        System.out.println("CREATED");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        Log.d(TAG, "onStart(): connecting");
//        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
       // }
        get_freq();
        get_id();
      //  startLocationUpdates();
        switch_prefs = getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE);
         val = switch_prefs.getString(PREFS_SWITCH_VALUE, null);
        System.out.println("PREF_switch" + val);
    }

    @Override
    public void onDestroy() {
       /* super.onDestroy();
        timer.cancel();*/
        Log.v(TAG, "Service Destroyed!");
        try {
            timer.cancel();
            doAsynchronousTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                        //System.out.println("HELLO WORLD");
                        displayLocation();

                    } catch (Exception e) {
                    }
                }
            };
            handler.post(runnable);
        }
    };
    timer.schedule(doAsynchronousTask, 10000, vz); //execute in every 10 ms
    System.out.println("VZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"+vz);
}
        //return START_NOT_STICKY;
        return START_STICKY;
    }

    public void get_id() {
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);

        txt_did = login_prefs.getString(APP_LOG_DID, null);

    }

    private class LongOperation extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... paramz) {

            System.out.println("Updating");
            System.out.println("LATLON"+latitude +longitude+ date);
            JSONParser jParser=new JSONParser();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("DID", txt_did));
            params.add(new BasicNameValuePair("LAT", Double.toString(latitude)));
            params.add(new BasicNameValuePair("LON", Double.toString(longitude)));
            params.add(new BasicNameValuePair("TSTAMP", date.toString()));
            params.add(new BasicNameValuePair("action", "location"));
System.out.println(latitude);
            // getting JSON string from URL
            json = jParser.makeHttpRequest(store, "POST", params);
            System.out.println("LAT LON"+json.toString());

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

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



//    GOOGLE API METHODS


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected(): connected to Google APIs");
        System.out.println("onConnected()");
        // Once connected with google api, get the location
     ///////////////////////////////////   displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
// Assign the new location
        mLastLocation = location;

       // Toast.makeText(getApplicationContext(), "Location changed!",
            //    Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
   //     displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }




    private void displayLocation() {
        isInternetPresent = cd.isConnectingToInternet();
        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnabled && !isInternetPresent) {
            System.out.println("Please Enable GPS Location / Please Check Data Services");
        }
else {







                    mLastLocation = LocationServices.FusedLocationApi
                            .getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                        System.out.println("LAT" + latitude + "LAN" + longitude);
                        time = mLastLocation.getTime();
                        date = new Date(time);
                        System.out.println("fhggkgfghghjj" + date);

                     //   Toast.makeText(this, "LAT" + latitude + "LAN" + longitude, Toast.LENGTH_LONG).show();
                        if(isInternetPresent) {
                            ////////////////////new LongOperation().execute();
                            store();
                       //     Toast.makeText(this, "UPDATING", Toast.LENGTH_SHORT).show();
                            System.out.println("FVNGFDGHFGHD");
                            off_data=txt_did+ Double.toString(latitude)+ Double.toString(longitude)+date+"ONLINE";
                            dea();
                        }
                        else
                        {
                           /* if (val.equals("true"))
                            {*/
                                if(date.toString()==null)
                                {
                                    System.out.println("NULL");
                                }
                                else {
                                    System.out.println("getette"+temp_date.toString());
                                    System.out.println(date.toString());

                                    String ma=date.toString();
                                    if(date.toString().equals(temp_date.toString()))
                                    {
                                        System.out.println("hehahahahah");

                                    }
                                    else
                                    {
                                        store();
                                        temp_date=date.toString();
                                    }

                                }

                          /*  }
                            else
                            {

                            }*/

                        }
                    } else {
                        Toast.makeText(this, "Please Check GPS / Data Services", Toast.LENGTH_LONG).show();
                    }




        }
    }


    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {
        System.out.println("startLocationUpdates()");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


private void store()
{


    latlon = getSharedPreferences(LATLON_PREFS, Context.MODE_APPEND);
    if(latlon.contains(LATLON_PREFS_VALUE))
    {
        System.out.println(" EXIST");
        String n = latlon.getString(LATLON_PREFS_VALUE, null);
        System.out.println("FDFDz"+n.toString());
//                                test_editor=test.edit();
//                                test_editor.remove(Test_PREFS_VALUE);
//                                test_editor.commit();
        try {
            JSONObject lat_lon_object = new JSONObject(n);

            JSONObject lat_lon_temp=new JSONObject();
            //lat_lon_temp.put("DID", txt_did);
            lat_lon_temp.put("LAT", latitude);
            lat_lon_temp.put("LON", longitude);
            lat_lon_temp.put("TSTAMP", date);
            JSONArray latlon_array = lat_lon_object.getJSONArray("LAT_LON");
            latlon_array.put(lat_lon_temp);
            System.out.println("FDFDz"+latlon_array.toString());




            lat_lon_object.put("LAT_LON", latlon_array);

            latlon=getSharedPreferences(LATLON_PREFS,0);
            latlon_editor=latlon.edit();

            latlon_editor.putString(LATLON_PREFS_VALUE, lat_lon_object.toString());
            System.out.println("LATLON"+lat_lon_object.toString());
            latlon_editor.commit();
            off_data=lat_lon_temp.toString();
            System.out.println("off_data"+off_data.toString());
            sea();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    else
    {
        System.out.println("DE");

        try {
            JSONObject lat_lon_object = new JSONObject();
            JSONObject lat_lon_temp = new JSONObject();
            //lat_lon_temp.put("DID", txt_did);
            lat_lon_temp.put("LAT", latitude);
            lat_lon_temp.put("LON", longitude);
            lat_lon_temp.put("TSTAMP", date);
            JSONArray latlon_array=new JSONArray();
            latlon_array.put(lat_lon_temp);
            lat_lon_object.put("LAT_LON", latlon_array);

            latlon_editor=latlon.edit();

            latlon_editor.putString(LATLON_PREFS_VALUE, lat_lon_object.toString());
            System.out.println("LATLON"+lat_lon_object.toString());
            latlon_editor.commit();
            off_data=lat_lon_temp.toString();
            System.out.println("off_data"+off_data.toString());
            sea();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}

    private void get_freq()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";


        frequency = getSharedPreferences(PREFS_FREQUENCY, Context.MODE_PRIVATE);
        text = frequency.getString(PREFS_TIME, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            new_freq="";
            new_freq="120000";

            frequency = getSharedPreferences(PREFS_FREQUENCY, 0); //1
            freq_editor = frequency.edit(); //2

            freq_editor.putString(PREFS_TIME, new_freq); //3
            value_freq=new_freq;
            freq_editor.commit();
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



    /*public Location getLocation() {
        try {
           LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d("Network", "NO network");
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, );
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                         locationz = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (locationz != null) {
                            zlatitude = locationz.getLatitude();
                            zlongitude = locationz.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (locationz == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                        Log.d("GPS Enabled", "GPS Enabled");

                        if (locationManager != null) {
                            locationz = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (locationz != null) {
                                System.out.println("HJGDHD"+zlatitude+"hjfg"+zlongitude);
                                zlatitude = locationz.getLatitude();
                                zlongitude = locationz.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return locationz;
    }*/


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
