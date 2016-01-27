package dapp.com.awok.awokdriversapp.Utils;

import dapp.com.awok.awokdriversapp.Modals.Order;

/**
 * Created by mohsin on 1/14/2016.
 */
public interface Callbacks {
    /**
     * Callback for when an item has been selected.
     */
    public void onItemSelected(Order order);
    public void updateTitle(String name, String date, String area);
    public void updateListing();
}
