package dapp.com.awok.awokdriversapp.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
    String new_pass;
    SharedPreferences login_prefs;
    Switch sw,cal_switch;
    TextView t_freq,t_upfreq,t_name,server_text;
    String txt_freq,txt_up_freq,txt_fname,serv_txt;
    long t_min_f,t_min_uf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.settings_table);
        login = getSharedPreferences(PREFS_LOGIN, 0); //1
        login_editor = login.edit(); //2
        login_editor.putString(PREFS_LOG_NAME, "log"); //3
        login_editor.putString(PREFS_LOG_PASS, "pass"); //3
        login_editor.commit();
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
        t_freq=(TextView)findViewById(R.id.txt_freq);
        frequency = getSharedPreferences(PREFS_FREQUENCY, 0);
//        txt_freq=frequency.getString(PREFS_TIME, null);
        txt_freq = "120000";
        t_min_f = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(txt_freq));
        t_freq.setText(String.valueOf(t_min_f)+" Seconds");
        t_upfreq=(TextView)findViewById(R.id.txt_up_freq);
        update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0);
//        txt_up_freq=update_frequency.getString(PREFS_UPDATE_TIME, null);
        txt_up_freq="600000";
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

        switch_prefs = getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE);
        String val = switch_prefs.getString(PREFS_SWITCH_VALUE, null);
        System.out.println("PREF_switch" + val);
        if (val.equals("true"))
        {
            sw.setChecked(true);
            svalue=true;
        }
        else
        {
            sw.setChecked(false);
            svalue=false;
        }
        cal_switch=(Switch)findViewById(R.id.switch_cal);
        switch_prefs_call = getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz.equals("true"))
        {
            cal_switch.setChecked(true);
            cal_value=true;
        }
        else
        {
            cal_switch.setChecked(false);
            cal_value=false;
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
    }

    private void new_serv() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_server);
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
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(SettingsActivity.this, FetchLocationCordinatesService.class));
        startService(new Intent(SettingsActivity.this,FetchLocationCordinatesService.class));
        stopService(new Intent(SettingsActivity.this, PushLocationDataToServerService.class));
        startService(new Intent(SettingsActivity.this, PushLocationDataToServerService.class));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
        volumeControl.setMax(9);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
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
                jv.setText(txt);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                jv.setText(txt);
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(progressz);
                frequency = getSharedPreferences(PREFS_FREQUENCY, 0); //1
                freq_editor = frequency.edit(); //2
                freq_editor.putString(PREFS_TIME, progressz); //3
                freq_editor.commit();
                long minutes = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(progressz));
                t_freq.setText(String.valueOf(minutes)+" Seconds");
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

    public void new_pass_settings()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        confrmPass=(EditText)dialog.findViewById(R.id.confirm_password);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
        pass.setTypeface(myFont);
        confrmPass.setTypeface(myFont);
        pass.setHint("ENTER PIN");
        confrmPass.setHint("CONFIRM PIN");

        confrmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    new_pass = "";
                    new_pass = pass.getText().toString();
                    if (new_pass.equals("")) {
                        pass.setHint("PLEASE ENTER NEW PIN");
                    } else if (confrmPass.getText().toString().equalsIgnoreCase("")) {
                        confrmPass.setHint("PLEASE CONFIRM YOUR PIN");
                    } else if (!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString())) {
                        Toast.makeText(SettingsActivity.this, "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
                    } else {
                        settings = getSharedPreferences(PREFS_NAME, 0); //1
                        editor = settings.edit(); //2
                        editor.putString(PREFS_SET, new_pass); //3
                        editor.commit();
                        dialog.dismiss();
                    }
                }
                return false;
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = "";
                new_pass = pass.getText().toString();
                if (new_pass.equals("")) {
                    pass.setHint("PLEASE ENTER NEW PIN");
                } else if (confrmPass.getText().toString().equalsIgnoreCase("")) {
                    confrmPass.setHint("PLEASE CONFIRM YOUR PIN");
                } else if (!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString())) {
                    Toast.makeText(SettingsActivity.this, "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
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

    public void new_update()
    {
        uc();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_update_seekbar);
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
        volumeControl.setMax(5);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
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
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                jv.setText(txt_up);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                jv.setText(txt_up);
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(prg_up);
                update_frequency = getSharedPreferences(PREFS_UPDATE_FREQUENCY, 0); //1
                update_freq_editor = update_frequency.edit(); //2
                update_freq_editor.putString(PREFS_UPDATE_TIME, prg_up); //3

                update_freq_editor.commit();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(prg_up));
                t_upfreq.setText(String.valueOf(minutes)+" Minutes");
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
}
