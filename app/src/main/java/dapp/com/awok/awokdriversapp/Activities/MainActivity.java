package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dapp.com.awok.awokdriversapp.Adapters.OrderListViewAdapter;
import dapp.com.awok.awokdriversapp.Modals.Order;
import dapp.com.awok.awokdriversapp.R;
import dapp.com.awok.awokdriversapp.Services.FetchLocationCordinatesService;
import dapp.com.awok.awokdriversapp.Services.PushLocationDataToServerService;
import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;
import dapp.com.awok.awokdriversapp.Utils.JSONParser;
import dapp.com.awok.awokdriversapp.Utils.PullRefreshLayout;

/**
 * Created by shon on 4/6/2015.
 */
public class MainActivity extends Activity implements SearchView.OnQueryTextListener {
    private ListView orderListView;
    private OrderListViewAdapter orderListViewAdapter;
    RelativeLayout bodyRelativeLayout,headerRelativeLayout, mainRelativeLayout;
    public static final String PREFS_SWITCH = "APP_SWITCH";
    public static final String PREFS_SWITCH_VALUE = "APP_SWITCH_VALUE";
    SharedPreferences switch_prefs;
    SharedPreferences.Editor switch_editor;
    PullRefreshLayout pullRefreshLayout;
    public static final String PREFS_SWITCH_CALL = "APP_SWITCH_CALL";
    public static final String PREFS_SWITCH_VALUE_CALL = "APP_SWITCH_VALUE_CALL";
    SharedPreferences switch_prefs_call;
    SharedPreferences.Editor switch_editor_call;
    float x1,x2;
    float y1, y2;
    private List<Order> orderArrayList = new ArrayList<Order>();
    String data_name,data_date,data_img,txt_pass,txt_did,new_pass,res,txt_fname,txt_lname;
    private int swipe_Max_Distance = 350;
    private static String validate; //= "http://dev.alifca.com/d_login.php";
    private static String url; //= "http://dev.alifca.com/d_login.php";


    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_DID = "PREFS_APP_DID";
    public static final String APP_LOG_KEY = "PREFS_APP_KEY";
    public static final String APP_LOG_FNAME = "PREFS_APP_FNAME";
    public static final String APP_LOG_LNAME = "PREFS_APP_LNAME";
    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;
    JSONObject json;

    public static final String PREFS_NAME = "APP_PREFS";
    public static final String PREFS_SET = "APP_PREFS_SET";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText pass, confrmPass;
    Context context;
    TextView nameTextView, dateTextView, pleaseWaitTextView, noOrderTextView;
    CircleProgressBar progressView;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    ImageView barcodeImageView;
    SearchView searchView;
    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
    SharedPreferences server_pref;
    SharedPreferences.Editor serv_editor;
    String serv_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_list);
        // start tracing to "/sdcard/calc.trace"
//        Debug.startMethodTracing("calc");
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, Context.MODE_PRIVATE);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        if(serv_txt==null)
        {
            server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0); //1
            serv_editor = server_pref.edit(); //2
            serv_editor.putString(PREFS_SERVER_VALUE, "dev.alifca.com"); //3
            serv_editor.commit();
        }
        context=getApplicationContext();
        orderListView = (ListView) findViewById(R.id.orderListView);
        orderListViewAdapter = new OrderListViewAdapter(this, orderArrayList,context);
        orderListView.setAdapter(orderListViewAdapter);

        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        validate="http://"+serv_txt+"/d_login.php";
        url="http://"+serv_txt+"/d_login.php";

        pleaseWaitTextView =(TextView)findViewById(R.id.waitTextView);
        progressView =(CircleProgressBar)findViewById(R.id.progress1);
        progressView.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        progressView.setShowArrow(true);
        noOrderTextView =(TextView)findViewById(R.id.noOrderTextView);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        cd = new ConnectionDetector(MainActivity.this);
        bodyRelativeLayout =(RelativeLayout)findViewById(R.id.bodyRelativeLayout);
        headerRelativeLayout=(RelativeLayout)findViewById(R.id.headerRelativeLayout);
        barcodeImageView =(ImageView)findViewById(R.id.barcodeImageView);
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                new craz().execute();
            }
        });
        setup();
        mainRelativeLayout =(RelativeLayout)findViewById(R.id.mainRelativeLayout);
        mainRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchevent) {

                switch (touchevent.getAction())

                {
                    // when user first touches the screen we get x and y coordinate
                    case MotionEvent.ACTION_DOWN: {
                        x1 = touchevent.getX();
                        y1 = touchevent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        x2 = touchevent.getX();
                        y2 = touchevent.getY();
                        if (x1 < x2) {
                            //         Toast.makeText(MainActivity.this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        // if right to left sweep event on screen
                        if (x1 > x2) {
                            //         Toast.makeText(MainActivity.this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        // if UP to Down sweep event on screen
                        if (y1 < y2) {
                            //      Toast.makeText(MainActivity.this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        //     / /if Down to UP sweep event on screen
                        if (y1 > y2) {
                            //      Toast.makeText(MainActivity.this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP: {
                        get_data();
                        break;
                    }
                }
                return false;
            }
        });
        noOrderTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                get_data();
                return false;
            }
        });
        ImageView g=(ImageView)findViewById(R.id.imageView);
        nameTextView =(TextView)findViewById(R.id.nameTextView);
        dateTextView =(TextView)findViewById(R.id.dateTextView);
        orderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                get_data();
                return false;
            }
        });
        g.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                get_data();
                return false;
            }
        });
        orderListView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent touchevent) {

                switch (touchevent.getAction())

                {
                    // when user first touches the screen we get x and y coordinate
                    case MotionEvent.ACTION_DOWN: {
                        x1 = touchevent.getX();
                        y1 = touchevent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        x2 = touchevent.getX();
                        y2 = touchevent.getY();
                        if (x1 < x2) {
                            //      Toast.makeText(MainActivity.this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        // if right to left sweep event on screen
                        if (x1 > x2) {
                            //      Toast.makeText(MainActivity.this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        // if UP to Down sweep event on screen
                        if (y1 < y2) {
                            //     Toast.makeText(MainActivity.this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        //     / /if Down to UP sweep event on screen
                        if (y1 > y2) {
                            //    Toast.makeText(MainActivity.this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP: {
                        get_data();
                        break;
                    }
                }
                return false;
            }
        });

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long idz) {
            }
        });
        switch_v();
        switch_cal();
    }


    @Override
    protected void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // stop tracing
//        Debug.stopMethodTracing();
//    }

    private void switch_v() {
        switch_prefs = getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE);
        String val = switch_prefs.getString(PREFS_SWITCH_VALUE, null);
        System.out.println("PREF_switch" + val);

        if (val==null)
        {
            System.out.println("NUL:DDFF");
            switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
            switch_editor = switch_prefs.edit(); //2

            switch_editor.putString(PREFS_SWITCH_VALUE, "true"); //3


            switch_editor.commit();

        }
        else
        {
            System.out.println("NOT NULL");

        }
    }


    private void switch_cal() {
        switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz==null)
        {
            System.out.println("NUL:DDFF");
            switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, 0); //1
            switch_editor_call = switch_prefs_call.edit(); //2

            switch_editor_call.putString(PREFS_SWITCH_VALUE_CALL, "true"); //3


            switch_editor_call.commit();

        }
        else
        {
            System.out.println("NOT NULL");

        }
    }


    public void get_log_data()
    {
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
        txt_pass = login_prefs.getString(APP_LOG_KEY, null);
        txt_did = login_prefs.getString(APP_LOG_DID, null);
        txt_fname = login_prefs.getString(APP_LOG_FNAME, null);
        txt_lname = login_prefs.getString(APP_LOG_LNAME, null);
        if(txt_pass==null)
        {
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }
        else
        {
            isInternetPresent = cd.isConnectingToInternet();
            if(isInternetPresent) {
                new Validate().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please Connect to Data Services and Restart App", Toast.LENGTH_LONG).show();
            }
        }

        System.out.println("LOG"+txt_fname);
        System.out.println("LNAME"+txt_lname);
        System.out.println("DID"+txt_did);
        System.out.println("MD5"+txt_pass);

    }
    private void setup()
    {
        String text;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_SET, null);
        System.out.println("PREF" + text);
        if (text==null)
        {
            welcome_pass();
        }
        else
        {
            get_log_data();
        }
    }

    private void get_data()
    {
        String text;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_SET, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            new_pass();
        }
        else
        {
            set_log();
        }
    }


    /**OPENS DIALOG TO SET NEW ADMIN PASSWORD***/
    public void welcome_pass()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        confrmPass=(EditText)dialog.findViewById(R.id.confirm_password);
        pass.setHint("Enter Password");
        confrmPass.setHint("Confirm Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass="";
                new_pass=pass.getText().toString();
                if(new_pass.equals(""))
                {
                    pass.setHint("Please Enter New Password");
                }
                else if(confrmPass.getText().toString().equalsIgnoreCase(""))
                {
                    confrmPass.setHint("Please Confirm your Password");
                }
                else if(!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Your confirmed password does not match initially entered password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    settings = getSharedPreferences(PREFS_NAME, 0); //1
                    editor = settings.edit(); //2
                    editor.putString(PREFS_SET, new_pass); //3
                    editor.commit();
                    dialog.dismiss();
                    get_log_data();
                }


            }
        });
//        Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        dialog.show();
    }

/**OPENS DIALOG TO SET NEW ADMIN PASSWORD***/
    public void new_pass()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        confrmPass=(EditText)dialog.findViewById(R.id.confirm_password);
        pass.setHint("Enter Password");
        confrmPass.setHint("Confirm Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = "";
                new_pass = pass.getText().toString();
                if (new_pass.equals("")) {
                    pass.setHint("Please Enter New Password");
                } else if (confrmPass.getText().toString().equalsIgnoreCase("")) {
                    confrmPass.setHint("Please Confirm your Password");
                } else if (!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Your confirmed password does not match initially entered password", Toast.LENGTH_SHORT).show();
                } else {
                    settings = getSharedPreferences(PREFS_NAME, 0); //1
                    editor = settings.edit(); //2
                    editor.putString(PREFS_SET, new_pass); //3
                    editor.commit();
                    dialog.dismiss();
                }
            }
        });
        Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

/**OPENS SETTINGS DIALOG FOR PASSWORD****/
    public void set_log()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enter_admin_password);
        dialog.setCanceledOnTouchOutside(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("Enter Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass="";
                new_pass=pass.getText().toString();
                settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String next = settings.getString(PREFS_SET, null);
                System.out.println("PREF" + next);
                if (next.equals(new_pass))
                {
                    Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                    startActivity(i);
                    dialog.dismiss();
                }
                else
                {
                    pass.setText("");
                    pass.setHint("Wrong Password!! Try Again");
                }
            }
        });
        Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        searchView.clearFocus();
//        return true;
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        orderListViewAdapter.getFilter().filter(newText);
        return false;
    }
    private class Validate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            String c_did,c_key;
            JSONParser jParser=new JSONParser();
            login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            c_did = login_prefs.getString(APP_LOG_DID, null);
            c_key = login_prefs.getString(APP_LOG_KEY, null);
            System.out.println("key"+c_key);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            params.add(new BasicNameValuePair("action", "validate"));
            System.out.println(c_key);
            json = jParser.makeHttpRequest(validate, "POST", params);
    try {
        res = json.getString("key");
        System.out.println(res);
    } catch (JSONException e) {
        e.printStackTrace();
    }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(res.equals("true"))
            {
                startService(new Intent(MainActivity.this,FetchLocationCordinatesService.class));
                startService(new Intent(MainActivity.this,PushLocationDataToServerService.class));
                new detail().execute();
            }
            else if(res.equals("false"))
            {
                login_prefs_editor = login_prefs.edit(); //2
                login_prefs_editor.clear();
                login_prefs_editor.commit();
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Server Error , Please Try Again", Toast.LENGTH_LONG).show();

            }
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }




    private class detail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            Boolean del=true;
            Boolean pos=true;
            Boolean can=true;
            Boolean ma=true;
            JSONParser jParser = new JSONParser();
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("did",txt_did));
            params.add(new BasicNameValuePair("action", "list"));
            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));

            json = jParser.makeHttpRequest(url, "POST", params);
            System.out.println(json.toString());


            try {

                if(json.getString("ORDER_LIST").equals("nodata"))
                {
                    System.out.println("NO_DATA_hggnb");
                    data_name=json.getString("DRIVER_NAME");
                    data_date=json.getString("DATE");
                    data_img=json.getString("BARCODE");
                    System.out.println(data_name.toString());
                    System.out.println(data_date.toString());
                }
                else
                {
                    JSONArray ja=json.getJSONArray("ORDER_LIST");
                    System.out.println(ja.toString());
                    data_name=json.getString("DRIVER_NAME");
                    data_date=json.getString("DATE");
                    data_img=json.getString("BARCODE");
                    System.out.println(data_name.toString());
                    System.out.println(data_date.toString());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject g = ja.getJSONObject(i);
                        Order LIstDetails = new Order();
                        LIstDetails.setOrder_no(g.getString("ORDER_NUM"));
                        LIstDetails.setName(g.getString("USER_NAME"));
                        LIstDetails.setPhone(g.getString("PHONE"));
                        LIstDetails.setId(g.getString("ID"));
                        LIstDetails.setStatus(g.getString("ORDER_STATUS"));
                        if(g.getString("ORDER_STATUS").equals("F"))
                        {
                            if(del)
                            {
                                LIstDetails.setShow("true");
                                del=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }
                        if(g.getString("ORDER_STATUS").equals("O")) {
                            if(ma)
                            {
                                LIstDetails.setShow("true");
                                ma=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }
                        if(g.getString("ORDER_STATUS").equals("P"))
                        {
                            if(can)
                            {
                                LIstDetails.setShow("true");
                                can=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }

                        }
                        if(g.getString("ORDER_STATUS").equals("S"))
                        {
                            if(can)
                            {
                                LIstDetails.setShow("true");
                                can=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }

                        }
                        if (g.getString("ORDER_STATUS").equals("X")) {
                            if(pos)
                            {
                                LIstDetails.setShow("true");
                                pos=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }

                        System.out.println("gnvnvnvf" + g.getString("ORDER_STATUS"));

                        orderArrayList.add(LIstDetails);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if(json.getString("ORDER_LIST").equals("nodata"))
                {
                    nameTextView.setText(data_name.toString());
                    dateTextView.setText(data_date.toString());
                    byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    barcodeImageView.setImageBitmap(bitmap);
                    orderListView.setVisibility(View.GONE);
                    noOrderTextView.setVisibility(View.VISIBLE);
                }
                else
                {
                    nameTextView.setText(data_name.toString());
                    dateTextView.setText(data_date.toString());
                    System.out.println("jdhf"+data_img);
                    byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    barcodeImageView.setImageBitmap(bitmap);
                    orderListView.setVisibility(View.VISIBLE);
                    noOrderTextView.setVisibility(View.GONE);
                    orderListViewAdapter.notifyDataSetChanged();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            progressView.setVisibility(View.GONE);
            pleaseWaitTextView.setVisibility(View.GONE);
            bodyRelativeLayout.setVisibility(View.VISIBLE);
            headerRelativeLayout.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE );
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            progressView.setVisibility(View.VISIBLE);
            progressView.setShowArrow(true);
            pleaseWaitTextView.setVisibility(View.VISIBLE);
            bodyRelativeLayout.setVisibility(View.GONE);
            headerRelativeLayout.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            super.onPreExecute();
        }
    }

    private class craz extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            Boolean del=true;
            Boolean pos=true;
            Boolean can=true;
            Boolean ma=true;
            JSONParser jParser = new JSONParser();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did",txt_did));
            params.add(new BasicNameValuePair("action", "list"));
            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            json = jParser.makeHttpRequest(url, "POST", params);
            System.out.println(json.toString());
            try {

                if(json.getString("ORDER_LIST").equals("nodata"))
                {
                    System.out.println("NO_DATA_hggnb");
                    data_name=json.getString("DRIVER_NAME");
                    data_date=json.getString("DATE");
                    System.out.println(data_name.toString());
                    System.out.println(data_date.toString());
                }
                else
                {
                    JSONArray ja=json.getJSONArray("ORDER_LIST");
                    System.out.println(ja.toString());
                    orderListViewAdapter.clear();
                    data_name=json.getString("DRIVER_NAME");
                    data_date=json.getString("DATE");
                    System.out.println(data_name.toString());
                    System.out.println(data_date.toString());
                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject g = ja.getJSONObject(i);
                        Order LIstDetails = new Order();
                        LIstDetails.setOrder_no(g.getString("ORDER_NUM"));
                        LIstDetails.setName(g.getString("USER_NAME"));
                        LIstDetails.setPhone(g.getString("PHONE"));
                        LIstDetails.setId(g.getString("ID"));
                        LIstDetails.setStatus(g.getString("ORDER_STATUS"));
                        if(g.getString("ORDER_STATUS").equals("F"))
                        {
                            if(del)
                            {
                                LIstDetails.setShow("true");
                                del=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }
                        if(g.getString("ORDER_STATUS").equals("O")) {
                            if(ma)
                            {
                                LIstDetails.setShow("true");
                                ma=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }
                        if(g.getString("ORDER_STATUS").equals("P"))
                        {
                            if(can)
                            {
                                LIstDetails.setShow("true");
                                can=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }

                        }
                        if(g.getString("ORDER_STATUS").equals("S"))
                        {
                            if(can)
                            {
                                LIstDetails.setShow("true");
                                can=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }

                        }
                        if (g.getString("ORDER_STATUS").equals("X")) {
                            if(pos)
                            {
                                LIstDetails.setShow("true");
                                pos=false;
                            }
                            else
                            {
                                LIstDetails.setShow("false");
                            }
                        }
                        System.out.println("gnvnvnvf" + g.getString("ORDER_STATUS"));
                        orderArrayList.add(LIstDetails);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(json.getString("ORDER_LIST").equals("nodata"))
                {
                    nameTextView.setText(data_name.toString());
                    dateTextView.setText(data_date.toString());
                    orderListView.setVisibility(View.GONE);
                    noOrderTextView.setVisibility(View.VISIBLE);

                }
                else
                {
                    nameTextView.setText(data_name.toString());
                    dateTextView.setText(data_date.toString());
                    orderListView.setVisibility(View.VISIBLE);
                    noOrderTextView.setVisibility(View.GONE);

                    orderListViewAdapter.notifyDataSetChanged();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            pullRefreshLayout.setRefreshing(false);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}