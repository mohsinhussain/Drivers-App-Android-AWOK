package dapp.com.awok.awokdriversapp.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dapp.com.awok.awokdriversapp.Activities.OrderDetailActivity;
import dapp.com.awok.awokdriversapp.Modals.Order;
import dapp.com.awok.awokdriversapp.R;

public class OrderListViewAdapter extends BaseAdapter implements Filterable {
	private Activity activity;
	private LayoutInflater inflater;
    private Context context;
    String CALL_LOG;
    String CALL_LOG_VALUE;
    SharedPreferences call_log;
    String store_phone,store_user,store_duration;
    SharedPreferences.Editor call_log_value;
    ImageView iv;
    Time time;
    String timez;
    List<Order> orderFilterList;
    ValueFilter valueFilter;
    String strDate;
	private List<Order> orderList;
	public OrderListViewAdapter(Activity activity, List<Order> orderList, Context context) {
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		this.activity = activity;
		this.orderList = orderList;
        this.context = context;
        orderFilterList = orderList;
    }

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int location) {
		return orderList.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.new_listada, null);

        final TextView orderNumberTextView = (TextView) convertView.findViewById(R.id.orderNumberTextView);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView phoneTextView = (TextView) convertView.findViewById(R.id.phoneTextView);
        final TextView id = (TextView) convertView.findViewById(R.id.id);
        final ImageView arrowImageView=(ImageView)convertView.findViewById(R.id.arrowImageView);
        final ImageView phoneImageView=(ImageView)convertView.findViewById(R.id.phoneImageView);
        final Button b1=(Button)convertView.findViewById(R.id.red);
        final LinearLayout categoryLinearLayout=(LinearLayout)convertView.findViewById(R.id.categoryLinearLayout);
        final TextView categoryTextView=(TextView)convertView.findViewById(R.id.categoryTextView);

        // getting movie data for the row
        Order order = orderList.get(position);

		// thumbnail image
/*b1.setTag(m.getStatus());
        l1.setTag(m.getShow());*/
        id.setTag(order.getStatus());
        orderNumberTextView.setText("# " + order.getOrder_no());
        nameTextView.setText(order.getName());
        phoneTextView.setText(order.getPhone());
        phoneImageView.setTag(order.getPhone());
        id.setText(order.getId());
        orderNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getData();
            }
        });
        iv=arrowImageView;
        if(order.getStatus().equals("F"))
        {
            if(order.getShow().equals("true")) {
                phoneImageView.setImageResource(R.drawable.jg);
                phoneImageView.setEnabled(false);

                categoryLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                categoryTextView.setText("Delivered");
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                categoryLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                phoneImageView.setImageResource(R.drawable.jg);
                phoneImageView.setEnabled(false);
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                categoryLinearLayout.setVisibility(View.GONE);
            }
        }



        if(order.getStatus().equals("O"))
        {
            if(order.getShow().equals("true")) {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                categoryLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                categoryTextView.setText("Pending");
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                categoryLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                categoryLinearLayout.setVisibility(View.GONE);
            }
        }


        if(order.getStatus().equals("X"))
        {
            if(order.getShow().equals("true")) {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                categoryLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
                categoryTextView.setText("Postponed");
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
                categoryLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
                categoryLinearLayout.setVisibility(View.GONE);
            }
        }

        if(order.getStatus().equals("S"))
        {
            if(order.getShow().equals("true")) {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                categoryLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryTextView.setText("Canceled");
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryLinearLayout.setVisibility(View.GONE);
            }
        }


        if(order.getStatus().equals("P"))
        {
            if(order.getShow().equals("true")) {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                categoryLinearLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryTextView.setText("Canceled");
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                phoneImageView.setImageResource(R.drawable.dd);
                phoneImageView.setEnabled(true);
                b1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                categoryLinearLayout.setVisibility(View.GONE);
            }
        }

//        arrowImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("ORDER" + phoneImageView.getTag().toString());
//                System.out.println("ORDER" + orderNumberTextView.getText().toString());
//                CALL_LOG_VALUE = id.getText().toString();
//                CALL_LOG = id.getText().toString();
//                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
//                store_phone = phoneImageView.getTag().toString();
//                store_user = nameTextView.getText().toString();
//                Intent i = new Intent(context, OrderDetailActivity.class);
//                i.putExtra("order_main", id.getText().toString());
//                i.putExtra("display_order_id", orderNumberTextView.getText().toString());
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//
//            }
//        });


        phoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("ORDER" + phoneImageView.getTag().toString());
                System.out.println("ORDER" + orderNumberTextView.getText().toString());
                CALL_LOG_VALUE = id.getText().toString();
                CALL_LOG = id.getText().toString();
                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
                store_phone = phoneImageView.getTag().toString();
                store_user = nameTextView.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + store_phone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);

            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ORDER" + phoneImageView.getTag().toString());
                System.out.println("ORDER" + orderNumberTextView.getText().toString());
                CALL_LOG_VALUE=id.getText().toString();
                CALL_LOG=id.getText().toString();
                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
                store_phone=phoneImageView.getTag().toString();
                store_user=nameTextView.getText().toString();
                Intent i = new Intent(context,OrderDetailActivity.class);
                i.putExtra("order_main",id.getText().toString());
                i.putExtra("display_order_id", orderNumberTextView.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
	}




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
        timer.schedule(doAsynchronousTask, 2000);
    }


    private void store()
    {


        call_log = context.getSharedPreferences(CALL_LOG, Context.MODE_APPEND);
        timez=strDate+" "+time.toString();
        System.out.println(" fh"+timez);
        if(call_log.contains(CALL_LOG_VALUE))
        {
            System.out.println(" EXIST");
            String n = call_log.getString(CALL_LOG_VALUE, null);
            System.out.println("FDFDz"+n.toString());
            try {
                JSONObject lat_lon_object = new JSONObject(n);

                JSONObject lat_lon_temp=new JSONObject();
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array = lat_lon_object.getJSONArray("CALL_LOG");
                latlon_array.put(lat_lon_temp);
                System.out.println("FDFDz"+latlon_array.toString());




                lat_lon_object.put("CALL_LOG", latlon_array);
                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();
                get_data();
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
                lat_lon_temp.put("ID", CALL_LOG_VALUE);
                lat_lon_temp.put("PHONE", store_phone);
                lat_lon_temp.put("USER_NAME", store_user);
                lat_lon_temp.put("DURATION", store_duration);
                lat_lon_temp.put("TIME", timez.toString());
                JSONArray latlon_array=new JSONArray();
                latlon_array.put(lat_lon_temp);
                lat_lon_object.put("CALL_LOG", latlon_array);

                call_log_value=call_log.edit();
                System.out.println("saveCALL_LOG_VALUE" + CALL_LOG_VALUE);
                call_log_value.putString(CALL_LOG_VALUE, lat_lon_object.toString());
                System.out.println("LATLON"+lat_lon_object.toString());
                call_log_value.commit();
                get_data();

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void getData() {

        CursorLoader cursorLoader = new CursorLoader(context,
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
        String date =managedCursor.getString(dateIndex);
        Date callDate = new Date(Long.valueOf(date));
        time=new Time(Long.valueOf(callDate.getTime()));
        Date dz=new Date(Long.valueOf(callDate.getDate()));
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
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");
                    isPhoneCalling = false;
                    terra();
                }

            }

        }

    }



    private void get_data()
    {
        //SharedPreferences settings;
        String text;
        //text_res="";
        System.out.println("callCALL_LOG_VALUE" + CALL_LOG_VALUE);

        call_log = context.getSharedPreferences(CALL_LOG, Context.MODE_APPEND);
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
    }



    public void clear()
    {
        orderList.clear();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
System.out.println("constraint"+constraint.toString());
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Order> filterList = new ArrayList<Order>();
                for (int i = 0; i < orderFilterList.size(); i++) {
                    if ( (orderFilterList.get(i).getOrder_no().toUpperCase() )
                            .contains(constraint.toString().toUpperCase())||(orderFilterList.get(i).getPhone().toUpperCase() )
                            .contains(constraint.toString().toUpperCase())) {

                        Order country = new Order(orderFilterList.get(i)
                                .getOrder_no() ,  orderFilterList.get(i)
                                .getName() ,  orderFilterList.get(i)
                                .getPhone(), orderFilterList.get(i)
                                .getId(), orderFilterList.get(i)
                                .getStatus(), orderFilterList.get(i)
                                .getShow());

                        filterList.add(country);
                    }
                }




                results.count = filterList.size();
                results.values = filterList;
            }

            else {
                results.count = orderFilterList.size();
                results.values = orderFilterList;
            }

               if(constraint.equals("orders")||constraint.equals("rma"))
            {
                System.out.println("CONS");
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            orderList = (ArrayList<Order>) results.values;
            notifyDataSetChanged();
        }

    }



}