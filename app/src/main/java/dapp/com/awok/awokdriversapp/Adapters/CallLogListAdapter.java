package dapp.com.awok.awokdriversapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import dapp.com.awok.awokdriversapp.Modals.CallLog;
import dapp.com.awok.awokdriversapp.R;

/**
 * Created by shon on 5/6/2015.
 */
public class CallLogListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<CallLog> call_items;

    public CallLogListAdapter(Activity activity, List<CallLog> call_items) {
        this.activity = activity;
        this.call_items = call_items;
    }

    @Override
    public int getCount() {
        return call_items.size();
    }

    @Override
    public Object getItem(int location) {
        return call_items.get(location);
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
            convertView = inflater.inflate(R.layout.call_row, null);


        TextView pd = (TextView) convertView.findViewById(R.id.phone_details);
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        RelativeLayout rowLayout = (RelativeLayout) convertView.findViewById(R.id.rowLayout);
        // getting movie data for the row
        CallLog m = call_items.get(position);

        // thumbnail image
       // thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        //title.setText(m.getTitle());

        // rating
        pd.setText(String.valueOf(m.getPhone()));

        if(m.getDuration().equalsIgnoreCase("0")){
            rowLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_red));
        }
        else{
            rowLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
        }

       duration.setText(String.valueOf(m.getDuration())+" Seconds");
        time.setText(String.valueOf(m.getTime()));

        return convertView;
    }
}
