package dapp.com.awok.awokdriversapp.Modals;

/**
 * Created by mohsin on 1/20/2016.
 */
public class AttachmentTypes {
    String id;
    String type;

    public AttachmentTypes(String id, String type){
        this.id=id;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
