package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import dapp.com.awok.awokdriversapp.Services.TService;
import dapp.com.awok.awokdriversapp.Utils.Callbacks;
import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;
import dapp.com.awok.awokdriversapp.Utils.DeviceAdminDemo;
import dapp.com.awok.awokdriversapp.Utils.JSONParser;
import dapp.com.awok.awokdriversapp.Utils.PullRefreshLayout;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends Fragment implements SearchView.OnQueryTextListener {


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
    String data_name,data_date,data_img,txt_pass,txt_did,new_pass,res,txt_fname,txt_lname, data_area, data_repId;
    private int swipe_Max_Distance = 350;
    private static String validate; //= "http://dev.alifca.com/d_login.php";
    private static String url; //= "http://dev.alifca.com/d_login.php";
    boolean update = false;
    private static String optionsURL;
    private static String postCallLogURL;
    public static final String APP_LOGIN = "PREFS_APP_LOGIN";
    public static final String APP_LOG_DID = "PREFS_APP_DID";
    public static final String APP_LOG_KEY = "PREFS_APP_KEY";
    public static final String APP_LOG_FNAME = "PREFS_APP_FNAME";
    public static final String APP_LOG_LNAME = "PREFS_APP_LNAME";
    SharedPreferences login_prefs;
    SharedPreferences.Editor login_prefs_editor;
    String json;
    private LinearLayout mTabsLinearLayout;


    public static final String PREFS_NAME = "APP_PREFS";
    public static final String PREFS_SET = "APP_PREFS_SET";
    SharedPreferences settings, completeLog;
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
    String serv_txt, COMPLETE_CALL_LOG;
    ImageView g;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    PagerSlidingTabStrip mTabStrip;
    LinearLayout lay_main_tabstrip;
    private static final int REQUEST_CODE = 0;
    private DevicePolicyManager mDPM;
    private ComponentName mAdminName;


    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */


    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Order order) {
        }

        @Override
        public void updateTitle(String name, String date, String area) {

        }

        @Override
        public void updateListing() {

        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.main_page_list, container, false);
        update = false;
        lay_main_tabstrip=(LinearLayout)rootView.findViewById(R.id.lay_main_tabstrip);
        mTabStrip = (PagerSlidingTabStrip ) rootView.findViewById(R.id.pager_tab_strip);
        mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        cd = new ConnectionDetector(getActivity().getApplicationContext());

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        orderListView = (ListView ) rootView.findViewById(R.id.orderListView);
        pleaseWaitTextView =(TextView)rootView.findViewById(R.id.waitTextView);
        progressView =(CircleProgressBar)rootView.findViewById(R.id.progress1);
        noOrderTextView =(TextView)rootView.findViewById(R.id.noOrderTextView);
        COMPLETE_CALL_LOG = "completeLog";
        final View activityRootView = rootView.findViewById(R.id.mainRelativeLayout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff<100) { // if more than 100 pixels, its probably a keyboard...
                    int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
                    View searchPlate = searchView.findViewById(searchPlateId);
//                    searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                    searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
//                    System.out.println("Keyboard check called");
                }
            }
        });


        searchView = (SearchView) rootView.findViewById(R.id.search_view);

        bodyRelativeLayout =(RelativeLayout)rootView.findViewById(R.id.bodyRelativeLayout);
        headerRelativeLayout=(RelativeLayout)rootView.findViewById(R.id.headerRelativeLayout);
        barcodeImageView =(ImageView)rootView.findViewById(R.id.barcodeImageView);
        mainRelativeLayout =(RelativeLayout)rootView.findViewById(R.id.mainRelativeLayout);
        g=(ImageView)rootView.findViewById(R.id.imageView);
        nameTextView =(TextView)rootView.findViewById(R.id.nameTextView);
        dateTextView =(TextView)rootView.findViewById(R.id.dateTextView);


        mainSearchAdapter = new MainSearchAdapter(getActivity(), orderArrayList,getActivity());
        orderListView.setAdapter(mainSearchAdapter);

        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getChildFragmentManager());

        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int x = 0; x < mTabsLinearLayout.getChildCount(); x++) {
                    if (x == i) {
                        TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
                        switch (i) {
                            case 0:
                                Log.v("PageViewerAdapter", "position: " + i);
                                mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
                                tv.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                                break;

                            case 1:
                                Log.v("PageViewerAdapter", "position: " + i);
                                mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_red_dark));
                                tv.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                break;
                            case 2:
                                Log.v("PageViewerAdapter", "position: " + i);
                                mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_orange_dark));
                                tv.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                                break;
                            case 3:
                                Log.v("PageViewerAdapter", "position: " + i);
                                mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_green_dark));
                                tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                break;
                            case 4:
                                Log.v("PageViewerAdapter", "position: " + i);
                                mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.darker_gray));
                                tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                                break;
                        }
                    } else {
                        TextView tv = (TextView) mTabsLinearLayout.getChildAt(x);
                        tv.setTextColor(getResources().getColor(android.R.color.black));
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabStrip.setViewPager(mViewPager);
        mTabsLinearLayout = ((LinearLayout)mTabStrip.getChildAt(0));
        mTabStrip.setIndicatorHeight(5);
        for(int i=0; i < mTabsLinearLayout.getChildCount(); i++){
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            tv.setTextAppearance(getActivity(), R.style.normalText);
            tv.setTextSize(getResources().getDimension(R.dimen.textsize));
            if(i == 0){
                tv.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                tv.setTextColor(getResources().getColor(android.R.color.black));
            }
        }
        server_pref = getActivity().getSharedPreferences(PREFS_SERVER_NAME, Context.MODE_PRIVATE);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        if(serv_txt==null)
        {
            server_pref = getActivity().getSharedPreferences(PREFS_SERVER_NAME, 0); //1
            serv_editor = server_pref.edit(); //2
            serv_editor.putString(PREFS_SERVER_VALUE, "http://dev.alifca.com/api/drivers/"); //3
            serv_editor.commit();
        }
        context=getActivity().getApplicationContext();




        server_pref = getActivity().getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        validate=serv_txt+"listing/";
        url=serv_txt+"login/";
        optionsURL=serv_txt+"options/";
        postCallLogURL=serv_txt+"uploads_call_log/";
        progressView.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        progressView.setShowArrow(true);
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        final EditText e = (EditText)searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
                    View searchPlate = searchView.findViewById(searchPlateId);
                    if(e.getText().toString().equalsIgnoreCase("")){
//                        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_red));
                        searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    else{
//                        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
                    }


                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(this);

        int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) searchView.findViewById(closeButtonId);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
//        searchPlate.setVisibility(View.GONE);
//        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
        int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchText.setText("");
            }
        });


        setup();

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




//        try {
//            // Initiate DevicePolicyManager.
//            mDPM = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
//            mAdminName = new ComponentName(getActivity(), DeviceAdminDemo.class);
//
//            if (!mDPM.isAdminActive(mAdminName)) {
//                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
//                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
//                startActivityForResult(intent, REQUEST_CODE);
//            } else {
//                 mDPM.lockNow();
//                 Intent intent = new Intent(getActivity(),
//                 TService.class);
//                 getActivity().startService(intent);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }



        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            Intent intent = new Intent(getActivity(), TService.class);
            getActivity().startService(intent);
        }
    }

    @Override
    public void onResume() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

    @Override
    public void onStart() {







        noOrderTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                get_data();
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

        super.onStart();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        searchView.clearFocus();
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
//        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
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
            searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
//            searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
        }
        else
        {
            orderListView.setVisibility(View.VISIBLE);
            lay_main_tabstrip.setVisibility(View.GONE);
            mainSearchAdapter.getFilter().filter(newText);
            int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            View searchPlate = searchView.findViewById(searchPlateId);
            searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
//            searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
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
            return 5;
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
                case 4:
                    title =  "INVALID";
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

        public DemoObjectFragment() {

        }

        ;

        public DemoObjectFragment(ArrayList<Order> orderList) {
            this.orderList = orderList;
        }

        //call this method to update fragments in ViewPager dynamically
        public void update(int position, Activity act, Context mContext) {
            orderList=orderArrayList;
            newList = new ArrayList<Order>();
            switch (position) {
                case 0:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("PENDING")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("CANCELLED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("POSTPONED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("DELIVERED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("INVALID")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
            }
            orderListViewAdapter = new OrderListViewAdapter(act, newList, mContext);
            orderListView.setAdapter(orderListViewAdapter);
//            orderListViewAdapter = new OrderListViewAdapter(getActivity(), newList, getActivity());
//            orderListView.setAdapter(orderListViewAdapter);
            update = false;
        }

//        TextView testTextView;


        /*@Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            (MainActivity)getActivity().
            return true;
        }*/
        int position = 0;
        ArrayList<Order> newList;
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
                }*/ {

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
//                            get_data();
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
                    new ValidateFragment().execute();
                }
            });
            newList = new ArrayList<Order>();
            position = fragmentPosition;
            switch (fragmentPosition) {
                case 0:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("PENDING")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("CANCELLED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("POSTPONED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("DELIVERED")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < orderList.size(); i++) {
                        if (orderList.get(i).getStatus().equalsIgnoreCase("INVALID")) {
                            newList.add(orderList.get(i));
                        }
                    }
                    break;
            }
            orderListViewAdapter = new OrderListViewAdapter(getActivity(), newList, getActivity());
            orderListView.setAdapter(orderListViewAdapter);
//            ((TextView) rootView.findViewById(R.id.text1)).setText(
//                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }

        public class ValidateFragment extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... paramz) {
                String c_did,c_key;
                JSONParser jParser=new JSONParser();
                login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
                c_did = login_prefs.getString(APP_LOG_DID, null);
                c_key = login_prefs.getString(APP_LOG_KEY, null);
                System.out.println("key"+c_key);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("did", c_did));
                params.add(new BasicNameValuePair("key", c_key));
                System.out.println(c_key);
                json = jParser.makeHttpRequest(validate, "GET", params);
                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                pullRefreshLayout.setRefreshing(false);
                Boolean del=true;
                Boolean pos=true;
                Boolean can=true;
                Boolean ma=true;
                Boolean inv=true;
                String message = "";
                String repotId = "";
                boolean status;
                JSONObject response;
                try {
                    response = new JSONObject(s);
                    status =  response.getBoolean("status");
                    message = response.getString("message");
                    if(status){
                        orderArrayList.clear();
                        orderListViewAdapter.clear();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        JSONObject data =  response.getJSONObject("data");
                        JSONObject oderListObject =  data.getJSONObject("ORDER_LIST");

                        data_name=data.getString("DRIVER_NAME");
                        data_date=data.getString("DATE");
                        data_img=data.getString("BARCODE");
                        data_area=data.getString("DRIVER_LOCATION");
                        repotId = data.getString("REPORT_ID");
                        data_repId = data.getString("REPORT_ID");
                        login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = login_prefs.edit();
                        edit.putString("reportId", repotId);
                        edit.commit();
                        System.out.println(data_name.toString());
                        System.out.println(data_date.toString());



                        JSONArray deliveredJSONArray = oderListObject.getJSONArray("DELIVERED");
                        JSONArray pendingJSONArray = oderListObject.getJSONArray("PENDING");
                        JSONArray postponedJSONArray = oderListObject.getJSONArray("POSTPONED");
                        JSONArray invalidJSONArray = oderListObject.getJSONArray("INVALID");
                        JSONArray cancelledJSONArray = oderListObject.getJSONArray("CANCELLED");
//                    if(orderListingJSONArray.length()==0)
//                    {
//                        System.out.println("LIST IS EMPTY");
//                        data_name=data.getString("DRIVER_NAME");
//                        data_date=data.getString("DATE");
//                        data_img=data.getString("BARCODE");
//                        System.out.println(data_name.toString());
//                        System.out.println(data_date.toString());
//                    }
//                    else
//                    {
//                        JSONArray ja=json.getJSONArray("ORDER_LIST");

                        for (int i = 0; i < deliveredJSONArray.length(); i++) {
                            JSONObject g = deliveredJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                            if(del)
                            {
                                order.setShow("true");
                                del=false;
                            }
                            else
                            {
                                order.setShow("false");
                            }
                            orderArrayList.add(order);
                        }


                        for (int i = 0; i < pendingJSONArray.length(); i++) {
                            JSONObject g = pendingJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
//                            order.setPhone("00971562022892");
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                            if(ma)
                            {
                                order.setShow("true");
                                ma=false;
                            }
                            else
                            {
                                order.setShow("false");
                            }
                            orderArrayList.add(order);
                        }

                        for (int i = 0; i < cancelledJSONArray.length(); i++) {
                            JSONObject g = cancelledJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                            if(can)
                            {
                                order.setShow("true");
                                can=false;
                            }
                            else
                            {
                                order.setShow("false");
                            }
                            orderArrayList.add(order);
                        }


                        for (int i = 0; i < postponedJSONArray.length(); i++) {
                            JSONObject g = postponedJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                            if(pos)
                            {
                                order.setShow("true");
                                pos=false;
                            }
                            else
                            {
                                order.setShow("false");
                            }
                            orderArrayList.add(order);
                        }

                        for (int i = 0; i < invalidJSONArray.length(); i++) {
                            JSONObject g = invalidJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                            if(inv)
                            {
                                order.setShow("true");
                                inv=false;
                            }
                            else
                            {
                                order.setShow("false");
                            }
                            orderArrayList.add(order);
                        }





//                        nameTextView.setText(data_name.toString());
//                        dateTextView.setText(data_date.toString());
//                        System.out.println("jdhf"+data_img);
//                        byte[] imageAsBytes = Base64.decode(data_img.getBytes(), Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                        barcodeImageView.setImageBitmap(bitmap);
//                orderListView.setVisibility(View.VISIBLE);
//                        noOrderTextView.setVisibility(View.GONE);
//                orderListViewAdapter.notifyDataSetChanged();

//                        getActivity().startService(new Intent(getActivity(), FetchLocationCordinatesService.class));
//                        getActivity().startService(new Intent(getActivity(), PushLocationDataToServerService.class));

                        nameTextView.setText(data_name.toString());
                        dateTextView.setText(data_date.toString());
                        orderListView.setVisibility(View.VISIBLE);
                        noOrderTextView.setVisibility(View.GONE);
//                        orderListViewAdapter.notifyDataSetChanged();
                        newList = new ArrayList<Order>();
                        switch (position) {
                            case 0:
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getStatus().equalsIgnoreCase("PENDING")) {
                                        newList.add(orderList.get(i));
                                    }
                                }
                                break;

                            case 1:
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getStatus().equalsIgnoreCase("CANCELLED")) {
                                        newList.add(orderList.get(i));
                                    }
                                }
                                break;
                            case 2:
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getStatus().equalsIgnoreCase("POSTPONED")) {
                                        newList.add(orderList.get(i));
                                    }
                                }
                                break;
                            case 3:
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getStatus().equalsIgnoreCase("DELIVERED")) {
                                        newList.add(orderList.get(i));
                                    }
                                }
                                break;
                            case 4:
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getStatus().equalsIgnoreCase("INVALID")) {
                                        newList.add(orderList.get(i));
                                    }
                                }
                                break;
                        }
                        orderListViewAdapter = new OrderListViewAdapter(getActivity(), newList, getActivity());
                        orderListView.setAdapter(orderListViewAdapter);
                    }

                    else{
                        nameTextView.setText(data_name.toString());
                        dateTextView.setText(data_date.toString());
                        orderListView.setVisibility(View.GONE);
                        noOrderTextView.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
//                        login_prefs_editor = login_prefs.edit(); //2
//                        login_prefs_editor.clear();
//                        login_prefs_editor.commit();
//                        Intent i=new Intent(getActivity(),LoginActivity.class);
//                        startActivity(i);
//                        getActivity().finish();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                progressView.setVisibility(View.GONE);
//                pleaseWaitTextView.setVisibility(View.GONE);
//                bodyRelativeLayout.setVisibility(View.VISIBLE);
//                headerRelativeLayout.setVisibility(View.VISIBLE);
//                searchView.setVisibility(View.VISIBLE );



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
//                progressView.setVisibility(View.VISIBLE);
//                progressView.setShowArrow(true);
//                pleaseWaitTextView.setVisibility(View.VISIBLE);
//                bodyRelativeLayout.setVisibility(View.GONE);
//                headerRelativeLayout.setVisibility(View.GONE);
//                searchView.setVisibility(View.GONE);
                super.onPreExecute();
            }
        }
    }

    private void switch_v() {
        switch_prefs = getActivity().getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE);
        String val = switch_prefs.getString(PREFS_SWITCH_VALUE, null);
        System.out.println("PREF_switch" + val);

        if (val==null)
        {
            System.out.println("NUL:DDFF");
            switch_prefs = getActivity().getSharedPreferences(PREFS_SWITCH, 0); //1
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
        switch_prefs_call = getActivity().getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
        String valz = switch_prefs_call.getString(PREFS_SWITCH_VALUE_CALL, null);
        System.out.println("PREF_switch" + valz);

        if (valz==null)
        {
            System.out.println("NUL:DDFF");
            switch_prefs_call = getActivity().getSharedPreferences(PREFS_SWITCH_CALL, 0); //1
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
        login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
        txt_pass = login_prefs.getString(APP_LOG_KEY, null);
        txt_did = login_prefs.getString(APP_LOG_DID, null);
        txt_fname = login_prefs.getString(APP_LOG_FNAME, null);
        txt_lname = login_prefs.getString(APP_LOG_LNAME, null);
        if(txt_pass==null)
        {
            Intent i=new Intent(getActivity(),LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        }
        else
        {
            isInternetPresent = cd.isConnectingToInternet();
            if(isInternetPresent) {

                new Options().execute();
                new Validate().execute();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Please Connect to Data Services and Restart App", Toast.LENGTH_LONG).show();
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
        settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
        settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        confrmPass=(EditText)dialog.findViewById(R.id.confirm_password);
        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NotoSans-Regular.ttf");
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
                        Toast.makeText(getActivity(), "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        pass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        confrmPass.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
                        settings = getActivity().getSharedPreferences(PREFS_NAME, 0); //1
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
                    Toast.makeText(getActivity(), "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    settings = getActivity().getSharedPreferences(PREFS_NAME, 0); //1
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
        final Dialog dialog = new Dialog(getActivity());
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
                    Toast.makeText(getActivity(), "Your confirmed PIN does not match initially entered PIN", Toast.LENGTH_SHORT).show();
                } else {
                    settings = getActivity().getSharedPreferences(PREFS_NAME, 0); //1
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
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enter_admin_password);
        dialog.setCanceledOnTouchOutside(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("ENTER PIN");
        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NotoSans-Regular.ttf");
        pass.setTypeface(myFont);

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    new_pass="";
                    new_pass=pass.getText().toString();
                    settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    String next = settings.getString(PREFS_SET, null);
                    System.out.println("PREF" + next);
                    if (next.equals(new_pass))
                    {
                        Intent i=new Intent(getActivity(),SettingsActivity.class);
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
                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String next = settings.getString(PREFS_SET, null);
                System.out.println("PREF" + next);
                if (next.equals(new_pass))
                {
                    Intent i=new Intent(getActivity(),SettingsActivity.class);
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


    public class PostCallLog extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            String c_did,c_key;
            JSONParser jParser=new JSONParser();
            SharedPreferences callLogPrefs = getActivity().getSharedPreferences(COMPLETE_CALL_LOG, Context.MODE_PRIVATE);
            String data = "";
            if(callLogPrefs.contains(COMPLETE_CALL_LOG)){
                data = callLogPrefs.getString(COMPLETE_CALL_LOG, null);
            }
            login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            c_did = login_prefs.getString(APP_LOG_DID, null);
            c_key = login_prefs.getString(APP_LOG_KEY, null);
            System.out.println("key" + c_key);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            params.add(new BasicNameValuePair("rid", data_repId));
            params.add(new BasicNameValuePair("data", data));
            System.out.println("call log data posted: " + data);
            System.out.println("call log data rid: " + data_repId);
            if(data.equalsIgnoreCase("")){
                return "";
            }
            else{
                return jParser.makeHttpRequest(postCallLogURL, "POST", params);
            }

        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equalsIgnoreCase("")){

            }
            else{
                System.out.println("Call log data resonse: "+s);
                String message = "";
                boolean status;
                JSONObject response;
                try {
                    response = new JSONObject(s);
                    status = response.getBoolean("status");
                    message = response.getString("message");
                    if (status) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        SharedPreferences callLogPrefs = getActivity().getSharedPreferences(COMPLETE_CALL_LOG, Context.MODE_PRIVATE);
                        callLogPrefs.edit().clear();
//                        login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edit = login_prefs.edit();
//                        edit.putString("options", s);
//                        edit.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.onPostExecute(s);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    public class Options extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            String c_did,c_key;
            JSONParser jParser=new JSONParser();
            login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            c_did = login_prefs.getString(APP_LOG_DID, null);
            c_key = login_prefs.getString(APP_LOG_KEY, null);
            System.out.println("key"+c_key);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            System.out.println(c_key);
            json = jParser.makeHttpRequest(optionsURL, "GET", params);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            String message = "";
            boolean status;
            JSONObject response;
            try {
                response = new JSONObject(s);
                status = response.getBoolean("status");
                message = response.getString("message");
                if (status) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = login_prefs.edit();
                    edit.putString("options", s);
                    edit.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public class Validate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            String c_did,c_key;
            JSONParser jParser=new JSONParser();
            login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            c_did = login_prefs.getString(APP_LOG_DID, null);
            c_key = login_prefs.getString(APP_LOG_KEY, null);
            System.out.println("key"+c_key);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            System.out.println(c_key);
            json = jParser.makeHttpRequest(validate, "GET", params);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            Boolean del=true;
            Boolean pos=true;
            Boolean can=true;
            Boolean ma=true;
            Boolean inv=true;
            String message = "";
            String repotId = "";
            boolean status;
            JSONObject response;
            try {
                response = new JSONObject(s);
                status =  response.getBoolean("status");
                message = response.getString("message");
                if(status){
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    orderArrayList.clear();
                    JSONObject data =  response.getJSONObject("data");
                    JSONObject oderListObject =  data.getJSONObject("ORDER_LIST");

                    data_name=data.getString("DRIVER_NAME");
                    data_date=data.getString("DATE");
                    data_img=data.getString("BARCODE");
                    data_area=data.getString("DRIVER_LOCATION");
                    repotId = data.getString("REPORT_ID");
                    data_repId = data.getString("REPORT_ID");
                    login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = login_prefs.edit();
                    edit.putString("reportId", repotId);
                    edit.commit();
                    System.out.println(data_name.toString());
                    System.out.println(data_date.toString());
                    mCallbacks.updateTitle(data_name, data_date, data_area);


                    JSONArray deliveredJSONArray = oderListObject.getJSONArray("DELIVERED");
                    JSONArray pendingJSONArray = oderListObject.getJSONArray("PENDING");
                    JSONArray postponedJSONArray = oderListObject.getJSONArray("POSTPONED");
                    JSONArray invalidJSONArray = oderListObject.getJSONArray("INVALID");
                    JSONArray cancelledJSONArray = oderListObject.getJSONArray("CANCELLED");
//                    if(orderListingJSONArray.length()==0)
//                    {
//                        System.out.println("LIST IS EMPTY");
//                        data_name=data.getString("DRIVER_NAME");
//                        data_date=data.getString("DATE");
//                        data_img=data.getString("BARCODE");
//                        System.out.println(data_name.toString());
//                        System.out.println(data_date.toString());
//                    }
//                    else
//                    {
//                        JSONArray ja=json.getJSONArray("ORDER_LIST");

                        for (int i = 0; i < deliveredJSONArray.length(); i++) {
                            JSONObject g = deliveredJSONArray.getJSONObject(i);
                            Order order = new Order();
                            order.setOrder_no(g.getString("ORDER_NUM"));
                            order.setName(g.getString("USER_NAME"));
                            order.setPhone(g.getString("PHONE"));
                            order.setPhone2(g.getString("PHONE_2"));
                            order.setId(g.getString("ORDER_ID"));
                            order.setStatus(g.getString("STATUS"));
                                if(del)
                                {
                                    order.setShow("true");
                                    del=false;
                                }
                                else
                                {
                                    order.setShow("false");
                                }
                            orderArrayList.add(order);
                        }


                    for (int i = 0; i < pendingJSONArray.length(); i++) {
                        JSONObject g = pendingJSONArray.getJSONObject(i);
                        Order order = new Order();
                        order.setOrder_no(g.getString("ORDER_NUM"));
                        order.setName(g.getString("USER_NAME"));
                        order.setPhone(g.getString("PHONE"));
                        order.setPhone2(g.getString("PHONE_2"));
//                        order.setPhone("00971562022892");
                        order.setId(g.getString("ORDER_ID"));
                        order.setStatus(g.getString("STATUS"));
                        if(ma)
                        {
                            order.setShow("true");
                            ma=false;
                        }
                        else
                        {
                            order.setShow("false");
                        }
                        orderArrayList.add(order);
                    }

                    for (int i = 0; i < cancelledJSONArray.length(); i++) {
                        JSONObject g = cancelledJSONArray.getJSONObject(i);
                        Order order = new Order();
                        order.setOrder_no(g.getString("ORDER_NUM"));
                        order.setName(g.getString("USER_NAME"));
                        order.setPhone(g.getString("PHONE"));
                        order.setPhone2(g.getString("PHONE_2"));
                        order.setId(g.getString("ORDER_ID"));
                        order.setStatus(g.getString("STATUS"));
                        if(can)
                        {
                            order.setShow("true");
                            can=false;
                        }
                        else
                        {
                            order.setShow("false");
                        }
                        orderArrayList.add(order);
                    }


                    for (int i = 0; i < postponedJSONArray.length(); i++) {
                        JSONObject g = postponedJSONArray.getJSONObject(i);
                        Order order = new Order();
                        order.setOrder_no(g.getString("ORDER_NUM"));
                        order.setName(g.getString("USER_NAME"));
                        order.setPhone(g.getString("PHONE"));
                        order.setPhone2(g.getString("PHONE_2"));
                        order.setId(g.getString("ORDER_ID"));
                        order.setStatus(g.getString("STATUS"));
                        if(pos)
                        {
                            order.setShow("true");
                            pos=false;
                        }
                        else
                        {
                            order.setShow("false");
                        }
                        orderArrayList.add(order);
                    }

                    for (int i = 0; i < invalidJSONArray.length(); i++) {
                        JSONObject g = invalidJSONArray.getJSONObject(i);
                        Order order = new Order();
                        order.setOrder_no(g.getString("ORDER_NUM"));
                        order.setName(g.getString("USER_NAME"));
                        order.setPhone(g.getString("PHONE"));
                        order.setPhone2(g.getString("PHONE_2"));
                        order.setId(g.getString("ORDER_ID"));
                        order.setStatus(g.getString("STATUS"));
                        if(inv)
                        {
                            order.setShow("true");
                            inv=false;
                        }
                        else
                        {
                            order.setShow("false");
                        }
                        orderArrayList.add(order);
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

//                    getActivity().startService(new Intent(getActivity(), FetchLocationCordinatesService.class));
//                    getActivity().startService(new Intent(getActivity(), PushLocationDataToServerService.class));
//                    if(update){
                    mainSearchAdapter.notifyDataSetChanged();
                        mDemoCollectionPagerAdapter =
                                new DemoCollectionPagerAdapter(
                                        getChildFragmentManager());

                        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
                    int m = mViewPager.getCurrentItem();
                    mTabStrip.setIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
//                    if(searchView.isFocused()){
                        orderListView.setVisibility(View.GONE);
                        lay_main_tabstrip.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
                        View searchPlate = searchView.findViewById(searchPlateId);
                    searchPlate.setBackgroundColor(getResources().getColor(R.color.white));
//                        searchPlate.setBackground(getResources().getDrawable(R.drawable.edit_text_border_black));
//                    }
//                        int x = mViewPager.getCurrentItem();
////                        ((DemoObjectFragment)mDemoCollectionPagerAdapter.getItem(x)).update(x);
//                        Activity act = getActivity();
//                        Context context = getActivity();
//                                ((DemoObjectFragment) mDemoCollectionPagerAdapter.getItem(x)).update(x, act, context);
//                    }

                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    login_prefs_editor = login_prefs.edit(); //2
                    login_prefs_editor.clear();
                    login_prefs_editor.commit();
                    Intent i=new Intent(getActivity(),LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressView.setVisibility(View.GONE);
            pleaseWaitTextView.setVisibility(View.GONE);
            bodyRelativeLayout.setVisibility(View.VISIBLE);
//            headerRelativeLayout.setVisibility(View.VISIBLE);
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

            new PostCallLog().execute();
        }

        @Override
        protected void onPreExecute() {
            progressView.setVisibility(View.VISIBLE);
            progressView.setShowArrow(true);
            pleaseWaitTextView.setVisibility(View.VISIBLE);
            bodyRelativeLayout.setVisibility(View.GONE);
//            headerRelativeLayout.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            super.onPreExecute();
        }
    }





    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // TODO: replace with a real list adapter.
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                DummyContent.ITEMS));
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

//    @Override
//    public void onListItemClick(ListView listView, View view, int position, long id) {
//        super.onListItemClick(listView, view, position, id);
//
//        // Notify the active callbacks interface (the activity, if the
//        // fragment is attached to one) that an item has been selected.
//        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        orderListView.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void refreshListing() {
        System.out.println("HAVE TO REFRESH LISTING");
        update = true;
        new Validate().execute();

    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            orderListView.setItemChecked(mActivatedPosition, false);
        } else {
            orderListView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
