package dapp.com.awok.awokdriversapp.Modals;

/**
 * Created by mohsin on 1/20/2016.
 */
public class PostponedReasons {
    String id;
    String reason;

    public PostponedReasons(String id, String reason){
        this.id=id;
        this.reason=reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
