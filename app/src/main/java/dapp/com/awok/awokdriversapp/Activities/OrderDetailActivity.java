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
    TextView order_date,cus_name,mobile,emirates,location,address,total_price,del_price,name_card,card_no;
    String s_order_date,s_cus_name,s_mobile,s_emirates,s_location,s_address,s_total_price,s_del_price,s_name_card,s_card_no,s_delivery,s_paid,s_number_card,ca2;
    LinearLayout container,container_bundle,temp;
    JSONObject json,main_json;
    JSONArray order_list,cancel_list;
    String bu_namez,bu_name,bu_quantity,bu_price,order_main,dis_order_id;
    ImageView img_temp;
    TextView or_main,p;
    String ca_val;
    String store_phone,store_user;
    Button c_status,cal_log,cal_hide;

    public static final String PREFS_SWITCH_CALL = "APP_SWITCH_CALL";
    public static final String PREFS_SWITCH_VALUE_CALL = "APP_SWITCH_VALUE_CALL";
    SharedPreferences switch_prefs_call;
    String CALL_LOG_VALUE;
    ScrollView sv;
    LinearLayout o;
    LinearLayout cardz;
    float x1,x2;
    float y1, y2;
    ScrollView s1;
    RelativeLayout r2;
    CircleProgressBar p1;
    String txc;
    TextView ca;
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
    String serv_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_details);
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        //validate="http://"+serv_txt+"/d_login.php";
        url="http://"+serv_txt+"/d_login.php";
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
        ca=(TextView)findViewById(R.id.textView2);
        sv=(ScrollView)findViewById(R.id.s_main);
        o=(LinearLayout)findViewById(R.id.o);
        p1=(CircleProgressBar)findViewById(R.id.progress1);
        p1.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        p1.setShowArrow(true);
        r2=(RelativeLayout)findViewById(R.id.main_details);
        s1=(ScrollView)findViewById(R.id.s_main);
        s1.setOnTouchListener(new View.OnTouchListener() {
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
        cardz=(LinearLayout)findViewById(R.id.cardz);
        name_card=(TextView)findViewById(R.id.name_card);
        card_no=(TextView)findViewById(R.id.card_no);
        cal_hide=(Button)findViewById(R.id.call_log);
        c_status=(Button)findViewById(R.id.c_status);
        c_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss=c_status.getText().toString();
                if(ss.equals("DELIVERED"))
                {

                }
               else if(ss.equals("CANCELED"))
                {

                }
               else if(ss.equals("POSTPONED"))
                {

                }
                else {
                    status();
                }
            }
        });
        order_date=(TextView)findViewById(R.id.order_date);
        cus_name=(TextView)findViewById(R.id.customer_name);
        mobile=(TextView)findViewById(R.id.mobile);
        emirates=(TextView)findViewById(R.id.emirates);
        location=(TextView)findViewById(R.id.location);
        address=(TextView)findViewById(R.id.address);
        total_price=(TextView)findViewById(R.id.total_price);
        del_price=(TextView)findViewById(R.id.del_price);
        name_card=(TextView)findViewById(R.id.name_card);
        card_no=(TextView)findViewById(R.id.card_no);

cal_hide();
call_cus=(Button)findViewById(R.id.cal_cus);
        call_cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CALL_LOG_VALUE=order_main;
                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
                store_phone=call_cus.getTag().toString();
                store_user=cus_name.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
               // Intent callIntent = new Intent(Intent.ACTION_CALL);
                //callIntent.setData(Uri.parse("tel:" + cal.getTag()));
               // callIntent.setData(Uri.parse("tel:" + call_cus.getTag()));
                callIntent.setData(Uri.parse("tel:" + store_phone));
                //'callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(callIntent);
            }
        });


        cal_log=(Button)findViewById(R.id.call_log);
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
        ///////////////pDialog = new ProgressDialog(OrderDetailActivity.this);
        new details().execute();

    }

public void cal_hide()
    {
        switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz.equals("true"))
        {
            cal_hide.setVisibility(View.VISIBLE);

        }
        else
        {
            cal_hide.setVisibility(View.GONE);
        }
    }


    private class details extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            try {

                if (json.equals("")) {
                  //  r.setVisibility(View.GONE);
                  /*  b_order_id.setText("ORDER ID : NULL");
                    b_order_date.setText("ORDER DATE : NULL");
                    b_city.setText("CITY : NULL");
                    b_items_price.setText("ITEM PRICE : NULL");
                    b_del_price.setText("DELIVERY PRICE : NULL");
                    b_street.setText("STREET : NULL");
                    b_del_time.setText("DELIVERY TIME : NULL");
                    b_building.setText("BUILDING : NULL");
                    b_area.setText("AREA : NULL");
                    b_order_user.setText("NAME : NULL");
                    b_tot_price.setText("TOTAL PRICE : NULL");*/

                }
                else {
//                    if(order_id.equals("null"))
//                    {
//                        b_order_id.setVisibility(View.GONE);
//                    }
//                                        else
//                    {
//                        b_order_id.setText("ORDER ID : " + order_id.toString());
//                    }

                 /*   if(order_date.equals("null"))
                    {
                        b_order_date.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_order_date.setText("ORDER DATE : " + order_date.toString());
                    }
                    if(city.equals("null"))
                    {
                        b_city.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_city.setText("CITY : " + city.toString());
                    }

                    *//*if(items_price.equals("null"))
                    {
                        b_items_price.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_items_price.setText("ITEM PRICE : " + items_price.toString());
                    }*//*

                    if(del_price.equals("null"))
                    {
                        b_del_price.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_del_price.setText("DELIVERY PRICE : " + del_price.toString());
                    }

                    if(street.equals("null"))
                    {
                        b_street.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_street.setText("STREET : " + street.toString());
                    }

                    if(del_time.equals("null"))
                    {
                        b_del_time.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_del_time.setText("DELIVERY TIME : " + del_time.toString());
                    }

                    if(building.equals("null"))
                    {
                        b_building.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_building.setText("BUILDING : " + building.toString());
                    }


                    if(area.equals("null"))
                    {
                        b_area.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_area.setText("AREA : " + area.toString());
                    }





                    if(order_user.equals("null"))
                    {
                        b_order_user.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_order_user.setText("NAME : " + order_user.toString());
                    }


                    if(tot_price.equals("null"))
                    {
                        b_tot_price.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_tot_price.setText("TOTAL PRICE : " + tot_price.toString());
                    }




                    if(phone.equals("null"))
                    {
                        b_phone.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_phone.setText("PHONE : "+ phone.toString());
                    }






                    if(pay_method.equals("null"))
                    {
                        b_pay_method.setVisibility(View.GONE);
                    }
                    else
                    {
                        b_pay_method.setText("PAYMENT TYPE : "+pay_method.toString());
                    }

                    if(del.equals("F"))
                    {
                        delivered.setVisibility(View.GONE);
                    }
                    else
                    {

                    }*/
         /*   b_name.setText("PRODUCT NAME : "+nameTextView.toString());
            b_quantity.setText("QUANTITY : "+quantity.toString());
            b_price.setText("PRICE : "+price.toString());*/



                    order_date.setText(s_order_date);
                    cus_name.setText(s_cus_name);
                    mobile.setText(s_mobile);
                    emirates.setText(s_emirates);
                    location.setText(s_location);
                    address.setText(s_address);
                    total_price.setText("Total Price : "+s_total_price+" AED");
                    if(s_del_price.equals("Free"))
                    {
                        del_price.setText("Delivery Price : 0 AED");
                    }
                    else
                    {
                        del_price.setText("Delivery Price : "+s_del_price+" AED");
                    }

                    name_card.setText(s_name_card);
                    card_no.setText(s_card_no);
                    call_cus.setTag(s_mobile);
                    name_card.setText(s_cus_name);
                    card_no.setText("xxxx xxxx xxxx "+s_number_card);

                    if(ca2.equals("0"))
                    {
                        ca.setText("Order Amount Paid");
                    }
                    else
                    {
                        ca.setText(ca2+" AED/= NCND- D/O Return");
                    }

                    if(s_paid.equals("N"))
                    {
cardz.setVisibility(View.GONE);
                    }
                    else
                    {

                    }

                    if(s_delivery.equals("F"))
                    {
                        c_status.setText("DELIVERED");

                        int imgResource = R.drawable.delivered;
                        //c_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        c_status.setBackgroundColor(Color.LTGRAY);
                        c_status.setEnabled(false);
                        //////////call_cus.setBackgroundColor(Color.DKGRAY);
                        //////call_cus.setEnabled(false);
                        //call_cus.setVisibility(View.GONE);
                    }
                    else if(s_delivery.equals("P"))
                    {
                        c_status.setText("CANCELED");
                        c_status.setBackgroundColor(Color.parseColor("#e24545"));
                    }
                    else if(s_delivery.equals("S"))
                    {
                        c_status.setText("CANCELED");
                        c_status.setBackgroundColor(Color.parseColor("#e24545"));
                    }
                    else if(s_delivery.equals("X"))
                    {
                        c_status.setText("POSTPONED");
                        c_status.setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.holo_orange_dark));
                    }



for(int j=0;j<cancel_list.length();j++)
{
    try{
        //JSONObject cc = order_list.getJSONObject(j);
        //reason[j]= cancel_list.getString(j);
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
                                //  container_bundle.setVisibility(View.VISIBLE);
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
                                           /* if(vz.getTag()==null)
                                            {
                                                vz.setTag("plus");
                                                System.out.println("plus");
                                            }

if(vz.getTag().equals("plus"))
{
    container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);
    if(temp==null)
    {
        System.out.println("nulllllll");
    }
    else {
        if (temp.getVisibility() == View.VISIBLE) {

            temp.setVisibility(View.GONE);
            img_temp.setImageResource(R.drawable.plus);

        } else {

        }
    }
        System.out.println("plus1");

        System.out.println("plus1");
        if (container_bundle.getVisibility() == View.VISIBLE) {

            container_bundle.setVisibility(View.GONE);
            vz.setImageResource(R.drawable.plus);
            vz.setTag("plus");
            System.out.println("plus");

        } else {

            container_bundle.setVisibility(View.VISIBLE);
            vz.setImageResource(R.drawable.minus);
            vz.setTag("minus");
            System.out.println("minus");
        }

        temp = container_bundle;
        img_temp=vz;
        img_temp.setTag(vz.getTag());


}
   else
{
    container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);
  //  container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);
    if (container_bundle.getVisibility() == View.VISIBLE) {

        container_bundle.setVisibility(View.GONE);
        vz.setImageResource(R.drawable.plus);
        vz.setTag("plus");

    } else {

        container_bundle.setVisibility(View.VISIBLE);
        vz.setImageResource(R.drawable.minus);
        vz.setTag("minus");
    }
    temp = container_bundle;
    img_temp=vz;
    img_temp.setTag(vz.getTag());
}*/
/*
                                          *//*  if (v.container_bundle.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                container_bundle.setVisibility(View.GONE);
                                            } else {
                                                // Either gone or invisible
                                                container_bundle.setVisibility(View.VISIBLE);
                                            }
                                            System.out.println(v.getId());*//*
                                            if(temp==null)
                                            {
                                                System.out.println("nulllllll");
                                            }
                                            else
                                            {
                                                System.out.println("not nuylllllllllllll");
                                                System.out.println("not nuylllllllllllll"+img_temp.getTag());
                                                if(img_temp.getTag().equals("minus"))
                                                {
                                                    if (temp.getVisibility() == View.VISIBLE) {
                                                        // Its visible
                                                        temp.setVisibility(View.GONE);
                                                        img_temp.setImageResource(R.drawable.plus);
                                                        System.out.println("TEMP IDuuuu"+temp.getId());
                                                    } else {
                                                        // Either gone or invisible
                                                        // temp.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                         *//*   if (temp.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                temp.setVisibility(View.GONE);
                                            } else {
                                                // Either gone or invisible
                                                temp.setVisibility(View.VISIBLE);
                                            }
                                            System.out.println("gfhf"+bu_na.getText());
                                            LinearLayout barcodeImageView=(LinearLayout)v.findViewById(R.id.container_bundle);*//*
                                            container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);

                                            if (container_bundle.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                container_bundle.setVisibility(View.GONE);
                                                vz.setImageResource(R.drawable.plus);
                                                vz.setTag("plus");

                                            } else {
                                                // Either gone or invisible
                                                container_bundle.setVisibility(View.VISIBLE);
                                                vz.setImageResource(R.drawable.minus);
                                                vz.setTag("minus");
                                            }

                                            temp = container_bundle;
                                            img_temp=vz;
                                            img_temp.setTag(vz.getTag());
                                            System.out.println("TEMP ID"+temp.getId());
                                            System.out.println("TEMP ID"+container_bundle.getId());*/




                                            /*if(temp==null)
                                            {
                                                System.out.println("nulllllll");
                                            }
                                            else
                                            {
                                                System.out.println("not nuylllllllllllll");
                                                System.out.println("not nuylllllllllllll"+img_temp.getTag());
                                                if(img_temp.getTag().equals("minus"))
                                                {
                                                    if (temp.getVisibility() == View.VISIBLE) {
                                                        // Its visible
                                                        temp.setVisibility(View.GONE);
                                                        img_temp.setImageResource(R.drawable.plus);
                                                        System.out.println("TEMP IDuuuu"+temp.getId());
                                                    } else {

                                                    }
                                                }

                                            }

                                            container_bundle=(LinearLayout)addView2.findViewById(R.id.container_bundle);

                                            if (container_bundle.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                container_bundle.setVisibility(View.GONE);
                                                vz.setImageResource(R.drawable.plus);
                                                vz.setTag("plus");

                                            } else {
                                                // Either gone or invisible
                                                container_bundle.setVisibility(View.VISIBLE);
                                                vz.setImageResource(R.drawable.minus);
                                                vz.setTag("minus");
                                            }
                                            if(temp==null)
                                            {
                                                temp = container_bundle;
                                                img_temp = vz;
                                                img_temp.setTag(vz.getTag());
                                            }
                                            else {
                                                String one = container_bundle.toString();
                                                String two = temp.toString();
                                                if (one.equals(two)) {
                                                    temp = null;
                                                } else

                                                {
                                                    temp = container_bundle;
                                                    img_temp = vz;
                                                    img_temp.setTag(vz.getTag());
                                                }
                  if(vz.getTag()==null)
                                            {
                                                vz.setTag("plus");
                                                System.out.println("plus");
                                            }
                                                                      }*/

                                          /*  if(vz.getTag()==null)
                                            {
                                                vz.setTag("plus");
                                                System.out.println("plus");
                                            }*/
/*if(vz.getTag().equals("minus"))
{
    if (container_bundle.getVisibility() == View.VISIBLE) {
        // Its visible
        container_bundle.setVisibility(View.GONE);
        vz.setImageResource(R.drawable.plus);
        vz.setTag("plus");
        temp = null;
        img_temp = vz;
        img_temp.setTag(vz.getTag());


    }
}
                                            else {
    if (temp == null) {

    } else {
        temp.setVisibility(View.GONE);
        img_temp.setImageResource(R.drawable.plus);
    }
    container_bundle = (LinearLayout) addView2.findViewById(R.id.container_bundle);

    if (container_bundle.getVisibility() == View.VISIBLE) {
        // Its visible
        container_bundle.setVisibility(View.GONE);
        vz.setImageResource(R.drawable.plus);
        vz.setTag("plus");
        temp = null;
        img_temp = vz;
        img_temp.setTag(vz.getTag());


    } else {
        // Either gone or invisible
        container_bundle.setVisibility(View.VISIBLE);
        vz.setImageResource(R.drawable.minus);
        vz.setTag("minus");
        temp = container_bundle;
        img_temp = vz;
        img_temp.setTag(vz.getTag());

    }
}*/
                                            container_bundle = (LinearLayout) addView2.findViewById(R.id.container_bundle);
                                            if (container_bundle.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                container_bundle.setVisibility(View.GONE);
                                                vz.setImageResource(R.drawable.plus);
                                                vz.setTag("plus");
                                                temp = null;
                                                img_temp = vz;
                                                img_temp.setTag(vz.getTag());


                                            } else {
                                                // Either gone or invisible
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
                                // Button tz = (Button) addView.findViewById(R.id.item);
                                LinearLayout bundle=(LinearLayout)addView.findViewById(R.id.bundle);
                                bundle.setVisibility(View.GONE);
                                TextView nz = (TextView) addView.findViewById(R.id.item_name);
                                TextView qu = (TextView) addView.findViewById(R.id.order_quantity);
                                TextView pr = (TextView) addView.findViewById(R.id.price);
                                // int countz = barcodeImageView + 1;
                                // tz.setText("Item Number " + countz);
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
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        /*    if (pDialog.isShowing())
                pDialog.dismiss();*/
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            o.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            /////////////pDialog.show();
            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... paramz) {
            JSONParser jParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("order_id",order_main));
            params.add(new BasicNameValuePair("action", "details"));
            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
/*3463*//*404273461*/
            json = jParser.makeHttpRequest(url, "POST", params);
            System.out.println(json.toString());


            if (json.equals("")) {

            } else {
                try {
                    main_json = json.getJSONObject("orders");


                    System.out.println(json.toString());
                    System.out.println(main_json.toString());




                    order_list = main_json.getJSONArray("ORDER_LIST");
                    cancel_list=main_json.getJSONArray("CANCEL_REASON");
                    s_order_date = main_json.getString("ORDER_DATE");
                    s_cus_name = main_json.getString("ORDER_USER");
                    s_mobile = main_json.getString("PHONE");
                    s_emirates = main_json.getString("CITY");
                    s_location = main_json.getString("AREA");
                    s_address = main_json.getString("STREET");
                    s_total_price = main_json.getString("TOTAL_PRICE");
                    s_del_price = main_json.getString("DELIVERY_PRICE");
                    s_name_card = main_json.getString("ORDER_DATE");
                    s_card_no = main_json.getString("ORDER_DATE");
                    s_delivery=main_json.getString("STATUS_ID");
                    s_paid=main_json.getString("CHECK_CARD");
                    //s_name_card=main_json.getString()
                    s_number_card=main_json.getString("PAYMENT_CARD");
                    ca2=main_json.getString("COLLECT_PRICE");












                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return null;

    }

    }


/*
    public void terra()
    {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        final TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //yout method here
                            System.out.println("start");
                            getData();
                            System.out.println("getdata");
                            store();
                            System.out.println("store");
                            timer.cancel();
                            System.out.println(" end");
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 5000);
    }


    private void store()
    {


        call_log = this.getSharedPreferences(CallLog, Context.MODE_APPEND);

        *//*timez=data_date+" "+timez.toString();*//*
        timez=strDate+" "+time.toString();
        System.out.println(" fh"+timez);
        if(call_log.contains(CALL_LOG_VALUE))
        {
            System.out.println(" EXIST");
            String n = call_log.getString(CALL_LOG_VALUE, null);
            System.out.println("FDFDz"+n.toString());
//                                test_editor=test.edit();
//                                test_editor.remove(Test_PREFS_VALUE);
//                                test_editor.commit();
            try {
                JSONObject lat_lon_object = new JSONObject(n);

                JSONObject lat_lon_temp=new JSONObject();
                //lat_lon_temp.put("DID", txt_did);
                // lat_lon_temp.put("DATE", data_date);
                // lat_lon_temp.put("DRIVER_NAME", data_name);
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array = lat_lon_object.getJSONArray("CallLog");
                latlon_array.put(lat_lon_temp);
                System.out.println("FDFDz"+latlon_array.toString());




                lat_lon_object.put("CALL_LOG", latlon_array);

                *//*call_log=context.getSharedPreferences(CALL_LOG_VALUE, 0);
                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();*//*

                call_log_value=call_log.edit();
                call_log_value.clear();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();

                get_data();
                *//*off_data=lat_lon_temp.toString();
                System.out.println("off_data"+off_data.toString());
                sea();*//*

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
                //lat_lon_temp.put("DATE", data_date);
                //lat_lon_temp.put("DRIVER_NAME", data_name);
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array=new JSONArray();
                latlon_array.put(lat_lon_temp);
                lat_lon_object.put("CALL_LOG", latlon_array);

                call_log_value=call_log.edit();
                call_log_value.clear();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();

                get_data();

               *//* off_data=lat_lon_temp.toString();
                System.out.println("off_data"+off_data.toString());
                sea();*//*

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
       // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void getData() {

        CursorLoader cursorLoader = new CursorLoader(this,
                CallLog.Calls.CONTENT_URI, null, null, null, null);
        Cursor managedCursor = cursorLoader.loadInBackground();


        int durationIndex = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int numberIndex = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int typeIndex = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int dateIndex = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        managedCursor.moveToFirst();

        String duration =managedCursor.getString(durationIndex);
        store_duration=duration;
        String number =managedCursor.getString(numberIndex);
        String type =managedCursor.getString(typeIndex);
        String dateTextView =managedCursor.getString(dateIndex);
        Date callDate = new Date(Long.valueOf(dateTextView));
        time=new Time(Long.valueOf(callDate.getTime()));
        Date dz=new Date(Long.valueOf(callDate.getDate()));

//
        System.out.println("time"+time+"dateTextView"+dz);
        managedCursor.close();
        System.out.println("duration" + duration + "number" + number + "type" + type + "dateTextView" + callDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyy");
        strDate = sdf.format(c.getTime());
        System.out.println("strDate"+strDate);
    }



    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.barcodeImageView(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.barcodeImageView(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.barcodeImageView(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.barcodeImageView(LOG_TAG, "restart app");



                    isPhoneCalling = false;
                    terra();
                    //ran();
                }

            }

        }

    }*/



   /* private void get_data()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";
        System.out.println("callCALL_LOG_VALUE" + CALL_LOG_VALUE);

        call_log = this.getSharedPreferences(CallLog, Context.MODE_APPEND);
        text = call_log.getString(CALL_LOG_VALUE, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            System.out.println("NO");
        }
        else
        {
            System.out.println("NO" + text);
        }
    }*/


  /*  public void terra()
    {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        final TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //yout method here
                            System.out.println("start");
                            getData();
                            System.out.println("getdata");
                            store();
                            System.out.println("store");
                            timer.cancel();
                            System.out.println(" end");
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 5000);
    }


    private void store()
    {


        call_log = getSharedPreferences(CallLog, Context.MODE_PRIVATE);

        *//*timez=data_date+" "+timez.toString();*//*
        timez=strDate+" "+time.toString();
        System.out.println(" fh"+timez);
        if(call_log.contains(CALL_LOG_VALUE))
        {
            System.out.println(" EXIST");
            String n = call_log.getString(CALL_LOG_VALUE, null);
            System.out.println("FDFDz"+n.toString());
//                                test_editor=test.edit();
//                                test_editor.remove(Test_PREFS_VALUE);
//                                test_editor.commit();
            try {
                JSONObject lat_lon_object = new JSONObject(n);

                JSONObject lat_lon_temp=new JSONObject();
                //lat_lon_temp.put("DID", txt_did);
                // lat_lon_temp.put("DATE", data_date);
                // lat_lon_temp.put("DRIVER_NAME", data_name);
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array = lat_lon_object.getJSONArray("CALL_LOG");
                latlon_array.put(lat_lon_temp);
                System.out.println("FDFDz"+latlon_array.toString());




                lat_lon_object.put("CALL_LOG", latlon_array);

                *//*call_log=context.getSharedPreferences(CALL_LOG_VALUE, 0);
                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();*//*

                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                //call_log_value.clear();
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();

                /////////////get_data();
                *//*off_data=lat_lon_temp.toString();
                System.out.println("off_data"+off_data.toString());
                sea();*//*

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
                //lat_lon_temp.put("DATE", data_date);
                //lat_lon_temp.put("DRIVER_NAME", data_name);
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array=new JSONArray();
                latlon_array.put(lat_lon_temp);
                lat_lon_object.put("CallLog", latlon_array);

                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                //call_log_value.clear();
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();

            //////////    get_data();

               *//* off_data=lat_lon_temp.toString();
                System.out.println("off_data"+off_data.toString());
                sea();*//*

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        *//*activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*//*
    }

    private void getData() {

        CursorLoader cursorLoader = new CursorLoader(this,
                CallLog.Calls.CONTENT_URI, null, null, null, null);
        Cursor managedCursor = cursorLoader.loadInBackground();


        int durationIndex = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int numberIndex = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int typeIndex = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int dateIndex = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        managedCursor.moveToFirst();

        String duration =managedCursor.getString(durationIndex);
        store_duration=duration;
        String number =managedCursor.getString(numberIndex);
        String type =managedCursor.getString(typeIndex);
        String dateTextView =managedCursor.getString(dateIndex);
        Date callDate = new Date(Long.valueOf(dateTextView));
        time=new Time(Long.valueOf(callDate.getTime()));
        Date dz=new Date(Long.valueOf(callDate.getDate()));

//
        System.out.println("time"+time+"dateTextView"+dz);
        managedCursor.close();
        System.out.println("duration" + duration + "number" + number + "type" + type + "dateTextView" + callDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyy");
        strDate = sdf.format(c.getTime());
        System.out.println("strDate"+strDate);
    }



    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.barcodeImageView(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.barcodeImageView(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.barcodeImageView(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.barcodeImageView(LOG_TAG, "restart app");



                    isPhoneCalling = false;
                    terra();
                    //ran();
                }

            }

        }

    }*/



    /*private void get_data()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";
        System.out.println("callCALL_LOG_VALUE" + CALL_LOG_VALUE);

        call_log = getSharedPreferences(CallLog, Context.MODE_APPEND);
        text = call_log.getString(CALL_LOG_VALUE, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            System.out.println("NO");
        }
        else
        {
            System.out.println("NO" + text);
        }
    }*/


    public void status()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.status);
        //dialog.setTitle("Keyword To Block");

        dialog.setCanceledOnTouchOutside(false);

       Button b1=(Button)dialog.findViewById(R.id.button);
        Button b2=(Button)dialog.findViewById(R.id.button2);
        Button b3=(Button)dialog.findViewById(R.id.vv);







        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DELIVERED");
                //new del().execute();
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
        //dialog.setTitle("Keyword To Block");

        dialog.setCanceledOnTouchOutside(false);

        //TextView conf=(TextView)dialog.findViewById(R.id.edit_pass);
        //conf.setText("Are you sure order is Postponed ?");
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
                c_status.setText("DELIVERED");

                //int imgResource = R.drawable.delivered;
                //c_status.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                c_status.setBackgroundColor(Color.LTGRAY);
                c_status.setEnabled(false);
                //call_cus.setVisibility(View.GONE);
                //call_cus.setBackgroundColor(Color.DKGRAY);
                //call_cus.setEnabled(false);
            }
            else
            {

            }


            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
           /* sv.setVisibility(View.VISIBLE);
            o.setVisibility(View.VISIBLE);*/

            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /////////////pDialog.setMessage("Please Wait...");
           //////////// pDialog.show();
            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            p1.bringToFront();
            p.bringToFront();
//            sv.setVisibility(View.GONE);
//            o.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            params2.add(new BasicNameValuePair("order_id",order_main));
            params2.add(new BasicNameValuePair("action", "delivered"));
            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));

            json = jParser.makeHttpRequest(url, "POST", params2);

            try {
                txc=json.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }





    private class pc extends AsyncTask<String, Void, String> {

        @Override

        protected void onPostExecute(String s) {

            if(txc.equals("pleaseWaitTextView"))
            {
                c_status.setText("pleaseWaitTextView");

                /*int imgResource = R.drawable.delivered;
                c_status.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                c_status.setBackgroundColor(Color.LTGRAY);*/
                //c_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
               /* c_status.setBackgroundColor(Color.LTGRAY);
                c_status.setEnabled(false);
                call_cus.setBackgroundColor(Color.DKGRAY);
                call_cus.setEnabled(false);*/
                //call_cus.setVisibility(View.GONE);
                c_status.setText("POSTPONED");
                c_status.setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.holo_orange_dark));
            }
            else
            {

            }


            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
           /* sv.setVisibility(View.VISIBLE);
            o.setVisibility(View.VISIBLE);*/

            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /////////////pDialog.setMessage("Please Wait...");
            //////////// pDialog.show();
            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            p1.bringToFront();
            p.bringToFront();
//            sv.setVisibility(View.GONE);
//            o.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            //params2.add(new BasicNameValuePair("order_id",order_main));
            params2.add(new BasicNameValuePair("action", "postponed"));
            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));

            json = jParser.makeHttpRequest(url, "POST", params2);
            System.out.println("POST"+json);

            try {
                txc=json.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void get_data()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";


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
        //dialog.setTitle("Keyword To Block");

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
        //dialog.setTitle("Keyword To Block");
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

                    //doAsynchronousTask.cancel();
                    Intent i=new Intent(OrderDetailActivity.this,SettingsActivity.class);
                    // barcodeImageView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        //dialog.setTitle("Keyword To Block");

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



    public void welcome_canceled()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm);
        //dialog.setTitle("Keyword To Block");

        dialog.setCanceledOnTouchOutside(false);

        conf=(TextView)dialog.findViewById(R.id.edit_pass);
        conf.setText("Are you sure order is Canceled ?");
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



    public void welcome_pos()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm);
        //dialog.setTitle("Keyword To Block");

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
               //////////////////// c_status.setText("pleaseWaitTextView");

                /*int imgResource = R.drawable.delivered;
                c_status.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                c_status.setBackgroundColor(Color.LTGRAY);*/
                //c_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            /*    c_status.setBackgroundColor(Color.LTGRAY);
                c_status.setEnabled(false);
                call_cus.setBackgroundColor(Color.DKGRAY);
                call_cus.setEnabled(false);*/
                //call_cus.setVisibility(View.GONE);
                c_status.setText("CANCELED");
                c_status.setBackgroundColor(Color.parseColor("#e24545"));
            }
            else
            {

            }


            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            p1.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
           /* sv.setVisibility(View.VISIBLE);
            o.setVisibility(View.VISIBLE);*/

            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /////////////pDialog.setMessage("Please Wait...");
            //////////// pDialog.show();
            p1.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            p1.bringToFront();
            p.bringToFront();
//            sv.setVisibility(View.GONE);
//            o.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            //params2.add(new BasicNameValuePair("order_id",order_main));
            params2.add(new BasicNameValuePair("action", "cancel"));
            params2.add(new BasicNameValuePair("can_val", ca_val));

            params2.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));

            json = jParser.makeHttpRequest(url, "POST", params2);

            try {
                txc=json.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
