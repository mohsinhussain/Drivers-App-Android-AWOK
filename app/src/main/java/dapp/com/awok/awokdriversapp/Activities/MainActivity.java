package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dapp.com.awok.awokdriversapp.Adapters.MainSearchAdapter;
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
public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener{
    private ListView orderListView;
    private MainSearchAdapter mainSearchAdapter;
    RelativeLayout bodyRelativeLayout,headerRelativeLayout, mainRelativeLayout;
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
    private ArrayList<Order> orderArrayList = new ArrayList<Order>();
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
    String json;


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

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    PagerSlidingTabStrip mTabStrip;
LinearLayout lay_main_tabstrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_list);
        lay_main_tabstrip=(LinearLayout)findViewById(R.id.lay_main_tabstrip);
        mTabStrip = (PagerSlidingTabStrip ) findViewById(R.id.pager_tab_strip);
        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        Log.v("PageViewerAdapter", "position: " + i);
                        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
                        break;

                    case 1:
                        Log.v("PageViewerAdapter", "position: " + i);
                        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_red_dark));
                        break;
                    case 2:
                        Log.v("PageViewerAdapter", "position: " + i);
                        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_orange_dark));
                        break;
                    case 3:
                        Log.v("PageViewerAdapter", "position: " + i);
                        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mTabStrip.setViewPager(mViewPager);
        server_pref = getSharedPreferences(PREFS_SERVER_NAME, Context.MODE_PRIVATE);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        if(serv_txt==null)
        {
            server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0); //1
            serv_editor = server_pref.edit(); //2
            serv_editor.putString(PREFS_SERVER_VALUE, "http://dev.alifca.com/api/drivers/"); //3
            serv_editor.commit();
        }
        context=getApplicationContext();
        orderListView = (ListView ) findViewById(R.id.orderListView);
        mainSearchAdapter = new MainSearchAdapter(this, orderArrayList,context);
        orderListView.setAdapter(mainSearchAdapter);

        server_pref = getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        validate=serv_txt+"listing/";
        url=serv_txt+"login/";

        pleaseWaitTextView =(TextView)findViewById(R.id.waitTextView);
        progressView =(CircleProgressBar)findViewById(R.id.progress1);
        progressView.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        progressView.setShowArrow(true);
        noOrderTextView =(TextView)findViewById(R.id.noOrderTextView);

        final View activityRootView = findViewById(R.id.mainRelativeLayout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff<100) { // if more than 100 pixels, its probably a keyboard...
                    int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
                    View searchPlate = searchView.findViewById(searchPlateId);
                    searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
//                    System.out.println("Keyboard check called");
                }
            }
        });


        searchView = (SearchView) findViewById(R.id.search_view);

        final EditText e = (EditText)searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
                    View searchPlate = searchView.findViewById(searchPlateId);
                    if(e.getText().toString().equalsIgnoreCase("")){
                        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                    }
                    else{
                        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                    }


                }
                return false;
            }
        });
//        e.setBackgroundColor(Color.BLACK); //←If you just want a color
//        e.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));

        searchView.setOnQueryTextListener(this);

        int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) searchView.findViewById(closeButtonId);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
//        searchPlate.setVisibility(View.GONE);
        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchText.setText("");
            }
        });

        cd = new ConnectionDetector(MainActivity.this);
        bodyRelativeLayout =(RelativeLayout)findViewById(R.id.bodyRelativeLayout);
        headerRelativeLayout=(RelativeLayout)findViewById(R.id.headerRelativeLayout);
        barcodeImageView =(ImageView)findViewById(R.id.barcodeImageView);
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
        g.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                get_data();
                return false;
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {


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
    public boolean onQueryTextSubmit(String query) {

        searchView.clearFocus();
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(newText.length()<=0)
        {
            orderListView.setVisibility(View.GONE);
            lay_main_tabstrip.setVisibility(View.VISIBLE);
            searchView.clearFocus();
            int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            View searchPlate = searchView.findViewById(searchPlateId);
            searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        }
        else
        {
            orderListView.setVisibility(View.VISIBLE);
            lay_main_tabstrip.setVisibility(View.GONE);
            mainSearchAdapter.getFilter().filter(newText);
            int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            View searchPlate = searchView.findViewById(searchPlateId);
            searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        }

        return false;
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment(orderArrayList);
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String title = "";
            switch (position) {
                case 0:
                    title = "PENDING";
                    break;

                case 1:
                    title =  "CANCELLED";
                    break;
                case 2:
                    title =  "POSTPONED";
                    break;
                case 3:
                    title =  "DELIVERED";
                    break;
            }
            return title;
        }
    }

    // Instances of this class are fragments representing a single
    // object in our collection.
    public class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";
        private ArrayList<Order> orderList;
        ListView orderListView;
        PullRefreshLayout pullRefreshLayout;
        private OrderListViewAdapter orderListViewAdapter;
        public DemoObjectFragment(){

        };
        public  DemoObjectFragment(ArrayList<Order> orderList){
            this.orderList = orderList;
        }

//        TextView testTextView;


        /*@Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            (MainActivity)getActivity().
            return true;
        }*/






        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            int fragmentPosition = args.getInt(ARG_OBJECT);

            orderListView = (ListView) rootView.findViewById(R.id.orderListView);
            pullRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            orderListView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent touchevent) /*{

                    if(event.getAction() == MotionEvent.ACTION_MOVE){
                        //do something

                    }
                    return true;
                }*/
                {

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

            pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // start refresh
//                    new craz().execute();
                }
            });
            ArrayList<Order> newList = new ArrayList<Order>();
            switch (fragmentPosition) {
                case 0:
                    for(int i =0;i<orderList.size();i++){
                        if(orderList.get(i).getStatus().equalsIgnoreCase("O")){
                            newList.add(orderList.get(i));
                        }
                    }
                    break;

                case 1:
                    for(int i =0;i<orderList.size();i++){
                        if(orderList.get(i).getStatus().equalsIgnoreCase("P") || (orderList.get(i).getStatus().equalsIgnoreCase("S"))){
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 2:
                    for(int i =0;i<orderList.size();i++){
                        if(orderList.get(i).getStatus().equalsIgnoreCase("X")){
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 3:
                    for(int i =0;i<orderList.size();i++){
                        if(orderList.get(i).getStatus().equalsIgnoreCase("F")){
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
            }

//            if( (order.getStatus().equalsIgnoreCase("O")&&fragmentPosition==0) || ((order.getStatus().equalsIgnoreCase("S")|| order.getStatus().equalsIgnoreCase("P"))&&fragmentPosition==1) ||
//                (order.getStatus().equalsIgnoreCase("X")&&fragmentPosition==2) || (order.getStatus().equalsIgnoreCase("F")&&fragmentPosition==3)) {
//            orderListViewAdapter = new OrderListViewAdapter(getActivity(), newList,getActivity(),m);
            orderListView.setAdapter(orderListViewAdapter);
//            ((TextView) rootView.findViewById(R.id.text1)).setText(
//                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }

//        private class craz extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... paramz) {
//                Boolean del=true;
//                Boolean pos=true;
//                Boolean can=true;
//                Boolean ma=true;
//                JSONParser jParser = new JSONParser();
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("did",txt_did));
//                params.add(new BasicNameValuePair("key",txt_pass));
////                params.add(new BasicNameValuePair("action", "list"));
////                params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
//                json = jParser.makeHttpRequest(url, "GET", params);
//                System.out.println(json.toString());
//                try {
//
//                    if(json.getString("ORDER_LIST").equals("nodata"))
//                    {
//                        System.out.println("NO_DATA_hggnb");
//                        data_name=json.getString("DRIVER_NAME");
//                        data_date=json.getString("DATE");
//                        System.out.println(data_name.toString());
//                        System.out.println(data_date.toString());
//                    }
//                    else
//                    {
//                        JSONArray ja=json.getJSONArray("ORDER_LIST");
//                        System.out.println(ja.toString());
////                        orderListViewAdapter.clear();
//                        orderArrayList.clear();
//                        data_name=json.getString("DRIVER_NAME");
//                        data_date=json.getString("DATE");
//                        System.out.println(data_name.toString());
//                        System.out.println(data_date.toString());
//                        for (int i = 0; i < ja.length(); i++) {
//
//                            JSONObject g = ja.getJSONObject(i);
//                            Order LIstDetails = new Order();
//                            LIstDetails.setOrder_no(g.getString("ORDER_NUM"));
//                            LIstDetails.setName(g.getString("USER_NAME"));
//                            LIstDetails.setPhone(g.getString("PHONE"));
//                            LIstDetails.setId(g.getString("ID"));
//                            LIstDetails.setStatus(g.getString("ORDER_STATUS"));
//                            if(g.getString("ORDER_STATUS").equals("F"))
//                            {
//                                if(del)
//                                {
//                                    LIstDetails.setShow("true");
//                                    del=false;
//                                }
//                                else
//                                {
//                                    LIstDetails.setShow("false");
//                                }
//                            }
//                            if(g.getString("ORDER_STATUS").equals("O")) {
//                                if(ma)
//                                {
//                                    LIstDetails.setShow("true");
//                                    ma=false;
//                                }
//                                else
//                                {
//                                    LIstDetails.setShow("false");
//                                }
//                            }
//                            if(g.getString("ORDER_STATUS").equals("P"))
//                            {
//                                if(can)
//                                {
//                                    LIstDetails.setShow("true");
//                                    can=false;
//                                }
//                                else
//                                {
//                                    LIstDetails.setShow("false");
//                                }
//
//                            }
//                            if(g.getString("ORDER_STATUS").equals("S"))
//                            {
//                                if(can)
//                                {
//                                    LIstDetails.setShow("true");
//                                    can=false;
//                                }
//                                else
//                                {
//                                    LIstDetails.setShow("false");
//                                }
//
//                            }
//                            if (g.getString("ORDER_STATUS").equals("X")) {
//                                if(pos)
//                                {
//                                    LIstDetails.setShow("true");
//                                    pos=false;
//                                }
//                                else
//                                {
//                                    LIstDetails.setShow("false");
//                                }
//                            }
//                            System.out.println("gnvnvnvf" + g.getString("ORDER_STATUS"));
//                            orderArrayList.add(LIstDetails);
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                try {
//                    if(json.getString("ORDER_LIST").equals("nodata"))
//                    {
//                        nameTextView.setText(data_name.toString());
//                        dateTextView.setText(data_date.toString());
//                        orderListView.setVisibility(View.GONE);
//                        noOrderTextView.setVisibility(View.VISIBLE);
//
//                    }
//                    else
//                    {
//                        nameTextView.setText(data_name.toString());
//                        dateTextView.setText(data_date.toString());
//                        orderListView.setVisibility(View.VISIBLE);
//                        noOrderTextView.setVisibility(View.GONE);
//
//                    orderListViewAdapter.notifyDataSetChanged();
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                pullRefreshLayout.setRefreshing(false);
//                super.onPostExecute(s);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//        }
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
            finish();
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
        if (text==null) {
            welcome_pass();
        }
        else {
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
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
        pass.setTypeface(myFont);
        confrmPass.setTypeface(myFont);
        pass.setHint("ENTER PIN");
        confrmPass.setHint("CONFIRM PIN");

        confrmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    new_pass="";
                    new_pass=pass.getText().toString();
                    if(new_pass.equals(""))
                    {
                        pass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                        pass.setHint("PLEASE ENTER NEW PIN");
                    }
                    else if(confrmPass.getText().toString().equalsIgnoreCase(""))
                    {
                        pass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        confrmPass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                        confrmPass.setHint("PLEASE CONFIRM YOUR PIN");
                    }
                    else if(!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString()))
                    {
                        pass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                        confrmPass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                        Toast.makeText(MainActivity.this, "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        pass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        confrmPass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        settings = getSharedPreferences(PREFS_NAME, 0); //1
                        editor = settings.edit(); //2
                        editor.putString(PREFS_SET, new_pass); //3
                        editor.commit();
                        dialog.dismiss();
                        get_log_data();
                    }
                }
                return false;
            }
        });

        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass="";
                new_pass=pass.getText().toString();
                if(new_pass.equals(""))
                {
                    pass.setHint("PLEASE ENTER NEW PIN");
                }
                else if(confrmPass.getText().toString().equalsIgnoreCase(""))
                {
                    confrmPass.setHint("PLEASE CONFIRM YOUR PIN");
                }
                else if(!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
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
        pass.setHint("Enter PIN");
        confrmPass.setHint("Confirm PIN");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = "";
                new_pass = pass.getText().toString();
                if (new_pass.equals("")) {
                    pass.setHint("Please Enter New PIN");
                } else if (confrmPass.getText().toString().equalsIgnoreCase("")) {
                    confrmPass.setHint("Please Confirm your PIN");
                } else if (!confrmPass.getText().toString().equalsIgnoreCase(pass.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
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
        pass.setHint("ENTER PIN");
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
        pass.setTypeface(myFont);

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
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
                        pass.setHint("WRONG PIN!! TRY AGAIN.");
                    }
                }
                return false;
            }
        });


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
                    pass.setHint("WWRONG PIN!! TRY AGAIN.");
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

//    @Override
//    public boolean onQueryTextSubmit(String query) {
////        searchView.clearFocus();
////        return true;
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
////        orderListViewAdapter.getFilter().filter(newText);
//        return false;
//    }



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
//            params.add(new BasicNameValuePair("did", "546457"));
//            params.add(new BasicNameValuePair("key", "gvJeU4Dh9372b18b77b7c704343fc55b2be4577c"));
//            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
//            params.add(new BasicNameValuePair("action", "validate"));
            System.out.println(c_key);
            json = jParser.makeHttpRequest(validate, "GET", params);
//    try {
//        res = json.getString("key");
//        System.out.println(res);
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            Boolean del=true;
            Boolean pos=true;
            Boolean can=true;
            Boolean ma=true;
            String message = "";
            boolean status;
            JSONObject response;
            try {
                response = new JSONObject(s);
                status =  response.getBoolean("status");
                message = response.getString("message");
                if(status){
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    JSONObject data =  response.getJSONObject("data");
                    JSONArray orderListingJSONArray = data.getJSONArray("ORDER_LIST");
                    if(orderListingJSONArray.length()==0)
                    {
                        System.out.println("LIST IS EMPTY");
                        data_name=data.getString("DRIVER_NAME");
                        data_date=data.getString("DATE");
                        data_img=data.getString("BARCODE");
                        System.out.println(data_name.toString());
                        System.out.println(data_date.toString());
                    }
                    else
                    {
//                        JSONArray ja=json.getJSONArray("ORDER_LIST");
                        System.out.println(orderListingJSONArray.toString());
                        data_name=data.getString("DRIVER_NAME");
                        data_date=data.getString("DATE");
                        data_img=data.getString("BARCODE");
                        System.out.println(data_name.toString());
                        System.out.println(data_date.toString());
                        for (int i = 0; i < orderListingJSONArray.length(); i++) {
                            JSONObject g = orderListingJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("ORDER_STATUS"));
                            if(g.getString("ORDER_STATUS").equals("F"))
                            {
                                if(del)
                                {
                                    order.setShow("true");
                                    del=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }
                            }
                            if(g.getString("ORDER_STATUS").equals("O")) {
                                if(ma)
                                {
                                    order.setShow("true");
                                    ma=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }
                            }
                            if(g.getString("ORDER_STATUS").equals("P"))
                            {
                                if(can)
                                {
                                    order.setShow("true");
                                    can=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }

                            }
                            if(g.getString("ORDER_STATUS").equals("S"))
                            {
                                if(can)
                                {
                                    order.setShow("true");
                                    can=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }

                            }
                            if (g.getString("ORDER_STATUS").equals("X")) {
                                if(pos)
                                {
                                    order.setShow("true");
                                    pos=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }
                            }

                            System.out.println("gnvnvnvf" + g.getString("ORDER_STATUS"));

                            orderArrayList.add(order);
                        }
                    }

                    nameTextView.setText(data_name.toString());
                    dateTextView.setText(data_date.toString());
                    System.out.println("jdhf"+data_img);
                    byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    barcodeImageView.setImageBitmap(bitmap);
//                orderListView.setVisibility(View.VISIBLE);
                    noOrderTextView.setVisibility(View.GONE);
//                orderListViewAdapter.notifyDataSetChanged();

                    startService(new Intent(MainActivity.this,FetchLocationCordinatesService.class));
                    startService(new Intent(MainActivity.this,PushLocationDataToServerService.class));

                }
                else{
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    login_prefs_editor = login_prefs.edit(); //2
                    login_prefs_editor.clear();
                    login_prefs_editor.commit();
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressView.setVisibility(View.GONE);
            pleaseWaitTextView.setVisibility(View.GONE);
            bodyRelativeLayout.setVisibility(View.VISIBLE);
            headerRelativeLayout.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE );



//            if(res.equals("true"))
//            {
//                startService(new Intent(MainActivity.this,FetchLocationCordinatesService.class));
//                startService(new Intent(MainActivity.this,PushLocationDataToServerService.class));
//                new detail().execute();
//            }
//            else if(res.equals("false"))
//            {
//                login_prefs_editor = login_prefs.edit(); //2
//                login_prefs_editor.clear();
//                login_prefs_editor.commit();
//                Intent i=new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(), "Server Error , Please Try Again", Toast.LENGTH_LONG).show();
//
//            }
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




//    private class detail extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... paramz) {
//            Boolean del=true;
//            Boolean pos=true;
//            Boolean can=true;
//            Boolean ma=true;
//            JSONParser jParser = new JSONParser();
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//            params.add(new BasicNameValuePair("did",txt_did));
//            params.add(new BasicNameValuePair("action", "list"));
//            params.add(new BasicNameValuePair("access", "1b6b1a4a42b9c9811e3ebc264080e465"));
//
//            json = jParser.makeHttpRequest(url, "POST", params);
//            System.out.println(json.toString());
//
//
//            try {
//
//                if(json.getString("ORDER_LIST").equals("nodata"))
//                {
//                    System.out.println("NO_DATA_hggnb");
//                    data_name=json.getString("DRIVER_NAME");
//                    data_date=json.getString("DATE");
//                    data_img=json.getString("BARCODE");
//                    System.out.println(data_name.toString());
//                    System.out.println(data_date.toString());
//                }
//                else
//                {
//                    JSONArray ja=json.getJSONArray("ORDER_LIST");
//                    System.out.println(ja.toString());
//                    data_name=json.getString("DRIVER_NAME");
//                    data_date=json.getString("DATE");
//                    data_img=json.getString("BARCODE");
//                    System.out.println(data_name.toString());
//                    System.out.println(data_date.toString());
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject g = ja.getJSONObject(i);
//                        Order LIstDetails = new Order();
//                        LIstDetails.setOrder_no(g.getString("ORDER_NUM"));
//                        LIstDetails.setName(g.getString("USER_NAME"));
//                        LIstDetails.setPhone(g.getString("PHONE"));
//                        LIstDetails.setId(g.getString("ID"));
//                        LIstDetails.setStatus(g.getString("ORDER_STATUS"));
//                        if(g.getString("ORDER_STATUS").equals("F"))
//                        {
//                            if(del)
//                            {
//                                LIstDetails.setShow("true");
//                                del=false;
//                            }
//                            else
//                            {
//                                LIstDetails.setShow("false");
//                            }
//                        }
//                        if(g.getString("ORDER_STATUS").equals("O")) {
//                            if(ma)
//                            {
//                                LIstDetails.setShow("true");
//                                ma=false;
//                            }
//                            else
//                            {
//                                LIstDetails.setShow("false");
//                            }
//                        }
//                        if(g.getString("ORDER_STATUS").equals("P"))
//                        {
//                            if(can)
//                            {
//                                LIstDetails.setShow("true");
//                                can=false;
//                            }
//                            else
//                            {
//                                LIstDetails.setShow("false");
//                            }
//
//                        }
//                        if(g.getString("ORDER_STATUS").equals("S"))
//                        {
//                            if(can)
//                            {
//                                LIstDetails.setShow("true");
//                                can=false;
//                            }
//                            else
//                            {
//                                LIstDetails.setShow("false");
//                            }
//
//                        }
//                        if (g.getString("ORDER_STATUS").equals("X")) {
//                            if(pos)
//                            {
//                                LIstDetails.setShow("true");
//                                pos=false;
//                            }
//                            else
//                            {
//                                LIstDetails.setShow("false");
//                            }
//                        }
//
//                        System.out.println("gnvnvnvf" + g.getString("ORDER_STATUS"));
//
//                        orderArrayList.add(LIstDetails);
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            try {
//                if(json.getString("ORDER_LIST").equals("nodata"))
//                {
//                    nameTextView.setText(data_name.toString());
//                    dateTextView.setText(data_date.toString());
//                    byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                    barcodeImageView.setImageBitmap(bitmap);
////                    orderListView.setVisibility(View.GONE);
//                    noOrderTextView.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    nameTextView.setText(data_name.toString());
//                    dateTextView.setText(data_date.toString());
//                    System.out.println("jdhf"+data_img);
//                    byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                    barcodeImageView.setImageBitmap(bitmap);
////                    orderListView.setVisibility(View.VISIBLE);
//                    noOrderTextView.setVisibility(View.GONE);
////                    orderListViewAdapter.notifyDataSetChanged();
//                }
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            progressView.setVisibility(View.GONE);
//            pleaseWaitTextView.setVisibility(View.GONE);
//            bodyRelativeLayout.setVisibility(View.VISIBLE);
//            headerRelativeLayout.setVisibility(View.VISIBLE);
//            searchView.setVisibility(View.VISIBLE );
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressView.setVisibility(View.VISIBLE);
//            progressView.setShowArrow(true);
//            pleaseWaitTextView.setVisibility(View.VISIBLE);
//            bodyRelativeLayout.setVisibility(View.GONE);
//            headerRelativeLayout.setVisibility(View.GONE);
//            searchView.setVisibility(View.GONE);
//            super.onPreExecute();
//        }
//    }


}