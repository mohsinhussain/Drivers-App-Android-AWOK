package dapp.com.awok.awokdriversapp.Modals;

/**
 * Created by shon on 5/6/2015.
 */
public class CallLog {
    private String Duration, Time,Phone;
    public CallLog() {
    }

    public CallLog(String Duration, String Time, String Phone) {
        this.Duration = Duration;
        this.Time = Time;
        this.Phone = Phone;


    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }



    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }


}
