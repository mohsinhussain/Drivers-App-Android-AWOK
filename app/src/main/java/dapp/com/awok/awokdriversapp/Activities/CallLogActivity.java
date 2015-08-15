package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dapp.com.awok.awokdriversapp.Adapters.CallLogListAdapter;
import dapp.com.awok.awokdriversapp.Modals.CallLog;
import dapp.com.awok.awokdriversapp.R;

/**
 * Created by shon on 5/6/2015.
 */

public class CallLogActivity extends Activity {


    private ListView listView;
    private CallLogListAdapter adapter;



    private List<CallLog> call_list = new ArrayList<CallLog>();


    String id_main,d_order_id;
    String CALL_LOG;
    String CALL_LOG_VALUE;
    SharedPreferences call_log;
    Context context;
    String call_text;
    TextView o;
    TextView gk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_main);
        Intent myIntent = getIntent();
        id_main = myIntent.getStringExtra("id_main");
        d_order_id = myIntent.getStringExtra("display_order_id");
        context=getApplicationContext();
        CALL_LOG_VALUE=id_main;
        CALL_LOG=id_main;
        System.out.println("CALL_LOG_VALUE"+CALL_LOG_VALUE);
        String text;
        System.out.println("callCALL_LOG_VALUE" + CALL_LOG_VALUE);
        o=(TextView)findViewById(R.id.two);
        o.setText(d_order_id);
        gk=(TextView)findViewById(R.id.gk);
        listView = (ListView) findViewById(R.id.listView_cal);
        adapter = new CallLogListAdapter(this,call_list);
        listView.setAdapter(adapter);
        call_log = getSharedPreferences(CALL_LOG, Context.MODE_PRIVATE);
        text = call_log.getString(CALL_LOG_VALUE, null);
        System.out.println("PREF" + text);

        if (text==null)
        {
            System.out.println("NO");
            gk.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
            System.out.println("NO" + text);
            call_text=text;
            gk.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            set_CALL();
        }
    }
    public void set_CALL()
    {
        try
        {
                JSONObject cal_json = new JSONObject(call_text);
                System.out.println(cal_json);
                JSONArray log=cal_json.getJSONArray("CALL_LOG");
                System.out.println(log);
                for(int i=0;i<log.length();i++)
                {
                    JSONObject l = log.getJSONObject(i);
                    CallLog cl = new CallLog();
                    cl.setDuration(l.getString("DURATION"));
                    cl.setPhone(l.getString("PHONE"));
                    cl.setTime(l.getString("TIME"));
                    call_list.add(cl);
                }
                adapter.notifyDataSetChanged();
        }
        catch (JSONException e)
        {

        }
    }
}
