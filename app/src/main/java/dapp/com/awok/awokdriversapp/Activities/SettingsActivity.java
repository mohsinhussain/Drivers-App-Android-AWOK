package dapp.com.awok.awokdriversapp.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import dapp.com.awok.awokdriversapp.Services.PushLocationDataToServerService;
import dapp.com.awok.awokdriversapp.R;
import dapp.com.awok.awokdriversapp.Services.FetchLocationCordinatesService;


public class SettingsActivity extends ActionBarActivity {



   // private SimpleGestureFilter detector;

String pass_one,pass_two;

    public static final String PREFS_UPDATE_FREQUENCY = "APP_UPDATE_FREQ";
    public static final String PREFS_UPDATE_TIME = "APP_UPDATE_TIME";
    SharedPreferences update_frequency;
    SharedPreferences.Editor update_freq_editor;

    public static final String PREFS_SERVER_NAME = "APP_SERVER_NAME";
    public static final String PREFS_SERVER_VALUE = "APP_SERVER_VALUE";
SharedPreferences server_pref;
    SharedPreferences.Editor serv_editor;



    public static final String PREFS_FREQUENCY = "APP_FREQ";
    public static final String PREFS_TIME = "APP_TIME";
    String progressz="120000";
    String prg_up="600000";
    String txt="30";
    String txt_up="10 Minutes";
    public static final String PREFS_LOG_NAME = "APP_LNAME";
    public static final String PREFS_LOG_PASS = "APP_PREFS_LPASS";



    public static final String PREFS_SWITCH = "APP_SWITCH";
    public static final String PREFS_SWITCH_VALUE = "APP_SWITCH_VALUE";
    SharedPreferences switch_prefs;
    SharedPreferences.Editor switch_editor;



    public static final String PREFS_SWITCH_CALL = "APP_SWITCH_CALL";
    public static final String PREFS_SWITCH_VALUE_CALL = "APP_SWITCH_VALUE_CALL";
    SharedPreferences switch_prefs_call;
    SharedPreferences.Editor switch_editor_call;


    float x1,x2;
    float y1, y2;
Boolean svalue,cal_value;
    public static final String PREFS_LOGIN = "APP_LOGIN";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "APP_PREFS";
    public static final String PREFS_SET = "APP_PREFS_SET";
    SharedPreferences login;
    SharedPreferences.Editor login_editor;
    SharedPreferences frequency;
    SharedPreferences.Editor freq_editor;
    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_FNAME = "PREFS_APP_FNAME";
    EditText pass,log,serv, confrmPass;
    String l_temp,p_temp,new_pass;
    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;
    Switch sw,cal_switch;
    TextView t_freq,t_upfreq,t_name,server_text;
    String txt_freq,txt_up_freq,txt_fname,serv_txt;
    long t_min_f,t_min_uf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //setContentView(R.pullRefreshLayout.activity_main_activity2);
        setContentView(R.layout.settings_table);








        login = getSharedPreferences(PREFS_LOGIN, 0); //1
        login_editor = login.edit(); //2

        login_editor.putString(PREFS_LOG_NAME, "log"); //3
        login_editor.putString(PREFS_LOG_PASS, "pass"); //3

        login_editor.commit();

     //   detector = new SimpleGestureFilter(this,this);
        Button loginlogout=(Button)findViewById(R.id.login);
server_text=(TextView)findViewById(R.id.txt_server);
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        server_text.setText(serv_txt);



t_name=(TextView)findViewById(R.id.t_name);
        login_prefs = getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);

        txt_fname = login_prefs.getString(APP_LOG_FNAME, null);
        if(txt_fname==null)
        {
            t_name.setText("Not Logged In");
            loginlogout.setText("LoginActivity");

        }
        else
        {
            t_name.setText("Logged in as "+txt_fname);
            loginlogout.setText("Logout");

        }
//t_name.setText("Logged in as "+txt_fname);




t_freq=(TextView)findViewById(R.id.txt_freq);
        frequency = getSharedPreferences(PREFS_FREQUENCY, 0);
        txt_freq=frequency.getString(PREFS_TIME, null);
         t_min_f = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(txt_freq));
        t_freq.setText(String.valueOf(t_min_f)+" Seconds");




        t_upfreq=(TextView)findViewById(R.id.txt_up_freq);
        update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0);
        txt_up_freq=update_frequency.getString(PREFS_UPDATE_TIME, null);
        t_min_uf = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(txt_up_freq));
        t_upfreq.setText(String.valueOf(t_min_uf)+" Minutes");



        sw=(Switch)findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    svalue=true;
                    switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
                    switch_editor = switch_prefs.edit(); //2

                    switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


                    switch_editor.commit();
                }
                else
                {
                    svalue=false;
                    switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
                    switch_editor = switch_prefs.edit(); //2

                    switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


                    switch_editor.commit();
                }
            }
        });


       ////////// sw=(Switch)findViewById(R.id.switch1);
        switch_prefs = getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE);
        String val = switch_prefs.getString(PREFS_SWITCH_VALUE, null);
        System.out.println("PREF_switch" + val);

        if (val.equals("true"))
        {
sw.setChecked(true);
            svalue=true;
            /*switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
            switch_editor = switch_prefs.edit(); //2

            switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


            switch_editor.commit();*/
          //  enableBroadcastReceiver();

        }
        else
        {
            sw.setChecked(false);
            svalue=false;
           /* switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
            switch_editor = switch_prefs.edit(); //2

            switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


            switch_editor.commit();*/
           // disableBroadcastReceiver();
        }
        cal_switch=(Switch)findViewById(R.id.switch_cal);
        switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz.equals("true"))
        {
            cal_switch.setChecked(true);
            cal_value=true;
            /*switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
            switch_editor = switch_prefs.edit(); //2

            switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


            switch_editor.commit();*/
            //  enableBroadcastReceiver();

        }
        else
        {
            cal_switch.setChecked(false);
            cal_value=false;
           /* switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
            switch_editor = switch_prefs.edit(); //2

            switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


            switch_editor.commit();*/
            // disableBroadcastReceiver();
        }



        cal_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    cal_value=true;
                    switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, 0); //1
                    switch_editor_call = switch_prefs_call.edit(); //2

                    switch_editor_call.putString(PREFS_SWITCH_VALUE_CALL, cal_value.toString()); //3


                    switch_editor_call.commit();
                }
                else
                {
                    cal_value=false;
                    switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, 0); //1
                    switch_editor_call = switch_prefs_call.edit(); //2

                    switch_editor_call.putString(PREFS_SWITCH_VALUE_CALL, cal_value.toString()); //3


                    switch_editor_call.commit();
                }
            }
        });


        Button server=(Button)findViewById(R.id.button_server);
        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_serv();
            }
        });



        Button b3=(Button)findViewById(R.id.button3);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new_freq();
            }
        });



        Button b9=(Button)findViewById(R.id.button_freq);

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_update();
            }
        });


        Button b4=(Button)findViewById(R.id.button4);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass_settings();
            }
        });



        loginlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        /*Button b5=(Button)findViewById(R.id.button5);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /////////////////////////////////////////////set_login();
                login_prefs = getSharedPreferences(APP_LOGIN, 0); //1
                login_prefs_editor = login_prefs.edit(); //2




                login_prefs_editor.clear();




                login_prefs_editor.commit();
                stopService(new Intent(SettingsActivity.this,FetchLocationCordinatesService.class));
                Intent barcodeImageView=new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(barcodeImageView);
            }
        });*/




      /*  Button b6=(Button)findViewById(R.id.button6);

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SettingsActivity.this,FetchLocationCordinatesService.class));
                //startService(new Intent(SettingsActivity.this,FetchLocationCordinatesService.class));
                Intent barcodeImageView=new Intent(SettingsActivity.this,test.class);
                startActivity(barcodeImageView);
            }
        });*/


     /*   Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeImageView=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(barcodeImageView);
            }
        });*/
    }

    private void new_serv() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_server);
        //dialog.setTitle("Keyword To Block");
        dialog.setCanceledOnTouchOutside(false);


        serv=(EditText)dialog.findViewById(R.id.two);
        serv.setHint("Please Enter Server Details");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pass = "";
                new_pass = serv.getText().toString();
                server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0); //1
                serv_editor = server_pref.edit(); //2

                serv_editor.putString(PREFS_SERVER_VALUE, new_pass); //3


                serv_editor.commit();
                dialog.dismiss();


                server_text.setText(new_pass);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        // do nothing



        super.onBackPressed();


        /*switch_prefs = getSharedPreferences(PREFS_SWITCH, 0); //1
        switch_editor = switch_prefs.edit(); //2

        switch_editor.putString(PREFS_SWITCH_VALUE, svalue.toString()); //3


        switch_editor.commit();*/



        stopService(new Intent(SettingsActivity.this, FetchLocationCordinatesService.class));
        startService(new Intent(SettingsActivity.this,FetchLocationCordinatesService.class));
        stopService(new Intent(SettingsActivity.this, PushLocationDataToServerService.class));
        startService(new Intent(SettingsActivity.this, PushLocationDataToServerService.class));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




   /* public void new_freq()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.pullRefreshLayout.activity_alert);




        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("Enter Frequency(Minutes)");
        pass.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_freq;
                new_freq=pass.getText().toString();
                int tz= (int)TimeUnit.MINUTES.toMillis(Integer.parseInt(new_freq));

                frequency = getSharedPreferences(PREFS_FREQUENCY, 0); //1
                freq_editor = frequency.edit(); //2
                String conv=Integer.toString(tz);
                freq_editor.putString(PREFS_TIME, conv); //3

                freq_editor.commit();
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
    }*/
public void let()
{
    frequency = getSharedPreferences(PREFS_FREQUENCY, 0);
    txt_freq=frequency.getString(PREFS_TIME, null);
}



    public void uc()
    {
        update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0);
        txt_up_freq=update_frequency.getString(PREFS_UPDATE_TIME, null);
    }


    public void new_freq()
    {
        let();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_seekbar);
        //dialog.setTitle("Keyword To Block");
        dialog.setCanceledOnTouchOutside(false);
        final TextView jv=(TextView)dialog.findViewById(R.id.textView1);
        SeekBar volumeControl = (SeekBar) dialog.findViewById(R.id.volume_bar12);
        if(txt_freq.equals("30000"))
        {
            volumeControl.setProgress(0);
            jv.setText("30 Seconds");
        }
        else if(txt_freq.equals("60000"))
        {
            volumeControl.setProgress(1);
            jv.setText("1 Minute");
        }
        else if(txt_freq.equals("90000"))
        {
            volumeControl.setProgress(2);
            jv.setText("1 Minute 30 Seconds");
        }
        else if(txt_freq.equals("120000"))
        {
            volumeControl.setProgress(3);
            jv.setText("2 Minutes");
        }
        else if(txt_freq.equals("150000"))
        {
            volumeControl.setProgress(4);
            jv.setText("2 Minutes 30 Seconds");
        }
        else if(txt_freq.equals("180000"))
        {
            volumeControl.setProgress(5);
            jv.setText("3 Minutes");
        }
        else if(txt_freq.equals("210000"))
        {
            volumeControl.setProgress(6);
            jv.setText("3 Minutes 30 Seconds");
        }
        else if(txt_freq.equals("240000"))
        {
            volumeControl.setProgress(7);
            jv.setText("4 Minutes");
        }
        else if(txt_freq.equals("270000"))
        {
            volumeControl.setProgress(8);
            jv.setText("4 Minutes 30 Seconds");
        }
        else if(txt_freq.equals("300000"))
        {
            volumeControl.setProgress(9);
            jv.setText("5 Minutes");
        }

        //volumeControl.set
        //volumeControl.incrementProgressBy(1);
        //volumeControl.setProgress(0);

        volumeControl.setMax(9);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                //progressz=progress/10;
                if(progress==0)
                {
                    progressz="30000";
                    txt="30 Seconds";
                    jv.setText(txt);
                }
                else if(progress==1)
                {
                    progressz="60000";
                    txt="1 Minute";
                    jv.setText(txt);
                }
                else if(progress==2)
                {
                    progressz="90000";
                    txt="1 Minute 30 Seconds";
                    jv.setText(txt);
                }
                else if(progress==3)
                {
                    progressz="120000";
                    txt="2 Minutes";
                    jv.setText(txt);
                }
                else if(progress==4)
                {
                    progressz="150000";
                    txt="2 Minutes 30 Seconds";
                    jv.setText(txt);
                }
                else if(progress==5)
                {
                    progressz="180000";
                    txt="3 Minutes";
                    jv.setText(txt);
                }
                else if(progress==6)
                {
                    progressz="210000";
                    txt="3 Minutes 30 Seconds";
                    jv.setText(txt);
                }
                else if(progress==7)
                {
                    progressz="240000";
                    txt="4 Minutes";
                    jv.setText(txt);
                }
                else if(progress==8)
                {
                    progressz="270000";
                    txt="4 Minute 30 Seconds";
                    jv.setText(txt);
                }
                else if(progress==9)
                {
                    progressz="300000";
                    txt="5 Minutes";
                    jv.setText(txt);
                }


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                //Toast.makeText(SeekbarActivity.this, "seek bar progress: " + progressChanged, Toast.LENGTH_SHORT)
                //.show();
                jv.setText(txt);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
          //      Toast.makeText(SettingsActivity.this, "seek bar progress: " + progressChanged + "pg=" + progressz, Toast.LENGTH_SHORT)
                 //       .show();
                jv.setText(txt);
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String new_freq;
                //new_freq=progressz;
                //int tz= (int)TimeUnit.MINUTES.toMillis((long) Double.parseDouble(new_freq));
                        System.out.println(progressz);
                frequency = getSharedPreferences(PREFS_FREQUENCY, 0); //1
                freq_editor = frequency.edit(); //2
               // String conv=Integer.toString(tz);
                freq_editor.putString(PREFS_TIME, progressz); //3

                freq_editor.commit();
                long minutes = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(progressz));
                t_freq.setText(String.valueOf(minutes)+" Seconds");
                dialog.dismiss();
//                Intent barcodeImageView=new Intent(SettingsActivity.this,test.class);
//                startActivity(barcodeImageView);
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

    /*public void set_login()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.pullRefreshLayout.login_alert);
        //dialog.setTitle("Keyword To Block");
        dialog.setCanceledOnTouchOutside(false);

log=(EditText)dialog.findViewById(R.id.edit_uname);
       // log.setHint();
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
      //  pass.setHint("Enter Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setText("LoginActivity");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = getSharedPreferences(PREFS_LOGIN, Context.MODE_PRIVATE); //1
                l_temp = login.getString(PREFS_LOG_NAME, null);
                p_temp = login.getString(PREFS_LOG_PASS, null);


                if(l_temp==null)
                {

                }

                else
                {
                    if ((l_temp.equals(log.getText().toString())) && (p_temp.equals(pass.getText().toString())))
                    {
                        System.out.println("SUCCESS");
//                        Intent barcodeImageView=new Intent(SettingsActivity.this,test.class);
//                        startActivity(barcodeImageView);
                    }
                    else
                    {
                        System.out.println("FAIL");
                        pass.setHint("Sorry !! Try Again");
                    }
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
    }*/



    public void new_pass_settings()
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
                    Toast.makeText(SettingsActivity.this, "Your confirmed password does not match initially entered password", Toast.LENGTH_SHORT).show();
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




    /*public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();

                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

//                / /if left to right sweep event on screen
                if (x1 < x2)
                {
                    Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                }

                // if right to left sweep event on screen
                if (x1 > x2)
                {
                    Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                }

                // if UP to Down sweep event on screen
                if (y1 < y2)
                {
                    Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                }

                //     / /if Down to UP sweep event on screen
                if (y1 > y2)
                {
                    Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                }
                break;
            }

          *//*  case MotionEvent.ACTION_OUTSIDE:
            {

                    Toast.makeText(SettingsActivity.this, " Performed", Toast.LENGTH_LONG).show();

                break;
            }*//*
        }
        return false;
    }*/


   /* @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }*/




    public void new_pass_one()
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

                pass_one=pass.getText().toString();
                dialog.dismiss();
                new_pass_two();

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



    public void new_pass_two()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        //dialog.setTitle("Keyword To Block");
        dialog.setCanceledOnTouchOutside(false);


        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("Please Enter New Again");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass_two=pass.getText().toString();
                if(pass_one.equals(pass_two)) {
                    settings = getSharedPreferences(PREFS_NAME, 0); //1
                    editor = settings.edit(); //2

                    editor.putString(PREFS_SET, pass_two); //3

                    editor.commit();
                    dialog.dismiss();
                }
                else
                {
                    pass.setText("");
                    pass.setHint("Passwords Donot Match");
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






    public void new_update()
    {
        uc();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_update_seekbar);
        //dialog.setTitle("Keyword To Block");
        dialog.setCanceledOnTouchOutside(false);
        final TextView jv=(TextView)dialog.findViewById(R.id.textView1);
        SeekBar volumeControl = (SeekBar) dialog.findViewById(R.id.volume_bar12);
        if(txt_up_freq.equals("600000"))
        {
            volumeControl.setProgress(0);

            jv.setText("10 Minutes");
        }
        else if(txt_up_freq.equals("1200000"))
        {
            volumeControl.setProgress(1);

            jv.setText("20 Minutes");
        }
        else if(txt_up_freq.equals("1800000"))
        {
            volumeControl.setProgress(2);

            jv.setText("30 Minutes");
        }
        else if(txt_up_freq.equals("2400000"))
        {
            volumeControl.setProgress(3);

            jv.setText("40 Minutes");
        }
        else if(txt_up_freq.equals("3000000"))
        {
            volumeControl.setProgress(4);

            jv.setText("50 Minutes");
        }
        else if(txt_up_freq.equals("3600000"))
        {
            volumeControl.setProgress(5);

            jv.setText("60 Minutes");
        }
        //volumeControl.incrementProgressBy(1);
        //volumeControl.setProgress(0);

        volumeControl.setMax(5);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                //progressz=progress/10;
                if(progress==0)
                {
                    prg_up="600000";
                    txt_up="10 Minutes";
                    jv.setText(txt_up);
                }
                else if(progress==1)
                {
                    prg_up="1200000";
                    txt_up="20 Minutes";
                    jv.setText(txt_up);
                }
                else if(progress==2)
                {
                    prg_up="1800000";
                    txt_up="30 Minutes";
                    jv.setText(txt_up);
                }
                else if(progress==3)
                {
                    prg_up="2400000";
                    txt_up="40 Minutes";
                    jv.setText(txt_up);
                }
                else if(progress==4)
                {
                    prg_up="3000000";
                    txt_up="50 Minutes";
                    jv.setText(txt_up);
                }
                else if(progress==5)
                {
                    prg_up="3600000";
                    txt_up="60 Minutes";
                    jv.setText(txt_up);
                }
                /*else if(progress==6)
                {
                    prg_up="4200000";
                    txt_up="70 Minutes";
                }*/
                /*else if(progress==70)
                {
                    progressz="4800000";
                    txt="80 Minutes";
                }
                else if(progress==80)
                {
                    progressz="5400000";
                    txt="90 Minutes";
                }
                else if(progress==90)
                {
                    progressz="6000000";
                    txt="100 Minutes";
                }*/


            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                //Toast.makeText(SeekbarActivity.this, "seek bar progress: " + progressChanged, Toast.LENGTH_SHORT)
                //.show();
                jv.setText(txt_up);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
          //      Toast.makeText(SettingsActivity.this, "seek bar progress: " + progressChanged + "pg=" + prg_up, Toast.LENGTH_SHORT)
                //        .show();
                jv.setText(txt_up);
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String new_freq;
                //new_freq=progressz;
                //int tz= (int)TimeUnit.MINUTES.toMillis((long) Double.parseDouble(new_freq));
                System.out.println(prg_up);
                update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0); //1
                update_freq_editor = update_frequency.edit(); //2
                // String conv=Integer.toString(tz);
                update_freq_editor.putString(PREFS_UPDATE_TIME, prg_up); //3

                update_freq_editor.commit();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(prg_up));
                t_upfreq.setText(String.valueOf(minutes)+" Minutes");
                dialog.dismiss();
//                Intent barcodeImageView=new Intent(SettingsActivity.this,test.class);
//                startActivity(barcodeImageView);
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
}
