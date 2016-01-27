package dapp.com.awok.awokdriversapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dapp.com.awok.awokdriversapp.AppController;
import dapp.com.awok.awokdriversapp.Modals.AttachmentTypes;
import dapp.com.awok.awokdriversapp.Modals.CancelReasons;
import dapp.com.awok.awokdriversapp.Modals.Order;
import dapp.com.awok.awokdriversapp.Modals.PostponedReasons;
import dapp.com.awok.awokdriversapp.R;
import dapp.com.awok.awokdriversapp.Activities.dummy.DummyContent;
import dapp.com.awok.awokdriversapp.Utils.Callbacks;
import dapp.com.awok.awokdriversapp.Utils.ConnectionDetector;
import dapp.com.awok.awokdriversapp.Utils.JSONParser;
import dev.dworks.libs.asignature.SignatureView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

import static dapp.com.awok.awokdriversapp.R.color.white;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    List<PostponedReasons> postPonedreason=new ArrayList<PostponedReasons>();
    List<CancelReasons> cancelReasons=new ArrayList<CancelReasons>();
    List<AttachmentTypes> attTypes =new ArrayList<AttachmentTypes>();
    List<String> imageTypes =new ArrayList<String>();
    private static String url;
    private static String changeStatusURL;
    private static String addAttachmentURL;
    TextView orderDateValueTextView, customerNameValueTextView, mobileValueTextView, emirateValueTextView, locationValueTextView, addressValueTextView, totalPriceValueTextView, deliveryPriceValueTextView, nameOnCardValueTextView, cardNumberValueTextView;
    String s_order_date,s_cus_name,s_mobile,s_emirates,s_location,s_address,s_total_price,s_del_price,s_name_card,s_card_no,s_delivery,s_number_card,ca2;
    boolean s_paid;
    LinearLayout mContainer,container_bundle,temp, attContainer;
    String json,main_json;
    JSONArray order_list,cancel_list;
    String bu_namez,bu_name,bu_quantity,bu_price,order_main,dis_order_id;
    ImageView img_temp;
    TextView or_main,p;
    String ca_val;
    ArrayList<String> imagesArray = new ArrayList<String>();
    String store_phone,store_user;
    Button cal_log, callLogButton;
    FloatingActionMenu menu;
    public static final String PREFS_SWITCH_CALL = "APP_SWITCH_CALL";
    public static final String PREFS_SWITCH_VALUE_CALL = "APP_SWITCH_VALUE_CALL";
    SharedPreferences switch_prefs_call;
    String CALL_LOG_VALUE;
    ScrollView mainScrollView;
    static final int REQUEST_CODE = 69;
    LinearLayout footerLinearLayout;
    LinearLayout verificationLinearLayout;
    float x1,x2;
    float y1, y2;
    RelativeLayout mainRelativeLayout;
    CircleProgressBar progressView;
    String txc;
    TextView amountTextView;
    FloatingActionButton call_cus,changeStatusButton, addAttachmentsButton;
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
    CollapsingToolbarLayout appBarLayout;
    boolean isGPSEnabled = false;
    private static int UPDATE_INTERVAL = 5000; // 10 sec
    private static int FATEST_INTERVAL = 1000; // 5 sec
    private static int DISPLACEMENT = 5; // 10 meters
    LocationManager locationManager;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    TextView attHeader;
    String type = "";
    double latitude,longitude;
    private Location mLastLocation;
    ConnectionDetector cd;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    String geolocation = "";
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    Handler handler=new Handler();
    Runnable r;
    private LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;
    boolean isNetworkEnabled = false;
    ListView attachmentTypesList;
    RelativeLayout relativeLayout1;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private Callbacks mCallbacks = sDummyCallbacks;

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
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("order_main")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            order_main = getArguments().getString("order_main");
            dis_order_id = getArguments().getString("display_order_id");
            s_delivery = getArguments().getString("orderStatus");
            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(dis_order_id);
            }
        }



        login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
        String response = login_prefs.getString("options", null);
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject data = obj.getJSONObject("data");
            JSONArray postponedJSONARRAY = data.getJSONArray("POSTPONED_RESONS");
            for(int i=0;i<postponedJSONARRAY.length();i++){
                PostponedReasons Preason = new PostponedReasons(postponedJSONARRAY.getJSONObject(i).getString("ID"),
                postponedJSONARRAY.getJSONObject(i).getString("MESSAGE"));
                postPonedreason.add(Preason);
            }

            JSONArray cancelJSONARRAY = data.getJSONArray("CANELLATION_RESONS");
            for(int i=0;i<cancelJSONARRAY.length();i++){
                CancelReasons Creason = new CancelReasons(cancelJSONARRAY.getJSONObject(i).getString("ID"),
                        cancelJSONARRAY.getJSONObject(i).getString("MESSAGE"));
                cancelReasons.add(Creason);
            }

            JSONArray attJSONARRAY = data.getJSONArray("ATTACHMENTS");
            for(int i=0;i<attJSONARRAY.length();i++){
                AttachmentTypes attreason = new AttachmentTypes(attJSONARRAY.getJSONObject(i).getString("ID"),
                        attJSONARRAY.getJSONObject(i).getString("MESSAGE"));
                attTypes.add(attreason);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API).build();

        mLocationRequest = new LocationRequest().create();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        // creating connection detector class instance
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        Log.d("ItemDetailFragment", "onStart(): connecting");
//        if (mGoogleApiClient != null) {
//        mGoogleApiClient.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_details, container, false);
        attHeader = (TextView) rootView.findViewById(R.id.attHeader);
        attachmentTypesList=(ListView)rootView.findViewById(R.id.listView);

        server_pref = getActivity().getSharedPreferences(PREFS_SERVER_NAME, 0);
        serv_txt=server_pref.getString(PREFS_SERVER_VALUE, null);
        //validate="http://"+serv_txt+"/d_login.php";
        url=serv_txt+"details/";
        changeStatusURL=serv_txt+"status/";
        addAttachmentURL = serv_txt+"uploads/";
//        Intent myIntent = getActivity().getIntent();
//        order_main = myIntent.getStringExtra("order_main");
//        dis_order_id = myIntent.getStringExtra("display_order_id");
        System.out.println("order"+order_main);
        or_main=(TextView)rootView.findViewById(R.id.or_main);
        or_main.setText(dis_order_id);
        p=(TextView)rootView.findViewById(R.id.pw);
        ImageView bac=(ImageView)rootView.findViewById(R.id.bac);
        relativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        amountTextView =(TextView)rootView.findViewById(R.id.textView2);
        mainScrollView =(ScrollView)rootView.findViewById(R.id.mainScrollView);
        menu = (FloatingActionMenu) rootView.findViewById(R.id.menu);
        footerLinearLayout =(LinearLayout)rootView.findViewById(R.id.footerLinearLayout);
        progressView =(CircleProgressBar)rootView.findViewById(R.id.progress1);
        progressView.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_purple);
        progressView.setShowArrow(true);
        mainRelativeLayout =(RelativeLayout)rootView.findViewById(R.id.mainRelativeLayout);
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
        verificationLinearLayout =(LinearLayout)rootView.findViewById(R.id.verificationLinearLayout);
        nameOnCardValueTextView =(TextView)rootView.findViewById(R.id.nameOnCardValueTextView);
        cardNumberValueTextView =(TextView)rootView.findViewById(R.id.cardNumberValueTextView);
        callLogButton =(Button)rootView.findViewById(R.id.callLogButton);
        addAttachmentsButton =(FloatingActionButton)rootView.findViewById(R.id.addAttachmentButton);
//                attachmentsButton =(FloatingActionButton)rootView.findViewById(R.id.attachmentsButton);
        addAttachmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add Attachments", Toast.LENGTH_LONG).show();
                addAttachment_sp();
                menu.close(true);
            }
        });
//        attachmentsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Attachments", Toast.LENGTH_LONG).show();
//                showAttachments();
//                menu.close(true);
//            }
//        });
        changeStatusButton =(FloatingActionButton)rootView.findViewById(R.id.changeStatusButton1);
        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss = changeStatusButton.getLabelText().toString();
                if (ss.equals("DELIVERED")||ss.equals("INVALID")) {

                }
                else {
                    status();
                }
                menu.close(true);
            }
        });
        orderDateValueTextView =(TextView)rootView.findViewById(R.id.orderDateValueTextView);
        customerNameValueTextView =(TextView)rootView.findViewById(R.id.customerNameValueTextView);
        mobileValueTextView =(TextView)rootView.findViewById(R.id.mobileValueTextView);
        emirateValueTextView =(TextView)rootView.findViewById(R.id.emirateValueTextView);
        locationValueTextView =(TextView)rootView.findViewById(R.id.locationValueTextView);
        addressValueTextView =(TextView)rootView.findViewById(R.id.addressValueTextView);
        totalPriceValueTextView =(TextView)rootView.findViewById(R.id.totalPriceValueTextView);
        deliveryPriceValueTextView =(TextView)rootView.findViewById(R.id.deliveryPriceValueTextView);
        nameOnCardValueTextView =(TextView)rootView.findViewById(R.id.nameOnCardValueTextView);
        cardNumberValueTextView =(TextView)rootView.findViewById(R.id.cardNumberValueTextView);


        call_cus=(FloatingActionButton)rootView.findViewById(R.id.callCustomerButton);
        call_cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.close(true);
                CALL_LOG_VALUE=order_main;
                System.out.println("CALL_LOG_VALUE" + CALL_LOG_VALUE);
                store_phone=call_cus.getTag().toString();
                store_user= customerNameValueTextView.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + store_phone));
                startActivity(callIntent);
            }
        });


        cal_log=(Button)rootView.findViewById(R.id.callLogButton);
        cal_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CallLogActivity.class);
                myIntent.putExtra("id_main",order_main);
                myIntent.putExtra("display_order_id",dis_order_id);
                startActivity(myIntent);
            }
        });


        mContainer=(LinearLayout)rootView.findViewById(R.id.container);
        attContainer=(LinearLayout)rootView.findViewById(R.id.containerAttachments);
        new details().execute();

        return rootView;
    }


    @Override
    public void onStart() {
//        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onResume() {
        cal_hide();
        super.onResume();
    }

    public void cal_hide()
    {
        switch_prefs_call = getActivity().getSharedPreferences(PREFS_SWITCH_CALL, Context.MODE_PRIVATE);
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

    @Override
    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            stopLocationUpdates();
        }

        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("ItemDetailFragment", "onConnected(): connected to Google APIs");
        System.out.println("onConnected()");
        // Once connected with google api, get the location
        ///////////////////////////////////   displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("ItemDetailFragment", "onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
// Assign the new location
        mLastLocation = location;

        // Toast.makeText(getApplicationContext(), "Location changed!",
        //    Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
             displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("ItemDetailFragment", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    imageTypes.clear();
                    JSONObject data =  response.getJSONObject("data");
//                    JSONObject orderObject = data.getJSONObject("orders");
//                    System.out.println(json.toString());
//                    System.out.println(main_json.toString());
                    order_list = data.getJSONArray("ORDER_LIST");
//                    cancel_list=data.getJSONArray("CANCEL_REASON");
                    s_order_date = data.getString("ORDER_DATE");
                    s_cus_name = data.getString("ORDER_USER");
//                    s_mobile = data.getString("PHONE");
                    s_mobile = "00971562022892";
                    s_emirates = data.getString("CITY");
                    s_location = data.getString("AREA");
                    s_address = data.getString("STREET");
                    s_total_price = data.getString("TOTAL_PRICE");
                    s_del_price = data.getString("DELIVERY_PRICE");
                    s_name_card = data.getString("ORDER_DATE");
                    s_card_no = data.getString("ORDER_DATE");
//                    s_delivery=data.getString("STATUS_ID");
                    s_paid=data.getBoolean("PAYMENT_NEED_VER");
                    //s_name_card=main_json.getString()
                    s_number_card=data.getString("PAYMENT_CARD");
                    ca2=data.getString("COLLECT_PRICE");

//                    if(!data.getBoolean("EXTRA_DATA")){
//                        attachmentsButton.setVisibility(View.GONE);
//                    }
//                    else{
                    attContainer.removeAllViews();
                    JSONArray imagesArray = data.getJSONObject("EXTRA_DATA").getJSONArray("images");
                    if(imagesArray.length()==0){
                        attContainer.setVisibility(View.GONE);
                        attHeader.setVisibility(View.GONE);
//                        TextView tv=new TextView(getActivity());
//                        tv.setText("NONE!");
//                        attContainer.addView(tv);
                    }
                    else{
                        for(int i=0; i<imagesArray.length();i++){
                            TextView tv=new TextView(getActivity());
                            tv.setText(imagesArray.getJSONObject(i).getString("image"));
                            attContainer.addView(tv);
                        }
                    }
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
                    totalPriceValueTextView.setText(s_total_price + " AED");
                    if(s_del_price.equals("Free"))
                    {
                        deliveryPriceValueTextView.setText("FREE");
                    }
                    else
                    {
                        deliveryPriceValueTextView.setText(s_del_price + " AED");
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

                    if(s_paid)
                    {
                        verificationLinearLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        verificationLinearLayout.setVisibility(View.GONE);
                    }

                    System.out.println("Status: "+s_delivery);

                    if(s_delivery.equals("PENDING"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    }
                    else if(s_delivery.equals("DELIVERED"))
                    {
                        or_main.setText(dis_order_id);
                        changeStatusButton.setVisibility(View.GONE);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                    }
                    else if(s_delivery.equals("CANCELLED"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    else if(s_delivery.equals("INVALID"))
                    {
                        or_main.setText(dis_order_id);
                        changeStatusButton.setVisibility(View.GONE);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                    else if(s_delivery.equals("POSTPONED"))
                    {
                            or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                    }

//                    for(int j=0;j<cancel_list.length();j++)
//                    {
//                        try{
//                            reason.add(cancel_list.getString(j));
//                            System.out.println("fhgkjghg" + cancel_list.toString());
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                    }

                    mContainer.removeAllViews();

                    for (int i = 0; i < order_list.length(); i++) {

                        try {
                            JSONObject g = order_list.getJSONObject(i);
                            System.out.println("CHILD CHECK"+g.toString());
                            if(g.has("CHILD"))
                            {
                                System.out.println("CHILD");
                                JSONArray jarray=g.getJSONArray("CHILD");
                                LayoutInflater layoutInflater2 =
                                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                    TextView tv=new TextView(getActivity());
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
                                mContainer.addView(addView2);
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
                                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View addView = getActivity().getLayoutInflater().inflate(R.layout.new_details_bundle, null);
                                LinearLayout bundle=(LinearLayout)addView.findViewById(R.id.bundle);
                                bundle.setVisibility(View.GONE);
                                TextView nz = (TextView) addView.findViewById(R.id.item_name);
                                TextView qu = (TextView) addView.findViewById(R.id.order_quantity);
                                TextView pr = (TextView) addView.findViewById(R.id.price);
                                nz.setText(bu_name.toString());
                                qu.setText(bu_quantity.toString());
                                pr.setText(bu_price.toString()+" AED");
                                mContainer.addView(addView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            menu.setVisibility(View.VISIBLE);
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
            String c_did = login_prefs.getString(APP_LOG_DID, null);
            String c_key = login_prefs.getString(APP_LOG_KEY, null);
            String reportId = login_prefs.getString("reportId", null);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            params.add(new BasicNameValuePair("rid", reportId));
            params.add(new BasicNameValuePair("oid",order_main));
            json = jParser.makeHttpRequest(url, "GET", params);

            return json;
        }
    }

    public void showAttachments()
    {

        attachmentTypesList.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, imageTypes);
        attachmentTypesList.setAdapter(adapter);

    }

    public void status()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.status);
        dialog.setCanceledOnTouchOutside(true);
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
                postponed_sp();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

//                System.out.println("Image data: "+ photos.get(0));
                imagesArray = new ArrayList<String>();
                for(int i=0;i<photos.size();i++){
                    File f = new File(photos.get(i));
//                    File temp = new File(Environment.getExternalStorageDirectory().getPath() ,dis_order_id+"_"+type+"_"+(i+1)+".jpg");
                    File newFile = new File(Environment.getExternalStorageDirectory().getPath() ,dis_order_id+"_"+type+"_"+(i+1)+".jpg");
                    f.renameTo(new File(Environment.getExternalStorageDirectory().getPath() ,dis_order_id+"_"+type+"_"+(i+1)+".jpg"));
                    Bitmap bm = BitmapFactory.decodeFile(newFile.getPath());
                    if(bm!=null){
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 70, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        imagesArray.add(encodedImage);
                    }
                }
                new AddAttachmentAPICALL().execute(imagesArray.toString(), type);
//                Picasso.with(this).load(f).transform(new CircleTransformation()).into(profileImage);
            }
        }
    }

    private void addAttachment_sp() {
        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Select Type Of Attachment");
        dialog.setContentView(R.layout.spinner);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner s=(Spinner)dialog.findViewById(R.id.spinner);
        /**
         *
         * HAVE TO POPULATE OPTIONS HERE
         *
         *
         */
        ArrayList<String> attStringArray = new ArrayList<String>();
        for(int i=0;i<attTypes.size();i++){
            attStringArray.add(attTypes.get(i).getType());
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, attStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(s.getSelectedItem().toString());
                ca_val=s.getSelectedItem().toString();
                for(int i=0;i<attTypes.size();i++){
                    if(ca_val.equalsIgnoreCase(attTypes.get(i).getType())){
                        type = attTypes.get(i).getId();
                    }
                }
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setShowCamera(true);
                intent.setShowGif(true);
                intent.setPhotoCount(5);
                startActivityForResult(intent, REQUEST_CODE);
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

    private void postponed_sp() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner s=(Spinner)dialog.findViewById(R.id.spinner);
        /**
         *
         * HAVE TO POPULATE OPTIONS HERE
         *
         *
         */

        ArrayList<String> postStringArray = new ArrayList<String>();
        for(int i=0;i<postPonedreason.size();i++){
            postStringArray.add(postPonedreason.get(i).getReason());
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, postStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(s.getSelectedItem().toString());
                ca_val=s.getSelectedItem().toString();
                for(int i=0;i<postPonedreason.size();i++){
                    if(ca_val.equalsIgnoreCase(postPonedreason.get(i).getReason())){
                        ca_val = postPonedreason.get(i).getId();
                    }
                }
                new changeStatusAPICall().execute("postponed", ca_val);
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

    private void can_sp() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner s=(Spinner)dialog.findViewById(R.id.spinner);
        /**
         *
         * HAVE TO POPULATE OPTIONS HERE
         *
         *
         */

        ArrayList<String> cancelStringArray = new ArrayList<String>();
        for(int i=0;i<cancelReasons.size();i++){
            cancelStringArray.add(cancelReasons.get(i).getReason());
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cancelStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(s.getSelectedItem().toString());
                ca_val=s.getSelectedItem().toString();
                for(int i=0;i<cancelReasons.size();i++){
                    if(ca_val.equalsIgnoreCase(cancelReasons.get(i).getReason())){
                        ca_val = cancelReasons.get(i).getId();
                    }
                }
                new changeStatusAPICall().execute("cancelled", ca_val);
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
//                changeStatusButton.setText("DELIVERED");
//                changeStatusButton.setBackgroundColor(Color.LTGRAY);
//                changeStatusButton.setEnabled(false);
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




    public class AddAttachmentAPICALL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... paramz) {
            String c_did,c_key;
            JSONParser jParser=new JSONParser();

            c_did = login_prefs.getString(APP_LOG_DID, null);
            c_key = login_prefs.getString(APP_LOG_KEY, null);
            String reportId = login_prefs.getString("reportId", null);
            String image = paramz[0];
            String option = paramz[1];
            System.out.println("c_did="+c_did);
            System.out.println("c_key="+c_key);
            System.out.println("attachment="+image);
            System.out.println("option="+option);
            System.out.println("rid="+reportId);
            System.out.println("onm=" + dis_order_id);
            System.out.println("key=" + c_key);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("did", c_did));
            params.add(new BasicNameValuePair("key", c_key));
            for(int i =0;i<imagesArray.size();i++){
                String key = "attachment["+i+"]";
                params.add(new BasicNameValuePair(key, imagesArray.get(i)));
            }
            params.add(new BasicNameValuePair("option", option));
            params.add(new BasicNameValuePair("rid", reportId));
            params.add(new BasicNameValuePair("onm", dis_order_id));
            System.out.println(c_key);
            json = jParser.makeHttpRequest(addAttachmentURL, "POST", params);
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
                    new details().execute();
                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressView.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
            p.setText("Please Wait...");
            menu.setVisibility(View.VISIBLE);
            mainScrollView.setVisibility(View.VISIBLE);
            footerLinearLayout.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
        }




        @Override
        protected void onPreExecute() {
            progressView.setVisibility(View.VISIBLE);
            p.setVisibility(View.VISIBLE);
            p.setText("Uploading Attachment");
            menu.setVisibility(View.GONE);
            mainScrollView.setVisibility(View.GONE);
            footerLinearLayout.setVisibility(View.GONE);
            super.onPreExecute();
        }
    }

    private class changeStatusAPICall extends AsyncTask<String, Void, String> {

        @Override

        protected void onPostExecute(String s) {

            String message = "";
            boolean status;
            JSONObject response;
            JSONObject data;
            try {
                response = new JSONObject(s);
                status = response.getBoolean("status");
                message = response.getString("message");

                if(status) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    data = response.getJSONObject("data");
                    s_delivery = data.getString("status").toUpperCase();
                    System.out.println("New Status: "+s_delivery);
                    if(s_delivery.equals("PENDING"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    }
                    else if(s_delivery.equals("DELIVERED"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                        changeStatusButton.setVisibility(View.GONE);
                    }
                    else if(s_delivery.equals("CANCELLED"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    else if(s_delivery.equals("INVALID"))
                    {
                        or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        changeStatusButton.setVisibility(View.GONE);
                    }
                    else if(s_delivery.equals("POSTPONED"))
                    {
                            or_main.setText(dis_order_id);
                        relativeLayout1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                    }

                    mCallbacks.updateListing();
                    progressView.setVisibility(View.GONE);
                    p.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    progressView.setVisibility(View.GONE);
                    p.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
            login_prefs = getActivity().getSharedPreferences(APP_LOGIN, Context.MODE_PRIVATE);
            String c_did = login_prefs.getString(APP_LOG_DID, null);
            String c_key = login_prefs.getString(APP_LOG_KEY, null);
            String reportId = login_prefs.getString("reportId", null);
            String status = params[0];
            System.out.println("c_did="+c_did);
            System.out.println("c_key="+c_key);
            System.out.println("status="+status);
            System.out.println("rid="+reportId);
            System.out.println("onm=" + dis_order_id);
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("did", c_did));
            params2.add(new BasicNameValuePair("key", c_key));
            params2.add(new BasicNameValuePair("status", status));
            if(status.equalsIgnoreCase("delivered")){
                params2.add(new BasicNameValuePair("glocation", params[3]));
                params2.add(new BasicNameValuePair("attachment", params[2]));
                params2.add(new BasicNameValuePair("receive_amount", params[1]));
            }
            else{
                params2.add(new BasicNameValuePair("comment", params[1]));
            }

            params2.add(new BasicNameValuePair("rid", reportId));
            params2.add(new BasicNameValuePair("onm", dis_order_id));
            json = jParser.makeHttpRequest(changeStatusURL, "POST", params2);
            System.out.println("POST"+json);

//            try {
//                txc=json.getString("key");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return json;
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





    public void new_pass()
    {
        final Dialog dialog = new Dialog(getActivity());
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
                settings = getActivity().getSharedPreferences(PREFS_NAME, 0); //1
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
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        dialog.setCanceledOnTouchOutside(false);
        pass=(EditText)dialog.findViewById(R.id.edit_pass);
        pass.setHint("Enter Password");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = "";
                new_pass = pass.getText().toString();
                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String next = settings.getString(PREFS_SET, null);
                System.out.println("PREF" + next);

                if (next.equals(new_pass)) {
                    Intent i = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(i);
                    dialog.dismiss();
                } else {
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

    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }



    public void welcome_del()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCanceledOnTouchOutside(false);

        final SignatureView signature = (SignatureView) dialog.findViewById(R.id.signature);
        final EditText totalAmount = (EditText)dialog.findViewById(R.id.totalCostEditText);
        final Button editPriceButton = (Button) dialog.findViewById(R.id.editButton);
        Button refreshCanvasButton = (Button) dialog.findViewById(R.id.refreshButton);
//        final ImageView image = (ImageView)dialog.findViewById(R.id.image);
        totalAmount.setText(s_total_price);
//        conf=(TextView)dialog.findViewById(R.id.edit_pass);
//        conf.setText("Are you sure order is Delivered ?");
        totalAmount.setFocusable(false);
        totalAmount.setEnabled(false);

//        final View activityRootView = dialog.findViewById(R.id.mainRelativeLayout);
//        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
//                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
//                    if (totalAmount.isSelected()||totalAmount.isFocused()){
//                        totalAmount.setFocusable(false);
//                        totalAmount.setEnabled(false);
//                    }
//                }
//            }
//        });

        Button ok = (Button) dialog.findViewById(R.id.acceptButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature.setBackgroundColor(white);
                Bitmap sign = signature.getBitmap();
                Drawable bmp = signature.getBackground();
//                Bitmap bJPGcompress = codec(sign, Bitmap.CompressFormat.JPEG, 80);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                sign.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
//                image.setImageDrawable(bmp);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                String signature_64 = encodedImage;
                displayLocation();
                System.out.println("price: "+s_total_price+" geolocation: "+geolocation+" signation_64: "+signature_64);
                new changeStatusAPICall().execute("delivered", s_total_price, signature_64, geolocation );

                dialog.dismiss();
            }
        });

        refreshCanvasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature.clear();
            }
        });

        editPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount.setEnabled(true);
                totalAmount.setFocusable(true);
                totalAmount.setFocusableInTouchMode(true);
                totalAmount.requestFocus();
            }
        });

        totalAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here\
                    totalAmount.setFocusable(false);
                    totalAmount.setEnabled(false);
                }
                return false;
            }
        });


//        Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    protected void startLocationUpdates() {
        System.out.println("startLocationUpdates()");
        fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient, mLocationRequest, this);

    }


    protected void stopLocationUpdates() {
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    private void displayLocation() {
        isInternetPresent = cd.isConnectingToInternet();
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext()
                .getSystemService(getActivity().LOCATION_SERVICE);

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







            mLastLocation = fusedLocationProviderApi
                    .getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                System.out.println("LAT" + latitude + "LAN" + longitude);

                //   Toast.makeText(this, "LAT" + latitude + "LAN" + longitude, Toast.LENGTH_LONG).show();


                }
            else{
//                mLastLocation = LocationServices.FusedLocationApi.
            }
            geolocation = latitude+"-"+longitude;
        }
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }



    public void welcome_pos()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm);
        dialog.setCanceledOnTouchOutside(false);
        conf=(TextView)dialog.findViewById(R.id.edit_pass);
        conf.setText("Are you sure order is Postponed ?");
        Button ok=(Button)dialog.findViewById(R.id.done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new changeStatusAPICall().execute("postponed");
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
//                changeStatusButton.setText("CANCELED");
//                changeStatusButton.setBackgroundColor(Color.parseColor("#e24545"));
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

}
