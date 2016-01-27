package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import dapp.com.awok.awokdriversapp.Utils.JSONParser;
import dapp.com.awok.awokdriversapp.R;

/**
 * Created by shon on 5/2/2015.
 */
public class OrderDetailActivity extends Activity {
    List<String> reason=new ArrayList<String>();
    private static String url;
    TextView orderDateValueTextView, customerNameValueTextView, mobileValueTextView, emirateValueTextView, locationValueTextView, addressValueTextView, totalPriceValueTextView, deliveryPriceValueTextView, nameOnCardValueTextView, cardNumberValueTextView;
    String s_order_date,s_cus_name,s_mobile,s_emirates,s_location,s_address,s_total_price,s_del_price,s_name_card,s_card_no,s_delivery,s_paid,s_number_card,ca2;
    LinearLayout container,container_bundle,temp;
    String json,main_json;
    JSONArray order_list,cancel_list;
    String bu_namez,bu_name,bu_quantity,bu_price,order_main,dis_order_id;
    ImageView img_temp;
    TextView or_main,p;
    String ca_val;
    String store_phone,store_user;
    Button changeStatusButton,cal_log, callLogButton;

    public static final String PREFS_SWITCH_CALL = "APP_SWITCH_CALL";
    public static final String PREFS_SWITCH_VALUE_CALL = "APP_SWITCH_VALUE_CALL";
    SharedPreferences switch_prefs_call;
    String CALL_LOG_VALUE;
    ScrollView mainScrollView;
    LinearLayout footerLinearLayout;
    LinearLayout verificationLinearLayout;
    float x1,x2;
    float y1, y2;
    RelativeLayout mainRelativeLayout;
    CircleProgressBar progressView;
    String txc;
    TextView amountTextView;
    Button call_cus;
    public static final String PREFS_NAME = "APP_PREFS";
    public static final String PREFS_SET = "APP_PREFS_SET";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText pass;
    TextView conf;
    String new_pass;
    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
    SharedPreferences server_pref;
    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_DID = "PREFS_APP_DID";
    public static final String APP_LOG_KEY = "PREFS_APP_KEY";
    public static final String APP_LOG_FNAME = "PREFS_APP_FNAME";
    public static final String APP_LOG_LNAME = "PREFS_APP_LNAME";
    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;
    String serv_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_details);
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        //validate="http://"+serv_txt+"/d_login.php";
        url=serv_txt+"details/";
        Intent myIntent = getIntent();
        order_main = myIntent.getStringExtra("order_main");
        dis_order_id = myIntent.getStringExtra("display_order_id");
        System.out.println("order"+order_main);
        or_main=(TextView)findViewById(R.id.or_main);
        or_main.setText(dis_order_id);
        p=(TextView)findViewById(R.id.pw);
        ImageView bac=(ImageView)findViewById(R.id.bac);
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        amountTextView =(TextView)findViewById(R.id.textView2);
        mainScrollView =(ScrollView)findViewById(R.id.mainScrollView);
        footerLinearLayout =(LinearLayout)findViewById(R.id.footerLinearLayout);
        progressView =(CircleProgressBar)findViewById(R.id.progress1);
        progressView.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        progressView.setShowArrow(true);
        mainRelativeLayout =(RelativeLayout)findViewById(R.id.mainRelativeLayout);
        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
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


//                / /if left to right sweep event on screen


                      /*  int point=touchevent.getPointerCount();
                        System.out.println("gjhkghlkgjhl;fjlk"+point);*/


                        if (x1 < x2) {
                            //             Toast.makeText(OrderDetailActivity.this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();

                            //   get_data();


                        }

                        // if right to left sweep event on screen
                        if (x1 > x2) {
                            //               Toast.makeText(OrderDetailActivity.this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                        }

                        // if UP to Down sweep event on screen
                        if (y1 < y2) {
                            //              Toast.makeText(OrderDetailActivity.this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                        }

                        //     / /if Down to UP sweep event on screen
                        if (y1 > y2) {


                            ///return true;
                            //         Toast.makeText(OrderDetailActivity.this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                            /////////////// refreshContent();
                        }
                        break;
                    }
                 /*   case MotionEvent.ACTION_POINTER_UP:
                    {
                       // final float xDistance = Math.abs(touchevent.getX());
                        final float yDistance = Math.abs(y1);
                       // System.out.println("xdistance;fjlk" + xDistance);
                        System.out.println("ydistance;fjlk" + yDistance);
                        if (y1 > y2) {
                            int point = touchevent.getPointerCount();
                            if (point == 2) {
                                if (y1 > 1000) {
                                    System.out.println("gjhkghlkgjhl;fjlk" + y1);
                                    System.out.println("gjhkghlkgjhl;fjlk" + point);
                                    get_data();
                                }
                            }
                        }
                        break;
                    }*/
                    case MotionEvent.ACTION_POINTER_UP: {


                        get_data();

                        break;
                    }

                }

                return false;
            }
        });
        verificationLinearLayout =(LinearLayout)findViewById(R.id.verificationLinearLayout);
        nameOnCardValueTextView =(TextView)findViewById(R.id.nameOnCardValueTextView);
        cardNumberValueTextView =(TextView)findViewById(R.id.cardNumberValueTextView);
        callLogButton =(Button)findViewById(R.id.callLogButton);
        changeStatusButton =(Button)findViewById(R.id.changeStatusButton);
        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss = changeStatusButton.getText().toString();
                if (ss.equals("DELIVERED")) {

                } else if (ss.equals("CANCELED")) {

                } else if (ss.equals("POSTPONED")) {

                } else {
                    status();
                }
            }
        });
        orderDateValueTextView =(TextView)findViewById(R.id.orderDateValueTextView);
        customerNameValueTextView =(TextView)findViewById(R.id.customerNameValueTextView);
        mobileValueTextView =(TextView)findViewById(R.id.mobileValueTextView);
        emirateValueTextView =(TextView)findViewById(R.id.emirateValueTextView);
        locationValueTextView =(TextView)findViewById(R.id.locationValueTextView);
        addressValueTextView =(TextView)findViewById(R.id.addressValueTextView);
        totalPriceValueTextView =(TextView)findViewById(R.id.totalPriceValueTextView);
        deliveryPriceValueTextView =(TextView)findViewById(R.id.deliveryPriceValueTextView);
        nameOnCardValueTextView =(TextView)findViewById(R.id.nameOnCardValueTextView);
        cardNumberValueTextView =(TextView)findViewById(R.id.cardNumberValueTextView);

        cal_hide();
        call_cus=(Button)findViewById(R.id.cal_cus);
        call_cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CALL_LOG_VALUE=order_main;
                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
                store_phone=call_cus.getTag().toString();
                store_user= customerNameValueTextView.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + store_phone));
                startActivity(callIntent);
            }
        });


        cal_log=(Button)findViewById(R.id.callLogButton);
        cal_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(OrderDetailActivity.this, CallLogActivity.class);
                myIntent.putExtra("id_main",order_main);
                myIntent.putExtra("display_order_id",dis_order_id);
                startActivity(myIntent);
            }
        });


        container=(LinearLayout)findViewById(R.id.container);
        new details().execute();

    }

    public void cal_hide()
    {
        switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz.equals("true"))
        {
            callLogButton.setVisibility(View.VISIBLE);

        }
        else
        {
            callLogButton.setVisibility(View.GONE);
        }
    }


    private class details extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {

            String message = "";
            boolean status;
            JSONObject response;
            try {
                response = new JSONObject(s);
                status =  response.getBoolean("status");
                message = response.getString("message");
                if(status){
                    Toast.makeText(OrderDetailActivity.this, message, Toast.LENGTH_LONG).show();
                    JSONObject data =  response.getJSONObject("data");
//                    JSONObject orderObject = data.getJSONObject("orders");
//                    System.out.println(json.toString());
//                    System.out.println(main_json.toString());
                    order_list = data.getJSONArray("ORDER_LIST");
                    cancel_list=data.getJSONArray("CANCEL_REASON");
                    s_order_date = data.getString("ORDER_DATE");
                    s_cus_name = data.getString("ORDER_USER");
                    s_mobile = data.getString("PHONE");
                    s_emirates = data.getString("CITY");
                    s_location = data.getString("AREA");
                    s_address = data.getString("STREET");
                    s_total_price = data.getString("TOTAL_PRICE");
                    s_del_price = data.getString("DELIVERY_PRICE");
                    s_name_card = data.getString("ORDER_DATE");
                    s_card_no = data.getString("ORDER_DATE");
                    s_delivery=data.getString("STATUS_ID");
//                    s_paid=data.getString("CHECK_CARD");
                    //s_name_card=main_json.getString()
                    s_number_card=data.getString("PAYMENT_CARD");
                    ca2=data.getString("COLLECT_PRICE");


                    orderDateValueTextView.setText(s_order_date);
                    customerNameValueTextView.setText(s_cus_name);
                    mobileValueTextView.setText(s_mobile);
                    emirateValueTextView.setText(s_emirates);
                    if(s_location.equalsIgnoreCase("null")){
                        locationValueTextView.setText("No Address");
                    }
                    else{
                        locationValueTextView.setText(s_location);
                    }

                    addressValueTextView.setText(s_address);
                    totalPriceValueTextView.setText("Total Price : " + s_total_price + " AED");
                    if(s_del_price.equals("Free"))
                    {
                        deliveryPriceValueTextView.setText("Delivery Price : FREE");
                    }
                    else
                    {
                        deliveryPriceValueTextView.setText("Delivery Price : " + s_del_price + " AED");
                    }

                    nameOnCardValueTextView.setText(s_name_card);
                    cardNumberValueTextView.setText(s_card_no);
                    call_cus.setTag(s_mobile);
                    nameOnCardValueTextView.setText(s_cus_name);
                    cardNumberValueTextView.setText("xxxx xxxx xxxx " + s_number_card);

                    if(ca2.equals("0"))
                    {
                        amountTextView.setText("Order Amount Paid");
                    }
                    else
                    {
                        amountTextView.setText(ca2 + " AED/= NCND- D/O Return");
                    }

//                    if(s_paid.equals("N"))
//                    {
//                        verificationLinearLayout.setVisibility(View.GONE);
//                    }
//                    else
//                    {
//
//                    }

                    if(s_delivery.equals("F"))
                    {
                        changeStatusButton.setText("DELIVERED");
                        changeStatusButton.setBackgroundColor(Color.LTGRAY);
                        changeStatusButton.setEnabled(false);
                    }
                    else if(s_delivery.equals("P"))
                    {
                        changeStatusButton.setText("CANCELED");
                        changeStatusButton.setBackgroundColor(Color.parseColor("#e24545"));
                    }
                    else if(s_delivery.equals("S"))
                    {
                        changeStatusButton.setText("CANCELED");
                        changeStatusButton.setBackgroundColor(Color.parseColor("#e24545"));
                    }
                    else if(s_delivery.equals("X"))
                    {
                        changeStatusButton.setText("POSTPONED");
                        changeStatusButton.setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.holo_orange_dark));
                    }

                    for(int j=0;j<cancel_list.length();j++)
                    {
                        try{
                            reason.add(cancel_list.getString(j));
                            System.out.println("fhgkjghg" + cancel_list.toString());
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }

                    for (int i = 0; i < order_list.length(); i++) {

                        try {
                            JSONObject g = order_list.getJSONObject(i);
                            System.out.println("CHILD CHECK"+g.toString());
                            if(g.has("CHILD"))
                            {
                                System.out.println("CHILD");
                                JSONArray jarray=g.getJSONArray("CHILD");
                                LayoutInflater layoutInflater2 =
                                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View addView2 = layoutInflater2.inflate(R.layout.new_details_bundle, null);
                                bu_name=g.getString("NAME");
                                bu_quantity=g.getString("QUANTITY");
                                bu_price=g.getString("PRICE");
                                final TextView bu_na = (TextView) addView2.findViewById(R.id.item_name);
                                TextView bu_qu = (TextView) addView2.findViewById(R.id.order_quantity);
                                TextView bu_pr = (TextView) addView2.findViewById(R.id.price);
                                container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);
                                bu_na.setText(bu_name.toString());
                                bu_qu.setText(bu_quantity.toString());
                                bu_pr.setText(bu_price.toString());

                                for(int j=0;j<jarray.length();j++)
                                {
                                    JSONObject b_item=jarray.getJSONObject(j);
                                    bu_namez=b_item.getString("NAME");
                                    TextView tv=new TextView(OrderDetailActivity.this);
                                    tv.setText("- "+bu_namez);
                                    final ImageView vz=(ImageView)addView2.findViewById(R.id.ex);
                                    vz.setTag("plus");
                                    vz.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            container_bundle = (LinearLayout) addView2.findViewById(R.id.container_bundle);
                                            if (container_bundle.getVisibility() == View.VISIBLE) {
                                                container_bundle.setVisibility(View.GONE);
                                                vz.setImageResource(R.drawable.plus);
                                                vz.setTag("plus");
                                                temp = null;
                                                img_temp = vz;
                                                img_temp.setTag(vz.getTag());
                                            } else {
                                                container_bundle.setVisibility(View.VISIBLE);
                                                vz.setImageResource(R.drawable.minus);
                                                vz.setTag("minus");
                                                temp = container_bundle;
                                                img_temp = vz;
                                                img_temp.setTag(vz.getTag());
                                            }
                                        }
                                    });
                                    container_bundle.addView(tv);
                                }
                                container.addView(addView2);
                            }
                            else
                            {
                                System.out.println("NO CHILD");
                                bu_name = g.getString("NAME");
                                bu_quantity = g.getString("QUANTITY");
                                bu_price = g.getString("PRICE");
                                System.out.println("nameTextView" + bu_name.toString());
                                System.out.println("quantity" + bu_quantity.toString());
                                System.out.println("price" + bu_price.toString());
                                LayoutInflater layoutInflater =
                                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View addView = layoutInflater.inflate(R.layout.new_details_bundle, null);
                                LinearLayout bundle=(LinearLayout)addView.findViewById(R.id.bundle);
                                bundle.setVisibility(View.GONE);
                                TextView nz = (TextView) addView.findViewById(R.id.item_name);
                                TextView qu = (TextView) addView.findViewById(R.id.order_quantity);
                                TextView pr = (TextView) addView.findViewById(R.id.price);
                                nz.setText(bu_name.toString());
                                qu.setText(bu_quantity.toString());
                                pr.setText(bu_price.toString());
                                container.addView(addView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
                else{
                    Toast.makeText(OrderDetailActivity.this, message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            mainScrollView.setVisibility(View.VISIBLE);
            footerLinearLayout.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... paramz) {
            JSONParser jParser = new JSONParser();
            login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            String c_did = login_prefs.getString(APP_LOG_DID, null);
            String c_key = login_prefs.getString(APP_LOG_KEY, null);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
//            params.add(new BasicNameValuePair("did", "546457"));
//            params.add(new BasicNameValuePair("key", "gvJeU4Dh9372b18b77b7c704343fc55b2be4577c"));
//            params.add(new BasicNameValuePair("oid",order_main));
            params.add(new BasicNameValuePair("oid","404784147"));
            json = jParser.makeHttpRequest(url, "GET", params);
//            System.out.println(json.toString());

            return json;
        }
    }

    public void status()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.status);
        dialog.setCanceledOnTouchOutside(false);
        Button b1=(Button)dialog.findViewById(R.id.button);
        Button b2=(Button)dialog.findViewById(R.id.button2);
        Button b3=(Button)dialog.findViewById(R.id.vv);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DELIVERED");
                welcome_del();
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("POSTPONED");
                //new pc().execute();
                welcome_pos();
                dialog.dismiss();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CANCELED");
                // new pc().execute();
                //welcome_canceled();
                can_sp();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void can_sp() {
        final Dialog dialog = new Dialog(OrderDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner s=(Spinner)dialog.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(OrderDetailActivity.this,android.R.layout.simple_spinner_item, reason);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(s.getSelectedItem().toString());
                ca_val=s.getSelectedItem().toString();
                new can_spinner().execute();
                dialog.dismiss();
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


    private class del extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {

            if(txc.equals("delivered"))
            {
                changeStatusButton.setText("DELIVERED");
                changeStatusButton.setBackgroundColor(Color.LTGRAY);
                changeStatusButton.setEnabled(false);
            }
            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            progressView.bringToFront();
            p.bringToFront();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("order_id",order_main));
            params2.add(new BasicNameValuePair("action", "delivered"));
            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            json = jParser.makeHttpRequest(url, "POST", params2);
//            try {
//                txc=json.getString("key");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return null;
        }
    }





    private class pc extends AsyncTask<String, Void, String> {

        @Override

        protected void onPostExecute(String s) {

            if(txc.equals("pleaseWaitTextView"))
            {
                changeStatusButton.setText("pleaseWaitTextView");
                changeStatusButton.setText("POSTPONED");
                changeStatusButton.setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.holo_orange_dark));
            }
            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            progressView.bringToFront();
            p.bringToFront();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("action", "postponed"));
            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            json = jParser.makeHttpRequest(url, "POST", params2);
            System.out.println("POST"+json);

//            try {
//                txc=json.getString("key");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return null;
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





    public void new_pass()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.setCanceledOnTouchOutside(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("Please Enter New Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass="";
                new_pass=pass.getText().toString();
                settings = getSharedPreferences(PREFS_NAME, 0); //1
                editor = settings.edit(); //2
                editor.putString(PREFS_SET, new_pass); //3
                editor.commit();
                dialog.dismiss();
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

    public void set_log()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
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
                    Intent i=new Intent(OrderDetailActivity.this,SettingsActivity.class);
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



    public void welcome_del()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm);
        dialog.setCanceledOnTouchOutside(false);
        conf=(TextView)dialog.findViewById(R.id.edit_pass);
        conf.setText("Are you sure order is Delivered ?");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new del().execute();
                dialog.dismiss();
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




    public void welcome_pos()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm);
        dialog.setCanceledOnTouchOutside(false);
        conf=(TextView)dialog.findViewById(R.id.edit_pass);
        conf.setText("Are you sure order is Postponed ?");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new pc().execute();
                dialog.dismiss();
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



    private class can_spinner extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {

            if(txc.equals("c"))
            {
                changeStatusButton.setText("CANCELED");
                changeStatusButton.setBackgroundColor(Color.parseColor("#e24545"));
            }

            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);

            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            progressView.bringToFront();
            p.bringToFront();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("action", "cancel"));
            params2.add(new BasicNameValuePair("can_val", ca_val));
            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
            json = jParser.makeHttpRequest(url, "POST", params2);
//            try {
//                txc=json.getString("key");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return null;
        }
    }

}
